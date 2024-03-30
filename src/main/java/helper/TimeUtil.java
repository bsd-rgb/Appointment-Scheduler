package helper;

import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.TimeZone;

public class TimeUtil {

   static final private int hoursOpen = 14;
    static final private LocalTime businessStartTime = LocalTime.of(8, 0);
    static final private LocalTime businessEndTime = LocalTime.MIDNIGHT.minusHours(2);
    static final private ZoneId businessZoneId = ZoneId.of("America/New_York");
    static final private ZonedDateTime businessStartZDT = ZonedDateTime.of(LocalDate.now(), businessStartTime, businessZoneId);
    static final private ZonedDateTime businessEndZDT = ZonedDateTime.of(LocalDate.now(), businessEndTime, businessZoneId);
    static ZoneId localZoneId = ZoneId.of(TimeZone.getDefault().getID());


    public static ObservableList<LocalTime> businessHours (){
        ObservableList<LocalTime> localTimeList = FXCollections.observableArrayList();
        ObservableList<LocalTime> tempList = FXCollections.observableArrayList();
        ZonedDateTime businessStartToLocalZDT = businessStartZDT.withZoneSameInstant(localZoneId);
        LocalTime localStart = LocalTime.of(businessStartToLocalZDT.getHour(), businessStartToLocalZDT.getMinute());
        ZonedDateTime businessEndToLocalZDT = businessEndZDT.withZoneSameInstant(localZoneId);
        String timePattern = "hh:mm a";
        DateTimeFormatter patternFormatter = DateTimeFormatter.ofPattern(timePattern);



        while(businessStartToLocalZDT.isBefore(businessEndToLocalZDT.plusSeconds(1))) {



           //tempList.add(LocalTime.of(businessStartToLocalZDT.getHour(), businessStartToLocalZDT.getMinute()));
           localTimeList.add(LocalTime.of(businessStartToLocalZDT.getHour(), businessStartToLocalZDT.getMinute()));
           //localTimeList.add(LocalTime.parse(businessStartToLocalZDT.format(patternFormatter)));
           businessStartToLocalZDT = businessStartToLocalZDT.plusMinutes(30);







        }
      /* for(LocalTime time: tempList) {

            localTimeList.add(LocalTime.parse(patternFormatter.format(time)));

        }*/
        return localTimeList;
    }
}


