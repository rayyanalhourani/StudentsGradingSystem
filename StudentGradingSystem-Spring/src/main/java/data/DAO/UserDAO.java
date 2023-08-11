package data.DAO;
import data.DAO.mappers.UserMapper;
import data.DBConnection;
import data.Jdbc;
import model.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class UserDAO implements IUserDAO{
    private Validation validation = new Validation();
    private JdbcTemplate jdbcTemplate = new Jdbc().getJdbcTemplate();
    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(16);
    public UserDAO() throws SQLException, ClassNotFoundException {
    }

    public int addUser(User user){
        try{
            //check if the user exist or not
            boolean ExistUser= validation.isUserExist(user.getId());
            user.setPassword(encoder.encode(user.getPassword()));
            String query = String.format("INSERT INTO user (user_id, user_name, password, role) VALUES (%s , '%s' , '%s' , '%s')",user.getId(),user.getName(),user.getPassword(),user.getRole());
            if (ExistUser) {
                return 0;
            } else {
                return jdbcTemplate.update(query);
            }
        }catch (Exception e){
            System.out.println(e);
        }
        return 0;
    }


    public String login(int userid , String password){
        try {
            //check if the user is existed and compare his password with send password and return the role of user
            String query = "select * from user where user_id="+userid;
            List<User> user = jdbcTemplate.query(query, new UserMapper());

            String pass = "", role = "";
        if(user.size()>0) {
            pass=user.get(0).getPassword();
            role=user.get(0).getRole();
            if (!pass.equals("") && encoder.matches(password,pass)) {
                return role;
            }
        }
        }catch (Exception e){
            System.out.println(e);
        }
        return "";
    }

}
