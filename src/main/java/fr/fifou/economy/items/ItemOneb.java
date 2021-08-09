package fr.fifou.economy.items;

import fr.fifou.economy.ModEconomy;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemOneb extends Item
{
    public static final String NAME = "item_oneb";

    public ItemOneb()
    {
        ItemsRegistery.setItemName(this, "item_oneb");
        this.maxStackSize = 64;
        this.setCreativeTab(ModEconomy.tabEconomy);
        this.setUnlocalizedName("item_oneb");
    }

    /**
     * Called each tick as long the item is on a player inventory. Uses by maps to check if is on a player hand and
     * update it's contents.
     */
    public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected)
    {
        if (worldIn.isRemote)
        {
            System.out.println((Object)worldIn.getChunkFromBlockCoords(entityIn.getPosition()).getPos());
        }
    }
}
