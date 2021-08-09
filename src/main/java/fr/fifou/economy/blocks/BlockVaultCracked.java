package fr.fifou.economy.blocks;

import fr.fifou.economy.ModEconomy;
import fr.fifou.economy.blocks.tileentity.TileEntityBlockVaultCracked;
import fr.fifou.economy.items.ItemsRegistery;
import fr.fifou.economy.packets.PacketOpenCracked;
import fr.fifou.economy.packets.PacketsRegistery;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.items.IItemHandler;

public class BlockVaultCracked extends BlockContainer implements ITileEntityProvider
{
    public static final String NAME = "block_vault_cracked";

    public BlockVaultCracked()
    {
        super(Material.IRON);
        BlocksRegistery.setBlockName(this, "block_vault_cracked");
        this.setUnlocalizedName("block_vault_cracked");
        this.setResistance(20000.0F);
        this.setBlockUnbreakable();
    }

    /**
     * Called by ItemBlocks after a block is set in the world, to allow post-place logic
     */
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
    {
        TileEntityBlockVaultCracked tileentityblockvaultcracked = (TileEntityBlockVaultCracked)worldIn.getTileEntity(pos);
        int i = MathHelper.floor((double)(placer.rotationYaw * 4.0F / 360.0F) + 2.5D) & 3;
        tileentityblockvaultcracked.setDirection((byte)i);
        String s = String.valueOf(worldIn.rand.nextInt(8));
        String s1 = String.valueOf(worldIn.rand.nextInt(8));
        String s2 = String.valueOf(worldIn.rand.nextInt(8));
        String s3 = String.valueOf(worldIn.rand.nextInt(8));
        tileentityblockvaultcracked.setPassword(s + s1 + s2 + s3);
        tileentityblockvaultcracked.setCracked(false);
    }

    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        TileEntityBlockVaultCracked tileentityblockvaultcracked = (TileEntityBlockVaultCracked)worldIn.getTileEntity(pos);

        if (playerIn.getHeldItemMainhand().getItem().equals(ItemsRegistery.ITEM_VAULT_CRACKER))
        {
            if (!tileentityblockvaultcracked.getCracked())
            {
                playerIn.getHeldItemMainhand().damageItem(11, playerIn);
                playerIn.openGui(ModEconomy.instance, 10, worldIn, pos.getX(), pos.getY(), pos.getZ());
            }
        }
        else if (tileentityblockvaultcracked.getCracked())
        {
            PacketsRegistery.network.sendToServer(new PacketOpenCracked(pos.getX(), pos.getY(), pos.getZ(), true));
        }

        return false;
    }

    /**
     * Used to determine ambient occlusion and culling when rebuilding chunks for render
     */
    public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }

    public int getRenderType()
    {
        return -1;
    }

    /**
     * Returns a new instance of a block's tile entity class. Called on placing the block.
     */
    public TileEntity createNewTileEntity(World worldIn, int meta)
    {
        return new TileEntityBlockVaultCracked();
    }

    /**
     * Called serverside after this block is replaced with another in Chunk, but before the Tile Entity is updated
     */
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state)
    {
        TileEntityBlockVaultCracked tileentityblockvaultcracked = (TileEntityBlockVaultCracked)worldIn.getTileEntity(pos);

        if (tileentityblockvaultcracked != null)
        {
            IItemHandler iitemhandler = tileentityblockvaultcracked.getHandler();

            if (iitemhandler != null)
            {
                for (int i = 0; i < iitemhandler.getSlots(); ++i)
                {
                    if (iitemhandler.getStackInSlot(i) != ItemStack.EMPTY)
                    {
                        EntityItem entityitem = new EntityItem(worldIn, (double)pos.getX() + 0.5D, (double)pos.getY() + 0.5D, (double)pos.getZ() + 0.5D, iitemhandler.getStackInSlot(i));
                        float f = 0.1F;
                        float f1 = worldIn.rand.nextFloat() - 0.5F;
                        float f2 = worldIn.rand.nextFloat() - 0.5F;
                        float f3 = worldIn.rand.nextFloat() - 0.5F;
                        entityitem.motionX = (double)(f1 * f);
                        entityitem.motionY = (double)(f2 * f);
                        entityitem.motionZ = (double)(f3 * f);
                        worldIn.spawnEntity(entityitem);
                    }
                }
            }
        }

        super.breakBlock(worldIn, pos, state);
        worldIn.removeTileEntity(pos);
    }
}
