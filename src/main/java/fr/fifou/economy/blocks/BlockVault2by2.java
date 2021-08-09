package fr.fifou.economy.blocks;

import fr.fifou.economy.ModEconomy;
import fr.fifou.economy.blocks.tileentity.TileEntityBlockVault2by2;
import fr.fifou.economy.items.ItemsRegistery;
import java.util.Random;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.items.IItemHandler;

public class BlockVault2by2 extends BlockContainer implements ITileEntityProvider
{
    public static final String NAME = "block_vault2by2";

    public BlockVault2by2()
    {
        super(Material.IRON);
        BlocksRegistery.setBlockName(this, "block_vault2by2");
        this.setUnlocalizedName("block_vault2by2");
        this.setResistance(20000.0F);
        this.setBlockUnbreakable();
    }

    /**
     * Get the Item that this Block should drop when harvested.
     */
    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        return Item.getItemFromBlock(BlocksRegistery.BLOCK_VAULT);
    }

    public int quantityDropped(IBlockState state, int fortune, Random random)
    {
        return 4;
    }

    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess worldIn, BlockPos pos)
    {
        TileEntityBlockVault2by2 tileentityblockvault2by2 = (TileEntityBlockVault2by2)worldIn.getTileEntity(pos);

        if (tileentityblockvault2by2 != null)
        {
            switch (tileentityblockvault2by2.getDirection())
            {
                case 0:
                    return new AxisAlignedBB(-1.0D, 0.0D, 1.0D, 1.0D, 2.0D, 0.0D);

                case 1:
                    return new AxisAlignedBB(0.0D, 0.0D, 1.0D, 1.0D, 2.0D, -1.0D);

                case 2:
                    return new AxisAlignedBB(0.0D, 0.0D, 0.0D, 2.0D, 2.0D, 1.0D);

                case 3:
                    return new AxisAlignedBB(0.0D, 0.0D, 2.0D, 1.0D, 2.0D, 0.0D);
            }
        }

        return null;
    }

    /**
     * Returns a new instance of a block's tile entity class. Called on placing the block.
     */
    public TileEntity createNewTileEntity(World worldIn, int meta)
    {
        return new TileEntityBlockVault2by2();
    }

    /**
     * The type of render function called. MODEL for mixed tesr and static model, MODELBLOCK_ANIMATED for TESR-only,
     * LIQUID for vanilla liquids, INVISIBLE to skip all rendering
     */
    public EnumBlockRenderType getRenderType(IBlockState state)
    {
        return EnumBlockRenderType.ENTITYBLOCK_ANIMATED;
    }

    public boolean isOpaqueCube()
    {
        return false;
    }

    /**
     * Called serverside after this block is replaced with another in Chunk, but before the Tile Entity is updated
     */
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state)
    {
        TileEntityBlockVault2by2 tileentityblockvault2by2 = (TileEntityBlockVault2by2)worldIn.getTileEntity(pos);

        if (tileentityblockvault2by2 != null)
        {
            IItemHandler iitemhandler = tileentityblockvault2by2.getHandler();

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
     * Called by ItemBlocks after a block is set in the world, to allow post-place logic
     */
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
    {
        TileEntityBlockVault2by2 tileentityblockvault2by2 = (TileEntityBlockVault2by2)worldIn.getTileEntity(pos);
        tileentityblockvault2by2.setString(placer.getUniqueID().toString());
        tileentityblockvault2by2.ownerS = placer.getUniqueID().toString();
        int i = MathHelper.floor((double)(placer.rotationYaw * 4.0F / 360.0F) + 2.5D) & 3;
        tileentityblockvault2by2.setDirection((byte)i);
    }

    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        TileEntityBlockVault2by2 tileentityblockvault2by2 = (TileEntityBlockVault2by2)worldIn.getTileEntity(pos);

        if (tileentityblockvault2by2 != null && tileentityblockvault2by2.getOwnerS() != null)
        {
            String s = tileentityblockvault2by2.getOwnerS();
            String s1 = playerIn.getUniqueID().toString();

            if (s.equals(s1))
            {
                playerIn.openGui(ModEconomy.instance, 6, worldIn, pos.getX(), pos.getY(), pos.getZ());
            }
            else
            {
                for (int i = 0; i < tileentityblockvault2by2.getOthers().size(); ++i)
                {
                    String s2 = tileentityblockvault2by2.getOthers().get(i).toString();

                    if (playerIn.getName().equals(s2))
                    {
                        playerIn.openGui(ModEconomy.instance, 6, worldIn, pos.getX(), pos.getY(), pos.getZ());
                    }
                }
            }
        }

        return true;
    }

    public void onBlockClicked(World worldIn, BlockPos pos, EntityPlayer playerIn)
    {
        TileEntityBlockVault2by2 tileentityblockvault2by2 = (TileEntityBlockVault2by2)worldIn.getTileEntity(pos);
        ItemStack itemstack = playerIn.getHeldItemMainhand();
        worldIn.getBlockState(pos);

        if (tileentityblockvault2by2 != null && itemstack.isItemEqual(new ItemStack(ItemsRegistery.ITEM_REMOVER)))
        {
            String s = tileentityblockvault2by2.getOwnerS();
            String s1 = playerIn.getUniqueID().toString();

            if (s.equals(s1))
            {
                worldIn.destroyBlock(pos, true);
                worldIn.removeTileEntity(pos);
            }
        }
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
