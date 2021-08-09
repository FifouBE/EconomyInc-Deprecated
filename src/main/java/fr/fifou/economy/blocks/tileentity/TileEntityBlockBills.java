package fr.fifou.economy.blocks.tileentity;

import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

public class TileEntityBlockBills extends TileEntity
{
    private byte direction;
    public int numbBills = 0;
    public String billRef = "";

    public TileEntityBlockBills()
    {
    }

    public TileEntityBlockBills(byte dirIn, int numbBillsIn, String billRefIn)
    {
        this.numbBills = numbBillsIn;
        this.direction = dirIn;
        this.billRef = billRefIn;
    }

    public SPacketUpdateTileEntity getUpdatePacket()
    {
        return new SPacketUpdateTileEntity(this.pos, 1, this.getUpdateTag());
    }

    public NBTTagCompound getUpdateTag()
    {
        return this.writeToNBT(new NBTTagCompound());
    }

    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt)
    {
        this.readFromNBT(pkt.getNbtCompound());
    }

    public String getBill()
    {
        return this.billRef;
    }

    public void setBillRef(String billRefIn)
    {
        this.billRef = billRefIn;
    }

    public int getNumbBills()
    {
        return this.numbBills;
    }

    public void setNumbUse(int numbBillsIn)
    {
        this.numbBills = numbBillsIn;
    }

    public void addBill()
    {
        ++this.numbBills;
    }

    public byte getDirection()
    {
        return this.direction;
    }

    public void setDirection(byte direction)
    {
        this.direction = direction;
    }

    public NBTTagCompound writeToNBT(NBTTagCompound compound)
    {
        compound.setInteger("numbBills", this.numbBills);
        compound.setByte("direction", this.direction);
        compound.setString("billRef", this.billRef);
        return super.writeToNBT(compound);
    }

    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);
        this.numbBills = compound.getInteger("numbBills");
        this.direction = compound.getByte("direction");
        this.billRef = compound.getString("billRef");
    }

    /**
     * For tile entities, ensures the chunk containing the tile entity is saved to disk later - the game won't think it
     * hasn't changed and skip it.
     */
    public void markDirty()
    {
        IBlockState iblockstate = this.world.getBlockState(this.getPos());
        this.world.notifyBlockUpdate(this.getPos(), iblockstate, iblockstate, 3);
    }
}
