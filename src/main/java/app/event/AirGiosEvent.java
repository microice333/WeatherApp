package app.event;

public class AirGiosEvent extends WeatherEvent {
	private final String pm25;
	private final String pm10;
	
	public AirGiosEvent(String pm25, String pm10) {
		this.pm25 = pm25;
		this.pm10 = pm10;
	}
	
	public String getPm25() {
		return pm25;
	}
	
	public String getPm10() {
		return pm10;
	}
}
