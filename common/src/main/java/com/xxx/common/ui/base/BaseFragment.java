package com.xxx.common.ui.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class BaseFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(getLayoutId(), container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initBundle(getArguments());
        initView();
        initData();
        initOnClick();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    public void showDialog() {
        FragmentActivity activity = getActivity();
        if (activity != null) {
            ((BaseActivity) activity).showLoading();
        }
    }

    public void hideDialog() {
        FragmentActivity activity = getActivity();
        if (activity != null) {
            ((BaseActivity) activity).hideLoading();
        }
    }

    //获取到Layout的ID
    protected abstract int getLayoutId();

    //初始化传递数据
    protected abstract void initBundle(Bundle bundle);

    //初始化控件Id
    protected abstract void initView();

    //初始化数据
    protected abstract void initData();

    //初始化点击事件
    protected abstract void initOnClick();

}
