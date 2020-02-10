package ua.polischuk.model.dao.impl;

import org.apache.commons.dbcp.BasicDataSource;
import java.sql.Connection;
import java.sql.SQLException;


public class ConnectionPoolHolder {

    private static volatile ConnectionPoolHolder pool;
    private BasicDataSource  dataSource;

    private ConnectionPoolHolder()
    {
        BasicDataSource ds = new BasicDataSource();
        ds.setUrl("jdbc:mysql://localhost:3306/myservletdb?useUnicode=yes&characterEncoding=UTF-8");
        ds.setUsername("root");
        ds.setPassword("1");
        ds.setDriverClassName("com.mysql.jdbc.Driver");
        ds.setMinIdle(5);
        ds.setInitialSize(10);
        ds.setMaxIdle(1);
        ds.setMaxOpenPreparedStatements(100);
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
        //logger.info("connect");
        try {
            return this.dataSource.getConnection();
        } catch (SQLException e) {
            System.out.println("connect catch");
            //logger.info("connection error", e);
            throw new RuntimeException(e);
        }
    }

    /*public static DataSource getDataSource(){
        if (dataSource == null){
            synchronized (ConnectionPoolHolder.class) {
                if (dataSource == null) {

                    BasicDataSource ds = new BasicDataSource();
                    ds.setUrl("jdbc:mysql://localhost:3306/myservletdb?useUnicode=yes&characterEncoding=UTF-8");
                    ds.setUsername("root");
                    ds.setPassword("1");
                    ds.setDriverClassName("com.mysql.jdbc.Driver");
                    ds.setMinIdle(5);
                    ds.setMaxIdle(10);
                    ds.setMaxOpenPreparedStatements(100);
                    dataSource = ds;
                }
            }
        }
        return dataSource;

    }

    public final Connection getConnection() {
        //logger.info("connect");
        try {
            return this.dataSource.getConnection();
        } catch (SQLException e) {
            System.out.println("connect catch");
            //logger.info("connection error", e);
            throw new RuntimeException(e);
        }
    }*/
}
