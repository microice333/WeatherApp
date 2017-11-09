package app.network;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import app.event.AirGiosEvent;
import io.reactivex.netty.RxNetty;
import rx.Observable;

public class AirGiosDataSource extends WeatherDataSource {
	private static final String URL = "http://powietrze.gios.gov.pl/pjp/current/getAQIDetailsList?param=AQI";
	private static final String STATION_JSON_KEY = "stationName";
	private static final String VALUES_JSON_KEY = "values";
	private static final String PM_25_JSON_KEY = "PM2.5";
	private static final String PM_10_JSON_KEY = "PM10";
	private static final String WARSAW = "Warszawa";

	@Override
	protected <T> Observable<AirGiosEvent> makeRequest() {
		return RxNetty.createHttpRequest(prepareHttpGETRequest(URL)).compose(this::unpackResponse)
				.map(JsonHelper::asJsonArray)
				.map(jsonArray -> {
					boolean foundParams = false;
					int i = 0;
					String pm25Str = PARAM_NO_AVAIBLE;
					String pm10Str = PARAM_NO_AVAIBLE;
					
					while (i < jsonArray.size() && !foundParams) {
						JsonObject current = jsonArray.get(i).getAsJsonObject();
						String station = current.get(STATION_JSON_KEY).getAsString();
						
						if (station.startsWith(WARSAW)) {
							JsonObject currentValues = current.get(VALUES_JSON_KEY).getAsJsonObject();
							JsonElement pm25 = currentValues.get(PM_25_JSON_KEY);
							JsonElement pm10 = currentValues.get(PM_10_JSON_KEY);
							
							if (pm25 != null)
								pm25Str = pm25.getAsString();
							
							if (pm10 != null)
								pm10Str = pm10.getAsString();
							
							foundParams = checkFound(pm25Str, pm10Str);
						}
						
						i++;
					}
					
					return new AirGiosEvent(pm25Str, pm10Str);
				});
	}

	private boolean checkFound(String pm25, String pm10) {
		return !pm25.equals(WeatherDataSource.PARAM_NO_AVAIBLE)
				&& !pm10.equals(WeatherDataSource.PARAM_NO_AVAIBLE);
	}
}
