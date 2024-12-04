package com.trabalhojava.sistemarpg.controller;

import java.sql.SQLException;

import com.trabalhojava.sistemarpg.dao.ClasseDBDAO;
import com.trabalhojava.sistemarpg.dao.PersonagemDBDAO;
import com.trabalhojava.sistemarpg.dao.PersonagemSistemaDBDAO;
import com.trabalhojava.sistemarpg.dao.RacaDBDAO;
import com.trabalhojava.sistemarpg.dao.SistemaDBDAO;
import com.trabalhojava.sistemarpg.main.RolagemDados;
import com.trabalhojava.sistemarpg.model.Classe;
import com.trabalhojava.sistemarpg.model.Personagem;
import com.trabalhojava.sistemarpg.model.PersonagemSistema;
import com.trabalhojava.sistemarpg.model.Raca;
import com.trabalhojava.sistemarpg.model.Sistema;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class defAtributos {
    

    public void exibirTelaDefinirAtributos(int personagemId, String nome, String classe, String raca, String info, String caminhoImagem, Stage telaAnterior) {
            
            String[] atributos = {"Força", "Destreza", "Constituição", "Inteligência", "Sabedoria", "Carisma"};
            TextField[] campos = new TextField[atributos.length];
            telaAnterior.close();
            Stage stageAtributos = new Stage();
            stageAtributos.setTitle("Definir Atributos");

            GridPane grid = new GridPane();
            grid.setPadding(new Insets(20));
            grid.setHgap(10);
            grid.setVgap(10);
            grid.setStyle("-fx-background-color: #2B2B2B;");

            Label titulo = new Label("Definir Atributos do Personagem");
            titulo.setFont(Font.font("Arial", 18));
            titulo.setTextFill(Color.WHITE);
            GridPane.setColumnSpan(titulo, 2);

            Label lblForca = new Label("Força:");
            lblForca.setStyle("-fx-text-fill: #D3D3D3;");
            TextField campoForca = new TextField();

            Label lblDestreza = new Label("Destreza:");
            lblDestreza.setStyle("-fx-text-fill: #D3D3D3;");
            TextField campoDestreza = new TextField();

            Label lblConstituicao = new Label("Constituição:");
            lblConstituicao.setStyle("-fx-text-fill: #D3D3D3;");
            TextField campoConstituicao = new TextField();

            Label lblInteligencia = new Label("Inteligência:");
            lblInteligencia.setStyle("-fx-text-fill: #D3D3D3;");
            TextField campoInteligencia = new TextField();

            Label lblSabedoria = new Label("Sabedoria:");
            lblSabedoria.setStyle("-fx-text-fill: #D3D3D3;");
            TextField campoSabedoria = new TextField();

            Label lblCarisma = new Label("Carisma:");
            lblCarisma.setStyle("-fx-text-fill: #D3D3D3;");
            TextField campoCarisma = new TextField();
            
        
            for (int i = 0; i < atributos.length; i++) {
                Label label = new Label(atributos[i] + ":");
                label.setStyle("-fx-text-fill: #D3D3D3;");
                TextField campo = new TextField();
                campos[i] = campo;

    
                Button btnAleatorio = criarBotaoAleatorio(campo);
                grid.add(label, 0, i + 1);
                grid.add(campo, 1, i + 1);
                grid.add(btnAleatorio, 2, i + 1);
            }

            
            Button btnSalvar = new Button("Salvar Personagem");
            btnSalvar.setFont(Font.font("Arial", 16));
            btnSalvar.setTextFill(Color.WHITE);
            btnSalvar.setStyle("-fx-background-color: #007ACC; -fx-background-radius: 8;");
            btnSalvar.setOnAction(e -> {
                try {
                    if (campoForca.getText().isEmpty() || campoDestreza.getText().isEmpty() || 
                        campoInteligencia.getText().isEmpty() || campoConstituicao.getText().isEmpty() || campoSabedoria.getText().isEmpty() || campoCarisma.getText().isEmpty()) {
                        throw new IllegalArgumentException("Todos os campos de atributos devem ser preenchidos.");
                    }
                    if (Integer.parseInt(campoForca.getText()) > 20 || Integer.parseInt(campoDestreza.getText()) > 20 || Integer.parseInt(campoInteligencia.getText()) > 20 || Integer.parseInt(campoConstituicao.getText()) > 20 || Integer.parseInt(campoSabedoria.getText()) > 20 || Integer.parseInt(campoCarisma.getText()) > 20) {
                        throw new IllegalArgumentException("Os atributos não podem ser maiores que 20.");
                    }

                    Personagem personagem = new Personagem(personagemId, nome, info, caminhoImagem, 1,
                            Integer.parseInt(campoForca.getText()), Integer.parseInt(campoDestreza.getText()),
                            Integer.parseInt(campoInteligencia.getText()), Integer.parseInt(campoConstituicao.getText()), Integer.parseInt(campoSabedoria.getText()), Integer.parseInt(campoCarisma.getText()));
                    //personagens.add(personagem);
                    PersonagemDBDAO personagemDB = new PersonagemDBDAO();
                    SistemaDBDAO sistemaDB = new SistemaDBDAO();
                    Sistema sistema;
                    try {
                        sistema = sistemaDB.buscaPorCodigo(1);
                    } catch (SQLException ex) {
                        AlertUtil.exibirAlerta("Erro de Banco de Dados", "Erro ao buscar sistema no banco de dados.");
                        return;
                    }
                    ClasseDBDAO classeDB = new ClasseDBDAO();
                    Classe classet;
                    try {
                        classet = classeDB.buscaPorNome(classe);
                    } catch (SQLException ex) {
                        AlertUtil.exibirAlerta("Erro de Banco de Dados", "Erro ao buscar classe no banco de dados.");
                        return;
                    }

                    RacaDBDAO racaDB = new RacaDBDAO();
                    Raca racat;
                    try {
                        racat = racaDB.buscaPorNome(raca);
                    } catch (SQLException ex) {
                        AlertUtil.exibirAlerta("Erro de Banco de Dados", "Erro ao buscar raça no banco de dados.");
                        return;
                    }
                    PersonagemSistemaDBDAO personagemSistemaDB = new PersonagemSistemaDBDAO();
                    PersonagemSistema personagemSistema = new PersonagemSistema(personagem, sistema, racat, classet);

                    try {
                        personagemDB.insere(personagem);
                        personagemSistemaDB.insere(personagemSistema);
                    } catch (SQLException ex) {
                        AlertUtil.exibirAlerta("Erro de Banco de Dados", "Erro ao salvar personagem no banco de dados.");
                    }
                    stageAtributos.close();
                } catch (NumberFormatException ex) {
                    AlertUtil.exibirAlerta("Erro de Formato", "Os atributos devem ser números inteiros.");
                } catch (IllegalArgumentException ex) {
                    AlertUtil.exibirAlerta("Campos Vazios", ex.getMessage());
                }
            });

            grid.add(titulo, 0, 0);
            grid.add(lblForca, 0, 1);
            grid.add(campoForca, 1, 1);
            grid.add(lblDestreza, 0, 2);
            grid.add(campoDestreza, 1, 2);
            grid.add(lblConstituicao, 0, 3);
            grid.add(campoConstituicao, 1, 3);
            grid.add(lblInteligencia, 0, 4);
            grid.add(campoInteligencia, 1, 4);
            grid.add(lblSabedoria, 0, 5);
            grid.add(campoSabedoria, 1, 5);
            grid.add(lblCarisma, 0, 6);
            grid.add(campoCarisma, 1, 6);
            grid.add(btnSalvar, 1, 7);

            Scene cena = new Scene(grid, 400, 300);
            stageAtributos.setScene(cena);
            stageAtributos.show();
        }

        private Button criarBotaoAleatorio(TextField campo) {
            RolagemDados dado2 = new RolagemDados();
            Button btnAleatorio = new Button("🎲"); 
            btnAleatorio.setOnAction(e -> campo.setText(String.valueOf(dado2.rodarAtributos())));
            return btnAleatorio;
        }
        
}
