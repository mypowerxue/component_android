package com.xxx.module_my.ui;

import android.app.Activity;
import android.content.Intent;
import android.widget.TextView;

import com.xxx.common.model.sp.SharedPreferencesUtil;
import com.xxx.common.model.utils.DataCleanManager;
import com.xxx.common.model.utils.SystemUtil;
import com.xxx.common.ui.base.BaseActivity;
import com.xxx.module_my.R;
import com.xxx.module_my.contract.SettingContract;
import com.xxx.module_my.presenter.SettingPresenter;
import com.xxx.module_my.ui.activity.LoginActivity;
import com.xxx.module_my.ui.psw.PswSettingActivity;

public class SettingActivity extends BaseActivity<SettingPresenter> implements SettingContract.IView {

    public static void actionStart(Activity activity) {
        Intent intent = new Intent(activity, SettingActivity.class);
        activity.startActivity(intent);
    }

    TextView mCleanCacheText;
    TextView mVersionCode;

    private String versionName;

    @Override
    protected String initTitle() {
        return getString(R.string.setting_title);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_setting;
    }

    @Override
    protected void initBundle(Intent intent) {

    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        versionName = SystemUtil.getVersionName(this);
        mVersionCode.setText(versionName);

        // 查找本地缓存大小
        mCleanCacheText.setText(DataCleanManager.getCacheSize(this));
    }

    @Override
    protected void initOnClick() {
        case R.id.setting_clean_cache:
        // 清空缓存
        DataCleanManager.cleanCache(this);
        mCleanCacheText.setText("0.0B");
        break;
        case R.id.setting_feedback:
        FeedBackActivity.actionStart(this);
        break;
        case R.id.account_setting_check_version:
        checkAppVersion();
        break;
        case R.id.setting_password:
        PswSettingActivity.actionStart(this);
        break;
        case R.id.setting_language:
        LanguageActivity.actionStart(this);
        break;
        case R.id.setting_out_login:
        SharedPreferencesUtil.getInstance().cleanAll(); //清空所有数据
        LoginActivity.actionStartForResult(this);
        finish();
        break;
    }

}

    @Override
    public void showNeedUpdate(String url) {

    }

    @Override
    public void showError(int errorCode, String errorMessage) {

    }

}
