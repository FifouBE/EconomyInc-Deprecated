package fr.fifou.economy.items;

import fr.fifou.economy.ModEconomy;
import net.minecraft.item.Item;

public class ItemTwentyb extends Item
{
    public static final String NAME = "item_twentyb";

    public ItemTwentyb()
    {
        ItemsRegistery.setItemName(this, "item_twentyb");
        this.maxStackSize = 64;
        this.setCreativeTab(ModEconomy.tabEconomy);
        this.setUnlocalizedName("item_twentyb");
    }
}
