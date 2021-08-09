package fr.fifou.economy.items;

import fr.fifou.economy.ModEconomy;
import net.minecraft.item.Item;

public class ItemFivehundreedb extends Item
{
    public static final String NAME = "item_fivehundreedb";

    public ItemFivehundreedb()
    {
        ItemsRegistery.setItemName(this, "item_fivehundreedb");
        this.maxStackSize = 64;
        this.setCreativeTab(ModEconomy.tabEconomy);
        this.setUnlocalizedName("item_fivehundreedb");
    }
}
