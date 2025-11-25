package com.example.processSimulator;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import org.kordamp.bootstrapfx.BootstrapFX;

import java.io.IOException;
/**
 * Classe principal da aplicação JavaFX.
 * Responsável por iniciar o palco principal da aplicação e prover métodos
 * utilitários para carregar telas, abrir novos palcos temporários e fechar palco atual.
 */

public class HelloApplication extends Application {

    public static Stage stage;

    /**
     * Responsavel por criar o palco inicial da aplicação com a tela inicial para a aplicação
     * @param stage palco principal que o JavaFX cria ao iniciar a app
     * @throws Exception caso ocorra erro ao carregar a primeira cena
     */
    public void start(Stage stage) throws Exception {
        this.stage = stage;
        painelEntrada();
    }

    public static void carregarCena(String titulo, String caminhoFXML) {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource(caminhoFXML));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load(), 800, 600);
            scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        stage.setTitle(titulo);
        stage.setScene(scene);
        stage.show();
    }

    public static void retirarPalco() {
        stage.close();
    }

    public static void carregarPalcoSecundario(String titulo, String caminhoFXML) {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource(caminhoFXML));
        Scene scene = null;
        Stage stageTemporario = new Stage();
        try {
            scene = new Scene(fxmlLoader.load(), 1000, 800);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        stageTemporario.setTitle(titulo);
        stageTemporario.setScene(scene);
        stageTemporario.show();
    }

    public static void painelEntrada() {
        carregarCena("Painel Entrada", "painel-entrada.fxml");
    }

    public static void showAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
