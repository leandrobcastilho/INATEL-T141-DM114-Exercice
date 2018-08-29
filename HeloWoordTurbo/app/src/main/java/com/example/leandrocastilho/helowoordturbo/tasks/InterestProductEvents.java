package com.example.leandrocastilho.helowoordturbo.tasks;


import com.example.leandrocastilho.helowoordturbo.models.InterestProduct;
import com.example.leandrocastilho.helowoordturbo.webservice.WebServiceResponse;

import java.util.List;

public interface InterestProductEvents {

    void getInterestProductsFinished(List<InterestProduct> interestProducts);

    void getInterestProductsFailed(WebServiceResponse webServiceResponse);

    void getInterestProductsByEmailFinished(List<InterestProduct> products);

    void getInterestProductsByEmailFailed(WebServiceResponse webServiceResponse);

    void postInterestProductFinished(InterestProduct interestProduct);

    void postInterestProductFailed(WebServiceResponse webServiceResponse);

    void deleteInterestProductFinished(InterestProduct interestProduct);

    void deleteInterestProductFailed(WebServiceResponse webServiceResponse);
}
