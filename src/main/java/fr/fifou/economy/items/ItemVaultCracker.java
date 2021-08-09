package fr.fifou.economy.items;

import fr.fifou.economy.ModEconomy;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemVaultCracker extends Item
{
    public static final String NAME = "item_vault_cracker";

    public ItemVaultCracker()
    {
        this.setMaxDamage(100);
        ItemsRegistery.setItemName(this, "item_vault_cracker");
        this.maxStackSize = 1;
        this.setCreativeTab(ModEconomy.tabEconomy);
        this.setUnlocalizedName("item_vault_cracker");
    }

    @SideOnly(Side.CLIENT)
    public boolean hasEffect(ItemStack stack)
    {
        return true;
    }
}
