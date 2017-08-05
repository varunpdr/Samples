package com.example.kuliza306.zolostayssample.profile;

/**
 * Created by kuliza306 on 05/08/17.
 */

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.kuliza306.zolostayssample.R;
import com.example.kuliza306.zolostayssample.database.UserInfoData;
import com.example.kuliza306.zolostayssample.databinding.ActivityProfileBinding;
import com.example.kuliza306.zolostayssample.forgotpasswordscreen.ForgotPasswordActivity;
import com.example.kuliza306.zolostayssample.loginscreen.LoginActivity;
import com.example.kuliza306.zolostayssample.utility.Utility;

import rx.Observer;
import rx.functions.Action1;

/**
 * Created by kuliza306 on 05/08/17.
 */

public class ProfileActivity extends AppCompatActivity {

    private ActivityProfileBinding mBinding;
    private ProfileViewModel mViewModel;
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

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_profile);
        UserInfoData userInfoData = getIntent().getParcelableExtra("userInfo");
        mViewModel = new ProfileViewModel(userInfoData, this);
        mBinding.setProfileViewModel(mViewModel);

    }

    private void initSetUp() {

        mViewModel.getmMobileStream().subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                mMobileError = s;
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
                Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
                startActivity(intent);
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
                        Utility.showSnackbar(ProfileActivity.this,mMobileError, mBinding.coordinateLayout, false);
                    } else if (mEmailError != null) {
                        Utility.showSnackbar(ProfileActivity.this,mEmailError, mBinding.coordinateLayout, false);
                    } else if (mNameError != null) {
                        Utility.showSnackbar(ProfileActivity.this,mNameError, mBinding.coordinateLayout, false);
                    }
                } else {
                    Utility.showSnackbar(ProfileActivity.this,getResources().getString(R.string.update_success), mBinding.coordinateLayout, true);
                }
            }
        });

    }
}
