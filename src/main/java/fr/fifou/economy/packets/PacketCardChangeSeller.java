package fr.fifou.economy.packets;

import fr.fifou.economy.capability.CapabilityLoading;
import fr.fifou.economy.capability.IMoney;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketCardChangeSeller implements IMessage
{
    private static double cost;

    public PacketCardChangeSeller()
    {
    }

    public PacketCardChangeSeller(double cost)
    {
        cost = cost;
    }

    public void fromBytes(ByteBuf buf)
    {
        cost = buf.readDouble();
    }

    public void toBytes(ByteBuf buf)
    {
        buf.writeDouble(cost);
    }

    public static class Handler implements IMessageHandler<PacketCardChangeSeller, IMessage>
    {
        public IMessage onMessage(PacketCardChangeSeller message, MessageContext ctx)
        {
            EntityPlayer entityplayer = ctx.getServerHandler().player;
            World world = entityplayer.world;
            ((IMoney)entityplayer.getCapability(CapabilityLoading.CAPABILITY_MONEY, (EnumFacing)null)).setMoney(((IMoney)entityplayer.getCapability(CapabilityLoading.CAPABILITY_MONEY, (EnumFacing)null)).getMoney() - PacketCardChangeSeller.cost);
            ((IMoney)entityplayer.getCapability(CapabilityLoading.CAPABILITY_MONEY, (EnumFacing)null)).sync(entityplayer);
            return null;
        }
    }
}
