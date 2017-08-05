package com.example.kuliza306.zolostayssample.registrationscreen;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.ObservableField;
import android.view.View;

import com.example.kuliza306.zolostayssample.R;
import com.example.kuliza306.zolostayssample.database.DataProviderManager;
import com.example.kuliza306.zolostayssample.database.UserInfoData;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import rx.Observable;
import rx.functions.Func1;
import rx.subjects.PublishSubject;

import static com.example.kuliza306.zolostayssample.utility.FieldUtils.toObservable;

/**
 * Created by kuliza306 on 05/08/17.
 */

public class RegistrationViewModel extends BaseObservable {

    public ObservableField<String> mMobileField = new ObservableField<>("");
    public ObservableField<String> mPasswordField = new ObservableField<>("");
    public ObservableField<String> mNameField = new ObservableField<>("");
    public ObservableField<String> mEmailField = new ObservableField<>("");
    private Observable<String> mMobileStream;
    private Observable<String> mPasswordStream;
    private Observable<String> mNameStream;
    private Observable<String> mEmailStream;
    private PublishSubject<Boolean> mRegisterStatus;
    private PublishSubject<String> mTransitionSubject;
    private Context mContext;

    RegistrationViewModel(Context context) {
        mContext = context;
        mMobileStream = toObservable(mMobileField);
        mPasswordStream = toObservable(mPasswordField);
        mNameStream = toObservable(mNameField);
        mEmailStream = toObservable(mEmailField);
        mRegisterStatus = PublishSubject.create();
        mTransitionSubject = PublishSubject.create();

    }

    public Observable<String> getmMobileStream() {
        return mMobileStream.map(new Func1<String, String>() {
            @Override
            public String call(String s) {
                return validateMobile();
            }
        });
    }

    public PublishSubject<Boolean> getmRegisterStatus() {
        return mRegisterStatus;
    }

    public PublishSubject<String> getmTransitionSubject() {
        return mTransitionSubject;
    }

    public Observable<String> getmEmailStream() {
        return mEmailStream.map(new Func1<String, String>() {
            @Override
            public String call(String s) {
                return validateEmail();
            }
        });
    }

    public Observable<String> getmPasswordStream() {
        return mPasswordStream.map(new Func1<String, String>() {
            @Override
            public String call(String s) {
                return validatePassword();
            }
        });
    }

    public Observable<String> getmNameStream() {
        return mNameStream.map(new Func1<String, String>() {
            @Override
            public String call(String s) {
                return validateName();
            }
        });
    }

    public View.OnClickListener onRegButtonClick() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                processRegistration();
            }
        };
    }

    public View.OnClickListener onLogInClick()
    {

        return new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                mTransitionSubject.onNext("login");
            }
        };
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

    private String validateName() {
        String regx = "^[\\p{L} .'-]+$";
        Pattern pattern = Pattern.compile(regx, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(mNameField.get().toString());

        if (mNameField.get().toString().trim().isEmpty()) {
            return "name cant'be empty";
        } else if (!matcher.matches()) {
            return "invalid name";
        }
        return null;
    }

    private String validateEmail() {
        if (mEmailField.get().isEmpty()) {
            return "email cant'be empty";
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(mEmailField.get()).matches()) {
            return "invalid email id";
        }
        return null;
    }

    private void processRegistration() {
        if (validateMobile() != null || validateEmail() != null || validatePassword() != null || validateName() != null) {
            mRegisterStatus.onNext(false);
        } else {

            UserInfoData userInfoData=DataProviderManager.getUserInfo(mContext,mMobileField.get().toString(),mEmailField.get().toLowerCase().toString());
            if(userInfoData==null)
            {
                DataProviderManager.insertUserInfo(mContext,new UserInfoData(mMobileField.get().toLowerCase(),mEmailField.get().toLowerCase(),mNameField.get().toLowerCase(),mPasswordField.get().toLowerCase()));
                mRegisterStatus.onNext(true);
            }
            else
            {
                mRegisterStatus.onNext(false);
            }
        }
    }

}
