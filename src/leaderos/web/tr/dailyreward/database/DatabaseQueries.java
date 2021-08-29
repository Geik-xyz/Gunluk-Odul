package leaderos.web.tr.dailyreward.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import leaderos.web.tr.dailyreward.Main;
import leaderos.web.tr.dailyreward.utils.RewardManager;
import leaderos.web.tr.dailyreward.utils.objects.Cache;

public class DatabaseQueries {
	
	public static void createTable() {
		
        try (Connection connection = ConnectionPool.getConnection();
             Statement statement = connection.createStatement()) {
            statement.addBatch("CREATE TABLE IF NOT EXISTS `Tablo`\n" +
                    "(\n" +
                    " `username`         varchar(20) NOT NULL UNIQUE,\n" +
                    " `lastTake`          varchar(30) NOT NULL ,\n" +
                    " `lastTakeUnix`          long DEFAULT 0 ,\n" +
                    " `totalLoot`          int DEFAULT 0 ,\n" +
                    " `streak`          int DEFAULT 0\n" +
                    ");");
            statement.executeBatch();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
	
	public static void savePlayerAllCache(String name) 
	{
		
		String SQL_QUERY = "UPDATE Tablo SET lastTake = ?, lastTakeUnix = ?, totalLoot = ?, streak = ? WHERE username = ?";
		
        try (Connection con = ConnectionPool.getConnection())
        {
        	
            PreparedStatement pst = con.prepareStatement(SQL_QUERY);
            
            pst.setString(1, RewardManager.cache.get(name).getLastTake());
            
            pst.setLong(2, RewardManager.cache.get(name).getLastTakeUnix());
            
            pst.setInt(3, RewardManager.cache.get(name).getTotalLoot());
            
            pst.setInt(4, RewardManager.cache.get(name).getStreak());
            
            pst.setString(5, name);

            pst.executeUpdate();
        	
            pst.close();
            
        }
        
        catch (SQLException | ClassNotFoundException | NullPointerException e1) {  return;  }
        
	}
	
	public static Cache getPlayerInformations(String name)
	{
		
		String SQL_QUERY = "SELECT * FROM Tablo WHERE username = ?";
        
        try (Connection con = ConnectionPool.getConnection())
        {
        	
            PreparedStatement pst = con.prepareStatement(SQL_QUERY);

            pst.setString(1, name);

            ResultSet resultSet = pst.executeQuery();
            
            Cache cache = null;
            
            if (resultSet.next())
            {
            	
            	String lastTake = resultSet.getString("lastTake");
            	
            	long lastTakeUnix = resultSet.getLong("lastTakeUnix");
            	
            	int totalLoot = resultSet.getInt("totalLoot");
            	
            	int streak = resultSet.getInt("streak");
            	
            	cache = new Cache(lastTake, lastTakeUnix, totalLoot, streak);
            	
            }
            
            resultSet.close();
            
            pst.close();
            
            return cache;
            
        }
        
        catch (SQLException | ClassNotFoundException e1) {  e1.printStackTrace(); return null;  }
        
	}
	
	public static int getLootID(String username) {
		String SQL_QUERY = "SELECT streak FROM Tablo WHERE username = ?";
        int streak = 0;
        try (Connection con = ConnectionPool.getConnection()) {
            PreparedStatement pst = con.prepareStatement(SQL_QUERY);

            pst.setString(1, username);

            ResultSet resultSet = pst.executeQuery();
            
            if (resultSet.next()) {
            	int tempStreak = resultSet.getInt("streak");
            	int maxIndex = Main.rewards.size();
            	if (tempStreak >= maxIndex) {
            		streak = 0;
            	} else streak = tempStreak;
            }
            resultSet.close();
            pst.close();
        }
        
        catch (SQLException | ClassNotFoundException e1) { return streak;}
        
        return streak;
	}
	public static void registerPlayerToDB(String name) {
		String SQL_QUERY = "INSERT OR IGNORE INTO Tablo (username, lastTake, lastTakeUnix, streak, totalLoot) VALUES (?, ?, ?, ?, ?)";
        try (Connection con = ConnectionPool.getConnection()) {
            PreparedStatement pst = con.prepareStatement(SQL_QUERY);

            pst.setString(1, name);
            pst.setString(2, "00-00-0000");
            pst.setString(3, "1592858068");
            pst.setInt(4, 0);
            pst.setInt(5, 0);

            pst.executeUpdate();
        	
            pst.close();
        }
        
        catch (SQLException | ClassNotFoundException e1) { return; }
        
	}
	public static int updatePlayerStreaks(String name) throws SQLException, ClassNotFoundException {
		String SQL_QUERY = "UPDATE Tablo SET lastTake = ?, lastTakeUnix = ?, totalLoot = ?, streak = ? WHERE username = ?";
        try (Connection con = ConnectionPool.getConnection()) {
            PreparedStatement pst = con.prepareStatement(SQL_QUERY);

            Date time = new Date();
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
            long unix = System.currentTimeMillis() / 1000L;
            
            pst.setString(1, formatter.format(time));
            pst.setLong(2, unix);
            pst.setInt(3, getTotalLoot(name)+1);
            pst.setInt(4, getLootID(name)+1);
            pst.setString(5, name);

            int returnValue = pst.executeUpdate();
        	
            pst.close();
            return returnValue;
        }
	}
	public static int getTotalLoot(String username) throws SQLException, ClassNotFoundException {
		String SQL_QUERY = "SELECT totalLoot FROM Tablo WHERE username = ?";
        int totalLoot = 0;
        try (Connection con = ConnectionPool.getConnection()) {
            PreparedStatement pst = con.prepareStatement(SQL_QUERY);

            pst.setString(1, username);

            ResultSet resultSet = pst.executeQuery();
            
            if (resultSet.next()) {
            	totalLoot = resultSet.getInt("totalLoot");
            }
            resultSet.close();
            pst.close();
        }
        return totalLoot;
	}
	public static boolean isStreakStillValid(String username) throws SQLException, ClassNotFoundException, ParseException {
		String SQL_QUERY = "SELECT lastTake FROM Tablo WHERE username = ?";
		boolean streakStatus = false;
        try (Connection con = ConnectionPool.getConnection()) {
            PreparedStatement pst = con.prepareStatement(SQL_QUERY);

            pst.setString(1, username);

            ResultSet resultSet = pst.executeQuery();
            
            if (resultSet.next()) {
            	Calendar calendar = Calendar.getInstance();
            	SimpleDateFormat sdformat = new SimpleDateFormat("dd-MM-yyyy");
        		Date lastTake = sdformat.parse(resultSet.getString("lastTake"));
        		Date now = new Date();
        		String nowString = sdformat.format(now);
        		Date ActualNow = sdformat.parse(nowString);
        		calendar.setTime(lastTake);
        		calendar.add(Calendar.DAY_OF_YEAR, 1);
        		// Calendar to Date with format
        		Date calendarDateFormat = calendar.getTime();
        		String calendarDateToString = sdformat.format(calendarDateFormat);
        		Date aDayAfterDate = sdformat.parse(calendarDateToString);
        		if (aDayAfterDate.equals(ActualNow) || lastTake.equals(ActualNow)) streakStatus = true;
            }
            resultSet.close();
            pst.close();
        }
        return streakStatus;
	}
	
	
	public static void resetPlayerStreak(String name)
	{
		
		String SQL_QUERY = "UPDATE Tablo SET streak = ? WHERE username = ?";
		
        try (Connection con = ConnectionPool.getConnection())
        {
        	
            PreparedStatement pst = con.prepareStatement(SQL_QUERY);
            
            pst.setInt(1, 0);
            
            pst.setString(2, name);

            pst.executeUpdate();
        	
            pst.close();
            
        }
        
        catch (ClassNotFoundException | SQLException e1) {}
	}

}
