package fr.fifou.economy.items;

import fr.fifou.economy.ModEconomy;
import net.minecraft.item.Item;

public class ItemFiftyb extends Item
{
    public static final String NAME = "item_fiftybe";

    public ItemFiftyb()
    {
        ItemsRegistery.setItemName(this, "item_fiftybe");
        this.maxStackSize = 64;
        this.setCreativeTab(ModEconomy.tabEconomy);
        this.setUnlocalizedName("item_fiftybe");
    }
}
