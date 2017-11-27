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
 * @author jesimar
 */
public class Route {
    
    private final List<Waypoint> route = new LinkedList<>();
    
    public Route(){
        
    }
    
    public void addWaypoint(Waypoint wpt){
        route.add(wpt);
    }
    
    public List<Waypoint> getRoute(){
        return route;
    }
    
    public Waypoint getWaypoint(int i){
        return route.get(i);
    }
    
    public int size(){
        return route.size();
    }
    
    public void printRoute(){
        System.out.println("Route");
        for (Waypoint wpt: route){
            System.out.println(wpt.toString());
        }
    }
}
