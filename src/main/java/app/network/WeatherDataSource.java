package app.network;

import static app.event.EventStream.eventStream;

import java.util.concurrent.TimeUnit;

import app.event.AppEvent;
import app.event.ErrorEvent;
import app.event.RefreshEvent;
import app.event.WeatherEvent;
import io.netty.buffer.ByteBuf;
import io.netty.util.CharsetUtil;
import io.reactivex.netty.protocol.http.client.HttpClientRequest;
import io.reactivex.netty.protocol.http.client.HttpClientResponse;
import rx.Observable;
import rx.schedulers.Schedulers;

public abstract class WeatherDataSource {
	private static final int POLL_INTERVAL = 60;
	private static final int INITIAL_DELAY = 3;
	private static final int TIMEOUT = 20;
	protected static final String PARAM_NO_AVAIBLE = "-";
	
	public Observable<? extends AppEvent> dataSourceStream() {
		return fixedIntervalStream().compose(this::wrapRequest)
				.mergeWith(eventStream().eventsInIO().ofType(RefreshEvent.class)
						.compose(this::wrapRequest));
	}
	
	protected Observable<Long> fixedIntervalStream() {
		return Observable.interval(INITIAL_DELAY, POLL_INTERVAL, TimeUnit.SECONDS, Schedulers.io());
	}
	
	protected abstract <T> Observable<? extends WeatherEvent> makeRequest();
	
	private <T> Observable<AppEvent> wrapRequest(Observable<T> observable) {
		return observable.flatMap(ignore -> 
			makeRequest().timeout(TIMEOUT, TimeUnit.SECONDS).cast(AppEvent.class).onErrorReturn(ErrorEvent::new));
	}
	
	protected HttpClientRequest<ByteBuf> prepareHttpGETRequest(String url) {
		return HttpClientRequest.createGet(url);
	}
	
	protected <T> Observable<String> unpackResponse(Observable<HttpClientResponse<ByteBuf>> responseObservable) {
		return responseObservable.flatMap(HttpClientResponse::getContent)
				.map(buffer -> buffer.toString(CharsetUtil.UTF_8));
	}
}
