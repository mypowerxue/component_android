package com.xxx.module_my.ui;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.xxx.gid.StatusData;
import com.xxx.gid.base.activity.BaseTitleActivity;
import com.xxx.gid.config.UIConfig;
import com.xxx.gid.model.http.Api;
import com.xxx.gid.model.http.ApiCallback;
import com.xxx.gid.model.http.bean.base.BaseBean;
import com.xxx.gid.model.http.utils.ApiType;
import com.xxx.gid.model.utils.BitmapUtils;
import com.xxx.gid.model.utils.CameraUtil;
import com.xxx.gid.model.utils.GlideUtil;
import com.xxx.gid.model.utils.PermissionUtil;
import com.xxx.gid.model.utils.ToastUtil;
import com.xxx.gid.ui.my.entry.UserInfoEntry;
import com.xxx.gid.ui.my.window.SetIconPopup;
import com.xxx.module_my.R;

import org.greenrobot.eventbus.EventBus;

import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class AccountSettingActivity extends BaseTitleActivity implements SetIconPopup.Callback {

    public static void actionStart(Activity activity, String name, int sex, String icon) {
        Intent intent = new Intent(activity, AccountSettingActivity.class);
        intent.putExtra("name", name);
        intent.putExtra("sex", sex);
        intent.putExtra("icon", icon);
        activity.startActivity(intent);
    }

    ImageView mIcon;
    TextView mName;
    TextView mSex;

    private SetIconPopup mSetIconPopup;

    public static final int PHOTO = 1;  //相册状态
    public static final int CAMERA = 2; //相机状态
    private int tag;

    private String nickName;
    private int sex;
    private String icon;

    @Override
    protected String initTitle() {
        return getString(R.string.account_setting_title);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_account_setting;
    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        nickName = intent.getStringExtra("name");
        sex = intent.getIntExtra("sex", ApiType.ACCOUNT_SEX_MAN);
        icon = intent.getStringExtra("icon");

        mName.setText(nickName);
        switch (sex) {
            case ApiType.ACCOUNT_SEX_MAN:
                mSex.setText(getString(R.string.account_setting_man));
                break;
            case ApiType.ACCOUNT_SEX_WOMAN:
                mSex.setText(getString(R.string.account_setting_woman));
                break;
            default:
                mSex.setText(getString(R.string.account_setting_man));
                break;
        }
        GlideUtil.loadCircle(this, icon, GlideUtil.MY_ICON_DEFAULT, mIcon);

        mSetIconPopup = SetIconPopup.getInstance(this, this);
    }

    @OnClick({R.id.account_setting_icon, R.id.account_setting_name, R.id.account_setting_sex})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.account_setting_icon:
                if (mSetIconPopup != null) {
                    mSetIconPopup.show();
                }
                break;
            case R.id.account_setting_name:
                AccountSetNameActivity.actionStart(this, nickName);
                break;
            case R.id.account_setting_sex:
                AccountSetSexActivity.actionStart(this, sex == 0 ? ApiType.ACCOUNT_SEX_MAN : sex);
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PermissionUtil.REQUEST_PERMISSION_CODE) {
            //确认相机功能 打开相机/相册

            switch (tag) {
                case PHOTO:
                    CameraUtil.openPhoto(this);
                    break;
                case CAMERA:
                    CameraUtil.openCamera(this);
                    break;
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == UIConfig.REQUEST_CODE && resultCode == UIConfig.RESULT_CODE && data != null) {
            String name = data.getStringExtra("name");
            int sex = data.getIntExtra("sex", -1);
            if (name != null && !name.isEmpty()) {
                nickName = name;
                mName.setText(name);
            }
            if (sex != -1) {
                switch (sex) {
                    case ApiType.ACCOUNT_SEX_MAN:
                        mSex.setText(getString(R.string.account_setting_man));
                        break;
                    case ApiType.ACCOUNT_SEX_WOMAN:
                        mSex.setText(getString(R.string.account_setting_woman));
                        break;
                }
            }
        } else {
            CameraUtil.onActivityResult(this, requestCode, resultCode, data, new CameraUtil.CallBack() {
                @Override
                public void callback(Bitmap bitmap, File file) {
                    mIcon.setImageBitmap(bitmap);
                    upLoadIcon(file);
                }
            });
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mSetIconPopup != null) {
            mSetIconPopup.dismiss();
            mSetIconPopup = null;
        }
    }

    @Override
    public void onCamera() {
        tag = CAMERA;
        CameraUtil.openCamera(this);
        mSetIconPopup.dismiss();
    }

    @Override
    public void onPhoto() {
        tag = PHOTO;
        CameraUtil.openPhoto(this);
        mSetIconPopup.dismiss();
    }

    /**
     * @Model 上传头像
     */
    private void upLoadIcon(final File file) {
        String base64 = null;
        if (file != null) {
            base64 = "data:image/jpeg;base64," + BitmapUtils.imgToBase64(BitmapFactory.decodeFile(file.getAbsolutePath()));
        }
        if (base64 == null) {
            ToastUtil.showToast("图片格式有误");
            return;
        }
        Api.getInstance().upLoadIcon(base64)
                .flatMap(new Function<BaseBean<String>, ObservableSource<BaseBean<Object>>>() {
                    @Override
                    public ObservableSource<BaseBean<Object>> apply(BaseBean<String> stringBaseBean) {
                        return Api.getInstance().setIcon(StatusData.userId, stringBaseBean.getData());
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiCallback<Object>(this) {
                    @Override
                    public void onSuccess(Object data) {
                        ToastUtil.showToast(getString(R.string.modify_success));
                        //发送EventBus 更新首页
                        EventBus.getDefault().post(new UserInfoEntry(null, icon));
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
