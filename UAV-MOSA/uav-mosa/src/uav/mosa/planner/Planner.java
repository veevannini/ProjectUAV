/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uav.mosa.planner;

import java.io.IOException;
import uav.mosa.aircraft.Drone;
import uav.mosa.reader.ReaderLoadConfig;
import uav.mosa.struct.Mission3D;

/**
 *
 * @author jesimar
 */
public abstract class Planner {
    
    final ReaderLoadConfig config;
    final String dir;
    final Drone drone;
    final Mission3D pointsMission;    

    public Planner(Drone drone, Mission3D pointsMission) {
        this.config = ReaderLoadConfig.getInstance();
        this.dir = config.getDirPlanner();
        this.drone = drone;
        this.pointsMission = pointsMission;        
    }
            
    public abstract void execMission(int i) throws IOException, InterruptedException;
    
    public abstract void clearLogs() throws IOException;
}
