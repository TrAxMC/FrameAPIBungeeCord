/**
 * Dies ist ein Plugin von FrameDev
 * Bitte nichts �ndern, @Copyright by FrameDev 
 */
package de.framedev.frameapibungeecord.main;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author Darryl
 *
 */
public class IsTableExist {

	public static boolean isExist(String table) {
		MYSQL.connect();
		try {
			Statement statement = MYSQL.getConnection().createStatement();
			ResultSet rs = statement.executeQuery("SHOW TABLES LIKE '" +table+ "'");
			if(rs.next()) {
				return true;
			} else {
				return false;
				
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
}
