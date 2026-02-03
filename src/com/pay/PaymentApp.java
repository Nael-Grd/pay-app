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

        afficherMenu(primaryStage);
    }

    public void afficherMenu(Stage primaryStage) {
        Button webInterface = new Button("Interface Web");
        Button mobileInterface = new Button("Interface Mobile");

        VBox menu = new VBox();
        menu.setSpacing(15);
        menu.setAlignment(Pos.CENTER);
        menu.setPadding(new Insets(20));
        menu.getChildren().addAll(webInterface, mobileInterface);

        webInterface.setOnAction(e -> { afficherFormulaire(primaryStage, "WEB"); });

        mobileInterface.setOnAction(e -> { afficherFormulaire(primaryStage, "MOBILE");  });

        Scene scene = new Scene(menu, 400, 400);
        primaryStage.setTitle("Stage");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void afficherFormulaire(Stage primaryStage, String platform) {
        VBox vbox = new VBox();
        vbox.setSpacing(15);
        vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new Insets(30));

        if ("MOBILE".equals(platform)) {
            primaryStage.setTitle("Paiement Mobile");
            vbox.setStyle("-fx-background-color: #e3f2fd;"); 
        } else {
            primaryStage.setTitle("Portail Paiement Web");
            vbox.setStyle("-fx-background-color: #ffffff;"); 
        }

        Label titlLabel = new Label("Interface de paiement");

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

        TextField emailField = new TextField();
        emailField.setPromptText("Email PayPal");
        TextField cardField = new TextField();
        cardField.setPromptText("Numéro de carte (16 chiffres)");

        Button confirmButton = new Button("Confirmer le paiement");

        paymentModeBox.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            vbox.getChildren().removeAll(emailField, cardField); // On nettoie d'abord
            if ("Paypal".equals(newVal)) {
                vbox.getChildren().add(vbox.getChildren().indexOf(confirmButton), emailField);
            } 
            else if ("Carte bancaire".equals(newVal)) {
                vbox.getChildren().add(vbox.getChildren().indexOf(confirmButton), cardField);
            }
        });

        confirmButton.setOnAction(e -> {
            try {
                double amount = Double.parseDouble(amounTextField.getText());   // on récupère le montant
                Currency currency = (Currency) currencyBox.getValue();   // on récupere la monnaie
                String paymentModeString = paymentModeBox.getValue();   // on récupère le mode
            
                PaymentAPI api = null;
                if ("Paypal".equals(paymentModeString)) {
                    api = new PaypalAPI();
                } 
                else if ("Carte bancaire".equals(paymentModeString)) {
                    api = new CreditCardAPI();
                }
                else if (paymentModeString == null) {
                    throw new PaymentException("Veuillez sélectionner un mode de paiement.");
                }

                Order order; 
                if ("WEB".equals(platform)) { 
                    order = new WebOrder(api);
                }   
                else { 
                    order = new MobileOrder(api); 
                }

                if ("Paypal".equals(paymentModeString)) {
                    String email = emailField.getText();
                    if (email.isEmpty() || !email.contains("@") || !email.contains(".")) {
                        throw new PaymentException("Erreur : Veuillez entrer un email PayPal valide (ex: nom@mail.com).");
                    }
                }
                else if ("Carte bancaire".equals(paymentModeString)) {
                    String cardNum = cardField.getText();
                    if (cardNum.length() != 16 || !cardNum.matches("\\d+")) {
                        throw new PaymentException("Erreur : Le numéro de carte doit contenir exactement 16 chiffres.");
                    }
                }

                PaymentRequest request = new PaymentRequest(amount, currency);
                order.checkout(request);
                
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

        Button backButton = new Button("Retour");
        backButton.setOnAction(e -> { afficherMenu(primaryStage);});

        vbox.setSpacing(15);
        vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new Insets(20));
        vbox.getChildren().addAll(titlLabel, amounTextField, currencyBox, paymentModeBox, confirmButton, backButton);

        double width = "MOBILE".equals(platform) ? 350 : 600;
        double height = "MOBILE".equals(platform) ? 600 : 400;
        Scene scene = new Scene(vbox, width, height);
        primaryStage.setScene(scene);
        primaryStage.show();    
    }

    public static void main(String[] args) {
        launch(args);
    }

}
    

// javac --module-path /usr/share/openjfx/lib --add-modules javafx.controls -d bin src/com/pay/model/*.java src/com/pay/abstraction/*.java src/com/pay/implementation/*.java src/com/pay/exception/*.java src/com/pay/PaymentApp.java

// java --module-path /usr/share/openjfx/lib --add-modules javafx.controls -cp bin com.pay.PaymentApp