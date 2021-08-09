package fr.fifou.economy.items;

import fr.fifou.economy.ModEconomy;
import net.minecraft.item.Item;

public class ItemPacketOne extends Item
{
    public static final String NAME = "item_packetone";

    public ItemPacketOne()
    {
        ItemsRegistery.setItemName(this, "item_packetone");
        this.maxStackSize = 64;
        this.setCreativeTab(ModEconomy.tabEconomy);
        this.setUnlocalizedName("item_packetone");
    }
}
