# Pay app

[![JavaFX](https://img.shields.io/badge/JavaFX-blue?style=for-the-badge&logo=java&logoColor=white)](https://openjfx.io/)

## *FR* Documentation (*ENG* bellow)
Ce projet est une application de démonstration implémentant le **Design Pattern Bridge**. L'objectif est de séparer l'abstraction de l'interface de paiement (Web vs Mobile) de l'implémentation concrète des services de paiement (PayPal, Carte Bancaire).

---

## Fonctionnalités
* **Menu de Sélection** : Choix entre l'interface Web et Mobile.
* **Formulaires Dynamiques** : Affichage des champs (Email pour PayPal, Numéro de carte pour le paiement bancaire).
* **Gestion d'Exceptions** : Système d'alertes JavaFX pour gérer les erreurs de saisie et de paiement.

---

## Installation et Démarrage

### Prérequis
L'application nécessite un environnement Java 17+ et les bibliothèques JavaFX.

> [!IMPORTANT]
> **Utilisateurs WSL2 / Linux (sans écran local)** : 
> Pour afficher l'interface graphique, vous devez utiliser un serveur X (comme **XLaunch / VcXsrv**) sur votre machine hôte Windows.

### Configuration XLaunch :
1. Sélectionnez **Multiple windows**.
2. Sélectionnez **Start no client**.
3. **Important** : Décochez **Native OpenGL** et cochez **Disable access control**.

### Lancement :
```bash
# Compilation
javac --module-path /usr/share/openjfx/lib --add-modules javafx.controls -d bin src/com/pay/model/*.java src/com/pay/abstraction/*.java src/com/pay/implementation/*.java src/com/pay/exception/*.java src/com/pay/PaymentApp.java

# Détection automatique de l'IP et Lancement
export DISPLAY=$(ip route | grep default | awk '{print $3}'):0.0
export LIBGL_ALWAYS_SOFTWARE=1
java --module-path /usr/share/openjfx/lib --add-modules javafx.controls -Dprism.order=sw -cp bin com.pay.PaymentApp
```
---

## *ENG* Project Documentation 
This project is a demonstration application implementing the **Bridge Design Pattern**. The goal is to separate the abstraction of the payment interface (Web vs. Mobile) from the concrete implementation of payment services (PayPal, Credit Card).

---

## Features
* **Selection Menu**: Choice between web and mobile interface.
* **Dynamic Forms**: Display of fields (email for PayPal, card number for bank payment).
* **Exception Management**: JavaFX alert system to manage input and payment errors.
---

## Installation and Start

### Prerequisites
The application requires Java 17+ and JavaFX libraries.

> [!IMPORTANT]
> **WSL2 / Linux users (without a local display)**: 
> To display the GUI, you must use an X server (such as **XLaunch / VcXsrv**) on your Windows host machine.

### XLaunch configuration:
1. Select **Multiple windows**.
2. Select **Start no client**.
3. **Important**: Uncheck **Native OpenGL** and check **Disable access control**.

### Launch  :
```bash
# Compilation
javac --module-path /usr/share/openjfx/lib --add-modules javafx.controls -d bin src/com/pay/model/*.java src/com/pay/abstraction/*.java src/com/pay/implementation/*.java src/com/pay/exception/*.java src/com/pay/PaymentApp.java

# Automatic IP Detection and Launch
export DISPLAY=$(ip route | grep default | awk '{print $3}'):0.0
export LIBGL_ALWAYS_SOFTWARE=1
java --module-path /usr/share/openjfx/lib --add-modules javafx.controls -Dprism.order=sw -cp bin com.pay.PaymentApp
```
