package com.example.leandrocastilho.helowoordturbo.tasks;

import com.example.leandrocastilho.helowoordturbo.models.Order;
import com.example.leandrocastilho.helowoordturbo.webservice.WebServiceResponse;

import java.util.List;

public interface OrderEvents {
    void getOrdersFinished(List<Order> orders);

    void getOrdersFailed(WebServiceResponse webServiceResponse);

    void getOrderByIdFinished(Order order);

    void getOrderByIdFailed(WebServiceResponse webServiceResponse);
}

