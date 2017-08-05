package com.example.kuliza306.zolostayssample.registrationscreen;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Toast;

import com.example.kuliza306.zolostayssample.R;
import com.example.kuliza306.zolostayssample.database.DataProviderManager;
import com.example.kuliza306.zolostayssample.databinding.ActivityMainBinding;
import com.example.kuliza306.zolostayssample.databinding.ActivityRegistrationBinding;
import com.example.kuliza306.zolostayssample.loginscreen.LoginActivity;
import com.example.kuliza306.zolostayssample.loginscreen.LoginViewModel;

import rx.Observer;
import rx.functions.Action1;

/**
 * Created by kuliza306 on 05/08/17.
 */

public class RegistrationActivity extends AppCompatActivity {

    private ActivityRegistrationBinding mBinding;
    private RegistrationViewModel mViewModel;
    private boolean sPasswordFlag = true;
    private String mMobileError;
    private String mPasswordError;
    private String mNameError;
    private String mEmailError;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initDataBinding();
        initSetUp();
    }

    private void initDataBinding() {

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_registration);
        mViewModel = new RegistrationViewModel(this);
        mBinding.setRegViewModel(mViewModel);

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

        mViewModel.getmMobileStream().subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                mMobileError = s;
            }
        });

        mViewModel.getmPasswordStream().subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                mPasswordError = s;
            }
        });

        mViewModel.getmEmailStream().subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                mEmailError = s;
            }
        });
        mViewModel.getmNameStream().subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                mNameError = s;
            }
        });

        mViewModel.getmTransitionSubject().subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
               finish();
            }
        });

                mViewModel.getmRegisterStatus().subscribe(new Observer<Boolean>() {
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
                                mBinding.phone.setError(mMobileError);
                            } else if (mEmailError != null) {
                                mBinding.email.setError(mEmailError);
                            } else if (mNameError != null) {
                                mBinding.name.setError(mNameError);
                            } else if (mPasswordError != null) {
                                mBinding.password.setError(mPasswordError);
                            } else {
                                Toast.makeText(RegistrationActivity.this, "user already exists", Toast.LENGTH_LONG).show();

                            }
                        } else {
                            //todo can open user profile from this point.
                            Toast.makeText(RegistrationActivity.this, "Registration successfull", Toast.LENGTH_LONG).show();
                        }
                    }
                });

    }
}
