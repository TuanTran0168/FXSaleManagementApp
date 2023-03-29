package com.tuantran.fxsaleapp;

import com.tuantran.pojo.*;
import com.tuantran.pojo.SanPham;
import com.tuantran.services.ChiNhanhService;
import com.tuantran.services.HoaDonService;
import com.tuantran.services.SanPhamService;
import com.tuantran.utils.MessageBox;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.Window;

public class FormNhanVienBanHangController implements Initializable {

    static SanPhamService sanPhamService = new SanPhamService();
    static HoaDonService hoaDonService = new HoaDonService();
    static ChiNhanhService chiNhanhService = new ChiNhanhService();
    
    List<SanPham> sanPhamDuocChon;
    
    List<SanPham> spService;
    int count;
    NhanVien nhanVienDiemDanh;

    @FXML
    TableView<SanPham> tbSanPhams;
    @FXML
    TableView<SanPham> tbSanPhamDuocChon;
    @FXML
    ComboBox<SanPham> cbSanPhams;
    @FXML
    TextField txtSearch;

    @FXML
    Button btnGetObject;

    @FXML
    TextField txtTestTen;
    @FXML
    TextField txtTestHo;
    @FXML TextField txtDiaChi;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
//        this.loadTableColumnsSanPham();
        try {
//            Alert a = MessageBox.getBox("CC", nv.getTenNhanVien(), Alert.AlertType.CONFIRMATION);
//            a.show();
//            ActionEvent a = btnGetObject.fire();
//  
//            handleButtonAction();
            

            spService = sanPhamService.getSanPham();
            sanPhamDuocChon = new ArrayList<>();
            this.loadTableDataSanPham(null, this.tbSanPhams);
            this.loadTableColumnsSanPham(this.tbSanPhams);
            this.loadComboBox(this.cbSanPhams);

            this.addTextChange(this.txtSearch, this.tbSanPhams);
//            ==========================================================================
            this.loadTableColumnsSanPhamDuocChon(this.tbSanPhamDuocChon);

        } catch (SQLException ex) {
            Logger.getLogger(FormNhanVienBanHangController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    public void handleButtonAction(ActionEvent event) {
        Scene currentScene = ((Node) event.getSource()).getScene();
        Stage currentStage = (Stage) currentScene.getWindow();
        NhanVien nv = (NhanVien) currentStage.getUserData();
        Alert a = MessageBox.getBox("CC", nv.getTenNhanVien(), Alert.AlertType.CONFIRMATION);
        a.show();
    }

    @FXML
    public void xuLyDiemDanh(ActionEvent event) throws SQLException {
        Scene currentScene = ((Node) event.getSource()).getScene();
        Stage currentStage = (Stage) currentScene.getWindow();
        nhanVienDiemDanh = (NhanVien) currentStage.getUserData();
        this.txtTestHo.setText(nhanVienDiemDanh.getHoNhanVien());
        this.txtTestTen.setText(nhanVienDiemDanh.getTenNhanVien());
       
        List<ChiNhanh> cn = chiNhanhService.getChiNhanhs(String.format("", nhanVienDiemDanh.getIdChiNhanh()));
        this.txtDiaChi.setText(cn.get(0).getDiaChi());
        Alert a = MessageBox.getBox("CC", nhanVienDiemDanh.getTenNhanVien(), Alert.AlertType.CONFIRMATION);
        a.show();
    }

    public void showThongTin(Button button) {
        Scene currentScene = Window.getWindows().stream().filter(Window::isShowing).findFirst().orElse(null).getScene();

//        Scene currentScene = ((Node) event.getSource()).getScene();
        Stage currentStage = (Stage) currentScene.getWindow();
        NhanVien nv = (NhanVien) currentStage.getUserData();
        Alert a = MessageBox.getBox("CC", nv.getTenNhanVien(), Alert.AlertType.CONFIRMATION);
        a.show();
    }

    public void showThongTin_1() {
//        Scene currentScene = ((Node) btnGetObject.getScene());

        Scene currentScene = ((Node) this.btnGetObject).getScene();
        Stage currentStage = (Stage) currentScene.getWindow();
        NhanVien nv = (NhanVien) currentStage.getUserData();
        Alert a = MessageBox.getBox("CC", nv.getTenNhanVien(), Alert.AlertType.CONFIRMATION);
        a.show();
    }

    private void loadTableColumnsSanPham(TableView tableView) {
        TableColumn colIdSanPham = new TableColumn("Mã sản phẩm");
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

        TableColumn colLuaChon = new TableColumn("Lựa chọn");
//        this.themButtonVaoTableColumnSanPham(colLuaChon, "Chọn nè");

        tableView.getColumns().clear();
        tableView.getColumns().addAll(colIdSanPham, colTenSanPham, colDonVi, colGia, colIdKhuyenMai);

        this.themButtonVaoTableColumnSanPham(tableView, colLuaChon, "Chọn nha nha");
    }

    private void loadTableColumnsSanPhamDuocChon(TableView tableView) {
        TableColumn colIdSanPham = new TableColumn("Mã sản phẩm");
        TableColumn colTenSanPham = new TableColumn("Tên sản phẩm");
        TableColumn colGia = new TableColumn("Giá sản phẩm");
        TableColumn colDonVi = new TableColumn("Đơn vị");
        TableColumn colIdKhuyenMai = new TableColumn("Khuyến Mãi");
        TableColumn colSoLuong = new TableColumn("Số lượng");

//        pojo
        colIdSanPham.setCellValueFactory(new PropertyValueFactory("idSanPham"));
        colTenSanPham.setCellValueFactory(new PropertyValueFactory("tenSanPham"));
        colGia.setCellValueFactory(new PropertyValueFactory("gia"));
        colDonVi.setCellValueFactory(new PropertyValueFactory("donVi"));
        colIdKhuyenMai.setCellValueFactory(new PropertyValueFactory("idKhuyenMai"));
        colSoLuong.setCellValueFactory(new PropertyValueFactory("so_luong"));
//        colSoLuong.setEditable(true);

        TableColumn colLuaChon = new TableColumn("Xóa");
//        this.themButtonVaoTableColumnSanPham(colLuaChon, "Chọn nè");

        tableView.getColumns().clear();
        tableView.getColumns().addAll(colIdSanPham, colTenSanPham, colDonVi, colGia, colIdKhuyenMai, colSoLuong);

        this.themButtonVaoTableColumnSanPhamDuocChon(tableView, colLuaChon, "Xóa");
    }

    private void loadTableDataSanPham(String keyword, TableView tableView) throws SQLException {
        List<SanPham> sanPhams = sanPhamService.getSanPham(keyword);

        tableView.getItems().clear();
        tableView.setItems(FXCollections.observableList(sanPhams));
    }

    private void themButtonVaoTableColumn(TableColumn tableColumn, String nameButton) {
        tableColumn.setCellFactory(r -> {
            Button btn = new Button(nameButton);

            this.xuLyButtonThemSanPham(btn);

            TableCell c = new TableCell();
            c.setGraphic(btn);
            return c;
        });
    }

    private void themButtonVaoTableColumnSanPham(TableView tableView, TableColumn tableColumn, String nameButton) {
        tableColumn.setCellFactory(r -> {
            Button btn = new Button(nameButton);

            this.xuLyButtonThemSanPham(btn);

            TableCell c = new TableCell();
            c.setGraphic(btn);
            return c;

        });

        tableView.getColumns().add(tableColumn);
    }

    private void themButtonVaoTableColumnSanPhamDuocChon(TableView tableView, TableColumn tableColumn, String nameButton) {
        tableColumn.setCellFactory(r -> {
            Button btn = new Button(nameButton);

            this.xuLyButtonXoaSanPham(btn);

            TableCell c = new TableCell();
            c.setGraphic(btn);
            return c;

        });

        tableView.getColumns().add(tableColumn);
    }

    private void xuLyButtonThemSanPham(Button button) {
        button.setOnAction(evt -> {
            Alert a = MessageBox.getBox("Question", "Bạn có chắc chắn muốn chọn?", Alert.AlertType.CONFIRMATION);
            a.showAndWait().ifPresent(res -> {
                if (res == ButtonType.OK) {
                    Button btnXuLy = (Button) evt.getSource();
                    TableCell cell = (TableCell) btnXuLy.getParent();
                    SanPham s = (SanPham) cell.getTableRow().getItem();

//                    THUẬT TOÁN THÊM ĐẲNG CẤP :) RESET BIẾN ĐẾM ĐÚNG CHỖ :) TỐN 4 TIẾNG RƯỠI CỦA BỐ :)
                    if (sanPhamDuocChon.isEmpty()) {
                        sanPhamDuocChon.add(s);
//                        btnXuLy.setDisable(true);
                        count = 0;
                        Alert b = MessageBox.getBox("Question", "count = " + count, Alert.AlertType.CONFIRMATION);
                        b.show();
                    } else {
                        for (SanPham sp : sanPhamDuocChon) {
                            if (s.getIdSanPham() == sp.getIdSanPham()) {
                                Alert b = MessageBox.getBox("Question", "count = " + count, Alert.AlertType.CONFIRMATION);
                                b.show();
                                count = 0;
                                break;
                            } else {
                                count++;
                                Alert b = MessageBox.getBox("Question", "count = " + count, Alert.AlertType.CONFIRMATION);
                                b.show();
                            }
                        }
                        if (count == sanPhamDuocChon.size()) {
                            sanPhamDuocChon.add(s);
//                            btnXuLy.setDisable(true);
                            count = 0;
                            Alert b = MessageBox.getBox("Question", "count = " + count, Alert.AlertType.CONFIRMATION);
                            b.show();
                        }
                    }
                }
            });
            
            this.tbSanPhamDuocChon.setItems(FXCollections.observableList(sanPhamDuocChon));
        });
    }
    
    @FXML
    public void xuLyThemHoaDon(ActionEvent evt) throws SQLException {
        Date ngayCT = new Date(40,10,10);
        
        int idNhanVien = nhanVienDiemDanh.getIdNhanVien();
        int idChiNhanh = nhanVienDiemDanh.getIdChiNhanh();
        HoaDon hoaDon = new HoaDon(idNhanVien, idChiNhanh, 1, 0, 0, ngayCT);
        
        List<ChiTietHoaDon> chiTietHoaDons = new ArrayList<>();
        
        double tongTien = 0;
        double soTienNhan = 500000;
        for (SanPham sp : sanPhamDuocChon) {
            int soLuong = 2;
            ChiTietHoaDon cthd = new ChiTietHoaDon(sp.getIdSanPham(), hoaDon.getIdHoaDon(), soLuong, sp.getGia());
            chiTietHoaDons.add(cthd);
            tongTien += sp.getGia() * soLuong;
        }
        
        hoaDon.setTongTien(tongTien);
        hoaDon.setSoTienNhan(soTienNhan);
        hoaDonService.addHoaDon(hoaDon, chiTietHoaDons);
//        try {
//            hoaDonService.addHoaDon(hoaDon, chiTietHoaDons);
//            MessageBox.getBox("Question", "Thêm hóa đơn thành công", Alert.AlertType.INFORMATION).show();
//        } catch (SQLException ex) {
//            MessageBox.getBox("Question", "Thêm hóa đơn thất bại", Alert.AlertType.INFORMATION).show();
//            Logger.getLogger(FormNhanVienBanHangController.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }

    private void xuLyButtonXoaSanPham(Button button) {
        button.setOnAction(evt -> {
            Alert a = MessageBox.getBox("Question", "Bạn có chắc chắn muốn xóa?", Alert.AlertType.CONFIRMATION);
            a.showAndWait().ifPresent(res -> {
                if (res == ButtonType.OK) {
                    Button btnXuLy = (Button) evt.getSource();
                    TableCell cell = (TableCell) btnXuLy.getParent();
                    SanPham s = (SanPham) cell.getTableRow().getItem();
                    sanPhamDuocChon.remove(s);
                }
            });
            this.tbSanPhamDuocChon.setItems(FXCollections.observableList(sanPhamDuocChon));
        });

    }

    private void loadComboBox(ComboBox comboBox) throws SQLException {
//        List<SanPham> sp = sanPhamService.getSanPham();

        comboBox.setItems(FXCollections.observableList(spService));
    }

    private void addTextChange(TextField textFeild, TableView tableView) {
        textFeild.textProperty().addListener(e -> {
            try {
                this.loadTableDataSanPham(textFeild.getText(), tableView);
//                làm sao xóa được cái nút đây :(((
//                this.loadTableColumnsSanPham(tableView);
            } catch (SQLException ex) {
                Logger.getLogger(FormNhanVienBanHangController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }
}
