package og.survival.locksystem.database;

import og.survival.locksystem.utils.OGLocksystemUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Map;

public class DAO {

    private DatabaseConnection connection;
    private static DAO dataAccessObject;

    private DAO(DatabaseConnection connection) {
        this.connection = connection;
    }

    public static DAO getDataAccessObject(){
        if(dataAccessObject != null){
            return dataAccessObject;
        }else{
            dataAccessObject = new DAO(new DatabaseConnection("", "", "", "", 2));
            return dataAccessObject;
        }
    }

    public void createDAOConnection(String host, String database, String username, String password, int port){
        dataAccessObject = new DAO(new DatabaseConnection(host, database, username, password, port));
    }

    /**
     * Function is used to save all locked object into database
     */
    public void saveLockedObjectsToDatabase(){
        try{
            this.connection.openConnection();
            PreparedStatement ps = this.connection.getConnection().prepareStatement("INSERT INTO OGLockedItemsWithOwner VALUES (?, ?, ?, ?, ?)");

            for(Map.Entry<Location, String> entry : OGLocksystemUtils.getOgLocksystemUtils().getLockedItemsWithOwner().entrySet()){
                ps.setString(1, entry.getKey().getWorld().getName());
                ps.setInt(2, entry.getKey().getBlockX());
                ps.setInt(3, entry.getKey().getBlockY());
                ps.setInt(4, entry.getKey().getBlockZ());
                ps.setString(5, entry.getValue());

                ps.executeUpdate();
            }
        }catch (Exception ex){
            System.out.println(ex.getLocalizedMessage());
        }finally {
            if(connection.getConnection() != null){
                connection.closeConnection();
            }
        }
    }

    /**
     * Function is used to load all locked objects from the database
     */
    public void loadLockedObjectFromDatabase(){

        try{
            connection.openConnection();
            PreparedStatement ps = connection.getConnection().prepareStatement("SELECT World, BlockX, BlockY, BlockZ, UUID FROM OGLockedItemsWithOwner");
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Location loc = new Location(Bukkit.getWorld(rs.getString("World")), rs.getInt("BlockX"), rs.getInt("BlockY"), rs.getInt("BlockZ"));
                String playerUUID = rs.getString("UUID");
                OGLocksystemUtils.getOgLocksystemUtils().addLockedItem(loc, playerUUID);
            }
        }catch (Exception ex){
            System.out.println(ex.getLocalizedMessage());
        }finally {
            if(connection.getConnection() != null){
                connection.closeConnection();
            }
        }
    }

    /**
     * Function is used to create the database tables if they do not exist already
     */
    public void createTableIfNotExists(){
        try{
            this.connection.openConnection();
            PreparedStatement ps = this.connection.getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS OGLockedItemsWithOwner" +
                    "(World varchar(50)," +
                    "BlockX int," +
                    "BlockY int," +
                    "BlockZ int," +
                    "UUID varchar(50)," +
                    "PRIMARY KEY(World, BlockX, BlockY, BlockZ, UUID))");
            ps.executeUpdate();
        }catch (Exception ex){
            System.out.println(ex.getLocalizedMessage());
        }finally {
            if(connection.getConnection() != null){
                connection.closeConnection();
            }
        }
    }
}
