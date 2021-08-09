package fr.fifou.economy.items;

import fr.fifou.economy.ModEconomy;
import net.minecraft.item.Item;

public class ItemHundreedb extends Item
{
    public static final String NAME = "item_hundreedb";

    public ItemHundreedb()
    {
        ItemsRegistery.setItemName(this, "item_hundreedb");
        this.maxStackSize = 64;
        this.setCreativeTab(ModEconomy.tabEconomy);
        this.setUnlocalizedName("item_hundreedb");
    }
}
