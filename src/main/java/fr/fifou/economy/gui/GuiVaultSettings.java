package fr.fifou.economy.gui;

import com.google.common.collect.Lists;
import fr.fifou.economy.blocks.tileentity.TileEntityBlockVault;
import fr.fifou.economy.packets.PacketListNBT;
import fr.fifou.economy.packets.PacketsRegistery;
import java.io.IOException;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiLabel;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.client.event.GuiScreenEvent.ActionPerformedEvent.Post;
import net.minecraftforge.client.event.GuiScreenEvent.ActionPerformedEvent.Pre;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.config.GuiButtonExt;
import org.lwjgl.input.Keyboard;

public class GuiVaultSettings extends GuiScreen
{
    private static final ResourceLocation background = new ResourceLocation("economy", "textures/gui/screen/gui_item.png");
    protected int xSize = 256;
    protected int ySize = 124;
    protected int guiLeft;
    protected int guiTop;
    private GuiTextField addPlayersToList;
    private TileEntityBlockVault tile;
    protected List<GuiButton> properButtonList = Lists.<GuiButton>newArrayList();
    protected List<GuiLabel> properLabelList = Lists.<GuiLabel>newArrayList();
    protected GuiButton properSelectedButton;

    public GuiVaultSettings(TileEntityBlockVault tile)
    {
        this.tile = tile;
    }

    /**
     * Adds the buttons (and other controls) to the screen in question. Called when the GUI is displayed and when the
     * window resizes, the buttonList is cleared beforehand.
     */
    public void initGui()
    {
        int i = (this.width - this.xSize) / 2;
        int j = (this.height - this.ySize) / 2;
        Keyboard.enableRepeatEvents(true);
        this.addPlayersToList = new GuiTextField(0, this.fontRenderer, i + 50, j + 110, 155, 12);
        this.addPlayersToList.setMaxStringLength(35);
        this.addPlayersToList.setEnabled(false);
        this.addPlayersToList.setText("Add other players.");
    }

    /**
     * Called from the main game loop to update the screen.
     */
    public void updateScreen()
    {
        this.properButtonList.clear();

        for (int i = 0; i < this.tile.getOthers().size(); ++i)
        {
            this.properButtonList.add(new GuiButtonExt(i, this.width / 2 + 35, this.height / 2 - 55 + i * 20, 40, 13, TextFormatting.DARK_RED + "\u00e2\u0153\u2013"));
        }

        if (this.tile.getMax() == 5)
        {
            this.addPlayersToList.setEnabled(false);
            this.addPlayersToList.setText("Max players allowed reached");
        }
    }

