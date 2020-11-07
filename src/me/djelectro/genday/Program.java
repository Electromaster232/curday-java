package me.djelectro.genday;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

public class Program implements Comparable<Program>{
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
        int timenum = getFinalTimeslotNum();
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

    public int getFinalTimeslotNum(){
        return Timeslots.getCorrectedTimeslot(Timeslots.valueOf(Timeslots.getNearestHourQuarter(timeslot)), timezone);
    }






    @Override
    public int compareTo(Program o) {
        return (this.getFinalTimeslotNum() - o.getFinalTimeslotNum());
    }
}
