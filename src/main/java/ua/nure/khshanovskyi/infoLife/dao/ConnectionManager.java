package ua.nure.khshanovskyi.infoLife.dao;


import org.apache.log4j.Logger;
import ua.nure.khshanovskyi.infoLife.exception.DaoException;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionManager {

    private static final Logger LOGGER = Logger.getLogger(ConnectionManager.class);

    private DataSource dataSource;

    public ConnectionManager(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * Rollback current state db to previous one.
     *
     * @param con the con
     */
    protected void rollback(Connection con) {
        if (con != null) {
            try {
                con.rollback();
            } catch (SQLException e) {
                LOGGER.error("Cannot rollback connection");
                throw new DaoException("Cannot rollback connection", e);
            }
        }
    }

    /**
     * Closes connection with database.
     *
     * @param ac the con
     */
    protected void close(AutoCloseable ac) {
        if (ac != null) {
            try {
                ac.close();
            } catch (Exception e) {
                LOGGER.error("Cannot close auto closable");
                throw new DaoException("Cannot close auto closable", e);
            }
        }
    }

    /**
     * Gets connection from data source.
     *
     * @return connection
     */
    protected Connection getConnection() {
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            LOGGER.error("Cannot get connection from data source");
            throw new DaoException("Cannot get connection from data source", e);
        }
    }
}
