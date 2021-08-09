package fr.fifou.economy.items;

import fr.fifou.economy.ModEconomy;
import net.minecraft.item.Item;

public class ItemGearsMecanism extends Item
{
    public static final String NAME = "item_gearmecanism";

    public ItemGearsMecanism()
    {
        ItemsRegistery.setItemName(this, "item_gearmecanism");
        this.maxStackSize = 64;
        this.setCreativeTab(ModEconomy.tabEconomy);
        this.setUnlocalizedName("item_gearmecanism");
    }
}
