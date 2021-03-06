package com.och.train.tools;

import android.widget.BaseAdapter;
import android.widget.Spinner;

public class SpinnerTool {
    public static void SelectSpinnerItemByValue(Spinner spnr, Object value)
    {
        if (value!=null) {
            BaseAdapter adapter = (BaseAdapter) spnr.getAdapter();
            for (int position = 0; position < adapter.getCount(); position++) {
                if (( adapter.getItem(position)).toString().equals(value.toString())) {
                    spnr.setSelection(position);
                    return;
                }
            }
        }
    }
}
