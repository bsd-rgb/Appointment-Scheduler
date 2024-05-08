package helper;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.*;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.WeekFields;
import java.util.Locale;
import java.util.TimeZone;

/** The TimeUtil class is used for managing the business hour times and getting the start and end of the current week.
 *
 * @author Brandi Davis
 * */
public class TimeUtil {

    /** The start time of the business. */
    static final private LocalTime businessStartTime = LocalTime.of(8, 0);
    /** The end time of the business. */
    static final private LocalTime businessEndTime = LocalTime.MIDNIGHT.minusHours(2);
    /** The timezone of the business. */
    static final private ZoneId businessZoneId = ZoneId.of("America/New_York");
    /** The Zoned Date Time of the business start time. */
    static final private ZonedDateTime businessStartZDT = ZonedDateTime.of(LocalDate.now(), businessStartTime, businessZoneId);
    /** The Zoned Date Time of the business end time. */
    static final private ZonedDateTime businessEndZDT = ZonedDateTime.of(LocalDate.now(), businessEndTime, businessZoneId);
    /** The Zone ID of the current system (OS) default. */
    public static ZoneId localZoneId = ZoneId.of(TimeZone.getDefault().getID());
    /** Holds the current date. */
    public static LocalDate localCurrentDate = LocalDate.now();
    /** Gets the start of the week of the system's default. */
    private static final DayOfWeek startDayOfWeek = WeekFields.of(Locale.getDefault()).getFirstDayOfWeek();
    /** Gets the last day of the week by adding on to the start of the week. */
    private static final DayOfWeek lastDayOfWeek = startDayOfWeek.plus(6);
    /** The date of the start of the current week. */
    private static final LocalDate startCurrentWeek = localCurrentDate.with(TemporalAdjusters.previousOrSame(startDayOfWeek));
    /** The date of the end of the current week. */
    private static final LocalDate endCurrentWeek = localCurrentDate.with(TemporalAdjusters.nextOrSame(lastDayOfWeek));

    /** Retrieves the local zoned date time of the system.
     *
     * @return the system default zoned date time
     * */
    public static ZonedDateTime getLocalZonedDateTime(){
        return ZonedDateTime.of(LocalDateTime.now(), ZoneId.systemDefault());
    }

    /** Retrieves the date of the start of the current week.
     *
     * @return the date of the start of the current week
     * */
    public static LocalDate getStartCurrentWeek(){
        return startCurrentWeek;
    }

    /** Retrieves the date of the end of the current week.
     *
     * @return the date of the end of the current week
     * */
    public static LocalDate getEndCurrentWeek(){
        return endCurrentWeek;
    }

    /** Retrieves the current date.
     *
     * @return the current date
     * */
    public static LocalDate getLocalCurrentDate(){
        return localCurrentDate;
    }

    /** Generates business hours for appointment times in 15 minute increments.
     *
     * @return a list that holds the times of the business hours
     * */
    public static ObservableList<LocalTime> businessHours (){
        ObservableList<LocalTime> localTimeList = FXCollections.observableArrayList();

        ZonedDateTime businessStartToLocalZDT = businessStartZDT.withZoneSameInstant(localZoneId);
        ZonedDateTime businessEndToLocalZDT = businessEndZDT.withZoneSameInstant(localZoneId);

        while(businessStartToLocalZDT.isBefore(businessEndToLocalZDT.plusSeconds(1))) {
            localTimeList.add(businessStartToLocalZDT.toLocalTime());
            businessStartToLocalZDT = businessStartToLocalZDT.plusMinutes(15);
        }
        return localTimeList;
    }
}


