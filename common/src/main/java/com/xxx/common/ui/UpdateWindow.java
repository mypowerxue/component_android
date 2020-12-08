package com.xxx.common.ui;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.Button;

import com.xxx.common.R;
import com.xxx.common.ui.base.BaseDialog;

public class UpdateWindow extends BaseDialog {

    private String url;
    private Button mBtn;

    private void setUrl(String url) {
        this.url = url;
    }

    public static void getInstance(Context context, String url) {
        UpdateWindow window = new UpdateWindow(context);
        window.show();
        window.setUrl(url);
    }

    private UpdateWindow(Context context) {
        super(context);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.window_update;
    }

    @Override
    protected void initView() {
        mBtn = (Button) findViewById(R.id.window_update_true);
    }

    @Override
    protected void initOnClick() {
        mBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction("android.intent.action.VIEW");
                intent.setData(Uri.parse(url));
                getContext().startActivity(intent);
            }
        });
    }

    @Override
    protected void initData() {
        setCancelable(false); // 是否可以按“返回键”消失
    }

}
