package com.nikitin.webproject.database.util.mysql;

import org.apache.log4j.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Connection pool for MySQL database.
 */
public class MySqlConnectionSupplier {
    private static final Logger LOGGER = Logger.getLogger(MySqlConnectionSupplier.class);

    private static final String  JNDI_LOOKUP_SERVICE = "java:/comp/env/jdbc/fleet_of_vehicles_db";
    private static DataSource dataSource;

    static {
        try {
            Context context = new InitialContext();
            dataSource = (DataSource) context.lookup(JNDI_LOOKUP_SERVICE);
        } catch (NamingException e) {
            LOGGER.error("ERROR: trying to read data from context.xml", e);
        }
    }

    public static Connection getConnection() {
        Connection connection = null;

        try {
            connection = dataSource.getConnection();
        } catch (SQLException e) {
            LOGGER.error("ERROR: trying to establish a connection"  , e);
        }

        return connection;
    }
}
