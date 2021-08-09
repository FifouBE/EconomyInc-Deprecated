package fr.fifou.economy.packets;

import fr.fifou.economy.ModEconomy;
import fr.fifou.economy.blocks.tileentity.TileEntityBlockVaultCracked;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketOpenCracked implements IMessage
{
    private static int x;
    private static int y;
    private static int z;
    private static boolean cracked;

    public PacketOpenCracked()
    {
    }

    public PacketOpenCracked(int x, int y, int z, boolean cracked)
    {
        x = x;
        y = y;
        z = z;
        cracked = cracked;
    }

    public void fromBytes(ByteBuf buf)
    {
        x = buf.readInt();
        y = buf.readInt();
        z = buf.readInt();
        cracked = buf.readBoolean();
    }

    public void toBytes(ByteBuf buf)
    {
        buf.writeInt(x);
        buf.writeInt(y);
        buf.writeInt(z);
        buf.writeBoolean(cracked);
    }

    public static class Handler implements IMessageHandler<PacketOpenCracked, IMessage>
    {
        public IMessage onMessage(PacketOpenCracked message, MessageContext ctx)
        {
            EntityPlayer entityplayer = ctx.getServerHandler().player;
            TileEntityBlockVaultCracked tileentityblockvaultcracked = (TileEntityBlockVaultCracked)entityplayer.world.getTileEntity(new BlockPos(PacketOpenCracked.x, PacketOpenCracked.y, PacketOpenCracked.z));

            if (tileentityblockvaultcracked != null)
            {
                if (PacketOpenCracked.cracked)
                {
                    entityplayer.openGui(ModEconomy.instance, 9, entityplayer.world, PacketOpenCracked.x, PacketOpenCracked.y, PacketOpenCracked.z);
                    tileentityblockvaultcracked.setCracked(true);
                    tileentityblockvaultcracked.markDirty();
                }
                else
                {
                    entityplayer.openGui(ModEconomy.instance, 10, entityplayer.world, PacketOpenCracked.x, PacketOpenCracked.y, PacketOpenCracked.z);
                }
            }

            return null;
        }
    }
}
