package fr.fifou.economy.items;

import fr.fifou.economy.ModEconomy;
import net.minecraft.item.Item;

public class ItemRemover extends Item
{
    public static final String NAME = "item_remover";

    public ItemRemover()
    {
        ItemsRegistery.setItemName(this, "item_remover");
        this.maxStackSize = 1;
        this.setCreativeTab(ModEconomy.tabEconomy);
        this.setUnlocalizedName("item_remover");
    }
}
