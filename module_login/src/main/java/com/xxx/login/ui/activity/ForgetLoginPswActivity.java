package com.xxx.login.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.xxx.common.model.config.MatchesConfig;
import com.xxx.common.ui.base.BaseActivity;
import com.xxx.common.ui.config.UIConfig;
import com.xxx.common.ui.utils.KeyBoardUtil;
import com.xxx.common.ui.utils.ToastUtil;
import com.xxx.login.R;
import com.xxx.login.contract.ForgerLoginContract;
import com.xxx.login.presenter.ForgerLoginPresenter;

/**
 * @Page 忘记密码页
 * @Author xxx
 */
public class ForgetLoginPswActivity extends BaseActivity<ForgerLoginPresenter> implements ForgerLoginContract.IView {

    public static void actionStart(Activity activity) {
        Intent intent = new Intent(activity, ForgetLoginPswActivity.class);
        activity.startActivityForResult(intent, UIConfig.REQUEST_CODE);
    }

    private ImageButton mReturn;
    private TextView mSelectorPhone;
    private EditText mAccountEdit;
    private EditText mSmsCodeEdit;
    private TextView mSendSmsCode;
    private EditText mPasswordEdit;
    private CheckBox mPasswordEye;
    private EditText mPasswordAgainEdit;
    private CheckBox mPasswordAgainEye;
    private TextView mBtn;

    private String phoneName = "中国";    //默认是中国
    private String phoneCode = "86";    //默认 +86

    @Override
    protected int getLayoutId() {
        return R.layout.activity_forget_login_psw;
    }

    @Override
    protected void initBundle(Intent intent) {

    }

    @Override
    protected void initView() {
        mReturn = (ImageButton) findViewById(R.id.forget_login_psw_return);
        mSelectorPhone = (TextView) findViewById(R.id.forget_login_psw_selector_phone);
        mAccountEdit = (EditText) findViewById(R.id.forget_login_psw_account_edit);
        mSmsCodeEdit = (EditText) findViewById(R.id.forget_login_psw_sms_code_edit);
        mSendSmsCode = (TextView) findViewById(R.id.forget_login_psw_send_sms_code);
        mPasswordEdit = (EditText) findViewById(R.id.forget_login_psw_password_edit);
        mPasswordEye = (CheckBox) findViewById(R.id.forget_login_psw_password_eye);
        mPasswordAgainEdit = (EditText) findViewById(R.id.forget_login_psw_password_again_edit);
        mPasswordAgainEye = (CheckBox) findViewById(R.id.forget_login_psw_password_again_eye);
        mBtn = (TextView) findViewById(R.id.forget_login_psw_btn);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initOnClick() {
        mReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mSelectorPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectCountyActivity.actionStart(ForgetLoginPswActivity.this, SelectCountyActivity.FORGET_PAGE_CODE, phoneCode);
            }
        });
        mPasswordAgainEye.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                KeyBoardUtil.setInputTypePassword(mPasswordAgainEye.isChecked(), mPasswordAgainEdit);
            }
        });
        mPasswordEye.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                KeyBoardUtil.setInputTypePassword(mPasswordEye.isChecked(), mPasswordEdit);
            }
        });
        mSendSmsCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String account = mAccountEdit.getText().toString();
                if (account.isEmpty()) {
                    ToastUtil.showToast(ForgetLoginPswActivity.this,R.string.forget_login_psw_error_1);
                    showErrorView(mAccountEdit);
                    return;
                }
                presenter.sendSMSCode(account);
            }
        });
        mBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //手机号
                String account = mAccountEdit.getText().toString();
                if (account.isEmpty()) {
                    ToastUtil.showToast(ForgetLoginPswActivity.this,R.string.forget_login_psw_error_1);
                    showErrorView(mAccountEdit);
                    return;
                }
                if (!account.matches(MatchesConfig.MATCHES_PHONE)) {
                    ToastUtil.showToast(ForgetLoginPswActivity.this,getString(R.string.login_error_5));
                    showErrorView(mAccountEdit);
                    return;
                }

                //验证码
                String smsCode = mSmsCodeEdit.getText().toString();
                if (smsCode.isEmpty()) {
                    ToastUtil.showToast(ForgetLoginPswActivity.this,R.string.forget_login_psw_error_2);
                    showErrorView(mSmsCodeEdit);
                    return;
                }
                if (!smsCode.matches(MatchesConfig.MATCHES_SMS_CODE)) {
                    ToastUtil.showToast(ForgetLoginPswActivity.this,R.string.forget_login_psw_error_5);
                    showErrorView(mSmsCodeEdit);
                    return;
                }

                //密码
                String password = mPasswordEdit.getText().toString();
                String passwordAgain = mPasswordAgainEdit.getText().toString();
                if (password.isEmpty()) {
                    ToastUtil.showToast(ForgetLoginPswActivity.this,R.string.forget_login_psw_error_3);
                    showErrorView(mPasswordEdit);
                    return;
                }
                if (!password.equals(passwordAgain)) {
                    ToastUtil.showToast(ForgetLoginPswActivity.this,R.string.forget_login_psw_error_7);
                    showErrorView(mPasswordEdit, mPasswordAgainEdit);
                    return;
                }
                if (!password.matches(MatchesConfig.MATCHES_PASSWORD)) {
                    ToastUtil.showToast(ForgetLoginPswActivity.this,R.string.forget_login_psw_error_6);
                    showErrorView(mPasswordEdit, mPasswordAgainEdit);
                    return;
                }

                presenter.forgerPsw(account, smsCode, password, "0");
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == UIConfig.RESULT_CODE && data != null) {
            mSelectorPhone.setText(data.getStringExtra(SelectCountyActivity.RESULT_CODE_KRY));
        }
    }

    @Override
    public void showSuccess(String account, String password) {
        Intent intent = new Intent(ForgetLoginPswActivity.this, LoginActivity.class);
        intent.putExtra("account", account);
        intent.putExtra("password", password);
        setResult(UIConfig.RESULT_CODE, intent);
        ToastUtil.showToast(ForgetLoginPswActivity.this,R.string.forget_login_psw_success);
        finish();
    }

    @Override
    public void showDownTime(String nowTime) {
        mSendSmsCode.setText(nowTime);
    }

    @Override
    public void closeDownTime() {
        mSendSmsCode.setText(getString(R.string.forget_login_psw_send_sms_code));
    }

    @Override
    public void showError(int errorCode, String errorMessage) {
        ToastUtil.showToast(ForgetLoginPswActivity.this,errorMessage);
    }
}
