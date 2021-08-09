package fr.fifou.economy.gui;

import fr.fifou.economy.containers.ContainerInformaterTrade;
import fr.fifou.economy.items.ItemHundreedb;
import fr.fifou.economy.packets.PacketInformaterTrading;
import fr.fifou.economy.packets.PacketsRegistery;
import java.io.IOException;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.client.config.GuiButtonExt;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

public class GuiInformaterTrade extends GuiContainer
{
    protected InventoryPlayer playerInventory_getter;
    protected int safeCode;
    private static final ResourceLocation background = new ResourceLocation("economy", "textures/gui/container/gui_infor_trader.png");

    /** The X size of the inventory window in pixels. */
    protected int xSize = 176;

    /** The Y size of the inventory window in pixels. */
    protected int ySize = 168;

    /**
     * Starting X position for the Gui. Inconsistent use for Gui backgrounds.
     */
    protected int guiLeft;

    /**
     * Starting Y position for the Gui. Inconsistent use for Gui backgrounds.
     */
    protected int guiTop;
    private GuiButtonExt validate;

    public GuiInformaterTrade(InventoryPlayer playerInventory, int code)
    {
        super(new ContainerInformaterTrade(playerInventory));
        this.playerInventory_getter = playerInventory;
        this.safeCode = code;
    }

    /**
     * Adds the buttons (and other controls) to the screen in question. Called when the GUI is displayed and when the
     * window resizes, the buttonList is cleared beforehand.
     */
    public void initGui()
    {
        super.initGui();
        Keyboard.enableRepeatEvents(true);
        int i = (this.width - this.xSize) / 2;
        int j = (this.height - this.ySize) / 2;
        this.buttonList.add(this.validate = new GuiButtonExt(1, this.width / 2 + 26, this.height / 2 - 20, 50, 18, I18n.format("title.accept", new Object[0])));
    }

    /**
     * Called from the main game loop to update the screen.
     */
    public void updateScreen()
    {
        super.updateScreen();
    }

    /**
     * Called by the controls from the buttonList when activated. (Mouse pressed for buttons)
     */
    protected void actionPerformed(GuiButton button) throws IOException
    {
        if (button == this.validate)
        {
            EntityPlayer entityplayer = this.mc.player;
            World world = entityplayer.world;
            int i = 0;

            for (int j = 0; j < entityplayer.inventory.getSizeInventory(); ++j)
            {
                if (entityplayer.inventory.getStackInSlot(j).getItem() instanceof ItemHundreedb)
                {
                    ++i;

                    if (i <= 1)
                    {
                        PacketsRegistery.network.sendToServer(new PacketInformaterTrading(j, this.safeCode));
                        entityplayer.inventory.getStackInSlot(j).setCount(entityplayer.inventory.getStackInSlot(j).getCount() - 1);
                        entityplayer.inventory.addItemStackToInventory((new ItemStack(Items.PAPER)).setStackDisplayName("Code : " + String.valueOf(this.safeCode)));
                    }
                }
            }
        }
    }

    /**
     * Draws the screen and all the components in it.
     */
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        this.drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
        this.drawScreen(mouseX, mouseY, partialTicks);
        this.fontRenderer.drawString(I18n.format("title.stranger", new Object[0]), this.width / 2 - 81, this.height / 2 - 75, 0);
        this.fontRenderer.drawString(I18n.format("title.informations", new Object[0]), this.width / 2 - 81, this.height / 2 - 60, 0);
        this.fontRenderer.drawString(I18n.format("title.seevault", new Object[0]), this.width / 2 - 81, this.height / 2 - 50, 0);
        this.fontRenderer.drawString(I18n.format("title.codevault", new Object[0]), this.width / 2 - 81, this.height / 2 - 40, 0);
        this.fontRenderer.drawString(I18n.format("title.onlyhundred", new Object[0]), this.width / 2 - 81, this.height / 2 - 30, 0);
        this.fontRenderer.drawString(I18n.format("title.deal", new Object[0]), this.width / 2 - 81, this.height / 2 - 15, 0);
    }

    /**
     * Draws the background layer of this container (behind the items).
     */
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
    {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(background);
        int i = (this.width - this.xSize) / 2;
        int j = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);
    }

    /**
     * Called when the screen is unloaded. Used to disable keyboard repeat events
     */
    public void onGuiClosed()
    {
        super.onGuiClosed();
    }
}
