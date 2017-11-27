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
public class StatusUAV {
    
    public String mode;
    public String systemStatus;
    public boolean armed;
    public boolean isArmable;    
    public boolean ekfOk;

    public StatusUAV() {
    }

    public StatusUAV(String mode, String systemStatus, boolean armed, 
            boolean isArmable, boolean ekfOk) {
        this.mode = mode;
        this.systemStatus = systemStatus;
        this.armed = armed;
        this.isArmable = isArmable;        
        this.ekfOk = ekfOk;
    }
    
    public void parserInfoMode(String line) {
        try{
            line = line.substring(10, line.length() - 2);        
            this.mode = line;
        }catch (Exception ex){
            
        }
    }
    
    public void parserInfoSystemStatus(String line) {
        try{
            line = line.substring(19, line.length() - 2);        
            this.systemStatus = line;
        }catch (Exception ex){
            
        }
    }
    
    public void parserInfoArmed(String line) {
        try{
            line = line.substring(10, line.length() - 1);        
            this.armed = Boolean.parseBoolean(line);
        }catch (Exception ex){
            
        }
    }
    
    public void parserInfoIsArmable(String line) {
        try{
            line = line.substring(15, line.length() - 1);        
            this.isArmable = Boolean.parseBoolean(line);
        }catch (Exception ex){
            
        }
    }        
    
    public void parserInfoEkfOk(String line) {
        try{
            line = line.substring(11, line.length() - 1);        
            this.ekfOk = Boolean.parseBoolean(line);
        }catch (Exception ex){
            
        }
    }

    public void setMode(String mode) {
        this.mode = mode;
    }
    
    public void setSystemStatus(String systemStatus) {
        this.systemStatus = systemStatus;
    }

    public void setArmed(boolean armed) {
        this.armed = armed;
    }

    public void setIsArmable(boolean isArmable) {
        this.isArmable = isArmable;
    }   

    public void setEkfOk(boolean ekfOk) {
        this.ekfOk = ekfOk;
    }    

    @Override
    public String toString() {
        return "StatusUAV{" + "mode=" + mode + ", systemStatus=" + systemStatus + 
                ", armed=" + armed + ", isArmable=" + isArmable + ", ekfOk=" + ekfOk + '}';
    }
            
}
