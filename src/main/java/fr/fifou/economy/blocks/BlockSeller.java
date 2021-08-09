package fr.fifou.economy.blocks;

import fr.fifou.economy.ModEconomy;
import fr.fifou.economy.blocks.tileentity.TileEntityBlockSeller;
import fr.fifou.economy.items.ItemsRegistery;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.IItemHandler;

public class BlockSeller extends BlockContainer
{
    public static final PropertyDirection FACING = BlockHorizontal.FACING;
    public static final String NAME = "block_seller";

    public BlockSeller()
    {
        super(Material.IRON);
        BlocksRegistery.setBlockName(this, "block_seller");
        this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
        this.setUnlocalizedName("block_seller");
        this.setCreativeTab(ModEconomy.tabEconomy);
        this.setResistance(20000.0F);
        this.setBlockUnbreakable();
    }

    /**
     * Returns a new instance of a block's tile entity class. Called on placing the block.
     */
    public TileEntity createNewTileEntity(World worldIn, int meta)
    {
        return new TileEntityBlockSeller();
    }

    /**
     * Called by ItemBlocks after a block is set in the world, to allow post-place logic
     */
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
    {
        worldIn.setBlockState(pos, state.withProperty(FACING, placer.getHorizontalFacing()), 2);
        TileEntityBlockSeller tileentityblockseller = (TileEntityBlockSeller)worldIn.getTileEntity(pos);
        tileentityblockseller.setOwner(placer.getUniqueID().toString());
        tileentityblockseller.setFacing(state.toString().substring(28, 33));
        tileentityblockseller.setOwnerName(placer.getName());
    }

    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        TileEntityBlockSeller tileentityblockseller = (TileEntityBlockSeller)worldIn.getTileEntity(pos);

        if (tileentityblockseller != null && tileentityblockseller.getOwner() != null)
        {
            String s = tileentityblockseller.getOwner();
            String s1 = playerIn.getUniqueID().toString();

            if (s.equals(s1))
            {
                if (!tileentityblockseller.getCreated())
                {
                    playerIn.openGui(ModEconomy.instance, 3, worldIn, pos.getX(), pos.getY(), pos.getZ());
                }
                else
                {
                    playerIn.openGui(ModEconomy.instance, 4, worldIn, pos.getX(), pos.getY(), pos.getZ());
                }
            }
            else
            {
                playerIn.openGui(ModEconomy.instance, 4, worldIn, pos.getX(), pos.getY(), pos.getZ());
            }
        }

