package data.DAO;

import model.User;

import javax.sql.DataSource;

public interface IUserDAO {
    public int addUser(User user);
    public String login(int userid , String password);
}
