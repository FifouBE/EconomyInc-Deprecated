package fr.fifou.economy.gui;

import fr.fifou.economy.capability.CapabilityLoading;
import fr.fifou.economy.capability.IMoney;
import fr.fifou.economy.packets.PacketCardChange;
import fr.fifou.economy.packets.PacketsRegistery;
import java.awt.Color;
import java.io.IOException;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.client.config.GuiButtonExt;

public class GuiItem extends GuiScreen
{
    private static final ResourceLocation background = new ResourceLocation("economy", "textures/gui/screen/gui_item.png");
    private GuiButtonExt oneB;
    private GuiButtonExt fiveB;
    private GuiButtonExt TenB;
    private GuiButtonExt TwentyB;
    private GuiButtonExt FiftyB;
    private GuiButtonExt HundreedB;
    private GuiButtonExt TwoHundreedB;
    private GuiButtonExt FiveHundreedB;
    private GuiButtonExt oneBMinus;
    private GuiButtonExt fiveBMinus;
    private GuiButtonExt TenBMinus;
    private GuiButtonExt TwentyBMinus;
    private GuiButtonExt FiftyBMinus;
    private GuiButtonExt HundreedBMinus;
    private GuiButtonExt TwoHundreedBMinus;
    private GuiButtonExt FiveHundreedBMinus;
    private GuiButtonExt upgrade;
    private double funds_s;
    private String owner;
    protected int xSize;
    protected int ySize;
    protected int guiLeft;
    protected int guiTop;

    public GuiItem()
    {
        this.owner = Minecraft.getMinecraft().player.getName();
        this.xSize = 256;
        this.ySize = 124;
    }

    /**
     * Adds the buttons (and other controls) to the screen in question. Called when the GUI is displayed and when the
     * window resizes, the buttonList is cleared beforehand.
     */
    public void initGui()
    {
        this.guiLeft = (this.width - this.xSize) / 2;
        this.guiTop = (this.height - this.ySize) / 2;
        GlStateManager.pushMatrix();
        GlStateManager.translate((float)(this.width / 2), (float)(this.height / 2), 0.0F);
        GlStateManager.rotate(180.0F, 1.0F, 0.0F, 0.0F);
        GlStateManager.popMatrix();
        this.buttonList.add(this.oneB = new GuiButtonExt(0, this.width / 2 + 90, this.height / 2 - 55, 30, 20, TextFormatting.GREEN + "+1"));
        this.buttonList.add(this.fiveB = new GuiButtonExt(1, this.width / 2 - 120, this.height / 2, 30, 20, TextFormatting.GREEN + "+5"));
        this.buttonList.add(this.TenB = new GuiButtonExt(2, this.width / 2 - 85, this.height / 2, 30, 20, TextFormatting.GREEN + "+10"));
        this.buttonList.add(this.TwentyB = new GuiButtonExt(3, this.width / 2 - 50, this.height / 2, 30, 20, TextFormatting.GREEN + "+20"));
        this.buttonList.add(this.FiftyB = new GuiButtonExt(4, this.width / 2 - 15, this.height / 2, 30, 20, TextFormatting.GREEN + "+50"));
        this.buttonList.add(this.HundreedB = new GuiButtonExt(5, this.width / 2 + 20, this.height / 2, 30, 20, TextFormatting.GREEN + "+100"));
        this.buttonList.add(this.TwoHundreedB = new GuiButtonExt(6, this.width / 2 + 55, this.height / 2, 30, 20, TextFormatting.GREEN + "+200"));
        this.buttonList.add(this.FiveHundreedB = new GuiButtonExt(7, this.width / 2 + 90, this.height / 2, 30, 20, TextFormatting.GREEN + "+500"));
        this.buttonList.add(this.oneBMinus = new GuiButtonExt(8, this.width / 2 + 90, this.height / 2 - 25, 30, 20, TextFormatting.RED + "-1"));
        this.buttonList.add(this.fiveBMinus = new GuiButtonExt(9, this.width / 2 - 120, this.height / 2 + 30, 30, 20, TextFormatting.RED + "-5"));
        this.buttonList.add(this.TenBMinus = new GuiButtonExt(10, this.width / 2 - 85, this.height / 2 + 30, 30, 20, TextFormatting.RED + "-10"));
        this.buttonList.add(this.TwentyBMinus = new GuiButtonExt(11, this.width / 2 - 50, this.height / 2 + 30, 30, 20, TextFormatting.RED + "-20"));
        this.buttonList.add(this.FiftyBMinus = new GuiButtonExt(12, this.width / 2 - 15, this.height / 2 + 30, 30, 20, TextFormatting.RED + "-50"));
        this.buttonList.add(this.HundreedBMinus = new GuiButtonExt(13, this.width / 2 + 20, this.height / 2 + 30, 30, 20, TextFormatting.RED + "-100"));
        this.buttonList.add(this.TwoHundreedBMinus = new GuiButtonExt(14, this.width / 2 + 55, this.height / 2 + 30, 30, 20, TextFormatting.RED + "-200"));
        this.buttonList.add(this.FiveHundreedBMinus = new GuiButtonExt(15, this.width / 2 + 90, this.height / 2 + 30, 30, 20, TextFormatting.RED + "-500"));
    }

