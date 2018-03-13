package guistablemarriage;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.*;
import javafx.geometry.*;
public class AlertBox {
    public static boolean value;
    public static boolean display(String message) {
        value = false;
        HBox hbBtns;
        VBox vbCtrls;
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        Button btnSubmit, btnExit;
        TextArea Msg;
        Msg = new TextArea();
        Msg.setPrefWidth(400);
        Msg.setPrefHeight(200);
        Msg.setEditable(false);
        Msg.setText(message);
        btnSubmit = new Button("Yes");
        btnSubmit.setDefaultButton(true);
        btnSubmit.setPrefWidth(75);
        btnSubmit.setOnAction(e -> {
            window.close();
            value = true;
        });
        btnExit = new Button("No");
        btnExit.setPrefWidth(75);
        btnExit.setOnAction(e->{
            window.close();
            value = false;
        });
        hbBtns = new HBox(10);
        hbBtns.setAlignment(Pos.CENTER);
        hbBtns.getChildren().addAll(btnSubmit, btnExit);
        vbCtrls = new VBox(20);
        vbCtrls.getChildren().addAll(Msg, hbBtns);
        hbBtns.setTranslateY(-10);
        
        Scene scene1 = new Scene(vbCtrls, 345, 150);
        window.setScene(scene1);
        window.showAndWait();
        return value;
    }
}