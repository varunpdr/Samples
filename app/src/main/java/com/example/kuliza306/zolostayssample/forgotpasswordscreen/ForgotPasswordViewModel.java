package com.example.kuliza306.zolostayssample.forgotpasswordscreen;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.ObservableField;
import android.view.View;

import com.example.kuliza306.zolostayssample.R;
import com.example.kuliza306.zolostayssample.database.DataProviderManager;
import com.example.kuliza306.zolostayssample.database.UserInfoData;
import com.example.kuliza306.zolostayssample.utility.Constants;

import rx.Observable;
import rx.functions.Func1;
import rx.subjects.PublishSubject;

import static com.example.kuliza306.zolostayssample.utility.FieldUtils.toObservable;

/**
 * Created by kuliza306 on 05/08/17.
 */

public class ForgotPasswordViewModel extends BaseObservable {

    public ObservableField<String> mEmailField = new ObservableField<>("");
    private Observable<String> mEmailStream;
    private PublishSubject<String> mTransitionSubject;
    private PublishSubject<Boolean> mForgotStatus;
    private Context mContext;

    ForgotPasswordViewModel(Context context) {
        mContext = context;
        mEmailStream = toObservable(mEmailField);
        mForgotStatus = PublishSubject.create();
        mTransitionSubject = PublishSubject.create();

    }

    public PublishSubject<String> getmTransitionSubject() {
        return mTransitionSubject;
    }

    public void setmTransitionSubject(PublishSubject<String> mTransitionSubject) {
        this.mTransitionSubject = mTransitionSubject;
    }


    public Observable<String> getmEmailStream() {
        return mEmailStream.map(new Func1<String, String>() {
            @Override
            public String call(String s) {
                return validateEmail();
            }
        });
    }

    private String validateEmail() {
        if (mEmailField.get().isEmpty()) {
            return mContext.getResources().getString(R.string.empty_email);
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(mEmailField.get()).matches()) {
            return mContext.getString(R.string.invalid_email);
        }
        return null;
    }

    public View.OnClickListener onLogInClick() {

        return new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                mTransitionSubject.onNext(Constants.LOGIN);
            }
        };
    }

    public View.OnClickListener onResetButtonClick() {

        return new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                processForgotRequest();
            }
        };
    }

    private void processForgotRequest() {
        if (validateEmail() != null) {
            mForgotStatus.onNext(false);
        } else {

            UserInfoData userInfoData = DataProviderManager.getUserEmailInfo(mContext, mEmailField.get().toLowerCase().toString());
            if (userInfoData == null) {
                mForgotStatus.onNext(false);
            } else {
                mForgotStatus.onNext(true);
            }
        }
    }

    public PublishSubject<Boolean> getmForgotStatus() {
        return mForgotStatus;
    }
}
