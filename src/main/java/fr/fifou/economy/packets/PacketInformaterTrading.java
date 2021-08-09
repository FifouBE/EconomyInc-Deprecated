package fr.fifou.economy.packets;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketInformaterTrading implements IMessage
{
    public static int stackSlot;
    public static int code;

    public PacketInformaterTrading()
    {
    }

    public PacketInformaterTrading(int stackSlot, int safeCode)
    {
        stackSlot = stackSlot;
        code = safeCode;
    }

    public void fromBytes(ByteBuf buf)
    {
        stackSlot = buf.readInt();
        code = buf.readInt();
    }

    public void toBytes(ByteBuf buf)
    {
        buf.writeInt(stackSlot);
        buf.writeInt(code);
    }

    public static class Handler implements IMessageHandler<PacketInformaterTrading, IMessage>
    {
        public IMessage onMessage(PacketInformaterTrading message, MessageContext ctx)
        {
            EntityPlayer entityplayer = ctx.getServerHandler().player;
            World world = entityplayer.world;
            entityplayer.inventory.getStackInSlot(PacketInformaterTrading.stackSlot).setCount(entityplayer.inventory.getStackInSlot(PacketInformaterTrading.stackSlot).getCount() - 1);
            entityplayer.inventory.addItemStackToInventory((new ItemStack(Items.PAPER)).setStackDisplayName("Code : " + String.valueOf(PacketInformaterTrading.code)));
            return null;
        }
    }
}
