package ua.polischuk.model.repository;


import ua.polischuk.model.repository.impl.ConnectionPoolHolder;
import ua.polischuk.model.repository.impl.RepositoryFactory;

public abstract class DaoFactory {
    private static DaoFactory daoFactory;

    public abstract UserRepository createUserDao();
    public abstract TestRepository createTestDao();
    public abstract UserTestRepository createUserAndTestDao();


    public static DaoFactory getInstance(){
        if( daoFactory == null ){
            synchronized (DaoFactory.class){
                if(daoFactory==null){
                    DaoFactory temp = new RepositoryFactory(ConnectionPoolHolder.poolHolder());
                    daoFactory = temp;
                }
            }
        }
        return daoFactory;
    }
}
