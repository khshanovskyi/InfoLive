package ua.nure.khshanovskyi.infoLife.dao;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ua.nure.khshanovskyi.infoLife.exception.DaoException;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionManager {

    /**
     * {@link Logger} log4j for logs.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ConnectionManager.class);

    /**
     * {@link DataSource}
     */
    private DataSource dataSource;

    /**
     * Constructor for initialization this class and initialization {@link DataSource} for work with DB.
     *
     * @param dataSource {@link DataSource}
     */
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
