package com.example.processSimulator.controller;
import com.example.processSimulator.model.PCB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.stream.Collectors;

public class PainelEntradaController {

    @FXML
    private TextField arrivalTimeTextField;

    @FXML
    private TextField execucaoTextField;
    @FXML
    private TextField pidTextField;

    @FXML
    private Button adicionarButton;

    @FXML
    private TabelaController tabelaController;

    private ObservableList<PCB> listaDeProcessos = FXCollections.observableArrayList();

    @FXML
    private ComboBox<String> algoritmoComboBox; // Elemento nao será mais necessário aqui, mas sera criado em outra tela ao clicar em iniciar simulacao

    /**
     * Opcional: Este método é chamado automaticamente após o FXML ser carregado.
     * Útil para configurações iniciais.
     */

    @FXML
    private void initialize() {

        if (tabelaController != null){
            tabelaController.getProcessosTable().setEditable(true);
            tabelaController.setProcessos(listaDeProcessos);
        }
        else tabelaController.getProcessosTable().setPlaceholder(new Label("Nenhum processo criado!"));
    }


    // TODO como salvar o algoritmo escolihod como variavel global
    /**
     * Este método é chamado quando o botão "Adicionar Processo" é clicado,
     * conforme definido pelo onAction="#handleAdicionarProcesso" no FXML.
     */

    @FXML
    protected void handleAdicionarProcesso(ActionEvent event) {
        String pid = pidTextField.getText();
        String arrivalTime = arrivalTimeTextField.getText();
        String tempoExecucao = execucaoTextField.getText();

        PCB pcb = new PCB(Long.parseLong(pid), Long.parseLong(tempoExecucao), Long.parseLong(arrivalTime));

        listaDeProcessos.add(pcb);

        pidTextField.clear();
        arrivalTimeTextField.clear();
        execucaoTextField.clear();
    }

    /**
     * Método usado para criar processos através da leitura de arquivo
     * @param event
     * Link para documentacao: https://jenkov.com/tutorials/javafx/filechooser.html
     */
    @FXML
    protected void handleCarregarArquivo(ActionEvent event){

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Abrir Arquivo de Processos");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Arquivos de Texto", "*.txt")
        );

        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        File arquivo = fileChooser.showOpenDialog(stage);

        if (arquivo != null) {
            try {
                List<PCB> novosProcessos;
                novosProcessos = Files.lines(arquivo.toPath())
                        .map(linha -> linha.split(","))
                        .map(dados -> {
                            long pid = Long.parseLong(dados[0].trim());
                            long burstTime = Long.parseLong(dados[1].trim());
                            long arrivalTime = Long.parseLong(dados[2].trim());
                            return new PCB(pid, burstTime, arrivalTime);
                        })
                        .collect(Collectors.toList());

                listaDeProcessos.addAll(novosProcessos);

            } catch (IOException e) {
                mostrarAlerta("Erro de Leitura", "Não foi possível ler o arquivo: " + e.getMessage());
            } catch (Exception e) {
                // Pega erros de formato (ex: NumberFormatException ou linha mal formatada)
                mostrarAlerta("Erro de Formato", "O arquivo está mal formatado. " + e.getMessage());
            }
        }


    }


    /**
     * Método responsavel por disparar evento que criará segunda tela onde toda a animação do escalonamento será execultada.
     * @param event
     */
    public void handleIniciarSimulacao(ActionEvent event) {
    }

    private void mostrarAlerta(String titulo, String mensagem) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
}
