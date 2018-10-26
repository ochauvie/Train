package com.och.train.listener;


import com.och.train.model.Destination;

public interface DestinationListener {

    void onClick(Destination item);
    void onDelete(Destination item);

}
