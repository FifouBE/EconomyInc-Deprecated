package fr.fifou.economy.packets;

import fr.fifou.economy.blocks.tileentity.TileEntityBlockSeller;
import fr.fifou.economy.capability.CapabilityLoading;
import fr.fifou.economy.capability.IMoney;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketSellerFundsTotal implements IMessage
{
    private double fundstotal;
    private int x;
    private int y;
    private int z;
    private int amount;
    private boolean recovery;

    public PacketSellerFundsTotal()
    {
    }

    public PacketSellerFundsTotal(double fundstotalS, int xS, int yS, int zS, int amountS, boolean recoveryS)
    {
        this.fundstotal = fundstotalS;
        this.x = xS;
        this.y = yS;
        this.z = zS;
        this.amount = amountS;
        this.recovery = recoveryS;
    }

    public void fromBytes(ByteBuf buf)
    {
        this.fundstotal = buf.readDouble();
        this.x = buf.readInt();
        this.y = buf.readInt();
        this.z = buf.readInt();
        this.amount = buf.readInt();
        this.recovery = buf.readBoolean();
    }

    public void toBytes(ByteBuf buf)
    {
        buf.writeDouble(this.fundstotal);
        buf.writeInt(this.x);
        buf.writeInt(this.y);
        buf.writeInt(this.z);
        buf.writeInt(this.amount);
        buf.writeBoolean(this.recovery);
    }

    public static class Handler implements IMessageHandler<PacketSellerFundsTotal, IMessage>
    {
        public IMessage onMessage(PacketSellerFundsTotal message, MessageContext ctx)
        {
            EntityPlayer entityplayer = ctx.getServerHandler().player;
            World world = entityplayer.world;
            BlockPos blockpos = new BlockPos(message.x, message.y, message.z);
            TileEntityBlockSeller tileentityblockseller = (TileEntityBlockSeller)world.getTileEntity(blockpos);

            if (tileentityblockseller != null)
            {
                if (!message.recovery)
                {
                    if (!tileentityblockseller.getStackInSlot(0).isEmpty())
                    {
                        boolean flag = tileentityblockseller.getAdmin();

                        if (!flag)
                        {
                            tileentityblockseller.setFundsTotal(message.fundstotal);
                            tileentityblockseller.getStackInSlot(0);
                            entityplayer.addItemStackToInventory(tileentityblockseller.getStackInSlot(0).splitStack(1));
                            int i = message.amount - 1;
                            tileentityblockseller.setAmount(i);
                            tileentityblockseller.markDirty();
                        }
                        else if (flag)
                        {
                            tileentityblockseller.setFundsTotal(message.fundstotal);
                            tileentityblockseller.setAmount(message.amount);
                            ItemStack itemstack = tileentityblockseller.getStackInSlot(0).copy().splitStack(1);
                            entityplayer.addItemStackToInventory(itemstack);
                            tileentityblockseller.markDirty();
                        }
                    }
                }
                else if (message.recovery)
                {
                    ((IMoney)entityplayer.getCapability(CapabilityLoading.CAPABILITY_MONEY, (EnumFacing)null)).setMoney(((IMoney)entityplayer.getCapability(CapabilityLoading.CAPABILITY_MONEY, (EnumFacing)null)).getMoney() + message.fundstotal);
                    ((IMoney)entityplayer.getCapability(CapabilityLoading.CAPABILITY_MONEY, (EnumFacing)null)).sync(entityplayer);
                    tileentityblockseller.setFundsTotal(0.0D);
                    tileentityblockseller.markDirty();
                }
            }

            return null;
        }
    }
}
