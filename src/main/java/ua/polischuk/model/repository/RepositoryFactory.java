package ua.polischuk.model.repository;


import ua.polischuk.model.repository.impl.ConnectionPoolHolder;
import ua.polischuk.model.repository.impl.JDBCRepositoryFactory;

public abstract class RepositoryFactory {

    private static volatile RepositoryFactory repositoryFactory;

    public abstract UserRepository createUserRepos();

    public abstract TestRepository createTestRepos();

    public abstract UserTestRepository createUserAndTestRepos();


    public static RepositoryFactory getInstance(){
        RepositoryFactory localFactory = repositoryFactory;

        if( localFactory == null ){
            synchronized (RepositoryFactory.class){
                localFactory = repositoryFactory;
                if(localFactory ==null){
                    repositoryFactory= localFactory = new JDBCRepositoryFactory(ConnectionPoolHolder.poolHolder());
                }
            }
        }
        return localFactory;
    }
}
