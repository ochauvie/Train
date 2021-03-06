package com.och.train.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.och.train.R;
import com.och.train.model.IRefData;

import java.util.List;

public class IDataSpinnerAdapter extends BaseAdapter {

    private List<IRefData> dataList;
    private LayoutInflater mInflater;
    private int layoutId;


    public IDataSpinnerAdapter(Context mContext, List<IRefData> dataList, int layoutId) {
        this.dataList = dataList;
        this.mInflater = LayoutInflater.from(mContext);
        this.layoutId = layoutId;
    }


    @Override
    public int getCount() {
        if (dataList!=null) {
            return dataList.size();
        }
        return 0;
    }


    @Override
    public Object getItem(int position) {
        if (dataList!=null) {
            return dataList.get(position);
        }
        return null;
    }


    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }


    @Override public View getDropDownView(int position, View cnvtView, ViewGroup prnt) {
        return getCustomView(position, cnvtView, prnt);
    }


    public View getCustomView(int position, View convertView, ViewGroup parent) {
        RelativeLayout mySpinner  = (RelativeLayout) mInflater.inflate(layoutId, parent, false);

        IRefData item = dataList.get(position);

        TextView main_text = mySpinner .findViewById(R.id.text_main_seen);
        main_text.setText(item.getLabel());

        //if (layoutId == R.layout.custom_spinner) {
         //   ImageView left_icon = (ImageView) mySpinner.findViewById(R.id.left_pic);
         //   left_icon.setImageResource(item.getFlag());
        //}

        return mySpinner;

    }
}
