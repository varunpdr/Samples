package com.example.kuliza306.zolostayssample.registrationscreen;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.kuliza306.zolostayssample.R;
import com.example.kuliza306.zolostayssample.database.DataProviderManager;
import com.example.kuliza306.zolostayssample.databinding.ActivityMainBinding;
import com.example.kuliza306.zolostayssample.databinding.ActivityRegistrationBinding;
import com.example.kuliza306.zolostayssample.loginscreen.LoginViewModel;

/**
 * Created by kuliza306 on 05/08/17.
 */

public class RegistrationActivity extends AppCompatActivity {

    private ActivityRegistrationBinding mBinding;
    private RegistrationViewModel mViewModel;
    private boolean sPasswordFlag = true;
    private String mMobileError;
    private String mPasswordError;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initDataBinding();
        initSetUp();
    }

    private void initDataBinding() {

        mBinding= DataBindingUtil.setContentView(this, R.layout.activity_registration);
        mViewModel=new RegistrationViewModel(this);
        mBinding.setRegViewModel(mViewModel);

    }

    private void initSetUp() {

    }
}
