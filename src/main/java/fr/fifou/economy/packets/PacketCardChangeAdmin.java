package fr.fifou.economy.packets;

import fr.fifou.economy.capability.CapabilityLoading;
import fr.fifou.economy.capability.IMoney;
import fr.fifou.economy.items.ItemCreditcard;
import io.netty.buffer.ByteBuf;
import java.util.UUID;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketCardChangeAdmin implements IMessage
{
    private static String uuid;
    private static double funds;
    private static String type;

    public PacketCardChangeAdmin()
    {
    }

    public PacketCardChangeAdmin(double funds, String uuid, String type)
    {
        funds = funds;
        uuid = uuid;
        type = type;
    }

    public void fromBytes(ByteBuf buf)
    {
        funds = buf.readDouble();
        uuid = ByteBufUtils.readUTF8String(buf);
        type = ByteBufUtils.readUTF8String(buf);
    }

    public void toBytes(ByteBuf buf)
    {
        buf.writeDouble(funds);
        ByteBufUtils.writeUTF8String(buf, uuid);
        ByteBufUtils.writeUTF8String(buf, type);
    }

    public static class Handler implements IMessageHandler<PacketCardChangeAdmin, IMessage>
    {
        public IMessage onMessage(PacketCardChangeAdmin message, MessageContext ctx)
        {
            EntityPlayer entityplayer = ctx.getServerHandler().player;
            World world = entityplayer.world;
            UUID uuid = UUID.fromString(PacketCardChangeAdmin.uuid);
            EntityPlayer entityplayer1 = world.getPlayerEntityByUUID(uuid);

            if (PacketCardChangeAdmin.type.equals("add"))
            {
                for (int i = 0; i < entityplayer1.inventory.getSizeInventory(); ++i)
                {
                    if (entityplayer1.inventory.getStackInSlot(i) != null && entityplayer1.inventory.getStackInSlot(i).getItem() instanceof ItemCreditcard)
                    {
                        ItemStack itemstack = entityplayer1.inventory.getStackInSlot(i);

                        if (itemstack.hasTagCompound() && itemstack.getTagCompound().hasKey("Owner"))
                        {
                            String s = itemstack.getTagCompound().getString("OwnerUUID");

                            if (s != null && s.equals(entityplayer.getUniqueID().toString()))
                            {
                                IMoney imoney = CapabilityLoading.getMoneyHandler(entityplayer1);
                                double d0 = itemstack.getTagCompound().getDouble("Funds");
                                double d1 = d0 + PacketCardChangeAdmin.funds;
                                itemstack.getTagCompound().setDouble("Funds", d1);
                            }
                        }
                    }
                }
            }
            else if (PacketCardChangeAdmin.type.equals("remove"))
            {
                for (int j = 0; j < entityplayer1.inventory.getSizeInventory(); ++j)
                {
                    if (entityplayer1.inventory.getStackInSlot(j) != null && entityplayer1.inventory.getStackInSlot(j).getItem() instanceof ItemCreditcard)
                    {
                        ItemStack itemstack1 = entityplayer1.inventory.getStackInSlot(j);

                        if (itemstack1.hasTagCompound() && itemstack1.getTagCompound().hasKey("Owner"))
                        {
                            String s1 = itemstack1.getTagCompound().getString("OwnerUUID");

                            if (s1 != null && s1.equals(entityplayer.getUniqueID().toString()))
                            {
                                IMoney imoney1 = CapabilityLoading.getMoneyHandler(entityplayer1);
                                double d2 = itemstack1.getTagCompound().getDouble("Funds");
                                double d3 = d2 - PacketCardChangeAdmin.funds;
                                itemstack1.getTagCompound().setDouble("Funds", d3);
                            }
                        }
                    }
                }
            }

            return null;
        }
    }
}
