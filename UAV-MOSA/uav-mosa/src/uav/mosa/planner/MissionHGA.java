/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uav.mosa.planner;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Scanner;
import java.util.concurrent.Executors;
import uav.mosa.aircraft.Drone;
import uav.mosa.struct.Mission3D;
import uav.mosa.util.UtilIO;

/**
 *
 * @author jesimar
 */
public class MissionHGA extends Planner{
    
    public MissionHGA(Drone drone, Mission3D pointsMission) 
            throws IOException {
        super(drone, pointsMission);
    }
    
    @Override
    public void clearLogs() throws IOException{
        File src_ga = new File(dir + "ga-config-base");
        File dst_ga = new File(dir + "ga-config");
        UtilIO.copyFile(src_ga, dst_ga);        
        new File(dir + "log_error.txt").delete();        
        UtilIO.deleteFile(new File(dir), ".log");
        UtilIO.deleteFile(new File(dir), ".png");
    }
    
    @Override
    public void execMission(int i) throws IOException, InterruptedException{
        updateFileConfig();
        definePathAB(i);        
        execHGA();
        createFileFinalRoute(i);
    }
    
    private void updateFileConfig() throws FileNotFoundException{
        File src_ga = new File(dir + "ga-config-base");
        File dst_ga = new File(dir + "ga-config");
        String time = config.getTimeExec();
        String timeH = config.getTimeHorizon();
        String qtdWpt = config.getQtdWaypoints();
        String delta = config.getDelta();
        String maxVel = config.getMaxVelocity();
        String maxCtrl = config.getMaxControl();
        UtilIO.copyFileMofif(src_ga, dst_ga, time, 207, delta, 304, 
                qtdWpt, 425, timeH, 426, maxVel, 427, maxCtrl, 428);
    }
    
    private double vx1 = 0.0;
    private double vy1 = 0.0;
    private void definePathAB(int i) throws FileNotFoundException{
        double px1 = pointsMission.getPosition3D(i).getX();
        double py1 = pointsMission.getPosition3D(i).getY();
        double px2 = pointsMission.getPosition3D(i+1).getX();
        double py2 = pointsMission.getPosition3D(i+1).getY();
        double vx2 = 0;
        double vy2 = 0;
        if (i < pointsMission.size() - 2){
            double px3 = pointsMission.getPosition3D(i+2).getX();
            double py3 = pointsMission.getPosition3D(i+2).getY();        
            double dx = px3 - px1;
            double dy = py3 - py1;
            double norm = Math.sqrt(dx*dx+dy*dy);
            double vc = drone.getSpeedCruize();
            vx2 = dx * vc/norm;
            vy2 = dy * vc/norm;            
        }
        saveFileMissionConfig(px1, py1, px2, py2, vx1, vy1, vx2, vy2);
        vx1 = vx2;
        vy1 = vy2;
    }
    
    private void saveFileMissionConfig(double px1, double py1, double px2, double py2, 
            double vx1, double vy1, double vx2, double vy2) throws FileNotFoundException{
        PrintStream print = new PrintStream(new File(dir + "mission-config.sgl"));
        print.println("----------- start state (px py vx vy) -----------");
        print.println(px1 + "," + py1 + "," + vx1 + "," + vy1);        
        print.println("--------------- end point (px py)---------------");        
        print.println(px2 + "," + py2 + ","+ vx2 + "," + vy2);        
        print.println();
        print.println("<TrueName>");
        print.println("Config2D-2.sgl");
        print.close();
    }
    
    private void execHGA() throws IOException, InterruptedException{
        boolean print = false;
        boolean error = false;
        File f = new File(dir);
        final Process comp = Runtime.getRuntime().exec(config.getCmdExecPlanner(), null, f);
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                Scanner sc = new Scanner(comp.getInputStream());
                if (print) {
                    while (sc.hasNextLine()) {
                        System.out.println(sc.nextLine());
                    }
                }
                sc.close();
            }
        });
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                Scanner sc = new Scanner(comp.getErrorStream());
                if (error) {
                    while (sc.hasNextLine()) {
                        System.err.println("err:" + sc.nextLine());
                    }
                }
                sc.close();
            }
        });
        comp.waitFor();
    }
    
    private void createFileFinalRoute(int i) throws IOException{
        File src = new File(dir + "output-simulation.log");
        File dst = new File(dir + "out-sim"+i+".log");
        UtilIO.copyFile(src, dst);
        File route = new File(dir + "route3D"+i+".txt");
        
        Scanner sc = new Scanner(dst);
        PrintStream print = new PrintStream(route);  
        boolean test1 = false;
        boolean test2 = false;
        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            if (!test1 && !test2){
                if (line.equals("------------------------ [ states ] ------------------------")){
                    test1 = true;
                }
            }else if (test1 && !test2){
                if (!line.equals("------------------------ [ controls ] ------------------------")){
                    print.println(line);
                }else{
                    test2 = true;
                }
            }
        }
        sc.close();
        print.close();
    }
        
}
