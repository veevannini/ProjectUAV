/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uav.mosa;

import uav.mosa.planner.MissionHGA;
import uav.mosa.writer.WriteRoute3DtoGeo;
import uav.mosa.reader.ReaderMission;
import uav.mosa.struct.Mission;
import uav.mosa.reader.ReaderLoadConfig;
import uav.mosa.struct.basic.Waypoint;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Locale;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;
import uav.mosa.aircraft.Ararinha;
import uav.mosa.aircraft.Drone;
import uav.mosa.aircraft.iDroneAlpha;
import uav.mosa.communication.Communication;
import uav.mosa.struct.Command;
import uav.mosa.struct.Constants;
import uav.mosa.struct.Mission3D;
import uav.mosa.struct.StateMOSA;
import uav.mosa.util.Util;
import uav.mosa.util.UtilString;

/**
 *
 * @author jesimar
 */
public final class MOSA {        
    
    private final Communication communication;
    private final ReaderLoadConfig config;
    private final Mission3D mission3D;
    private final WriteRoute3DtoGeo write3DtoGeo;
    private final Drone drone;    
    private MissionHGA missionHGA;
    private StateMOSA stateMOSA;
    
    public static void main(String[] args) throws IOException, ClassNotFoundException, Throwable {
        Locale.setDefault(Locale.US);
        MOSA mosa = new MOSA();        
    }

    public MOSA() throws IOException, ClassNotFoundException, Throwable {
        System.out.println("MOSA");        
        this.config = ReaderLoadConfig.getInstance();
        this.mission3D = new Mission3D();
        this.write3DtoGeo = new WriteRoute3DtoGeo();
        if (config.getNameDrone().equals(Constants.NAME_DRONE_iDRONE_ALPHA)){
            drone = new Ararinha();
        }else {
            drone = new iDroneAlpha();
        }
        this.communication = new Communication(drone);
        if (config.getTypeRoute().equals(Constants.TYPE_ROUTE_ROUTE_READY)){
            monitoring();
            sendRouteReadyToDronekit();
        }else if (config.getTypeRoute().equals(Constants.TYPE_ROUTE_PLANNER)){
            monitoring();
            start();
            loop();
        }
    }            
    
    private void start() throws IOException {
        System.out.println("\tstart");
        readMission();        
        missionHGA = new MissionHGA(drone, mission3D);
        missionHGA.clearLogs();
        updateStateMOSA(StateMOSA.WAITING);
    }

    public void loop() throws Throwable {
        System.out.println("\tloop");
        int nRoute = 0;
        while (nRoute < mission3D.size() - 1 && stateMOSA == StateMOSA.WAITING){
            System.out.println("\t\tRoute: " + nRoute);
            updateStateMOSA(StateMOSA.PROCESSING);
            missionHGA.execMission(nRoute);         
            write3DtoGeo.route3DtoGeo(nRoute);
            sendRoutePlannerToDronekit(nRoute);
            updateStateMOSA(StateMOSA.READY);
            nRoute++;
            if (nRoute < mission3D.size() - 1){
                updateStateMOSA(StateMOSA.WAITING);
                Util.sleepMilliSeconds(10000);
            } else{
                updateStateMOSA(StateMOSA.COMPLETED);
            }
        }
        Util.sleepMilliSeconds(100);
    }
    
    public void sendRoutePlannerToDronekit(int nRoute)throws IOException, ClassNotFoundException{
        System.out.println("\tsend route planner");        
        Mission mission = new Mission();
        readFileRoutePlanner(config.getDirPlanner() + "routeGeo" + nRoute + ".txt", mission, nRoute);
        mission.printMission();
        communication.setMission(mission);
    }
    
    public void sendRouteReadyToDronekit() throws IOException, ClassNotFoundException{
        System.out.println("\tsend route ready");        
        Mission mission = new Mission();
        readFileReadyRoute(config.getDirRouteReady()+ config.getFileRouteReady(), mission);
        mission.printMission();
        communication.setMission(mission);
        try {
            Thread.sleep(5000);
        } catch (InterruptedException ex) {
            Logger.getLogger(MOSA.class.getName()).log(Level.SEVERE, null, ex);
        }
         
//        Mission mission2 = new Mission();
//        readFileReadyRoute(config.getDirRouteReady()+ "route.txt", mission2);
//        mission2.printMission();        
//        communication.appendMission(mission2);
    }         
    
