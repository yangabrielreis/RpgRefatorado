package com.trabalhojava.sistemarpg.controller;

import javafx.scene.control.Alert;

public class AlertUtil {

    public static void exibirAlerta(String titulo, String mensagem) {
        Alert alerta = new Alert(Alert.AlertType.ERROR);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensagem);
        alerta.showAndWait();
    }
}
