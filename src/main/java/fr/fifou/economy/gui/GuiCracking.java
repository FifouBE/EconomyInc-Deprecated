package fr.fifou.economy.gui;

import fr.fifou.economy.ModEconomy;
import fr.fifou.economy.blocks.tileentity.TileEntityBlockVaultCracked;
import fr.fifou.economy.packets.PacketOpenCracked;
import fr.fifou.economy.packets.PacketsRegistery;
import java.io.IOException;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.config.GuiButtonExt;

public class GuiCracking extends GuiScreen
{
    private TileEntityBlockVaultCracked tile;
    private static final ResourceLocation background = new ResourceLocation("economy", "textures/gui/screen/gui_cracking.png");
    protected int xSize = 176;
    protected int ySize = 168;
    protected int xSizeButton = 20;
    protected int ySizeButton = 20;
    protected int guiLeft;
    protected int guiTop;
    private int counter = 0;
    private int ic_0 = 0;
    private int ic_1 = 0;
    private int ic_2 = 0;
    private int ic_3 = 0;
    private int pass_0 = 0;
    private int pass_1 = 0;
    private int pass_2 = 0;
    private int pass_3 = 0;
    private String pass_full = "";
    protected GuiButtonExt button_0;
    protected GuiButtonExt button_1;
    protected GuiButtonExt button_2;
    protected GuiButtonExt button_3;
    protected GuiButtonExt button_4;
    protected GuiButtonExt button_5;
    protected GuiButtonExt button_6;
    protected GuiButtonExt button_7;
    protected GuiButtonExt button_8;
    protected GuiButtonExt button_9;
    protected GuiButtonExt button_10;
    protected GuiButtonExt button_11;

    public GuiCracking(TileEntityBlockVaultCracked tile)
    {
        this.tile = tile;
    }

    /**
     * Called from the main game loop to update the screen.
     */
    public void updateScreen()
    {
        super.updateScreen();
    }

    /**
     * Adds the buttons (and other controls) to the screen in question. Called when the GUI is displayed and when the
     * window resizes, the buttonList is cleared beforehand.
     */
    public void initGui()
    {
        this.guiLeft = (this.width - this.xSize) / 2;
        this.guiTop = (this.height - this.ySize) / 2;
        this.buttonList.add(this.button_0 = new GuiButtonExt(0, this.width / 2 - 10, this.height / 2 + 50, 20, 20, "0"));
        this.buttonList.add(this.button_1 = new GuiButtonExt(1, this.width / 2 - 35, this.height / 2 + 25, 20, 20, "1"));
        this.buttonList.add(this.button_2 = new GuiButtonExt(2, this.width / 2 - 10, this.height / 2 + 25, 20, 20, "2"));
        this.buttonList.add(this.button_3 = new GuiButtonExt(3, this.width / 2 + 15, this.height / 2 + 25, 20, 20, "3"));
        this.buttonList.add(this.button_4 = new GuiButtonExt(4, this.width / 2 - 35, this.height / 2, 20, 20, "4"));
        this.buttonList.add(this.button_5 = new GuiButtonExt(5, this.width / 2 - 10, this.height / 2, 20, 20, "5"));
        this.buttonList.add(this.button_6 = new GuiButtonExt(6, this.width / 2 + 15, this.height / 2, 20, 20, "6"));
        this.buttonList.add(this.button_7 = new GuiButtonExt(7, this.width / 2 - 35, this.height / 2 - 25, 20, 20, "7"));
        this.buttonList.add(this.button_8 = new GuiButtonExt(8, this.width / 2 - 10, this.height / 2 - 25, 20, 20, "8"));
        this.buttonList.add(this.button_9 = new GuiButtonExt(9, this.width / 2 + 15, this.height / 2 - 25, 20, 20, "9"));
        this.buttonList.add(this.button_10 = new GuiButtonExt(10, this.width / 2 - 35, this.height / 2 + 50, 20, 20, "X"));
        this.buttonList.add(this.button_11 = new GuiButtonExt(11, this.width / 2 + 15, this.height / 2 + 50, 20, 20, "V"));
    }

    /**
     * Returns true if this GUI should pause the game when it is displayed in single-player
     */
    public boolean doesGuiPauseGame()
    {
        return false;
    }

    /**
     * Draws the screen and all the components in it.
     */
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        this.drawDefaultBackground();
        this.mc.getTextureManager().bindTexture(background);
        int i = this.guiLeft;
        int j = this.guiTop;
        this.drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);
        super.drawScreen(mouseX, mouseY, partialTicks);
        this.drawImageInGui();
    }

    /**
     * Called by the controls from the buttonList when activated. (Mouse pressed for buttons)
     */
    protected void actionPerformed(GuiButton button) throws IOException
    {
        super.actionPerformed(button);

        switch (button.id)
        {
            case 0:
                this.countCheck(0);
                break;

            case 1:
                this.countCheck(1);
                break;

            case 2:
                this.countCheck(2);
                break;

            case 3:
                this.countCheck(3);
                break;

            case 4:
                this.countCheck(4);
                break;

            case 5:
                this.countCheck(5);
                break;

            case 6:
                this.countCheck(6);
                break;

            case 7:
                this.countCheck(7);
                break;

            case 8:
                this.countCheck(8);
                break;

            case 9:
                this.countCheck(9);
                break;

            case 10:
                this.mc.player.openGui(ModEconomy.instance, 10, this.mc.world, this.tile.getPos().getX(), this.tile.getPos().getY(), this.tile.getPos().getZ());
                break;

            case 11:
                this.pass_full = this.pass_0 + String.valueOf(this.pass_1) + this.pass_2 + this.pass_3;

                if (this.tile.getPassword().equals(this.pass_full))
                {
                    PacketsRegistery.network.sendToServer(new PacketOpenCracked(this.tile.getPos().getX(), this.tile.getPos().getY(), this.tile.getPos().getZ(), true));
                }
                else
                {
                    this.mc.player.closeScreen();
                }
        }
    }

    private void countCheck(int i)
    {
        if (this.counter == 0)
        {
            this.ic_0 = 20 * i;
            this.counter = 1;
            this.pass_0 = i;
        }
        else if (this.counter == 1)
        {
            this.ic_1 = 20 * i;
            this.counter = 2;
            this.pass_1 = i;
        }
        else if (this.counter == 2)
        {
            this.ic_2 = 20 * i;
            this.counter = 3;
            this.pass_2 = i;
        }
        else if (this.counter == 3)
        {
            this.ic_3 = 20 * i;
            this.counter = 4;
            this.pass_3 = i;
        }
    }

    public void drawImageInGui()
    {
        int i = this.guiLeft + 46;
        int j = this.guiTop + 27;
        this.mc.getTextureManager().bindTexture(background);
        this.drawTexturedModalRect(i + 0, j, 236, 0 + this.ic_0, this.xSizeButton, this.ySizeButton);
        this.drawTexturedModalRect(i + 21, j, 236, 0 + this.ic_1, this.xSizeButton, this.ySizeButton);
        this.drawTexturedModalRect(i + 42, j, 236, 0 + this.ic_2, this.xSizeButton, this.ySizeButton);
        this.drawTexturedModalRect(i + 63, j, 236, 0 + this.ic_3, this.xSizeButton, this.ySizeButton);
    }

    /**
     * Called when the screen is unloaded. Used to disable keyboard repeat events
     */
    public void onGuiClosed()
    {
        super.onGuiClosed();
        this.counter = 0;
    }
}
