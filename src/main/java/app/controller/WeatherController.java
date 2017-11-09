package app.controller;

import static app.event.EventStream.eventStream;
import static app.event.EventStream.joinStream;
import static app.event.EventStream.onEvent;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.concurrent.TimeUnit;

import org.kordamp.ikonli.javafx.FontIcon;

import app.MainApp;
import app.event.AirGiosEvent;
import app.event.ErrorEvent;
import app.event.OpenAndMeteoWeatherEvent;
import app.event.RefreshEvent;
import app.event.WeatherEvent;
import app.network.MeteoDataSource;
import app.network.OpenWeatherDataSource;
import app.network.WeatherDataSource;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.text.Text;
import rx.Observable;
import rx.observables.JavaFxObservable;
import rx.schedulers.JavaFxScheduler;
import util.Util;

public class WeatherController {
	private static final int ERROR_MSG_DURATION = 10;

	@FXML
	private Text temp;
	
	@FXML
	private Text pressure;
	
	@FXML
	private Text cloud;
	
	@FXML
	private Text windPower;
	
	@FXML
	private Text windDeg;
	
	@FXML
	private Text humidity;
	
	@FXML
	private Text pm25;
	
	@FXML
	private Text pm10;
	
	@FXML
	private Text update;
	
	@FXML
	private ComboBox<String> combo;
	
	@FXML
	private Button refreshButton;
	
	@FXML
	private Node errorIcon;
	
	@FXML
	private FontIcon cloudIcon;
	
	private MainApp mainApp;
	
	@FXML
	private void initialize() {
		initializeStatus();
		
		initializeRefreshButton();
		initializeComboBox();
	}
	
	private void initializeRefreshButton() {
		joinStream(JavaFxObservable.actionEventsOf(refreshButton).map(e -> new RefreshEvent()));
	}
	
	private void initializeComboBox() {
		joinStream(JavaFxObservable.valuesOf(combo.valueProperty()).skip(1)
				.map(ignore -> {
					int index = combo.getSelectionModel().getSelectedIndex();
					WeatherDataSource source;
					
					if (index == 0)
						source = new OpenWeatherDataSource();
					else
						source = new MeteoDataSource();
					
					mainApp.changeSource(source);
					return new RefreshEvent();
				}));
	}
	
	private void initializeStatus() {
		Observable<ErrorEvent> errors = eventStream().eventsInFx().ofType(ErrorEvent.class);
		errorIcon.visibleProperty()
				.bind(onEvent(errors, true).andOn(
						errors.throttleWithTimeout(ERROR_MSG_DURATION, TimeUnit.SECONDS, JavaFxScheduler.getInstance()),
						false).toBinding());
	}
	
	private String prepareTimestamp(LocalDateTime time) {
		DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM);
		return time.format(formatter).toString();
	}
	
	public void setSource(Observable<WeatherEvent> source) {		
		source.ofType(OpenAndMeteoWeatherEvent.class).subscribe(e -> {
			String icon = e.getCloudIcon();
			
			if (!icon.equals(Util.NO_ICON)) {
				cloudIcon.setIconLiteral(icon);
				cloudIcon.setVisible(true);
			} else {
				cloudIcon.setVisible(false);
			}
			
			temp.setText(e.getTemperature());
			pressure.setText(e.getPressure());
			cloud.setText(e.getClouds());
			windPower.setText(e.getWindPower());
			windDeg.setText(e.getWindDeg());
			humidity.setText(e.getHumidity());
			update.setText(prepareTimestamp(e.getTimestamp()));
		}); 

		source.ofType(AirGiosEvent.class).subscribe(e -> {
			pm25.setText(e.getPm25());
			pm10.setText(e.getPm10());
			update.setText(prepareTimestamp(e.getTimestamp()));
		});
	}
	
	public void setMainApp(MainApp app) {
		mainApp = app;
	}
}
