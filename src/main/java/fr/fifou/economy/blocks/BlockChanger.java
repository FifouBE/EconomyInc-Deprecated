package fr.fifou.economy.blocks;

import fr.fifou.economy.ModEconomy;
import fr.fifou.economy.blocks.tileentity.TileEntityBlockChanger;
import fr.fifou.economy.items.ItemsRegistery;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.items.IItemHandler;

public class BlockChanger extends BlockContainer implements ITileEntityProvider
{
    public static final String NAME = "block_changer";
    public static final PropertyDirection FACING = BlockHorizontal.FACING;

    public BlockChanger()
    {
        super(Material.IRON);
        BlocksRegistery.setBlockName(this, "block_changer");
        this.setUnlocalizedName("block_changer");
        this.setCreativeTab(ModEconomy.tabEconomy);
        this.setBlockUnbreakable();
        this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
    }

    /**
     * Returns a new instance of a block's tile entity class. Called on placing the block.
     */
    public TileEntity createNewTileEntity(World worldIn, int meta)
    {
        return new TileEntityBlockChanger();
    }

    /**
     * The type of render function called. MODEL for mixed tesr and static model, MODELBLOCK_ANIMATED for TESR-only,
     * LIQUID for vanilla liquids, INVISIBLE to skip all rendering
     */
    public EnumBlockRenderType getRenderType(IBlockState state)
    {
        return EnumBlockRenderType.MODEL;
    }

    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        TileEntityBlockChanger tileentityblockchanger = (TileEntityBlockChanger)worldIn.getTileEntity(pos);

        if (!worldIn.isRemote)
        {
            if (tileentityblockchanger != null && tileentityblockchanger.getNumbUse() < 1)
            {
                playerIn.openGui(ModEconomy.instance, 5, worldIn, pos.getX(), pos.getY(), pos.getZ());
                tileentityblockchanger.setNumbUse(1);
                tileentityblockchanger.setEntityPlayer(playerIn);
                tileentityblockchanger.markDirty();
            }
        }
        else if (worldIn.isRemote && tileentityblockchanger != null && tileentityblockchanger.getNumbUse() > 0)
        {
            playerIn.sendMessage(new TextComponentString(I18n.format("title.alreadyUsed", new Object[0])));
        }

        return true;
    }

    public void onBlockClicked(World worldIn, BlockPos pos, EntityPlayer playerIn)
    {
        TileEntityBlockChanger tileentityblockchanger = (TileEntityBlockChanger)worldIn.getTileEntity(pos);
        ItemStack itemstack = playerIn.getHeldItemMainhand();
        worldIn.getBlockState(pos);

        if (tileentityblockchanger != null && itemstack.isItemEqual(new ItemStack(ItemsRegistery.ITEM_REMOVER)))
        {
            if (tileentityblockchanger.getNumbUse() < 1)
            {
                worldIn.destroyBlock(pos, true);
                worldIn.removeTileEntity(pos);
            }
            else
            {
                FMLCommonHandler.instance().getSide();

                if (Side.CLIENT.isClient())
                {
                    worldIn.destroyBlock(pos, true);
                    worldIn.removeTileEntity(pos);
                }
            }
        }
    }

    /**
     * Called serverside after this block is replaced with another in Chunk, but before the Tile Entity is updated
     */
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state)
    {
        TileEntityBlockChanger tileentityblockchanger = (TileEntityBlockChanger)worldIn.getTileEntity(pos);

        if (tileentityblockchanger != null)
        {
            IItemHandler iitemhandler = tileentityblockchanger.getHandler();

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

    /**
     * Called by ItemBlocks just before a block is actually set in the world, to allow for adjustments to the
     * IBlockstate
     */
    public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
    {
        return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite());
    }

    /**
     * Called by ItemBlocks after a block is set in the world, to allow post-place logic
     */
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
    {
        worldIn.setBlockState(pos, state.withProperty(FACING, placer.getHorizontalFacing().getOpposite()), 2);
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
     * Called on both Client and Server when World#addBlockEvent is called. On the Server, this may perform additional
     * changes to the world, like pistons replacing the block with an extended base. On the client, the update may
     * involve replacing tile entities, playing sounds, or performing other visual actions to reflect the server side
     * changes.
     */
    public boolean eventReceived(IBlockState state, World worldIn, BlockPos pos, int id, int param)
    {
        super.eventReceived(state, worldIn, pos, id, param);
        TileEntity tileentity = worldIn.getTileEntity(pos);
        return tileentity == null ? false : tileentity.receiveClientEvent(id, param);
    }
}
