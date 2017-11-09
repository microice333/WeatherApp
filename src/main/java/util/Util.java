package util;

public class Util {
	private static final String OPEN_API_SUNNY = "01d";
	private static final String OPEN_API_CLEAR_NIGHT = "01n";
	private static final String OPEN_API_CLOUDS_DAY_1 = "02d";
	private static final String OPEN_API_CLOUDS_DAY_2 = "03d";
	private static final String OPEN_API_CLOUDS_DAY_3 = "04d";
	private static final String OPEN_API_CLOUDS_NIGHT_1 = "02n";
	private static final String OPEN_API_CLOUDS_NIGHT_2 = "03n";
	private static final String OPEN_API_CLOUDS_NIGHT_3 = "04n";
	private static final String OPEN_API_SHOWER_DAY = "09d";
	private static final String OPEN_API_SHOWER_NIGHT = "09n";
	private static final String OPEN_API_RAIN_DAY = "10d";
	private static final String OPEN_API_RAIN_NIGHT = "10n";
	private static final String OPEN_API_THUNDERSTORM_DAY = "11d";
	private static final String OPEN_API_THUNDERSTORM_NIGHT = "11n";
	private static final String OPEN_API_SNOW_DAY = "13d";
	private static final String OPEN_API_SNOW_NIGHT = "13n";
	private static final String OPEN_API_FOG_DAY = "50d";
	private static final String OPEN_API_FOG_NIGHT = "50n";
	private static final String WI_SUNNY = "wi-day-sunny";
	private static final String WI_NIGHT_CLEAR = "wi-night-clear";
	private static final String WI_CLOUDS_DAY = "wi-day-cloudy";
	private static final String WI_NIGHT_CLOUDS = "wi-night-cloudy";
	private static final String WI_SHOWER_DAY = "wi-day-showers";
	private static final String WI_SHOWER_NIGHT = "wi-night-showers";
	private static final String WI_RAIN_DAY = "wi-day-rain";
	private static final String WI_RAIN_NIGHT = "wi-night-rain";
	private static final String WI_THUNDERSTORM_DAY = "wi-day-thunderstorm";
	private static final String WI_THUNDERSTORM_NIGHT = "wi-night-thunderstorm";
	private static final String WI_SNOW_DAY = "wi-day-snow";
	private static final String WI_SNOW_NIGHT = "wi-night-snow";
	private static final String WI_FOG_DAY = "wi-day-fog";
	private static final String WI_FOG_NIGHT = "wi-night-fog";
	public static final String NO_ICON = "";
	
	public static String parseIcon(String apiIcon) {
		if (apiIcon.equals(OPEN_API_SUNNY)) {
			return WI_SUNNY;
		} else if (apiIcon.equals(OPEN_API_CLEAR_NIGHT)) {
			return WI_NIGHT_CLEAR;
		} else if (apiIcon.equals(OPEN_API_CLOUDS_DAY_1) || apiIcon.equals(OPEN_API_CLOUDS_DAY_2)
				|| apiIcon.equals(OPEN_API_CLOUDS_DAY_3)) {
			return WI_CLOUDS_DAY;
		} else if (apiIcon.equals(OPEN_API_CLOUDS_NIGHT_1) || apiIcon.equals(OPEN_API_CLOUDS_NIGHT_2)
				|| apiIcon.equals(OPEN_API_CLOUDS_NIGHT_3)) {
			return WI_NIGHT_CLOUDS;
		} else if (apiIcon.equals(OPEN_API_SHOWER_DAY)) {
			return WI_SHOWER_DAY;
		} else if (apiIcon.equals(OPEN_API_SHOWER_NIGHT)) {
			return WI_SHOWER_NIGHT;
		} else if (apiIcon.equals(OPEN_API_RAIN_DAY)) {
			return WI_RAIN_DAY; 
		} else if (apiIcon.equals(OPEN_API_RAIN_NIGHT)) {
			return WI_RAIN_NIGHT;
		} else if (apiIcon.equals(OPEN_API_THUNDERSTORM_DAY)) {
			return WI_THUNDERSTORM_DAY;
		} else if (apiIcon.equals(OPEN_API_THUNDERSTORM_NIGHT)) {
			return WI_THUNDERSTORM_NIGHT;
		} else if (apiIcon.equals(OPEN_API_SNOW_DAY)) {
			return WI_SNOW_DAY;
		} else if (apiIcon.equals(OPEN_API_SNOW_NIGHT)) {
			return WI_SNOW_NIGHT;
		} else if (apiIcon.equals(OPEN_API_FOG_DAY)) {
			return WI_FOG_DAY;
		} else if (apiIcon.equals(OPEN_API_FOG_NIGHT)) {
			return WI_FOG_NIGHT;
		} 
		
		return NO_ICON;
	}
}
