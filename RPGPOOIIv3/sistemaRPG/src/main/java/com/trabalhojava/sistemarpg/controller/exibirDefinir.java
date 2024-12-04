package com.trabalhojava.sistemarpg.controller;

import java.io.File;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class exibirDefinir {
    public void exibirTelaDefinirCaracteristicas() {
            Stage stageCaracteristicas = new Stage();
            stageCaracteristicas.setTitle("Definir Características");

            GridPane grid = new GridPane();
            grid.setPadding(new Insets(20));
            grid.setHgap(10);
            grid.setVgap(10);
            grid.setStyle("-fx-background-color: #2B2B2B;");

            Label titulo = new Label("Definir Características do Personagem");
            titulo.setFont(Font.font("Arial", 18));
            titulo.setTextFill(Color.WHITE);
            GridPane.setColumnSpan(titulo, 2);

            Label lblPersonagemId = new Label("ID do Personagem:");
            lblPersonagemId.setStyle("-fx-text-fill: #D3D3D3;");
            TextField campoPersonagemId = new TextField();

            Label lblNome = new Label("Nome:");
            lblNome.setStyle("-fx-text-fill: #D3D3D3;");
            TextField campoNome = new TextField();

            Label lblClasse = new Label("Classe:");
            lblClasse.setStyle("-fx-text-fill: #D3D3D3;");
            ComboBox<String> campoClasse = new ComboBox<>();  
            campoClasse.getItems().addAll("Guerreiro", "Mago", "Arqueiro", "Ladino");

            Label lblRaca = new Label("Raça:");
            lblRaca.setStyle("-fx-text-fill: #D3D3D3;");
            ComboBox<String> campoRaca = new ComboBox<>();
            campoRaca.getItems().addAll("Humano", "Elfo", "Anão", "Orc"); 

            Label lblDescricao = new Label("Descrição:");
            lblDescricao.setStyle("-fx-text-fill: #D3D3D3;");
            TextField campoInfo = new TextField();

            Label lblImagem = new Label("Caminho da Imagem:");
            lblImagem.setStyle("-fx-text-fill: #D3D3D3;");
            TextField campoImagem = new TextField();

            Button btnSelecionarImagem = new Button("Selecionar Imagem");
            btnSelecionarImagem.setFont(Font.font("Arial", 14));
            btnSelecionarImagem.setTextFill(Color.WHITE);
            btnSelecionarImagem.setStyle("-fx-background-color: #007ACC; -fx-background-radius: 8;");
            btnSelecionarImagem.setOnAction(e -> {
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Selecionar Imagem");
                fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Imagens", "*.png", "*.jpg", "*.jpeg", "*.gif")
                );
                File selectedFile = fileChooser.showOpenDialog(stageCaracteristicas);
                if (selectedFile != null) {
                    campoImagem.setText(selectedFile.getAbsolutePath());
                }
            });
            grid.add(btnSelecionarImagem, 2, 6);

            defAtributos exibirTelaDefinirAtributos = new defAtributos();
            Button btnProximo = new Button("Próximo: Definir Atributos");
            btnProximo.setFont(Font.font("Arial", 16));
            btnProximo.setTextFill(Color.WHITE);
            btnProximo.setStyle("-fx-background-color: #007ACC; -fx-background-radius: 8;");
            btnProximo.setOnAction(e -> exibirTelaDefinirAtributos.exibirTelaDefinirAtributos(
                Integer.parseInt(campoPersonagemId.getText()), 
                campoNome.getText(), 
                campoClasse.getValue(), 
                campoRaca.getValue(), 
                campoInfo.getText(), 
                campoImagem.getText(), 
                stageCaracteristicas
            ));

            grid.add(titulo, 0, 0);
            grid.add(lblPersonagemId, 0, 1);
            grid.add(campoPersonagemId, 1, 1);
            grid.add(lblNome, 0, 2);
            grid.add(campoNome, 1, 2);
            grid.add(lblClasse, 0, 3);
            grid.add(campoClasse, 1, 3);
            grid.add(lblRaca, 0, 4);
            grid.add(campoRaca, 1, 4);
            grid.add(lblDescricao, 0, 5);
            grid.add(campoInfo, 1, 5);
            grid.add(lblImagem, 0, 6);
            grid.add(campoImagem, 1, 6);
            grid.add(btnProximo, 1, 7);

            Scene cena = new Scene(grid, 400, 350);
            stageCaracteristicas.setScene(cena);
            stageCaracteristicas.show();
        }
}
