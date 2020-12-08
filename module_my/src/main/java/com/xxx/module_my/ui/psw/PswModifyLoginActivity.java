package com.xxx.module_my.ui.psw;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.xxx.common.model.config.MatchesConfig;
import com.xxx.common.model.http.Api;
import com.xxx.common.model.http.ApiCallback;
import com.xxx.common.ui.base.BaseActivity;
import com.xxx.common.ui.config.UIConfig;
import com.xxx.common.ui.utils.DownTimeUtil;
import com.xxx.common.ui.utils.KeyBoardUtil;
import com.xxx.common.ui.utils.ToastUtil;
import com.xxx.module_my.R;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * @Page 修改登录密码页
 * @Author xxx
 */
public class PswModifyLoginActivity extends BaseActivity {

    public static void actionStart(Activity activity) {
        Intent intent = new Intent(activity, PswModifyLoginActivity.class);
        activity.startActivity(intent);
    }

    @BindView(R.id.modify_login_psw_sms_code_edit)
    EditText mSMSCodeEdit;
    @BindView(R.id.modify_login_psw_old_password_edit)
    EditText mOldPasswordEdit;
    @BindView(R.id.modify_login_psw_new_password_edit)
    EditText mNewPasswordEdit;
    @BindView(R.id.modify_login_psw_new_password_again_edit)
    EditText mNewPasswordAgainEdit;

    @BindView(R.id.modify_login_psw_send_sms_code)
    TextView mSendSMSCode;

    @BindView(R.id.modify_login_psw_old_password_eye)
    CheckBox mOldPasswordEye;
    @BindView(R.id.modify_login_psw_new_password_eye)
    CheckBox mNewPasswordEye;
    @BindView(R.id.modify_login_psw_new_password_again_eye)
    CheckBox mNewPasswordAgainEye;

    @Override
    protected String initTitle() {
        return getString(R.string.modify_login_psw_title);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_psw_modify_login;
    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.modify_login_psw_send_sms_code, R.id.modify_login_psw_btn, R.id.modify_login_psw_old_password_eye, R.id.modify_login_psw_new_password_eye, R.id.modify_login_psw_new_password_again_eye})
    public void OnClick(View view) {
        KeyBoardUtil.closeKeyBord(this, mSMSCodeEdit);
        switch (view.getId()) {
            case R.id.modify_login_psw_old_password_eye:
                KeyBoardUtil.setInputTypePassword(mOldPasswordEye.isChecked(), mOldPasswordEdit);
                break;
            case R.id.modify_login_psw_new_password_eye:
                KeyBoardUtil.setInputTypePassword(mNewPasswordEye.isChecked(), mNewPasswordEdit);
                break;
            case R.id.modify_login_psw_new_password_again_eye:
                KeyBoardUtil.setInputTypePassword(mNewPasswordAgainEye.isChecked(), mNewPasswordAgainEdit);
                break;
            case R.id.modify_login_psw_send_sms_code:
                sendSMSCode();
                break;
            case R.id.modify_login_psw_btn:
                updatePsw();
                break;
        }
    }

    @Override
    public void finish() {
        KeyBoardUtil.closeKeyBord(this, mSMSCodeEdit);
        super.finish();
    }

    /**
     * @Model 发送修改密码短信验证码
     */
    private void sendSMSCode() {
        Api.getInstance().sendModifyLoginSMSCode()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiCallback<Object>(this) {

                    @Override
                    public void onSuccess(Object data) {
                        ToastUtil.showToast(R.string.send_sms_code_success);
                        DownTimeUtil.getInstance().openDownTime(UIConfig.SMS_CODE_DOWN_TIME, new DownTimeUtil.Callback() {
                            @SuppressLint("SetTextI18n")
                            @Override
                            public void run(int nowTime) {
                                if (mSendSMSCode != null)
                                    mSendSMSCode.setText(nowTime + "s");
                            }

                            @Override
                            public void end() {
                                if (mSendSMSCode != null)
                                    mSendSMSCode.setText(getString(R.string.modify_pay_psw_send_sms_code));
                            }
                        });
                    }

                    @Override
                    public void onError(int errorCode, String errorMessage) {
                        ToastUtil.showToast(errorMessage);
                    }

                    @Override
                    public void onStart(Disposable d) {
                        super.onStart(d);
                        showDialog();
                    }

                    @Override
                    public void onEnd() {
                        super.onEnd();
                        hideDialog();
                    }
                });
    }


    /**
     * @Model 修改登录密码
     */
    private void updatePsw() {
        String smsCode = mSMSCodeEdit.getText().toString();
        String oldPassword = mOldPasswordEdit.getText().toString();
        String newPassword = mNewPasswordEdit.getText().toString();
        String newPasswordAgain = mNewPasswordAgainEdit.getText().toString();

        if (smsCode.isEmpty()) {
            ToastUtil.showToast(R.string.modify_login_psw_error_1);
            showEditError(mSMSCodeEdit);
            return;
        }
        if (oldPassword.isEmpty()) {
            ToastUtil.showToast(R.string.modify_login_psw_error_2);
            showEditError(mOldPasswordEdit);
            return;
        }
        if (newPassword.isEmpty()) {
            ToastUtil.showToast(R.string.modify_login_psw_error_3);
            showEditError(mNewPasswordEdit);
            return;
        }
        if (!newPassword.equals(newPasswordAgain)) {
            ToastUtil.showToast(R.string.modify_login_psw_error_4);
            showEditError(mNewPasswordEdit, mNewPasswordAgainEdit);
            return;
        }
        if (!smsCode.matches(MatchesConfig.MATCHES_SMS_CODE)) {
            ToastUtil.showToast(R.string.modify_login_psw_error_5);
            showEditError(mSMSCodeEdit);
            return;
        }
        if (!oldPassword.matches(MatchesConfig.MATCHES_PASSWORD)) {
            ToastUtil.showToast(R.string.modify_login_psw_error_6);
            showEditError(mOldPasswordEdit);
            return;
        }
        if (!newPassword.matches(MatchesConfig.MATCHES_PASSWORD)) {
            ToastUtil.showToast(R.string.modify_login_psw_error_7);
            showEditError(mNewPasswordEdit, mNewPasswordAgainEdit);
            return;
        }

        Api.getInstance().modifyLoginPsw(smsCode, oldPassword, newPassword)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiCallback<Object>(this) {

                    @Override
                    public void onSuccess(Object data) {
                        ToastUtil.showToast(getString(R.string.modify_success));
                        finish();
                    }

                    @Override
                    public void onError(int errorCode, String errorMessage) {
                        ToastUtil.showToast(errorMessage);
                    }

                    @Override
                    public void onStart(Disposable d) {
                        super.onStart(d);
                        showDialog();
                    }

                    @Override
                    public void onEnd() {
                        super.onEnd();
                        hideDialog();
                    }
                });
    }
}
