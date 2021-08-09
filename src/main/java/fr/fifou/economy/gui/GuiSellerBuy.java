package fr.fifou.economy.gui;

import fr.fifou.economy.blocks.tileentity.TileEntityBlockSeller;
import fr.fifou.economy.capability.CapabilityLoading;
import fr.fifou.economy.capability.IMoney;
import fr.fifou.economy.items.ItemCreditcard;
import fr.fifou.economy.packets.PacketCardChangeSeller;
import fr.fifou.economy.packets.PacketSellerFundsTotal;
import fr.fifou.economy.packets.PacketsRegistery;
import java.io.IOException;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.client.config.GuiButtonExt;
import org.lwjgl.opengl.GL11;

public class GuiSellerBuy extends GuiScreen
{
    private TileEntityBlockSeller tile;
    private static final ResourceLocation background = new ResourceLocation("economy", "textures/gui/screen/gui_item.png");
    protected int xSize = 256;
    protected int ySize = 124;
    protected int guiLeft;
    protected int guiTop;
    private GuiButtonExt slot1;
    private GuiButtonExt takeFunds;
    private String owner = "";
    private String itemName = "";
    private double cost;
    private int amount;
    private double fundsTotalRecovery;
    private int sizeInventoryCheckCard;

    public GuiSellerBuy(TileEntityBlockSeller tile)
    {
        this.tile = tile;
    }

    /**
     * Called from the main game loop to update the screen.
     */
    public void updateScreen()
    {
        super.updateScreen();
        this.amount = this.tile.getAmount();
        this.fundsTotalRecovery = this.tile.getFundsTotal();
        this.tile.setFundsTotal(this.fundsTotalRecovery);
        this.tile.setAmount(this.amount);
        this.tile.markDirty();
    }

