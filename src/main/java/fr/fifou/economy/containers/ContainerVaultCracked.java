package fr.fifou.economy.containers;

import fr.fifou.economy.blocks.tileentity.TileEntityBlockVaultCracked;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class ContainerVaultCracked extends Container
{
    public int X;
    public int Y;
    public int Z;

    public ContainerVaultCracked(InventoryPlayer inventoryPlayer, TileEntityBlockVaultCracked tile)
    {
        this.X = tile.getPos().getX();
        this.Y = tile.getPos().getY();
        this.Z = tile.getPos().getZ();
        IItemHandler iitemhandler = tile.getHandler();

        for (int i = 0; i < 3; ++i)
        {
            for (int j = 0; j < 9; ++j)
            {
                this.addSlotToContainer(new SlotItemHandler(iitemhandler, j + i * 9, 8 + j * 18, 17 + i * 18));
            }
        }

        this.bindPlayerInventory(inventoryPlayer);
    }

    private void bindPlayerInventory(InventoryPlayer inventoryPlayer)
    {
        for (int i = 0; i < 3; ++i)
        {
            for (int j = 0; j < 9; ++j)
            {
                this.addSlotToContainer(new Slot(inventoryPlayer, j + i * 9 + 9, 8 + j * 18, 48 + i * 18 + 37));
            }
        }

        for (int k = 0; k < 9; ++k)
        {
            this.addSlotToContainer(new Slot(inventoryPlayer, k, 8 + k * 18, 143));
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
            int i = this.inventorySlots.size() - playerIn.inventory.mainInventory.size();

            if (index < i)
            {
                if (!this.mergeItemStack(itemstack1, i, this.inventorySlots.size(), true))
                {
                    return ItemStack.EMPTY;
                }
            }
            else if (!this.mergeItemStack(itemstack1, 0, i, false))
            {
                return ItemStack.EMPTY;
            }

            if (itemstack1.getCount() == 0)
            {
                slot.putStack(ItemStack.EMPTY);
            }
            else
            {
                slot.onSlotChanged();
            }

            slot.onTake(playerIn, itemstack1);
        }

        return itemstack;
    }
}
