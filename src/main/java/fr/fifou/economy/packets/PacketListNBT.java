package fr.fifou.economy.packets;

import fr.fifou.economy.blocks.tileentity.TileEntityBlockVault;
import fr.fifou.economy.blocks.tileentity.TileEntityBlockVault2by2;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketListNBT implements IMessage
{
    private static String names;
    private static int x;
    private static int y;
    private static int z;
    private static boolean isBlock2x2;
    private static String addrem;
    private static int index;

    public PacketListNBT()
    {
    }

    public PacketListNBT(String names, int x, int y, int z, boolean isBlock2x2, String addrem, int index)
    {
        names = names;
        x = x;
        y = y;
        z = z;
        isBlock2x2 = isBlock2x2;
        addrem = addrem;
        index = index;
    }

    public void fromBytes(ByteBuf buf)
    {
        names = ByteBufUtils.readUTF8String(buf);
        x = buf.readInt();
        y = buf.readInt();
        z = buf.readInt();
        isBlock2x2 = buf.readBoolean();
        addrem = ByteBufUtils.readUTF8String(buf);
        index = buf.readInt();
    }

    public void toBytes(ByteBuf buf)
    {
        ByteBufUtils.writeUTF8String(buf, names);
        buf.writeInt(x);
        buf.writeInt(y);
        buf.writeInt(z);
        buf.writeBoolean(isBlock2x2);
        ByteBufUtils.writeUTF8String(buf, addrem);
        buf.writeInt(index);
    }

    public static class Handler implements IMessageHandler<PacketListNBT, IMessage>
    {
        public IMessage onMessage(PacketListNBT message, MessageContext ctx)
        {
            EntityPlayer entityplayer = ctx.getServerHandler().player;
            World world = entityplayer.world;

            if (PacketListNBT.addrem.equals("add"))
            {
                if (PacketListNBT.isBlock2x2)
                {
                    TileEntityBlockVault2by2 tileentityblockvault2by2 = (TileEntityBlockVault2by2)world.getTileEntity(new BlockPos(PacketListNBT.x, PacketListNBT.y, PacketListNBT.z));

                    if (tileentityblockvault2by2 != null)
                    {
                        tileentityblockvault2by2.setOthers(PacketListNBT.names);
                        tileentityblockvault2by2.addToMax();
                        tileentityblockvault2by2.markDirty();
                    }
                }
                else
                {
                    TileEntityBlockVault tileentityblockvault = (TileEntityBlockVault)world.getTileEntity(new BlockPos(PacketListNBT.x, PacketListNBT.y, PacketListNBT.z));

                    if (tileentityblockvault != null)
                    {
                        tileentityblockvault.setOthers(PacketListNBT.names);
                        tileentityblockvault.addToMax();
                        tileentityblockvault.markDirty();
                    }
                }
            }
            else if (PacketListNBT.addrem.equals("remove"))
            {
                if (PacketListNBT.isBlock2x2)
                {
                    TileEntityBlockVault2by2 tileentityblockvault2by21 = (TileEntityBlockVault2by2)world.getTileEntity(new BlockPos(PacketListNBT.x, PacketListNBT.y, PacketListNBT.z));

                    if (tileentityblockvault2by21 != null)
                    {
                        tileentityblockvault2by21.getOthers().remove(PacketListNBT.index);
                        tileentityblockvault2by21.removeToMax();
                        tileentityblockvault2by21.markDirty();
                    }
                }
                else
                {
                    TileEntityBlockVault tileentityblockvault1 = (TileEntityBlockVault)world.getTileEntity(new BlockPos(PacketListNBT.x, PacketListNBT.y, PacketListNBT.z));

                    if (tileentityblockvault1 != null)
                    {
                        tileentityblockvault1.getOthers().remove(PacketListNBT.index);
                        tileentityblockvault1.removeToMax();
                        tileentityblockvault1.markDirty();
                    }
                }
            }

            return null;
        }
    }
}
