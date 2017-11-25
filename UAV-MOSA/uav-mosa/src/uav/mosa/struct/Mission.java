/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uav.mosa.struct;

import uav.mosa.struct.basic.Waypoint;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Jesimar S. Arantes
 */
public class Mission {
    
    private final List<Waypoint> mission = new LinkedList<>();
    
    public Mission(){
        
    }
    
    public void addWaypoint(Waypoint wpt){
        mission.add(wpt);
    }
    
    public List<Waypoint> getMission(){
        return mission;
    }
    
    public Waypoint getWaypoint(int i){
        return mission.get(i);
    }
    
    public void removeWaypoint(){
        mission.remove(mission.size()-1);
    }
    
    public int size(){
        return mission.size();
    }
    
    public void printMission(){
        System.out.println("Mission");
        for (Waypoint wpt: mission){
            System.out.println(wpt.toString());
        }
    }
}
