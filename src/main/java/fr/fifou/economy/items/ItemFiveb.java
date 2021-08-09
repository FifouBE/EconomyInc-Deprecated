package fr.fifou.economy.items;

import fr.fifou.economy.ModEconomy;
import net.minecraft.item.Item;

public class ItemFiveb extends Item
{
    public static final String NAME = "item_fiveb";

    public ItemFiveb()
    {
        ItemsRegistery.setItemName(this, "item_fiveb");
        this.maxStackSize = 64;
        this.setCreativeTab(ModEconomy.tabEconomy);
        this.setUnlocalizedName("item_fiveb");
    }
}
