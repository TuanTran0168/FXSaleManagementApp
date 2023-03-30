package com.tuantran.fxsaleapp;

import com.tuantran.pojo.*;
import com.tuantran.pojo.SanPham;
import com.tuantran.services.ChiNhanhService;
import com.tuantran.services.ChiTietHoaDonService;
import com.tuantran.services.HoaDonService;
import com.tuantran.services.SanPhamService;
import com.tuantran.utils.MessageBox;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
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
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.converter.DoubleStringConverter;

public class FormNhanVienBanHangController implements Initializable {

    static SanPhamService sanPhamService = new SanPhamService();
    static HoaDonService hoaDonService = new HoaDonService();
    static ChiNhanhService chiNhanhService = new ChiNhanhService();
    static ChiTietHoaDonService chiTietHoaDonService = new ChiTietHoaDonService();

    List<SanPham> sanPhamDuocChon;
    List<String> listSoLuongSanPhamDuocChon;

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
    @FXML
    TextField txtDiaChi;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            
            this.tbSanPhamDuocChon.setEditable(true);
            sanPhamDuocChon = new ArrayList<>();
            listSoLuongSanPhamDuocChon = new ArrayList<>();

            spService = sanPhamService.getSanPham();
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

//    TableColumn colSoLuong = new TableColumn("Số lượng");
    private void loadTableColumnsSanPhamDuocChon(TableView tableView) {
        TableColumn colIdSanPham = new TableColumn("Mã sản phẩm");
        TableColumn colTenSanPham = new TableColumn("Tên sản phẩm");
        TableColumn colGia = new TableColumn("Giá sản phẩm");
        TableColumn colDonVi = new TableColumn("Đơn vị");
        TableColumn colIdKhuyenMai = new TableColumn("Khuyến Mãi");
        TableColumn<SanPham, Double> colSoLuong = new TableColumn<>("Số lượng");

//        pojo
        colIdSanPham.setCellValueFactory(new PropertyValueFactory("idSanPham"));
        colTenSanPham.setCellValueFactory(new PropertyValueFactory("tenSanPham"));
        colGia.setCellValueFactory(new PropertyValueFactory("gia"));
        colDonVi.setCellValueFactory(new PropertyValueFactory("donVi"));
        colIdKhuyenMai.setCellValueFactory(new PropertyValueFactory("idKhuyenMai"));
        colSoLuong.setCellValueFactory(new PropertyValueFactory("soLuongTemp"));
        colSoLuong.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        
        
        colSoLuong.setOnEditCommit((TableColumn.CellEditEvent<SanPham, Double> event) -> {
            SanPham sp = event.getTableView().getItems().get(event.getTablePosition().getRow());
            
            sp.setSoLuongTemp(event.getNewValue());
        });
        
        colSoLuong.setEditable(true);

//        String title = "Thay đổi số lượng của sản phẩm";
//        String headerText = "Số lượng";
//        String contentText = "Mời bạn nhập số lượng: ";
//        this.chinhSuaColumnTrongTableView(tableView, colSoLuong, title, headerText, contentText);

        TableColumn colLuaChon = new TableColumn("Xóa");

        tableView.getColumns().clear();
        tableView.getColumns().addAll(colIdSanPham, colTenSanPham, colDonVi, colGia, colIdKhuyenMai, colSoLuong);

        this.themButtonVaoTableColumnSanPhamDuocChon(tableView, colLuaChon, "Xóa");
//        String nameTextField = "1";
//        this.themTextFieldVaoTableColumnSanPhamDuocChon(tableView, colSoLuong, nameTextField);
    }

