package fr.fifou.economy.items;

import fr.fifou.economy.ModEconomy;
import net.minecraft.item.Item;

public class ItemPacketFiveHundeed extends Item
{
    public static final String NAME = "item_packetfivehundreed";

    public ItemPacketFiveHundeed()
    {
        ItemsRegistery.setItemName(this, "item_packetfivehundreed");
        this.maxStackSize = 64;
        this.setCreativeTab(ModEconomy.tabEconomy);
        this.setUnlocalizedName("item_packetfivehundreed");
    }
}
