package fr.fifou.economy.packets;

import fr.fifou.economy.blocks.tileentity.TileEntityBlockSeller;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketSellerCreated implements IMessage
{
    private static boolean created;
    private double cost;
    private int x;
    private int y;
    private int z;
    private String name = "";
    private int amount = 0;
    private boolean admin;

    public PacketSellerCreated()
    {
    }

    public PacketSellerCreated(boolean createdS, double costS, String nameS, int amountS, int xS, int yS, int zS, boolean adminS)
    {
        created = createdS;
        this.x = xS;
        this.y = yS;
        this.z = zS;
        this.name = nameS;
        this.amount = amountS;
        this.cost = costS;
        this.admin = adminS;
    }

    public void fromBytes(ByteBuf buf)
    {
        created = buf.readBoolean();
        this.cost = buf.readDouble();
        this.x = buf.readInt();
        this.y = buf.readInt();
        this.z = buf.readInt();
        this.name = ByteBufUtils.readUTF8String(buf);
        this.amount = buf.readInt();
        this.admin = buf.readBoolean();
    }

    public void toBytes(ByteBuf buf)
    {
        buf.writeBoolean(created);
        buf.writeDouble(this.cost);
        buf.writeInt(this.x);
        buf.writeInt(this.y);
        buf.writeInt(this.z);
        ByteBufUtils.writeUTF8String(buf, this.name);
        buf.writeInt(this.amount);
        buf.writeBoolean(this.admin);
    }

    public static class Handler implements IMessageHandler<PacketSellerCreated, IMessage>
    {
        public IMessage onMessage(PacketSellerCreated message, MessageContext ctx)
        {
            EntityPlayer entityplayer = ctx.getServerHandler().player;
            World world = entityplayer.world;
            BlockPos blockpos = new BlockPos(message.x, message.y, message.z);
            TileEntityBlockSeller tileentityblockseller = (TileEntityBlockSeller)world.getTileEntity(blockpos);

            if (tileentityblockseller != null)
            {
                tileentityblockseller.setCreated(PacketSellerCreated.created);
                tileentityblockseller.setCost(message.cost);
                tileentityblockseller.setItem(message.name);
                tileentityblockseller.setAmount(message.amount);
                tileentityblockseller.setAdmin(Boolean.valueOf(message.admin));
                tileentityblockseller.markDirty();
            }

            return null;
        }
    }
}
