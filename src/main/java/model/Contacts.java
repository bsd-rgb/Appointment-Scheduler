package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/** The Contacts class provides methods for retrieving contact information.
 *
 * @author Brandi Davis
 * */
public class Contacts {

    /** The ID of the contact. */
    private int contactId;

    /** The name of the contact. */
    private String contactName;

    /** The email of the contact. */
    private String contactEmail;

    /** An Observable list to hold all contacts from the database. */
    public static ObservableList<Contacts> allContacts = FXCollections.observableArrayList();

    /** The constructor for the Contacts class.
     *
     * @param contactId the ID of the contact
     * @param contactName the name of the contact
     * @param contactEmail the email of the contact
     * */
    public Contacts(int contactId, String contactName, String contactEmail) {
        this.contactId = contactId;
        this.contactName = contactName;
        this.contactEmail = contactEmail;
    }

    /** Overrides the toString() method to return the contact name instead of the object's name.
     *
     * @return the name of the contact
     * */
    @Override
    public String toString(){
        return contactName;
    }

    /** Adds a contact to the all contacts Observable list.
     *
     * @param contact the contact to be added
     * */
    public static void addContact(Contacts contact) {
        allContacts.add(contact);
    }

    /** Retrieves the contact ID.
     *
     * @return the ID of the contact
     * */
    public int getContactId() {
        return contactId;
    }

    /** Retrieves the contact name.
     *
     * @return the name of the contact
     * */
    public String getContactName() {
        return contactName;
    }
}
