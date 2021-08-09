package fr.fifou.economy.blocks;

import fr.fifou.economy.ModEconomy;
import fr.fifou.economy.blocks.tileentity.TileEntityBlockBills;
import fr.fifou.economy.items.ItemsRegistery;
import net.minecraft.block.Block;
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

public class BlockBills extends Block implements ITileEntityProvider
{
    public static final String NAME = "block_bills";
    public EntityItem item;

    public BlockBills()
    {
        super(Material.CLOTH);
        BlocksRegistery.setBlockName(this, "block_bills");
        this.setUnlocalizedName("block_bills");
        this.setCreativeTab(ModEconomy.tabEconomy);
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
        return new TileEntityBlockBills();
    }

    /**
     * Called serverside after this block is replaced with another in Chunk, but before the Tile Entity is updated
     */
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state)
    {
        TileEntityBlockBills tileentityblockbills = (TileEntityBlockBills)worldIn.getTileEntity(pos);

        for (int i = 0; i < tileentityblockbills.getNumbBills(); ++i)
        {
            this.checkBillRefForDrop(tileentityblockbills, worldIn, pos);
            float f = 0.1F;
            float f1 = worldIn.rand.nextFloat() - 0.5F;
            float f2 = worldIn.rand.nextFloat() - 0.5F;
            float f3 = worldIn.rand.nextFloat() - 0.5F;
            this.item.motionX = (double)(f1 * f);
            this.item.motionY = (double)(f2 * f);
            this.item.motionZ = (double)(f3 * f);
            worldIn.spawnEntity(this.item);
        }

        worldIn.removeTileEntity(pos);
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

    /**
     * Called by ItemBlocks after a block is set in the world, to allow post-place logic
     */
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
    {
        TileEntityBlockBills tileentityblockbills = (TileEntityBlockBills)worldIn.getTileEntity(pos);

        if (!worldIn.isRemote)
        {
            int i = MathHelper.floor((double)(placer.rotationYaw * 4.0F / 360.0F) + 2.5D) & 3;
            tileentityblockbills.setDirection((byte)i);
            tileentityblockbills.markDirty();
        }
    }

    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        if (!worldIn.isRemote)
        {
            TileEntityBlockBills tileentityblockbills = (TileEntityBlockBills)worldIn.getTileEntity(pos);

            if (tileentityblockbills.getNumbBills() != 64)
            {
                String s = playerIn.getHeldItem(hand).getUnlocalizedName();

                if (tileentityblockbills.getNumbBills() == 0)
                {
                    if (s.equals("item.item_oneb") || s.equals("item.item_fiveb") || s.equals("item.item_tenb") || s.equals("item.item_twentyb") || s.equals("item.item_fiftybe") || s.equals("item.item_hundreedb") || s.equals("item.item_twohundreedb") || s.equals("item.item_fivehundreedb"))
                    {
                        this.checkBillRef(tileentityblockbills, worldIn, playerIn, hand);
                        tileentityblockbills.addBill();
                        playerIn.getHeldItem(hand).setCount(playerIn.getHeldItemMainhand().getCount() - 1);
                        tileentityblockbills.markDirty();
                    }
                }
                else if (tileentityblockbills.getBill().equals(s))
                {
                    tileentityblockbills.addBill();
                    playerIn.getHeldItem(hand).setCount(playerIn.getHeldItemMainhand().getCount() - 1);
                    tileentityblockbills.markDirty();
                }
            }
        }

        return true;
    }

    public void checkBillRef(TileEntityBlockBills te, World worldIn, EntityPlayer playerIn, EnumHand hand)
    {
        if (!worldIn.isRemote)
        {
            String s = playerIn.getHeldItem(hand).getUnlocalizedName();
            byte b0 = -1;

            switch (s.hashCode())
            {
                case -2095573238:
                    if (s.equals("item.item_fivehundreedb"))
                    {
                        b0 = 7;
                    }

                    break;

                case -1956797619:
                    if (s.equals("item.item_oneb"))
                    {
                        b0 = 0;
                    }

                    break;

                case -1956657034:
                    if (s.equals("item.item_tenb"))
                    {
                        b0 = 2;
                    }

                    break;

                case -539628161:
                    if (s.equals("item.item_fiveb"))
                    {
                        b0 = 1;
                    }

                    break;

                case 1033927264:
                    if (s.equals("item.item_twentyb"))
                    {
                        b0 = 3;
                    }

                    break;

                case 1094075866:
                    if (s.equals("item.item_fiftybe"))
                    {
                        b0 = 4;
                    }

                    break;

                case 1452084658:
                    if (s.equals("item.item_twohundreedb"))
                    {
                        b0 = 6;
                    }

                    break;

                case 1599489276:
                    if (s.equals("item.item_hundreedb"))
                    {
                        b0 = 5;
                    }
            }

            switch (b0)
            {
                case 0:
                    te.setBillRef("item.item_oneb");
                    te.markDirty();
                    break;

                case 1:
                    te.setBillRef("item.item_fiveb");
                    te.markDirty();
                    break;

                case 2:
                    te.setBillRef("item.item_tenb");
                    te.markDirty();
                    break;

                case 3:
                    te.setBillRef("item.item_twentyb");
                    te.markDirty();
                    break;

                case 4:
                    te.setBillRef("item.item_fiftybe");
                    te.markDirty();
                    break;

                case 5:
                    te.setBillRef("item.item_hundreedb");
                    te.markDirty();
                    break;

                case 6:
                    te.setBillRef("item.item_twohundreedb");
                    te.markDirty();
                    break;

                case 7:
                    te.setBillRef("item.item_fivehundreedb");
                    te.markDirty();
                    break;

                default:
                    te.setBillRef("item.item_zerob");
                    te.markDirty();
            }
        }
    }

    public void checkBillRefForDrop(TileEntityBlockBills te, World worldIn, BlockPos pos)
    {
        if (!worldIn.isRemote)
        {
            String s = te.getBill();
            byte b0 = -1;

            switch (s.hashCode())
            {
                case -2095573238:
                    if (s.equals("item.item_fivehundreedb"))
                    {
                        b0 = 7;
                    }

                    break;

                case -1956797619:
                    if (s.equals("item.item_oneb"))
                    {
                        b0 = 0;
                    }

                    break;

                case -1956657034:
                    if (s.equals("item.item_tenb"))
                    {
                        b0 = 2;
                    }

                    break;

                case -539628161:
                    if (s.equals("item.item_fiveb"))
                    {
                        b0 = 1;
                    }

                    break;

                case 1033927264:
                    if (s.equals("item.item_twentyb"))
                    {
                        b0 = 3;
                    }

                    break;

                case 1094075866:
                    if (s.equals("item.item_fiftybe"))
                    {
                        b0 = 4;
                    }

                    break;

                case 1452084658:
                    if (s.equals("item.item_twohundreedb"))
                    {
                        b0 = 6;
                    }

                    break;

                case 1599489276:
                    if (s.equals("item.item_hundreedb"))
                    {
                        b0 = 5;
                    }
            }

            switch (b0)
            {
                case 0:
                    this.item = new EntityItem(worldIn, (double)pos.getX() + 0.5D, (double)pos.getY() + 0.5D, (double)pos.getZ() + 0.5D, new ItemStack(ItemsRegistery.ITEM_ONEB));
                    break;

                case 1:
                    this.item = new EntityItem(worldIn, (double)pos.getX() + 0.5D, (double)pos.getY() + 0.5D, (double)pos.getZ() + 0.5D, new ItemStack(ItemsRegistery.ITEM_FIVEB));
                    break;

                case 2:
                    this.item = new EntityItem(worldIn, (double)pos.getX() + 0.5D, (double)pos.getY() + 0.5D, (double)pos.getZ() + 0.5D, new ItemStack(ItemsRegistery.ITEM_TENB));
                    break;

                case 3:
                    this.item = new EntityItem(worldIn, (double)pos.getX() + 0.5D, (double)pos.getY() + 0.5D, (double)pos.getZ() + 0.5D, new ItemStack(ItemsRegistery.ITEM_TWENTYB));
                    break;

                case 4:
                    this.item = new EntityItem(worldIn, (double)pos.getX() + 0.5D, (double)pos.getY() + 0.5D, (double)pos.getZ() + 0.5D, new ItemStack(ItemsRegistery.ITEM_FIFTYB));
                    break;

                case 5:
                    this.item = new EntityItem(worldIn, (double)pos.getX() + 0.5D, (double)pos.getY() + 0.5D, (double)pos.getZ() + 0.5D, new ItemStack(ItemsRegistery.ITEM_HUNDREEDB));
                    break;

                case 6:
                    this.item = new EntityItem(worldIn, (double)pos.getX() + 0.5D, (double)pos.getY() + 0.5D, (double)pos.getZ() + 0.5D, new ItemStack(ItemsRegistery.ITEM_TWOHUNDREEDB));
                    break;

                case 7:
                    this.item = new EntityItem(worldIn, (double)pos.getX() + 0.5D, (double)pos.getY() + 0.5D, (double)pos.getZ() + 0.5D, new ItemStack(ItemsRegistery.ITEM_FIVEHUNDREEDB));
            }
        }
    }
}
