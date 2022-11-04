package og.survival.locksystem.utils;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OGLocksystemUtils {

    private static OGLocksystemUtils ogLocksystemUtils;

    private String commandPrefixMessage;
    private String commandWrongUsageMessage;
    private String commandInLockingModeMessage;
    private String commandLockSuccessMessage;
    private String commandItemAlreadyLockedMessage;
    private String commandRemoveLockMessage;
    private String commandLockRemovedMessage;

    private List<Player> inLockingMode;
    private List<Player> inUnlockMode;
    private Map<Location, String> lockedItemsWithOwner;

    private OGLocksystemUtils(){
        commandPrefixMessage = "";
        commandWrongUsageMessage = "";
        commandInLockingModeMessage = "";
        commandLockSuccessMessage = "";
        commandItemAlreadyLockedMessage = "";
        commandRemoveLockMessage = "";
        commandLockRemovedMessage = "";
        inLockingMode = new ArrayList<>();
        inUnlockMode = new ArrayList<>();
        lockedItemsWithOwner = new HashMap<>();
    }

    /**
     * Function returns the singleton utils object used by other classes to access all data stored by the plugin
     *
     * @return - Returns the singleton object representing the utils class
     */
    public static OGLocksystemUtils getOgLocksystemUtils() {
        if(ogLocksystemUtils == null){
            ogLocksystemUtils = new OGLocksystemUtils();
        }
        return ogLocksystemUtils;
    }

    /**
     * Function is used to set a player in locking mode after executing oglock command
     *
     * @param p - Player object that represents the player that should be set in locking mode
     */
    public void setPlayerInLockingMode(Player p){
        if(!this.inLockingMode.contains(p)){
            this.inLockingMode.add(p);
        }
    }

    /**
     * Function used to remove a player from locking mode after an item was successfully locked
     *
     * @param p - Player object that represents the player that should be removed from locking mode
     */
    public void removePlayerFromLockingMode(Player p){
        if(this.inLockingMode.contains(p)){
            this.inLockingMode.remove(p);
        }
    }

    /**
     * Function checks, wheter an player is in locking mode
     *
     * @param p - Player object that represents the player that should be checked
     * @return - Returns true/false, based on the status of the player
     */
    public boolean playerInLockingMode(Player p){
        return this.inLockingMode.contains(p);
    }

    /**
     * Function is used to set a player in unlocking mode after executing oglock command
     *
     * @param p - Player object that represents the player that should be set in locking mode
     */
    public void setPlayerInUnlockingMode(Player p){
        if(!this.inUnlockMode.contains(p)){
            this.inUnlockMode.add(p);
        }
    }

    /**
     * Function used to remove a player from unlocking mode after an item was successfully locked
     *
     * @param p - Player object that represents the player that should be removed from locking mode
     */
    public void removePlayerFromUnlockingMode(Player p){
        if(this.inUnlockMode.contains(p)){
            this.inUnlockMode.remove(p);
        }
    }

    /**
     * Function checks, wheter an player is in unlocking mode
     *
     * @param p - Player object that represents the player that should be checked
     * @return - Returns true/false, based on the status of the player
     */
    public boolean playerInUnlockingMode(Player p){
        return this.inUnlockMode.contains(p);
    }

    /**
     * Function is used to add an object to the list of locked items
     *
     * @param itemLocation - Location object that represents the location of the object that should be locked
     * @param playerUUID - String that holds the uuid of the player that requests the lock of an item
     */
    public void addLockedItem(Location itemLocation, String playerUUID){
        if(!this.lockedItemsWithOwner.containsKey(itemLocation)){
            this.lockedItemsWithOwner.put(itemLocation, playerUUID);
        }
    }

    /**
     * Function is used to remove the lock on an locked item.
     *
     * @param location - Location object that represents the location of the object that should be unlocked
     */
    public void removeLockedItem(Location location){
        if(this.lockedItemsWithOwner.containsKey(location)){
            this.lockedItemsWithOwner.remove(location);
        }
    }

    /**
     * Function is used to check wheter an object is locked
     *
     * @param itemLocation - Location object that represents the location of the item that should be checked
     * @return - Returns true/false, based on the lock status of the given location object
     */
    public boolean itemIsLocked(Location itemLocation){
        return this.lockedItemsWithOwner.containsKey(itemLocation);
    }

    /**
     * Function is used to determine the owner of an locked object.
     *
     * @param itemLocation - Location object that represents the location of the locked item, where the owner should be determined
     * @return - String that holds the uuid of the owner
     */
    public String getItemOwner(Location itemLocation){
        return this.lockedItemsWithOwner.get(itemLocation);
    }

    /**
     * Function is used to set all messages printed during the usage of this plugin.
     * The content of the messages are editable in the configuration file.
     *
     * @param commandPrefixMessage - String that represents the prefix printed before every message
     * @param commandWrongUsageMessage - String that represents the message that is printed when the oglock command is used in the wrong way
     * @param commandInLockingModeMessage - String that represents the message that is printed when the user is set in locking mode after executing the oglock command
     * @param commandLockSuccessMessage - String that represents the message that is printed, when the user successfully locked an item
     */
    public void setMessages(String commandPrefixMessage, String commandWrongUsageMessage, String commandInLockingModeMessage, String commandLockSuccessMessage, String commandItemAlreadyLockedMessage, String commandRemoveLockMessage, String commandLockRemovedMessage){
        this.commandPrefixMessage = commandPrefixMessage;
        this.commandWrongUsageMessage = commandWrongUsageMessage;
        this.commandInLockingModeMessage = commandInLockingModeMessage;
        this.commandLockSuccessMessage = commandLockSuccessMessage;
        this.commandItemAlreadyLockedMessage = commandItemAlreadyLockedMessage;
        this.commandRemoveLockMessage = commandRemoveLockMessage;
        this.commandLockRemovedMessage = commandLockRemovedMessage;
    }

    /**
     * Function returns the message that is printed, when the oglock command is used in the wrong way.
     * The content of the message is editable in the configuration file.
     *
     * @return - String that represents the error message
     */
    public String getCommandWrongUsageMessage() {
        return commandWrongUsageMessage;
    }

    /**
     * Function returns the message that is printed, when the oglock command is executed and the player is set in locking mode.
     * The content of the message is editable in the configuration file.
     *
     * @return - String that represents the error message
     */
    public String getCommandInLockingModeMessage() {
        return commandInLockingModeMessage;
    }

    /**
     * Function returns the prefix that is printed before every message
     * The content of the prefix is editable in the configuration file.
     *
     * @return - String that represents the error message
     */
    public String getCommandPrefixMessage() {
        return commandPrefixMessage;
    }

    /**
     * Function returns the message that is printed, when the player is removed from locking mode and has successfully locked an object.
     * The content of the message is editable in the configuration file.
     *
     * @return - String that represents the error message
     */
    public String getCommandLockSuccessMessage() {
        return commandLockSuccessMessage;
    }

    /**
     * Function returns the message that is printed, when the player is trying to lock an already locked item
     * The content of the message is editable in the configuration file.
     *
     * @return - String that represents the error message
     */
    public String getCommandItemAlreadyLocked() {
        return commandItemAlreadyLockedMessage;
    }

    /**
     * Function returns the message that is printed, when the player tries to lock an already locked object
     * The content of the message is editable in the configuration file.
     *
     * @return - String that represents the error message
     */
    public String getCommandItemAlreadyLockedMessage() {
        return commandItemAlreadyLockedMessage;
    }

    /**
     * Function returns the message that is printed, when the player want to remove the lock on an object when executing /ogunlock
     * The content of the message is editable in the configuration file.
     *
     * @return - String that represents the error message
     */
    public String getCommandRemoveLockMessage() {
        return commandRemoveLockMessage;
    }

    /**
     * Function returns the message that is printed, when the player successfully removed to lock on an object
     * The content of the message is editable in the configuration file.
     *
     * @return - String that represents the error message
     */
    public String getCommandLockRemovedMessage() {
        return commandLockRemovedMessage;
    }

    /**
     * Function returns the map that holds all locked items and its owner.
     *
     * @return - Map that holds all locked object locations and the uuid of their owner
     */
    public Map<Location, String> getLockedItemsWithOwner() {
        return lockedItemsWithOwner;
    }
}
