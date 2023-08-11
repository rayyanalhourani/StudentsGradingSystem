package data.DAO.mappers;

import model.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMapper implements RowMapper<User> {

    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        int id = rs.getInt("user_id");
        String name = rs.getString("user_name");
        String pass= rs.getString("password");
        String role= rs.getString("role");

        return new User(id,name , pass,role);
    }
}
