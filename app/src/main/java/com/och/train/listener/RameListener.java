package com.och.train.listener;


import com.och.train.model.Rame;

public interface RameListener {

    void onClick(Rame rame);
    void onEnRoute(Rame rame);
    void onImage(Rame rame);

}
