package dao;

import com.mysql.cj.protocol.Resultset;
import model.Users;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsersDao {

    private static Users currentUser;

    public static Users getCurrentUser() {
        return currentUser;

    }
    public static void setCurrentUser(Users user){
        currentUser = user;
    }



    public static void selectUsers() throws SQLException {
        String sql = "SELECT * FROM users";
        PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        Users userResult;

        while(rs.next()){
            int userId = rs.getInt("User_ID");
            String userName = rs.getString("User_Name");
            String password = rs.getString("Password");
            userResult = new Users(userId, userName, password);
            Users.addUsers(userResult);
            Users.addUserIds(userId);
        }
    }



    public static Users findUser(String userName, String password) throws SQLException {

        String sql = "SELECT * FROM users WHERE User_Name = '" + userName + "' AND Password = '" +password + "'";

        PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        Users userResult;

        while(rs.next()) {
            currentUser = new Users();
            currentUser.setUserName(rs.getString("User_Name"));
            int userId = rs.getInt("User_ID");
            String userNameQ = rs.getString("User_Name");
            String passwordQ = rs.getString("Password");
            userResult = new Users(userId,userNameQ,passwordQ);
            return userResult;

        }

            return null;
    }
}
