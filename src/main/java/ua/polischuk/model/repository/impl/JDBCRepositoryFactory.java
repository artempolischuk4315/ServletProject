package ua.polischuk.model.repository.impl;



import ua.polischuk.model.repository.RepositoryFactory;
import ua.polischuk.model.repository.TestRepository;
import ua.polischuk.model.repository.UserRepository;
import ua.polischuk.model.repository.UserTestRepository;


public class JDBCRepositoryFactory extends RepositoryFactory {

    private final ConnectionPoolHolder connectionPoolHolder;

    public JDBCRepositoryFactory(ConnectionPoolHolder connectionPoolHolder) {
        this.connectionPoolHolder = connectionPoolHolder;
    }

    @Override
    public TestRepository createTestRepos() { return new JDBCTestRepository(connectionPoolHolder); }

    @Override
    public UserRepository createUserRepos() {
        return new JDBCUserRepository(connectionPoolHolder);
    }

    @Override
    public UserTestRepository createUserAndTestRepos(){ return  new JDBCUserTestRepository(connectionPoolHolder);}


}
