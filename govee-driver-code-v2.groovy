/**
 *  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 *  in compliance with the License. You may obtain a copy of the License at:
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed
 *  on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License
 *  for the specific language governing permissions and limitations under the License.
 *
 * 
 */

import hubitat.helper.ColorUtils

metadata {
	definition (
		name: "Govee LED Strips",
		version: "v1",
		namespace: "dbdevelopment",
		author: "Dean Berman",
		description: "Govee LED Strips Integration",
		category: "My Apps",
	) {

		capability "Switch Level"
		capability "Actuator"
		capability "Color Control"
		capability "ColorMode"
		// capability "Power Meter"
		capability "Switch"
		capability "Refresh"
		capability "Sensor"

		command "setAdjustedColor"
		command "reset"
		command "refresh"
	}

	tiles(scale: 2) {
		multiAttributeTile(name:"switch", type: "lighting", width: 6, height: 4, canChangeIcon: true) {
			tileAttribute ("device.switch", key: "PRIMARY_CONTROL") {
				attributeState "on", label:'${name}', action:"switch.off", icon:"st.lights.philips.hue-single", backgroundColor:"#00A0DC", nextState:"turningOff"
				attributeState "off", label:'${name}', action:"switch.on", icon:"st.lights.philips.hue-single", backgroundColor:"#ffffff", nextState:"turningOn"
				attributeState "turningOn", label:'${name}', action:"switch.off", icon:"st.lights.philips.hue-single", backgroundColor:"#00A0DC", nextState:"turningOff"
				attributeState "turningOff", label:'${name}', action:"switch.on", icon:"st.lights.philips.hue-single", backgroundColor:"#ffffff", nextState:"turningOn"
			}
			tileAttribute ("device.power", key: "SECONDARY_CONTROL") {
				attributeState "power", label:'Power level: ${currentValue}W', icon: "st.Appliances.appliances17"
			}
			tileAttribute ("device.level", key: "SLIDER_CONTROL") {
				attributeState "level", action:"switch level.setLevel"
			}
			tileAttribute ("device.color", key: "COLOR_CONTROL") {
				attributeState "color", action:"setAdjustedColor"
			}
		}

		multiAttributeTile(name:"switchNoPower", type: "lighting", width: 6, height: 4, canChangeIcon: true) {
			tileAttribute ("device.switch", key: "PRIMARY_CONTROL") {
				attributeState "on", label:'${name}', action:"switch.off", icon:"st.lights.philips.hue-single", backgroundColor:"#00A0DC", nextState:"turningOff"
				attributeState "off", label:'${name}', action:"switch.on", icon:"st.lights.philips.hue-single", backgroundColor:"#ffffff", nextState:"turningOn"
				attributeState "turningOn", label:'${name}', action:"switch.off", icon:"st.lights.philips.hue-single", backgroundColor:"#00A0DC", nextState:"turningOff"
				attributeState "turningOff", label:'${name}', action:"switch.on", icon:"st.lights.philips.hue-single", backgroundColor:"#ffffff", nextState:"turningOn"
			}
			tileAttribute ("device.level", key: "SLIDER_CONTROL") {
				attributeState "level", action:"switch level.setLevel"
			}
			tileAttribute ("device.color", key: "COLOR_CONTROL") {
				attributeState "color", action:"setAdjustedColor"
			}
		}

		multiAttributeTile(name:"switchNoSlider", type: "lighting", width: 6, height: 4, canChangeIcon: true) {
			tileAttribute ("device.switch", key: "PRIMARY_CONTROL") {
				attributeState "on", label:'${name}', action:"switch.off", icon:"st.lights.philips.hue-single", backgroundColor:"#00A0DC", nextState:"turningOff"
				attributeState "off", label:'${name}', action:"switch.on", icon:"st.lights.philips.hue-single", backgroundColor:"#ffffff", nextState:"turningOn"
				attributeState "turningOn", label:'${name}', action:"switch.off", icon:"st.lights.philips.hue-single", backgroundColor:"#00A0DC", nextState:"turningOff"
				attributeState "turningOff", label:'${name}', action:"switch.on", icon:"st.lights.philips.hue-single", backgroundColor:"#ffffff", nextState:"turningOn"
			}
			tileAttribute ("device.power", key: "SECONDARY_CONTROL") {
				attributeState "power", label:'The power level is currently: ${currentValue}W', icon: "st.Appliances.appliances17"
			}
			tileAttribute ("device.color", key: "COLOR_CONTROL") {
				attributeState "color", action:"setAdjustedColor"
			}
		}

		multiAttributeTile(name:"switchNoSliderOrColor", type: "lighting", width: 6, height: 4, canChangeIcon: true) {
			tileAttribute ("device.switch", key: "PRIMARY_CONTROL") {
				attributeState "on", label:'${name}', action:"switch.off", icon:"st.lights.philips.hue-single", backgroundColor:"#00A0DC", nextState:"turningOff"
				attributeState "off", label:'${name}', action:"switch.on", icon:"st.lights.philips.hue-single", backgroundColor:"#ffffff", nextState:"turningOn"
				attributeState "turningOn", label:'${name}', action:"switch.off", icon:"st.lights.philips.hue-single", backgroundColor:"#00A0DC", nextState:"turningOff"
				attributeState "turningOff", label:'${name}', action:"switch.on", icon:"st.lights.philips.hue-single", backgroundColor:"#ffffff", nextState:"turningOn"
			}
			tileAttribute ("device.power", key: "SECONDARY_CONTROL") {
				attributeState "power", label:'The light is currently consuming this amount of power: ${currentValue}W', icon: "st.Appliances.appliances17"
			}
		}

		valueTile("color", "device.color", inactiveLabel: false, decoration: "flat", width: 2, height: 2) {
			state "color", label: '${currentValue}'
		}

		standardTile("reset", "device.reset", inactiveLabel: false, decoration: "flat", width: 2, height: 2) {
			state "reset", label:"Reset Color", action:"reset", icon:"st.lights.philips.hue-single", defaultState: true
		}
		standardTile("refresh", "device.switch", inactiveLabel: false, decoration: "flat", width: 2, height: 2) {
			state "refresh", label:"", action:"refresh.refresh", icon:"st.secondary.refresh", defaultState: true
		}

		main(["switch"])
		details(["switch", "switchNoPower", "switchNoSlider", "switchNoSliderOrColor", "color", "refresh", "reset"])
	}
}

