package fr.fifou.economy.packets;

import fr.fifou.economy.capability.CapabilityLoading;
import fr.fifou.economy.capability.IMoney;
import fr.fifou.economy.items.ItemsRegistery;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketCardChange implements IMessage
{
    private static double funds;

    public PacketCardChange()
    {
    }

    public PacketCardChange(double funds)
    {
        funds = funds;
    }

    public void fromBytes(ByteBuf buf)
    {
        funds = buf.readDouble();
    }

    public void toBytes(ByteBuf buf)
    {
        buf.writeDouble(funds);
    }

    public static class Handler implements IMessageHandler<PacketCardChange, IMessage>
    {
        public IMessage onMessage(PacketCardChange message, MessageContext ctx)
        {
            EntityPlayer entityplayer = ctx.getServerHandler().player;
            ItemStack itemstack = entityplayer.getHeldItemMainhand();
            IMoney imoney = CapabilityLoading.getMoneyHandler(entityplayer);

            if (PacketCardChange.funds == 1.0D)
            {
                if (((IMoney)entityplayer.getCapability(CapabilityLoading.CAPABILITY_MONEY, (EnumFacing)null)).getMoney() >= 1.0D)
                {
                    double d0 = imoney.getMoney();
                    imoney.setMoney(d0 - 1.0D);
                    entityplayer.inventory.addItemStackToInventory(new ItemStack(ItemsRegistery.ITEM_ONEB));
                    imoney.sync((EntityPlayerMP)entityplayer);
                }
            }
            else if (PacketCardChange.funds == 5.0D)
            {
                if (((IMoney)entityplayer.getCapability(CapabilityLoading.CAPABILITY_MONEY, (EnumFacing)null)).getMoney() >= 5.0D)
                {
                    double d1 = imoney.getMoney();
                    imoney.setMoney(d1 - 5.0D);
                    entityplayer.inventory.addItemStackToInventory(new ItemStack(ItemsRegistery.ITEM_FIVEB));
                    imoney.sync((EntityPlayerMP)entityplayer);
                }
            }
            else if (PacketCardChange.funds == 10.0D)
            {
                if (((IMoney)entityplayer.getCapability(CapabilityLoading.CAPABILITY_MONEY, (EnumFacing)null)).getMoney() >= 10.0D)
                {
                    double d2 = imoney.getMoney();
                    imoney.setMoney(d2 - 10.0D);
                    entityplayer.inventory.addItemStackToInventory(new ItemStack(ItemsRegistery.ITEM_TENB));
                    imoney.sync((EntityPlayerMP)entityplayer);
                }
            }
            else if (PacketCardChange.funds == 20.0D)
            {
                if (((IMoney)entityplayer.getCapability(CapabilityLoading.CAPABILITY_MONEY, (EnumFacing)null)).getMoney() >= 20.0D)
                {
                    double d3 = imoney.getMoney();
                    imoney.setMoney(d3 - 20.0D);
                    entityplayer.inventory.addItemStackToInventory(new ItemStack(ItemsRegistery.ITEM_TWENTYB));
                    imoney.sync((EntityPlayerMP)entityplayer);
                }
            }
            else if (PacketCardChange.funds == 50.0D)
            {
                if (((IMoney)entityplayer.getCapability(CapabilityLoading.CAPABILITY_MONEY, (EnumFacing)null)).getMoney() >= 50.0D)
                {
                    double d4 = imoney.getMoney();
                    imoney.setMoney(d4 - 50.0D);
                    entityplayer.inventory.addItemStackToInventory(new ItemStack(ItemsRegistery.ITEM_FIFTYB));
                    imoney.sync((EntityPlayerMP)entityplayer);
                }
            }
            else if (PacketCardChange.funds == 100.0D)
            {
                if (((IMoney)entityplayer.getCapability(CapabilityLoading.CAPABILITY_MONEY, (EnumFacing)null)).getMoney() >= 100.0D)
                {
                    double d5 = imoney.getMoney();
                    imoney.setMoney(d5 - 100.0D);
                    entityplayer.inventory.addItemStackToInventory(new ItemStack(ItemsRegistery.ITEM_HUNDREEDB));
                    imoney.sync((EntityPlayerMP)entityplayer);
                }
            }
            else if (PacketCardChange.funds == 200.0D)
            {
                if (((IMoney)entityplayer.getCapability(CapabilityLoading.CAPABILITY_MONEY, (EnumFacing)null)).getMoney() >= 200.0D)
                {
                    double d6 = imoney.getMoney();
                    imoney.setMoney(d6 - 200.0D);
                    entityplayer.inventory.addItemStackToInventory(new ItemStack(ItemsRegistery.ITEM_TWOHUNDREEDB));
                    imoney.sync((EntityPlayerMP)entityplayer);
                }
            }
            else if (PacketCardChange.funds == 500.0D)
            {
                if (((IMoney)entityplayer.getCapability(CapabilityLoading.CAPABILITY_MONEY, (EnumFacing)null)).getMoney() >= 500.0D)
                {
                    double d7 = imoney.getMoney();
                    imoney.setMoney(d7 - 500.0D);
                    entityplayer.inventory.addItemStackToInventory(new ItemStack(ItemsRegistery.ITEM_FIVEHUNDREEDB));
                    imoney.sync((EntityPlayerMP)entityplayer);
                }
            }
            else if (PacketCardChange.funds == -1.0D)
            {
                if (entityplayer.inventory.hasItemStack(new ItemStack(ItemsRegistery.ITEM_ONEB)))
                {
                    double d8 = imoney.getMoney();
                    imoney.setMoney(d8 + 1.0D);
                    entityplayer.inventory.clearMatchingItems(ItemsRegistery.ITEM_ONEB, 0, 1, (NBTTagCompound)null);
                    imoney.sync((EntityPlayerMP)entityplayer);
                }
            }
            else if (PacketCardChange.funds == -5.0D)
            {
                if (entityplayer.inventory.hasItemStack(new ItemStack(ItemsRegistery.ITEM_FIVEB)))
                {
                    double d9 = imoney.getMoney();
                    imoney.setMoney(d9 + 5.0D);
                    entityplayer.inventory.clearMatchingItems(ItemsRegistery.ITEM_FIVEB, 0, 1, (NBTTagCompound)null);
                    imoney.sync((EntityPlayerMP)entityplayer);
                }
            }
            else if (PacketCardChange.funds == -10.0D)
            {
                if (entityplayer.inventory.hasItemStack(new ItemStack(ItemsRegistery.ITEM_TENB)))
                {
                    double d10 = imoney.getMoney();
                    imoney.setMoney(d10 + 10.0D);
                    entityplayer.inventory.clearMatchingItems(ItemsRegistery.ITEM_TENB, 0, 1, (NBTTagCompound)null);
                    ((IMoney)entityplayer.getCapability(CapabilityLoading.CAPABILITY_MONEY, (EnumFacing)null)).sync((EntityPlayerMP)entityplayer);
                    imoney.sync((EntityPlayerMP)entityplayer);
                }
            }
            else if (PacketCardChange.funds == -20.0D)
            {
                if (entityplayer.inventory.hasItemStack(new ItemStack(ItemsRegistery.ITEM_TWENTYB)))
                {
                    double d11 = imoney.getMoney();
                    imoney.setMoney(d11 + 20.0D);
                    entityplayer.inventory.clearMatchingItems(ItemsRegistery.ITEM_TWENTYB, 0, 1, (NBTTagCompound)null);
                    imoney.sync((EntityPlayerMP)entityplayer);
                }
            }
            else if (PacketCardChange.funds == -50.0D)
            {
                if (entityplayer.inventory.hasItemStack(new ItemStack(ItemsRegistery.ITEM_FIFTYB)))
                {
                    double d12 = imoney.getMoney();
                    imoney.setMoney(d12 + 50.0D);
                    entityplayer.inventory.clearMatchingItems(ItemsRegistery.ITEM_FIFTYB, 0, 1, (NBTTagCompound)null);
                    imoney.sync((EntityPlayerMP)entityplayer);
                }
            }
            else if (PacketCardChange.funds == -100.0D)
            {
                if (entityplayer.inventory.hasItemStack(new ItemStack(ItemsRegistery.ITEM_HUNDREEDB)))
                {
                    double d13 = imoney.getMoney();
                    imoney.setMoney(d13 + 100.0D);
                    entityplayer.inventory.clearMatchingItems(ItemsRegistery.ITEM_HUNDREEDB, 0, 1, (NBTTagCompound)null);
                    imoney.sync((EntityPlayerMP)entityplayer);
                }
            }
            else if (PacketCardChange.funds == -200.0D)
            {
                if (entityplayer.inventory.hasItemStack(new ItemStack(ItemsRegistery.ITEM_TWOHUNDREEDB)))
                {
                    double d14 = imoney.getMoney();
                    imoney.setMoney(d14 + 200.0D);
                    entityplayer.inventory.clearMatchingItems(ItemsRegistery.ITEM_TWOHUNDREEDB, 0, 1, (NBTTagCompound)null);
                    imoney.sync((EntityPlayerMP)entityplayer);
                }
            }
            else if (PacketCardChange.funds == -500.0D && entityplayer.inventory.hasItemStack(new ItemStack(ItemsRegistery.ITEM_FIVEHUNDREEDB)))
            {
                double d15 = imoney.getMoney();
                imoney.setMoney(d15 + 500.0D);
                entityplayer.inventory.clearMatchingItems(ItemsRegistery.ITEM_FIVEHUNDREEDB, 0, 1, (NBTTagCompound)null);
                imoney.sync((EntityPlayerMP)entityplayer);
            }

            return null;
        }
    }
}
