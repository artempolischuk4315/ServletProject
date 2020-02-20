package ua.polischuk.model.repository.impl;



import ua.polischuk.model.repository.DaoFactory;
import ua.polischuk.model.repository.TestRepository;
import ua.polischuk.model.repository.UserRepository;
import ua.polischuk.model.repository.UserTestRepository;


public class RepositoryFactory extends DaoFactory {

    private final ConnectionPoolHolder connectionPoolHolder;

    public RepositoryFactory(ConnectionPoolHolder connectionPoolHolder) {
        this.connectionPoolHolder = connectionPoolHolder;
    }

    @Override
    public TestRepository createTestDao() { return new JDBCTestRepository(connectionPoolHolder); }
    @Override
    public UserRepository createUserDao() {
        return new JDBCUserRepository(connectionPoolHolder);
    }
    @Override
    public UserTestRepository createUserAndTestDao(){ return  new JDBCUserTestRepository(connectionPoolHolder);}


}
