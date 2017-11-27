/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uav.mosa;

import uav.mosa.struct.Mission;
import uav.mosa.config.LoadConfig;
import uav.mosa.struct.basic.Waypoint;
import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Locale;
import uav.mosa.struct.Mission3D;
import uav.mosa.struct.Route3D;
import uav.mosa.struct.StateMOSA;
import uav.mosa.util.UtilString;

/**
 *
 * @author jesimar
 */
public class MOSA {
    
    private final int PORT = 50000;
    private final String HOST = "localhost";//"127.0.0.1";    
    private final String PROTOCOL = "http://";
    private final String UAV_SOURCE = "MOSA";
    private double levelBattery;
    private Waypoint gps;
    private final LoadConfig config;
    private final int TIME_OUT = 30;
    
    private final Mission3D mission3D;
    private final Route3D route3D;
    private MissionHGA missionHGA;
    private StateMOSA stateMOSA;
    
    public static void main(String[] args) throws IOException, ClassNotFoundException, Throwable {
        Locale.setDefault(Locale.US);
        MOSA mosa = new MOSA();         
        mosa.start();        
        mosa.loop();
        mosa.send();
    }

    public MOSA() throws IOException, ClassNotFoundException {
        System.out.println("mosa");
        this.config = new LoadConfig();       
        this.mission3D = new Mission3D();
        this.route3D = new Route3D();        
    }
    
    public void start() throws IOException {
        System.out.println("start");
        readMission(mission3D);
        this.mission3D.printMission();
        this.missionHGA = new MissionHGA(mission3D, config.getDirPlanner());        
        updateStateMOSA(StateMOSA.WAITING);
    }

    public void loop() throws Throwable {
        System.out.println("loop");
        int nRoute = 0;
        while (nRoute < mission3D.size() - 1 && stateMOSA == StateMOSA.WAITING){
            updateStateMOSA(StateMOSA.PROCESSING);
            missionHGA.execMission(nRoute);
            readRoute(nRoute);
            updateStateMOSA(StateMOSA.READY);
            nRoute++;
            if (nRoute < mission3D.size() - 1){
                updateStateMOSA(StateMOSA.WAITING);
                sleep_ms(100);
            } else{
                updateStateMOSA(StateMOSA.COMPLETED);
            }
        }
        sleep_ms(100);
    }
    
    public void send()throws IOException, ClassNotFoundException{
        System.out.println("send");
        Route3DtoGeo r3d = new Route3DtoGeo();
        r3d.route3DtoGeo(config);
        Mission mission = new Mission();
        //readFile(nameFileRoute, mission);
        readFile(config.getDirPlanner() + "route0Geo.log", mission);
        mission.printMission();
        setMission(mission);
    }
    
    private void setMission(Mission mission) {
        boolean ok = false;
        long timeInitial = System.currentTimeMillis();
        long timeActual = System.currentTimeMillis();
        long diff;
        do{
            try {
                timeActual = System.currentTimeMillis();
                URL url = new URL(PROTOCOL + HOST + ":" + PORT + "/set-mission/");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoOutput(true);
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Type", "application/json");
                connection.setRequestProperty("UAV-Source", UAV_SOURCE);
                OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
                Gson gson = new Gson();
                String strMission = gson.toJson(mission);
                System.out.println(strMission);
                writer.write(strMission);
                writer.flush();
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine = in.readLine();
                System.out.println("" + inputLine);
                writer.close();
                in.close();
                ok = true;
            } catch (IOException ex) {
                try {
                    Thread.sleep(200);
                } catch (InterruptedException ex1) {
                    //ex.printStackTrace();
                }
                ok = false;
                //ex.printStackTrace();
            }
            diff = timeActual - timeInitial;
            System.out.println("diff: " + diff);
        }while(!ok && diff < TIME_OUT * 1000);
        if (diff >= TIME_OUT * 1000){
            System.err.println("timeout mosa application");
            System.exit(0);
        }
    }
    
    private void updateStateMOSA(StateMOSA state){
        stateMOSA = state;
    }    
   