preferences {
	section("Govee LED Device") {
		// TODO: put inputs here
        input("apikey", "apikey", title: "Govee API Key", description: "Designated Govee API Key for accessing their system", required: true)
        input("deviceID", "deviceid", title: "Govee KED Device ID", description: "Device ID for the Govee LED Lights", required: true) 
        input(name: "modelnum", type: "enum", title: "Please select the Govee Model Number", options: ["H6160", "H6163", "H6104", "H6109", "H6110", "H6117",
            "H6159", "H7021", "H7022", "H6086", "H6089", "H6182", "H6085",
            "H7014", "H5081", "H6188", "H6135", "H6137", "H6141", "H6142",
            "H6195", "H7005", "H6083", "H6002", "H6003",
            "H6148","H6052","H6143","H6144","H6050","H6199","H6054","H5001"], defaultValue: "POST", required: true, displayDuringSetup: true)
        input name: "logEnable", type: "bool", title: "Enable debug logging", defaultValue: true
	}
}



/*
def DEBUG = "on"



//	----------------
//	SETTER FUNCTIONS
//	----------------
def set_DEBUG(id) {
  
	state.DEBUG = id
}

//	-----------------------
//	GETTER FUNCTIONS - URLS
//	-----------------------
def get_DEBUG() {  
    if (logEnable) 
    {
        return "on"    }
    else
    {
        return "off"
    } 
}
*/







