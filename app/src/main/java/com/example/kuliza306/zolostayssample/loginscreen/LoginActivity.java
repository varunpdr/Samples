package com.example.kuliza306.zolostayssample.loginscreen;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.PasswordTransformationMethod;
import android.view.View;

import com.example.kuliza306.zolostayssample.R;
import com.example.kuliza306.zolostayssample.application.ZoloApplication;
import com.example.kuliza306.zolostayssample.database.DaoSession;
import com.example.kuliza306.zolostayssample.database.UserInfoData;
import com.example.kuliza306.zolostayssample.databinding.ActivityLoginBinding;
import com.example.kuliza306.zolostayssample.forgotpasswordscreen.ForgotPasswordActivity;
import com.example.kuliza306.zolostayssample.profile.ProfileActivity;
import com.example.kuliza306.zolostayssample.registrationscreen.RegistrationActivity;
import com.example.kuliza306.zolostayssample.utility.Constants;
import com.example.kuliza306.zolostayssample.utility.Utility;

import javax.inject.Inject;

import rx.Observer;
import rx.functions.Action1;

public class LoginActivity extends AppCompatActivity {

    private LoginViewModel mLoginViewModel;
    private ActivityLoginBinding mBinding;
    private boolean sPasswordFlag = true;
    private String mMobileError;
    private String mPasswordError;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initDataBinding();
        initSetUp();
    }

    private void initDataBinding() {
        mBinding = DataBindingUtil.setContentView(LoginActivity.this, R.layout.activity_login);
        mLoginViewModel = new LoginViewModel(this);
        mBinding.setLoginViewModel(mLoginViewModel);
    }

    private void initSetUp() {
        mBinding.passwordVibility.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sPasswordFlag) {
                    mBinding.passwordVibility.setImageDrawable(getResources().getDrawable(R.drawable.ic_visibility));
                    mBinding.password.setTransformationMethod(null);
                    sPasswordFlag = false;
                } else {
                    mBinding.passwordVibility.setImageDrawable(getResources().getDrawable(R.drawable.ic_visibility_off));
                    mBinding.password.setTransformationMethod(new PasswordTransformationMethod());
                    sPasswordFlag = true;
                }
                mBinding.password.setSelection(mBinding.password.getText().length());
            }
        });

        mLoginViewModel.getMobileObservable().subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                mMobileError = s;
            }
        });

        mLoginViewModel.getPasswordObservable().subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                mPasswordError = s;
            }
        });

        mLoginViewModel.getLoginStatus().subscribe(new Observer<Boolean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Boolean status) {
                if (!status) {
                    if (mMobileError != null) {
                        Utility.showSnackbar(LoginActivity.this, mMobileError, mBinding.coordinateLayout, false);
                    } else if (mPasswordError != null) {
                        Utility.showSnackbar(LoginActivity.this, mPasswordError, mBinding.coordinateLayout, false);
                    } else {
                        Utility.showSnackbar(LoginActivity.this, getResources().getString(R.string.invalid_credentials), mBinding.coordinateLayout, false);
                    }
                } else {
                    Utility.showSnackbar(LoginActivity.this, getResources().getString(R.string.login_success), mBinding.coordinateLayout, true);
                }
            }
        });

        mLoginViewModel.getmTransitionSubject().subscribe(new Action1<String>() {
            @Override
            public void call(String event) {
                mLoginViewModel.clearFields();
                if (event.equalsIgnoreCase(Constants.REGISTER)) {
                    Intent intent = new Intent(LoginActivity.this, RegistrationActivity.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
                    startActivityForResult(intent,Constants.FORGOT_EVENT);
                }
            }
        });

        mLoginViewModel.getmLoginModelSubject().subscribe(new Observer<UserInfoData>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(UserInfoData userInfoData) {
                Intent i = new Intent(LoginActivity.this, ProfileActivity.class);
                i.putExtra(Constants.USER_INFO, userInfoData);
                startActivity(i);
                finish();
                ;
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==Constants.FORGOT_EVENT && resultCode==RESULT_OK)
        {
            Utility.showSnackbar(LoginActivity.this, getResources().getString(R.string.email_sent), mBinding.coordinateLayout, true);
        }
    }
}
