package com.xxx.login.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xxx.common.model.config.MatchesConfig;
import com.xxx.common.ui.base.BaseActivity;
import com.xxx.common.ui.config.UIConfig;
import com.xxx.common.ui.manager.ActivityManager;
import com.xxx.common.ui.utils.KeyBoardUtil;
import com.xxx.common.ui.utils.ToastUtil;
import com.xxx.login.R;
import com.xxx.login.contract.LoginContract;
import com.xxx.login.presenter.LoginPresenter;

/**
 * @Page 登录页面
 * @Author xxx
 */
public class LoginActivity extends BaseActivity<LoginPresenter> implements LoginContract.IView {

    public static void actionStart(Activity activity) {
        Intent intent = new Intent(activity, SelectCountyActivity.class);
        activity.startActivity(intent);
    }

    private ImageButton mLoginReturn;

    private TextView mLoginTitle;
    private LinearLayout mLoginLayout;
    private TextView mLoginSelectorPhone;
    private EditText mLoginAccountEdit;
    private EditText mLoginPasswordEdit;
    private CheckBox mLoginPasswordEye;
    private TextView mLoginForgerPassword;
    private Button mLoginBtn;

    private TextView mRegisterTitle;
    private LinearLayout mRegisterLayout;
    private TextView mRegisterSelectorPhone;
    private EditText mRegisterAccountEdit;
    private EditText mRegisterSmsCodeEdit;
    private TextView mRegisterSendSmsCode;
    private EditText mRegisterPasswordEdit;
    private CheckBox mRegisterPasswordEye;
    private EditText mRegisterInviteEdit;
    private CheckBox mRegisterCheckBtn;
    private TextView mRegisterUser;
    private Button mRegisterBtn;

    public static final int LOGIN_TAG = 1;
    public static final int REGISTER_TAG = 2;

    private String LoginPhoneName = "中国";    //默认是中国
    private String LoginPhoneCode = "86";    //默认 +86
    private String RegisterPhoneName = "中国";    //默认是中国
    private String RegisterPhoneCode = "86";    //默认 +86

    private int tag = LOGIN_TAG;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initBundle(Intent intent) {

    }

    @Override
    protected void initView() {
        mLoginReturn = (ImageButton) findViewById(R.id.login_return);
        mLoginTitle = (TextView) findViewById(R.id.login_title);
        mRegisterTitle = (TextView) findViewById(R.id.register_title);
        mLoginLayout = (LinearLayout) findViewById(R.id.login_layout);
        mLoginSelectorPhone = (TextView) findViewById(R.id.login_selector_phone);
        mLoginAccountEdit = (EditText) findViewById(R.id.login_account_edit);
        mLoginPasswordEdit = (EditText) findViewById(R.id.login_password_edit);
        mLoginPasswordEye = (CheckBox) findViewById(R.id.login_password_eye);
        mLoginForgerPassword = (TextView) findViewById(R.id.login_forger_password);
        mLoginBtn = (Button) findViewById(R.id.login_btn);
        mRegisterLayout = (LinearLayout) findViewById(R.id.register_layout);
        mRegisterSelectorPhone = (TextView) findViewById(R.id.register_selector_phone);
        mRegisterAccountEdit = (EditText) findViewById(R.id.register_account_edit);
        mRegisterSmsCodeEdit = (EditText) findViewById(R.id.register_sms_code_edit);
        mRegisterSendSmsCode = (TextView) findViewById(R.id.register_send_sms_code);
        mRegisterPasswordEdit = (EditText) findViewById(R.id.register_password_edit);
        mRegisterPasswordEye = (CheckBox) findViewById(R.id.register_password_eye);
        mRegisterInviteEdit = (EditText) findViewById(R.id.register_invite_edit);
        mRegisterCheckBtn = (CheckBox) findViewById(R.id.register_check_btn);
        mRegisterUser = (TextView) findViewById(R.id.register_user);
        mRegisterBtn = (Button) findViewById(R.id.register_btn);
    }

