/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uav.mosa.aircraft.sensors;

/**
 *
 * @author jesimar
 */
public class GPSInfo {
    
    public int fixType;//0-1: no fix, 2: 2D fix, 3: 3D fix
    public int satellitesVisible;//numero de satelites visiveis.

    public GPSInfo() {
    }

    public GPSInfo(int fix, int num_sat) {
        this.fixType = fix;
        this.satellitesVisible = num_sat;
    }
    
    public void parserInfoGPSInfo(String line) {
        try{
            line = line.substring(12, line.length() - 2);        
            String v[] = line.split(", ");
            this.fixType = Integer.parseInt(v[0]);        
            this.satellitesVisible = Integer.parseInt(v[1]);
        }catch (Exception ex){
            
        }
    } 

    public void setFixType(int fixType) {
        this.fixType = fixType;
    }

    public void setSatellitesVisible(int satellitesVisible) {
        this.satellitesVisible = satellitesVisible;
    }        

    @Override
    public String toString() {
        return "GPSInfo{" + "fixType=" + fixType + ", satellitesVisible=" + satellitesVisible + '}';
    }        
    
}
