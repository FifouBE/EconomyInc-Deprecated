package fr.fifou.economy.items;

import fr.fifou.economy.ModEconomy;
import net.minecraft.item.Item;

public class ItemPacketTwenty extends Item
{
    public static final String NAME = "item_packettwenty";

    public ItemPacketTwenty()
    {
        ItemsRegistery.setItemName(this, "item_packettwenty");
        this.maxStackSize = 64;
        this.setCreativeTab(ModEconomy.tabEconomy);
        this.setUnlocalizedName("item_packettwenty");
    }
}
