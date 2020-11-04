package me.djelectro.genday;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class Channel {
    private int channelID;
    private String channelName;
    private String callsign;
    private boolean[] channelFlags = new boolean[3];

    private ArrayList<Program> programs = new ArrayList<>();

    public Channel(int id, String cs, String channel) {
        channelID = id;
        callsign = cs;
        channelName = channel;
    }

    public void setChannelFlags(boolean... Flags){
        channelFlags = Flags;
    }

    public Channel addProgram(Program p1){
        programs.add(p1);
        return this;
    }

    private byte generateFlagBytes(){
        byte result = 0x00;
        if(channelFlags[0]){
            result = (byte) 0x40;
        }
        if(channelFlags[1]){
            result = (byte) (result | (byte) 0x04);
        }
        if(channelFlags[2]){
            result = (byte) (result | (byte) 0x02);
        }
        return result;
    }

    public byte[] toBytes() throws IOException {
        ByteArrayOutputStream b1 = new ByteArrayOutputStream();
        b1.write(String.format("[%4d ", channelID).getBytes());
        b1.write(new byte[]{0x00, 0x00, 0x00, 0x00, 0x00, 0x00});
        b1.write(String.valueOf(channelName).getBytes());
        b1.write(0x00);
        b1.write((" " + callsign).getBytes());
        b1.write(new byte[]{0x00, 0x00, 0x00, 0x00, generateFlagBytes(), (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, 0x00,0x00,0x00,0x00, 0x00, 0x00, (byte) 0x8A, (byte) 0xFF, (byte) 0xFF, 0x30, 0x30, 0x00, 0x00, 0x03});
        b1.write(String.valueOf(channelName).getBytes());
        b1.write(0x00);
        for(Program r : programs){
            b1.write(r.toBytes());
        }
        return b1.toByteArray();
    }
}
