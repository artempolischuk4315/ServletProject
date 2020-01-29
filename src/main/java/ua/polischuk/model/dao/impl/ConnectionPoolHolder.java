package ua.polischuk.model.dao.impl;

import org.apache.commons.dbcp.BasicDataSource;

import javax.sql.DataSource;
import java.util.ResourceBundle;

public class ConnectionPoolHolder {
    private static volatile DataSource dataSource;
    public static DataSource getDataSource(){

        if (dataSource == null){
            synchronized (ConnectionPoolHolder.class) {
                if (dataSource == null) {

                    /*ResourceBundle resource = ResourceBundle.getBundle("db");
                    ds.setDriverClassName(resource.getString("MYSQL_DB_DRIVER_CLASS"));*/ // TODO: отак в проперти
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


}
