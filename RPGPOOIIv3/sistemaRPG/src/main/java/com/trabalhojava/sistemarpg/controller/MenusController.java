    package com.trabalhojava.sistemarpg.controller;

    import javafx.animation.KeyFrame;
    import javafx.animation.Timeline;
    import javafx.application.Application;
    import javafx.geometry.Insets;
    import javafx.scene.Scene;
    import javafx.scene.control.*;
    import javafx.scene.image.Image;
    import javafx.scene.image.ImageView;
    import javafx.scene.layout.*;
    import javafx.stage.Stage;
    import javafx.scene.text.Font;
    import javafx.scene.text.FontWeight;
    import javafx.scene.paint.Color;
    import javafx.util.Duration;

    import java.sql.SQLException;
    import java.util.ArrayList;
    import java.util.List;
    import com.trabalhojava.sistemarpg.dao.PersonagemSistemaDBDAO;
    import com.trabalhojava.sistemarpg.model.Personagem;
    import com.trabalhojava.sistemarpg.model.PersonagemSistema;
    import com.trabalhojava.sistemarpg.main.RolagemDados;


    public class MenusController extends Application {
        List<Personagem> personagens = new ArrayList<>();
        RolagemDados dado = new RolagemDados();

        public static void main(String[] args) {
            launch(args);
        }

        @Override
        public void start(Stage primaryStage) {
            primaryStage.setTitle("Gerenciador de Personagens");

            BorderPane root = new BorderPane();
            root.setStyle("-fx-background-color: #1E1E1E; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.15), 10, 0.5, 0.0, 0.0);");

            ImageView gifView = new ImageView(new Image("file:/home/shimiraoleg/Desktop/RPGPOOIIv3/sistemaRPG/ninjaBranco.gif"));

            gifView.setFitWidth(250);
            gifView.setFitHeight(350);
            gifView.setPreserveRatio(true);
            gifView.setStyle("-fx-border-color: #2F2F2F; -fx-border-width: 2; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.25), 10, 0.5, 0.0, 0.0);");

            HBox barraTopo = new HBox();
            barraTopo.setSpacing(15);
            barraTopo.setPadding(new Insets(10));
            barraTopo.setStyle("-fx-background-color: #2B2B2B;");

            Button btnAdicionar = new Button("+ Adicionar");
            btnAdicionar.setFont(Font.font("Arial", 16));
            btnAdicionar.setTextFill(Color.WHITE);
            btnAdicionar.setStyle("-fx-background-color: #007ACC; -fx-background-radius: 8;");

            Button btnVer = new Button("👁 Ver Personagens");
            btnVer.setFont(Font.font("Arial", 16));
            btnVer.setTextFill(Color.WHITE);
            btnVer.setStyle("-fx-background-color: #007ACC; -fx-background-radius: 8;");

            Button btnEditar = new Button("✏ Editar Personagens");
            btnEditar.setFont(Font.font("Arial", 16));
            btnEditar.setTextFill(Color.WHITE);
            btnEditar.setStyle("-fx-background-color: #007ACC; -fx-background-radius: 8;");

            exibirDefinir eD = new exibirDefinir();
            btnAdicionar.setOnAction(e -> eD.exibirTelaDefinirCaracteristicas());
            
            btnVer.setOnAction(e -> exibirTelaVerPersonagens());
            //btnEditar.setOnAction(e -> exibirTelaEditarPersonagens());
            exibirEditar eE = new exibirEditar();
            btnEditar.setOnAction(e -> eE.exibirTelaEditarPersonagens());

            barraTopo.getChildren().addAll(btnAdicionar, btnVer, btnEditar);
            root.setTop(barraTopo);

            VBox centro = new VBox();
            centro.setSpacing(15);
            centro.setPadding(new Insets(15));
            centro.setStyle("-fx-alignment: center; -fx-background-color: #1E1E1E;");
            
            Label titulo = new Label("Bem vindo");
            titulo.setFont(Font.font("Verdana", FontWeight.BOLD, 24));
            titulo.setTextFill(Color.WHITE);
            
            centro.getChildren().addAll(titulo, gifView);
            root.setCenter(centro);

            HBox barraRGB = new HBox();
            barraRGB.setPrefHeight(9); 
            root.setBottom(barraRGB);

            Timeline timeline = new Timeline(
                new KeyFrame(Duration.millis(30), e -> { 
                    double offset = (System.currentTimeMillis() % 3000) / 3000.0; 
                    barraRGB.setStyle(String.format("-fx-background-color: linear-gradient(to right, "
                            + "hsb(%f, 100%%, 100%%), "
                            + "hsb(%f, 100%%, 100%%), "
                            + "hsb(%f, 100%%, 100%%), "
                            + "hsb(%f, 100%%, 100%%), "
                            + "hsb(%f, 100%%, 100%%), "
                            + "hsb(%f, 100%%, 100%%), "
                            + "hsb(%f, 100%%, 100%%));",
                            360 * (offset + 0) % 360,
                            360 * (offset + 0.1) % 360,
                            360 * (offset + 0.2) % 360,
                            360 * (offset + 0.3) % 360,
                            360 * (offset + 0.4) % 360,
                            360 * (offset + 0.5) % 360,
                            360 * (offset + 0.6) % 360));
                })
            );
            timeline.setCycleCount(Timeline.INDEFINITE);
            timeline.play();

            Scene cena = new Scene(root, 600, 500);
            primaryStage.setScene(cena);
            primaryStage.show();
        }

        
        private void exibirTelaVerPersonagens() {
            Stage stageVisualizar = new Stage();
            stageVisualizar.setTitle("Personagens Cadastrados");

            VBox vbox = new VBox();
            vbox.setSpacing(10);
            vbox.setPadding(new Insets(15));
            vbox.setStyle("-fx-background-color: #2B2B2B;");


            Label titulo = new Label("Personagens Cadastrados:");
            titulo.setFont(Font.font("Arial", 18));
            titulo.setTextFill(Color.WHITE);
            vbox.getChildren().add(titulo);

            PersonagemSistemaDBDAO personagemSistemaDB = new PersonagemSistemaDBDAO();
            List<PersonagemSistema> personagensSistema;
            try {
                personagensSistema = personagemSistemaDB.listar();
            } catch (SQLException ex) {
                AlertUtil.exibirAlerta("Erro de Banco de Dados", "Erro ao buscar personagens no banco de dados.");
                return;
            }

            if (personagensSistema.isEmpty()) {
                Label vazio = new Label("Nenhum personagem cadastrado.");
                vazio.setFont(Font.font("Arial", 18));
                vazio.setTextFill(Color.WHITE);
                vbox.getChildren().add(vazio);
            } 

            for (PersonagemSistema personagemSistema : personagensSistema) {
                Button personagemButton = new Button(personagemSistema.getPersonagem().getNome() + " - " + personagemSistema.getClasse().getNomeClasse());
                personagemButton.setFont(Font.font("Arial", 16));
                personagemButton.setTextFill(Color.WHITE);
                personagemButton.setStyle("-fx-background-color: #007ACC; -fx-background-radius: 8;");
                personagemButton.setOnAction(e -> {
                    Stage stageDetalhes = new Stage();
                    stageDetalhes.setTitle("Detalhes do Personagem");

                    VBox vboxDetalhes = new VBox();
                    vboxDetalhes.setSpacing(10);
                    vboxDetalhes.setPadding(new Insets(15));
                    vboxDetalhes.setStyle("-fx-background-color: #2B2B2B;");

                    Label tituloDetalhes = new Label("Detalhes do Personagem");
                    tituloDetalhes.setFont(Font.font("Arial", 18));
                    tituloDetalhes.setTextFill(Color.WHITE);
                    vboxDetalhes.getChildren().add(tituloDetalhes);

                    Label lblNome = new Label("Nome: " + personagemSistema.getPersonagem().getNome());
                    lblNome.setFont(Font.font("Arial", 16));
                    lblNome.setTextFill(Color.WHITE);

                    Label lblClasse = new Label("Classe: " + personagemSistema.getClasse().getNomeClasse());
                    lblClasse.setFont(Font.font("Arial", 16));
                    lblClasse.setTextFill(Color.WHITE);

                    Label lblRaca = new Label("Raça: " + personagemSistema.getRaca().getNomeRaca());
                    lblRaca.setFont(Font.font("Arial", 16));
                    lblRaca.setTextFill(Color.WHITE);

                    Label lblDescricao = new Label("Descrição: " + personagemSistema.getPersonagem().getDescricao());
                    lblDescricao.setFont(Font.font("Arial", 16));
                    lblDescricao.setTextFill(Color.WHITE);

                    if(personagemSistema.getPersonagem().getUrlImg() != null) {
                        ImageView imagemView = new ImageView(new Image("file:" + personagemSistema.getPersonagem().getUrlImg()));
                        imagemView.setFitHeight(100);
                        HBox imagemContainer = new HBox(imagemView);
                        imagemView.setPreserveRatio(true);
                        imagemContainer.setPadding(new Insets(10));
                        VBox.setMargin(imagemContainer, new Insets(0, 0, 0, 20));
                        vboxDetalhes.getChildren().add(imagemContainer);
                    }
                    

                    Label lblNvl = new Label("Nível: " + personagemSistema.getNivel());
                    lblNvl.setFont(Font.font("Arial", 16));
                    lblNvl.setTextFill(Color.WHITE);

                    Label lblHp = new Label("Pontos de Vida: " + personagemSistema.getHp());
                    lblHp.setFont(Font.font("Arial", 16));
                    lblHp.setTextFill(Color.WHITE);

                    Label lblForca = new Label("Força: " + personagemSistema.getForca());
                    lblForca.setFont(Font.font("Arial", 16));
                    lblForca.setTextFill(Color.WHITE);

                    Label lblDestreza = new Label("Destreza: " + personagemSistema.getDestreza());
                    lblDestreza.setFont(Font.font("Arial", 16));
                    lblDestreza.setTextFill(Color.WHITE);

                    Label lblConstituicao = new Label("Constituição: " + personagemSistema.getConstituicao());
                    lblConstituicao.setFont(Font.font("Arial", 16));
                    lblConstituicao.setTextFill(Color.WHITE);

                    Label lblInteligencia = new Label("Inteligência: " + personagemSistema.getInteligencia());
                    lblInteligencia.setFont(Font.font("Arial", 16));
                    lblInteligencia.setTextFill(Color.WHITE);

                    Label lblSabedoria = new Label("Sabedoria: " + personagemSistema.getSabedoria());
                    lblSabedoria.setFont(Font.font("Arial", 16));
                    lblSabedoria.setTextFill(Color.WHITE);

                    Label lblCarisma = new Label("Carisma: " + personagemSistema.getCarisma());
                    lblCarisma.setFont(Font.font("Arial", 16));
                    lblCarisma.setTextFill(Color.WHITE);

                    Button btnTesteForca = new Button("Testar Força");
                    btnTesteForca.setFont(Font.font("Arial", 14));
                    btnTesteForca.setTextFill(Color.WHITE);
                    btnTesteForca.setStyle("-fx-background-color: #007ACC; -fx-background-radius: 8;");
                    btnTesteForca.setOnAction(ev -> {
                        int resultado = dado.testeAtributo(personagemSistema.getForca());
                        ResultadoTesteView.exibirResultadoTeste("Força", resultado);
                    });

                    Button btnTesteDestreza = new Button("Testar Destreza");
                    btnTesteDestreza.setFont(Font.font("Arial", 14));
                    btnTesteDestreza.setTextFill(Color.WHITE);
                    btnTesteDestreza.setStyle("-fx-background-color: #007ACC; -fx-background-radius: 8;");
                    btnTesteDestreza.setOnAction(ev -> {
                        int resultado = dado.testeAtributo(personagemSistema.getDestreza());
                        ResultadoTesteView.exibirResultadoTeste("Destreza", resultado);
                    });

                    Button btnTesteConstituicao = new Button("Testar Constituição");
                    btnTesteConstituicao.setFont(Font.font("Arial", 14));
                    btnTesteConstituicao.setTextFill(Color.WHITE);
                    btnTesteConstituicao.setStyle("-fx-background-color: #007ACC; -fx-background-radius: 8;");
                    btnTesteConstituicao.setOnAction(ev -> {
                        int resultado = dado.testeAtributo(personagemSistema.getConstituicao());
                        ResultadoTesteView.exibirResultadoTeste("Constituição", resultado);
                    });

                    Button btnTesteInteligencia = new Button("Testar Inteligência");
                    btnTesteInteligencia.setFont(Font.font("Arial", 14));
                    btnTesteInteligencia.setTextFill(Color.WHITE);
                    btnTesteInteligencia.setStyle("-fx-background-color: #007ACC; -fx-background-radius: 8;");
                    btnTesteInteligencia.setOnAction(ev -> {
                        int resultado = dado.testeAtributo(personagemSistema.getInteligencia());
                        ResultadoTesteView.exibirResultadoTeste("Inteligência", resultado);
                    });

                    Button btnTesteSabedoria = new Button("Testar Sabedoria");
                    btnTesteSabedoria.setFont(Font.font("Arial", 14));
                    btnTesteSabedoria.setTextFill(Color.WHITE);
                    btnTesteSabedoria.setStyle("-fx-background-color: #007ACC; -fx-background-radius: 8;");
                    btnTesteSabedoria.setOnAction(ev -> {
                        int resultado = dado.testeAtributo(personagemSistema.getSabedoria());
                        ResultadoTesteView.exibirResultadoTeste("Sabedoria", resultado);
                    });

                    Button btnTesteCarisma = new Button("Testar Carisma");
                    btnTesteCarisma.setFont(Font.font("Arial", 14));
                    btnTesteCarisma.setTextFill(Color.WHITE);
                    btnTesteCarisma.setStyle("-fx-background-color: #007ACC; -fx-background-radius: 8;");
                    btnTesteCarisma.setOnAction(ev -> {
                        int resultado = dado.testeAtributo(personagemSistema.getCarisma());
                        ResultadoTesteView.exibirResultadoTeste("Carisma", resultado);
                    });

                    HBox hboxForca = new HBox(lblForca, btnTesteForca);
                    hboxForca.setSpacing(10);
                    HBox hboxDestreza = new HBox(lblDestreza, btnTesteDestreza);
                    hboxDestreza.setSpacing(10);
                    HBox hboxConstituicao = new HBox(lblConstituicao, btnTesteConstituicao);
                    hboxConstituicao.setSpacing(10);
                    HBox hboxInteligencia = new HBox(lblInteligencia, btnTesteInteligencia);
                    hboxInteligencia.setSpacing(10);
                    HBox hboxSabedoria = new HBox(lblSabedoria, btnTesteSabedoria);
                    hboxSabedoria.setSpacing(10);
                    HBox hboxCarisma = new HBox(lblCarisma, btnTesteCarisma);
                    hboxCarisma.setSpacing(10);

                    vboxDetalhes.getChildren().addAll(lblNome, lblClasse, lblRaca, lblDescricao, lblNvl, lblHp, hboxForca, hboxDestreza, hboxConstituicao, hboxInteligencia, hboxSabedoria, hboxCarisma);

                    Scene cenaDetalhes = new Scene(vboxDetalhes, 400, 500);
                    stageDetalhes.setScene(cenaDetalhes);
                    stageDetalhes.show();
                });

                vbox.getChildren().add(personagemButton);
            }

            Scene cena = new Scene(vbox, 400, 300);
            stageVisualizar.setScene(cena);
            stageVisualizar.show();
        }
    }