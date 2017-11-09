package app.network;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import app.event.OpenAndMeteoWeatherEvent;
import io.reactivex.netty.RxNetty;
import rx.Observable;

public class OpenWeatherDataSource extends WeatherDataSource {
	private static final String URL = "http://api.openweathermap.org/data/2.5/weather?q=Warsaw&units=metric&APPID=<here_should_be_yoursID>";
	private static final String MAIN_JSON_KEY = "main";
	private static final String TEMP_JSON_KEY = "temp";
	private static final String PRESSUREE_JSON_KEY = "pressure";
	private static final String HUMIDITY_JSON_KEY = "humidity";
	private static final String WIND_JSON_KEY = "wind";
	private static final String WIND_SPEED_JSON_KEY = "speed";
	private static final String WIND_DEG_JSON_KEY = "deg";
	private static final String WEATHER_JSON_KEY = "weather";
	private static final String WEATHER_DESCRIPTION_JSON_KEY = "description";
	private static final String WEATHER_ICON_JSON_KEY = "icon";
	
	@Override
	protected <T> Observable<OpenAndMeteoWeatherEvent> makeRequest() {
		return RxNetty.createHttpRequest(prepareHttpGETRequest(URL))
				.compose(this::unpackResponse).map(JsonHelper::asJsonObject) 
				.map(jsonObject -> { 
					JsonObject main = jsonObject.getAsJsonObject(MAIN_JSON_KEY);
					String temp = checkParam(main.get(TEMP_JSON_KEY));
					String pressure = checkParam(main.get(PRESSUREE_JSON_KEY));
					String humidity = checkParam(main.get(HUMIDITY_JSON_KEY));
					
					JsonObject wind = jsonObject.getAsJsonObject(WIND_JSON_KEY);
					String speed = checkParam(wind.get(WIND_SPEED_JSON_KEY));
					String deg = checkParam(wind.get(WIND_DEG_JSON_KEY));
						
					JsonArray weather = jsonObject.getAsJsonArray(WEATHER_JSON_KEY);
					String clouds = checkParam(weather.get(0).getAsJsonObject().get(WEATHER_DESCRIPTION_JSON_KEY));
					String icon = checkParam(weather.get(0).getAsJsonObject().get(WEATHER_ICON_JSON_KEY));
					
					return new OpenAndMeteoWeatherEvent(temp, pressure, clouds, speed, deg, humidity, icon);
				});
	}
	
	private String checkParam(JsonElement elem) {
		if (elem != null)
			return elem.getAsString();
		
		return WeatherDataSource.PARAM_NO_AVAIBLE;
	}
}
