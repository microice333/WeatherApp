package app.event;

import app.network.WeatherDataSource;

public class ChangeSourceEvent extends AppEvent {
	private WeatherDataSource source;
	
	public ChangeSourceEvent(WeatherDataSource source) {
		this.source = source;
	}
	
	public WeatherDataSource getSource() {
		return source;
	}
}
