from dronekit import VehicleMode
import commands

def getGPS(request):
    vehicle = request['vehicle']
    gps = vehicle.location.global_relative_frame
    return {
        'gps': [gps.lat, gps.lon, gps.alt]
    }

def getBattery(request):
    vehicle = request['vehicle']
    bat = vehicle.battery
    return {
        'bat': [bat.voltage, bat.current, bat.level]
    }

def getAttitude(request):
    vehicle = request['vehicle']
    att = vehicle.attitude
    return {
        'att': [att.pitch, att.yaw, att.roll]
    }

def getVelocity(request):
    vehicle = request['vehicle']
    return {
        'vel': vehicle.velocity
    }

def getHeading(request):
    vehicle = request['vehicle']
    return {
        'heading': vehicle.heading
    }

def getGroundSpeed(request):
    vehicle = request['vehicle']
    return {
        'groundspeed': vehicle.groundspeed
    }

def getAirSpeed(request):
    vehicle = request['vehicle']
    return {
        'airspeed': vehicle.airspeed
    }

def getGPSfix(request):
    vehicle = request['vehicle']
    return {
        'gpsfix': [vehicle.gps_0.fix_type, vehicle.gps_0.satellites_visible]
    }

def getMode(request):
    vehicle = request['vehicle']
    return {
        'mode': vehicle.mode.name
    }
    
def getSystemStatus(request):
    vehicle = request['vehicle']
    return {
        'system-status': vehicle.system_status.state
    }

def getArmed(request):
    vehicle = request['vehicle']
    return {
        'armed': vehicle.armed
    }

def isArmable(request):
    vehicle = request['vehicle']
    return {
        'is-armable': vehicle.is_armable
    }

def getEkfOk(request):
    vehicle = request['vehicle']
    return {
        'ekf-ok': vehicle.ekf_ok
    }


def getAllInfo(request):
    vehicle = request['vehicle']
    gps = vehicle.location.global_relative_frame
    bat = vehicle.battery
    att = vehicle.attitude
    return {
        'allinfo': [gps.lat, gps.lon, gps.alt, bat.voltage, bat.current, bat.level, 
        att.pitch, att.yaw, att.roll, vehicle.heading, vehicle.groundspeed, 
        vehicle.airspeed, vehicle.gps_0.fix_type, vehicle.gps_0.satellites_visible, 
        vehicle.velocity, vehicle.mode.name, vehicle.system_status.state, 
        vehicle.armed, vehicle.is_armable, vehicle.ekf_ok]
    }

def setWaypoint(request):
    point = request['body']['waypoint']
    vehicle = request['vehicle']
    cmds = vehicle.commands
    cmds.download()
    cmds.wait_ready()
    finished = True if cmds.next == cmds.count else False
    print "finished status:"
    print finished
    if point['action'] == 'takeoff':
        commands.takeoff(vehicle, point['alt'], cmds)
    elif point['action'] == 'waypoint':
        commands.goto(point['lat'], point['lng'], point['alt'], cmds)
    elif point['action'] == 'land':
        commands.land(point['lat'], point['lng'], point['alt'], cmds)
    elif point['action'] == 'rtl':
        commands.rtl(cmds)
    cmds.upload()
    if finished:
        commands.startmission(vehicle)
    return {
        'status-set-waypoint': 'ok'
    }

def appendMission(request):
    mission = request['body']['mission']
    vehicle = request['vehicle']
    cmds = vehicle.commands    
    cmds.clear()
    for item in mission:
        if item['action'] == 'takeoff':
            commands.takeoff(vehicle, item['alt'], cmds)
        elif item['action'] == 'waypoint':
            commands.goto(item['lat'], item['lng'], item['alt'], cmds)
        elif item['action'] == 'land':
            commands.land(item['lat'], item['lng'], item['alt'], cmds)
        elif item['action'] == 'rtl':
            commands.rtl(cmds)
    cmds.upload()
    commands.startmission(vehicle)
    return {
        'status-append-mission': 'ok'
    }

def setMission(request):
    mission = request['body']['mission']
    vehicle = request['vehicle']
    cmds = vehicle.commands
    cmds.clear()
    for item in mission:
        if item['action'] == 'takeoff':
            commands.takeoff(vehicle, item['alt'], cmds)
        elif item['action'] == 'waypoint':
            commands.goto(item['lat'], item['lng'], item['alt'], cmds)
        elif item['action'] == 'land':
            commands.land(item['lat'], item['lng'], item['alt'], cmds)
        elif item['action'] == 'rtl':
            commands.rtl(cmds)
    cmds.upload()
    commands.startmission(vehicle)
    return {
        'status-set-mission': 'ok'
    }
