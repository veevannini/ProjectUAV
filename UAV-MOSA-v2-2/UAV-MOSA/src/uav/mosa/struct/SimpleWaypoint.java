/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uav.mosa.struct;

import uav.mosa.struct.basic.Waypoint;

/**
 *
 * @author Jesimar S. Arantes
 */
public class SimpleWaypoint {
    
    private Waypoint waypoint;
    
    public SimpleWaypoint(){
        
    }
    
    public void addWaypoint(Waypoint wpt){
        waypoint = wpt;
    }
    
    public Waypoint getWaypoint(){
        return waypoint;
    }
    
    public void printWaypoint(){
        System.out.println("Waypoint");        
        System.out.println(waypoint.toString());
    }
}