def refresh() {
	////////////////////
	//////////////////// TURN THIS ON OR OFF to enable or disable debugging in the GUI
	//////////////////// VALUES: on, off
	////////////////////
  
	//log.debug "DEBUG MODE=" + get_DEBUG()
	
	
	
	if(logEnable) { log.trace "refresh()..." }
	
	def ret = callURL('devicestate', '')
	
	// Handling the power state
	def power = ret.data["properties"][1]["powerState"]
	if(logEnable) { log.debug "[refresh()] - Power=${power}" }
	if(power == 'on') {
		on(false)
	} else {
		off(false)
	}
	
	// Handling the brightness level
	def brightness = ret.data["properties"][2]["brightness"]
	if(logEnable) { log.debug "[refresh()] -- Brightness=${brightness}" }
	setLevel(brightness, false);
	
	// Handling the color
    
    if(logEnable) { log.debug "LOG ret.data --- Property3=${ret.data["properties"][3]}, Color=${ret.data["properties"][3]["color"]}}"  }
    List rgb = [ret.data["properties"][3]["color"]["r"], ret.data["properties"][3]["color"]["g"], ret.data["properties"][3]["color"]["b"]]
	if(logEnable) { log.debug "[refresh()] --- R=${rgb[0]}, G=${rgb[1]}, B=${rgb[2]}" }
	setColorRGB(rgb, false)
	
	unschedule()
	// Set it to run once a minute (continuous polling)
	if(logEnable) { log.debug "[refresh()] ---- Polling/Refresh every 2 minutes" }
	runIn(120, refresh)
}





// handle commands
def on(callapi=true) {
	if(logEnable) { log.trace "on(${callapi})..." }
	
	if(callapi) {
		if(logEnable) { log.debug "[on()] - Executing callURL for devicecontrol-power = on" }
		callURL('devicecontrol-power', "on")
	}
	
	if(logEnable) { log.debug "[on()] -- sendEvent to switch = on" }
	sendEvent(name: "switch", value: "on")
}

def off(callapi=true) {
	if(logEnable) { log.trace "off(${callapi})..." }
	
	if(callapi) {
		if(logEnable) { log.debug "[off()] - Executing callURL for devicecontrol-power = off" }
		callURL('devicecontrol-power', "off")
	}
	
	if(logEnable) { log.debug "[off()] -- sendEvent to switch = off" }
	sendEvent(name: "switch", value: "off")
}

/*
def parse(description) {
	if(logEnable) { log.trace "parse(${description})..." }
	
	def results = []
	def map = description
	if (description instanceof String)  {
		log.debug "Hue Bulb stringToMap - ${map}"
		map = stringToMap(description)
	}
	if (map?.name && map?.value) {
		results << createEvent(name: "${map?.name}", value: "${map?.value}")
	}
	results
}
def nextLevel() {
	if(logEnable) { log.trace "nextLevel()..." }
	
	def level = device.latestValue("level") as Integer ?: 0
	if (level <= 100) {
		level = Math.min(25 * (Math.round(level / 25) + 1), 100) as Integer
	}
	else {
		level = 25
	}
	setLevel(level, true)
}
*/
def setSaturation(percent) {
	if(logEnable) { log.trace "setSaturation(${percent})..." }
	
	log.info "[setSaturation()] - Govee API does not support SATURATION at this time, so this value is being set within Hubitat"
	
	//if(logEnable) { log.debug "[setSaturation()] - saturation = ${percent}" }
	sendEvent(name: "saturation", value: percent)
}

def setHue(percent) {
	if(logEnable) { log.trace "setHue(${percent})..." }
	
	log.info "[setHue()] - Govee API does not support HUB at this time, so this value is being set within Hubitat"
	
	//if(logEnable) { log.debug "[setHue()] - HUE = ${percent}" }
	sendEvent(name: "hue", value: percent)
}

