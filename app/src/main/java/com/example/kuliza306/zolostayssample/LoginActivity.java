package com.example.kuliza306.zolostayssample;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.view.View;

import com.example.kuliza306.zolostayssample.databinding.ActivityMainBinding;

public class LoginActivity extends AppCompatActivity {

    private LoginViewModel mLoginViewModel;
    private ActivityMainBinding mActivityMainBinding;
    private boolean sIsPasswordVisible;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initDataBinding();
        initSetUp();
    }

    private void initDataBinding()
    {
        mActivityMainBinding= DataBindingUtil.setContentView(this,R.layout.activity_main);
        mLoginViewModel=new LoginViewModel();
        mActivityMainBinding.setLoginViewModel(mLoginViewModel);
    }

    private void initSetUp()
    {
        mActivityMainBinding.passwordVibility.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mActivityMainBinding.password.getInputType()== InputType.TYPE_CLASS_TEXT)
                {
                    mActivityMainBinding.passwordVibility.setImageDrawable(getResources().getDrawable(R.drawable.ic_visibility_off));
                    mActivityMainBinding.password.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                }
                else
                {
                    mActivityMainBinding.passwordVibility.setImageDrawable(getResources().getDrawable(R.drawable.ic_visibility));
                    mActivityMainBinding.password.setInputType(InputType.TYPE_CLASS_TEXT);
                }
            }
        });
    }
}
