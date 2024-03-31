package model;

import dao.UsersDao;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDateTime;

public class Users {

    private int userId;
    private String userName;
    private String password;

    public Users(int userId, String userName, String password) {
        this.userId = userId;
        this.userName = userName;
        this.password = password;
    }

    public Users() {

    }
    @Override
    public String toString(){
        return userName;
    }

    public static ObservableList<Users> allUsers = FXCollections.observableArrayList();
    public static ObservableList<Integer> allUserIds = FXCollections.observableArrayList();

    public static void addUsers(Users user) {
        allUsers.add(user);
    }

    public static void addUserIds(int id) {
        allUserIds.add(id);
    }

    public static ObservableList<Integer> getUserIds() {

        return allUserIds;
    }

    public static ObservableList<Users> getAllUsers(){
        return allUsers;
    }



    public static Users getLoggedInUser() {
        return UsersDao.getCurrentUser();
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }



}
