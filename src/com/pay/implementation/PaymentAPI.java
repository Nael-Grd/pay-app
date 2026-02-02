package com.pay.implementation;

import com.pay.exception.PaymentException;
import com.pay.model.PaymentRequest;

public interface PaymentAPI {
    void processPayment(PaymentRequest payment) throws PaymentException;
}