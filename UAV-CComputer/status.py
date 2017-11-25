def getStatus(vehicle):
	print " Autopilot Firmware version: %s" % vehicle.version
	print "   Major version number: %s" % vehicle.version.major
	print "   Minor version number: %s" % vehicle.version.minor
	print "   Patch version number: %s" % vehicle.version.patch
	print "   Release type: %s" % vehicle.version.release_type()
	print "   Release version: %s" % vehicle.version.release_version()
	print "   Stable release?: %s" % vehicle.version.is_stable()
	print " Global Location: %s" % vehicle.location.global_frame
	print " Global Location (relative altitude): %s" % vehicle.location.global_relative_frame
	print " Local Location: %s" % vehicle.location.local_frame
	print " Attitude: %s" % vehicle.attitude
	print " Velocity: %s" % vehicle.velocity
	print " GPS: %s" % vehicle.gps_0
	print " Gimbal status: %s" % vehicle.gimbal
	print " Battery: %s" % vehicle.battery
	print " EKF OK?: %s" % vehicle.ekf_ok
	print " Last Heartbeat: %s" % vehicle.last_heartbeat
	print " Heading: %s" % vehicle.heading
	print " Is Armable?: %s" % vehicle.is_armable
	print " System status: %s" % vehicle.system_status.state
	print " Groundspeed: %s" % vehicle.groundspeed
	print " Airspeed: %s" % vehicle.airspeed
	print " Mode: %s" % vehicle.mode.name
	print " Armed: %s" % vehicle.armed
	print "\n Print all parameters of vehicle:"
	for key, value in vehicle.parameters.iteritems():
		print "  Key:%s Value:%s" % (key,value)