    /**
     * Called from the main game loop to update the screen.
     */
    public void updateScreen()
    {
        super.updateScreen();
        EntityPlayer entityplayer = this.mc.player;
        ItemStack itemstack = this.mc.player.getHeldItemMainhand();
        this.funds_s = ((IMoney)entityplayer.getCapability(CapabilityLoading.CAPABILITY_MONEY, (EnumFacing)null)).getMoney();
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
        EntityPlayer entityplayer = this.mc.player;
        World world = entityplayer.world;

        if (button == this.oneB)
        {
            PacketsRegistery.network.sendToServer(new PacketCardChange(-1.0D));
        }

        if (button == this.fiveB)
        {
            PacketsRegistery.network.sendToServer(new PacketCardChange(-5.0D));
        }

        if (button == this.TenB)
        {
            PacketsRegistery.network.sendToServer(new PacketCardChange(-10.0D));
        }

        if (button == this.TwentyB)
        {
            PacketsRegistery.network.sendToServer(new PacketCardChange(-20.0D));
        }

        if (button == this.FiftyB)
        {
            PacketsRegistery.network.sendToServer(new PacketCardChange(-50.0D));
        }

        if (button == this.HundreedB)
        {
            PacketsRegistery.network.sendToServer(new PacketCardChange(-100.0D));
        }

        if (button == this.TwoHundreedB)
        {
            PacketsRegistery.network.sendToServer(new PacketCardChange(-200.0D));
        }

        if (button == this.FiveHundreedB)
        {
            PacketsRegistery.network.sendToServer(new PacketCardChange(-500.0D));
        }

        if (button == this.oneBMinus)
        {
            PacketsRegistery.network.sendToServer(new PacketCardChange(1.0D));
        }

        if (button == this.fiveBMinus)
        {
            PacketsRegistery.network.sendToServer(new PacketCardChange(5.0D));
        }

        if (button == this.TenBMinus)
        {
            PacketsRegistery.network.sendToServer(new PacketCardChange(10.0D));
        }

        if (button == this.TwentyBMinus)
        {
            PacketsRegistery.network.sendToServer(new PacketCardChange(20.0D));
        }

        if (button == this.FiftyBMinus)
        {
            PacketsRegistery.network.sendToServer(new PacketCardChange(50.0D));
        }

        if (button == this.HundreedBMinus)
        {
            PacketsRegistery.network.sendToServer(new PacketCardChange(100.0D));
        }

        if (button == this.TwoHundreedBMinus)
        {
            PacketsRegistery.network.sendToServer(new PacketCardChange(200.0D));
        }

        if (button == this.FiveHundreedBMinus)
        {
            PacketsRegistery.network.sendToServer(new PacketCardChange(500.0D));
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
        drawEntityOnScreen(i + 51, j + 75, 30.0F, (float)(i + 51) - (float)mouseX, (float)(j + 75 - 50) - (float)mouseY, this.mc.player);
        this.fontRenderer.drawString((new TextComponentTranslation(I18n.format("title.ownerCard", new Object[0]) + ": " + this.owner, new Object[0])).getFormattedText(), this.width / 2 - 75, this.height / 2 - 55, Color.DARK_GRAY.getRGB());
        this.fontRenderer.drawString((new TextComponentTranslation(I18n.format("title.fundsCard", new Object[0]) + ": " + this.funds_s, new Object[0])).getFormattedText(), this.width / 2 - 75, this.height / 2 - 45, Color.DARK_GRAY.getRGB());
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    public static void drawEntityOnScreen(int posX, int posY, float scale, float mouseX, float mouseY, EntityLivingBase ent)
    {
        GlStateManager.enableColorMaterial();
        GlStateManager.pushMatrix();
        GlStateManager.translate((double)((float)posX - 25.0F), (double)((float)posY) - 22.5D, 50.0D);
        GlStateManager.scale(-scale, scale, scale);
        GlStateManager.scale(0.75F, 0.75F, 0.75F);
        GlStateManager.rotate(180.0F, 0.0F, 0.0F, 1.0F);
        float f = ent.renderYawOffset;
        float f1 = ent.rotationYaw;
        float f2 = ent.rotationPitch;
        float f3 = ent.prevRotationYawHead;
        float f4 = ent.rotationYawHead;
        GlStateManager.rotate(0.0F, 0.0F, 1.0F, 0.0F);
        RenderHelper.enableStandardItemLighting();
        GlStateManager.rotate(0.0F, 0.0F, 1.0F, 0.0F);
        float f5 = -25.0F;
        ent.renderYawOffset = f5;
        ent.rotationYaw = f5;
        ent.rotationPitch = 0.0F;
        ent.rotationYawHead = 0.0F;
        ent.prevRotationYawHead = 0.0F;
        GlStateManager.translate(0.0F, 0.0F, 0.0F);
        RenderManager rendermanager = Minecraft.getMinecraft().getRenderManager();
        rendermanager.setPlayerViewY(180.0F);
        rendermanager.setRenderShadow(false);
        rendermanager.renderEntity(ent, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F, false);
        rendermanager.setRenderShadow(true);
        ent.renderYawOffset = f;
        ent.rotationYaw = f1;
        ent.rotationPitch = f2;
        ent.prevRotationYawHead = f3;
        ent.rotationYawHead = f4;
        GlStateManager.popMatrix();
        RenderHelper.disableStandardItemLighting();
        GlStateManager.disableRescaleNormal();
        GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
        GlStateManager.disableTexture2D();
        GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
    }
}
