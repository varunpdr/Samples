package com.example.kuliza306.zolostayssample.loginscreen;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.ObservableField;
import android.view.View;

import com.example.kuliza306.zolostayssample.database.DataProviderManager;
import com.example.kuliza306.zolostayssample.database.UserInfoData;

import rx.Observable;
import rx.functions.Func1;
import rx.subjects.PublishSubject;

import static com.example.kuliza306.zolostayssample.utility.FieldUtils.toObservable;


/**
 * This view model handles login module.The observable fields of mvvm are converted to rx streams so
 * that text watchers can be ignored.This view models checks the validation of mMobileStream and mPasswordStream .
 * On successfull validation publish subjects returns status of login with true value else returns an error with false
 * value.
 * Created by Varun Pidyar on 02/08/17.
 */
@SuppressWarnings("DefaultFileTemplate")
public class LoginViewModel extends BaseObservable {


    public ObservableField<String> mMobileField = new ObservableField<>("");
    public ObservableField<String> mPasswordField = new ObservableField<>("");
    private Observable<String> mMobileStream;
    private Observable<String> mPasswordStream;
    private PublishSubject<Boolean> mLoginStatus;
    private PublishSubject<String> mTransitionSubject;
    private PublishSubject<UserInfoData> mLoginModelSubject;
    private Context mContext;

    LoginViewModel(Context context) {
        mContext=context;
        mMobileStream = toObservable(mMobileField);
        mPasswordStream = toObservable(mPasswordField);
        mLoginStatus = PublishSubject.create();
        mTransitionSubject = PublishSubject.create();
        mLoginModelSubject=PublishSubject.create();

    }


    Observable<String> getMobileObservable() {
        return mMobileStream.map(new Func1<String, String>() {
            @Override
            public String call(String s) {
                return validateMobile();
            }
        });
    }

    Observable<String> getPasswordObservable() {
        return mPasswordStream.map(new Func1<String, String>() {
            @Override
            public String call(String s) {
                return validatePassword();
            }
        });
    }

    public PublishSubject getmTransitionSubject() {
        return mTransitionSubject;
    }

    public PublishSubject getLoginStatus() {
        return mLoginStatus;
    }

    public View.OnClickListener onLogInButtonClick() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                processLogin();
            }
        };
    }

    public View.OnClickListener onRegisterButtonClick() {
        return new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                triggerTransition("register");
            }
        };
    }

    public View.OnClickListener onForgotTextClick() {
        return new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                triggerTransition("forgotPassword");
            }
        };
    }

    private void triggerTransition(String clickEvent) {
        mTransitionSubject.onNext(clickEvent);
    }

    private void processLogin() {
        if (validateMobile() != null || validatePassword() != null) {
            mLoginStatus.onNext(false);
        } else {
            UserInfoData userInfoData = DataProviderManager.getUserLoginInfo(mContext, mMobileField.get().toString().trim(),mPasswordField.get());
            if (userInfoData != null) {
                mLoginStatus.onNext(true);
                mLoginModelSubject.onNext(userInfoData);
            } else {
                mLoginStatus.onNext(false);
            }
        }
    }

    private String validateMobile() {
        if (mMobileField.get().toString().trim().isEmpty()) {
            return "mobile number can't be empty";
        } else if (Integer.parseInt(mMobileField.get().toString().substring(0, 1)) < 7 || mMobileField.get().toString().trim().length() < 10)
            return "invalid mobile number";

        return null;
    }

    private String validatePassword() {
        if (mPasswordField.get().toString().trim().isEmpty()) {
            return "password can't be empty";
        } else if (mPasswordField.get().toString().trim().length() < 6 || mPasswordField.get().toString().trim().length() > 16) {
            return "password length should be between 6-16 chars";
        }

        return null;
    }

    public PublishSubject<UserInfoData> getmLoginModelSubject() {
        return mLoginModelSubject;
    }

    public void clearFields() {
        mMobileField.set("");
        mPasswordField.set("");
    }
}
