package com.pay;

import com.pay.model.*;
import com.pay.implementation.*;
import com.pay.abstraction.*;
import com.pay.exception.PaymentException;

import javafx.application.Application;  // La base de l'application
import javafx.collections.FXCollections;
import javafx.stage.Stage;
import javafx.scene.Scene;

import javafx.scene.layout.VBox;   // Les éléments de mise en page (Layout)
import javafx.geometry.Pos; // Pour centrer tes éléments
import javafx.geometry.Insets; // Pour ajouter des marges autour de la fenêtre

import javafx.scene.control.Label;   // Les composants (Nodes)
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;


public class PaymentApp extends Application {

    public void start(Stage primaryStage) {

        Label titlLabel = new Label("Application de paiement");

        TextField amounTextField = new TextField();  // pour le montant
        amounTextField.setPromptText("0.00");     // texte indicatif en gris à l'intérieur

        ComboBox currencyBox = new ComboBox<Currency>(
            FXCollections.observableArrayList(Currency.EUR, Currency.USD, Currency.GBP)
        );
        currencyBox.setPromptText("Devise");
        ComboBox<String> paymentModeBox = new ComboBox<String>(
            FXCollections.observableArrayList("Carte bancaire", "Paypal")
        );
        paymentModeBox.setPromptText("Mode de paiement");

        Button confirmButton = new Button("Confirmer le paiement");

        VBox vbox = new VBox();
        vbox.setSpacing(15);
        vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new Insets(20));
        vbox.getChildren().addAll(titlLabel, amounTextField, currencyBox, paymentModeBox, confirmButton);

        Scene scene = new Scene(vbox, 400, 400);
        primaryStage.setTitle("Stage");
        primaryStage.setScene(scene);
        primaryStage.show();


        confirmButton.setOnAction(e -> {
            try {
                double amount = Double.parseDouble(amounTextField.getText());   // on récupère le montant
                Currency currency = (Currency) currencyBox.getValue();   // on récupere la monnaie
                String paymentModeString = paymentModeBox.getValue();   // on récupère le mode
            
                PaymentAPI api = null;
                if ("Paypal".equals(paymentModeString)) {
                    api = new PaypalAPI();
                } 
                else {
                    api = new CreditCardAPI();
                }

                WebOrder web = new WebOrder(api);
                PaymentRequest request = new PaymentRequest(amount, currency);
                web.checkout(request);
                
                Alert success = new Alert(AlertType.INFORMATION);
                success.setTitle("Paiement réussi");
                success.setHeaderText(null);
                success.setContentText("Paiement de " + amount + " " + currency + " validé !");
                success.showAndWait();
            }
            catch (NumberFormatException nfe) {
                new Alert(AlertType.ERROR, "Veuillez entrer un montant valide.").showAndWait();
            } 
            catch (PaymentException pe) {
                new Alert(AlertType.ERROR, pe.getMessage()).showAndWait();
            } 
            catch (Exception ex) {
                new Alert(AlertType.ERROR, "Une erreur est survenue : " + ex.getMessage()).showAndWait();
            }
        });
    }

    public static void main(String[] args) {
        launch(args);
    }

}
    

// --module-path /usr/share/openjfx/lib --add-modules javafx.controls -d bin src/com/pay/model/*.java src/com/pay/abstraction/*.java src/com/pay/implementation/*.java src/com/pay/exception/*.java src/com/pay/PaymentApp.java
// --module-path /usr/share/openjfx/lib --add-modules javafx.controls -cp bin com.pay.PaymentApp