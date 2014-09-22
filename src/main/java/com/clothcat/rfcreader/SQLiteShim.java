/*
 * The MIT License
 *
 * Copyright 2014 Stephen Stafford.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
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
 * @deprecated since we'e using the XML file now and (probably) won't need a
 * database at all
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
