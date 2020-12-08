package com.xxx.module_my.ui.psw;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.xxx.gid.R;
import com.xxx.gid.StatusData;
import com.xxx.gid.base.activity.BaseTitleActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Page 密码设置页
 * @Author xxx
 */
public class PswSettingActivity extends BaseTitleActivity {

    public static void actionStart(Activity activity) {
        Intent intent = new Intent(activity, PswSettingActivity.class);
        activity.startActivity(intent);
    }

    @BindView(R.id.psw_setting_pay_text)
    TextView mPayText;

    @Override
    protected String initTitle() {
        return getString(R.string.password_setting_title);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_psw_setting;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (StatusData.isSettingPayPsw) {
            mPayText.setText(R.string.password_setting_modify_pay);
        } else {
            mPayText.setText(R.string.password_setting_setting_pay_psw);
        }
    }

    @OnClick({R.id.psw_setting_modify_login, R.id.psw_setting_modify_pay})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.psw_setting_modify_login:
                PswModifyLoginActivity.actionStart(this);
                break;
            case R.id.psw_setting_modify_pay:
                if (StatusData.isSettingPayPsw) {
                    PswModifyPayActivity.actionStart(this);
                } else {
                    PswSettingPayActivity.actionStart(this);
                }
                break;
        }
    }
}
