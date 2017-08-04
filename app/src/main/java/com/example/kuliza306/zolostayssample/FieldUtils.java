package com.example.kuliza306.zolostayssample;

import android.databinding.ObservableField;
import android.support.annotation.NonNull;

import com.example.kuliza306.zolostayssample.ReadOnlyField;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action0;
import rx.subscriptions.Subscriptions;

/**
 * Created by kuliza306 on 04/08/17.
 */

public class FieldUtils {
    /**
     * Converts an ObservableField to an Observable. Note that setting null value inside
     * ObservableField (except for initial value) throws a NullPointerException.
     * @return Observable that contains the latest value in the ObservableField
     */
    public static <T> Observable<T> toObservable(@NonNull final ObservableField<T> observableField) {
        return Observable.create(new Observable.OnSubscribe<T>() {
            @Override
            public void call(final Subscriber<? super T> subscriber) {

                if (!subscriber.isUnsubscribed()) {
                    subscriber.onNext(observableField.get());
                }

                final android.databinding.Observable.OnPropertyChangedCallback callback = new android.databinding.Observable.OnPropertyChangedCallback() {
                    @Override
                    public void onPropertyChanged(android.databinding.Observable dataBindingObservable, int propertyId) {
                        if (dataBindingObservable == observableField) {
                            subscriber.onNext(observableField.get());
                        }
                    }
                };

                observableField.addOnPropertyChangedCallback(callback);

                subscriber.add(Subscriptions.create(new Action0() {
                    @Override
                    public void call() {
                        observableField.removeOnPropertyChangedCallback(callback);
                    }
                }));
            }
        });
    }
    @NonNull
    public static <T> ReadOnlyField<T> toField(@NonNull final io.reactivex.Observable<T> observable) {
        return ReadOnlyField.create(observable);
    }
}