    private void readFileRoutePlanner(String name, Mission wps, int nRoute) 
            throws FileNotFoundException, IOException{
        BufferedReader br = new BufferedReader(new FileReader(name));
        String sCurrentLine;
        boolean firstTime = true;
        double lat = 0;
        double lng = 0;
        double alt = 0;
        while ((sCurrentLine = br.readLine()) != null) {
            sCurrentLine = UtilString.changeValueSeparator(sCurrentLine);
            String s[] = sCurrentLine.split(";");
            lat = Double.parseDouble(s[0]);
            lng = Double.parseDouble(s[1]);
            alt = Double.parseDouble(s[2]);
            if (firstTime && nRoute == 0){
                wps.addWaypoint(new Waypoint(Command.CMD_TAKEOFF, 0.0, 0.0, alt));
                firstTime = false;
            }
            wps.addWaypoint(new Waypoint(Command.CMD_WAYPOINT, lat, lng, alt));            
        }
        if (nRoute == mission3D.size() - 2){
            if (config.getActionAfterFinishMission().equals(Constants.COMMAND_LAND)){
                wps.addWaypoint(new Waypoint(Command.CMD_LAND, lat, lng, 0.0));
            }else if (config.getActionAfterFinishMission().equals(Constants.COMMAND_RTL)){
                wps.addWaypoint(new Waypoint(Command.CMD_RTL, 0.0, 0.0, 0.0));
            }
        }
    }
    
    private void readFileReadyRoute(String name, Mission wps) 
            throws FileNotFoundException, IOException{
        BufferedReader br = new BufferedReader(new FileReader(name));
        String sCurrentLine;
        boolean firstTime = true;
        double lat = 0;
        double lng = 0;
        double alt = 0;
        while ((sCurrentLine = br.readLine()) != null) {
            sCurrentLine = UtilString.changeValueSeparator(sCurrentLine);
            String s[] = sCurrentLine.split(";");
            lat = Double.parseDouble(s[0]);
            lng = Double.parseDouble(s[1]);
            alt = Double.parseDouble(s[2]); 
            if (firstTime){
                wps.addWaypoint(new Waypoint(Command.CMD_TAKEOFF, 0.0, 0.0, alt));
                firstTime = false;
            }
            wps.addWaypoint(new Waypoint(Command.CMD_WAYPOINT, lat, lng, alt));            
        }
        if (config.getActionAfterFinishMission().equals(Constants.COMMAND_LAND)){
            wps.addWaypoint(new Waypoint(Command.CMD_LAND, lat, lng, 0.0));
        }else if (config.getActionAfterFinishMission().equals(Constants.COMMAND_RTL)){
            wps.addWaypoint(new Waypoint(Command.CMD_RTL, 0.0, 0.0, 0.0));
        }
    }   
    
    private void monitoring() throws FileNotFoundException{
        int time = (int)(1000.0/Double.parseDouble(config.getFreqUpdateDataAP()));
        boolean isPartial = config.getFormDataAcquisition().equals(
                                Constants.FORM_DATA_ACQUISITION_PARTIAL);
        boolean isTotal = config.getFormDataAcquisition().equals(
                                Constants.FORM_DATA_ACQUISITION_TOTAL);
        PrintStream printLogStatus = new PrintStream("log-status.csv");    
        PrintStream printLogAircraft = new PrintStream(new File(config.getFileLogAircraft()));    
        printLogAircraft.println(drone.title());
        printLogStatus.println(drone.title2());
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                while(true){
                    try {
                        Thread.sleep(time);
                        if (isPartial){
                            communication.getGPS();
                            communication.getBattery();
                            communication.getAttitude();
                            communication.getVelocity();
                            communication.getHeading();
                            communication.getGroundSpeed();
                            communication.getAirSpeed();
                            communication.getGPSFix();
                            communication.getMode();
                            communication.getSystemStatus();
                            communication.getArmed();
                            communication.isArmable();                            
                            communication.getEkfOk();
                        }else if (isTotal){
                            communication.getAllInfo();
                        }
                        printLogAircraft.println(drone.toString());
                        printLogStatus.println(drone.toString2());
                        printLogAircraft.flush();
                        printLogStatus.flush();
                        drone.incrementTime(time);
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                        printLogAircraft.close();
                        printLogStatus.close();
                    } catch (IOException ex) {
                        ex.printStackTrace();                        
                        printLogAircraft.close();
                        printLogStatus.close();
                    }                       
                }
            }
        });
    }  
    
    private void readMission(){
        ReaderMission read = new ReaderMission(mission3D);
        read.reader(new File(config.getDirPlanner() + config.getFileWaypointsMission()));
    }
    
    private void updateStateMOSA(StateMOSA state){
        stateMOSA = state;
    } 
}
