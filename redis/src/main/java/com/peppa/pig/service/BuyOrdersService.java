package com.peppa.pig.service;

import com.peppa.pig.model.BuyOrders;

public interface BuyOrdersService {
    void minusResidue(int goodId);

    int insert(BuyOrders order);

    BuyOrders getById(int orderId);
}
