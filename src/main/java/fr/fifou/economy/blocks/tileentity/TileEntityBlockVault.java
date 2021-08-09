package fr.fifou.economy.blocks.tileentity;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.items.ItemStackHandler;

public class TileEntityBlockVault extends TileEntity
{
    ItemStackHandler inventory = new ItemStackHandler(27);
    public String ownerS = "";
    private byte direction;
    private List<String> allowedPlayers = new ArrayList<String>();
    private int maxAllowedPlayers = 0;
    private boolean isOpen;

    public TileEntityBlockVault()
    {
    }

    public ItemStackHandler getHandler()
    {
        return this.inventory;
    }

    public TileEntityBlockVault(String ownerData)
    {
        this.ownerS = ownerData;
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
        this.allowedPlayers.clear();
        this.readFromNBT(pkt.getNbtCompound());
    }

    public boolean getIsOpen()
    {
        return this.isOpen;
    }

    public void setIsOpen(boolean isOpenIn)
    {
        this.isOpen = isOpenIn;
    }

    public void setOwner(String string)
    {
        this.ownerS = string;
    }

    public String getOwnerS()
    {
        return this.ownerS;
    }

    public Boolean hasItems()
    {
        for (int i = 0; i < 27; ++i)
        {
            if (this.inventory.getStackInSlot(i) != ItemStack.EMPTY)
            {
                return true;
            }
        }

        return false;
    }

    public void setOthers(String allowed)
    {
        this.allowedPlayers.add(allowed);
    }

    public List<String> getOthers()
    {
        return this.allowedPlayers;
    }

    public int getMax()
    {
        return this.maxAllowedPlayers;
    }

    public void addToMax()
    {
        ++this.maxAllowedPlayers;
    }

    public void removeToMax()
    {
        --this.maxAllowedPlayers;
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
        compound.setTag("inventory", this.inventory.serializeNBT());
        compound.setString("ownerS", this.ownerS);
        compound.setByte("direction", this.direction);
        compound.setInteger("maxallowed", this.maxAllowedPlayers);
        NBTTagList nbttaglist = new NBTTagList();

        for (int i = 0; i < this.allowedPlayers.size(); ++i)
        {
            String s = this.allowedPlayers.get(i);

            if (s != null)
            {
                nbttaglist.appendTag(new NBTTagString(s));
            }
        }

        compound.setTag("allowedList", nbttaglist);
        compound.setBoolean("isOpen", this.isOpen);
        return super.writeToNBT(compound);
    }

    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);
        this.inventory.deserializeNBT(compound.getCompoundTag("inventory"));
        this.ownerS = compound.getString("ownerS");
        this.direction = compound.getByte("direction");
        this.maxAllowedPlayers = compound.getInteger("maxallowed");
        this.isOpen = compound.getBoolean("isOpen");
        NBTTagList nbttaglist = compound.getTagList("allowedList", 8);

        for (int i = 0; i < nbttaglist.tagCount(); ++i)
        {
            this.allowedPlayers.add(i, nbttaglist.getStringTagAt(i));
        }
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
