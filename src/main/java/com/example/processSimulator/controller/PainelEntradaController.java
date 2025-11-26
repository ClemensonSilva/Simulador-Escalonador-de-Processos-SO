package com.example.processSimulator.controller;
import com.example.processSimulator.HelloApplication;
import com.example.processSimulator.model.PCB;
import com.example.processSimulator.model.schedulersAlg.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.kordamp.bootstrapfx.BootstrapFX;

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
    @FXML private HBox priorityHBox;
    @FXML private  HBox quantumHbox;
    @FXML private TextField quantumTextField;

    @FXML
    private Button adicionarButton;

    @FXML
    private TabelaController tabelaController;

    private ObservableList<PCB> processList = FXCollections.observableArrayList();

    @FXML
    private ComboBox<IScheduler> algoritmoComboBox;
    // TODO adicionar campo priority

    /**
     * Opcional: Este método é chamado automaticamente após o FXML ser
     *
     * carregado.
     * Útil para configurações iniciais.
     */

    @FXML
    private void initialize() {
        setComboBox();
        if (tabelaController != null){
            tabelaController.getProcessosTable().setEditable(true);
            tabelaController.setProcessos(processList);
            tabelaController.getProcessosTable().setPlaceholder(new Label("Nenhum processo criado!"));
        }
        priorityHBox.setVisible(false);
        priorityHBox.setManaged(false);
        quantumHbox.setVisible(false);
        quantumHbox.setManaged(false);

    }

    @FXML
    private  void setComboBox(){
        algoritmoComboBox.getItems().addAll(new FCFS(),new SJF(), new PriorityScheduling(), new RoundRobin());
    }
    private int pid = 0; // TODO melhoria: retirar essa linha daqui
    @FXML
    protected void handleAdicionarProcesso(ActionEvent event) {

        String arrivalTime = arrivalTimeTextField.getText();
        String tempoExecucao = execucaoTextField.getText();
        // TODO refatorar, DRY

        String quantum = "0";
        if(algoritmoComboBox.getValue() instanceof RoundRobin) {
             quantum = quantumTextField.getText();
        }

        if(!arrivalTime.isBlank() || !tempoExecucao.isBlank()) {
            pid += 1;
            PCB pcb = new PCB(pid, Long.parseLong(tempoExecucao), Long.parseLong(arrivalTime), Long.parseLong(quantum), 0);

            processList.add(pcb);

            pidTextField.clear();
            arrivalTimeTextField.clear();
            execucaoTextField.clear();
        }else
            showAlert("Falha ao criar processo","Você deve preencher os campos para criar um processo!", Alert.AlertType.WARNING);

    }

    public void handleEscolhaAlgoritmo(ActionEvent event){
            boolean instanceOf = algoritmoComboBox.getValue() instanceof PriorityScheduling;

            priorityHBox.setManaged(instanceOf);
            priorityHBox.setVisible(instanceOf);

            instanceOf = algoritmoComboBox.getValue() instanceof  RoundRobin;
            quantumHbox.setManaged(instanceOf);
            quantumHbox.setVisible(instanceOf);
         showAlert("Mudanca","ALGORITMO MODIFICADO PARA " + algoritmoComboBox.getValue(), Alert.AlertType.INFORMATION);
    }


    /**
     * Método responsavel por disparar evento que criará segunda tela onde toda a animação do escalonamento será execultada.
     * @param event
     */
    public void handleIniciarSimulacao(ActionEvent event) throws IOException {

        if(!this.processList.isEmpty()){
            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("painel-simulacao.fxml"));
            Parent root = loader.load();
            SimuladorController controller = loader.getController();
            // passar lista de processos e algoritmo escolhido
            controller.setSimulationParams(this.processList, this.algoritmoComboBox.getValue());

            Scene scene = null;
            scene = new Scene(root, 800, 600);
            scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());

            HelloApplication.stage.setTitle("Simulação");
            HelloApplication.stage.setScene(scene);
            HelloApplication.stage.show();
        }
        else showAlert("Lista vazia", "Nenhum processo foi adicionado!", Alert.AlertType.ERROR);

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
        File file = fileChooser.showOpenDialog(stage);

        // TODO refatorar, DRY
        String quantum;
        quantum = "0";
        if(algoritmoComboBox.getValue() instanceof RoundRobin) {
            if(!quantumTextField.getText().isBlank()) quantum = quantumTextField.getText() ;
            else{
                tabelaController.getProcessosTable().getItems().clear();
                HelloApplication.showAlert("Quantum", "Defina o quantum para o processo", Alert.AlertType.ERROR);
                return;
            }
        }

        if (file != null) {
            try {
                List<PCB> novosProcessos;
                String finalQuantum = quantum;
                novosProcessos = Files.lines(file.toPath())
                        .map(linha -> linha.split(","))
                        .map(dados -> {
                            long burstTime = Long.parseLong(dados[0].trim());
                            long arrivalTime = Long.parseLong(dados[1].trim());
                            long priority = 0;
                            if(dados.length == 3){
                                 priority = Long.parseLong(dados[2].trim());
                            }
                            this.pid++;
                            return new PCB(this.pid, burstTime, arrivalTime, Long.parseLong(finalQuantum),priority);
                        })
                        .collect(Collectors.toList());
                processList.addAll(novosProcessos);

            } catch (IOException e) {
                showAlert("Erro de Leitura", "Não foi possível ler o arquivo: " + e.getMessage(), Alert.AlertType.INFORMATION);
            } catch (Exception e) {
                // Pega erros de formato (ex: NumberFormatException ou linha mal formatada)
                showAlert("Erro de Formato", "O arquivo está mal formatado. " + e.getMessage(), Alert.AlertType.INFORMATION);
            }
        }
    }

    private void showAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void handleCleanTable(ActionEvent event) {
        processList.clear();
    }
}
