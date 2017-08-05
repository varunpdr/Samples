package com.example.kuliza306.zolostayssample.forgotpasswordscreen;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.kuliza306.zolostayssample.R;
import com.example.kuliza306.zolostayssample.databinding.ActivityForgotPasswordBinding;
import com.example.kuliza306.zolostayssample.utility.Constants;
import com.example.kuliza306.zolostayssample.utility.Utility;

import rx.Observer;
import rx.functions.Action1;

/**
 * Created by kuliza306 on 05/08/17.
 */

public class ForgotPasswordActivity extends AppCompatActivity {

    private ActivityForgotPasswordBinding mBinding;
    private ForgotPasswordViewModel mViewModel;
    private String mEmailError;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initDataBinding();
        ;
        initSetUp();

    }

    private void initDataBinding() {

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_forgot_password);
        mViewModel = new ForgotPasswordViewModel(this);
        mBinding.setForgotViewModel(mViewModel);
    }

    private void initSetUp() {

        mViewModel.getmEmailStream().subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                mEmailError = s;
            }
        });

        mViewModel.getmTransitionSubject().subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                finish();
            }
        });

        mViewModel.getmForgotStatus().subscribe(new Observer<Boolean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Boolean status) {
                if (!status) {
                    if (mEmailError != null) {
                        Utility.showSnackbar(ForgotPasswordActivity.this, mEmailError, mBinding.coordinateLayout, false);
                    } else {
                        Utility.showSnackbar(ForgotPasswordActivity.this, getResources().getString(R.string.email_not_registered), mBinding.coordinateLayout, false);
                    }
                } else {
                    setResult(RESULT_OK);
                    finish();
                }
            }
        });
    }
}
