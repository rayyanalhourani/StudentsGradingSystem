package data.DAO;
import data.DBConnection;
import model.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UserDAO implements IUserDAO{
    private Statement statement = DBConnection.getInstance().getStatement();
    private Validation validation = new Validation();
    public UserDAO() throws SQLException, ClassNotFoundException {
    }

    public int addUser(User user){
        try{
            //check if the user exist or not
            boolean ExistUser= validation.isUserExist(user.getId());
            String query = String.format("INSERT INTO user (user_id, user_name, password, role) VALUES (%s , '%s' , '%s' , '%s')",user.getId(),user.getName(),user.getPassword(),user.getRole());
            if (ExistUser) {
                return 0;
            } else {
                return statement.executeUpdate(query);
            }
        }catch (Exception e){
            System.out.println(e);
        }
        return 0;
    }

    public String login(int userid , String password){
        try {
            //check if the user is existed and compare his password with send password and return the role of user
            ResultSet resultSet = statement.executeQuery("select * from user where user_id="+userid);
            String pass = "", role = "";
            while (resultSet.next()) {
                pass = resultSet.getString("Password");
                role = resultSet.getString("Role");
            }
            if (!pass.equals("") && password.equals(pass)) {
                return role;
            }
        }catch (Exception e){
            System.out.println(e);
        }
        return "";
    }


}
