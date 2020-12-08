package com.xxx.module_my.ui;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xxx.common.model.GlobalData;
import com.xxx.common.model.sp.SharedConst;
import com.xxx.common.model.sp.SharedPreferencesUtil;
import com.xxx.common.ui.base.BaseActivity;
import com.xxx.common.ui.config.EventBusConfig;
import com.xxx.common.ui.utils.LocalManageUtil;
import com.xxx.module_my.R;

import org.greenrobot.eventbus.EventBus;

public class LanguageActivity extends BaseActivity {

    private SharedPreferencesUtil instance;

    public static void actionStart(Activity activity) {
        Intent intent = new Intent(activity, LanguageActivity.class);
        activity.startActivity(intent);
    }

    private ImageButton mReturn;
    private TextView mTitle;
    private RelativeLayout mSimpleZh;
    private TextView mSimpleZhCheck;
    private RelativeLayout mEn;
    private TextView mEnCheck;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_language;
    }

    @Override
    protected void initBundle(Intent intent) {

    }

    @Override
    protected void initView() {
        mReturn = (ImageButton) findViewById(R.id.main_return);
        mTitle = (TextView) findViewById(R.id.main_title);

        mSimpleZh = (RelativeLayout) findViewById(R.id.language_simple_zh);
        mSimpleZhCheck = (TextView) findViewById(R.id.language_simple_zh_check);

        mEn = (RelativeLayout) findViewById(R.id.language_en);
        mEnCheck = (TextView) findViewById(R.id.language_en_check);
    }

    @Override
    protected void initData() {
//        mTitle.setText(R.string.language_title);

        instance = SharedPreferencesUtil.getInstance(this);
        String nowLanguage = instance.getString(SharedConst.CONSTANT_LAUNCHER);
        switch (nowLanguage) {
            case LocalManageUtil.LANGUAGE_CN:
                mSimpleZhCheck.setText("√");
                mEnCheck.setText("");
                break;
            case LocalManageUtil.LANGUAGE_US:
                mSimpleZhCheck.setText("");
                mEnCheck.setText("√");
                break;
        }
    }

    @Override
    protected void initOnClick() {
        mReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mSimpleZh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSimpleZhCheck.setText("√");
                mEnCheck.setText("");
                GlobalData.language = LocalManageUtil.LANGUAGE_CN;
                instance.saveString(SharedConst.CONSTANT_LAUNCHER, LocalManageUtil.LANGUAGE_CN);
                EventBus.getDefault().post(EventBusConfig.EVENT_LANGUAGE_TAG);
            }
        });

        mEn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSimpleZhCheck.setText("√");
                mEnCheck.setText("");
                GlobalData.language = LocalManageUtil.LANGUAGE_CN;
                instance.saveString(SharedConst.CONSTANT_LAUNCHER, LocalManageUtil.LANGUAGE_CN);
                EventBus.getDefault().post(EventBusConfig.EVENT_LANGUAGE_TAG);
            }
        });
    }

}
