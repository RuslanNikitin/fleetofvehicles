package com.nikitin.webproject;

import org.junit.After;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static org.junit.Assert.assertNotNull;

/**
 *  For testing the connection.
 *  Connects to the actual db (fleet_of_vehicles_db).
 */
public class DatabaseTest {
    private String URL = "jdbc:mysql://localhost:3306/fleet_of_vehicles_db?useSSL=false";
    private String USERNAME = "root";
    private String PASSWORD = "root";
    private String DRIVER = "com.mysql.jdbc.Driver";
    private Connection conn;

    @After
    public void tearDown() throws Exception {
        conn.close();
    }

    @Test
    public void connectTest() throws SQLException, ClassNotFoundException {
        Class.forName(DRIVER);
        conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        assertNotNull(conn);
    }
}
