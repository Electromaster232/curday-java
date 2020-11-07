package me.djelectro.genday;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;

public enum Timeslots {

    ONE(1, LocalTime.of(0, 0)),
    TWO(2, LocalTime.of(0, 30)),
    THREE(3, LocalTime.of(1, 0)),
    FOUR(4, LocalTime.of(1, 30)),
    FIVE(5, LocalTime.of(2, 0)),
    SIX(6, LocalTime.of(2, 30)),
    SEVEN(7, LocalTime.of(3, 0)),
    EIGHT(8, LocalTime.of(3, 30)),
    NINE(9, LocalTime.of(4, 0)),
    TEN(10, LocalTime.of(4, 30)),
    ELEVEN(11, LocalTime.of(5, 0)),
    TWELVE(12, LocalTime.of(5, 30)),
    THIRTEEN(13, LocalTime.of(6, 0)),
    FOURTEEN(14, LocalTime.of(6, 30)),
    FIFTEEN(15, LocalTime.of(7, 0)),
    SIXTEEN(16, LocalTime.of(7, 30)),
    SEVENTEEN(17, LocalTime.of(8, 0)),
    EIGHTEEN(18, LocalTime.of(8, 30)),
    NINETEEN(19, LocalTime.of(9, 0)),
    TWENTY(20, LocalTime.of(9, 30)),
    TWENTYONE(21, LocalTime.of(10, 0)),
    TWENTYTWO(22, LocalTime.of(10, 30)),
    TWENTYTHREE(23, LocalTime.of(11, 0)),
    TWENTYFOUR(24, LocalTime.of(11, 30)),
    TWENTYFIVE(25, LocalTime.of(12, 0)),
    TWENTYSIX(26, LocalTime.of(12, 30)),
    TWENTYSEVEN(27, LocalTime.of(13, 0)),
    TWENTYEIGHT(28, LocalTime.of(13, 30)),
    TWENTYNINE(29, LocalTime.of(14, 0)),
    THIRTY(30, LocalTime.of(14, 30)),
    THIRTYONE(31, LocalTime.of(15, 0)),
    THIRTYTWO(32, LocalTime.of(15, 30)),
    THIRTYTHREE(33, LocalTime.of(16, 0)),
    THIRTYFOUR(34, LocalTime.of(16, 30)),
    THIRTYFIVE(35, LocalTime.of(17, 0)),
    THIRTYSIX(36, LocalTime.of(17, 30)),
    THIRTYSEVEN(37, LocalTime.of(18, 0)),
    THIRTYEIGHT(38, LocalTime.of(18, 30)),
    THIRTYNINE(39, LocalTime.of(19, 0)),
    FORTY(40, LocalTime.of(19, 30)),
    FORTYONE(41, LocalTime.of(20, 0)),
    FORTYTWO(42, LocalTime.of(20, 30)),
    FORTYTHREE(43, LocalTime.of(21, 0)),
    FORTYFOUR(44, LocalTime.of(21, 30)),
    FORTYFIVE(45, LocalTime.of(22, 0)),
    FORTYSIX(46, LocalTime.of(22, 30)),
    FORTYSEVEN(47, LocalTime.of(23, 0)),
    FORTYEIGHT(48, LocalTime.of(23, 30));

    private final int slot1;
    private final LocalTime time1;
    private static Map map = new HashMap<>();

    Timeslots(int slot, LocalTime time){
        slot1 = slot;
        time1 = time;
    }

    static {
        for (Timeslots pageType : Timeslots.values()) {
            map.put(pageType.time1, pageType.slot1);
        }
    }

    public static int valueOf(LocalTime time) {
        return (int) map.get(time);
    }


    public LocalTime getValue() {
        return time1;
    }

    public static int getCorrectedTimeslot(int baseTimeslot, int timezone){
        int finalTimeslot;
        finalTimeslot = (baseTimeslot) - ((timezone) * 2);

        if(finalTimeslot > 48){
            finalTimeslot = finalTimeslot - 48;
        }
        else if(finalTimeslot <= 0){
            finalTimeslot = finalTimeslot + 48;
        }

        return finalTimeslot;

    }

    public static LocalTime getNearestHourQuarter(LocalDateTime datetime) {

        int minutes = datetime.getMinute();
        LocalDateTime newDatetime = datetime;
        if(minutes > 30){
            newDatetime = datetime.plusHours(1).minusMinutes(minutes);
        }
        else if(minutes < 30){
            newDatetime = datetime.minusMinutes(minutes);
        }

        return newDatetime.toLocalTime().truncatedTo(ChronoUnit.MINUTES);
    }

}
