package app.event;

import util.Util;

public class OpenAndMeteoWeatherEvent extends WeatherEvent {
	private final String temperature;
	private final String pressure;
	private final String clouds;
	private final String windPower;
	private final String windDeg;
	private final String humidity;
	private final String cloudIcon;
	
	public OpenAndMeteoWeatherEvent(String temperature, String pressure, String clouds, String windPower,
			String windDeg, String humidity, String icon) {
		this.temperature = temperature;
		this.pressure = pressure;
		this.clouds = clouds;
		this.windPower = windPower;
		this.windDeg = windDeg;
		this.humidity = humidity;
		this.cloudIcon = Util.parseIcon(icon);
	}
	
	public OpenAndMeteoWeatherEvent(String temperature, String pressure, String windPower,
			String windDeg, String humidity) {
		this.temperature = temperature;
		this.pressure = pressure;
		this.clouds = "-";
		this.cloudIcon = "";
		this.windPower = windPower;
		this.windDeg = windDeg;
		this.humidity = humidity;
	}
	
	public String getTemperature() {
		return temperature;
	}
	
	public String getPressure() {
		return pressure;
	}
	
	public String getClouds() {
		return clouds;
	}
	
	public String getWindPower() {
		return windPower;
	}
	
	public String getWindDeg() {
		return windDeg;
	}
	
	public String getHumidity() {
		return humidity;
	}
	
	public String getCloudIcon() {
		return cloudIcon;
	}
}
