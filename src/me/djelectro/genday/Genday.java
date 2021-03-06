package me.djelectro.genday;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class Genday {

    public static void main(String[] args) throws IOException, ParserConfigurationException, SAXException {
        String res = genCurday("curday.dat", "https://djelectro.endl.site/tv/buildxml.php?action=raw", 6, false, "PHIL", "Philadelphia");
        System.out.println(res);
    }

    public static String genCurday(String filename, String xmlURL, int timezone, boolean daylightSavings, String airportName, String fullCity) throws ParserConfigurationException, IOException, SAXException {
        StringBuilder s1 = new StringBuilder();
        Datfile d1 = new Datfile(timezone, daylightSavings, airportName, fullCity);
        DocumentBuilderFactory factory =
                DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(new BufferedInputStream(new URL(xmlURL).openStream()));
        //Element root = doc.getDocumentElement();
        NodeList nList = doc.getElementsByTagName("channel");
        for (int temp = 0; temp < nList.getLength(); temp++) {
            Node nNode = nList.item(temp);
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) nNode;
                Channel c1 = new Channel(Integer.parseInt(eElement.getAttribute("id")), eElement.getElementsByTagName("callsign").item(0).getTextContent(), eElement.getElementsByTagName("callsign").item(0).getTextContent());
                String flags = eElement.getElementsByTagName("flags").item(0).getTextContent();
                String[] flags2 = flags.split(",");
                c1.setChannelFlags((Integer.parseInt(flags2[0]) == 1), (Integer.parseInt(flags2[1]) == 1), (Integer.parseInt(flags2[2]) == 1));
                NodeList nList1 = doc.getElementsByTagName("programme");
                for (int temp1 = 0; temp1 < nList1.getLength(); temp1++) {
                    Node nNode1 = nList1.item(temp1);
                    if (nNode1.getNodeType() == Node.ELEMENT_NODE) {
                        Element eElement1 = (Element) nNode1;
                        if (Integer.parseInt(eElement1.getAttribute("channel")) == Integer.parseInt(eElement.getAttribute("id"))) {
                            s1.append(eElement1.getAttribute("start"));
                            s1.append("\n");
                            LocalDateTime startTime = LocalDateTime.parse(eElement1.getAttribute("start"), DateTimeFormatter.ofPattern("yyyyMMddHHmmss Z"));
                            LocalDateTime endTime = LocalDateTime.parse(eElement1.getAttribute("stop"), DateTimeFormatter.ofPattern("yyyyMMddHHmmss Z"));
                            if(LocalDateTime.now().isAfter(endTime)){
                                continue;
                            }
                            //if(Timeslots.getCorrectedTimeslot(Timeslots.valueOf(Timeslots.getNearestHourQuarter(startTime)), d1.getTimezone()) < Timeslots.getCorrectedTimeslot(Timeslots.valueOf(Timeslots.getNearestHourQuarter(LocalDateTime.now())), d1.getTimezone())){
                            if(LocalDateTime.now().plusHours(1).isAfter(startTime)){
                                startTime = LocalDateTime.now();
                            }
                            c1.addProgram(new Program(startTime, eElement1.getElementsByTagName("title").item(0).getTextContent() + " " + eElement1.getElementsByTagName("desc").item(0).getTextContent(), d1));
                            //c1.addProgram(new Program(LocalDateTime.now().plusHours(1), "FML!", d1));
                        }
                    }
                }
                //c1.addProgram(new Program(LocalDateTime.now(), "Yes", d1));
                //c1.addProgram(new Program(LocalDateTime.now().plusHours(1), "AAAA", d1));

                d1.addChannel(c1);
            }
        }
        writeByte(d1.toBytes(), filename, s1);
        return s1.toString();
    }

    static void writeByte(byte[] bytes, String filepath, StringBuilder s1)
    {
        File file = new File(filepath);
        try {

            // Initialize a pointer
            // in file using OutputStream
            OutputStream
                    os
                    = new FileOutputStream(file);

            // Starts writing the bytes in it
            os.write(bytes);
            s1.append("Successfully"
                    + " byte inserted");
            s1.append("\n");

            // Close the file
            os.close();
        }

        catch (Exception e) {
            s1.append("Exception: ").append(e);
            s1.append("\n");
        }
    }
}
