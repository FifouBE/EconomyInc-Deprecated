package fr.fifou.economy;

import fr.fifou.economy.items.ItemsRegistery;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TabEconomy extends CreativeTabs
{
    public TabEconomy(int i, String label)
    {
        super(i, label);
    }

    @SideOnly(Side.CLIENT)
    public ItemStack getTabIconItem()
    {
        return new ItemStack(ItemsRegistery.ITEM_CREDITCARD, 1, 0);
    }

    /**
     * Gets the translated Label.
     */
    public String getTranslatedTabLabel()
    {
        return "Economy Inc.";
    }
}
