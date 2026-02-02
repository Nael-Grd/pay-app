package com.pay.implementation;

import com.pay.exception.PaymentException;
import com.pay.model.PaymentRequest;

public class PaypalAPI implements PaymentAPI {

    public void processPayment(PaymentRequest payment) throws PaymentException {
        if (payment.amout() > 1000) {
            throw new PaymentException("[Paypal] : Paiement de " + payment.amout() + " " + payment.currency() + " refusé !");
        }
        System.out.println("[Paypal] : paiement de " + payment.amout() +" " + payment.currency() + " en cours.");
    }
    
    
}