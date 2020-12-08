package com.xxx.login.ui.adapter;

import android.graphics.Color;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xxx.common.model.http.bean.base.CountyBean;
import com.xxx.login.R;

import java.util.List;

public class SelectCountyAdapter extends BaseQuickAdapter<CountyBean, BaseViewHolder> {

    private int position;

    public void setPosition(int position) {
        this.position = position;
    }

    public int getPosition() {
        return position;
    }

    public SelectCountyAdapter(@Nullable List<CountyBean> data) {
        super(R.layout.item_select_county, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CountyBean item) {
        helper.setText(R.id.item_select_county_name, item.getZhName())
                .setText(R.id.item_select_county_code, item.getAreaCode());

        if (helper.getAdapterPosition() == position) {
            helper.setTextColor(R.id.item_select_county_name, Color.parseColor("#62B2F7"))
                    .setTextColor(R.id.item_select_county_code, Color.parseColor("#62B2F7"));
        } else {
            helper.setTextColor(R.id.item_select_county_name, Color.parseColor("#333333"))
                    .setTextColor(R.id.item_select_county_code, Color.parseColor("#333333"));
        }
    }
}
