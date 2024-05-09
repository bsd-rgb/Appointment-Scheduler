package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDateTime;

/** The Appointments class provides methods for retrieving appointment information.
 *
 * @author Brandi Davis
 * */
public class Appointments {

    /** The ID of the appointment. */
    private int appointmentId;

    /** The title of the appointment. */
    private String title;

    /** The description of the appointment. */
    private String description;

    /** The location of the appointment. */
    private String location;

    /** The type of the appointment. */
    private String type;

    /** The contact name associated with the appointment. */
    String contactName;

    /** The start date and time of the appointment. */
    private LocalDateTime start;

    /** The end date and time of the appointment. */
    private LocalDateTime end;

    /** The customer ID associated with the appointment. */
    private int customerId;

    /** The user ID associated with the appointment. */
    private int userId;

    /** The contact ID associated with the appointment. */
    private int contactId;

    /** An Observable list that holds all appointments from the database. */
    public static ObservableList<Appointments> allAppointments = FXCollections.observableArrayList();

    /** An Observable list that holds all appointment types from the database. */
    public static ObservableList<String> appointmentTypes = FXCollections.observableArrayList();

    /** An Observable list that holds filtered appointment data. */
    public static ObservableList<Appointments> filteredAppointments = FXCollections.observableArrayList();

    /** The count of the of filtered appointments. */
    public static int appointmentFilterListCount;

    /** Boolean that is used for detecting appointment overlap. */
    private static boolean overlap;

    /** Retrieves the appointment ID.
     *
     * @return the ID of the appointment
     * */
    public int getAppointmentId() {
        return appointmentId;
    }

    /** Retrieves the title of the appointment.
     *
     * @return the appointment title
     * */
    public String getTitle() {
        return title;
    }

    /** Sets the title of an appointment.
     *
     * @param title the title of the appointment
     * */
    public void setTitle(String title) {
        this.title = title;
    }

    /** Retrieves the description of the appointment.
     *
     * @return appointment description
     * */
    public String getDescription() {
        return description;
    }

    /** Retrieves the location of the appointment.
     *
     * @return the appointment location
     * */
    public String getLocation() {
        return location;
    }

    /** Sets the location of an appointment.
     *
     * @param location the appointment location
     * */
    public void setLocation(String location) {
        this.location = location;
    }

    /** Retrieves the contact name.
     *
     * @return the name of the contact
     * */
    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    /** Retrieves the type of the appointment.
     *
     * @return the appointment type
     * */
    public String getType() {
        return type;
    }

    /** Retrieves the start date and time of the appointment.
     *
     * @return the appointment start date and time
     * */
    public LocalDateTime getStart() {
        return start;
    }

    /** Sets the start date and time of the appointment.
     *
     * @param start the appointment start date and time
     * */
    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    /** Retrieves the end date and time of the appointment.
     *
     * @return the appointment end date and time
     * */
    public LocalDateTime getEnd() {
        return end;
    }

    /** Sets the start date and time of the appointment.
     *
     * @param end the appointment end date and time
     * */
    public void setEnd(LocalDateTime end) {
        this.end = end;
    }

    /** Retrieves the customer ID associated with an appointment.
     *
     * @return the customer ID
     * */
    public int getCustomerId() {
        return customerId;
    }

    /** Retrieves the user ID associated with an appointment.
     *
     * @return the user ID
     * */
    public int getUserId() {
        return userId;
    }

    /** Retrieves the contact ID associated with an appointment.
     *
     * @return the contact ID
     * */
    public int getContactId() {
        return contactId;
    }

    /** A constructor for the Appointments class used for appointment retrieval.
     *
     * @param appointmentId the ID of the appointment
     * @param title the title of the appointment
     * @param description the description of the appointment
     * @param location the location of the appointment
     * @param type the type of appointment
     * @param start the start date and time of the appointment
     * @param end the end date and time of the appointment
     * @param customerId the customer ID associated with the appointment
     * @param userId the user ID associated with the appointment
     * @param contactId the user ID associated with the appointment
     * @param contactName the name of the contact associated with the appointment
     * */
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

    /** A constructor for the Appointments class used for appointment creation.
     *
     * @param appointmentId the ID of the appointment
     * @param title the title of the appointment
     * @param description the description of the appointment
     * @param location the location of the appointment
     * @param type the type of appointment
     * @param start the start date and time of the appointment
     * @param end the end date and time of the appointment
     * @param customerId the customer ID associated with the appointment
     * @param userId the user ID associated with the appointment
     * @param contactId the user ID associated with the appointment
     * */
    public Appointments(int appointmentId, String title, String description, String location, String type, LocalDateTime start, LocalDateTime end, int customerId, int userId, int contactId) {
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

    }

    /** Checks to see if there are any overlaps for customer appointment times.
     *
     * @param customerId the ID of the customer to check for appointment overlap
     * @param origStartTime the first start time and date of the customer's appointment
     * @param origEndTime the first end time and date of the customer's appointment
     * @param newStartTime the second start time and date of the customer's appointment
     * @param newEndTime the second end time and date of the customer's appointment
     * @return boolean value depending on if there is an overlap or not
     * */
    public static boolean isOverlap(int customerId, LocalDateTime origStartTime, LocalDateTime origEndTime, LocalDateTime newStartTime, LocalDateTime newEndTime) {
        overlap = false;

        if ((newStartTime.isAfter(origStartTime) || newStartTime.equals(origStartTime)) && newStartTime.isBefore(origEndTime)) {
            overlap = true;
            //return overlap;
            return true;
        }

        if (newEndTime.isAfter(origStartTime) && (newEndTime.isBefore(origEndTime) || newEndTime.equals(origEndTime))) {
            overlap = true;
            return true;
        }

        if ((newStartTime.isBefore(origStartTime) || newStartTime.equals(origStartTime)) && (newEndTime.isAfter(origEndTime) || newEndTime.equals(origEndTime))) {
            overlap = true;
            return true;
        }
        return false;
    }

    /** Adds an appointment to the all appointments Observable list.
     *
     * @param appointment the appointment to be added
     * */
    public static void addAppointment(Appointments appointment){
        allAppointments.add(appointment);
    }

    /** Retrieves all appointments.
     *
     * @return all appointments from the all appointments Observable list
     * */
    public static ObservableList<Appointments> getAllAppointments(){
        return allAppointments;
    }

    /** Retrieves the appointment list filter count.
     *
     * @return the count of filtered appointments
     * */
    public static int getAppointmentFilterListCount() {
        return appointmentFilterListCount;
    }

    /** Sets the appointment list filter count.
     *
     * @param appointmentFilterListCount the count of filtered records
     * */
    public static void setAppointmentFilterListCount(int appointmentFilterListCount) {
        Appointments.appointmentFilterListCount = appointmentFilterListCount;
    }
}
