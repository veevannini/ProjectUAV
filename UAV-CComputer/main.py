from server import runServer
from vehicleConnection import connectDrone, closeConnection
from status import getStatus

print 'connectDrone'
vehicle = connectDrone()

print 'getStatus'
getStatus(vehicle)

print 'runServer'
runServer(vehicle)

print 'closeConnection'
closeConnection(vehicle)
