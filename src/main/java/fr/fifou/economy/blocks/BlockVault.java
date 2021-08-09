package fr.fifou.economy.blocks;

import fr.fifou.economy.ModEconomy;
import fr.fifou.economy.blocks.tileentity.TileEntityBlockVault;
import fr.fifou.economy.blocks.tileentity.TileEntityBlockVault2by2;
import fr.fifou.economy.items.ItemsRegistery;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.items.IItemHandler;

public class BlockVault extends BlockContainer implements ITileEntityProvider
{
    public static final String NAME = "block_vault";

    public BlockVault()
    {
        super(Material.IRON);
        BlocksRegistery.setBlockName(this, "block_vault");
        this.setUnlocalizedName("block_vault");
        this.setCreativeTab(ModEconomy.tabEconomy);
        this.setResistance(20000.0F);
        this.setBlockUnbreakable();
    }

    /**
     * Called by ItemBlocks after a block is set in the world, to allow post-place logic
     */
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
    {
        TileEntityBlockVault tileentityblockvault = (TileEntityBlockVault)worldIn.getTileEntity(pos);
        tileentityblockvault.setOwner(placer.getUniqueID().toString());
        tileentityblockvault.ownerS = placer.getUniqueID().toString();
        int i = MathHelper.floor((double)(placer.rotationYaw * 4.0F / 360.0F) + 2.5D) & 3;
        tileentityblockvault.setDirection((byte)i);
        byte b0 = tileentityblockvault.getDirection();

        if (b0 == 0)
        {
            int j = tileentityblockvault.getPos().getX();
            int k = tileentityblockvault.getPos().getY();
            int l = tileentityblockvault.getPos().getZ();

            if (worldIn.getBlockState(new BlockPos(j + 1, k, l)).getBlock() == BlocksRegistery.BLOCK_VAULT && worldIn.getBlockState(new BlockPos(j + 1, k + 1, l)).getBlock() == BlocksRegistery.BLOCK_VAULT && worldIn.getBlockState(new BlockPos(j, k + 1, l)).getBlock() == BlocksRegistery.BLOCK_VAULT)
            {
                for (int j4 = 0; j4 <= 1; ++j4)
                {
                    for (int i8 = 0; i8 <= 1; ++i8)
                    {
                        worldIn.setBlockToAir(new BlockPos(j + j4, k + i8, l));
                    }
                }

                worldIn.setBlockState(new BlockPos(j + 1, k, l), BlocksRegistery.BLOCK_VAULT_2BY2.getDefaultState());
                TileEntityBlockVault2by2 tileentityblockvault2by23 = (TileEntityBlockVault2by2)worldIn.getTileEntity(new BlockPos(j + 1, k, l));
                tileentityblockvault2by23.setDirection((byte)0);
                tileentityblockvault2by23.setString(tileentityblockvault.getOwnerS());
            }
            else if (worldIn.getBlockState(new BlockPos(j - 1, k, l)).getBlock() == BlocksRegistery.BLOCK_VAULT && worldIn.getBlockState(new BlockPos(j - 1, k + 1, l)).getBlock() == BlocksRegistery.BLOCK_VAULT && worldIn.getBlockState(new BlockPos(j, k + 1, l)).getBlock() == BlocksRegistery.BLOCK_VAULT)
            {
                for (int i4 = 0; i4 <= 1; ++i4)
                {
                    for (int l7 = 0; l7 <= 1; ++l7)
                    {
                        worldIn.setBlockToAir(new BlockPos(j - i4, k + l7, l));
                    }
                }

                worldIn.setBlockState(new BlockPos(j, k, l), BlocksRegistery.BLOCK_VAULT_2BY2.getDefaultState());
                TileEntityBlockVault2by2 tileentityblockvault2by22 = (TileEntityBlockVault2by2)worldIn.getTileEntity(new BlockPos(j, k, l));
                tileentityblockvault2by22.setDirection((byte)0);
                tileentityblockvault2by22.setString(tileentityblockvault.getOwnerS());
            }
            else if (worldIn.getBlockState(new BlockPos(j - 1, k, l)).getBlock() == BlocksRegistery.BLOCK_VAULT && worldIn.getBlockState(new BlockPos(j - 1, k - 1, l)).getBlock() == BlocksRegistery.BLOCK_VAULT && worldIn.getBlockState(new BlockPos(j, k - 1, l)).getBlock() == BlocksRegistery.BLOCK_VAULT)
            {
                for (int l3 = 0; l3 <= 1; ++l3)
                {
                    for (int k7 = 0; k7 <= 1; ++k7)
                    {
                        worldIn.setBlockToAir(new BlockPos(j - l3, k - k7, l));
                    }
                }

                worldIn.setBlockState(new BlockPos(j, k - 1, l), BlocksRegistery.BLOCK_VAULT_2BY2.getDefaultState());
                TileEntityBlockVault2by2 tileentityblockvault2by21 = (TileEntityBlockVault2by2)worldIn.getTileEntity(new BlockPos(j, k - 1, l));
                tileentityblockvault2by21.setDirection((byte)0);
                tileentityblockvault2by21.setString(tileentityblockvault.getOwnerS());
            }
            else if (worldIn.getBlockState(new BlockPos(j + 1, k, l)).getBlock() == BlocksRegistery.BLOCK_VAULT && worldIn.getBlockState(new BlockPos(j + 1, k - 1, l)).getBlock() == BlocksRegistery.BLOCK_VAULT && worldIn.getBlockState(new BlockPos(j, k - 1, l)).getBlock() == BlocksRegistery.BLOCK_VAULT)
            {
                for (int i1 = 0; i1 <= 1; ++i1)
                {
                    for (int j1 = 0; j1 <= 1; ++j1)
                    {
                        worldIn.setBlockToAir(new BlockPos(j + i1, k - j1, l));
                    }
                }

                worldIn.setBlockState(new BlockPos(j + 1, k - 1, l), BlocksRegistery.BLOCK_VAULT_2BY2.getDefaultState());
                TileEntityBlockVault2by2 tileentityblockvault2by2 = (TileEntityBlockVault2by2)worldIn.getTileEntity(new BlockPos(j + 1, k - 1, l));
                tileentityblockvault2by2.setDirection((byte)0);
                tileentityblockvault2by2.setString(tileentityblockvault.getOwnerS());
            }
        }
        else if (b0 == 2)
        {
            int k1 = tileentityblockvault.getPos().getX();
            int j2 = tileentityblockvault.getPos().getY();
            int i3 = tileentityblockvault.getPos().getZ();

            if (worldIn.getBlockState(new BlockPos(k1 - 1, j2, i3)).getBlock() == BlocksRegistery.BLOCK_VAULT && worldIn.getBlockState(new BlockPos(k1 - 1, j2 + 1, i3)).getBlock() == BlocksRegistery.BLOCK_VAULT && worldIn.getBlockState(new BlockPos(k1, j2 + 1, i3)).getBlock() == BlocksRegistery.BLOCK_VAULT)
            {
                for (int j5 = 0; j5 <= 1; ++j5)
                {
                    for (int i9 = 0; i9 <= 1; ++i9)
                    {
                        worldIn.setBlockToAir(new BlockPos(k1 - j5, j2 + i9, i3));
                    }
                }

                worldIn.setBlockState(new BlockPos(k1 - 1, j2, i3), BlocksRegistery.BLOCK_VAULT_2BY2.getDefaultState());
                TileEntityBlockVault2by2 tileentityblockvault2by27 = (TileEntityBlockVault2by2)worldIn.getTileEntity(new BlockPos(k1 - 1, j2, i3));
                tileentityblockvault2by27.setDirection((byte)2);
                tileentityblockvault2by27.setString(tileentityblockvault.getOwnerS());
            }
            else if (worldIn.getBlockState(new BlockPos(k1 + 1, j2, i3)).getBlock() == BlocksRegistery.BLOCK_VAULT && worldIn.getBlockState(new BlockPos(k1 + 1, j2 + 1, i3)).getBlock() == BlocksRegistery.BLOCK_VAULT && worldIn.getBlockState(new BlockPos(k1, j2 + 1, i3)).getBlock() == BlocksRegistery.BLOCK_VAULT)
            {
                for (int i5 = 0; i5 <= 1; ++i5)
                {
                    for (int l8 = 0; l8 <= 1; ++l8)
                    {
                        worldIn.setBlockToAir(new BlockPos(k1 + i5, j2 + l8, i3));
                    }
                }

                worldIn.setBlockState(new BlockPos(k1, j2, i3), BlocksRegistery.BLOCK_VAULT_2BY2.getDefaultState());
                TileEntityBlockVault2by2 tileentityblockvault2by26 = (TileEntityBlockVault2by2)worldIn.getTileEntity(new BlockPos(k1, j2, i3));
                tileentityblockvault2by26.setDirection((byte)2);
                tileentityblockvault2by26.setString(tileentityblockvault.getOwnerS());
            }
            else if (worldIn.getBlockState(new BlockPos(k1 + 1, j2, i3)).getBlock() == BlocksRegistery.BLOCK_VAULT && worldIn.getBlockState(new BlockPos(k1 + 1, j2 - 1, i3)).getBlock() == BlocksRegistery.BLOCK_VAULT && worldIn.getBlockState(new BlockPos(k1, j2 - 1, i3)).getBlock() == BlocksRegistery.BLOCK_VAULT)
            {
                for (int l4 = 0; l4 <= 1; ++l4)
                {
                    for (int k8 = 0; k8 <= 1; ++k8)
                    {
                        worldIn.setBlockToAir(new BlockPos(k1 + l4, j2 - k8, i3));
                    }
                }

                worldIn.setBlockState(new BlockPos(k1, j2 - 1, i3), BlocksRegistery.BLOCK_VAULT_2BY2.getDefaultState());
                TileEntityBlockVault2by2 tileentityblockvault2by25 = (TileEntityBlockVault2by2)worldIn.getTileEntity(new BlockPos(k1, j2 - 1, i3));
                tileentityblockvault2by25.setDirection((byte)2);
                tileentityblockvault2by25.setString(tileentityblockvault.getOwnerS());
            }
            else if (worldIn.getBlockState(new BlockPos(k1 - 1, j2, i3)).getBlock() == BlocksRegistery.BLOCK_VAULT && worldIn.getBlockState(new BlockPos(k1 - 1, j2 - 1, i3)).getBlock() == BlocksRegistery.BLOCK_VAULT && worldIn.getBlockState(new BlockPos(k1, j2 - 1, i3)).getBlock() == BlocksRegistery.BLOCK_VAULT)
            {
                for (int k4 = 0; k4 <= 1; ++k4)
                {
                    for (int j8 = 0; j8 <= 1; ++j8)
                    {
                        worldIn.setBlockToAir(new BlockPos(k1 - k4, j2 - j8, i3));
                    }
                }

                worldIn.setBlockState(new BlockPos(k1 - 1, j2 - 1, i3), BlocksRegistery.BLOCK_VAULT_2BY2.getDefaultState());
                TileEntityBlockVault2by2 tileentityblockvault2by24 = (TileEntityBlockVault2by2)worldIn.getTileEntity(new BlockPos(k1 - 1, j2 - 1, i3));
                tileentityblockvault2by24.setDirection((byte)2);
                tileentityblockvault2by24.setString(tileentityblockvault.getOwnerS());
            }
        }
        else if (b0 == 1)
        {
            int l1 = tileentityblockvault.getPos().getX();
            int k2 = tileentityblockvault.getPos().getY();
            int j3 = tileentityblockvault.getPos().getZ();

            if (worldIn.getBlockState(new BlockPos(l1, k2, j3 + 1)).getBlock() == BlocksRegistery.BLOCK_VAULT && worldIn.getBlockState(new BlockPos(l1, k2 + 1, j3 + 1)).getBlock() == BlocksRegistery.BLOCK_VAULT && worldIn.getBlockState(new BlockPos(l1, k2 + 1, j3)).getBlock() == BlocksRegistery.BLOCK_VAULT)
            {
                for (int j6 = 0; j6 <= 1; ++j6)
                {
                    for (int i10 = 0; i10 <= 1; ++i10)
                    {
                        worldIn.setBlockToAir(new BlockPos(l1, k2 + j6, j3 + i10));
                    }
                }

                worldIn.setBlockState(new BlockPos(l1, k2, j3 + 1), BlocksRegistery.BLOCK_VAULT_2BY2.getDefaultState());
                TileEntityBlockVault2by2 tileentityblockvault2by211 = (TileEntityBlockVault2by2)worldIn.getTileEntity(new BlockPos(l1, k2, j3 + 1));
                tileentityblockvault2by211.setDirection((byte)1);
                tileentityblockvault2by211.setString(tileentityblockvault.getOwnerS());
            }
            else if (worldIn.getBlockState(new BlockPos(l1, k2, j3 - 1)).getBlock() == BlocksRegistery.BLOCK_VAULT && worldIn.getBlockState(new BlockPos(l1, k2 + 1, j3 - 1)).getBlock() == BlocksRegistery.BLOCK_VAULT && worldIn.getBlockState(new BlockPos(l1, k2 + 1, j3)).getBlock() == BlocksRegistery.BLOCK_VAULT)
            {
                for (int i6 = 0; i6 <= 1; ++i6)
                {
                    for (int l9 = 0; l9 <= 1; ++l9)
                    {
                        worldIn.setBlockToAir(new BlockPos(l1, k2 + i6, j3 - l9));
                    }
                }

                worldIn.setBlockState(new BlockPos(l1, k2, j3), BlocksRegistery.BLOCK_VAULT_2BY2.getDefaultState());
                TileEntityBlockVault2by2 tileentityblockvault2by210 = (TileEntityBlockVault2by2)worldIn.getTileEntity(new BlockPos(l1, k2, j3));
                tileentityblockvault2by210.setDirection((byte)1);
                tileentityblockvault2by210.setString(tileentityblockvault.getOwnerS());
            }
            else if (worldIn.getBlockState(new BlockPos(l1, k2, j3 - 1)).getBlock() == BlocksRegistery.BLOCK_VAULT && worldIn.getBlockState(new BlockPos(l1, k2 - 1, j3 - 1)).getBlock() == BlocksRegistery.BLOCK_VAULT && worldIn.getBlockState(new BlockPos(l1, k2 - 1, j3)).getBlock() == BlocksRegistery.BLOCK_VAULT)
            {
                for (int l5 = 0; l5 <= 1; ++l5)
                {
                    for (int k9 = 0; k9 <= 1; ++k9)
                    {
                        worldIn.setBlockToAir(new BlockPos(l1, k2 - l5, j3 - k9));
                    }
                }

                worldIn.setBlockState(new BlockPos(l1, k2 - 1, j3), BlocksRegistery.BLOCK_VAULT_2BY2.getDefaultState());
                TileEntityBlockVault2by2 tileentityblockvault2by29 = (TileEntityBlockVault2by2)worldIn.getTileEntity(new BlockPos(l1, k2 - 1, j3));
                tileentityblockvault2by29.setDirection((byte)1);
                tileentityblockvault2by29.setString(tileentityblockvault.getOwnerS());
            }
            else if (worldIn.getBlockState(new BlockPos(l1, k2, j3 + 1)).getBlock() == BlocksRegistery.BLOCK_VAULT && worldIn.getBlockState(new BlockPos(l1, k2 - 1, j3 + 1)).getBlock() == BlocksRegistery.BLOCK_VAULT && worldIn.getBlockState(new BlockPos(l1, k2 - 1, j3)).getBlock() == BlocksRegistery.BLOCK_VAULT)
            {
                for (int k5 = 0; k5 <= 1; ++k5)
                {
                    for (int j9 = 0; j9 <= 1; ++j9)
                    {
                        worldIn.setBlockToAir(new BlockPos(l1, k2 - k5, j3 + j9));
                    }
                }

                worldIn.setBlockState(new BlockPos(l1, k2 - 1, j3 + 1), BlocksRegistery.BLOCK_VAULT_2BY2.getDefaultState());
                TileEntityBlockVault2by2 tileentityblockvault2by28 = (TileEntityBlockVault2by2)worldIn.getTileEntity(new BlockPos(l1, k2 - 1, j3 + 1));
                tileentityblockvault2by28.setDirection((byte)1);
                tileentityblockvault2by28.setString(tileentityblockvault.getOwnerS());
            }
        }
        else if (b0 == 3)
        {
            int i2 = tileentityblockvault.getPos().getX();
            int l2 = tileentityblockvault.getPos().getY();
            int k3 = tileentityblockvault.getPos().getZ();

            if (worldIn.getBlockState(new BlockPos(i2, l2, k3 - 1)).getBlock() == BlocksRegistery.BLOCK_VAULT && worldIn.getBlockState(new BlockPos(i2, l2 + 1, k3 - 1)).getBlock() == BlocksRegistery.BLOCK_VAULT && worldIn.getBlockState(new BlockPos(i2, l2 + 1, k3)).getBlock() == BlocksRegistery.BLOCK_VAULT)
            {
                for (int j7 = 0; j7 <= 1; ++j7)
                {
                    for (int i11 = 0; i11 <= 1; ++i11)
                    {
                        worldIn.setBlockToAir(new BlockPos(i2, l2 + j7, k3 - i11));
                    }
                }

                worldIn.setBlockState(new BlockPos(i2, l2, k3 - 1), BlocksRegistery.BLOCK_VAULT_2BY2.getDefaultState());
                TileEntityBlockVault2by2 tileentityblockvault2by215 = (TileEntityBlockVault2by2)worldIn.getTileEntity(new BlockPos(i2, l2, k3 - 1));
                tileentityblockvault2by215.setDirection((byte)3);
                tileentityblockvault2by215.setString(tileentityblockvault.getOwnerS());
            }
            else if (worldIn.getBlockState(new BlockPos(i2, l2, k3 + 1)).getBlock() == BlocksRegistery.BLOCK_VAULT && worldIn.getBlockState(new BlockPos(i2, l2 + 1, k3 + 1)).getBlock() == BlocksRegistery.BLOCK_VAULT && worldIn.getBlockState(new BlockPos(i2, l2 + 1, k3)).getBlock() == BlocksRegistery.BLOCK_VAULT)
            {
                for (int i7 = 0; i7 <= 1; ++i7)
                {
                    for (int l10 = 0; l10 <= 1; ++l10)
                    {
                        worldIn.setBlockToAir(new BlockPos(i2, l2 + i7, k3 + l10));
                    }
                }

                worldIn.setBlockState(new BlockPos(i2, l2, k3), BlocksRegistery.BLOCK_VAULT_2BY2.getDefaultState());
                TileEntityBlockVault2by2 tileentityblockvault2by214 = (TileEntityBlockVault2by2)worldIn.getTileEntity(new BlockPos(i2, l2, k3));
                tileentityblockvault2by214.setDirection((byte)3);
                tileentityblockvault2by214.setString(tileentityblockvault.getOwnerS());
            }
            else if (worldIn.getBlockState(new BlockPos(i2, l2, k3 + 1)).getBlock() == BlocksRegistery.BLOCK_VAULT && worldIn.getBlockState(new BlockPos(i2, l2 - 1, k3 + 1)).getBlock() == BlocksRegistery.BLOCK_VAULT && worldIn.getBlockState(new BlockPos(i2, l2 - 1, k3)).getBlock() == BlocksRegistery.BLOCK_VAULT)
            {
                for (int l6 = 0; l6 <= 1; ++l6)
                {
                    for (int k10 = 0; k10 <= 1; ++k10)
                    {
                        worldIn.setBlockToAir(new BlockPos(i2, l2 - l6, k3 + k10));
                    }
                }

                worldIn.setBlockState(new BlockPos(i2, l2 - 1, k3), BlocksRegistery.BLOCK_VAULT_2BY2.getDefaultState());
                TileEntityBlockVault2by2 tileentityblockvault2by213 = (TileEntityBlockVault2by2)worldIn.getTileEntity(new BlockPos(i2, l2 - 1, k3));
                tileentityblockvault2by213.setDirection((byte)3);
                tileentityblockvault2by213.setString(tileentityblockvault.getOwnerS());
            }
            else if (worldIn.getBlockState(new BlockPos(i2, l2, k3 - 1)).getBlock() == BlocksRegistery.BLOCK_VAULT && worldIn.getBlockState(new BlockPos(i2, l2 - 1, k3 - 1)).getBlock() == BlocksRegistery.BLOCK_VAULT && worldIn.getBlockState(new BlockPos(i2, l2 - 1, k3)).getBlock() == BlocksRegistery.BLOCK_VAULT)
            {
                for (int k6 = 0; k6 <= 1; ++k6)
                {
                    for (int j10 = 0; j10 <= 1; ++j10)
                    {
                        worldIn.setBlockToAir(new BlockPos(i2, l2 - k6, k3 - j10));
                    }
                }

                worldIn.setBlockState(new BlockPos(i2, l2 - 1, k3 - 1), BlocksRegistery.BLOCK_VAULT_2BY2.getDefaultState());
                TileEntityBlockVault2by2 tileentityblockvault2by212 = (TileEntityBlockVault2by2)worldIn.getTileEntity(new BlockPos(i2, l2 - 1, k3 - 1));
                tileentityblockvault2by212.setDirection((byte)3);
                tileentityblockvault2by212.setString(tileentityblockvault.getOwnerS());
            }
        }
    }

    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        if (!worldIn.isRemote)
        {
            TileEntityBlockVault tileentityblockvault = (TileEntityBlockVault)worldIn.getTileEntity(pos);

            if (tileentityblockvault != null && tileentityblockvault.getOwnerS() != null)
            {
                String s = tileentityblockvault.getOwnerS();
                String s1 = playerIn.getUniqueID().toString();

                if (s.equals(s1))
                {
                    playerIn.openGui(ModEconomy.instance, 2, worldIn, pos.getX(), pos.getY(), pos.getZ());
                    tileentityblockvault.setIsOpen(true);
                    tileentityblockvault.markDirty();
                }
                else
                {
                    for (int i = 0; i < tileentityblockvault.getOthers().size(); ++i)
                    {
                        String s2 = ((String)tileentityblockvault.getOthers().get(i)).toString();

                        if (playerIn.getName().equals(s2))
                        {
                            playerIn.openGui(ModEconomy.instance, 2, worldIn, pos.getX(), pos.getY(), pos.getZ());
                            tileentityblockvault.markDirty();
                        }
                    }
                }
            }
        }

