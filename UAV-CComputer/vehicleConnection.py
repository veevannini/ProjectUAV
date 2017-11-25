from dronekit import connect

def connectDrone():
    return connect('127.0.0.1:14551', wait_ready=True)

def closeConnection(vehicle):
    vehicle.close()
