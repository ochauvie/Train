package com.och.train.listener;


import com.och.train.model.Rame;

public interface RameListener {

    void onClick(Rame item, int position);
    void onEnRoute(Rame item, int position);

}
