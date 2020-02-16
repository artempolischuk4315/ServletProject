package ua.polischuk.model.dao.impl;



import ua.polischuk.model.dao.DaoFactory;
import ua.polischuk.model.repository.TestRepository;
import ua.polischuk.model.repository.UserRepository;
import ua.polischuk.model.repository.UserTestRepository;


public class JDBCDaoFactory extends DaoFactory {

    private final ConnectionPoolHolder connectionPoolHolder;

    public JDBCDaoFactory(ConnectionPoolHolder connectionPoolHolder) {
        this.connectionPoolHolder = connectionPoolHolder;
    }

    @Override
    public TestRepository createTestDao() { return new JDBCTestDao(connectionPoolHolder); }
    @Override
    public UserRepository createUserDao() {
        return new JDBCUserDao(connectionPoolHolder);
    }
    @Override
    public UserTestRepository createUserAndTestDao(){ return  new JDBCUserTestDao(connectionPoolHolder);}


}
