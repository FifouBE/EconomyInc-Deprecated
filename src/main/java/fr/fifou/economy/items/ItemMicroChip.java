package fr.fifou.economy.items;

import fr.fifou.economy.ModEconomy;
import fr.fifou.economy.capability.CapabilityLoading;
import fr.fifou.economy.capability.IMoney;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemMicroChip extends Item
{
    public static final String NAME = "item_microchip";

    public ItemMicroChip()
    {
        ItemsRegistery.setItemName(this, "item_microchip");
        this.maxStackSize = 1;
        this.setCreativeTab(ModEconomy.tabEconomy);
        this.setUnlocalizedName("item_microchip");
    }

    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer player, EnumHand handIn)
    {
        ItemStack itemstack = player.getHeldItemOffhand();
        ItemStack itemstack1 = player.getHeldItemMainhand();
        int i = 0;

        if (!worldIn.isRemote && !player.inventory.hasItemStack(itemstack) && player.inventory.hasItemStack(itemstack1))
        {
            for (int j = 0; j < player.inventory.getSizeInventory(); ++j)
            {
                if (player.inventory.getStackInSlot(j) == null)
                {
                    player.sendMessage(new TextComponentString("You can only linked one card, please remove the uncessary cards"));
                    return new ActionResult(EnumActionResult.FAIL, itemstack);
                }

                if (player.inventory.getStackInSlot(j).getItem() instanceof ItemCreditcard)
                {
                    ++i;
                    ItemStack itemstack2 = player.inventory.getStackInSlot(j);

                    if (i <= 1)
                    {
                        if (itemstack2.hasTagCompound() && itemstack2.getTagCompound().hasKey("Owner"))
                        {
                            String s = itemstack2.getTagCompound().getString("OwnerUUID");
                            String s1 = player.getUniqueID().toString();

                            if (s.equals(s1))
                            {
                                boolean flag = ((IMoney)player.getCapability(CapabilityLoading.CAPABILITY_MONEY, (EnumFacing)null)).getLinked();

                                if (!flag)
                                {
                                    player.sendMessage(new TextComponentString("Card updated !"));
                                    itemstack2.getTagCompound().setBoolean("Linked", true);
                                    ((IMoney)player.getCapability(CapabilityLoading.CAPABILITY_MONEY, (EnumFacing)null)).setLinked(true);
                                    ((IMoney)player.getCapability(CapabilityLoading.CAPABILITY_MONEY, (EnumFacing)null)).sync(player);
                                }
                                else
                                {
                                    player.sendMessage(new TextComponentString("Card is already linked"));
                                    player.addItemStackToInventory(itemstack1);
                                    ((IMoney)player.getCapability(CapabilityLoading.CAPABILITY_MONEY, (EnumFacing)null)).sync(player);
                                }
                            }
                        }
                        else
                        {
                            player.addItemStackToInventory(itemstack1);
                        }
                    }

                    return new ActionResult(EnumActionResult.SUCCESS, itemstack);
                }
            }
        }

        return new ActionResult(EnumActionResult.FAIL, itemstack);
    }

    @SideOnly(Side.CLIENT)

    /**
     * allows items to add custom lines of information to the mouseover description
     */
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn)
    {
        tooltip.add(I18n.format("title.wireless", new Object[0]));
    }
}