def setLevel(percent, callapi=true) {
	if(logEnable) { log.trace "setLevel(${percent}, ${callapi})..." }
	
	if(logEnable) { log.debug "[setLevel()] - sendEvent level = ${percent}" }
	sendEvent(name: "level", value: percent)
	def power = Math.round(percent / 1.175) * 0.1
	
	if(callapi) {
		if(logEnable) { log.debug "[setLevel()] -- Executing callURL for devicecontrol-brightness = ${percent}" }
		callURL('devicecontrol-brightness', percent)
	}
}
def setColorRGB(rgb, callapi=true) {
	if(logEnable) { log.trace "setColorRGB(${rgb}, ${callapi})..." }
	
	// Handling the color
	if(logEnable) { log.debug "[setColorRGB()] - R=${rgb[0]}, G=${rgb[1]}, B=${rgb[2]}" }
	//def hex = colorUtil.rgbToHex(rgb[0], rgb[1], rgb[2])
	def hex = String.format("#%02X%02X%02X", rgb[0], rgb[1], rgb[2])
	if(logEnable) { log.debug "[setColorRGB()] -- HEX=${hex}" }
	sendEvent(name: "color", value: hex)
	
	if(callapi) {
		if(logEnable) { log.debug "[setColorRGB()] --- Executing callURL for devicecontrol-rgb = ${rgb}" }
		callURL('devicecontrol-rgb', rgb)
	}
}
def setColor(Map value) {
	if(logEnable) { log.trace "setColor(${value})..." }
	
	// Handling the color
	if(logEnable) { log.debug "[setColor()] - H=${value.hue}, S=${value.saturation}, L=${value.level}" }
	
	if(value.level == null) {
		value.level = device.currentValue("level")
		if(logEnable) { log.debug "[setColor()] -- RESET HSL - H=${value.hue}, S=${value.saturation}, L=${value.level}" }
	}
	
	if (value.hue) { sendEvent(name: "hue", value: value.hue)}
	if (value.saturation) { sendEvent(name: "saturation", value: value.saturation)}
	if (value.hex) { sendEvent(name: "color", value: value.hex)}
	if (value.level) { sendEvent(name: "level", value: value.level)}
	if (value.switch) { sendEvent(name: "switch", value: value.switch)}
	
	def rgb = hsl2rgb(value.hue, value.saturation, value.level)
	
	// Handling the color
	if(logEnable) { log.debug "[setColor()] -- R=${rgb[0]}, G=${rgb[1]}, B=${rgb[2]}" }
	def hex = String.format("#%02X%02X%02X", rgb[0], rgb[1], rgb[2])
	
	if(logEnable) { log.debug "[setColor(Map value)] --- HEX=${hex}" }
	sendEvent(name: "color", value: hex)
	
	if(logEnable) { log.debug "[setColor()] ---- Executing callURL for devicecontrol-rgb = ${rgb}" }
	callURL('devicecontrol-rgb', rgb)
}





def hsl2rgb(H, S, L) {
	if(logEnable) { log.trace "hsl2rgb(${H}, ${S}, ${L})..." }
	
	if(logEnable) { log.debug "[hsl2rgb()] - Getting RGB..." }
	List rgb = ColorUtils.hsvToRGB([H, S, L])
	if(logEnable) { log.debug "[hsl2rgb()] -- R=${rgb[0]}, G=${rgb[1]}, B=${rgb[2]}..." }
	
	return rgb
}
def hue2rgb(P, Q, T) {
	if(logEnable) { log.trace "hue2rgb(${P}, ${Q}, ${T})..." }
	
	if(logEnable) { log.debug "[hue2rgb()] - Calculating values..." }
	
	if(T < 0) T += 1;
	if(T > 1) T -= 1;
	if(T < 1/6) return P + (Q - P) * 6 * T;
	if(T < 1/2) return Q;
	if(T < 2/3) return P + (Q - P) * (2/3 - T) * 6;
	return P;
}







def reset() {
	if(logEnable) { log.trace "reset()..." }
	
	def ret = callURL('devicestate', '')
	
	// Handling the power state
	if(logEnable) { log.debug "[reset()] - Power=on" }
	on(true)
	
	// Handling the brightness level
	if(logEnable) { log.debug "[refresh()] -- Brightness=100" }
	setLevel(100, true);
	
	// Handling the color
	List rgb = [255, 255, 255]
	if(logEnable) { log.debug "[refresh()] --- R=${rgb[0]}, G=${rgb[1]}, B=${rgb[2]}" }
	setColorRGB(rgb, true)
}

def setAdjustedColor(value) {
	if(logEnable) { log.trace "setAdjustedColor(${value})..." }
	
	log.trace "[setAdjustedColor()] - Attempt to set Color"
	

	if (value) {
		log.trace "setAdjustedColor: ${value}"
		def adjusted = value + [:]
		adjusted.hue = adjustOutgoingHue(value.hue)
		// Needed because color picker always sends 100
		adjusted.level = null
		setColor(adjusted)
	}

}

def installed() {
	if(logEnable) { log.trace "installed()..." }
	
	refresh()
}

def updated() {
	if(logEnable) { log.trace "updated()..." }
	
	refresh()
	
	// unschedule()
	// runEvery1Minutes(refresh)
	// runIn(2, refresh)
}

def poll() {
	if(logEnable) { log.trace "poll()..." }
	
	refresh()
}

