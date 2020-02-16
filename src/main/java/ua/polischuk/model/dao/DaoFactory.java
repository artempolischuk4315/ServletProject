package ua.polischuk.model.dao;


import ua.polischuk.model.dao.impl.ConnectionPoolHolder;
import ua.polischuk.model.dao.impl.JDBCDaoFactory;
import ua.polischuk.model.repository.TestRepository;
import ua.polischuk.model.repository.UserRepository;
import ua.polischuk.model.repository.UserTestRepository;

public abstract class DaoFactory {
    private static DaoFactory daoFactory;

    public abstract UserRepository createUserDao();
    public abstract TestRepository createTestDao();
    public abstract UserTestRepository createUserAndTestDao();


    public static DaoFactory getInstance(){
        if( daoFactory == null ){
            synchronized (DaoFactory.class){
                if(daoFactory==null){
                    DaoFactory temp = new JDBCDaoFactory(ConnectionPoolHolder.poolHolder());
                    daoFactory = temp;
                }
            }
        }
        return daoFactory;
    }
}
