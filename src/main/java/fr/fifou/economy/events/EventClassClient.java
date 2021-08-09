package fr.fifou.economy.events;

import fr.fifou.economy.ConfigFile;
import fr.fifou.economy.blocks.BlocksRegistery;
import fr.fifou.economy.blocks.tileentity.TileEntityBlockSeller;
import fr.fifou.economy.capability.CapabilityLoading;
import fr.fifou.economy.capability.IMoney;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.RayTraceResult.Type;
import net.minecraft.world.World;
import net.minecraftforge.client.event.DrawBlockHighlightEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

public class EventClassClient
{
    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void onDrawBlockHighlightEvent(DrawBlockHighlightEvent event)
    {
        if (ConfigFile.canPreviewItemInBlock)
        {
            EntityPlayer entityplayer = event.getPlayer();
            World world = entityplayer.world;
            Type type = event.getTarget().typeOfHit;
            Type type1 = event.getTarget().typeOfHit;

            if (type == Type.ENTITY)
            {
                return;
            }

            if (world != null && world.getBlockState(event.getTarget().getBlockPos()).getBlock() == BlocksRegistery.BLOCK_SELLER)
            {
                TileEntityBlockSeller tileentityblockseller = (TileEntityBlockSeller)world.getTileEntity(event.getTarget().getBlockPos());

                if (tileentityblockseller != null && tileentityblockseller.getCreated())
                {
                    int i = event.getTarget().getBlockPos().getX();
                    int j = event.getTarget().getBlockPos().getY();
                    int k = event.getTarget().getBlockPos().getZ();
                    float f = 0.0F;
                    float f1 = 0.0F;
                    RenderManager rendermanager = Minecraft.getMinecraft().getRenderManager();
                    GL11.glPushMatrix();
                    GlStateManager.enableRescaleNormal();
                    RenderHelper.enableStandardItemLighting();

                    if (tileentityblockseller.getStackInSlot(0).getUnlocalizedName().substring(0, 4).equals("tile"))
                    {
                        f = 0.1F;
                    }

                    if (tileentityblockseller.getFacing().substring(0, 4).equals("west"))
                    {
                        f1 = 94.0F;
                    }
                    else if (tileentityblockseller.getFacing().substring(0, 4).equals("east"))
                    {
                        f1 = 31.5F;
                    }
                    else if (tileentityblockseller.getFacing().equals("north"))
                    {
                        f1 = 188.0F;
                    }

                    ItemStack itemstack = new ItemStack(tileentityblockseller.getStackInSlot(0).getItem(), 1, tileentityblockseller.getStackInSlot(0).getMetadata());

                    if (tileentityblockseller.getAmount() == 0)
                    {
                        itemstack = new ItemStack(Blocks.BARRIER, 1, 0);
                    }

                    EntityItem entityitem = new EntityItem(world, (double)i + 0.5D, (double)((float)j + f), (double)k + 0.5D, itemstack);
                    entityitem.hoverStart = 0.0F;
                    rendermanager.renderEntityStatic(entityitem, 1.0F * f1, false);
                    RenderHelper.disableStandardItemLighting();
                    GlStateManager.disableRescaleNormal();
                    GL11.glPopMatrix();
                }
            }
        }
    }

    @SubscribeEvent
    public void onplayerLoggedInEvent(PlayerLoggedInEvent event)
    {
        ((IMoney)event.player.getCapability(CapabilityLoading.CAPABILITY_MONEY, (EnumFacing)null)).sync((EntityPlayerMP)event.player);
    }
}
