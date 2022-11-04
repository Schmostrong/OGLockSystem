package og.survival.locksystem.main;

import og.survival.locksystem.command.OGLockCommand;
import og.survival.locksystem.command.OGUnlockCommand;
import og.survival.locksystem.database.DAO;
import og.survival.locksystem.listener.OGLocksystemListener;
import og.survival.locksystem.utils.OGLocksystemUtils;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public class OGLocksystemMain extends JavaPlugin {

    @Override
    public void onEnable(){
        //Printing startup message
        System.out.println(ChatColor.WHITE + "[" + ChatColor.DARK_AQUA + "OGLockSystem" + ChatColor.WHITE + "] >> " + ChatColor.GREEN + "Starting...");

        //Creating the plugin data folder, if it does not exist
        if(!getDataFolder().exists()){
            getDataFolder().mkdirs();
        }
        this.getConfig().options().copyDefaults(true);
        saveDefaultConfig();

        //Reading messages from config file
        OGLocksystemUtils.getOgLocksystemUtils().setMessages(getConfig().getString("messages.prefix"),
                                                            getConfig().getString("messages.command_wrong_usage"),
                                                            getConfig().getString("messages.command_in_locking_mode"),
                                                            getConfig().getString("messages.command_locking_success"),
                                                            getConfig().getString("messages.command_already_locked"),
                                                            getConfig().getString("messages.command_remove_lock"),
                                                            getConfig().getString("messages.command_lock_removed"));

        //Loading database settings and creating a connection
        DAO.getDataAccessObject().createDAOConnection(this.getConfig().getString("mysql_host"),
                this.getConfig().getString("database"),
                this.getConfig().getString("username"),
                this.getConfig().getString("password"),
                this.getConfig().getInt("mysql_port"));

        //Create database tables if not already existing
        DAO.getDataAccessObject().createTableIfNotExists();

        //Load all locked items on server startup
        DAO.getDataAccessObject().loadLockedObjectFromDatabase();

        getCommand("oglock").setExecutor(new OGLockCommand());
        getCommand("ogunlock").setExecutor(new OGUnlockCommand());
        getServer().getPluginManager().registerEvents(new OGLocksystemListener(), this);
    }

    /**
     * When the server is shut down, all locked items should be saved in the database
     */
    @Override
    public void onDisable() {
        DAO.getDataAccessObject().saveLockedObjectsToDatabase();
    }
}
