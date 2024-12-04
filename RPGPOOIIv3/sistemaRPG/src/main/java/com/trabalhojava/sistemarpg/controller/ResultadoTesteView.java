package com.trabalhojava.sistemarpg.controller;

import javafx.scene.control.Alert;


public class ResultadoTesteView {
    public static void exibirResultadoTeste(String atributo, int resultado) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle("Resultado do Teste");
        alerta.setHeaderText(null);
        alerta.setContentText("O resultado do teste de " + atributo + " foi: " + resultado);
        alerta.showAndWait();
    }
}

 