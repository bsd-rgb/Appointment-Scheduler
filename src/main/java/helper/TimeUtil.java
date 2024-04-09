package helper;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Timestamp;
import java.time.*;
import java.util.TimeZone;

public class TimeUtil {

   static final private int hoursOpen = 14;
    static final private LocalTime businessStartTime = LocalTime.of(8, 0);
    static final private LocalTime businessEndTime = LocalTime.MIDNIGHT.minusHours(2);
    static final private ZoneId businessZoneId = ZoneId.of("America/New_York");
    static final private ZonedDateTime businessStartZDT = ZonedDateTime.of(LocalDate.now(), businessStartTime, businessZoneId);
    static final private ZonedDateTime businessEndZDT = ZonedDateTime.of(LocalDate.now(), businessEndTime, businessZoneId);
    public static ZoneId localZoneId = ZoneId.of(TimeZone.getDefault().getID());

    public static ZoneId getLocalZoneId(){
        return localZoneId;
    }




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

   public static ZonedDateTime localToUTC (LocalDateTime localDateTime, ZoneId zoneId) {

        return localDateTime.atZone(zoneId).withZoneSameInstant(ZoneId.of("UTC"));
   }

/*   public static ZonedDateTime ToLocal(LocalDateTime localDateTime) {

       return localDateTime.atZone(localZoneId).withZoneSameInstant(localZoneId);

   }*/

    public static ZonedDateTime ToLocal(ZonedDateTime zonedDateTime) {

        return zonedDateTime.withZoneSameInstant(localZoneId);

    }

}


