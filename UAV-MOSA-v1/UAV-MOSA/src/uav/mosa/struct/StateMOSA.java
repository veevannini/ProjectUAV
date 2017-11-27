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
public enum StateMOSA {
    
    WAITING, PROCESSING, READY, DISABLED, COMPLETED;
    
    public static int getMode(StateMOSA mode){
        switch (mode) {
            case WAITING:
                return 0;
            case PROCESSING:
                return 1;
            case READY:
                return 2;
            case DISABLED:
                return 3;
            case COMPLETED:
                return 4;
            default:
                return -1;
        }
    }
}