def adjustOutgoingHue(percent) {
	if(logEnable) { log.trace "adjustOutgoingHue(${percent})..." }
	
	log.error "[adjustOutgoingHue()] - Sorry but the Govee API does not support HUE at this time, so this is being skipped"
	
	/*
		def adjusted = percent
		if (percent > 31) {
			if (percent < 63.0) {
				adjusted = percent + (7 * (percent -30 ) / 32)
			}
			else if (percent < 73.0) {
				adjusted = 69 + (5 * (percent - 62) / 10)
			}
			else {
				adjusted = percent + (2 * (100 - percent) / 28)
			}
		}
		log.info "percent: $percent, adjusted: $adjusted"
		adjusted
	*/
}





// TODO: implement event handlers
def callURL(apiAction, details) {
	if(logEnable) { log.trace "callURL(${apiAction}, ${details})..." }
	
	if(logEnable) { log.debug "[callURL()] - APIKEY=${settings.apikey}, ID=${settings.deviceID}, MODEL=${settings.modelNum}" }
	
    def method
    def params
	if(apiAction == 'devices') {
        method = 'GET'
        params = [
            uri   : "https://developer-api.govee.com",
            path  : '/v1/devices',
			headers: ["Govee-API-Key": settings.apikey, "Content-Type": "application/json"],
        ]
	} else if(apiAction == 'devicestate') {
        method = 'GET'
        params = [
            uri   : "https://developer-api.govee.com",
            path  : '/v1/devices/state',
			headers: ["Govee-API-Key": settings.apikey, "Content-Type": "application/json"],
			query: [device: settings.deviceID, model: settings.modelNum],
        ]
	} else if(apiAction == 'devicecontrol-power') {
        method = 'PUT'
        params = [
            uri   : "https://developer-api.govee.com",
            path  : '/v1/devices/control',
			headers: ["Govee-API-Key": settings.apikey, "Content-Type": "application/json"],
			contentType: "application/json",
			body: [device: settings.deviceID, model: settings.modelNum, cmd: ["name": "turn", "value": details]],
        ]
	} else if(apiAction == 'devicecontrol-brightness') {
        method = 'PUT'
        params = [
            uri   : "https://developer-api.govee.com",
            path  : '/v1/devices/control',
			headers: ["Govee-API-Key": settings.apikey, "Content-Type": "application/json"],
			contentType: "application/json",
			body: [device: settings.deviceID, model: settings.modelNum, cmd: ["name": "brightness", "value": details]],
        ]
	} else if(apiAction == 'devicecontrol-rgb') {
		method = 'PUT'
        params = [
            uri   : "https://developer-api.govee.com",
            path  : '/v1/devices/control',
			headers: ["Govee-API-Key": settings.apikey, "Content-Type": "application/json"],
			contentType: "application/json",
			body: [device: settings.deviceID, model: settings.modelNum, cmd: ["name": "color", "value": ["r": details[0], "g": details[1], "b": details[2]]]],
        ]
    }
    
	if(logEnable) {
		/*
			log.debug params
			log.debug "APIACTION=${apiAction}"
			log.debug "METHOD=${method}"
			log.debug "URI=${params.uri}${params.path}"
			log.debug "HEADERS=${params.headers}"
			log.debug "QUERY=${params.query}"
			log.debug "BODY=${params.body}"
		*/
	}
	
	try {
		if(method == 'GET') {
			httpGet(params) { resp ->
				//log.debug "RESP="
				//log.debug "HEADERS="+resp.headers
				//log.debug "DATA="+resp.data
				
				if(logEnable) { log.debug "[callURL()] -- response.data="+resp.data }
				
				return resp.data
			}
		} else if(method == 'PUT') {
			httpPutJson(params) { resp ->
				//log debug "RESP="
				//log.debug "HEADERS="+resp.headers
				//log.debug "DATA="+resp.data
				
				if(logEnable) { log.debug "[callURL()] -- response.data="+resp.data }
				
				return resp.data
			}
		}
	} catch (groovyx.net.http.HttpResponseException e) {
		log.error "callURL() >>>>>>>>>>>>>>>> ERROR >>>>>>>>>>>>>>>>"
		log.error "Error: e.statusCode ${e.statusCode}"
		log.error "${e}"
		log.error "callURL() <<<<<<<<<<<<<<<< ERROR <<<<<<<<<<<<<<<<"
		
		return false
	}
}
