package fr.fifou.economy.containers;

import fr.fifou.economy.blocks.tileentity.TileEntityBlockChanger;
import fr.fifou.economy.items.ItemsRegistery;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class ContainerChanger extends Container
{
    public int X;
    public int Y;
    public int Z;
    public TileEntityBlockChanger te;

    public ContainerChanger(InventoryPlayer inventoryPlayer, TileEntityBlockChanger tile)
    {
        IItemHandler iitemhandler = tile.getHandler();
        this.addSlotToContainer(new SlotItemHandler(iitemhandler, 0, 56, 16));
        this.addSlotToContainer(new SlotItemHandler(iitemhandler, 1, 56, 52));
        this.addSlotToContainer(new SlotItemHandler(iitemhandler, 2, 116, 34));
        this.bindPlayerInventory(inventoryPlayer);
        this.X = tile.getPos().getX();
        this.Y = tile.getPos().getY();
        this.Z = tile.getPos().getZ();
        this.te = tile;
    }

    private void bindPlayerInventory(InventoryPlayer inventoryPlayer)
    {
        for (int i = 0; i < 3; ++i)
        {
            for (int j = 0; j < 9; ++j)
            {
                this.addSlotToContainer(new Slot(inventoryPlayer, j + i * 9 + 9, 8 + j * 18, 48 + i * 18 + 35));
            }
        }

        for (int k = 0; k < 9; ++k)
        {
            this.addSlotToContainer(new Slot(inventoryPlayer, k, 8 + k * 18, 141));
        }
    }

    /**
     * Determines whether supplied player can use this container
     */
    public boolean canInteractWith(EntityPlayer playerIn)
    {
        return true;
    }

    /**
     * Take a stack from the specified inventory slot.
     */
    public ItemStack transferStackInSlot(EntityPlayer playerIn, int index)
    {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = (Slot)this.inventorySlots.get(index);

        if (slot != null && slot.getHasStack())
        {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();

            if (index == 2)
            {
                if (!this.mergeItemStack(itemstack1, 3, 39, true))
                {
                    return ItemStack.EMPTY;
                }

                slot.onSlotChange(itemstack1, itemstack);
            }
            else if (index != 1 && index != 0)
            {
                if (itemstack1.getItem() == ItemsRegistery.ITEM_GOLDNUGGET)
                {
                    if (!this.mergeItemStack(itemstack1, 0, 1, false))
                    {
                        return ItemStack.EMPTY;
                    }
                }
                else if (itemstack1.getItem() == ItemsRegistery.ITEM_CREDITCARD)
                {
                    if (!this.mergeItemStack(itemstack1, 1, 2, false))
                    {
                        return ItemStack.EMPTY;
                    }
                }
                else if (index >= 3 && index < 30)
                {
                    if (!this.mergeItemStack(itemstack1, 30, 39, false))
                    {
                        return ItemStack.EMPTY;
                    }
                }
                else if (index >= 30 && index < 39 && !this.mergeItemStack(itemstack1, 3, 30, false))
                {
                    return ItemStack.EMPTY;
                }
            }
            else if (!this.mergeItemStack(itemstack1, 3, 39, false))
            {
                return ItemStack.EMPTY;
            }

            if (itemstack1.isEmpty())
            {
                slot.putStack(ItemStack.EMPTY);
            }
            else
            {
                slot.onSlotChanged();
            }

            if (itemstack1.getCount() == itemstack.getCount())
            {
                return ItemStack.EMPTY;
            }

            slot.onTake(playerIn, itemstack1);
        }

        return itemstack;
    }

    /**
     * Called when the container is closed.
     */
    public void onContainerClosed(EntityPlayer playerIn)
    {
        super.onContainerClosed(playerIn);
        World world = playerIn.world;

        if (!world.isRemote)
        {
            TileEntityBlockChanger tileentityblockchanger = (TileEntityBlockChanger)world.getTileEntity(new BlockPos(this.X, this.Y, this.Z));

            if (tileentityblockchanger.getNumbUse() > 0)
            {
                tileentityblockchanger.setNumbUse(0);
                tileentityblockchanger.setEntityPlayer((EntityPlayer)null);
                tileentityblockchanger.markDirty();
            }

            ItemStack itemstack = tileentityblockchanger.getStackInSlot(0).splitStack(1);

            if (!itemstack.isEmpty())
            {
                world.spawnEntity(new EntityItem(world, (double)this.X, (double)this.Y, (double)this.Z, itemstack));
            }

            itemstack = tileentityblockchanger.getStackInSlot(1).splitStack(1);

            if (!itemstack.isEmpty())
            {
                world.spawnEntity(new EntityItem(world, (double)this.X, (double)this.Y, (double)this.Z, itemstack));
            }

            itemstack = tileentityblockchanger.getStackInSlot(2).splitStack(1);

            if (!itemstack.isEmpty())
            {
                world.spawnEntity(new EntityItem(world, (double)this.X, (double)this.Y, (double)this.Z, itemstack));
            }
        }
    }
}
