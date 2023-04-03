/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tuantran.fxsaleapp;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
}
