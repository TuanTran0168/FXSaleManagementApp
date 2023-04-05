/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tuantran.fxsaleapp;

import com.tuantran.utils.MessageBox;
import java.io.IOException;
import java.util.function.UnaryOperator;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.TextFormatter.Change;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 *
 * @author TuanTran
 */
public class FormUtils {

    public void newForm(String formName, String titleForm) throws IOException {
        formName = formName + ".fxml";

        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();

        double screenWidth = bounds.getWidth();
        double screenHeight = bounds.getHeight();

        Parent form = FXMLLoader.load(getClass().getResource(formName));
        Scene formScene = new Scene(form);
        Stage formStage = new Stage();
        formStage.setScene(formScene);

        formStage.setTitle(titleForm);
        formStage.setWidth(screenWidth);
        formStage.setHeight(screenHeight);
        formStage.show();
    }

    public void newForm(String formName, String titleForm, double width, double height) throws IOException {
        formName = formName + ".fxml";
        Parent form = FXMLLoader.load(getClass().getResource(formName));
        Scene formScene = new Scene(form);
        Stage formStage = new Stage();
        formStage.setScene(formScene);

        formStage.setTitle(titleForm);
        formStage.setWidth(width);
        formStage.setHeight(height);

        formStage.show();
    }

    public void newForm(String formName, String titleForm, Object object) throws IOException {
        formName = formName + ".fxml";

        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();

        double screenWidth = bounds.getWidth();
        double screenHeight = bounds.getHeight();

        Parent form = FXMLLoader.load(getClass().getResource(formName));
        Scene formScene = new Scene(form);
        Stage formStage = new Stage();
        formStage.setScene(formScene);

        formStage.setTitle(titleForm);
        formStage.setWidth(screenWidth);
        formStage.setHeight(screenHeight);
//        formStage.setFullScreen(true);
        formStage.setUserData(object);
        formStage.show();
    }

//    public void onlyDoubleNumbers(TextField textField) {
//        UnaryOperator<Change> filter = change -> {
//            String text = change.getText();
//            if (text.matches("[0-9]*")) { // Chỉ cho phép nhập số
//                return change;
//            } else {
//                Alert a = MessageBox.getBox("Cảnh báo", "Vui lòng nhập số!!", Alert.AlertType.WARNING);
//                a.show();
//            }
//            return null;
//        };
//        TextFormatter<String> formatter = new TextFormatter<>(filter);
//        textField.setTextFormatter(formatter);
//    }
    public void onlyDoubleNumbers(TextField textField) {
        textField.setTextFormatter(new TextFormatter<>(change -> {
            String newText = change.getControlNewText();
            if (newText.matches("^[0-9]*(\\.[0-9]*)?$")) {
                return change;
            } 
            else {
                MessageBox.getBox("Cảnh báo", "Bạn chỉ có thể nhập số vào ô này!", Alert.AlertType.WARNING).show();
            }
            return null;
        }));
    }
    
    public void onlyNumbers(TextField textField) {
        textField.setTextFormatter(new TextFormatter<>(change -> {
            String newText = change.getControlNewText();
            if (newText.matches("^[0-9]*")) {
                return change;
            } 
            else {
                MessageBox.getBox("Cảnh báo", "Bạn chỉ có thể nhập số vào ô này!", Alert.AlertType.WARNING).show();
            }
            return null;
        }));
    }

}
