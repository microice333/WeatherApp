package app;

import static app.event.EventStream.eventStream;
import static app.event.EventStream.joinStream;

import java.util.LinkedList;
import java.util.List;

import app.controller.WeatherController;
import app.event.WeatherEvent;
import app.network.AirGiosDataSource;
import app.network.OpenWeatherDataSource;
import app.network.WeatherDataSource;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import rx.Subscription;


public class MainApp extends Application {
	private static final String FXML_MAIN_FORM = "/fxml/main.fxml";
	
	private Stage mainStage;
	
	private List<Subscription> sourceStreams = new LinkedList<>();

	@Override
	public void start(Stage primaryStage) throws Exception {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(MainApp.class.getResource(FXML_MAIN_FORM));
		mainStage = primaryStage;
		
		setupDataSources();
		
		Pane rootLayout = loader.load();
		
		WeatherController controller = loader.getController();
		controller.setMainApp(this);
		controller.setSource(eventStream().eventsInFx().ofType(WeatherEvent.class));
		
		Scene scene = new Scene(rootLayout);
		
		mainStage.setScene(scene);
		
		mainStage.setResizable(false);
		
		mainStage.show();
	}
	
	private void setupDataSources() {
		WeatherDataSource[] sources = { new OpenWeatherDataSource(), new AirGiosDataSource()};
		for (WeatherDataSource source : sources) {
			sourceStreams.add(joinStream(source.dataSourceStream()));
		}
	}
	
	public void changeSource(WeatherDataSource source) {
		sourceStreams.get(0).unsubscribe();
		sourceStreams.remove(0);
		sourceStreams.add(0, joinStream(source.dataSourceStream()));
	}

	public static void main(String[] args) {
		Application.launch(args);
	}
}
