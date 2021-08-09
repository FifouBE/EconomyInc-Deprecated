package fr.fifou.economy.items;

import fr.fifou.economy.ModEconomy;
import net.minecraft.item.Item;

public class ItemPacketTwoHundreed extends Item
{
    public static final String NAME = "item_packettwohundreed";

    public ItemPacketTwoHundreed()
    {
        ItemsRegistery.setItemName(this, "item_packettwohundreed");
        this.maxStackSize = 64;
        this.setCreativeTab(ModEconomy.tabEconomy);
        this.setUnlocalizedName("item_packettwohundreed");
    }
}
