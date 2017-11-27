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
public class Mission3D {
    
    private final List<Position3D> mission = new LinkedList<>();
    
    public Mission3D(){
        
    }
    
    public void addPosition3D(Position3D wpt){
        mission.add(wpt);
    }
    
    public List<Position3D> getMission(){
        return mission;
    }
    
    public Position3D getPosition3D(int i){
        return mission.get(i);
    }
    
    public int size(){
        return mission.size();
    }
    
    public void printMission(){
        System.out.println("Mission3D");
        for (Position3D wpt: mission){
            System.out.println(wpt.toString());
        }
    }
}