        return true;
    }

    /**
     * Called serverside after this block is replaced with another in Chunk, but before the Tile Entity is updated
     */
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state)
    {
        TileEntityBlockSeller tileentityblockseller = (TileEntityBlockSeller)worldIn.getTileEntity(pos);

        if (tileentityblockseller != null)
        {
            IItemHandler iitemhandler = tileentityblockseller.getHandler();

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
    }

    public void onBlockClicked(World worldIn, BlockPos pos, EntityPlayer playerIn)
    {
        TileEntityBlockSeller tileentityblockseller = (TileEntityBlockSeller)worldIn.getTileEntity(pos);
        ItemStack itemstack = playerIn.getHeldItemMainhand();
        worldIn.getBlockState(pos);

        if (tileentityblockseller != null && itemstack.isItemEqual(new ItemStack(ItemsRegistery.ITEM_REMOVER)))
        {
            String s = tileentityblockseller.getOwner().toString();
            String s1 = playerIn.getUniqueID().toString();

            if (s.equals(s1))
            {
                worldIn.destroyBlock(pos, true);
                worldIn.removeTileEntity(pos);
            }
        }
    }

    /**
     * Called after the block is set in the Chunk data, but before the Tile Entity is set
     */
    public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state)
    {
        this.setDefaultFacing(worldIn, pos, state);
    }

    private void setDefaultFacing(World worldIn, BlockPos pos, IBlockState state)
    {
        if (!worldIn.isRemote)
        {
            IBlockState iblockstate = worldIn.getBlockState(pos.north());
            IBlockState iblockstate1 = worldIn.getBlockState(pos.south());
            IBlockState iblockstate2 = worldIn.getBlockState(pos.west());
            IBlockState iblockstate3 = worldIn.getBlockState(pos.east());
            EnumFacing enumfacing = (EnumFacing)state.getValue(FACING);

            if (enumfacing == EnumFacing.NORTH && iblockstate.isFullBlock() && !iblockstate1.isFullBlock())
            {
                enumfacing = EnumFacing.SOUTH;
            }
            else if (enumfacing == EnumFacing.SOUTH && iblockstate1.isFullBlock() && !iblockstate.isFullBlock())
            {
                enumfacing = EnumFacing.NORTH;
            }
            else if (enumfacing == EnumFacing.WEST && iblockstate2.isFullBlock() && !iblockstate3.isFullBlock())
            {
                enumfacing = EnumFacing.EAST;
            }
            else if (enumfacing == EnumFacing.EAST && iblockstate3.isFullBlock() && !iblockstate2.isFullBlock())
            {
                enumfacing = EnumFacing.WEST;
            }

            worldIn.setBlockState(pos, state.withProperty(FACING, enumfacing), 2);
        }
    }

    public static void setState(boolean active, World worldIn, BlockPos pos)
    {
        IBlockState iblockstate = worldIn.getBlockState(pos);
        TileEntity tileentity = worldIn.getTileEntity(pos);

        if (active)
        {
            worldIn.setBlockState(pos, BlocksRegistery.BLOCK_SELLER.getDefaultState().withProperty(FACING, iblockstate.getValue(FACING)), 3);
            worldIn.setBlockState(pos, BlocksRegistery.BLOCK_SELLER.getDefaultState().withProperty(FACING, iblockstate.getValue(FACING)), 3);
        }
        else
        {
            worldIn.setBlockState(pos, BlocksRegistery.BLOCK_SELLER.getDefaultState().withProperty(FACING, iblockstate.getValue(FACING)), 3);
            worldIn.setBlockState(pos, BlocksRegistery.BLOCK_SELLER.getDefaultState().withProperty(FACING, iblockstate.getValue(FACING)), 3);
        }

        if (tileentity != null)
        {
            tileentity.validate();
            worldIn.setTileEntity(pos, tileentity);
        }
    }

    /**
     * Called by ItemBlocks just before a block is actually set in the world, to allow for adjustments to the
     * IBlockstate
     */
    public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
    {
        return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite());
    }

    /**
     * Convert the given metadata into a BlockState for this Block
     */
    public IBlockState getStateFromMeta(int meta)
    {
        EnumFacing enumfacing = EnumFacing.getFront(meta);

        if (enumfacing.getAxis() == EnumFacing.Axis.Y)
        {
            enumfacing = EnumFacing.NORTH;
        }

        return this.getDefaultState().withProperty(FACING, enumfacing);
    }

    /**
     * Convert the BlockState into the correct metadata value
     */
    public int getMetaFromState(IBlockState state)
    {
        return ((EnumFacing)state.getValue(FACING)).getIndex();
    }

    /**
     * Returns the blockstate with the given rotation from the passed blockstate. If inapplicable, returns the passed
     * blockstate.
     */
    public IBlockState withRotation(IBlockState state, Rotation rot)
    {
        return state.withProperty(FACING, rot.rotate((EnumFacing)state.getValue(FACING)));
    }

    /**
     * Returns the blockstate with the given mirror of the passed blockstate. If inapplicable, returns the passed
     * blockstate.
     */
    public IBlockState withMirror(IBlockState state, Mirror mirrorIn)
    {
        return state.withRotation(mirrorIn.toRotation((EnumFacing)state.getValue(FACING)));
    }

    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, new IProperty[] {FACING});
    }

    public boolean isFullCube(IBlockState state)
    {
        return false;
    }

    @SideOnly(Side.CLIENT)

    /**
     * Used to determine ambient occlusion and culling when rebuilding chunks for render
     */
    public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }

    /**
     * The type of render function called. MODEL for mixed tesr and static model, MODELBLOCK_ANIMATED for TESR-only,
     * LIQUID for vanilla liquids, INVISIBLE to skip all rendering
     */
    public EnumBlockRenderType getRenderType(IBlockState state)
    {
        return EnumBlockRenderType.MODEL;
    }

    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer()
    {
        return BlockRenderLayer.CUTOUT_MIPPED;
    }
}
