package com.tuantran.fxsaleapp;

import com.tuantran.pojo.NhanVien;
import com.tuantran.pojo.SanPham;
import com.tuantran.services.SanPhamService;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;


public class FormNhanVienBanHangController implements Initializable{
    static SanPhamService sanPhamService = new SanPhamService();
    
    @FXML TableView<NhanVien> tbNhanViens;
    @FXML TableView<SanPham> tbSanPhams;
    @FXML ComboBox<SanPham> cbSanPhams;
    
     @Override
    public void initialize(URL url, ResourceBundle rb) {
//        this.loadTableColumns();
        try {
            this.loadTableData();
            this.loadTableColumns();
            
            
            List<SanPham> sp = sanPhamService.getSanPham();
            
            this.cbSanPhams.setItems(FXCollections.observableList(sp));
            
        } catch (SQLException ex) {
            Logger.getLogger(FormNhanVienBanHangController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void loadTableColumns() {
        TableColumn colIdSanPham = new TableColumn("id");
        TableColumn colTenSanPham = new TableColumn("Tên sản phẩm");
        TableColumn colGia = new TableColumn("Giá sản phẩm");
        TableColumn colDonVi = new TableColumn("Đơn vị");
        TableColumn colIdKhuyenMai = new TableColumn("Khuyến Mãi");
        
//        pojo
        colIdSanPham.setCellValueFactory(new PropertyValueFactory("idSanPham"));
        colTenSanPham.setCellValueFactory(new PropertyValueFactory("tenSanPham"));
        colGia.setCellValueFactory(new PropertyValueFactory("gia"));
        colDonVi.setCellValueFactory(new PropertyValueFactory("donVi"));
        colIdKhuyenMai.setCellValueFactory(new PropertyValueFactory("idKhuyenMai"));
        
//        colIdSanPham.setCellValueFactory(new PropertyValueFactory("id_san_pham"));
//        colTenSanPham.setCellValueFactory(new PropertyValueFactory("ten_san_pham"));
//        colGia.setCellValueFactory(new PropertyValueFactory("gia"));
//        colDonVi.setCellValueFactory(new PropertyValueFactory("don_vi"));
//        colIdKhuyenMai.setCellValueFactory(new PropertyValueFactory("id_khuyen_mai"));
        
        this.tbSanPhams.getColumns().addAll(colIdSanPham, colTenSanPham, colDonVi, colGia, colIdKhuyenMai);
//        this.tbSanPhams.getColumns().addAll(colIdSanPham);
    }

    private void loadTableData() throws SQLException {
        List<SanPham> sanPhams = sanPhamService.getSanPham();
        
        this.tbSanPhams.getItems().clear();
        this.tbSanPhams.setItems(FXCollections.observableList(sanPhams));
    }
}