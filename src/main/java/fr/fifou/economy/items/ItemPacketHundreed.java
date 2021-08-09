package fr.fifou.economy.items;

import fr.fifou.economy.ModEconomy;
import net.minecraft.item.Item;

public class ItemPacketHundreed extends Item
{
    public static final String NAME = "item_packethundreed";

    public ItemPacketHundreed()
    {
        ItemsRegistery.setItemName(this, "item_packethundreed");
        this.maxStackSize = 64;
        this.setCreativeTab(ModEconomy.tabEconomy);
        this.setUnlocalizedName("item_packethundreed");
    }
}
