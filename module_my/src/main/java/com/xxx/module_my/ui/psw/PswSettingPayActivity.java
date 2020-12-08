package com.xxx.module_my.ui.psw;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import com.xxx.gid.R;
import com.xxx.gid.StatusData;
import com.xxx.gid.base.activity.BaseTitleActivity;
import com.xxx.gid.config.MatchesConfig;
import com.xxx.gid.model.http.Api;
import com.xxx.gid.model.http.ApiCallback;
import com.xxx.gid.model.utils.KeyBoardUtil;
import com.xxx.gid.model.utils.ToastUtil;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * @Page 设置资金密码页
 * @Author xxx
 */
public class PswSettingPayActivity extends BaseTitleActivity {

    public static void actionStart(Activity activity) {
        Intent intent = new Intent(activity, PswSettingPayActivity.class);
        activity.startActivity(intent);
    }

    @BindView(R.id.setting_pay_psw_password_edit)
    EditText mPasswordEdit;
    @BindView(R.id.setting_pay_psw_password_again_edit)
    EditText mPasswordAgainEdit;

    @BindView(R.id.setting_pay_psw_password_eye)
    CheckBox mPasswordEye;
    @BindView(R.id.setting_pay_psw_password_again_eye)
    CheckBox mPasswordAgainEye;

    @Override
    protected String initTitle() {
        return getString(R.string.setting_pay_psw_title);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_psw_setting_pay;
    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.setting_pay_psw_btn, R.id.setting_pay_psw_password_eye, R.id.setting_pay_psw_password_again_eye})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.setting_pay_psw_password_eye:
                KeyBoardUtil.setInputTypePassword(mPasswordEye.isChecked(), mPasswordEdit);
                break;
            case R.id.setting_pay_psw_password_again_eye:
                KeyBoardUtil.setInputTypePassword(mPasswordAgainEye.isChecked(), mPasswordAgainEdit);
                break;
            case R.id.setting_pay_psw_btn:
                updatePsw();
                break;
        }
    }

    @Override
    public void finish() {
        KeyBoardUtil.closeKeyBord(this, mPasswordEdit);
        super.finish();
    }

    /**
     * @Model 设置资金密码
     */
    private void updatePsw() {
        String newPassword = mPasswordEdit.getText().toString();
        String newPasswordAgain = mPasswordAgainEdit.getText().toString();

        if (newPassword.isEmpty()) {
            ToastUtil.showToast(R.string.setting_pay_psw_error_1);
            showEditError(mPasswordEdit);
            return;
        }
        if (!newPassword.equals(newPasswordAgain)) {
            ToastUtil.showToast(R.string.setting_pay_psw_error_2);
            showEditError(mPasswordEdit, mPasswordAgainEdit);
            return;
        }
        if (!newPassword.matches(MatchesConfig.MATCHES_PAY_PASSWORD)) {
            ToastUtil.showToast(R.string.setting_pay_psw_error_3);
            showEditError(mPasswordEdit, mPasswordAgainEdit);
            return;
        }

        Api.getInstance().settingPayPsw(newPassword)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiCallback<Object>(this) {

                    @Override
                    public void onSuccess(Object data) {
                        StatusData.isSettingPayPsw = true;
                        ToastUtil.showToast(getString(R.string.setting_success));
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
