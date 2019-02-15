package com.peppa.pig.serviceimpl;

import com.peppa.pig.model.BuyOrders;
import com.peppa.pig.service.BuyOrdersService;
import org.springframework.stereotype.Service;

@Service
public class BuyOrdersServiceImpl implements BuyOrdersService {
    @Override
    public void minusResidue(int goodId) {

    }

    @Override
    public int insert(BuyOrders order) {
        return 0;
    }

    @Override
    public BuyOrders getById(int orderId) {
        return null;
    }
}
