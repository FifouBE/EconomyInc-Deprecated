package fr.fifou.economy.items;

import fr.fifou.economy.ModEconomy;
import net.minecraft.item.Item;

public class ItemTwohundreedb extends Item
{
    public static final String NAME = "item_twohundreedb";

    public ItemTwohundreedb()
    {
        ItemsRegistery.setItemName(this, "item_twohundreedb");
        this.maxStackSize = 64;
        this.setCreativeTab(ModEconomy.tabEconomy);
        this.setUnlocalizedName("item_twohundreedb");
    }
}
