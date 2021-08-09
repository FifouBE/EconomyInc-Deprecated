package fr.fifou.economy.items;

import fr.fifou.economy.ModEconomy;
import net.minecraft.item.Item;

public class ItemGear extends Item
{
    public static final String NAME = "item_gear";

    public ItemGear()
    {
        ItemsRegistery.setItemName(this, "item_gear");
        this.maxStackSize = 64;
        this.setCreativeTab(ModEconomy.tabEconomy);
        this.setUnlocalizedName("item_gear");
    }
}
