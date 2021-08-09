package fr.fifou.economy.items;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemBlockMetadata extends ItemBlock
{
    String[] unlocalizedNames;

    public ItemBlockMetadata(Block block, String[] unlocalizedNamesIn)
    {
        super(block);
        this.unlocalizedNames = unlocalizedNamesIn;
        this.setHasSubtypes(true);
        this.setMaxDamage(0);
    }

    /**
     * Converts the given ItemStack damage value into a metadata value to be placed in the world when this Item is
     * placed as a Block (mostly used with ItemBlocks).
     */
    public int getMetadata(int damage)
    {
        return damage;
    }

    /**
     * Returns the unlocalized name of this item. This version accepts an ItemStack so different stacks can have
     * different names based on their damage or NBT.
     */
    public String getUnlocalizedName(ItemStack stack)
    {
        return "economy." + this.unlocalizedNames[stack.getMetadata()];
    }
}
