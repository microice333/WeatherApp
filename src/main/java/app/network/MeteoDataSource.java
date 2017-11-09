package app.network;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import app.event.OpenAndMeteoWeatherEvent;
import io.reactivex.netty.RxNetty;
import rx.Observable;

public class MeteoDataSource extends WeatherDataSource {
	private static final String URL = "http://www.meteo.waw.pl/";
	private static final Pattern TEMP_RE = Pattern.compile("<span id=\"PARAM_0_TA\">([0-9]*,[0-9]*)"
			+ "<\\/span>[\\s\\S]*<span id=\"PARAM_0_RH\">([0-9]*,[0-9]*)"
			+ "<\\/span>[\\s\\S]*<span id=\"PARAM_0_PR\">([0-9]*,[0-9]*)"
			+ "<\\/span>[\\s\\S]*<span id=\"PARAM_0_WV\">([0-9]*,[0-9]*)"
			+ "<\\/span>[\\s\\S]*<strong id=\"PARAM_WD\">([0-9]*,[0-9]*)<\\/strong>",
			Pattern.CASE_INSENSITIVE);
	
	@Override
	protected <T> Observable<OpenAndMeteoWeatherEvent> makeRequest() {
		return RxNetty.createHttpRequest(prepareHttpGETRequest(URL)).compose(this::unpackResponse)
				.map(htmlSource -> {
					Matcher m = TEMP_RE.matcher(htmlSource);
					String temp = WeatherDataSource.PARAM_NO_AVAIBLE;
					String humidity = WeatherDataSource.PARAM_NO_AVAIBLE;
					String pressure = WeatherDataSource.PARAM_NO_AVAIBLE;
					String windSpeed = WeatherDataSource.PARAM_NO_AVAIBLE;
					String windDeg = WeatherDataSource.PARAM_NO_AVAIBLE;
					
					if (m.find()) {
						temp = m.group(1).trim().replace(',', '.');
						humidity = m.group(2).trim().replace(',', '.');
						pressure = m.group(3).trim().replace(',', '.');
						windSpeed = m.group(4).trim().replace(',', '.');
						windDeg = m.group(5).trim().replace(',', '.');
					}
					
					return new OpenAndMeteoWeatherEvent(temp, pressure, windSpeed, windDeg, humidity);
				});
	}

}
