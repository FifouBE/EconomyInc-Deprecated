package fr.fifou.economy.items;

import fr.fifou.economy.ConfigFile;
import fr.fifou.economy.ModEconomy;
import fr.fifou.economy.capability.CapabilityLoading;
import fr.fifou.economy.capability.IMoney;
import java.util.List;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemCreditcard extends Item
{
    public static final String NAME = "item_creditcard";
    private double funds;
    private boolean link;

    public ItemCreditcard()
    {
        ItemsRegistery.setItemName(this, "item_creditcard");
        this.maxStackSize = 1;
        this.setCreativeTab(ModEconomy.tabEconomy);
        this.setUnlocalizedName("item_creditcard");
    }

    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand hand)
    {
        ItemStack itemstack = playerIn.getHeldItemMainhand();

        if (!playerIn.getHeldItemOffhand().isItemEqual(new ItemStack(ItemsRegistery.ITEM_CREDITCARD)))
        {
            if (!playerIn.isSneaking())
            {
                if (itemstack.hasTagCompound())
                {
                    String s1 = playerIn.getHeldItemMainhand().getTagCompound().getString("OwnerUUID");
                    String s = playerIn.getUniqueID().toString();

                    if (s1.equals(s))
                    {
                        if (worldIn.isRemote)
                        {
                            System.out.println(((IMoney)playerIn.getCapability(CapabilityLoading.CAPABILITY_MONEY, (EnumFacing)null)).getLinked());

                            if (ConfigFile.canAccessCardWithoutWT)
                            {
                                if (((IMoney)playerIn.getCapability(CapabilityLoading.CAPABILITY_MONEY, (EnumFacing)null)).getLinked())
                                {
                                    playerIn.openGui(ModEconomy.instance, 0, worldIn, 0, 0, 0);
                                }
                                else
                                {
                                    playerIn.sendMessage(new TextComponentString("You don't have the wireless technology to access your card."));
                                }
                            }
                        }
                        else if (!worldIn.isRemote)
                        {
                            System.out.println(((IMoney)playerIn.getCapability(CapabilityLoading.CAPABILITY_MONEY, (EnumFacing)null)).getLinked());
                            ((IMoney)playerIn.getCapability(CapabilityLoading.CAPABILITY_MONEY, (EnumFacing)null)).sync(playerIn);
                        }
                    }
                    else
                    {
                        System.out.println("Will be fix in another version of the mod. Quite bugged for the moment. Fifou_BE - Author");
                    }
                }

                return new ActionResult(EnumActionResult.SUCCESS, itemstack);
            }

            if (!worldIn.isRemote)
            {
                if (!itemstack.hasTagCompound())
                {
                    itemstack.setTagCompound(new NBTTagCompound());
                }

                if (!itemstack.getTagCompound().hasKey("Owner"))
                {
                    UUID uuid = playerIn.getUniqueID();
                    ((IMoney)playerIn.getCapability(CapabilityLoading.CAPABILITY_MONEY, (EnumFacing)null)).setLinked(false);
                    itemstack.getTagCompound().setString("OwnerUUID", uuid.toString());
                    itemstack.getTagCompound().setString("Owner", playerIn.getDisplayNameString());
                    itemstack.getTagCompound().setBoolean("Owned", true);
                    itemstack.getTagCompound().setBoolean("Linked", false);
                    playerIn.playSound(SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));
                    ((IMoney)playerIn.getCapability(CapabilityLoading.CAPABILITY_MONEY, (EnumFacing)null)).sync(playerIn);
                }
            }
        }

        return new ActionResult(EnumActionResult.SUCCESS, itemstack);
    }

    @SideOnly(Side.CLIENT)

    /**
     * allows items to add custom lines of information to the mouseover description
     */
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn)
    {
        EntityPlayer entityplayer = Minecraft.getMinecraft().player;

        if (stack.hasTagCompound())
        {
            double d0 = ((IMoney)entityplayer.getCapability(CapabilityLoading.CAPABILITY_MONEY, (EnumFacing)null)).getMoney();
            boolean flag = stack.getTagCompound().getBoolean("Linked");
            String s = "";

            if (flag)
            {
                s = I18n.format("title.yes", new Object[0]);
            }
            else
            {
                s = I18n.format("title.no", new Object[0]);
            }

            String s1 = stack.getTagCompound().getString("Owner");
            boolean flag1 = stack.getTagCompound().getBoolean("Owned");
            tooltip.add(I18n.format("title.ownerCard", new Object[0]) + " : " + s1);
            tooltip.add(I18n.format("title.fundsCard", new Object[0]) + " : " + d0);
            tooltip.add(I18n.format("title.linkdCard", new Object[0]) + " : " + s);
        }
    }

    @SideOnly(Side.CLIENT)
    public boolean hasEffect(ItemStack stack)
    {
        return stack.hasTagCompound();
    }
}
