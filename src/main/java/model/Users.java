package model;

import dao.UsersDao;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/** The Users class provides methods for retrieving user information.
 *
 * @author Brandi Davis
 * */
public class Users {

    /** The ID of the user. */
    private int userId;

    /** The username of the user. */
    private String userName;

    /** The password of the user. */
    private String password;

    /** An Observable list to hold all users. */
    public static ObservableList<Users> allUsers = FXCollections.observableArrayList();

    /** An Observable list to hold all user IDs. */
    public static ObservableList<Integer> allUserIds = FXCollections.observableArrayList();

    /** The constructor for the Users class.
     *
     * @param userId the ID of the user
     * @param userName the username of the user
     * @param password the user's password
     * */
    public Users(int userId, String userName, String password) {
        this.userId = userId;
        this.userName = userName;
        this.password = password;
    }

    /** Default constructor for the Users class. */
   public Users() {}

    /** Overrides the toString() method to return the username instead of the object's name.
     *
     * @return the username of the user
     * */
    @Override
    public String toString(){
        return userName;
    }

    /** Adds users to the all users Observable list.
     *
     * @param user the user to be added
     * */
    public static void addUsers(Users user) {
        allUsers.add(user);
    }

    /** Adds user IDs to the all user IDs Observable list.
     *
     * @param id the user ID to be added
     * */
    public static void addUserIds(int id) {
        allUserIds.add(id);
    }

    /** Returns the all user IDs Observable list.
     *
     * @return all user IDs
     * */
    public static ObservableList<Integer> getUserIds() {
        return allUserIds;
    }

    /** Returns the all users Observable list.
     *
     * @return all users in the database
     * */
    public static ObservableList<Users> getAllUsers(){
        return allUsers;
    }

    /** Retrieves the user that is currently logged into the program.
     *
     * @return the logged-in user
     * */
    public static Users getLoggedInUser() {
        return UsersDao.getCurrentUser();
    }

    /** Retrieves the user ID.
     *
     * @return the ID of the user
     * */
    public int getUserId() {
        return userId;
    }

    /** Retrieves the user's username.
     *
     * @return the username of the user
     * */
    public String getUserName() {
        return userName;
    }

    /** Setter for the user's username.
     *
     * @param userName the username to set
     * */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /** Retrieve's the user's password.
     *
     * @return the password for the user
     * */
    public String getPassword() {
        return password;
    }
}
