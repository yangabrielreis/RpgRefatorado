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
    import javafx.stage.FileChooser;
    import java.io.File;

    import java.sql.SQLException;
    import java.util.ArrayList;
    import java.util.List;

    import com.trabalhojava.sistemarpg.dao.ClasseDBDAO;
    import com.trabalhojava.sistemarpg.dao.PersonagemDBDAO;
    import com.trabalhojava.sistemarpg.dao.PersonagemSistemaDBDAO;
    import com.trabalhojava.sistemarpg.model.Personagem;
    import com.trabalhojava.sistemarpg.model.PersonagemSistema;
    import com.trabalhojava.sistemarpg.model.Classe;
    import com.trabalhojava.sistemarpg.model.Raca;
    import com.trabalhojava.sistemarpg.model.Sistema;
    import com.trabalhojava.sistemarpg.dao.RacaDBDAO;
    import com.trabalhojava.sistemarpg.dao.SistemaDBDAO;
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

            btnAdicionar.setOnAction(e -> exibirTelaDefinirCaracteristicas());
            btnVer.setOnAction(e -> exibirTelaVerPersonagens());
            btnEditar.setOnAction(e -> exibirTelaEditarPersonagens());

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


        private void exibirTelaDefinirCaracteristicas() {
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

            Button btnProximo = new Button("Próximo: Definir Atributos");
            btnProximo.setFont(Font.font("Arial", 16));
            btnProximo.setTextFill(Color.WHITE);
            btnProximo.setStyle("-fx-background-color: #007ACC; -fx-background-radius: 8;");
            btnProximo.setOnAction(e -> exibirTelaDefinirAtributos(
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


        private Button criarBotaoAleatorio(TextField campo) {
            Button btnAleatorio = new Button("🎲"); 
            btnAleatorio.setOnAction(e -> campo.setText(String.valueOf(dado.rodarAtributos())));
            return btnAleatorio;
        }


        private void exibirTelaDefinirAtributos(int personagemId, String nome, String classe, String raca, String info, String caminhoImagem, Stage telaAnterior) {
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
                    personagens.add(personagem);
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

                private void exibirTelaEditarPersonagens() {
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
        
        /*private int testeAtributo(int atributo)
        {
            return (int)(Math.random() * 20) + 1 + atributo;
        }

        private int rodarAtributos()
        {
            int menor = 7;
            int atual;
            int soma = 0;
            for(int i = 0; i < 4; i++)
            {
                atual = (int)(Math.random() * 6) + 1;
                soma = soma + atual;
                if(menor > atual){
                    menor = atual;
                }
            }
            
            return soma - menor;
        } */

       
    }

    

    