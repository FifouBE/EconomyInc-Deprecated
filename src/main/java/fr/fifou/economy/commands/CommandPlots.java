package fr.fifou.economy.commands;

import com.google.common.collect.Lists;
import fr.fifou.economy.world.saveddata.ChunksWorldSavedData;
import fr.fifou.economy.world.saveddata.PlotsChunkData;
import fr.fifou.economy.world.saveddata.PlotsData;
import fr.fifou.economy.world.saveddata.PlotsWorldSavedData;
import java.util.ArrayList;
import java.util.List;
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
import net.minecraft.util.IProgressUpdate;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.MinecraftException;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.storage.MapStorage;
import net.minecraftforge.server.permission.PermissionAPI;

public class CommandPlots extends CommandBase
{
    public static ITextComponent[] signText;

    /**
     * Gets the name of the command
     */
    @Override
    public String getName()
    {
        return "plot";
    }

    /**
     * Check if the given ICommandSender has permission to execute this command
     */
    public boolean checkPermission(MinecraftServer server, ICommandSender sender)
    {
        return sender instanceof EntityPlayer ? PermissionAPI.hasPermission((EntityPlayer)sender, "economy.command.plot") : true;
    }

    /**
     * Gets the usage string for the command.
     */
    @Override
    public String getUsage(ICommandSender arg0)
    {
        return "commands.plot.usage";
    }

    /**
     * Callback for when the command is executed
     */
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException
    {
        this.createCommand(server, sender, args);
    }

