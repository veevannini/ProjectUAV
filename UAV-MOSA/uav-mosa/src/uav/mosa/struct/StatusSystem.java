/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uav.mosa.struct;

/**
 *
 * @author jesimar
 */
public enum StatusSystem {
    
    WORKING, FAIL_ENGINE, FAIL_BATTERY, FAIL_GPS;
    
    public static int getStatus(StatusSystem status){
        switch (status) {
            case WORKING:
                return 0;
            case FAIL_ENGINE:
                return 1;
            case FAIL_BATTERY:
                return 2;
            case FAIL_GPS:
                return 3;            
            default:
                return -1;
        }
    }
    
    public static StatusSystem getStatusSystem(int value){        
        switch (value){
            case 0: 
                return StatusSystem.WORKING;
            case 1: 
                return StatusSystem.FAIL_ENGINE;
            case 2: 
                return StatusSystem.FAIL_BATTERY;
            case 3: 
                return StatusSystem.FAIL_GPS;
            default: 
                return StatusSystem.WORKING;
        }        
    }
}
