/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uav.mosa.struct;

import uav.mosa.struct.basic.Position3D;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author jesimar
 */
public class Route3D {
    
    private final List<Position3D> route = new LinkedList<>();
    
    public Route3D(){
        
    }
    
    public void addPosition3D(Position3D wpt){
        route.add(wpt);
    }
    
    public List<Position3D> getRoute(){
        return route;
    }
    
    public Position3D getPosition3D(int i){
        return route.get(i);
    }
    
    public int size(){
        return route.size();
    }
    
    public void printRoute(){
        System.out.println("Route3D");
        for (Position3D wpt: route){
            System.out.println(wpt.toString());
        }
    }
}
