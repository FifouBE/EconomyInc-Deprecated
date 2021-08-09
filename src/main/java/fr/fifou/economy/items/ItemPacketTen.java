package fr.fifou.economy.items;

import fr.fifou.economy.ModEconomy;
import net.minecraft.item.Item;

public class ItemPacketTen extends Item
{
    public static final String NAME = "item_packetten";

    public ItemPacketTen()
    {
        ItemsRegistery.setItemName(this, "item_packetten");
        this.maxStackSize = 64;
        this.setCreativeTab(ModEconomy.tabEconomy);
        this.setUnlocalizedName("item_packetten");
    }
}
