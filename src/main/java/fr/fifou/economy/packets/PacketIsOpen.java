package fr.fifou.economy.packets;

import fr.fifou.economy.blocks.tileentity.TileEntityBlockVault;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketIsOpen implements IMessage
{
    private static int x;
    private static int y;
    private static int z;
    private static boolean open;

    public PacketIsOpen()
    {
    }

    public PacketIsOpen(int x, int y, int z, boolean openIn)
    {
        x = x;
        y = y;
        z = z;
        open = openIn;
    }

    public void fromBytes(ByteBuf buf)
    {
        x = buf.readInt();
        y = buf.readInt();
        z = buf.readInt();
        open = buf.readBoolean();
    }

    public void toBytes(ByteBuf buf)
    {
        buf.writeInt(x);
        buf.writeInt(y);
        buf.writeInt(z);
        buf.writeBoolean(open);
    }

    public static class Handler implements IMessageHandler<PacketIsOpen, IMessage>
    {
        public IMessage onMessage(PacketIsOpen message, MessageContext ctx)
        {
            EntityPlayer entityplayer = ctx.getServerHandler().player;
            TileEntityBlockVault tileentityblockvault = (TileEntityBlockVault)entityplayer.world.getTileEntity(new BlockPos(PacketIsOpen.x, PacketIsOpen.y, PacketIsOpen.z));

            if (tileentityblockvault != null)
            {
                tileentityblockvault.setIsOpen(false);
                tileentityblockvault.markDirty();
            }

            return null;
        }
    }
}
