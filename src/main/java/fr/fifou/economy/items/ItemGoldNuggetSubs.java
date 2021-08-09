package fr.fifou.economy.items;

import fr.fifou.economy.ModEconomy;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemGoldNuggetSubs extends Item
{
    public static final String NAME = "item_goldnuggetsubs";

    public ItemGoldNuggetSubs()
    {
        ItemsRegistery.setItemName(this, "item_goldnuggetsubs");
        this.maxStackSize = 1;
        this.setCreativeTab(ModEconomy.tabEconomy);
        this.setUnlocalizedName("item_goldnuggetsubs");
    }

    @SideOnly(Side.CLIENT)

    /**
     * allows items to add custom lines of information to the mouseover description
     */
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn)
    {
        if (stack.hasTagCompound())
        {
            String s = stack.getTagCompound().getString("weight");
            tooltip.add(I18n.format("title.weight", new Object[0]) + s + "g");
        }
    }
}
