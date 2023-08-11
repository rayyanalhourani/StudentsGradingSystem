package implementation;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConnection {
    private static DBConnection instance=null;
    private static Connection connection=null;
    private Statement statement=null;

    private DBConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb", "root", "123456");
        statement = connection.createStatement();
    }

    public static DBConnection getInstance() throws SQLException, ClassNotFoundException {
        if(instance==null){
            instance=new DBConnection();
        }
        return instance;
    }

    public Statement getStatement() throws SQLException, ClassNotFoundException {
        if(statement==null){
            instance = new DBConnection();
        }
        return statement;
    }

}
