package com.example.processSimulator.view;

import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.SVGPath;

public class RocketNode extends Group {
    public RocketNode(String name) {
        // --- 1. O Corpo e as Aletas do Foguete (SVG) ---
        // SVGPath é ótimo para formas complexas.
        // O "content" abaixo são coordenadas vetoriais que desenham o corpo.
        SVGPath corpo = new SVGPath();
        corpo.setContent("M24.5,2.5 C24.5,2.5 5.3,27.4 5.3,46.7 C5.3,56.4 13.3,64 24.5,64 C35.7,64 43.7,56.4 43.7,46.7 C43.7,27.4 24.5,2.5 24.5,2.5 Z M5,42 L-8,59 L5,59 Z M44,42 L57,59 L44,59 Z");
        corpo.setFill(Color.web("#ecf0f1"));
        corpo.setStroke(Color.web("#bdc3c7"));
        corpo.setStrokeWidth(2);

        Label label =new Label(name);
        label.setLayoutX(24.5);
        label.setLayoutY(45);   // Logo abaixo da janela

        label.translateXProperty().bind(label.widthProperty().divide(-2));


        // --- 2. A Janelinha (Círculo) ---
        Circle janelaBorda = new Circle(24.5, 30, 10, Color.web("#95a5a6"));
        Circle janelaVidro = new Circle(24.5, 30, 7, Color.web("#3498db"));

        // --- 3. O Fogo na base (SVG) ---
        SVGPath fogo = new SVGPath();
        fogo.setContent("M15,64 L20,85 L25,68 L30,90 L35,64 Z");
        fogo.setFill(Color.web("#e74c3c"));
        fogo.setStroke(Color.web("#c0392b"));

        this.getChildren().addAll(fogo, corpo, janelaBorda, janelaVidro, label);

        this.setTranslateX(-24.5);
        this.setTranslateY(-45);

        this.setScaleX(1.5);
        this.setScaleY(1.5);
    }
}
