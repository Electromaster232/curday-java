package me.djelectro.genday;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class Genday {
    static String FILEPATH = "curday.dat";
    static File file = new File(FILEPATH);

    public static void main(String[] args) throws IOException {
        Datfile d1 = new Datfile(7, false, "PHIL", "Philadelphia");
        Channel c1 = new Channel(1, "TST", "TST001");
        c1.addProgram(new Program(LocalDateTime.now(), "Yes", d1));
        c1.addProgram(new Program(LocalDateTime.now().plusHours(1), "AAAA", d1));
        d1.addChannel(c1);
        writeByte(d1.toBytes());

    }

    static void writeByte(byte[] bytes)
    {
        try {

            // Initialize a pointer
            // in file using OutputStream
            OutputStream
                    os
                    = new FileOutputStream(file);

            // Starts writing the bytes in it
            os.write(bytes);
            System.out.println("Successfully"
                    + " byte inserted");

            // Close the file
            os.close();
        }

        catch (Exception e) {
            System.out.println("Exception: " + e);
        }
    }
}
