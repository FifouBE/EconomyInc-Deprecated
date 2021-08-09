package fr.fifou.economy.packets;

import fr.fifou.economy.capability.CapabilityLoading;
import fr.fifou.economy.capability.IMoney;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IThreadListener;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketMoneyData implements IMessage
{
    private double money;
    private boolean linked;
    private String name;
    private String onlineUUID;

    public PacketMoneyData()
    {
    }

    public PacketMoneyData(double money, boolean linked, String name, String OnUUID)
    {
        this.money = money;
        this.linked = linked;
        this.name = name;
        this.onlineUUID = OnUUID;
    }

    public void fromBytes(ByteBuf buf)
    {
        this.money = buf.readDouble();
        this.linked = buf.readBoolean();
        this.name = ByteBufUtils.readUTF8String(buf);
        this.onlineUUID = ByteBufUtils.readUTF8String(buf);
    }

    public void toBytes(ByteBuf buf)
    {
        buf.writeDouble(this.money);
        buf.writeBoolean(this.linked);
        ByteBufUtils.writeUTF8String(buf, this.name);
        ByteBufUtils.writeUTF8String(buf, this.onlineUUID);
    }

    public static class PacketMoneyHandlerClient implements IMessageHandler<PacketMoneyData, IMessage>
    {
        public IMessage onMessage(PacketMoneyData message, MessageContext ctx)
        {
            IThreadListener ithreadlistener = Minecraft.getMinecraft();
            EntityPlayer entityplayer = Minecraft.getMinecraft().player;
            ithreadlistener.addScheduledTask(() ->
            {
                if (entityplayer != null)
                {
                    IMoney imoney = (IMoney)entityplayer.getCapability(CapabilityLoading.CAPABILITY_MONEY, (EnumFacing)null);

                    if (imoney != null)
                    {
                        imoney.setMoney(message.money);
                        imoney.setLinked(message.linked);
                        imoney.setName(message.name);
                        imoney.setOnlineUUID(message.onlineUUID);
                    }
                }
            });
            return null;
        }
    }

    public static class PacketMoneyHandlerServer implements IMessageHandler<PacketMoneyData, IMessage>
    {
        public IMessage onMessage(PacketMoneyData message, MessageContext ctx)
        {
            IThreadListener ithreadlistener = ctx.getServerHandler().player.getServerWorld();
            EntityPlayer entityplayer = ctx.getServerHandler().player;
            ithreadlistener.addScheduledTask(() ->
            {
                if (entityplayer != null)
                {
                    IMoney imoney = (IMoney)entityplayer.getCapability(CapabilityLoading.CAPABILITY_MONEY, (EnumFacing)null);

                    if (imoney != null)
                    {
                        imoney.setMoney(message.money);
                        imoney.setLinked(message.linked);
                        imoney.setName(message.name);
                        imoney.setOnlineUUID(message.onlineUUID);
                    }
                }
            });
            return null;
        }
    }
}
