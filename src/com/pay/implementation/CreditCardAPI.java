package com.pay.implementation;

import com.pay.exception.PaymentException;
import com.pay.model.PaymentRequest;

public class CreditCardAPI implements PaymentAPI {

    public void processPayment(PaymentRequest payment) throws PaymentException {
        if (payment.amout() > 1000) {
            throw new PaymentException("[Carte de crédit] : Paiement de " + payment.amout() + " " + payment.currency() + " refusé !");
        }
        System.out.println("[Carte de crédit] : paiement de " + payment.amout() +" " + payment.currency() + " en cours.");
    }
    
}