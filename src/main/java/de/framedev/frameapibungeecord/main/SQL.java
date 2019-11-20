package de.framedev.frameapibungeecord.main;



import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Used Class From https://github.com/Mrtenz/MySQLAPI/tree/master/src/main/java/me/mrten/mysqlapi
 */
public class SQL {

    public static void UpdateData(String selected, String data, String table, String where) {
        try {
            Statement statement =MYSQL.getConnection().createStatement();
            String sql = new UpdateQuery(table).set(selected,data).where(where).build();
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            MYSQL.close();
        }
    }
    public static boolean isTableExists(String table) {
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
        } finally {
            MYSQL.close();
        }
        return false;
    }
    public static void InsertData(String column, String data, String table) {
        try {
            Statement statement = MYSQL.getConnection().createStatement();
            String sql = new InsertQuery(table).value(column,data).build();
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            MYSQL.close();
        }
    }
    public static void DeleteData(String table, String column, String data,String where) {
        try {
            Statement statement = MYSQL.getConnection().createStatement();
            String sql =  new DeleteQuery(table).where(where).build();
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            MYSQL.close();
        }
    }
    public static void createTable(String table,String column) {
        String sql = "CREATE TABLE IF NOT EXISTS " + table +"("+ column+");";
        // prepare the statement to be executed
        try {
            PreparedStatement stmt = (PreparedStatement) MYSQL.getConnection().prepareStatement(sql);
            stmt.executeUpdate();
        } catch (SQLException e3) {
            e3.printStackTrace();
        } finally {
            MYSQL.close();
        }
    }
    public static boolean exists(String column, String data, String table) {
        try {
            Statement statement = MYSQL.getConnection().createStatement();
            String sql = "SELECT * FROM " + table + " WHERE " + column + " = '" + data + "';";
            ResultSet res = statement.executeQuery(sql);
            if (res.next()) {
                if (res.getString(column) == null) {
                    return false;
                } else {
                    return true;
                }
            } else {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            MYSQL.close();
        }
        return false;

    }
    public static Object get(String selected , String column, String data, String table) {
        Object o = null;
        try {
            Statement statement = MYSQL.getConnection().createStatement();
            String sql = "SELECT * FROM "+table+" WHERE " + column + " = '" + data + "';";
            ResultSet res = statement.executeQuery(sql);
            if(res.next()) {
                o = res.getObject(selected);
                if (o != null) {
                    return o;
                } else {
                    return o;
                }
            } else {
                return o;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            MYSQL.close();
        }
        return o;
    }
}
