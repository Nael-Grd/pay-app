package com.pay.abstraction;

import com.pay.exception.PaymentException;
import com.pay.implementation.PaymentAPI;
import com.pay.model.PaymentRequest;

public abstract class Order {

    protected PaymentAPI paymentMode;

    public Order(PaymentAPI paymentMode) {
        this.paymentMode = paymentMode;
    }

    abstract void checkout(PaymentRequest payment) throws PaymentException;
}