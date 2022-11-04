package og.survival.locksystem.command;

import og.survival.locksystem.utils.OGLocksystemUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class OGUnlockCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(commandSender instanceof Player){
            if(command.getName().equalsIgnoreCase("ogunlock")){
                OGLocksystemUtils.getOgLocksystemUtils().setPlayerInUnlockingMode((Player) commandSender);
                commandSender.sendMessage(OGLocksystemUtils.getOgLocksystemUtils().getCommandPrefixMessage() + " "
                        + OGLocksystemUtils.getOgLocksystemUtils().getCommandRemoveLockMessage());
                return true;
            }else{
                commandSender.sendMessage(OGLocksystemUtils.getOgLocksystemUtils().getCommandPrefixMessage() + " "
                        + OGLocksystemUtils.getOgLocksystemUtils().getCommandWrongUsageMessage());
                return true;
            }
        }
        return false;
    }
}
