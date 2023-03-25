package com.tuantran.fxsaleapp;

import com.tuantran.pojo.NhanVien;
import com.tuantran.services.NhanVienService;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;


public class PrimaryController implements Initializable{
    @FXML private TextField txtTaiKhoan;
    @FXML private TextField txtMatKhau;
    @FXML private TextField txtKiemTraXiuThoi;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        
    }
    
    public void dangNhap(ActionEvent evt) {
        String tk = this.txtTaiKhoan.getText();
        String mk = this.txtMatKhau.getText();
        String test = "FAIL";
        NhanVienService nhanVienService = new NhanVienService();
        
        List<NhanVien> nhanViens;
        try {
            nhanViens = nhanVienService.getNhanViens();
            for (NhanVien nv : nhanViens) {
                if (nv.getTaiKhoan().equals(tk) && nv.getMatKhau().equals(mk)) {
                    if (nv.isQuanLy())
                        test = "Đây là quản lý nha";
                    else
                        test = "Đây là nhân viên nha";
                    
                    break;
                }
            }
            
            txtKiemTraXiuThoi.setText(test);
        } catch (SQLException ex) {
            Logger.getLogger(PrimaryController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