    public final void sleep_ms(int milliseconds) throws InterruptedException{
        Thread.sleep(milliseconds);
    }
    
    private void readFile(String name, Mission wps) throws FileNotFoundException, IOException{
        FileReader fr = new FileReader(name);
        BufferedReader br = new BufferedReader(fr);
        String sCurrentLine;
        br = new BufferedReader(new FileReader(name));
        boolean firstTime = true;
        while ((sCurrentLine = br.readLine()) != null) {
            sCurrentLine = UtilString.changeValueSeparator(sCurrentLine);
            String s[] = sCurrentLine.split(";");
            double lat = Float.parseFloat(s[0]);
            double lng = Float.parseFloat(s[1]);
            double alt = Float.parseFloat(s[2]);
            if (firstTime){
                wps.addWaypoint(new Waypoint("takeoff", lat, lng, alt));
                firstTime = false;
            }
            wps.addWaypoint(new Waypoint("waypoint", lat, lng, alt));
        }
    }
    
    private void readMission(Mission3D mission3D){
        ReaderMission read = new ReaderMission(mission3D);
        read.reader(new File(config.getDirPlanner() + config.getFileWaypointsMission()));
    }
    
    private void readRoute(int nRoute) {
        File inFile = new File(config.getDirPlanner() + "route" + nRoute + ".log");
        ReaderRoute read = new ReaderRoute(route3D);
        read.reader(inFile);
    }
    
    //Os metodos abaixo nao sao usados
    
    public void start2() throws IOException, ClassNotFoundException{
        request("gps");
        request("bat");
        
        Waypoint wp = new Waypoint(5, 10, 20);
        setWaypoint(wp);
        
        Waypoint wp1 = new Waypoint(1, 4, 10);
        Waypoint wp2 = new Waypoint(2, 3, 20);
        Waypoint wp3 = new Waypoint(3, 2, 30);
        Mission mission1 = new Mission();
        mission1.addWaypoint(wp1);
        mission1.addWaypoint(wp2);
        mission1.addWaypoint(wp3);
        setMission(mission1);
        
        Mission mission2 = new Mission();
        String name = "route.txt";
        readFile(name, mission2);
        setMission(mission2);
    }
    
    private void request(String value) throws MalformedURLException, IOException{   
        if (value.equals("gps")){
            getGPS();
        } else if (value.equals("bat")){
            getBattery();
        }else {
            System.err.println("Field not valid: " + value);
        }
    }
    
    private void getGPS() throws MalformedURLException, IOException{
        URL url = new URL(PROTOCOL + HOST + ":" + PORT + "/gps/");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestProperty("UAV-Source", UAV_SOURCE);
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));        
        String inputLine = in.readLine();
        System.out.println("" + inputLine);
        String str = inputLine.substring(9, inputLine.length()-2);
        String v[] = str.split(",");
        double lat = Double.parseDouble(v[0]);
        double lng = Double.parseDouble(v[1]);
        double alt = Double.parseDouble(v[2]);
        gps = new Waypoint(lat, lng, alt);
        System.out.println("GPS: " + gps);
        in.close();
    }
    
    private void getBattery() throws MalformedURLException, IOException{   
        URL url = new URL(PROTOCOL + HOST + ":" + PORT + "/bat/");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestProperty("UAV-Source", UAV_SOURCE);
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine = in.readLine();
        System.out.println("" + inputLine);
        levelBattery = Double.parseDouble(inputLine.substring(8, inputLine.length()-1));
        System.out.println("BAT: " + levelBattery);
        in.close();
    }
    
    private void setWaypoint(Waypoint wp) throws IOException{
        URL url = new URL(PROTOCOL + HOST + ":" + PORT + "/waypoint/");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setDoOutput(true);
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("UAV-Source", UAV_SOURCE);
        OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
        Gson gson = new Gson();
        String strWp = gson.toJson(wp);
        System.out.println(strWp);        
        writer.write(strWp);
        writer.close();
    }    
}
