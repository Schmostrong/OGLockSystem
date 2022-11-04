package og.survival.locksystem.listener;

import og.survival.locksystem.utils.OGLocksystemUtils;
import org.apache.commons.lang.ObjectUtils;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class OGLocksystemListener implements Listener {

    /**
     * Function is triggered, when a player interacts with any block in the world. If the player is in locking mode, the next clicked item is locked.
     * If he isn't, it needs to be checked, if the right clicked item is locked by another player. If it is, the event is cancelled.
     *
     * @param playerInteractEvent - Event that is triggered, when a player interacts with the block
     */
    @EventHandler
    public void onPlayerItemLock(PlayerInteractEvent playerInteractEvent){
        if(playerInteractEvent.getAction() == Action.LEFT_CLICK_BLOCK){
            //Check if player is in locking mode
            if(OGLocksystemUtils.getOgLocksystemUtils().playerInLockingMode(playerInteractEvent.getPlayer())){
                if(OGLocksystemUtils.getOgLocksystemUtils().itemIsLocked(playerInteractEvent.getClickedBlock().getLocation())){
                    //Print error message to player
                    playerInteractEvent.getPlayer().sendMessage(OGLocksystemUtils.getOgLocksystemUtils().getCommandPrefixMessage() + " " + OGLocksystemUtils.getOgLocksystemUtils().getCommandItemAlreadyLocked());

                    //Remove Player from locking mode
                    OGLocksystemUtils.getOgLocksystemUtils().removePlayerFromLockingMode(playerInteractEvent.getPlayer());
                }else{
                    //Remove Player from locking mode
                    OGLocksystemUtils.getOgLocksystemUtils().removePlayerFromLockingMode(playerInteractEvent.getPlayer());

                    //Add item to locked items
                    OGLocksystemUtils.getOgLocksystemUtils().addLockedItem(playerInteractEvent.getClickedBlock().getLocation(), playerInteractEvent.getPlayer().getUniqueId().toString());

                    //Inform player about success
                    playerInteractEvent.getPlayer().sendMessage(OGLocksystemUtils.getOgLocksystemUtils().getCommandPrefixMessage() + " " + OGLocksystemUtils.getOgLocksystemUtils().getCommandLockSuccessMessage());
                }
            }else if(OGLocksystemUtils.getOgLocksystemUtils().playerInUnlockingMode(playerInteractEvent.getPlayer())){
                if(OGLocksystemUtils.getOgLocksystemUtils().itemIsLocked(playerInteractEvent.getClickedBlock().getLocation())){
                    OGLocksystemUtils.getOgLocksystemUtils().removeLockedItem(playerInteractEvent.getClickedBlock().getLocation());
                    playerInteractEvent.getPlayer().sendMessage(OGLocksystemUtils.getOgLocksystemUtils().getCommandPrefixMessage() + " " + OGLocksystemUtils.getOgLocksystemUtils().getCommandLockRemovedMessage());
                    OGLocksystemUtils.getOgLocksystemUtils().removePlayerFromUnlockingMode(playerInteractEvent.getPlayer());
                }
            }
        }else{
            try{
                //Check if the clicked Block is locked
                if(OGLocksystemUtils.getOgLocksystemUtils().itemIsLocked(playerInteractEvent.getClickedBlock().getLocation())){
                    //If block is locked, check if it is checked by the clicking player. If not, event is cancelled
                    if(!playerInteractEvent.getPlayer().getUniqueId().toString().equals(OGLocksystemUtils.getOgLocksystemUtils().getItemOwner(playerInteractEvent.getClickedBlock().getLocation()))){
                        playerInteractEvent.setCancelled(true);
                    }
                }
            }catch (NullPointerException npe){

            }
        }
    }
}
