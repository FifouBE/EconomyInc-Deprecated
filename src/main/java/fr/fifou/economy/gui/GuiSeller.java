package fr.fifou.economy.gui;

import fr.fifou.economy.blocks.tileentity.TileEntityBlockSeller;
import fr.fifou.economy.containers.ContainerSeller;
import fr.fifou.economy.packets.PacketSellerCreated;
import fr.fifou.economy.packets.PacketsRegistery;
import java.awt.Color;
import java.io.IOException;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.fml.client.config.GuiButtonExt;
import org.lwjgl.opengl.GL11;

public class GuiSeller extends GuiContainer
{
    private TileEntityBlockSeller tile;
    private static final ResourceLocation background = new ResourceLocation("economy", "textures/gui/container/gui_seller.png");

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
    private GuiButtonExt one;
    private GuiButtonExt five;
    private GuiButtonExt ten;
    private GuiButtonExt twenty;
    private GuiButtonExt fifty;
    private GuiButtonExt hundreed;
    private GuiButtonExt twoHundreed;
    private GuiButtonExt fiveHundreed;
    private GuiButtonExt unlimitedStack;
    private static double cost;
    private boolean admin = false;

    public GuiSeller(InventoryPlayer playerInventory, TileEntityBlockSeller tile)
    {
        super(new ContainerSeller(playerInventory, tile));
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
        super.initGui();
        World world = this.mc.world;
        EntityPlayer entityplayer = this.mc.player;

        if (!this.tile.getCreated())
        {
            this.buttonList.add(this.validate = new GuiButtonExt(1, this.width / 2 + 26, this.height / 2 + 83, 55, 20, I18n.format("title.validate", new Object[0])));
            this.buttonList.add(this.one = new GuiButtonExt(2, this.width / 2 - 122, this.height / 2 - 75, 35, 20, "1"));
            this.buttonList.add(this.five = new GuiButtonExt(3, this.width / 2 - 122, this.height / 2 - 56, 35, 20, "5"));
            this.buttonList.add(this.ten = new GuiButtonExt(4, this.width / 2 - 122, this.height / 2 - 37, 35, 20, "10"));
            this.buttonList.add(this.twenty = new GuiButtonExt(5, this.width / 2 - 122, this.height / 2 - 18, 35, 20, "20"));
            this.buttonList.add(this.fifty = new GuiButtonExt(6, this.width / 2 - 122, this.height / 2 + 1, 35, 20, "50"));
            this.buttonList.add(this.hundreed = new GuiButtonExt(7, this.width / 2 - 122, this.height / 2 + 20, 35, 20, "100"));
            this.buttonList.add(this.twoHundreed = new GuiButtonExt(8, this.width / 2 - 122, this.height / 2 + 39, 35, 20, "200"));
            this.buttonList.add(this.fiveHundreed = new GuiButtonExt(9, this.width / 2 - 122, this.height / 2 + 58, 35, 20, "500"));

            if (entityplayer.isCreative())
            {
                this.buttonList.add(this.unlimitedStack = new GuiButtonExt(9, this.width / 2 + 2, this.height / 2 - 96, 80, 13, I18n.format("title.unlimited", new Object[0])));
            }
        }
    }

    /**
     * Called by the controls from the buttonList when activated. (Mouse pressed for buttons)
     */
    protected void actionPerformed(GuiButton button) throws IOException
    {
        super.actionPerformed(button);
        World world = this.mc.world;
        EntityPlayer entityplayer = this.mc.player;

        if (this.tile != null)
        {
            if (button == this.unlimitedStack)
            {
                if (!this.admin)
                {
                    this.admin = true;
                    this.tile.setAdmin(Boolean.valueOf(true));
                    entityplayer.sendMessage(new TextComponentString(I18n.format("title.unlimitedStack", new Object[0])));
                }
                else if (this.admin)
                {
                    this.admin = false;
                    this.tile.setAdmin(Boolean.valueOf(false));
                    entityplayer.sendMessage(new TextComponentString(I18n.format("title.limitedStack", new Object[0])));
                }
            }

            if (button == this.one)
            {
                this.tile.setCost(1.0D);
                cost = 1.0D;
            }
            else if (button == this.five)
            {
                this.tile.setCost(5.0D);
                cost = 5.0D;
            }
            else if (button == this.ten)
            {
                this.tile.setCost(10.0D);
                cost = 10.0D;
            }
            else if (button == this.twenty)
            {
                this.tile.setCost(20.0D);
                cost = 20.0D;
            }
            else if (button == this.fifty)
            {
                this.tile.setCost(50.0D);
                cost = 50.0D;
            }
            else if (button == this.hundreed)
            {
                this.tile.setCost(100.0D);
                cost = 100.0D;
            }
            else if (button == this.twoHundreed)
            {
                this.tile.setCost(200.0D);
                cost = 200.0D;
            }
            else if (button == this.fiveHundreed)
            {
                this.tile.setCost(500.0D);
                cost = 500.0D;
            }
            else if (button == this.validate)
            {
                if (this.tile.getCost() != 0.0D)
                {
                    if (this.tile.getStackInSlot(0) != ItemStack.EMPTY)
                    {
                        if (!this.admin)
                        {
                            this.tile.setAdmin(Boolean.valueOf(false));
                            this.tile.setCreated(true);
                            int i = this.tile.getPos().getX();
                            int j = this.tile.getPos().getY();
                            int k = this.tile.getPos().getZ();
                            int l = this.tile.getStackInSlot(0).getCount();
                            String s = this.tile.getStackInSlot(0).getDisplayName();
                            this.tile.setAmount(l);
                            this.tile.setItem(s);
                            entityplayer.closeScreen();
                            PacketsRegistery.network.sendToServer(new PacketSellerCreated(true, cost, s, l, i, j, k, false));
                            this.tile.markDirty();
                        }
                        else if (this.admin)
                        {
                            this.tile.setAdmin(Boolean.valueOf(true));
                            this.tile.setCreated(true);
                            int i1 = this.tile.getPos().getX();
                            int j1 = this.tile.getPos().getY();
                            int k1 = this.tile.getPos().getZ();
                            int l1 = this.tile.getStackInSlot(0).getCount();
                            String s1 = this.tile.getStackInSlot(0).getDisplayName();
                            this.tile.setAmount(l1);
                            this.tile.setItem(s1);
                            entityplayer.closeScreen();
                            PacketsRegistery.network.sendToServer(new PacketSellerCreated(true, cost, s1, l1, i1, j1, k1, true));
                            this.tile.markDirty();
                        }
                    }
                    else
                    {
                        entityplayer.sendMessage(new TextComponentString(I18n.format("title.sellAir", new Object[0])));
                    }
                }
                else
                {
                    entityplayer.sendMessage(new TextComponentString(I18n.format("title.noCost", new Object[0])));
                }
            }
        }
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
        this.fontRenderer.drawString((new TextComponentTranslation(I18n.format("title.block_seller", new Object[0]), new Object[0])).getFormattedText(), 8, 5, Color.DARK_GRAY.getRGB());
        this.fontRenderer.drawString((new TextComponentTranslation("Inventory", new Object[0])).getFormattedText(), 8, 73, Color.DARK_GRAY.getRGB());
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
}
