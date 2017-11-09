package app.event;

public class OpenWeatherEvent extends WeatherEvent {
	private final float temperature;
	private final float pressure;
	private final String clouds;
	private final float windPower;
	private final float windDeg;
	private final float humidity;
	
	public OpenWeatherEvent(float temperature, float pressure, String clouds, float windPower,
			float windDeg, float humidity) {
		this.temperature = temperature;
		this.pressure = pressure;
		this.clouds = clouds;
		this.windPower = windPower;
		this.windDeg = windDeg;
		this.humidity = humidity;
	}
	
	public float getTemperature() {
		return temperature;
	}
	
	public float getPressure() {
		return pressure;
	}
	
	public String getClouds() {
		return clouds;
	}
	
	public float getWindPower() {
		return windPower;
	}
	
	public float getWindDeg() {
		return windDeg;
	}
	
	public float getHumidity() {
		return humidity;
	}
}
