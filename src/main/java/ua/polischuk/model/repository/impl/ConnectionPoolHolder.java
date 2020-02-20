package ua.polischuk.model.repository.impl;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;


public class ConnectionPoolHolder {

    public static final String DB_URL = "db.url";
    public static final String DB_USERNAME = "db.username";
    public static final String DB_PASSWORD = "db.password";
    public static final String DB_DRIVER = "db.driver";
    public static final String DB_MIN_IDLE = "db.minIdle";
    public static final String DB_MAX_IDLE = "db.maxIdle";
    public static final String DB_MAX_OPEN_STATEMENT = "db.maxOpenStatement";
    public static final String BUNDLE_NAME = "db";

    public static final String CONNECTION_PROBLEM = "Connection problem";

    private static final Logger log = Logger.getLogger( JDBCUserRepository.class);


    private static volatile ConnectionPoolHolder pool;

    private BasicDataSource  dataSource;

    ResourceBundle dbData = ResourceBundle.getBundle(BUNDLE_NAME);

    private ConnectionPoolHolder()
    {
        BasicDataSource ds = new BasicDataSource();
        ds.setUrl(dbData.getString(DB_URL));
        ds.setUsername(dbData.getString(DB_USERNAME));
        ds.setPassword(dbData.getString(DB_PASSWORD));
        ds.setDriverClassName(dbData.getString(DB_DRIVER));
        ds.setMinIdle(Integer.parseInt(dbData.getString(DB_MIN_IDLE)));
        ds.setMaxIdle(Integer.parseInt(dbData.getString(DB_MAX_IDLE)));
        ds.setMaxOpenPreparedStatements(Integer.parseInt(dbData.getString(DB_MAX_OPEN_STATEMENT)));
        this.dataSource = ds;
    }

    public static ConnectionPoolHolder poolHolder() {
        if (pool == null) {
            synchronized (ConnectionPoolHolder.class) {
                if (pool == null) {
                    //logger.info("connection pool created");
                    pool = new ConnectionPoolHolder();
                }
            }
        }
        return pool;
    }


    public final Connection getConnection() {
        try {
            return this.dataSource.getConnection();
        } catch (SQLException e) {
            log.error(CONNECTION_PROBLEM, e);
            throw new RuntimeException(e);
        }
    }

}
