package com.clothcat.rfcreader;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Singleton utility class to abstract all of the database stuff in one place.
 *
 * @author ssta
 */
public class SQLiteShim {

    private static SQLiteShim instance;
    private Connection connection;

    // enforce singleton
    private SQLiteShim() {
        init();
    }

    public static SQLiteShim getInstance() {
        if (instance == null) {
            instance = new SQLiteShim();
        }
        return instance;
    }

    Connection getConnection() {
        if (connection == null) {
            try {
                Class.forName("org.sqlite.JDBC");
                connection = DriverManager.getConnection("jdbc:sqlite:rfcreader.db");
            } catch (ClassNotFoundException | SQLException ex) {
                Logger.getLogger(SQLiteShim.class.getName()).log(Level.SEVERE, null, ex);
                System.exit(-1);
            }
        }
        return connection;
    }

    /**
     * Create the database tables if they don't exist already
     */
    private void init() {
        Statement st = null;
        String RFC_INDEX_SQL = "CREATE TABLE IF NOT EXISTS "
                + " RFC_INDEX "
                + "(ID              INT     PRIMARY KEY     NOT NULL, "
                + " TITLE           TEXT                    NOT NULL, "
                + " ISSUE DATE      DATE, "
                + " AUTHORS         TEXT, "
                + " STATUS          TEXT)";

        try {
            Connection conn = getConnection();
            st = conn.createStatement();
            st.executeUpdate(RFC_INDEX_SQL);
        } catch (SQLException ex) {
            Logger.getLogger(SQLiteShim.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                st.close();
            } catch (Exception ex) {
                Logger.getLogger(SQLiteShim.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

}
