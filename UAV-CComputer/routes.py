import views

GET_URLS = {
    '/gps/': views.getGPS,
    '/bat/': views.getBattery,
    '/att/': views.getAttitude,
    '/vel/': views.getVelocity,
    '/heading/': views.getHeading,
    '/groundspeed/': views.getGroundSpeed,
    '/airspeed/': views.getAirSpeed,
    '/gpsfix/': views.getGPSfix,    
    '/mode/': views.getMode,
    '/system-status/': views.getSystemStatus,
    '/armed/': views.getArmed,
    '/is-armable/': views.isArmable,
    '/ekf-ok/': views.getEkfOk,
    '/allinfo/': views.getAllInfo
}

POST_URLS = {
    '/set-waypoint/': views.setWaypoint,
    '/append-mission/': views.appendMission,
    '/set-mission/': views.setMission
}
