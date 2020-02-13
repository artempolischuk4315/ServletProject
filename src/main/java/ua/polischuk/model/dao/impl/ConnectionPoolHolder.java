package ua.polischuk.model.dao.impl;

import org.apache.commons.dbcp.BasicDataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;


public class ConnectionPoolHolder {

    private static volatile ConnectionPoolHolder pool;
    private BasicDataSource  dataSource;
    ResourceBundle dbData = ResourceBundle.getBundle("db");

    private ConnectionPoolHolder()
    {
        BasicDataSource ds = new BasicDataSource();
        ds.setUrl(dbData.getString("db.url"));
        ds.setUsername(dbData.getString("db.username"));
        ds.setPassword(dbData.getString("db.password"));
        ds.setDriverClassName(dbData.getString("db.driver"));
        ds.setMinIdle(Integer.parseInt(dbData.getString("db.minIdle")));
        ds.setMaxIdle(Integer.parseInt(dbData.getString("db.maxIdle")));
        //ds.setMaxIdle(1);
        ds.setMaxOpenPreparedStatements(Integer.parseInt(dbData.getString("db.maxOpenStatement")));
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
