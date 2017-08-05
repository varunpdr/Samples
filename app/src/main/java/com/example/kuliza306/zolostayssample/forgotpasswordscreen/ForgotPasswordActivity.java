package com.example.kuliza306.zolostayssample.forgotpasswordscreen;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.kuliza306.zolostayssample.R;
import com.example.kuliza306.zolostayssample.databinding.ActivityForgotPasswordBinding;

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
                        mBinding.email.setError(mEmailError);
                    } else {
                        Toast.makeText(ForgotPasswordActivity.this, "This email is not registered with us.", Toast.LENGTH_LONG).show();

                    }
                } else {
                    //todo can open user profile from this point.
                    Toast.makeText(ForgotPasswordActivity.this, "Email has been sent to your email", Toast.LENGTH_LONG).show();
                    finish();
                }
            }
        });
    }
}
