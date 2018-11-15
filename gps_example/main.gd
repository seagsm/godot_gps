extends Node2D

var admob = null
var gps_tracker = null

var isReal = false
var isTop = true
var count = 0

func _ready():
	if(Engine.has_singleton("GpsModule")):
		gps_tracker = Engine.get_singleton("GpsModule")
		if gps_tracker != null:
			get_node("Label").text = "HERE IS"
#			get_node("CheckId").text = gps_tracker.getCheckId()
#           get_node("LabelLatitude").text = gps_tracker.getStringLatitude()
#		    get_node("LabelLongitude").text = gps_tracker.getStringLongitude()
#			gps_tracker.getLocation()
#			get_node("LabelLatitude").text = gps_tracker.getProvider()
#			gps_tracker.getInit()


			gps_tracker.getInit()
									
			var resG = int(gps_tracker.getGPSState())
			if resG == 1 :
				get_node("isGps").text = "GPS_ON"
			else:
				get_node("isGps").text = "GPS_OFF"	

			var resN = int(gps_tracker.getNetworkState())
			if resN == 1 :
				get_node("isNetwork").text = "NET_ON"
			else:
				get_node("isNetwork").text = "NET_OFF"	


#			gps_tracker.getInit()


func _on_Button_pressed():
	if gps_tracker != null:
#		gps_tracker.get_Latitude()
#		gps_tracker.get_Longitude()
		gps_tracker.updateLocation()
		get_node("LabelLatitude").text = gps_tracker.getStringLatitude()
		get_node("LabelLongitude").text = gps_tracker.getStringLongitude()
	else:
		get_node("LabelLatitude").text = "NUUUL"
		get_node("LabelLongitude").text = "NUUUL"
		
	var resG = int(gps_tracker.getGPSState())
	if resG == 1 :
		get_node("isGps").text = "GPS_ON"
	else:
		get_node("isGps").text = "GPS_OFF"	

	var resN = int(gps_tracker.getNetworkState())
	if resN == 1 :
		get_node("isNetwork").text = "NET_ON"
	else:
		get_node("isNetwork").text = "NET_OFF"		


func _process(delta):
	count = count + 1
	get_node("getInit").text = str(count)
	if gps_tracker != null:
#		gps_tracker.get_Latitude()
#		gps_tracker.get_Longitude()
#		gps_tracker.updateLocation()
		
		get_node("LabelLatitude").text = gps_tracker.getStringLatitude()
		get_node("LabelLongitude").text = gps_tracker.getStringLongitude()
		
		get_node("Altitude").text = gps_tracker.getStringAltitude()
		get_node("GpsTime").text = gps_tracker.getStringGPSTime()
		get_node("GpsSpeed").text = gps_tracker.getStringGPSSpeed()
		get_node("GpsBearing").text = gps_tracker.getStringGPSBearing()
		get_node("GpsAccuracy").text = gps_tracker.getStringGPSAccuracy()
		
		var resG = int(gps_tracker.getGPSState())
		if resG == 1 :
			get_node("isGps").text = "GPS_ON"
		else:
			get_node("isGps").text = "GPS_OFF"	

		var resN = int(gps_tracker.getNetworkState())
		if resN == 1 :
			get_node("isNetwork").text = "NET_ON"
		else:
			get_node("isNetwork").text = "NET_OFF"	
	else:
		get_node("LabelLatitude").text = "NUUUL"
		get_node("LabelLongitude").text = "NUUUL"
		
	













