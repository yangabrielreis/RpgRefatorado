package com.trabalhojava.sistemarpg.controller;

import java.io.File;
import java.sql.SQLException;
import java.util.List;

import com.trabalhojava.sistemarpg.dao.ClasseDBDAO;
import com.trabalhojava.sistemarpg.dao.PersonagemDBDAO;
import com.trabalhojava.sistemarpg.dao.PersonagemSistemaDBDAO;
import com.trabalhojava.sistemarpg.dao.RacaDBDAO;
import com.trabalhojava.sistemarpg.model.Classe;
import com.trabalhojava.sistemarpg.model.Personagem;
import com.trabalhojava.sistemarpg.model.PersonagemSistema;
import com.trabalhojava.sistemarpg.model.Raca;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class exibirEditar {
    public void exibirTelaEditarPersonagens() {
                    Stage stageEditar = new Stage();
                    stageEditar.setTitle("Editar Personagens");

                    VBox vbox = new VBox();
                    vbox.setSpacing(10);
                    vbox.setPadding(new Insets(15));
                    vbox.setStyle("-fx-background-color: #2B2B2B;");

                    PersonagemSistemaDBDAO personagemSistemaDB = new PersonagemSistemaDBDAO();
                    List<PersonagemSistema> personagensSistema;
                    try {
                    personagensSistema = personagemSistemaDB.listar();
                    } catch (SQLException ex) {
                        AlertUtil.exibirAlerta("Erro de Banco de Dados", "Erro ao buscar personagens no banco de dados.");
                    return;
                    }

                    for (PersonagemSistema personagemSistema : personagensSistema) {
                    Button personagemButton = new Button(personagemSistema.getPersonagem().getNome() + " - " + personagemSistema.getClasse().getNomeClasse());
                    personagemButton.setFont(Font.font("Arial", 16));
                    personagemButton.setTextFill(Color.WHITE);
                    personagemButton.setStyle("-fx-background-color: #007ACC; -fx-background-radius: 8;");
                    personagemButton.setOnAction(e -> {
                        Stage stageEditarPersonagem = new Stage();
                        stageEditarPersonagem.setTitle("Editar Personagem");

                        GridPane grid = new GridPane();
                        grid.setPadding(new Insets(20));
                        grid.setHgap(10);
                        grid.setVgap(10);
                        grid.setStyle("-fx-background-color: #2B2B2B;");

                        Label titulo = new Label("Editar Personagem");
                        titulo.setFont(Font.font("Arial", 18));
                        titulo.setTextFill(Color.WHITE);
                        GridPane.setColumnSpan(titulo, 2);

                        TextField campoNome = new TextField(personagemSistema.getPersonagem().getNome());
                        ComboBox<String> campoClasse = new ComboBox<>();
                        ComboBox<String> campoRaca = new ComboBox<>();
                        campoRaca.getItems().addAll("Humano", "Elfo", "Anão", "Orc");
                        campoClasse.getItems().addAll("Guerreiro", "Mago", "Arqueiro", "Ladino");
                        campoRaca.setValue(personagemSistema.getRaca().getNomeRaca());
                        campoClasse.setValue(personagemSistema.getClasse().getNomeClasse());
                        TextField campoInfo = new TextField(personagemSistema.getPersonagem().getDescricao());
                        TextField campoImagem = new TextField(personagemSistema.getPersonagem().getUrlImg());
                        TextField campoForca = new TextField(String.valueOf(personagemSistema.getPersonagem().getForca()));
                        TextField campoDestreza = new TextField(String.valueOf(personagemSistema.getPersonagem().getDestreza()));
                        TextField campoInteligencia = new TextField(String.valueOf(personagemSistema.getPersonagem().getInteligencia()));
                        TextField campoConstituicao = new TextField(String.valueOf(personagemSistema.getPersonagem().getConstituicao()));
                        TextField campoSabedoria = new TextField(String.valueOf(personagemSistema.getPersonagem().getSabedoria()));
                        TextField campoCarisma = new TextField(String.valueOf(personagemSistema.getPersonagem().getCarisma()));

                        Button btnSelecionarImagem = new Button("Selecionar Imagem");
                        btnSelecionarImagem.setFont(Font.font("Arial", 14));
                        btnSelecionarImagem.setTextFill(Color.WHITE);
                        btnSelecionarImagem.setStyle("-fx-background-color: #007ACC; -fx-background-radius: 8;");
                        btnSelecionarImagem.setOnAction(ev -> {
                        FileChooser fileChooser = new FileChooser();
                        fileChooser.setTitle("Selecionar Imagem");
                        fileChooser.getExtensionFilters().addAll(
                        new FileChooser.ExtensionFilter("Imagens", "*.png", "*.jpg", "*.jpeg", "*.gif")
                        );
                        File selectedFile = fileChooser.showOpenDialog(stageEditarPersonagem);
                        campoImagem.setText(selectedFile.getAbsolutePath());                    
                        });
                        grid.add(btnSelecionarImagem, 2, 5);

                        Button btnSalvar = new Button("Salvar Alterações");
                        btnSalvar.setFont(Font.font("Arial", 16));
                        btnSalvar.setTextFill(Color.WHITE);
                        btnSalvar.setStyle("-fx-background-color: #007ACC; -fx-background-radius: 8;");
                        btnSalvar.setOnAction(ev -> {
                        try {

                            if (campoForca.getText().isEmpty() || campoDestreza.getText().isEmpty() || campoInteligencia.getText().isEmpty() || campoConstituicao.getText().isEmpty() || campoSabedoria.getText().isEmpty() || campoCarisma.getText().isEmpty()) {
                            throw new IllegalArgumentException("Todos os campos de atributos devem ser preenchidos.");
                            }
                            if (Integer.parseInt(campoForca.getText()) > 20 || Integer.parseInt(campoDestreza.getText()) > 20 || Integer.parseInt(campoInteligencia.getText()) > 20 || Integer.parseInt(campoConstituicao.getText()) > 20 || Integer.parseInt(campoSabedoria.getText()) > 20 || Integer.parseInt(campoCarisma.getText()) > 20) {
                                AlertUtil.exibirAlerta("Valores inválidos", "Os atributos devem não podem ser maiores que 20.");    
                            throw new IllegalArgumentException("Os atributos não podem ser maiores que 20.");
                            }

                            Personagem personagemNovo = new Personagem(
                            personagemSistema.getPersonagem().getPersonagemId(),
                            campoNome.getText(),
                            campoInfo.getText(),
                            campoImagem.getText(),
                            personagemSistema.getPersonagem().getNivel(),
                            Integer.parseInt(campoForca.getText()),
                            Integer.parseInt(campoDestreza.getText()),
                            Integer.parseInt(campoInteligencia.getText()),
                            Integer.parseInt(campoConstituicao.getText()),
                            Integer.parseInt(campoSabedoria.getText()),
                            Integer.parseInt(campoCarisma.getText())
                            );

                            PersonagemDBDAO personagemDB = new PersonagemDBDAO();
                            personagemDB.atualizar(personagemNovo);

                            ClasseDBDAO classeDB = new ClasseDBDAO();
                            RacaDBDAO racaDB = new RacaDBDAO();
                            Classe classeNova = classeDB.buscaPorNome(campoClasse.getValue());
                            Raca racaNova = racaDB.buscaPorNome(campoRaca.getValue());

                            PersonagemSistema personagemSistemaNovo = new PersonagemSistema(
                            personagemNovo,
                            personagemSistema.getSistema(),
                            racaNova,
                            classeNova
                            );
                            personagemSistemaDB.atualizar(personagemSistema, personagemSistemaNovo);
                            stageEditarPersonagem.close();
                            stageEditar.close();
                            exibirTelaEditarPersonagens();
                        } catch (NumberFormatException ex) {
                            AlertUtil.exibirAlerta("Erro de Formato", "Os atributos devem ser números inteiros.");
                        } catch (SQLException ex) {
                            AlertUtil.exibirAlerta("Erro de Banco de Dados", "Erro ao atualizar personagem no banco de dados.");
                        }
                        });
                        
                        Label nomeLabel = new Label("Nome:");
                        nomeLabel.setStyle("-fx-text-fill: white;");
                        grid.add(nomeLabel, 0, 1);

                        Label classeLabel = new Label("Classe:");
                        classeLabel.setStyle("-fx-text-fill: white;");
                        grid.add(classeLabel, 0, 2);

                        Label racaLabel = new Label("Raça:");
                        racaLabel.setStyle("-fx-text-fill: white;");
                        grid.add(racaLabel, 0, 3);

                        Label descricaoLabel = new Label("Descrição:");
                        descricaoLabel.setStyle("-fx-text-fill: white;");
                        grid.add(descricaoLabel, 0, 4);

                        Label caminhoImagemLabel = new Label("Caminho da Imagem:");
                        caminhoImagemLabel.setStyle("-fx-text-fill: white;");
                        grid.add(caminhoImagemLabel, 0, 5);

                        Label forcaLabel = new Label("Força:");
                        forcaLabel.setStyle("-fx-text-fill: white;");
                        grid.add(forcaLabel, 0, 6);

                        Label destrezaLabel = new Label("Destreza:");
                        destrezaLabel.setStyle("-fx-text-fill: white;");
                        grid.add(destrezaLabel, 0, 7);

                        Label constituicaoLabel = new Label("Constituição:");
                        constituicaoLabel.setStyle("-fx-text-fill: white;");
                        grid.add(constituicaoLabel, 0, 8);

                        Label inteligenciaLabel = new Label("Inteligência:");
                        inteligenciaLabel.setStyle("-fx-text-fill: white;");
                        grid.add(inteligenciaLabel, 0, 9);

                        Label sabedoriaLabel = new Label("Sabedoria:");
                        sabedoriaLabel.setStyle("-fx-text-fill: white;");
                        grid.add(sabedoriaLabel, 0, 10);

                        Label carismaLabel = new Label("Carisma:");
                        carismaLabel.setStyle("-fx-text-fill: white;");
                        grid.add(carismaLabel, 0, 11);

                        grid.add(campoNome, 1, 1);
                        grid.add(campoClasse, 1, 2);
                        grid.add(campoRaca, 1, 3);
                        grid.add(campoInfo, 1, 4);
                        grid.add(campoImagem, 1, 5);
                        grid.add(campoForca, 1, 6);
                        grid.add(campoDestreza, 1, 7);
                        grid.add(campoConstituicao, 1, 8); 
                        grid.add(campoInteligencia, 1, 9);
                        grid.add(campoSabedoria, 1, 10);
                        grid.add(campoCarisma, 1, 11);
                        grid.add(btnSalvar, 1, 12);

                        Scene cena = new Scene(grid, 400, 500);
                        stageEditarPersonagem.setScene(cena);
                        stageEditarPersonagem.show();
                    });

                    vbox.getChildren().add(personagemButton);
                    }

                    Scene cena = new Scene(vbox, 400, 300);
                    stageEditar.setScene(cena);
                    stageEditar.show();
}
}
