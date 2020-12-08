package com.xxx.myapplication.main;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xxx.common.model.sp.SharedConst;
import com.xxx.common.model.sp.SharedPreferencesUtil;
import com.xxx.common.ui.UpdateWindow;
import com.xxx.common.ui.base.BaseActivity;
import com.xxx.common.ui.config.UIConfig;
import com.xxx.common.ui.utils.ExitAppUtil;
import com.xxx.common.ui.utils.ToastUtil;
import com.xxx.login.ui.activity.LoginActivity;
import com.xxx.myapplication.R;

/**
 * @Page 主页
 * @Author xxx
 */
public class MainActivity extends BaseActivity<MainPresenter> implements MainContract.IView {

    private LinearLayout mShop;
    private ImageView mShopImage;
    private TextView mShopText;

    private LinearLayout mMy;
    private ImageView mMyImage;
    private TextView mMyText;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initBundle(Intent intent) {

    }

    @Override
    protected void initView() {
        mShop = (LinearLayout) findViewById(R.id.main_shop);
        mShopImage = (ImageView) findViewById(R.id.main_shop_image);
        mShopText = (TextView) findViewById(R.id.main_shop_text);

        mMy = (LinearLayout) findViewById(R.id.main_my);
        mMyImage = (ImageView) findViewById(R.id.main_my_image);
        mMyText = (TextView) findViewById(R.id.main_my_text);
    }

    @Override
    protected void initData() {
        //没有登陆 就去登陆
        boolean isLogin = SharedPreferencesUtil.getInstance(this).getBoolean(SharedConst.IS_LOGIN);
        if (!isLogin) {
            LoginActivity.actionStart(this);
        }
    }

    @Override
    protected void initOnClick() {
        mShop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mShopImage.setImageResource(R.mipmap.main_shop_default);
                mMyImage.setImageResource(R.mipmap.main_my_default);

                mShopImage.setImageResource(R.mipmap.main_shop_selection);
//                FragmentManager.replaceFragment(this, ShopFragment.class, R.id.main_frame);
            }
        });
        mMy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mShopImage.setImageResource(R.mipmap.main_shop_default);
                mMyImage.setImageResource(R.mipmap.main_my_default);

                mMyImage.setImageResource(R.mipmap.main_my_selection);
//                FragmentManager.replaceFragment(this, MyFragment.class, R.id.main_frame);
            }
        });
    }

    @Override
    public void onBackPressed() {
        ExitAppUtil.getInstance().onBackPressed(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == UIConfig.LOGIN_RESULT_CODE) {
            initData();
        }
    }

    @Override
    public void showNeedUpdate(String url) {
        UpdateWindow.getInstance(this, url);
    }

    @Override
    public void showError(int errorCode, String errorMessage) {
        ToastUtil.showToast(this, errorMessage);
    }

}