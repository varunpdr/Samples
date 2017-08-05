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
import com.example.kuliza306.zolostayssample.utility.Utility;

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
                        Utility.showSnackbar(RegistrationActivity.this,mMobileError, mBinding.coordinateLayout, false);
                    } else if (mEmailError != null) {
                        Utility.showSnackbar(RegistrationActivity.this,mEmailError, mBinding.coordinateLayout, false);
                    } else if (mNameError != null) {
                        Utility.showSnackbar(RegistrationActivity.this,mNameError, mBinding.coordinateLayout, false);
                    } else if (mPasswordError != null) {
                        Utility.showSnackbar(RegistrationActivity.this,mPasswordError, mBinding.coordinateLayout, false);

                    } else {
                        Utility.showSnackbar(RegistrationActivity.this,getResources().getString(R.string.user_already_exists), mBinding.coordinateLayout, false);
                    }
                } else {
                    Utility.showSnackbar(RegistrationActivity.this,getResources().getString(R.string.register_success), mBinding.coordinateLayout, true);
                }
            }
        });

    }
}