    @Override
    protected void initData() {
        //保存记录
        mRegisterLayout = findViewById(R.id.register_layout);
        mRegisterLayout.setVisibility(View.GONE);
        mLoginLayout.setVisibility(View.VISIBLE);
    }

    @Override
    protected void initOnClick() {
        mLoginReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mLoginTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tag = LOGIN_TAG;
                mLoginLayout.setVisibility(View.VISIBLE);
                mRegisterLayout.setVisibility(View.GONE);
                mLoginTitle.setTextColor(Color.parseColor("#62B2F7"));
                mRegisterTitle.setTextColor(Color.parseColor("#999999"));
            }
        });
        mRegisterTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tag = REGISTER_TAG;
                mLoginLayout.setVisibility(View.GONE);
                mRegisterLayout.setVisibility(View.VISIBLE);
                mLoginTitle.setTextColor(Color.parseColor("#999999"));
                mRegisterTitle.setTextColor(Color.parseColor("#62B2F7"));
            }
        });
        mLoginPasswordEye.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                KeyBoardUtil.setInputTypePassword(mLoginPasswordEye.isChecked(), mLoginPasswordEdit);
            }
        });
        mLoginForgerPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ForgetLoginPswActivity.actionStart(LoginActivity.this);
            }
        });
        mLoginSelectorPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectCountyActivity.actionStart(LoginActivity.this, SelectCountyActivity.REGISTER_PAGE_CODE, LoginPhoneCode);
            }
        });
        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //验证账号
                String account = mLoginAccountEdit.getText().toString();
                if (account.isEmpty()) {
                    ToastUtil.showToast(LoginActivity.this, R.string.login_error_1);
                    showErrorView(mLoginAccountEdit);
                    return;
                }
                if (!account.matches(MatchesConfig.MATCHES_PHONE)) {
                    ToastUtil.showToast(LoginActivity.this, (R.string.login_error_5));
                    showErrorView(mLoginAccountEdit);
                    return;
                }

                //验证密码
                String password = mLoginPasswordEdit.getText().toString();
                if (password.isEmpty()) {
                    ToastUtil.showToast(LoginActivity.this, R.string.login_error_2);
                    showErrorView(mLoginPasswordEdit);
                    return;
                }
                if (!password.matches(MatchesConfig.MATCHES_PASSWORD)) {
                    ToastUtil.showToast(LoginActivity.this, R.string.register_error_6);
                    showErrorView(mLoginPasswordEdit);
                    return;
                }
                //请求账号密码
                presenter.login(account, password);
            }
        });
        mRegisterSelectorPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectCountyActivity.actionStart(LoginActivity.this, SelectCountyActivity.REGISTER_PAGE_CODE, RegisterPhoneCode);
            }
        });
        mRegisterPasswordEye.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                KeyBoardUtil.setInputTypePassword(mRegisterPasswordEye.isChecked(), mRegisterPasswordEdit);
            }
        });
        mRegisterSendSmsCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String account = mRegisterAccountEdit.getText().toString();
                if (account.isEmpty()) {
                    ToastUtil.showToast(LoginActivity.this, R.string.register_error_1);
                    showErrorView(mRegisterAccountEdit);
                    return;
                }
                presenter.sendSMSCode(account, RegisterPhoneName);
            }
        });
        mRegisterUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO 跳转用户协议

            }
        });
        mRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String account = mRegisterAccountEdit.getText().toString();
                //账号
                if (account.isEmpty()) {
                    ToastUtil.showToast(LoginActivity.this, R.string.register_error_1);
                    showErrorView(mRegisterAccountEdit);
                    return;
                }
                if (!account.matches(MatchesConfig.MATCHES_PHONE)) {
                    ToastUtil.showToast(LoginActivity.this, getString(R.string.register_error_1));
                    showErrorView(mRegisterAccountEdit);
                    return;
                }

                //短信验证码
                String smsCode = mRegisterSmsCodeEdit.getText().toString();
                if (smsCode.isEmpty()) {
                    ToastUtil.showToast(LoginActivity.this, R.string.register_error_2);
                    showErrorView(mRegisterSmsCodeEdit);
                    return;
                }
                if (!smsCode.matches(MatchesConfig.MATCHES_SMS_CODE)) {
                    ToastUtil.showToast(LoginActivity.this, R.string.register_error_5);
                    showErrorView(mRegisterSmsCodeEdit);
                    return;
                }

                //密码
                final String password = mRegisterPasswordEdit.getText().toString();
                if (password.isEmpty()) {
                    ToastUtil.showToast(LoginActivity.this, R.string.register_error_3);
                    showErrorView(mRegisterPasswordEdit);
                    return;
                }
                if (!password.matches(MatchesConfig.MATCHES_PASSWORD)) {
                    ToastUtil.showToast(LoginActivity.this, R.string.register_error_6);
                    showErrorView(mRegisterPasswordEdit);
                    return;
                }

                //验证码
                String inviteCode = mRegisterInviteEdit.getText().toString();
                if (inviteCode.isEmpty()) {
                    ToastUtil.showToast(LoginActivity.this, R.string.register_error_7);
                    showErrorView(mRegisterInviteEdit);
                    return;
                }

                //是否确认
                boolean checked = mRegisterCheckBtn.isChecked();
                if (!checked) {
                    ToastUtil.showToast(LoginActivity.this, R.string.register_error_8);
                    showErrorView(mRegisterCheckBtn);
                    return;
                }

                //发送请求
                presenter.register(account, account, smsCode, password, RegisterPhoneName, inviteCode);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == UIConfig.RESULT_CODE && data != null) {
            switch (tag) {
                case LOGIN_TAG:
                    LoginPhoneName = data.getStringExtra(SelectCountyActivity.RESULT_NAME_KRY);
                    LoginPhoneCode = data.getStringExtra(SelectCountyActivity.RESULT_CODE_KRY);
                    mLoginSelectorPhone.setText(LoginPhoneCode);
                    break;
                case REGISTER_TAG:
                    RegisterPhoneName = data.getStringExtra(SelectCountyActivity.RESULT_NAME_KRY);
                    RegisterPhoneCode = data.getStringExtra(SelectCountyActivity.RESULT_CODE_KRY);
                    mRegisterSelectorPhone.setText(RegisterPhoneCode);
                    break;
            }
        }
    }

    @Override
    public void onBackPressed() {
        ActivityManager.getInstance().AppExit();
    }

    @Override
    public void showLoginSuccess() {
        ToastUtil.showToast(LoginActivity.this, R.string.login_success);
        finish();
    }

    @Override
    public void showRegisterSuccess(String account, String password) {
        mRegisterLayout.setVisibility(View.GONE);
        mLoginLayout.setVisibility(View.VISIBLE);
        tag = LOGIN_TAG;
        mLoginTitle.setTextColor(Color.parseColor("#62B2F7"));
        mRegisterTitle.setTextColor(Color.parseColor("#999999"));
        ToastUtil.showToast(LoginActivity.this, R.string.regist_success);

        mRegisterAccountEdit.setText("");
        mRegisterSmsCodeEdit.setText("");
        mRegisterPasswordEdit.setText("");
        mRegisterInviteEdit.setText("");
        mRegisterCheckBtn.setChecked(false);

        mLoginAccountEdit.setText(account);
        mLoginPasswordEdit.setText(password);
        mLoginPasswordEye.setChecked(false);
        KeyBoardUtil.setInputTypePassword(false, mRegisterPasswordEdit);
    }

    @Override
    public void showDownTime(String nowTime) {
        mRegisterSendSmsCode.setText(nowTime);
    }

    @Override
    public void closeDownTime() {
        mRegisterSendSmsCode.setText(getString(R.string.modify_pay_psw_send_sms_code));
    }

    @Override
    public void showError(int errorCode, String errorMessage) {
        ToastUtil.showToast(LoginActivity.this, errorMessage);
    }
}
