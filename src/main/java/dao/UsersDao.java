package dao;

import model.Users;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/** Handles data retrieval from the Users database table.
 *
 * @author Brandi Davis
 * */
public class UsersDao {

    /** The user that is currently logged in. */
    private static Users currentUser;

    /** The getter for the currently logged-in user.
     *
     * @return the current user
     * */
    public static Users getCurrentUser() {
        return currentUser;
    }

    /** The setter for the currently logged-in user.
     *
     * @param user the user that is logged in
     * */
    public static void setCurrentUser(Users user){
        currentUser = user;
    }

    /** Selects users from the database.
     *
     * Adds users and user IDs to Observable lists in the Users class
     * @throws SQLException in the event of an error when executing the query
     * */
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

    /** Selects the user with the matching username and password.
     *
     * @param userName the username of the user
     * @param password the user's password
     * @return userResult if the username and password is found and returns null if not found
     * @throws SQLException in the event of an error when executing the query
     * */
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
