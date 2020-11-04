package me.djelectro.genday;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

public class Datfile {
    private int timezone;
    private boolean daylightSavings;
    private String airportName;
    private String city;

    private ArrayList<Channel> channels = new ArrayList<>();

    public Datfile(int tz, boolean ds, String an, String cy){
        timezone = tz;
        daylightSavings = ds;
        airportName = an;
        city = cy;
    }

    public Channel addChannel(Channel c1){
        channels.add(c1);
        return c1;
    }

    public int getTimezone(){return timezone;}

    private byte[] makeHeader() throws IOException {
        ByteArrayOutputStream b1 = new ByteArrayOutputStream();
        b1.write("AE3366N".getBytes());
        b1.write(new byte[]{0x03, 0x01});
        b1.write((timezone + boolToYN(daylightSavings)).getBytes());
        b1.write("YNNYYNNl".getBytes());
        b1.write(new byte[]{0x00, 0x00, '0', 0x00});
        b1.write("DREV 5".getBytes());
        b1.write(0x00);
        b1.write(airportName.getBytes());
        b1.write(0x00);
        b1.write(city.getBytes());
        b1.write(0x00);
        int jDay = LocalDate.now().getDayOfYear() - 1;
        if (jDay > 255){
            jDay = jDay - 255;
        }
        b1.write((jDay + "").getBytes());
        b1.write(0x00);
        b1.write(String.valueOf(channels.size() + 1).getBytes());
        b1.write(0x00);
        b1.write("131".getBytes());
        b1.write(0x00);
        b1.write("1348".getBytes());
        b1.write(0x00);
        return b1.toByteArray();

    }

    public byte[] toBytes() throws IOException {
        ByteArrayOutputStream b1 = new ByteArrayOutputStream();
        b1.write(makeHeader());
        for(Channel r : channels){
            b1.write(r.toBytes());
            b1.write("49".getBytes());
            b1.write(0x00);
        }
        return b1.toByteArray();
    }

    private static String boolToYN(boolean b1){
        if(b1) {
            return "Y";
        }
        return "N";
    }


}
