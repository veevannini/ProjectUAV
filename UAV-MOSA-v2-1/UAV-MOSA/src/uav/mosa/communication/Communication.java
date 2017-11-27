/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uav.mosa.communication;

import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import uav.mosa.aircraft.Drone;
import uav.mosa.struct.Mission;
import uav.mosa.struct.SimpleWaypoint;

/**
 *
 * @author jesimar
 */
public class Communication {
    
    private final int PORT = 50000;
    private final String HOST = "localhost";//"127.0.0.1";    
    private final String PROTOCOL = "http://";
    private final String UAV_SOURCE = "MOSA";
    private final int TIME_OUT = 30;   
    private final Drone drone;

    public Communication(Drone drone) {
        this.drone = drone;
    }        
    
    public void setMission(Mission mission) {
        boolean ok = false;
        long timeInitial = System.currentTimeMillis();
        long timeActual = System.currentTimeMillis();
        long diff;
        int t = 0;
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
                writer.close();
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine = in.readLine();
                System.out.println(inputLine);               
                in.close();
                ok = true;
            } catch (IOException ex) {
                try {
                    Thread.sleep(200);
                } catch (InterruptedException ex1) {
                    
                }
                ok = false;                
            }
            diff = timeActual - timeInitial;            
            if ((diff/1000) == t){
                System.out.println("\t\tTime in seconds: " + diff/1000);
                t++;
            }
        }while(!ok && diff < TIME_OUT * 1000);
        if (diff >= TIME_OUT * 1000){
            System.err.println("timeout mosa application");
            System.exit(0);
        }
    }
    
    public void appendMission(Mission mission) {
        boolean ok = false;
        long timeInitial = System.currentTimeMillis();
        long timeActual = System.currentTimeMillis();
        long diff;
        int t = 0;
        do{
            try {
                timeActual = System.currentTimeMillis();
                URL url = new URL(PROTOCOL + HOST + ":" + PORT + "/append-mission/");
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
                writer.close();
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine = in.readLine();
                System.out.println(inputLine);               
                in.close();
                ok = true;
            } catch (IOException ex) {
                try {
                    Thread.sleep(200);
                } catch (InterruptedException ex1) {
                    
                }
                ok = false;                
            }
            diff = timeActual - timeInitial;            
            if ((diff/1000) == t){
                System.out.println("\t\tTime in seconds: " + diff/1000);
                t++;
            }
        }while(!ok && diff < TIME_OUT * 1000);
        if (diff >= TIME_OUT * 1000){
            System.err.println("timeout mosa application");
            System.exit(0);
        }
    }
    
    public void setWaypoint(SimpleWaypoint wp) throws IOException{
        boolean ok = false;
        long timeInitial = System.currentTimeMillis();
        long timeActual = System.currentTimeMillis();
        long diff;
        int t = 0;
        do{
            try {
                timeActual = System.currentTimeMillis();
                URL url = new URL(PROTOCOL + HOST + ":" + PORT + "/set-waypoint/");
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
                writer.flush();
                writer.close();        
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine = in.readLine();
                System.out.println(inputLine);        
                in.close();
                ok = true;
            } catch (IOException ex) {
                try {
                    Thread.sleep(200);
                } catch (InterruptedException ex1) {
                    
                }
                ok = false;                
            }
            diff = timeActual - timeInitial;            
            if ((diff/1000) == t){
                System.out.println("\t\tTime in seconds: " + diff/1000);
                t++;
            }
        }while(!ok && diff < TIME_OUT * 1000);
        if (diff >= TIME_OUT * 1000){
            System.err.println("timeout mosa application");
            System.exit(0);
        }
    }
    
    public void getGPS() throws MalformedURLException, IOException{
        URL url = new URL(PROTOCOL + HOST + ":" + PORT + "/gps/");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestProperty("UAV-Source", UAV_SOURCE);
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));        
        String inputLine = in.readLine();
        System.out.println(inputLine);
        in.close();
        drone.getGPS().parserInfoGPS(inputLine);       
    }
    
    public void getBattery() throws MalformedURLException, IOException{   
        URL url = new URL(PROTOCOL + HOST + ":" + PORT + "/bat/");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestProperty("UAV-Source", UAV_SOURCE);
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine = in.readLine();
        System.out.println(inputLine);
        in.close();
        drone.getBattery().parserInfoBattery(inputLine);
    }
    
    public void getAttitude() throws MalformedURLException, IOException{
        URL url = new URL(PROTOCOL + HOST + ":" + PORT + "/att/");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestProperty("UAV-Source", UAV_SOURCE);
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));        
        String inputLine = in.readLine();
        System.out.println(inputLine);
        in.close();
        drone.getAttitude().parserInfoAttitude(inputLine);       
    }
    
    public void getVelocity() throws MalformedURLException, IOException{
        URL url = new URL(PROTOCOL + HOST + ":" + PORT + "/vel/");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestProperty("UAV-Source", UAV_SOURCE);
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));        
        String inputLine = in.readLine();
        System.out.println(inputLine);
        in.close();
        drone.getVelocity().parserInfoVelocity(inputLine);       
    }        
            
    public void getHeading() throws MalformedURLException, IOException{
        URL url = new URL(PROTOCOL + HOST + ":" + PORT + "/heading/");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestProperty("UAV-Source", UAV_SOURCE);
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));        
        String inputLine = in.readLine();
        System.out.println(inputLine);
        in.close();
        drone.getSensorUAV().parserInfoHeading(inputLine);       
    }
    
    public void getGroundSpeed() throws MalformedURLException, IOException{
        URL url = new URL(PROTOCOL + HOST + ":" + PORT + "/groundspeed/");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestProperty("UAV-Source", UAV_SOURCE);
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));        
        String inputLine = in.readLine();
        System.out.println(inputLine);
        in.close();
        drone.getSensorUAV().parserInfoGroundSpeed(inputLine);       
    }
    
    public void getAirSpeed() throws MalformedURLException, IOException{
        URL url = new URL(PROTOCOL + HOST + ":" + PORT + "/airspeed/");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestProperty("UAV-Source", UAV_SOURCE);
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));        
        String inputLine = in.readLine();
        System.out.println(inputLine);
        in.close();
        drone.getSensorUAV().parserInfoAirSpeed(inputLine);       
    }
    
    public void getGPSFix() throws MalformedURLException, IOException{
        URL url = new URL(PROTOCOL + HOST + ":" + PORT + "/gpsfix/");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestProperty("UAV-Source", UAV_SOURCE);
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));        
        String inputLine = in.readLine();
        System.out.println(inputLine);
        in.close();
        drone.getGPSInfo().parserInfoGPSInfo(inputLine);       
    }
    
    public void getAllInfo() throws MalformedURLException, IOException{
        URL url = new URL(PROTOCOL + HOST + ":" + PORT + "/allinfo/");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestProperty("UAV-Source", UAV_SOURCE);
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));        
        String inputLine = in.readLine();
        in.close();
        inputLine = inputLine.substring(13, inputLine.length() - 2);
        inputLine = inputLine.replace("[", "");
        inputLine = inputLine.replace("]", "");
        String v[] = inputLine.split(", ");
        double lat = Double.parseDouble(v[0]);
        double lng = Double.parseDouble(v[1]);
        double alt = Double.parseDouble(v[2]);
        drone.getGPS().updateGPS(lat, lng, alt);       
        
        double voltage = Double.parseDouble(v[3]);
        double current = Double.parseDouble(v[4]);
        double level = Double.parseDouble(v[5]);
        drone.getBattery().updateBattery(voltage, current, level);
        
        double pitch = Double.parseDouble(v[6]);
        double yaw = Double.parseDouble(v[7]);
        double roll = Double.parseDouble(v[8]);
        drone.getAttitude().updateAttitude(pitch, yaw, roll);
        
        double heading = Double.parseDouble(v[9]);
        drone.getSensorUAV().setHeading(heading);
                
        double groundspeed = Double.parseDouble(v[10]);
        drone.getSensorUAV().setGroundspeed(groundspeed);
        
        double airspeed = Double.parseDouble(v[11]);
        drone.getSensorUAV().setAirspeed(airspeed);
        
        int fixtype = Integer.parseInt(v[12]);
        drone.getGPSInfo().setFixType(fixtype);
        
        int satellitesVisible = Integer.parseInt(v[13]);
        drone.getGPSInfo().setSatellitesVisible(satellitesVisible);
        
        double vx = Double.parseDouble(v[14]);
        double vy = Double.parseDouble(v[15]);
        double vz = Double.parseDouble(v[16]);
        drone.getVelocity().updateVelocity(vx, vy, vz);
    }
    
    public void getMode() throws MalformedURLException, IOException{
        URL url = new URL(PROTOCOL + HOST + ":" + PORT + "/mode/");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestProperty("UAV-Source", UAV_SOURCE);
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));        
        String inputLine = in.readLine();
        System.out.println(inputLine);
        in.close();
        drone.getStatusUAV().parserInfoMode(inputLine);       
    }
    
    public void getSystemStatus() throws MalformedURLException, IOException{
        URL url = new URL(PROTOCOL + HOST + ":" + PORT + "/system-status/");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestProperty("UAV-Source", UAV_SOURCE);
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));        
        String inputLine = in.readLine();
        System.out.println(inputLine);
        in.close();
        drone.getStatusUAV().parserInfoSystemStatus(inputLine);       
    }
    
    public void getArmed() throws MalformedURLException, IOException{
        URL url = new URL(PROTOCOL + HOST + ":" + PORT + "/armed/");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestProperty("UAV-Source", UAV_SOURCE);
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));        
        String inputLine = in.readLine();
        System.out.println(inputLine);
        in.close();
        drone.getStatusUAV().parserInfoArmed(inputLine);       
    }    
    
    public void isArmable() throws MalformedURLException, IOException{
        URL url = new URL(PROTOCOL + HOST + ":" + PORT + "/is-armable/");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestProperty("UAV-Source", UAV_SOURCE);
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));        
        String inputLine = in.readLine();
        System.out.println(inputLine);
        in.close();
        drone.getStatusUAV().parserInfoIsArmable(inputLine);       
    }        
    
    public void getEkfOk() throws MalformedURLException, IOException{
        URL url = new URL(PROTOCOL + HOST + ":" + PORT + "/ekf-ok/");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestProperty("UAV-Source", UAV_SOURCE);
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));        
        String inputLine = in.readLine();
        System.out.println(inputLine);
        in.close();
        drone.getStatusUAV().parserInfoEkfOk(inputLine);       
    }
}
