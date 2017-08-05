package com.example.kuliza306.zolostayssample.loginscreen;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.kuliza306.zolostayssample.R;
import com.example.kuliza306.zolostayssample.databinding.ActivityMainBinding;
import com.example.kuliza306.zolostayssample.registrationscreen.RegistrationActivity;

import rx.Observer;
import rx.functions.Action1;

public class LoginActivity extends AppCompatActivity {

    private LoginViewModel mLoginViewModel;
    private ActivityMainBinding mActivityMainBinding;
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
        mActivityMainBinding = DataBindingUtil.setContentView(LoginActivity.this, R.layout.activity_main);
        mLoginViewModel = new LoginViewModel(this);
        mActivityMainBinding.setLoginViewModel(mLoginViewModel);
    }

    private void initSetUp() {
        mActivityMainBinding.passwordVibility.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sPasswordFlag) {
                    mActivityMainBinding.passwordVibility.setImageDrawable(getResources().getDrawable(R.drawable.ic_visibility));
                    mActivityMainBinding.password.setTransformationMethod(null);
                    sPasswordFlag = false;
                } else {
                    mActivityMainBinding.passwordVibility.setImageDrawable(getResources().getDrawable(R.drawable.ic_visibility_off));
                    mActivityMainBinding.password.setTransformationMethod(new PasswordTransformationMethod());
                    sPasswordFlag = true;
                }
                mActivityMainBinding.password.setSelection(mActivityMainBinding.password.getText().length());
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
                    if (mMobileError != null)
                        mActivityMainBinding.phone.setError(mMobileError);
                    else if (mPasswordError != null) {
                        mActivityMainBinding.password.setError(mPasswordError);
                    } else {
                        Toast.makeText(LoginActivity.this,"Invalid credentials",Toast.LENGTH_LONG).show();
                    }
                } else {
                    //todo can open user profile from this point.
                    Toast.makeText(LoginActivity.this, "Login Success", Toast.LENGTH_LONG).show();
                }
            }
        });

        mLoginViewModel.getmTransitionSubject().subscribe(new Action1<String>() {
            @Override
            public void call(String event) {
                if(event.equalsIgnoreCase("register"))
                {
                    Intent intent = new Intent(LoginActivity.this, RegistrationActivity.class);
                    startActivity(intent);
                }
                else
                {

                }
            }
        });



    }

}
