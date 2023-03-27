package com.tuantran.fxsaleapp;

import com.tuantran.pojo.NhanVien;
import com.tuantran.pojo.SanPham;
import com.tuantran.services.SanPhamService;
import com.tuantran.utils.MessageBox;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class FormNhanVienBanHangController implements Initializable {

    static SanPhamService sanPhamService = new SanPhamService();
    List<SanPham> sanPhamDuocChon;
    List<SanPham> spService;
    int count;

    @FXML
    TableView<SanPham> tbSanPhams;
    @FXML
    TableView<SanPham> tbSanPhamDuocChon;
    @FXML
    ComboBox<SanPham> cbSanPhams;
    @FXML
    TextField txtSearch;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
//        this.loadTableColumnsSanPham();
        try {
            spService = sanPhamService.getSanPham();
            sanPhamDuocChon = new ArrayList<>();
            this.loadTableData(null, this.tbSanPhams);
            this.loadTableColumnsSanPham(this.tbSanPhams);
            this.loadComboBox(this.cbSanPhams);

            this.addTextChange(this.txtSearch, this.tbSanPhams);
//            ==========================================================================
            this.loadTableColumnsSanPhamDuocChon(this.tbSanPhamDuocChon);

//            this.loadTableData(null, tbSanPhamDuocChon);
        } catch (SQLException ex) {
            Logger.getLogger(FormNhanVienBanHangController.class.getName()).log(Level.SEVERE, null, ex);
        }
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

        TableColumn colLuaChon = new TableColumn("Xóa");
//        this.themButtonVaoTableColumnSanPham(colLuaChon, "Chọn nè");

        tableView.getColumns().clear();
        tableView.getColumns().addAll(colIdSanPham, colTenSanPham, colDonVi, colGia, colIdKhuyenMai, colSoLuong);

        this.themButtonVaoTableColumnSanPhamDuocChon(tableView, colLuaChon, "Xóa");
    }

    private void loadTableData(String keyword, TableView tableView) throws SQLException {
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
                        count = 0;
                        Alert b = MessageBox.getBox("Question", "count = " + count , Alert.AlertType.CONFIRMATION);
                        b.show();
                    } else {
                        for (SanPham sp : sanPhamDuocChon) {
                            if (s.getIdSanPham() == sp.getIdSanPham()) {
                                Alert b = MessageBox.getBox("Question", "count = " + count , Alert.AlertType.CONFIRMATION);
                                b.show();
                                count = 0;
                                break;
                            }
                            else {
                                count++;
                                Alert b = MessageBox.getBox("Question", "count = " + count , Alert.AlertType.CONFIRMATION);
                                b.show();
                            }
                        }
                        if (count == sanPhamDuocChon.size()) {
                            sanPhamDuocChon.add(s);
                            count = 0;
                            Alert b = MessageBox.getBox("Question", "count = " + count , Alert.AlertType.CONFIRMATION);
                            b.show();
                        }    
                    }
                }
            });
            this.tbSanPhamDuocChon.setItems(FXCollections.observableList(sanPhamDuocChon));
        });

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
                this.loadTableData(textFeild.getText(), tableView);
//                làm sao xóa được cái nút đây :(((
//                this.loadTableColumnsSanPham(tableView);
            } catch (SQLException ex) {
                Logger.getLogger(FormNhanVienBanHangController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }
}
