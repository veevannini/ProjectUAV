/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uav.mosa.config;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 *
 * @author Jesimar S. Arantes
 */
public class LoadConfig {

    private final String dirPlanner;
    private final String fileWaypointsMission;

    private final Properties prop = new Properties();
    private final InputStream input = new FileInputStream("./config.properties");

    public LoadConfig() throws FileNotFoundException, IOException {
        prop.load(input);
        dirPlanner = prop.getProperty("prop.dir_planner");
        fileWaypointsMission = prop.getProperty("prop.file_waypoints_mission");        
        //printProperties();        
    }
    
    public void printProperties(){
        System.out.println(dirPlanner);
        System.out.println(fileWaypointsMission);
    }

    public String getDirPlanner() {
        return dirPlanner;
    }        

    public String getFileWaypointsMission() {
        return fileWaypointsMission;
    }        
}
