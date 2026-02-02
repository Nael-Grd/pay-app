package com.pay;

import com.pay.implementation.CreditCardAPI;
import com.pay.implementation.PaypalAPI;
import com.pay.model.Currency;
import com.pay.model.PaymentRequest;
import com.pay.abstraction.MobileOrder;
import com.pay.abstraction.WebOrder;
import com.pay.exception.PaymentException;

public class Main {

    public static void main(String[] args) {

        PaypalAPI paypal = new PaypalAPI();
        CreditCardAPI card = new CreditCardAPI();

        MobileOrder mobileOrder = new MobileOrder(paypal);
        WebOrder webOrder = new WebOrder(card);

        try {
            mobileOrder.checkout(new PaymentRequest(50.00, Currency.EUR));
        }
        catch (PaymentException e) {
            System.err.println(e.getMessage());
        }

        try {
            webOrder.checkout(new PaymentRequest(1100.00, Currency.USD));
        }
        catch (PaymentException e) {
            System.err.println(e.getMessage());
        }       

    }
}