        return true;
    }

    public void onBlockClicked(World worldIn, BlockPos pos, EntityPlayer playerIn)
    {
        TileEntityBlockVault tileentityblockvault = (TileEntityBlockVault)worldIn.getTileEntity(pos);
        ItemStack itemstack = playerIn.getHeldItemMainhand();
        worldIn.getBlockState(pos);

        if (tileentityblockvault != null && itemstack.isItemEqual(new ItemStack(ItemsRegistery.ITEM_REMOVER)))
        {
            String s = tileentityblockvault.getOwnerS();
            String s1 = playerIn.getUniqueID().toString();

            if (s.equals(s1))
            {
                worldIn.destroyBlock(pos, true);
                worldIn.removeTileEntity(pos);
            }
        }
    }

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
        return EnumBlockRenderType.ENTITYBLOCK_ANIMATED;
    }

    /**
     * Returns a new instance of a block's tile entity class. Called on placing the block.
     */
    public TileEntity createNewTileEntity(World worldIn, int meta)
    {
        return new TileEntityBlockVault();
    }

    /**
     * Called serverside after this block is replaced with another in Chunk, but before the Tile Entity is updated
     */
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state)
    {
        TileEntityBlockVault tileentityblockvault = (TileEntityBlockVault)worldIn.getTileEntity(pos);

        if (tileentityblockvault != null)
        {
            IItemHandler iitemhandler = tileentityblockvault.getHandler();

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
