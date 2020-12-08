package com.xxx.module_my.ui;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.xxx.common.ui.base.BaseActivity;
import com.xxx.gid.base.activity.BaseTitleActivity;
import com.xxx.gid.model.http.Api;
import com.xxx.gid.model.http.ApiCallback;
import com.xxx.gid.model.utils.ToastUtil;
import com.xxx.gid.ui.my.window.FeedBackDialog;
import com.xxx.module_my.R;
import com.xxx.module_my.contract.FeedBackContract;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class FeedBackActivity extends BaseActivity<FeedBackContract> implements FeedBackDialog.Callback {

    public static void actionStart(Activity activity) {
        Intent intent = new Intent(activity, FeedBackActivity.class);
        activity.startActivity(intent);
    }

    @BindView(R.id.feed_back_number)
    TextView mNumber;
    @BindView(R.id.feed_back_edit)
    EditText mContent;

    private FeedBackDialog mFeedBackDialog;

    @Override
    protected String initTitle() {
        return getString(R.string.feed_back_title);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_feed_back;
    }

    @Override
    protected void initData() {
        //初始化确认
        mFeedBackDialog = FeedBackDialog.getInstance(this, this);
    }

    @OnClick({R.id.feed_back_btn})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.feed_back_btn:
                submitFeedback();
                break;
        }
    }

    @OnTextChanged({R.id.feed_back_edit})
    public void OnTextChanged(CharSequence charSequence) {
        int length = charSequence.length();
        mNumber.setText(length + "/200");
    }

    @Override
    public void onCallback() {
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mFeedBackDialog != null) {
            mFeedBackDialog.dismiss();
            mFeedBackDialog = null;
        }
    }

    /**
     * @Model 提交意见反馈
     */
    private void submitFeedback() {
        String content = mContent.getText().toString();

        if (content.isEmpty()) {
            showEditError(mContent);
            ToastUtil.showToast(R.string.feed_back_error_1);
            return;
        }
        if (content.length() > 200) {
            showEditError(mContent);
            ToastUtil.showToast(getString(R.string.content_commit_too_long));
            return;
        }

        Api.getInstance().submitFeedback(content)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiCallback<Object>(this) {

                    @Override
                    public void onSuccess(Object data) {
                        ToastUtil.showToast(getString(R.string.submit_success));
                        mContent.setText("");
                        if (mFeedBackDialog != null) {
                            mFeedBackDialog.show();
                        }
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
