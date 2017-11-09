package app.event;

import javafx.beans.binding.Binding;
import rx.Observable;
import rx.Observable.Transformer;
import rx.Subscription;
import rx.functions.Func1;
import rx.javafx.sources.CompositeObservable;
import rx.schedulers.JavaFxScheduler;
import rx.schedulers.Schedulers;
import rx.subscribers.JavaFxSubscriber;

public class EventStream {
	
	public static class WrappedObservable<T> {
		final Observable<T> inner;

		public <U> WrappedObservable<T> andOn(Observable<U> observable, Func1<? super U, ? extends T> mapper) {
			return new WrappedObservable<>(inner.mergeWith(observable.map(mapper)));
		}

		public <U> WrappedObservable<T> andOn(Observable<U> observable, T value) {
			return new WrappedObservable<>(inner.mergeWith(fixedMap(observable, value)));
		}

		public Observable<T> toObservable() {
			return inner;
		}

		public Binding<T> toBinding() {
			return binding(inner);
		}

		public WrappedObservable(final Observable<T> inner) {
			this.inner = inner;
		}
	}
	
	private static EventStream instance = new EventStream();
	private CompositeObservable<AppEvent> composite = new CompositeObservable<>();

	public static EventStream eventStream() {
		return instance;
	}
	
	public Observable<AppEvent> events() {
		return eventStream().composite.toObservable();
	}
	
	@SuppressWarnings("unchecked")
	public static Subscription joinStream(Observable<? extends AppEvent> observable) {
		final CompositeObservable<AppEvent> local = eventStream().composite;
		synchronized (local) {
			return local.addAll((Observable<AppEvent>) observable);
		}
	}
	
	public static <T> Binding<T> binding(Observable<T> observable) {
		return JavaFxSubscriber.toBinding(observable);
	}

	public static <T> Observable<T> fixedMap(Observable<?> observable, T value) {
		return observable.map(ignore -> value);
	}

	public static <T, U> WrappedObservable<U> onEvent(Observable<T> observable, Func1<? super T, ? extends U> mapper) {
		return new WrappedObservable<>(observable.map(mapper));
	}

	public static <T, U> WrappedObservable<U> onEvent(Observable<T> observable, U value) {
		return new WrappedObservable<>(fixedMap(observable, value));
	}
	
	public Observable<AppEvent> eventsInFx() {
		return events().compose(fxTransformer());
	}
	
	private <T> Transformer<T, T> fxTransformer() {
		return obs -> obs.observeOn(JavaFxScheduler.getInstance());
	}
	
	public Observable<AppEvent> eventsInIO() {
		return events().compose(ioTransformer());
	}
	
	private <T> Transformer<T, T> ioTransformer() {
		return obs -> obs.observeOn(Schedulers.io());
	}
}
