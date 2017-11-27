/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uav.mosa.reader;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import uav.mosa.struct.Constants;

/**
 *
 * @author Jesimar S. Arantes
 */
public class ReaderLoadConfig {
    
    private static final ReaderLoadConfig instance = new ReaderLoadConfig();
    private Properties prop;
    private InputStream input;

    //global
    private String nameDrone;
    private String systemExec;
    private String actionAfterFinishMission;
    private String formDataAcquisition;
    private String freqUpdateDataAP;
    private String fileLogAircraft;
    private String routeDynamic;
    
    //planner
    private String dirPlanner;
    private String cmdExecPlanner;
    private String fileWaypointsMission; 
    private String fileGeoBase;    
    private String timeExec;
    private String qtdWaypoints;
    private String timeHorizon;
    private String delta;
    private String maxVelocity;
    private String maxControl;    
    private String altitudeRelativeMission;
    
    //routeready    
    private String dirRouteReady;
    private String fileRouteReady;   

    private ReaderLoadConfig(){
        try {
            prop = new Properties();
            input = new FileInputStream("./config.properties");
            prop.load(input);
            
            nameDrone                = prop.getProperty("prop.global.name_aircraft");
            systemExec               = prop.getProperty("prop.global.system_exec");
            actionAfterFinishMission = prop.getProperty("prop.global.action_after_finish_mission");
            formDataAcquisition      = prop.getProperty("prop.global.form_data_acquisition");
            freqUpdateDataAP         = prop.getProperty("prop.global.freq_update_data_ap");
            fileLogAircraft          = prop.getProperty("prop.global.file_log_aircraft");             
            routeDynamic             = prop.getProperty("prop.global.route_dynamic");
            
            dirPlanner               = prop.getProperty("prop.planner.dir");
            cmdExecPlanner           = prop.getProperty("prop.planner.cmd_exec");
            fileWaypointsMission     = prop.getProperty("prop.planner.file_waypoints_mission");         
            fileGeoBase              = prop.getProperty("prop.planner.file_geo_base");
            timeExec                 = prop.getProperty("prop.planner.time_exec");
            qtdWaypoints             = prop.getProperty("prop.planner.qtd_waypoints");
            timeHorizon              = prop.getProperty("prop.planner.time_horizon");
            delta                    = prop.getProperty("prop.planner.delta");
            maxVelocity              = prop.getProperty("prop.planner.max_velocity");
            maxControl               = prop.getProperty("prop.planner.max_control");                        
            altitudeRelativeMission  = prop.getProperty("prop.planner.altitude_relative_mission");
            
            dirRouteReady            = prop.getProperty("prop.route_ready.dir");
            fileRouteReady           = prop.getProperty("prop.route_ready.file_waypoints");
            checkReadFields();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    public static ReaderLoadConfig getInstance() {
        return instance;
    }
    
    public void printProperties(){
        System.out.println(nameDrone);
        System.out.println(systemExec);
        System.out.println(actionAfterFinishMission);
        System.out.println(formDataAcquisition);
        System.out.println(freqUpdateDataAP);
        System.out.println(fileLogAircraft);
        System.out.println(routeDynamic);
        
        System.out.println(dirPlanner);
        System.out.println(cmdExecPlanner);
        System.out.println(fileWaypointsMission);        
        System.out.println(fileGeoBase);               
        System.out.println(timeExec);
        System.out.println(qtdWaypoints);
        System.out.println(timeHorizon);
        System.out.println(delta);
        System.out.println(maxVelocity);
        System.out.println(maxControl);        
        System.out.println(altitudeRelativeMission);
                
        System.out.println(dirRouteReady);
        System.out.println(fileRouteReady);        
    }
    
    private void checkReadFields(){
        if (!nameDrone.equals(Constants.NAME_DRONE_ARARINHA) && 
                !nameDrone.equals(Constants.NAME_DRONE_iDRONE_ALPHA)){
            System.err.println("name of aircraft not valid");
        }
        if (!systemExec.equals(Constants.SYS_EXEC_ROUTE_READY) && 
                !systemExec.equals(Constants.SYS_EXEC_PLANNER) && 
                !systemExec.equals(Constants.SYS_EXEC_GPS_ANALYSER) && 
                !systemExec.equals(Constants.SYS_EXEC_POS_ANALYSER)){
            System.err.println("type of route not valid");
        }        
        if (!actionAfterFinishMission.equals(Constants.COMMAND_NONE) && 
                !actionAfterFinishMission.equals(Constants.COMMAND_LAND) && 
                !actionAfterFinishMission.equals(Constants.COMMAND_RTL)){
            System.err.println("action after finish mission not valid");
        }
        if (!formDataAcquisition.equals(Constants.FORM_DATA_ACQUISITION_TOTAL) && 
                !formDataAcquisition.equals(Constants.FORM_DATA_ACQUISITION_PARTIAL)){
            System.err.println("form data acquisition not valid");
        }
    }
    
    public String getNameDrone() {
        return nameDrone;
    }
    
    public String getSystemExec() {
        return systemExec;
    }
    
    public String getActionAfterFinishMission() {
        return actionAfterFinishMission;
    }
    
    public String getFormDataAcquisition() {
        return formDataAcquisition;
    }

    public String getFreqUpdateDataAP() {
        return freqUpdateDataAP;
    }   

    public String getFileLogAircraft() {
        return fileLogAircraft;
    }
    
    public String getRouteDynamic() {
        return routeDynamic;
    } 

    public String getDirPlanner() {
        return dirPlanner;
    }

    public String getCmdExecPlanner() {
        return cmdExecPlanner;
    }       

    public String getFileWaypointsMission() {
        return fileWaypointsMission;
    } 

    public String getFileGeoBase() {
        return fileGeoBase;
    }

    public String getTimeExec() {
        return timeExec;
    }

    public String getQtdWaypoints() {
        return qtdWaypoints;
    }

    public String getTimeHorizon() {
        return timeHorizon;
    }

    public String getDelta() {
        return delta;
    }

    public String getMaxVelocity() {
        return maxVelocity;
    }

    public String getMaxControl() {
        return maxControl;
    }
    
    public String getAltitudeRelativeMission() {
        return altitudeRelativeMission;
    }
    
    public String getDirRouteReady() {
        return dirRouteReady;
    }

    public String getFileRouteReady() {
        return fileRouteReady;
    }        
}