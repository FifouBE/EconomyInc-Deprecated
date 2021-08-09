package fr.fifou.economy.gui;

import fr.fifou.economy.blocks.tileentity.TileEntityBlockChanger;
import fr.fifou.economy.containers.ContainerChanger;
import java.awt.Color;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentTranslation;
import org.lwjgl.opengl.GL11;

public class GuiChanger extends GuiContainer
{
    private TileEntityBlockChanger tile;
    private boolean isProcessing;
    private static final ResourceLocation background = new ResourceLocation("economy", "textures/gui/container/gui_changer.png");

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

    public GuiChanger(InventoryPlayer playerInventory, TileEntityBlockChanger tileIn)
    {
        super(new ContainerChanger(playerInventory, tileIn));
        this.tile = tileIn;
    }

    /**
     * Called from the main game loop to update the screen.
     */
    public void updateScreen()
    {
        super.updateScreen();
    }

    /**
     * Returns true if this GUI should pause the game when it is displayed in single-player
     */
    public boolean doesGuiPauseGame()
    {
        return false;
    }

    /**
     * Draw the foreground layer for the GuiContainer (everything in front of the items)
     */
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
    {
        this.fontRenderer.drawString((new TextComponentTranslation(I18n.format("title.block_changer", new Object[0]), new Object[0])).getFormattedText(), this.xSize / 2 - this.fontRenderer.getStringWidth(I18n.format("title.block_changer", new Object[0])) / 2, 4, Color.DARK_GRAY.getRGB());
        this.fontRenderer.drawString((new TextComponentTranslation("Inventory", new Object[0])).getFormattedText(), 7, 72, Color.DARK_GRAY.getRGB());
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

        if (this.tile != null)
        {
            float f = (float)this.tile.getTimePassed() / 356.0F * 56.0F;

            if (this.tile.isProcessing)
            {
                this.drawTexturedModalRect(i + 55, j + 34, 176, 0, Math.round(f), this.ySize);
            }
        }
    }
}
