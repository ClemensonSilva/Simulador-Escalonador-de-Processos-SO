package com.example.processSimulator.controller;

import com.example.processSimulator.HelloApplication;
import com.example.processSimulator.model.PCB;
import com.example.processSimulator.model.schedulersAlg.IScheduler;
import javafx.animation.Timeline;
import javafx.animation.KeyFrame;
import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import org.kordamp.bootstrapfx.BootstrapFX;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class SimuladorController implements Initializable {
    @FXML private AnchorPane rootPane;
    @FXML
    Pane runningProcessContainer;
    @FXML
    HBox readyQueueContainer;
    @FXML
    VBox finishQueueContainer;
    @FXML
    Label clockLabel;
    @FXML private Button startButton;
    @FXML private Button resetButton;
    @FXML private Button returnButton;
    @FXML private Label statusSimulacaoLabel;

    private ObservableList<PCB> listaDeProcessos = FXCollections.observableArrayList();
    private IScheduler algoritm;
    private int clock ;
    private  long sizeListProcess;
    private Timeline simulationTimeline;
    private boolean isRunning = false;
    private Map<Long, Node> processVisualNodes = new HashMap<>(); // Mapeia PID -> Node

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        clockLabel.setText("0");
        simulationTimeline = new Timeline(
                new KeyFrame(Duration.millis(1000), e -> {
                    try {
                        runSingleTick();
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                })
        );
        simulationTimeline.setCycleCount(Timeline.INDEFINITE);
    }

    public void setSimulationParams(ObservableList<PCB> processos, IScheduler algoritm){
        this.listaDeProcessos.addAll(processos);
        this.algoritm = algoritm;
        statusSimulacaoLabel.setText(algoritm.toString());
        this.clock = 0;
        this.sizeListProcess = processos.size();
    }

    public void onStartClick(ActionEvent event) {
        if (isRunning) {
            simulationTimeline.pause();
            startButton.setText("Start");
            statusSimulacaoLabel.setText("Status simulação: PAUSED");
            isRunning = false;
        } else {
            simulationTimeline.play();
            startButton.setText("Pause");
            statusSimulacaoLabel.setText(" " + algoritm.getFinishedList().size() + "  e " + sizeListProcess);
            isRunning = true;
        }
    }

    public void onResetClick(ActionEvent event) {
    }

    public void onAddProcessClick(ActionEvent event) {
    }

    public void onReturnClick(ActionEvent event) {
        if (simulationTimeline != null) {
            simulationTimeline.stop();
        }
        HelloApplication.painelEntrada();
    }

    private void runSingleTick() throws IOException {
        PCB runningProcess;
        this.isRunning = true;
        // cria lista Node  do Ready container
        for (PCB p : this.listaDeProcessos) {
            if (p.getArrivalTime() == this.clock) {
                this.algoritm.addProcess(p);
                Node pcbNode = createProcessVisualNode(p);
                processVisualNodes.put(p.getPid(), pcbNode);
                readyQueueContainer.getChildren().add(pcbNode);
            }
        }

        // a cada clock, verifica se o processo foi encerrado
        if (algoritm.getRunningProcess() != null) {
                // Decrementa o tempo restante
                PCB p = algoritm.getRunningProcess();
                p.setRemaingTime(p.getRemaingTime() - 1);

                // Verificar se o processo terminou
                if (p.getRemaingTime() == 0) {
                    p.setCompletionTime(clock);
                    // TODO Configurar para calcular estatisticas
                    p.ProcgetTurnAround();
                    p.ProcWaitingTime();

                    algoritm.finishProcess();
                    animateToFinishQueue(p);
                    algoritm.setRunningProcess(null);
                }
        }

        if(algoritm.getRunningProcess() == null && !algoritm.isEmpty()) {
            runningProcess = algoritm.nextPCB();
            if(algoritm.getRunningProcess()!= null) animateToRunning(runningProcess);
        }

        if(isSimulationFinished()){
            simulationTimeline.stop();
            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("painel-resultados.fxml"));
            Parent root = loader.load();
            ResultadosController resultadosController = loader.getController();
            resultadosController.setResultsController(algoritm);
            Scene scene = null;
            scene = new Scene(root, 800, 600);
            scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());

            HelloApplication.stage.setTitle("Resultados");
            HelloApplication.stage.setScene(scene);
            HelloApplication.stage.show();
        }

        clock++;
        clockLabel.setText(String.valueOf(clock));
    }

    private boolean isSimulationFinished() {
        return algoritm.getFinishedList().size() == sizeListProcess;
    }

    private void animateToRunning(PCB process) {
        Node processNode = processVisualNodes.get(process.getPid());
        if (processNode == null) return;

        Point2D startPoint = processNode.localToScene(0, 0);

        readyQueueContainer.getChildren().remove(processNode);
        rootPane.getChildren().add(processNode);

        Point2D startPointInRoot = rootPane.sceneToLocal(startPoint);
        processNode.setLayoutX(startPointInRoot.getX());
        processNode.setLayoutY(startPointInRoot.getY());

        // Calculate the position of the running Label
        double targetX = runningProcessContainer.getLayoutX() + (runningProcessContainer.getWidth() - processNode.getBoundsInLocal().getWidth()) / 2;
        double targetY = runningProcessContainer.getLayoutY() + (runningProcessContainer.getHeight() - processNode.getBoundsInLocal().getHeight()) / 2;

        TranslateTransition transition = new TranslateTransition(Duration.millis(500), processNode);
        transition.setToX(targetX - startPointInRoot.getX());
        transition.setToY(targetY - startPointInRoot.getY());

        transition.setOnFinished(e -> {
            rootPane.getChildren().remove(processNode);
            runningProcessContainer.getChildren().add(processNode);
            processNode.setTranslateX(0);
            processNode.setTranslateY(0);
            processNode.setLayoutX((runningProcessContainer.getWidth() - processNode.getBoundsInLocal().getWidth()) / 2);
            processNode.setLayoutY((runningProcessContainer.getHeight() - processNode.getBoundsInLocal().getHeight()) / 2);
        });
        if(algoritm.isEmpty() && algoritm.getRunningProcess() == null) simulationTimeline.stop();

        transition.play();
    }

    private void animateToFinishQueue(PCB process) {
        Node processNode = processVisualNodes.get(process.getPid());
        if (processNode == null) return;

        Point2D startPoint = processNode.localToScene(0, 0);


        runningProcessContainer.getChildren().remove(processNode);
        rootPane.getChildren().add(processNode);

        Point2D startPointInRoot = rootPane.sceneToLocal(startPoint);
        processNode.setLayoutX(startPointInRoot.getX());
        processNode.setLayoutY(startPointInRoot.getY());

        double targetX = finishQueueContainer.getLayoutX() + (finishQueueContainer.getWidth() - processNode.getBoundsInLocal().getWidth()) / 2;
        double targetY = finishQueueContainer.getLayoutY() + (finishQueueContainer.getChildren().size() * (processNode.getBoundsInLocal().getHeight() + finishQueueContainer.getSpacing()));

        TranslateTransition transition = new TranslateTransition(Duration.millis(700), processNode);
        transition.setToX(targetX - startPointInRoot.getX());
        transition.setToY(targetY - startPointInRoot.getY());

        transition.setOnFinished(e -> {
            rootPane.getChildren().remove(processNode);
            finishQueueContainer.getChildren().add(processNode);
            processNode.setTranslateX(0);
            processNode.setTranslateY(0);
            processNode.setLayoutX(0);
            processNode.setLayoutY(0);
        });


        transition.play();
    }


    /**
     * Used to create the visual Process
     * @param p process to be drawn
     * @return the visual node of the process
     */
    private Node createProcessVisualNode(PCB p) {
        Label label = new Label("p" + p.getPid());
        label.setStyle("-fx-background-color: lightblue; -fx-border-color: darkblue; -fx-border-width: 1; -fx-padding: 10 15; -fx-font-weight: bold;");
        label.setPrefSize(60, 40);
        label.setAlignment(javafx.geometry.Pos.CENTER);
        return label;
    }

    public ObservableList<PCB> getListaDeProcessos() {
        return listaDeProcessos;
    }

    public void setListaDeProcessos(ObservableList<PCB> listaDeProcessos) {
        this.listaDeProcessos = listaDeProcessos;
    }

}
