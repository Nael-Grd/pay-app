package com.pay.abstraction;

import com.pay.exception.PaymentException;
import com.pay.implementation.PaymentAPI;
import com.pay.model.PaymentRequest;

public class MobileOrder extends Order {

    public MobileOrder(PaymentAPI paymentMode) {
        super(paymentMode);
    }

    public void checkout(PaymentRequest payment) throws PaymentException {
        paymentMode.processPayment(payment);
        System.out.println("Validation par empreinte digitale sur mobile");
    }
    
}