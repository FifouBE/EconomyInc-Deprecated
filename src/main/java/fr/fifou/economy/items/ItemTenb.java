package fr.fifou.economy.items;

import fr.fifou.economy.ModEconomy;
import net.minecraft.item.Item;

public class ItemTenb extends Item
{
    public static final String NAME = "item_tenb";

    public ItemTenb()
    {
        ItemsRegistery.setItemName(this, "item_tenb");
        this.maxStackSize = 64;
        this.setCreativeTab(ModEconomy.tabEconomy);
        this.setUnlocalizedName("item_tenb");
    }
}
