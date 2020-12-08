package com.xxx.module_my.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.xxx.common.model.http.bean.base.CountyBean;
import com.xxx.common.ui.base.BaseActivity;
import com.xxx.common.ui.config.UIConfig;
import com.xxx.common.ui.utils.ToastUtil;
import com.xxx.login.R;
import com.xxx.login.contract.SelectCountyContract;
import com.xxx.login.presenter.SelectCountyPresenter;
import com.xxx.login.ui.adapter.SelectCountyAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * @Page 选择国家页
 * @Author xxx
 */
public class SelectCountyActivity extends BaseActivity<SelectCountyPresenter> implements SelectCountyContract.IView, BaseQuickAdapter.OnItemClickListener, SwipeRefreshLayout.OnRefreshListener {

    public static void actionStart(Activity activity, int code, String countyCode) {
        Intent intent = new Intent(activity, SelectCountyActivity.class);
        intent.putExtra("return", code);
        intent.putExtra("countyCode", countyCode);
        activity.startActivityForResult(intent, UIConfig.REQUEST_CODE);
    }

    public static final String RESULT_CODE_KRY = "code";   //返回的Key
    public static final String RESULT_NAME_KRY = "name";   //返回的Key

    public static final int REGISTER_PAGE_CODE = 1;    //注册页面
    public static final int FORGET_PAGE_CODE = 2;   //忘记密码页面

    private ImageButton mReturn;
    private TextView mTitle;
    private SwipeRefreshLayout mRefresh;
    private RecyclerView mRecycler;
    private LinearLayout mNotData;
    private TextView mSelectCountySave;

    private int code;   //页面状态
    private SelectCountyAdapter mAdapter;
    private List<CountyBean> mList = new ArrayList<>();
    private String countyCode;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_select_country;
    }

    @Override
    protected void initBundle(Intent intent) {
        code = intent.getIntExtra("return", 0);
        countyCode = intent.getStringExtra("countyCode");
    }

    @Override
    protected void initView() {
        mReturn = (ImageButton) findViewById(R.id.main_return);
        mTitle = (TextView) findViewById(R.id.main_title);
        mRefresh = (SwipeRefreshLayout) findViewById(R.id.main_refresh);
        mRecycler = (RecyclerView) findViewById(R.id.main_recycler);
        mNotData = (LinearLayout) findViewById(R.id.main_not_data);
        mSelectCountySave = (TextView) findViewById(R.id.select_county_save);
    }

    @Override
    protected void initData() {
        mTitle.setText(getString(R.string.select_county_title));
        mAdapter = new SelectCountyAdapter(mList);
        mRecycler.setLayoutManager(new LinearLayoutManager(this));
        mRecycler.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(this);
        mRefresh.setOnRefreshListener(this);

        presenter.loadData();
    }

    @Override
    protected void initOnClick() {
        mReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mSelectCountySave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = null;
                switch (code) {
                    case REGISTER_PAGE_CODE:
                        intent = new Intent(SelectCountyActivity.this, LoginActivity.class);
                        break;
                    case FORGET_PAGE_CODE:
                        intent = new Intent(SelectCountyActivity.this, ForgetLoginPswActivity.class);
                        break;
                }
                if (intent != null) {
                    //记录选择
                    CountyBean countyBean = mList.get(mAdapter.getPosition());
                    intent.putExtra(SelectCountyActivity.RESULT_CODE_KRY, countyBean.getAreaCode());
                    intent.putExtra(SelectCountyActivity.RESULT_NAME_KRY, countyBean.getZhName());
                    setResult(UIConfig.RESULT_CODE, intent);
                    finish();
                }
            }
        });
    }

    @Override
    public void onRefresh() {
        presenter.loadData();
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        mAdapter.setPosition(position);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void showData(List<CountyBean> list) {
        mRecycler.setVisibility(View.VISIBLE);
        mNotData.setVisibility(View.GONE);

        mList.clear();
        mList.addAll(list);
        for (int i = 0; i < mList.size(); i++) {
            if (mList.get(i).getAreaCode().equals(countyCode)) {
                mAdapter.setPosition(i);
                break;
            }
        }
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void showNotData() {
        mNotData.setVisibility(View.VISIBLE);
        mRecycler.setVisibility(View.GONE);
    }

    @Override
    public void showError(int errorCode, String errorMessage) {
        ToastUtil.showToast(SelectCountyActivity.this, errorMessage);
    }
}
