/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tuantran.fxsaleapp;

import com.tuantran.utils.MessageBox;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.function.UnaryOperator;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.TextFormatter.Change;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.StringConverter;

/**
 *
 * @author TuanTran
 */
public class FormUtils {

    public final String MY_DATE_FORMAT = "dd/MM/yyyy";
    public final double SO_TIEN_GIAM_GIA_THEO_QUY_DINH = 1000000;
    public final double PHAN_TRAM_DISCOUNT = 10 / (100 * 1.0);
    public final String PHAN_TRAM_DISCOUNT_TEXT = this.PHAN_TRAM_DISCOUNT * 100 + "%";

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
            //^[0-9][0-9]*(\\.[0-9]*)?$
            if (newText.matches("^[0-9]*(\\.[0-9]*)?$")) {
                return change;
            } else {
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
            } else {
                MessageBox.getBox("Cảnh báo", "Bạn chỉ có thể nhập số vào ô này!", Alert.AlertType.WARNING).show();
            }
            return null;
        }));
    }

    public void formatDate(String yourDateFormat, DatePicker datePicker) {

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(yourDateFormat);

        StringConverter<LocalDate> converter = new StringConverter<LocalDate>() {
            @Override
            public String toString(LocalDate date) {
                if (date != null) {
                    return dateFormatter.format(date);
                } else {
                    return "";
                }
            }

            @Override
            public LocalDate fromString(String string) {
                if (string != null && !string.isEmpty()) {
                    return LocalDate.parse(string, dateFormatter);
                } else {
                    return null;
                }
            }
        };
        datePicker.setConverter(converter);
    }

}
