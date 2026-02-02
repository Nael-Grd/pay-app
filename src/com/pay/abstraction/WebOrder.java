package com.pay.abstraction;

import com.pay.implementation.PaymentAPI;
import com.pay.model.PaymentRequest;
import com.pay.exception.PaymentException;

public class WebOrder extends Order {

    public WebOrder(PaymentAPI paymentMode) {
        super(paymentMode);
    }

    public void checkout(PaymentRequest payment) throws PaymentException {
        paymentMode.processPayment(payment);
        System.out.println("Validation par empreinte digitale sur web");
    }
    
}