package me.djelectro.genday;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class Program {
    private LocalDateTime timeslot;
    private String programName;
    private int timezone;

    public Program(LocalDateTime date, String string, Datfile d1){
        timeslot = date;
        programName = string;
        timezone = d1.getTimezone();
    }

    public LocalDateTime getTimeslot(){return timeslot;}

    public String getProgramName(){return programName;}

    public byte[] toBytes() throws IOException {
        ByteArrayOutputStream b1 = new ByteArrayOutputStream();
        int timenum = getCorrectedTimeslot(Timeslots.valueOf(getNearestHourQuarter(timeslot).toLocalTime()));
        //int timenum = 33;
        b1.write(String.valueOf(timenum).getBytes());
        b1.write(0x00);
        b1.write(String.valueOf(1).getBytes());
        b1.write(0x00);
        b1.write(String.valueOf(33).getBytes());
        b1.write(0x00);
        b1.write(String.valueOf(0).getBytes());
        b1.write(0x00);
        b1.write(String.valueOf(0).getBytes());
        b1.write(0x00);
        b1.write(programName.getBytes());
        b1.write(0x00);
        return b1.toByteArray();

    }

    private static LocalDateTime getNearestHourQuarter(LocalDateTime datetime) {

        int minutes = datetime.getMinute();
        LocalDateTime newDatetime = datetime;
        if(minutes > 30){
             newDatetime = datetime.plusHours(1).minusMinutes(minutes);
        }
        else if(minutes < 30){
            newDatetime = datetime.minusMinutes(minutes);
        }
        newDatetime = newDatetime.truncatedTo(ChronoUnit.MINUTES);

        return newDatetime;
    }

    private int getCorrectedTimeslot(int baseTimeslot){
        int finalTimeslot;
        finalTimeslot = baseTimeslot - ((timezone - 1) * 2);

        if(finalTimeslot > 48){
            finalTimeslot = finalTimeslot - 48;
        }
        else if(finalTimeslot <= 0){
            finalTimeslot = finalTimeslot + 48;
        }

        return finalTimeslot;
    }


}
