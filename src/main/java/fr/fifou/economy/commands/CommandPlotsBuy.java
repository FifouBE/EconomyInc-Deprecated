package fr.fifou.economy.commands;

import com.google.common.collect.Lists;
import fr.fifou.economy.capability.CapabilityLoading;
import fr.fifou.economy.capability.IMoney;
import fr.fifou.economy.world.saveddata.PlotsData;
import fr.fifou.economy.world.saveddata.PlotsWorldSavedData;
import java.util.List;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraft.world.storage.MapStorage;
import net.minecraftforge.server.permission.PermissionAPI;

public class CommandPlotsBuy extends CommandBase
{
    /**
     * Gets the name of the command
     */
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

    /**
     * Check if the given ICommandSender has permission to execute this command
     */
    public boolean checkPermission(MinecraftServer server, ICommandSender sender)
    {
        return sender instanceof EntityPlayer ? PermissionAPI.hasPermission((EntityPlayer)sender, "economy.command.plotbuy") : true;
    }

    /**
     * Gets the usage string for the command.
     */
	@Override
	public String getUsage(ICommandSender sender) {
		// TODO Auto-generated method stub
		return null;
	}

    /**
     * Callback for when the command is executed
     */
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException
    {
        boolean flag = false;
        int i = -1;

        if (!args[0].matches("buy"))
        {
            throw new WrongUsageException("commands.plot.usage", new Object[0]);
        }
        else
        {
            String s = args[1];
            MapStorage mapstorage = sender.getEntityWorld().getPerWorldStorage();
            World world = sender.getEntityWorld();
            PlotsWorldSavedData plotsworldsaveddata = (PlotsWorldSavedData)mapstorage.getOrLoadData(PlotsWorldSavedData.class, "economy_PlotsData");

            if (plotsworldsaveddata != null)
            {
                for (int j = 0; j < PlotsWorldSavedData.get(world).getListContainer().size(); ++j)
                {
                    PlotsData plotsdata = (PlotsData)PlotsWorldSavedData.get(world).getListContainer().get(j);

                    if (plotsdata != null && ((String)plotsdata.getList().get(0)).equals(s))
                    {
                        boolean flag1 = plotsdata.getBought();

                        if (!flag1)
                        {
                            i = j;
                            flag = true;
                        }
                    }
                }
            }

            if (flag && i != -1)
            {
                PlotsData plotsdata1 = (PlotsData)PlotsWorldSavedData.get(world).getListContainer().get(i);
                EntityPlayer entityplayer = world.getPlayerEntityByName(sender.getName());
                double d1 = ((IMoney)entityplayer.getCapability(CapabilityLoading.CAPABILITY_MONEY, (EnumFacing)null)).getMoney();

                if (d1 >= plotsdata1.price)
                {
                    plotsdata1.bought = true;
                    plotsdata1.owner = entityplayer.getUniqueID().toString();
                    PlotsWorldSavedData.get(world).markDirty();
                    double d0 = d1 - plotsdata1.price;
                    ((IMoney)entityplayer.getCapability(CapabilityLoading.CAPABILITY_MONEY, (EnumFacing)null)).setMoney(d0);
                    ((IMoney)entityplayer.getCapability(CapabilityLoading.CAPABILITY_MONEY, (EnumFacing)null)).sync(entityplayer);
                    entityplayer.sendMessage(new TextComponentString("Success."));
                    this.replaceSign(world, plotsdata1.xPosFirst, plotsdata1.yPos, plotsdata1.zPosFirst, plotsdata1.xPosSecond, plotsdata1.zPosSecond, plotsdata1.name, plotsdata1.owner);
                }
                else
                {
                    throw new WrongUsageException("commands.plot.notEnough", new Object[0]);
                }
            }
            else
            {
                throw new WrongUsageException("commands.plot.notExistOrBought", new Object[0]);
            }
        }
    }

    public List<String> getTabCompletionOptions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos)
    {
        switch (args.length)
        {
            case 1:
                return Lists.newArrayList("buy");

            default:
                return Lists.<String>newArrayList();
        }
    }

    public static Vec3d getCenter(double minX, double minY, double minZ, double maxX, double maxY, double maxZ)
    {
        return new Vec3d(minX + (maxX - minX) * 0.5D, minY + (maxY - minY) * 0.5D, minZ + (maxZ - minZ) * 0.5D);
    }

    public void replaceSign(World worldIn, int xPosFirst, int yPos, int zPosFirst, int xPosSecond, int zPosSecond, String name, String owner)
    {
        EntityPlayer entityplayer = worldIn.getPlayerEntityByUUID(UUID.fromString(owner));

        if (entityplayer != null)
        {
            Vec3d vec3d = getCenter((double)xPosFirst, (double)yPos, (double)zPosFirst, (double)xPosSecond, (double)yPos, (double)zPosSecond);
            BlockPos blockpos = new BlockPos(vec3d.x, vec3d.y, vec3d.z);
            worldIn.setBlockToAir(blockpos);
            worldIn.setBlockState(blockpos, Blocks.STANDING_SIGN.getDefaultState(), 2);
            TileEntity tileentity = new TileEntitySign();
            tileentity.validate();
            worldIn.setTileEntity(blockpos, tileentity);
            TileEntitySign tileentitysign = (TileEntitySign)worldIn.getTileEntity(blockpos);

            if (tileentitysign != null)
            {
                tileentitysign.signText[0] = (new TextComponentString("[" + name + "]")).setStyle((new Style()).setBold(Boolean.valueOf(true)).setColor(TextFormatting.BLUE));
                tileentitysign.signText[1] = (new TextComponentString("Owned by")).setStyle((new Style()).setBold(Boolean.valueOf(true)).setColor(TextFormatting.BLACK));
                tileentitysign.signText[2] = (new TextComponentString(entityplayer.getName())).setStyle((new Style()).setBold(Boolean.valueOf(true)).setColor(TextFormatting.BLACK));
                tileentitysign.signText[3] = (new TextComponentString("[SOLD]")).setStyle((new Style()).setBold(Boolean.valueOf(true)).setColor(TextFormatting.RED));
                tileentitysign.markDirty();
            }
        }
    }

}