    /**
     * Draws the screen and all the components in it.
     */
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        this.drawDefaultBackground();
        this.mc.getTextureManager().bindTexture(background);
        int i = (this.width - this.xSize) / 2;
        int j = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);
        super.drawScreen(mouseX, mouseY, partialTicks);
        GlStateManager.disableLighting();
        GlStateManager.disableBlend();

        if (!Minecraft.getMinecraft().isSingleplayer() && this.tile.getOwnerS().equals(Minecraft.getMinecraft().player.getUniqueID().toString()))
        {
            this.addPlayersToList.drawTextBox();
        }

        for (int k = 0; k < this.tile.getOthers().size(); ++k)
        {
            this.fontRenderer.drawString(((String)this.tile.getOthers().get(k)).toString(), this.width / 2 - 70, this.height / 2 - 52 + k * 20, 0);
        }

        for (int l = 0; l < this.properButtonList.size(); ++l)
        {
            ((GuiButton)this.properButtonList.get(l)).drawButton(this.mc, mouseX, mouseY, partialTicks);
        }

        for (int i1 = 0; i1 < this.properLabelList.size(); ++i1)
        {
            ((GuiLabel)this.properLabelList.get(i1)).drawLabel(this.mc, mouseX, mouseY);
        }
    }

    /**
     * Called when the screen is unloaded. Used to disable keyboard repeat events
     */
    public void onGuiClosed()
    {
        super.onGuiClosed();
        Keyboard.enableRepeatEvents(false);
    }

    /**
     * Fired when a key is typed (except F11 which toggles full screen). This is the equivalent of
     * KeyListener.keyTyped(KeyEvent e). Args : character (character on the key), keyCode (lwjgl Keyboard key code)
     */
    protected void keyTyped(char typedChar, int keyCode) throws IOException
    {
        if (keyCode == 1)
        {
            super.keyTyped(typedChar, keyCode);
        }
        else if (keyCode != 28 && keyCode != 156)
        {
            this.addPlayersToList.textboxKeyTyped(typedChar, keyCode);
        }
        else if (this.tile.getMax() < 5)
        {
            this.addPlayerToTileEntity();
            this.addPlayersToList.setText("");
        }
        else
        {
            this.addPlayersToList.setText("Max players allowed reached ");
        }
    }

    private void addPlayerToTileEntity()
    {
        String s = this.addPlayersToList.getText();
        EntityPlayer entityplayer = Minecraft.getMinecraft().world.getPlayerEntityByName(s);

        if (entityplayer != null)
        {
            String s1 = entityplayer.getName();
            PacketsRegistery.network.sendToServer(new PacketListNBT(s1, this.tile.getPos().getX(), this.tile.getPos().getY(), this.tile.getPos().getZ(), false, "add", 0));
        }
    }

    /**
     * Called by the controls from the buttonList when activated. (Mouse pressed for buttons)
     */
    protected void actionPerformed(GuiButton button) throws IOException
    {
        switch (this.properSelectedButton.id)
        {
            case 0:
                PacketsRegistery.network.sendToServer(new PacketListNBT("", this.tile.getPos().getX(), this.tile.getPos().getY(), this.tile.getPos().getZ(), false, "remove", 0));
                break;

            case 1:
                PacketsRegistery.network.sendToServer(new PacketListNBT("", this.tile.getPos().getX(), this.tile.getPos().getY(), this.tile.getPos().getZ(), false, "remove", 1));
                break;

            case 2:
                PacketsRegistery.network.sendToServer(new PacketListNBT("", this.tile.getPos().getX(), this.tile.getPos().getY(), this.tile.getPos().getZ(), false, "remove", 2));
                break;

            case 3:
                PacketsRegistery.network.sendToServer(new PacketListNBT("", this.tile.getPos().getX(), this.tile.getPos().getY(), this.tile.getPos().getZ(), false, "remove", 3));
                break;

            case 4:
                PacketsRegistery.network.sendToServer(new PacketListNBT("", this.tile.getPos().getX(), this.tile.getPos().getY(), this.tile.getPos().getZ(), false, "remove", 4));
        }
    }

    /**
     * Called when the mouse is clicked. Args : mouseX, mouseY, clickedButton
     */
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException
    {
        super.mouseClicked(mouseX, mouseY, mouseButton);

        if (this.addPlayersToList.mouseClicked(mouseX, mouseY, mouseButton))
        {
            this.addPlayersToList.setText("");
            this.addPlayersToList.setEnabled(true);
        }

        if (mouseButton == 0)
        {
            for (int i = 0; i < this.properButtonList.size(); ++i)
            {
                GuiButton guibutton = this.properButtonList.get(i);

                if (guibutton.mousePressed(this.mc, mouseX, mouseY))
                {
                    Pre pre = new Pre(this, guibutton, this.properButtonList);

                    if (MinecraftForge.EVENT_BUS.post(pre))
                    {
                        break;
                    }

                    guibutton = pre.getButton();
                    this.properSelectedButton = guibutton;
                    guibutton.playPressSound(this.mc.getSoundHandler());
                    this.actionPerformed(guibutton);

                    if (this.equals(this.mc.currentScreen))
                    {
                        MinecraftForge.EVENT_BUS.post(new Post(this, pre.getButton(), this.properButtonList));
                    }
                }
            }
        }
    }

    /**
     * Called when a mouse button is released.
     */
    protected void mouseReleased(int mouseX, int mouseY, int state)
    {
        if (this.properSelectedButton != null && state == 0)
        {
            this.properSelectedButton.mouseReleased(mouseX, mouseY);
            this.properSelectedButton = null;
        }
    }

    /**
     * Returns true if this GUI should pause the game when it is displayed in single-player
     */
    public boolean doesGuiPauseGame()
    {
        return false;
    }
}
