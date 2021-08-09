package fr.fifou.economy.blocks.tileentity;

import fr.fifou.economy.ConfigFile;
import fr.fifou.economy.capability.CapabilityLoading;
import fr.fifou.economy.capability.IMoney;
import fr.fifou.economy.items.ItemsRegistery;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.items.ItemStackHandler;

public class TileEntityBlockChanger extends TileEntity implements ITickable
{
    ItemStackHandler inventory = new ItemStackHandler(3);
    private byte direction;
    public int numbUse;
    public EntityPlayer user;
    public String name;
    public int timeProcess = 356;
    public int timePassed = 0;
    public boolean isProcessing;

    public TileEntityBlockChanger()
    {
    }

    public TileEntityBlockChanger(int numbUse)
    {
        this.numbUse = numbUse;
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

    public ItemStack setStackInSlot(int slot, ItemStack stack, boolean simulate)
    {
        return this.inventory.insertItem(slot, stack, simulate);
    }

    public ItemStackHandler getHandler()
    {
        return this.inventory;
    }

    public int getNumbUse()
    {
        return this.numbUse;
    }

    public void setNumbUse(int numbUse)
    {
        this.numbUse = numbUse;
    }

    public EntityPlayer getEntityPlayer()
    {
        return this.user;
    }

    public void setEntityPlayer(EntityPlayer currentUser)
    {
        this.user = currentUser;
    }

    public byte getDirection()
    {
        return this.direction;
    }

    public void setDirection(byte direction)
    {
        this.direction = direction;
    }

    public int getTimePassed()
    {
        return this.timePassed;
    }

    public boolean getIsProcessing()
    {
        return this.isProcessing;
    }

    public NBTTagCompound writeToNBT(NBTTagCompound compound)
    {
        compound.setTag("inventory", this.inventory.serializeNBT());
        compound.setInteger("numbUse", this.numbUse);
        compound.setBoolean("isProcessing", this.isProcessing);
        compound.setInteger("timePassed", this.timePassed);
        return super.writeToNBT(compound);
    }

    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);
        this.numbUse = compound.getInteger("numbUse");
        this.isProcessing = compound.getBoolean("isProcessing");
        this.timePassed = compound.getInteger("timePassed");
        this.inventory.deserializeNBT(compound.getCompoundTag("inventory"));
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

    /**
     * Like the old updateEntity(), except more generic.
     */
    public void update()
    {
        TileEntityBlockChanger tileentityblockchanger = (TileEntityBlockChanger)this.world.getTileEntity(this.pos);
        ItemStack itemstack = tileentityblockchanger.getStackInSlot(0);
        ItemStack itemstack1 = tileentityblockchanger.getStackInSlot(1);
        ItemStack itemstack2 = tileentityblockchanger.getStackInSlot(2);

        if (!this.world.isRemote && itemstack.getItem() == ItemsRegistery.ITEM_GOLDNUGGET && itemstack.hasTagCompound() && itemstack1.getItem() == ItemsRegistery.ITEM_CREDITCARD && itemstack1.hasTagCompound() && tileentityblockchanger.getEntityPlayer() != null)
        {
            String s = itemstack1.getTagCompound().getString("OwnerUUID");
            String s1 = tileentityblockchanger.getEntityPlayer().getUniqueID().toString();

            if (s.equals(s1) && itemstack2.isEmpty())
            {
                if (this.timePassed == 356)
                {
                    EntityPlayer entityplayer = this.getEntityPlayer();
                    double d0 = ((IMoney)entityplayer.getCapability(CapabilityLoading.CAPABILITY_MONEY, (EnumFacing)null)).getMoney();
                    String s2 = itemstack.getTagCompound().getString("weight");
                    double d1 = d0 + Double.parseDouble(s2) * (double)ConfigFile.multiplierGoldNuggetWeight;
                    ((IMoney)entityplayer.getCapability(CapabilityLoading.CAPABILITY_MONEY, (EnumFacing)null)).setMoney(d1);
                    ((IMoney)entityplayer.getCapability(CapabilityLoading.CAPABILITY_MONEY, (EnumFacing)null)).sync(entityplayer);
                    itemstack.splitStack(1);
                    ItemStack itemstack3 = itemstack1.copy();
                    itemstack1.splitStack(1);
                    tileentityblockchanger.setStackInSlot(2, itemstack3, false);
                    this.timePassed = 0;
                    this.isProcessing = false;
                    this.markDirty();
                }
                else
                {
                    ++this.timePassed;
                    this.isProcessing = true;
                    this.markDirty();
                }
            }
        }

        if (itemstack.getItem() == Items.AIR || itemstack1.getItem() == Items.AIR)
        {
            this.timePassed = 0;
            this.isProcessing = false;
            this.markDirty();
        }
    }

    public ItemStack removeStackFromSlot(int index)
    {
        return this.inventory.getStackInSlot(index).splitStack(1);
    }

    public ItemStack getStackInSlot(int index)
    {
        return this.inventory.getStackInSlot(index);
    }
}
