package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Collections;

public class Appointments {

    private int appointmentId;
    private String title;
    private String description;
    private String location;
    private String type;

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    String contactName;
    private LocalDateTime start;
    private LocalDateTime end;
    private int customerId;
    private int userId;
    private int contactId;


    public int getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    public void setEnd(LocalDateTime end) {
        this.end = end;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getContactId() {
        return contactId;
    }

    public void setContactId(int contactId) {
        this.contactId = contactId;
    }

    public static ObservableList<Appointments> allAppointments = FXCollections.observableArrayList();
    public static ObservableList<String> appointmentTypes = FXCollections.observableArrayList();
    private static boolean overlap;
    public static boolean isOverlap(int customerId, LocalDateTime origStartTime, LocalDateTime origEndTime, LocalDateTime newStartTime, LocalDateTime newEndTime) {

        overlap = false;

                if ((newStartTime.isAfter(origStartTime) || newStartTime.equals(origStartTime)) && newStartTime.isBefore(origEndTime)) {
                    overlap = true;
                    System.out.println("Overlap variable: " + overlap);
                    //return overlap;

                    return true;
                }

                if (newEndTime.isAfter(origStartTime) && (newEndTime.isBefore(origEndTime) || newEndTime.equals(origEndTime))) {
                    overlap = true;
                    System.out.println("Overlap variable: " + overlap);
                    //return overlap;
                    return true;
                }

                if ((newStartTime.isBefore(origStartTime) || newStartTime.equals(origStartTime)) && (newEndTime.isAfter(origEndTime) || newEndTime.equals(origEndTime))) {
                    overlap = true;
                    System.out.println("Overlap variable: " + overlap);
                    //return overlap;
                    return true;
                }
                System.out.println("Overlap variable: " + overlap);
                return false;
            }


    public Appointments(int appointmentId, String title, String description, String location, String type, LocalDateTime start, LocalDateTime end, int customerId, int userId, int contactId, String contactName) {
        this.appointmentId = appointmentId;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.start = start;
        this.end = end;
        this.customerId = customerId;
        this.userId = userId;
        this.contactId = contactId;
        this.contactName = contactName;
    }

    public static void addAppointment(Appointments appointment){
        allAppointments.add(appointment);
    }



    public static ObservableList<Appointments> getAllAppointments(){
        return allAppointments;
    }


}
