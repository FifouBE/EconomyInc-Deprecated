package fr.fifou.economy.commands;

import com.google.common.collect.Lists;
import fr.fifou.economy.capability.CapabilityLoading;
import fr.fifou.economy.capability.IMoney;
import java.util.Collections;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.PlayerNotFoundException;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.server.permission.PermissionAPI;

public class CommandBalance extends CommandBase
{
    /**
     * Gets the name of the command
     */
	@Override
    public String getName()
    {
        return "balance";
    }

    /**
     * Check if the given ICommandSender has permission to execute this command
     */
    public boolean checkPermission(MinecraftServer server, ICommandSender sender)
    {
        return sender instanceof EntityPlayer ? PermissionAPI.hasPermission((EntityPlayer)sender, "economy.command.balance") : true;
    }

    /**
     * Gets the usage string for the command.
     */
    @Override
    public String getUsage(ICommandSender arg0)
    {
        return "commands.balance.usage";
    }

    /**
     * Callback for when the command is executed
     */
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException
    {
        if (args.length <= 0)
        {
            throw new WrongUsageException("commands.balance.usage", new Object[0]);
        }
        else
        {
            if (args[0].matches("check"))
            {
                EntityPlayerMP entityplayermp = server.getPlayerList().getPlayerByUsername(args[1]);
                int i = 0;

                if (entityplayermp == null)
                {
                    throw new PlayerNotFoundException("commands.generic.player.notFound", new Object[] {args[1]});
                }

                notifyCommandListener(sender, this, "Funds of " + entityplayermp.getName() + " : " + ((IMoney)entityplayermp.getCapability(CapabilityLoading.CAPABILITY_MONEY, (EnumFacing)null)).getMoney(), new Object[] {entityplayermp.getName()});
            }
            else if (args[0].matches("add"))
            {
                EntityPlayerMP entityplayermp1 = server.getPlayerList().getPlayerByUsername(args[1]);

                if (entityplayermp1 == null)
                {
                    throw new PlayerNotFoundException("commands.generic.player.notFound", new Object[] {args[1]});
                }

                switch (Integer.valueOf(args[2]).intValue())
                {
                    case 5:
                        ((IMoney)entityplayermp1.getCapability(CapabilityLoading.CAPABILITY_MONEY, (EnumFacing)null)).setMoney(((IMoney)entityplayermp1.getCapability(CapabilityLoading.CAPABILITY_MONEY, (EnumFacing)null)).getMoney() + 5.0D);
                        ((IMoney)entityplayermp1.getCapability(CapabilityLoading.CAPABILITY_MONEY, (EnumFacing)null)).sync(entityplayermp1);
                        break;

                    case 10:
                        ((IMoney)entityplayermp1.getCapability(CapabilityLoading.CAPABILITY_MONEY, (EnumFacing)null)).setMoney(((IMoney)entityplayermp1.getCapability(CapabilityLoading.CAPABILITY_MONEY, (EnumFacing)null)).getMoney() + 10.0D);
                        ((IMoney)entityplayermp1.getCapability(CapabilityLoading.CAPABILITY_MONEY, (EnumFacing)null)).sync(entityplayermp1);
                        break;

                    case 20:
                        ((IMoney)entityplayermp1.getCapability(CapabilityLoading.CAPABILITY_MONEY, (EnumFacing)null)).setMoney(((IMoney)entityplayermp1.getCapability(CapabilityLoading.CAPABILITY_MONEY, (EnumFacing)null)).getMoney() + 20.0D);
                        ((IMoney)entityplayermp1.getCapability(CapabilityLoading.CAPABILITY_MONEY, (EnumFacing)null)).sync(entityplayermp1);
                        break;

                    case 50:
                        ((IMoney)entityplayermp1.getCapability(CapabilityLoading.CAPABILITY_MONEY, (EnumFacing)null)).setMoney(((IMoney)entityplayermp1.getCapability(CapabilityLoading.CAPABILITY_MONEY, (EnumFacing)null)).getMoney() + 50.0D);
                        ((IMoney)entityplayermp1.getCapability(CapabilityLoading.CAPABILITY_MONEY, (EnumFacing)null)).sync(entityplayermp1);
                        break;

                    case 100:
                        ((IMoney)entityplayermp1.getCapability(CapabilityLoading.CAPABILITY_MONEY, (EnumFacing)null)).setMoney(((IMoney)entityplayermp1.getCapability(CapabilityLoading.CAPABILITY_MONEY, (EnumFacing)null)).getMoney() + 100.0D);
                        ((IMoney)entityplayermp1.getCapability(CapabilityLoading.CAPABILITY_MONEY, (EnumFacing)null)).sync(entityplayermp1);
                        break;

                    case 200:
                        ((IMoney)entityplayermp1.getCapability(CapabilityLoading.CAPABILITY_MONEY, (EnumFacing)null)).setMoney(((IMoney)entityplayermp1.getCapability(CapabilityLoading.CAPABILITY_MONEY, (EnumFacing)null)).getMoney() + 200.0D);
                        ((IMoney)entityplayermp1.getCapability(CapabilityLoading.CAPABILITY_MONEY, (EnumFacing)null)).sync(entityplayermp1);
                        break;

                    case 500:
                        ((IMoney)entityplayermp1.getCapability(CapabilityLoading.CAPABILITY_MONEY, (EnumFacing)null)).setMoney(((IMoney)entityplayermp1.getCapability(CapabilityLoading.CAPABILITY_MONEY, (EnumFacing)null)).getMoney() + 500.0D);
                        ((IMoney)entityplayermp1.getCapability(CapabilityLoading.CAPABILITY_MONEY, (EnumFacing)null)).sync(entityplayermp1);
                        break;

                    default:
                        throw new WrongUsageException("commands.balance.funds", new Object[0]);
                }
            }
            else
            {
                if (!args[0].matches("remove"))
                {
                    throw new WrongUsageException("commands.balance.usage", new Object[0]);
                }

                EntityPlayerMP entityplayermp2 = server.getPlayerList().getPlayerByUsername(args[1]);

                if (entityplayermp2 == null)
                {
                    throw new PlayerNotFoundException("commands.generic.player.notFound", new Object[] {args[1]});
                }

                switch (Integer.valueOf(args[2]).intValue())
                {
                    case 5:
                        ((IMoney)entityplayermp2.getCapability(CapabilityLoading.CAPABILITY_MONEY, (EnumFacing)null)).setMoney(((IMoney)entityplayermp2.getCapability(CapabilityLoading.CAPABILITY_MONEY, (EnumFacing)null)).getMoney() - 5.0D);
                        ((IMoney)entityplayermp2.getCapability(CapabilityLoading.CAPABILITY_MONEY, (EnumFacing)null)).sync(entityplayermp2);
                        break;

                    case 10:
                        ((IMoney)entityplayermp2.getCapability(CapabilityLoading.CAPABILITY_MONEY, (EnumFacing)null)).setMoney(((IMoney)entityplayermp2.getCapability(CapabilityLoading.CAPABILITY_MONEY, (EnumFacing)null)).getMoney() - 10.0D);
                        ((IMoney)entityplayermp2.getCapability(CapabilityLoading.CAPABILITY_MONEY, (EnumFacing)null)).sync(entityplayermp2);
                        break;

                    case 20:
                        ((IMoney)entityplayermp2.getCapability(CapabilityLoading.CAPABILITY_MONEY, (EnumFacing)null)).setMoney(((IMoney)entityplayermp2.getCapability(CapabilityLoading.CAPABILITY_MONEY, (EnumFacing)null)).getMoney() - 20.0D);
                        ((IMoney)entityplayermp2.getCapability(CapabilityLoading.CAPABILITY_MONEY, (EnumFacing)null)).sync(entityplayermp2);
                        break;

                    case 50:
                        ((IMoney)entityplayermp2.getCapability(CapabilityLoading.CAPABILITY_MONEY, (EnumFacing)null)).setMoney(((IMoney)entityplayermp2.getCapability(CapabilityLoading.CAPABILITY_MONEY, (EnumFacing)null)).getMoney() - 50.0D);
                        ((IMoney)entityplayermp2.getCapability(CapabilityLoading.CAPABILITY_MONEY, (EnumFacing)null)).sync(entityplayermp2);
                        break;

                    case 100:
                        ((IMoney)entityplayermp2.getCapability(CapabilityLoading.CAPABILITY_MONEY, (EnumFacing)null)).setMoney(((IMoney)entityplayermp2.getCapability(CapabilityLoading.CAPABILITY_MONEY, (EnumFacing)null)).getMoney() - 100.0D);
                        ((IMoney)entityplayermp2.getCapability(CapabilityLoading.CAPABILITY_MONEY, (EnumFacing)null)).sync(entityplayermp2);
                        break;

                    case 200:
                        ((IMoney)entityplayermp2.getCapability(CapabilityLoading.CAPABILITY_MONEY, (EnumFacing)null)).setMoney(((IMoney)entityplayermp2.getCapability(CapabilityLoading.CAPABILITY_MONEY, (EnumFacing)null)).getMoney() - 200.0D);
                        ((IMoney)entityplayermp2.getCapability(CapabilityLoading.CAPABILITY_MONEY, (EnumFacing)null)).sync(entityplayermp2);
                        break;

                    case 500:
                        ((IMoney)entityplayermp2.getCapability(CapabilityLoading.CAPABILITY_MONEY, (EnumFacing)null)).setMoney(((IMoney)entityplayermp2.getCapability(CapabilityLoading.CAPABILITY_MONEY, (EnumFacing)null)).getMoney() - 500.0D);
                        ((IMoney)entityplayermp2.getCapability(CapabilityLoading.CAPABILITY_MONEY, (EnumFacing)null)).sync(entityplayermp2);
                        break;

                    default:
                        throw new WrongUsageException("commands.balance.funds", new Object[0]);
                }
            }
        }
    }

    public List<String> getTabCompletionOptions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos)
    {
        switch (args.length)
        {
            case 1:
                return Lists.newArrayList("check", "add", "remove");

            case 2:
                return args.length >= 2 ? getListOfStringsMatchingLastWord(args, server.getOnlinePlayerNames()) : Collections.emptyList();

            case 3:
                return Lists.newArrayList("5", "10", "20", "50", "100", "200", "500");

            default:
                return Lists.<String>newArrayList();
        }
    }
}
