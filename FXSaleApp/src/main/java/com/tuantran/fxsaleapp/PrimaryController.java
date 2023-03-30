package com.tuantran.fxsaleapp;

import com.tuantran.pojo.NhanVien;
import com.tuantran.services.NhanVienService;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


public class PrimaryController implements Initializable{
    @FXML private TextField txtTaiKhoan;
    @FXML private TextField txtMatKhau;
    @FXML private Button btnDangNhap;
    
    private static Scene scene;
    private final FormUtils FORM_UTILS = new FormUtils();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        
    }
    
    public void dangNhap(ActionEvent evt) throws IOException {
        String tk = this.txtTaiKhoan.getText();
        String mk = this.txtMatKhau.getText();
        NhanVienService nhanVienService = new NhanVienService();
        
        List<NhanVien> nhanViens;
        try {
            nhanViens = nhanVienService.getNhanViens();
            for (NhanVien nv : nhanViens) {
                if (nv.getTaiKhoan().equals(tk) && nv.getMatKhau().equals(mk)) {
                    if (nv.isQuanLy())
                    {
                        String formName = "FormQuanLyBanHang";
                        String formTilte = "Quản lý bán hàng";
                        
//                        FORM_UTILS.newForm(formName, formTilte);
                        FORM_UTILS.newForm(formName, formTilte, nv);
                        Stage oldStage = (Stage) btnDangNhap.getScene().getWindow(); 
                        
                        oldStage.close();
                    }
                    else {
                        String formName = "FormNhanVienBanHang";
                        String formTilte = "Nhân viên bán hàng";
                        
                        FORM_UTILS.newForm(formName, formTilte, nv);
                        Stage oldStage = (Stage) btnDangNhap.getScene().getWindow(); 
                        oldStage.close();
                    }

                    break;
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(PrimaryController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
