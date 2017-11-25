from dronekit import Command, mavutil, VehicleMode
import time

def takeoff(vehicle, aTargetAltitude, cmds):
    arm_and_takeoff(vehicle, aTargetAltitude)
    cmds.add(Command(0, 0, 0, mavutil.mavlink.MAV_FRAME_GLOBAL_RELATIVE_ALT, mavutil.mavlink.MAV_CMD_NAV_TAKEOFF, 0, 0, 0, 0, 0, 0, 0, 0, aTargetAltitude))

def goto(x,y,z, cmds):
    cmds.add(Command(0, 0, 0, mavutil.mavlink.MAV_FRAME_GLOBAL_RELATIVE_ALT, mavutil.mavlink.MAV_CMD_NAV_WAYPOINT, 0, 0, 0, 0, 0, 0, x, y, z))

def land(x,y,z, cmds):
    cmds.add(Command(0, 0, 0, mavutil.mavlink.MAV_FRAME_GLOBAL_RELATIVE_ALT, mavutil.mavlink.MAV_CMD_NAV_LAND, 0, 0, 0, 0, 0, 0, x, y, z))

def rtl(cmds):
    cmds.add(Command(0, 0, 0, mavutil.mavlink.MAV_FRAME_GLOBAL_RELATIVE_ALT, mavutil.mavlink.MAV_CMD_NAV_RETURN_TO_LAUNCH, 0, 0, 0, 0, 0, 0, 0, 0, 0))

def startmission(vehicle):
    vehicle.mode = VehicleMode("GUIDED")
    vehicle.mode = VehicleMode("AUTO")
    msg = vehicle.message_factory.command_long_encode(
        0, 0,  # target_system, target_component
        mavutil.mavlink.MAV_CMD_MISSION_START,  # command
        0,  # confirmation
        0,  # param 1, yaw in degrees
        0,  # param 2, yaw speed deg/s
        0,  # param 3, direction -1 ccw, 1 cw
        0,  # param 4, relative offset 1, absolute angle 0
        0, 0, 0)  # param 5 ~ 7 not used
    # send command to vehicle
    vehicle.send_mavlink(msg)

def arm_and_takeoff(vehicle, aTargetAltitude, wait=False):
    print "Mode: ", vehicle.mode.name
    print "GPS: ", vehicle.gps_0.fix_type
    print "Is-Armable: ", vehicle.is_armable
    if vehicle.mode.name == "INITIALISING":
        print "Waiting for vehicle to initialise"
        time.sleep(1)
    while vehicle.gps_0.fix_type < 2:
        print "Waiting for GPS...:", vehicle.gps_0.fix_type
        time.sleep(1)

    print "Mode: ", vehicle.mode.name
    print "GPS: ", vehicle.gps_0.fix_type
    print "Is-Armable: ", vehicle.is_armable
    
    #print "Basic pre-arm checks"
    # Don't let the user try to arm until autopilot is ready
    #while not vehicle.is_armable:
    #    print " Waiting for vehicle to initialise..."
    #    print "Mode: ", vehicle.mode.name
    #    print "GPS: ", vehicle.gps_0.fix_type
    #    time.sleep(1)

    print "Arming motors"
    # Copter should arm in GUIDED mode
    vehicle.mode = VehicleMode("GUIDED")
    vehicle.armed = True

    while not vehicle.armed:
        print " Waiting for arming..."
        time.sleep(1)

    print "Taking off!"
    vehicle.simple_takeoff(aTargetAltitude)

    # Wait until the vehicle reaches a safe height before processing the goto 
    # (otherwise the command after Vehicle.simple_takeoff will execute immediately).
    while wait:
        print " Altitude: ", vehicle.location.global_relative_frame.alt
        if vehicle.location.global_relative_frame.alt >= aTargetAltitude * 0.95:  # Trigger just below target alt.
            print "Reached target altitude"
            break
        time.sleep(1)
