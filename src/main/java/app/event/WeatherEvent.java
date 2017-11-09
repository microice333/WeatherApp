package app.event;

import java.time.LocalDateTime;

public abstract class WeatherEvent extends AppEvent {
	private final LocalDateTime timestamp;
	
	public WeatherEvent() {
		this.timestamp = LocalDateTime.now();
	}
	
	public LocalDateTime getTimestamp() {
		return timestamp;
	}
}