    /**
     * Adds the buttons (and other controls) to the screen in question. Called when the GUI is displayed and when the
     * window resizes, the buttonList is cleared beforehand.
     */
    public void initGui()
    {
        this.guiLeft = (this.width - this.xSize) / 2;
        this.guiTop = (this.height - this.ySize) / 2;
        World world = this.mc.world;
        EntityPlayer entityplayer = this.mc.player;

        if (this.tile != null)
        {
            this.owner = this.tile.getOwnerName();
            this.itemName = this.tile.getItem();
            this.cost = this.tile.getCost();
            this.buttonList.add(this.slot1 = new GuiButtonExt(1, this.width / 2 - 50, this.height / 2 + 27, 100, 20, I18n.format("title.buy", new Object[0])));
            String s = this.tile.getOwner();
            String s1 = entityplayer.getUniqueID().toString();

            if (s.equals(s1))
            {
                this.buttonList.add(this.takeFunds = new GuiButtonExt(2, this.width / 2 + 20, this.height / 2 - 74, 100, 13, I18n.format("title.recover", new Object[0])));
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
     * Called by the controls from the buttonList when activated. (Mouse pressed for buttons)
     */
    protected void actionPerformed(GuiButton button) throws IOException
    {
        super.actionPerformed(button);
        World world = this.mc.world;
        EntityPlayer entityplayer = this.mc.player;

        if (this.tile != null)
        {
            if (button == this.slot1)
            {
                if (((IMoney)entityplayer.getCapability(CapabilityLoading.CAPABILITY_MONEY, (EnumFacing)null)).getLinked())
                {
                    if (((IMoney)entityplayer.getCapability(CapabilityLoading.CAPABILITY_MONEY, (EnumFacing)null)).getMoney() >= this.tile.getCost())
                    {
                        if (this.tile.getAmount() >= 1)
                        {
                            boolean flag = this.tile.getAdmin();

                            if (!flag)
                            {
                                double d0 = this.tile.getFundsTotal();
                                this.tile.setFundsTotal(d0 + this.tile.getCost());
                                int i = this.tile.getPos().getX();
                                int j = this.tile.getPos().getY();
                                int k = this.tile.getPos().getZ();
                                double d1 = this.tile.getCost();
                                int i1 = this.tile.getAmount();
                                this.tile.setAmount(i1 - 1);
                                PacketsRegistery.network.sendToServer(new PacketSellerFundsTotal(d0 + this.tile.getCost(), i, j, k, i1, false));
                                PacketsRegistery.network.sendToServer(new PacketCardChangeSeller(d1));
                                this.tile.markDirty();
                            }
                            else if (flag)
                            {
                                double d2 = this.tile.getFundsTotal();
                                this.tile.setFundsTotal(d2 + this.tile.getCost());
                                int k2 = this.tile.getPos().getX();
                                int l2 = this.tile.getPos().getY();
                                int i3 = this.tile.getPos().getZ();
                                double d5 = this.tile.getCost();
                                int k4 = this.tile.getAmount();
                                this.tile.setAmount(k4);
                                PacketsRegistery.network.sendToServer(new PacketSellerFundsTotal(d2 + this.tile.getCost(), k2, l2, i3, k4, false));
                                PacketsRegistery.network.sendToServer(new PacketCardChangeSeller(d5));
                                this.tile.markDirty();
                            }
                        }
                    }
                    else
                    {
                        entityplayer.sendMessage(new TextComponentString(I18n.format("title.noEnoughFunds", new Object[0])));
                    }
                }
                else
                {
                    for (int k1 = 0; k1 < entityplayer.inventory.getSizeInventory(); ++k1)
                    {
                        if (entityplayer.inventory.getStackInSlot(k1).getItem() instanceof ItemCreditcard)
                        {
                            ItemStack itemstack = entityplayer.inventory.getStackInSlot(k1);

                            if (itemstack.hasTagCompound())
                            {
                                if (entityplayer.getUniqueID().toString().equals(itemstack.getTagCompound().getString("OwnerUUID")))
                                {
                                    if (((IMoney)entityplayer.getCapability(CapabilityLoading.CAPABILITY_MONEY, (EnumFacing)null)).getMoney() >= this.tile.getCost())
                                    {
                                        if (this.tile.getAmount() >= 1)
                                        {
                                            boolean flag1 = this.tile.getAdmin();

                                            if (!flag1)
                                            {
                                                double d3 = this.tile.getFundsTotal();
                                                this.tile.setFundsTotal(d3 + this.tile.getCost());
                                                int j3 = this.tile.getPos().getX();
                                                int l3 = this.tile.getPos().getY();
                                                int l = this.tile.getPos().getZ();
                                                double d6 = this.tile.getCost();
                                                int j1 = this.tile.getAmount();
                                                this.tile.setAmount(j1 - 1);
                                                PacketsRegistery.network.sendToServer(new PacketSellerFundsTotal(d3 + this.tile.getCost(), j3, l3, l, j1, false));
                                                PacketsRegistery.network.sendToServer(new PacketCardChangeSeller(d6));
                                                this.tile.markDirty();
                                            }
                                            else if (flag1)
                                            {
                                                double d4 = this.tile.getFundsTotal();
                                                this.tile.setFundsTotal(d4 + this.tile.getCost());
                                                int k3 = this.tile.getPos().getX();
                                                int i4 = this.tile.getPos().getY();
                                                int j4 = this.tile.getPos().getZ();
                                                double d7 = this.tile.getCost();
                                                int l4 = this.tile.getAmount();
                                                this.tile.setAmount(l4);
                                                PacketsRegistery.network.sendToServer(new PacketSellerFundsTotal(d4 + this.tile.getCost(), k3, i4, j4, l4, false));
                                                PacketsRegistery.network.sendToServer(new PacketCardChangeSeller(d7));
                                                this.tile.markDirty();
                                            }
                                        }
                                    }
                                    else
                                    {
                                        entityplayer.sendMessage(new TextComponentString(I18n.format("title.noEnoughFunds", new Object[0])));
                                    }
                                }
                                else
                                {
                                    entityplayer.sendMessage(new TextComponentString(I18n.format("title.noSameOwner", new Object[0])));
                                }
                            }
                        }
                        else if (!(entityplayer.inventory.getStackInSlot(k1).getItem() instanceof ItemCreditcard))
                        {
                            ++this.sizeInventoryCheckCard;

                            if (this.sizeInventoryCheckCard == entityplayer.inventory.getSizeInventory())
                            {
                                if (this.tile.getAmount() != 0)
                                {
                                    entityplayer.sendMessage(new TextComponentString(I18n.format("title.noCardFoundAndNoLink", new Object[0])));
                                }
                                else if (this.tile.getAmount() == 0)
                                {
                                    entityplayer.sendMessage(new TextComponentString(I18n.format("title.noMoreQuantity", new Object[0])));
                                }

                                this.sizeInventoryCheckCard = 0;
                            }

                            if (k1 == entityplayer.inventory.getSizeInventory())
                            {
                                this.sizeInventoryCheckCard = 0;
                            }
                        }
                    }
                }
            }
            else if (button == this.takeFunds)
            {
                int l1 = this.tile.getPos().getX();
                int i2 = this.tile.getPos().getY();
                int j2 = this.tile.getPos().getZ();
                this.tile.setFundsTotal(0.0D);
                this.tile.markDirty();
                PacketsRegistery.network.sendToServer(new PacketSellerFundsTotal(this.fundsTotalRecovery, l1, i2, j2, this.amount, true));
            }
        }
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
        this.fontRenderer.drawString(TextFormatting.BOLD + I18n.format("title.seller", new Object[0]) + this.owner, this.width / 2 - 120, this.height / 2 - 50, 0);
        this.fontRenderer.drawString(TextFormatting.BOLD + I18n.format("title.item", new Object[0]) + this.itemName, this.width / 2 - 120, this.height / 2 - 40, 0);
        this.fontRenderer.drawString(TextFormatting.BOLD + I18n.format("title.cost", new Object[0]) + this.cost, this.width / 2 - 120, this.height / 2 - 30, 0);
        this.fontRenderer.drawString(TextFormatting.BOLD + I18n.format("title.amount", new Object[0]) + this.amount, this.width / 2 - 120, this.height / 2 - 20, 0);
        this.fontRenderer.drawString(TextFormatting.BOLD + I18n.format("title.fundsToRecover", new Object[0]) + this.fundsTotalRecovery, this.width / 2 - 120, this.height / 2 - 10, 0);
        super.drawScreen(mouseX, mouseY, partialTicks);
        this.drawImageInGui();
    }

    public void drawImageInGui()
    {
        int i = this.guiLeft;
        int j = this.guiTop;
        GL11.glPushMatrix();
        GlStateManager.enableRescaleNormal();
        RenderHelper.enableGUIStandardItemLighting();
        GL11.glScaled(2.0D, 2.0D, 2.0D);
        ItemStack itemstack = new ItemStack(Blocks.BARRIER, 1, 0);

        if (this.tile.getAmount() != 0)
        {
            itemstack = new ItemStack(this.tile.getStackInSlot(0).getItem(), 1, this.tile.getStackInSlot(0).getMetadata());
        }

        this.itemRender.renderItemIntoGUI(itemstack, i / 2 + 105, j / 2 + 5);
        RenderHelper.disableStandardItemLighting();
        GlStateManager.disableRescaleNormal();
        GL11.glPopMatrix();
    }
}