    private void chinhSuaColumnTrongTableView(TableView tableView, TableColumn tableColumn, String title, String headerText, String contentText) {
        tableColumn.setCellFactory(column -> {
            return new TableCell<SanPham, String>() {
                @Override
                public void startEdit() {
                    super.startEdit();
                    
                    TextInputDialog dialog = new TextInputDialog(getItem());
                    dialog.setTitle(title);
                    dialog.setHeaderText(headerText);
                    dialog.setContentText(contentText);

                    Optional<String> result = dialog.showAndWait();

                    if (result.isPresent()) {
                        commitEdit(result.get());
                        listSoLuongSanPhamDuocChon.add(result.get());
                    } else {
                        cancelEdit();
                    }
                }

                @Override
                public void cancelEdit() {
                    super.cancelEdit();
                }

                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);

                    if (empty) {
                        setText(null);
                    } else {
                        setText(item);
                    }
                }
            };
        });

        tableView.setEditable(true);
    }

    @FXML
    public void luuSoLuongSanPham(ActionEvent evt) throws SQLException {
        
//        SanPham sp = (SanPham) this.tbSanPhamDuocChon.getItems().get(0);
        this.listSoLuongSanPhamDuocChon = new ArrayList<String>();
        for (SanPham s : this.tbSanPhamDuocChon.getItems()) {
            
            this.listSoLuongSanPhamDuocChon.add(Double.toString(s.getSoLuongTemp()));
        }
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

    private void themTextFieldVaoTableColumnSanPhamDuocChon(TableView tableView, TableColumn tableColumn, String nameTextField) {
        tableColumn.setCellFactory(r -> {
            TextField txt = new TextField(nameTextField);

            TableCell c = new TableCell();
            c.setGraphic(txt);
            return c;
        });

        tableView.getColumns().add(tableColumn);
    }

    private void themTextFieldVaoTableCellSanPhamDuocChon(TableView tableView, TableCell tableCell, String nameTextField) {
        tableCell.setGraphic(new TextField(nameTextField));
        tableView.getColumns().add(tableCell);
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
                                Alert c = MessageBox.getBox("Question", "Đã tồn tại sản phẩm", Alert.AlertType.CONFIRMATION);
                                c.show();
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
        this.luuSoLuongSanPham(evt);
        Date ngayCT = new Date(40, 10, 10);

        int idNhanVien = nhanVienDiemDanh.getIdNhanVien();
        int idChiNhanh = nhanVienDiemDanh.getIdChiNhanh();

//         Lấy Số lượng Hóa đơn hiện tại
        int soLuongHoaDon = hoaDonService.getHoaDon(null).size();
//         Lấy số lượng chi tiết hóa đơn hiện tại
        int soLuongChiTietHoaDon = chiTietHoaDonService.getChiTietHoaDon(null).size();

//        Tạo hóa đơn
        HoaDon hoaDon = new HoaDon(soLuongHoaDon + 1, idNhanVien, idChiNhanh, 1, 0, 0, ngayCT);

        List<ChiTietHoaDon> chiTietHoaDons = new ArrayList<>();

        double tongTien = 0;
        double soTienNhan = 500000;
        int countSL = 0;
        for (SanPham sp : sanPhamDuocChon) {
            double soLuong = Double.parseDouble(listSoLuongSanPhamDuocChon.get(countSL));
            countSL++;
            soLuongChiTietHoaDon++;
            ChiTietHoaDon cthd = new ChiTietHoaDon(soLuongChiTietHoaDon, sp.getIdSanPham(), hoaDon.getIdHoaDon(), soLuong, sp.getGia());
            chiTietHoaDons.add(cthd);
            tongTien += sp.getGia() * soLuong;
        }

        hoaDon.setTongTien(tongTien);
        hoaDon.setSoTienNhan(soTienNhan);

//        hoaDonService.addHoaDon(hoaDon, chiTietHoaDons);
        try {
            hoaDonService.addHoaDon(hoaDon, chiTietHoaDons);
            MessageBox.getBox("Question", "Thêm hóa đơn thành công", Alert.AlertType.INFORMATION).show();
        } catch (SQLException ex) {
            MessageBox.getBox("Question", "Thêm hóa đơn thất bại", Alert.AlertType.INFORMATION).show();
            Logger.getLogger(FormNhanVienBanHangController.class.getName()).log(Level.SEVERE, null, ex);
        }
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

        comboBox.setItems(FXCollections.observableList(listSoLuongSanPhamDuocChon));
    }

    private void addTextChange(TextField textField, TableView tableView) {
        textField.textProperty().addListener(e -> {
            try {
                this.loadTableDataSanPham(textField.getText(), tableView);
//                làm sao xóa được cái nút đây :(((
//                this.loadTableColumnsSanPham(tableView);
            } catch (SQLException ex) {
                Logger.getLogger(FormNhanVienBanHangController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }
}
