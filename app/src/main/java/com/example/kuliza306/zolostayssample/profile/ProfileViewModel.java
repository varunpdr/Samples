package com.example.kuliza306.zolostayssample.profile;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.ObservableField;
import android.view.View;

import com.example.kuliza306.zolostayssample.R;
import com.example.kuliza306.zolostayssample.database.DataProviderManager;
import com.example.kuliza306.zolostayssample.database.UserInfoData;
import com.example.kuliza306.zolostayssample.utility.Constants;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import rx.Observable;
import rx.functions.Func1;
import rx.subjects.PublishSubject;

import static com.example.kuliza306.zolostayssample.utility.FieldUtils.toObservable;

/**
 * Created by kuliza306 on 05/08/17.
 */

public class ProfileViewModel extends BaseObservable {

    public ObservableField<String> mMobileField = new ObservableField<>("");
    public ObservableField<String> mNameField = new ObservableField<>("");
    public ObservableField<String> mEmailField = new ObservableField<>("");
    private Observable<String> mMobileStream;
    private Observable<String> mNameStream;
    private Observable<String> mEmailStream;
    private PublishSubject<Boolean> mRegisterStatus;
    private PublishSubject<String> mTransitionSubject;
    private static long key;
    private Context mContext;
    private UserInfoData mUserInfoData;

    ProfileViewModel(UserInfoData userInfoData, Context context) {
        mContext = context;
        mMobileStream = toObservable(mMobileField);
        mNameStream = toObservable(mNameField);
        mEmailStream = toObservable(mEmailField);
        mRegisterStatus = PublishSubject.create();
        mTransitionSubject = PublishSubject.create();
        mUserInfoData = userInfoData;
        mMobileField.set(userInfoData.getPhoneNumber());
        mEmailField.set(userInfoData.getEmailId());
        mNameField.set(userInfoData.getName());

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


    public Observable<String> getmNameStream() {
        return mNameStream.map(new Func1<String, String>() {
            @Override
            public String call(String s) {
                return validateName();
            }
        });
    }

    public View.OnClickListener onUpdateButtonClick() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                processUpdate();
            }
        };
    }

    public View.OnClickListener onLogOutClick() {

        return new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                mTransitionSubject.onNext(Constants.LOGIN);
            }
        };
    }

    private String validateMobile() {
        if (mMobileField.get().toString().trim().isEmpty()) {
            return mContext.getString(R.string.mobile_empty);
        } else if (Integer.parseInt(mMobileField.get().toString().substring(0, 1)) < 7 || mMobileField.get().toString().trim().length() < 10)
            return mContext.getString(R.string.invalid_mobile);

        return null;
    }


    private String validateName() {
        String regx = "^[\\p{L} .'-]+$";
        Pattern pattern = Pattern.compile(regx, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(mNameField.get().toString());

        if (mNameField.get().toString().trim().isEmpty()) {
            return mContext.getString(R.string.empty_name);
        } else if (!matcher.matches()) {
            return mContext.getString(R.string.invalid_name);
        }
        return null;
    }

    private String validateEmail() {
        if (mEmailField.get().isEmpty()) {
            return mContext.getString(R.string.empty_email);
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(mEmailField.get()).matches()) {
            return mContext.getString(R.string.invalid_email);
        }
        return null;
    }

    private void processUpdate() {
        if (validateMobile() != null || validateEmail() != null || validateName() != null) {
            mRegisterStatus.onNext(false);
        } else {
            new DataProviderManager().replaceUserInfo(mContext, new UserInfoData(mUserInfoData.getId(), mMobileField.get().toLowerCase(), mEmailField.get().toLowerCase(), mNameField.get().toLowerCase(), mUserInfoData.getPassword().toString()));
            mRegisterStatus.onNext(true);
        }
    }

}
