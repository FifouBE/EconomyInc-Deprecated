package fr.fifou.economy.gui;

import fr.fifou.economy.ModEconomy;
import fr.fifou.economy.blocks.tileentity.TileEntityBlockVault2by2;
import fr.fifou.economy.containers.ContainerVault2by2;
import java.awt.Color;
import java.io.IOException;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.client.config.GuiButtonExt;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

public class GuiVault2by2 extends GuiContainer
{
    protected TileEntityBlockVault2by2 tile_getter;
    protected InventoryPlayer playerInventory_getter;
    private static final ResourceLocation background = new ResourceLocation("economy", "textures/gui/container/gui_vault2by2.png");

    /** The X size of the inventory window in pixels. */
    protected int xSize = 176;

    /** The Y size of the inventory window in pixels. */
    protected int ySize = 222;

    /**
     * Starting X position for the Gui. Inconsistent use for Gui backgrounds.
     */
    protected int guiLeft;

    /**
     * Starting Y position for the Gui. Inconsistent use for Gui backgrounds.
     */
    protected int guiTop;

    public GuiVault2by2(InventoryPlayer playerInventory, TileEntityBlockVault2by2 tile)
    {
        super(new ContainerVault2by2(playerInventory, tile));
        this.tile_getter = tile;
        this.playerInventory_getter = playerInventory;
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

        if (this.tile_getter.getOwnerS().equals(this.mc.player.getUniqueID().toString()) && !Minecraft.getMinecraft().isSingleplayer())
        {
            this.buttonList.add(new GuiButtonExt(0, i + 161, j, 15, 15, TextFormatting.BOLD.toString() + TextFormatting.WHITE + "\u00e2\u0161\u2122"));
        }
    }

    /**
     * Called from the main game loop to update the screen.
     */
    public void updateScreen()
    {
        super.updateScreen();
    }

    /**
     * Draw the foreground layer for the GuiContainer (everything in front of the items)
     */
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
    {
        this.fontRenderer.drawString((new TextComponentTranslation(I18n.format("title.block_vault", new Object[0]), new Object[0])).getFormattedText(), 7, -22, Color.DARK_GRAY.getRGB());
        this.fontRenderer.drawString((new TextComponentTranslation("Inventory", new Object[0])).getFormattedText(), 7, 101, Color.DARK_GRAY.getRGB());
    }

    /**
     * Called by the controls from the buttonList when activated. (Mouse pressed for buttons)
     */
    protected void actionPerformed(GuiButton button) throws IOException
    {
        switch (button.id)
        {
            case 0:
                this.playerInventory_getter.player.openGui(ModEconomy.instance, 8, this.playerInventory_getter.player.world, this.tile_getter.getPos().getX(), this.tile_getter.getPos().getY(), this.tile_getter.getPos().getZ());

            default:
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