    public List<String> getTabCompletionOptions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos)
    {
        switch (args.length)
        {
            case 1:
                return Lists.newArrayList("create", "remove");

            case 2:
                return Lists.newArrayList(String.valueOf(sender.getPosition().getX()));

            case 3:
                return Lists.newArrayList(String.valueOf(sender.getPosition().getZ()));

            case 4:
                return Lists.newArrayList(String.valueOf(sender.getPosition().getX()));

            case 5:
                return Lists.newArrayList(String.valueOf(sender.getPosition().getZ()));

            case 6:
                return Lists.newArrayList(String.valueOf(sender.getPosition().getY()));

            default:
                return Lists.<String>newArrayList();
        }
    }

    public void createCommand(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException
    {
        boolean flag = true;
        boolean flag1 = false;
        int i = -1;

        if (args.length == 8)
        {
            if (!args[0].matches("create"))
            {
                throw new WrongUsageException("commands.plot.usage", new Object[0]);
            }

            int j = Integer.valueOf(args[1]).intValue();
            int k = Integer.valueOf(args[2]).intValue();
            int l = Integer.valueOf(args[3]).intValue();
            int i1 = Integer.valueOf(args[4]).intValue();
            int j1 = Integer.valueOf(args[5]).intValue();
            String s = args[6];
            double d0 = Double.valueOf(args[7]).doubleValue();

            if (Math.abs(l - j) >= 26 || Math.abs(i1 - k) >= 26)
            {
                throw new CommandException("commands.plot.tooManyBlocks", new Object[] {Integer.valueOf(25)});
            }

            MapStorage mapstorage = sender.getEntityWorld().getPerWorldStorage();
            World world = server.getEntityWorld();
            PlotsWorldSavedData plotsworldsaveddata = (PlotsWorldSavedData)mapstorage.getOrLoadData(PlotsWorldSavedData.class, "economy_PlotsData");

            if (plotsworldsaveddata != null)
            {
                for (int k1 = 0; k1 < PlotsWorldSavedData.get(world).getListContainer().size(); ++k1)
                {
                    PlotsData plotsdata = (PlotsData)PlotsWorldSavedData.get(world).getListContainer().get(k1);

                    if (plotsdata != null && ((String)plotsdata.getList().get(0)).equals(args[6]))
                    {
                        flag = false;
                    }
                }
            }

            if (!flag)
            {
                throw new CommandException("commands.plot.nameAlreadyExist", new Object[] {Integer.valueOf(25)});
            }

            String s2 = sender.getName();
            createData(world, s, sender, j, k, l, i1, j1, d0);
            saveAll(server, sender, false);
        }
        else if (args.length == 2 && args[0].matches("remove"))
        {
            String s1 = args[1];
            MapStorage mapstorage1 = sender.getEntityWorld().getPerWorldStorage();
            World world1 = server.getEntityWorld();
            PlotsWorldSavedData plotsworldsaveddata1 = (PlotsWorldSavedData)mapstorage1.getOrLoadData(PlotsWorldSavedData.class, "economy_PlotsData");

            if (plotsworldsaveddata1 != null)
            {
                for (int l1 = 0; l1 < PlotsWorldSavedData.get(world1).getListContainer().size(); ++l1)
                {
                    PlotsData plotsdata1 = (PlotsData)PlotsWorldSavedData.get(world1).getListContainer().get(l1);

                    if (plotsdata1 != null && ((String)plotsdata1.getList().get(0)).equals(s1))
                    {
                        i = l1;
                        flag1 = true;
                    }
                }
            }

            if (flag1 && i != -1)
            {
                PlotsWorldSavedData.get(world1).getListContainer().remove(i);
                PlotsWorldSavedData.get(world1).markDirty();
                saveAll(server, sender, false);
            }
        }
    }

    public static void createData(World worldIn, String name, ICommandSender sender, int xPosFirst, int zPosFirst, int xPosSecond, int zPosSecond, int yPos, double priceIn) throws CommandException
    {
        EntityPlayer entityplayer = worldIn.getPlayerEntityByName(sender.getName());
        List<ChunkPos> list = calculatingChunks(worldIn, xPosFirst, zPosFirst, xPosSecond, zPosSecond, yPos);
        PlotsData plotsdata = new PlotsData(name, entityplayer.getUniqueID().toString(), xPosFirst, zPosFirst, xPosSecond, zPosSecond, yPos, priceIn, false);
        PlotsChunkData plotschunkdata = new PlotsChunkData(list);
        PlotsWorldSavedData.get(worldIn).getListContainer().add(plotsdata);
        PlotsWorldSavedData.get(worldIn).markDirty();
        ChunksWorldSavedData.get(worldIn).getListContainer().add(plotschunkdata);
        ChunksWorldSavedData.get(worldIn).markDirty();
        createBorders(worldIn, name, entityplayer.getName(), xPosFirst, zPosFirst, xPosSecond, zPosSecond, yPos, priceIn);
        System.out.println((Object)entityplayer);
    }

    public static List<ChunkPos> calculatingChunks(World worldIn, int xPosFirst, int zPosFirst, int xPosSecond, int zPosSecond, int yPos)
    {
        Iterable<BlockPos> iterable = BlockPos.getAllInBox(new BlockPos(xPosFirst, yPos, zPosFirst), new BlockPos(xPosSecond, yPos, zPosSecond));
        List<ChunkPos> list = new ArrayList<ChunkPos>();
        int i;
        int k;

        if (xPosFirst < xPosSecond)
        {
            i = Math.floorDiv(xPosFirst, 16);
            k = Math.floorDiv(xPosSecond, 16);
        }
        else
        {
            i = Math.floorDiv(xPosSecond, 16);
            k = Math.floorDiv(xPosFirst, 16);
        }

        int j;
        int l;

        if (zPosFirst < zPosSecond)
        {
            j = Math.floorDiv(zPosFirst, 16);
            l = Math.floorDiv(zPosSecond, 16);
        }
        else
        {
            j = Math.floorDiv(zPosSecond, 16);
            l = Math.floorDiv(zPosFirst, 16);
        }

        for (int i1 = i; i1 <= k; ++i1)
        {
            for (int j1 = j; j1 <= l; ++j1)
            {
                Chunk chunk = worldIn.getChunkFromChunkCoords(i1, j1);
                list.add(chunk.getPos());
            }
        }

        return list;
    }

    public static void createBorders(World worldIn, String name, String senderName, int xPosFirst, int zPosFirst, int xPosSecond, int zPosSecond, int yPos, double priceIn)
    {
        AxisAlignedBB axisalignedbb = new AxisAlignedBB(new BlockPos(xPosFirst, yPos, zPosFirst), new BlockPos(xPosSecond, yPos, zPosSecond));
        AxisAlignedBB axisalignedbb1 = axisalignedbb.expand(1.0D, 0.0D, 1.0D);
        Vec3d vec3d = getCenter((double)xPosFirst, (double)yPos, (double)zPosFirst, (double)xPosSecond, (double)yPos, (double)zPosSecond);
        BlockPos blockpos = new BlockPos(vec3d.x, vec3d.y, vec3d.z);
        worldIn.setBlockToAir(blockpos);
        Iterable<BlockPos> iterable = BlockPos.getAllInBox(new BlockPos(axisalignedbb1.minX, (double)yPos, axisalignedbb1.minZ), new BlockPos(axisalignedbb1.maxX, (double)yPos, axisalignedbb1.maxZ));
        Iterable<BlockPos> iterable1 = BlockPos.getAllInBox(new BlockPos(axisalignedbb.minX, (double)yPos, axisalignedbb.minZ), new BlockPos(axisalignedbb.maxX, (double)yPos, axisalignedbb.maxZ));

        for (BlockPos blockpos1 : iterable)
        {
            worldIn.setBlockState(blockpos1, Blocks.STONE_SLAB.getDefaultState());
        }

        for (BlockPos blockpos2 : iterable1)
        {
            worldIn.setBlockState(blockpos2, Blocks.AIR.getDefaultState());
        }

        worldIn.setBlockState(blockpos, Blocks.STANDING_SIGN.getDefaultState(), 2);
        TileEntity tileentity = new TileEntitySign();
        tileentity.validate();
        worldIn.setTileEntity(blockpos, tileentity);
        TileEntitySign tileentitysign = (TileEntitySign)worldIn.getTileEntity(blockpos);

        if (tileentitysign != null)
        {
            tileentitysign.signText[0] = (new TextComponentString("[" + name + "]")).setStyle((new Style()).setBold(Boolean.valueOf(true)).setColor(TextFormatting.BLUE));
            tileentitysign.signText[1] = (new TextComponentString(senderName)).setStyle((new Style()).setBold(Boolean.valueOf(true)).setColor(TextFormatting.BLACK));
            tileentitysign.signText[2] = (new TextComponentString(String.valueOf(priceIn))).setStyle((new Style()).setBold(Boolean.valueOf(true)).setColor(TextFormatting.BLACK));
            tileentitysign.signText[3] = (new TextComponentString("[BUY]")).setStyle((new Style()).setBold(Boolean.valueOf(true)).setColor(TextFormatting.GREEN));
            tileentitysign.markDirty();
        }
    }

    public static Vec3d getCenter(double minX, double minY, double minZ, double maxX, double maxY, double maxZ)
    {
        return new Vec3d(minX + (maxX - minX) * 0.5D, minY + (maxY - minY) * 0.5D, minZ + (maxZ - minZ) * 0.5D);
    }

    private static void saveAll(MinecraftServer server, ICommandSender sender, boolean flush)
    {
        sender.sendMessage(new TextComponentTranslation("commands.save.start", new Object[0]));

        if (server.getPlayerList() != null)
        {
            server.getPlayerList().saveAllPlayerData();
        }

        try
        {
            for (int i = 0; i < server.worlds.length; ++i)
            {
                if (server.worlds[i] != null)
                {
                    WorldServer worldserver = server.worlds[i];
                    boolean flag = worldserver.disableLevelSaving;
                    worldserver.disableLevelSaving = false;
                    worldserver.saveAllChunks(true, (IProgressUpdate)null);
                    worldserver.disableLevelSaving = flag;
                }
            }

            if (flush)
            {
                sender.sendMessage(new TextComponentTranslation("commands.save.flushStart", new Object[0]));

                for (int j = 0; j < server.worlds.length; ++j)
                {
                    if (server.worlds[j] != null)
                    {
                        WorldServer worldserver1 = server.worlds[j];
                        boolean flag1 = worldserver1.disableLevelSaving;
                        worldserver1.disableLevelSaving = false;
                        worldserver1.saveAllChunks(false, null);
                        worldserver1.disableLevelSaving = flag1;
                    }
                }

                sender.sendMessage(new TextComponentTranslation("commands.save.flushEnd", new Object[0]));
            }
        }
        catch (MinecraftException var6)
        {
            System.out.println("Exception while loading file datas.");
        }
    }
}
