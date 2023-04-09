/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tuantran.fxsaleapp;

import static com.tuantran.fxsaleapp.FormQuanLyBanHangController.khuyenMaiService;
import com.tuantran.pojo.ThanhVien;
import com.tuantran.services.ThanhVienService;
import com.tuantran.utils.MessageBox;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 *
 * @author TuanTran
 */
public class QuanLyThanhVienController implements Initializable {

    ThanhVienService thanhVienService = new ThanhVienService();
    private final FormUtils FORM_UTILS = new FormUtils();

    @FXML
    private TableView<ThanhVien> tbThanhVien;

    @FXML
    private TextField txtThanhVien_id;

    @FXML
    private TextField txtThanhVien_hoThanhVien;

    @FXML
    private TextField txtThanhVien_tenThanhVien;

    @FXML
    private TextField txtThanhVien_soDienThoai;

    @FXML
    private DatePicker dpThanhVien_ngaySinh;

    @FXML
    private Button btnThoat;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        FORM_UTILS.onlyNumbers(this.txtThanhVien_soDienThoai);
        FORM_UTILS.formatDate(FORM_UTILS.MY_DATE_FORMAT, dpThanhVien_ngaySinh);
        this.loadALL();
    }

    private void loadALL() {
        try {
            this.txtThanhVien_id.setText("");
            this.txtThanhVien_hoThanhVien.setText("");
            this.txtThanhVien_tenThanhVien.setText("");
            this.txtThanhVien_soDienThoai.setText("");
            this.dpThanhVien_ngaySinh.setValue(null);
            this.loadTableColumnThanhVien(this.tbThanhVien);
            this.loadTableDataThanhVien(this.tbThanhVien, null);

            this.addTextChangeThanhVien(txtThanhVien_hoThanhVien, txtThanhVien_tenThanhVien, txtThanhVien_soDienThoai, tbThanhVien);

        } catch (SQLException ex) {
            Logger.getLogger(FormQuanLyBanHangController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    public void exit(ActionEvent evt) {
        Stage oldStage = (Stage) this.btnThoat.getScene().getWindow();
        oldStage.close();
    }

    private void loadTableColumnThanhVien(TableView tableView) {
        TableColumn colIdThanhVien = new TableColumn("Mã thành viên");
        TableColumn colHoThanhVien = new TableColumn("Họ thành viên");
        TableColumn colTenThanhVien = new TableColumn("Tên thành viên");
        TableColumn colNgaySinhThanhVien = new TableColumn("Ngày sinh");
        TableColumn colSoDienThoai = new TableColumn("Số điện thoại");
//        pojo
        colIdThanhVien.setCellValueFactory(new PropertyValueFactory("idThanhVien"));
        colHoThanhVien.setCellValueFactory(new PropertyValueFactory("hoThanhVien"));
        colTenThanhVien.setCellValueFactory(new PropertyValueFactory("tenThanhVien"));
        colNgaySinhThanhVien.setCellValueFactory(new PropertyValueFactory("ngaySinhThanhVien"));
        colSoDienThoai.setCellValueFactory(new PropertyValueFactory("soDienThoai"));

        tableView.getColumns().clear();
        tableView.getColumns().addAll(colIdThanhVien, colHoThanhVien, colTenThanhVien, colNgaySinhThanhVien, colSoDienThoai);
    }

    private void loadTableDataThanhVien(TableView tableView, String keyword) throws SQLException {
        List<ThanhVien> thanhViens = thanhVienService.getThanhViens(keyword, keyword, keyword, keyword);

        tableView.getItems().clear();
        tableView.setItems(FXCollections.observableList(thanhViens));
    }

    private void loadTableDataThanhVien(TableView tableView, String keyword_id, String keyword_hoThanhVien, String keyword_tenThanhVien, String keyword_soDienThoai) throws SQLException {
        List<ThanhVien> thanhViens = thanhVienService.getThanhViens(keyword_id, keyword_hoThanhVien, keyword_tenThanhVien, keyword_soDienThoai);

        tableView.getItems().clear();
        tableView.setItems(FXCollections.observableList(thanhViens));
    }

    @FXML
    public void addThanhVien(ActionEvent evt) throws SQLException {
        if (!this.txtThanhVien_hoThanhVien.getText().isEmpty() && !this.txtThanhVien_tenThanhVien.getText().isEmpty()
                && !this.txtThanhVien_soDienThoai.getText().isEmpty() && this.dpThanhVien_ngaySinh.getValue() != null) {

            List<ThanhVien> thanhViens = thanhVienService.getThanhViens(null, null, null, null);
            int idThanhVien = thanhViens.get(thanhViens.size() - 1).getIdThanhVien() + 1;

            String hoThanhVien = this.txtThanhVien_hoThanhVien.getText();
            String tenThanhVien = this.txtThanhVien_tenThanhVien.getText();
            String soDienThoai = this.txtThanhVien_soDienThoai.getText();
            LocalDate ngaySinh_LocalDate = this.dpThanhVien_ngaySinh.getValue();
            Date ngaySinh_Date = Date.valueOf(ngaySinh_LocalDate);

            int count = 0;
            for (ThanhVien thanhVien : thanhViens) {
                if (thanhVien.getSoDienThoai().equals(soDienThoai)) {
                    break;
                } else {
                    count++;
                }
            }

            if (count == thanhViens.size()) {
                ThanhVien thanhVien = new ThanhVien(idThanhVien, hoThanhVien, tenThanhVien, ngaySinh_Date, soDienThoai);

                try {
                    thanhVienService.addThanhVien(thanhVien);
                    this.loadTableDataThanhVien(this.tbThanhVien, null);
                    MessageBox.getBox("Question", "Thêm thành viên thành công", Alert.AlertType.INFORMATION).show();
                    this.loadALL();
                } catch (SQLException ex) {
                    MessageBox.getBox("Question", "Thêm thành viên thất bại", Alert.AlertType.INFORMATION).show();
                    Logger.getLogger(FormNhanVienBanHangController.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                MessageBox.getBox("Thông báo", "Số điện thoại này đã tồn tại!", Alert.AlertType.CONFIRMATION).show();
            }

        } else {
            MessageBox.getBox("Question", "Vui lòng nhập đầy đủ thông tin", Alert.AlertType.INFORMATION).show();
        }
    }

    @FXML
    public void deleteThanhVien(ActionEvent evt) {
        Object selectedObject = this.tbThanhVien.getSelectionModel().getSelectedItem();

        if (selectedObject != null) {
            Alert a = MessageBox.getBox("Question", "Bạn có chắc chắn muốn xóa không?", Alert.AlertType.CONFIRMATION);
            a.showAndWait().ifPresent(res -> {
                if (res == ButtonType.OK) {
                    ThanhVien thanhVien = (ThanhVien) selectedObject;
                    int idThanhVien = thanhVien.getIdThanhVien();

                    if (idThanhVien == 0) {
                        MessageBox.getBox("Thông báo", "Bạn không được sửa giá trị này!", Alert.AlertType.INFORMATION).show();
                    } else {
                        try {
                            thanhVienService.deleteThanhVien(Integer.toString(idThanhVien));
                            this.loadTableDataThanhVien(this.tbThanhVien, null);
                            MessageBox.getBox("Thông báo", "Xóa thành viên thành công", Alert.AlertType.INFORMATION).show();
                        } catch (SQLException ex) {
                            MessageBox.getBox("Thông báo", "Xóa thành viên thất bại", Alert.AlertType.INFORMATION).show();
                            Logger.getLogger(FormQuanLyBanHangController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            });
        } else {
            MessageBox.getBox("Question", "Hãy chọn thành viên cần xóa!", Alert.AlertType.INFORMATION).show();
        }
    }

    @FXML
    public void editThanhVien(ActionEvent evt) {
        Object selectedObject = this.tbThanhVien.getSelectionModel().getSelectedItem();
        if (selectedObject != null) {
            ThanhVien thanhVien = (ThanhVien) selectedObject;
            if (thanhVien.getIdThanhVien() == 0) {
                MessageBox.getBox("Question", "Bạn không được xóa giá trị này!", Alert.AlertType.INFORMATION).show();
            } else {

                this.txtThanhVien_id.setText(Integer.toString(thanhVien.getIdThanhVien()));
                this.txtThanhVien_hoThanhVien.setText(thanhVien.getHoThanhVien());
                this.txtThanhVien_tenThanhVien.setText(thanhVien.getTenThanhVien());
                this.txtThanhVien_soDienThoai.setText(thanhVien.getSoDienThoai());
                this.dpThanhVien_ngaySinh.setValue(thanhVien.getNgaySinhThanhVien().toLocalDate());
            }
        } else {
            MessageBox.getBox("Question", "Hãy chọn thành viên cần sửa!", Alert.AlertType.INFORMATION).show();
        }
    }

    @FXML
    public void updateThanhVien(ActionEvent evt) {
        if (!this.txtThanhVien_id.getText().isEmpty()) {
            Alert a = MessageBox.getBox("Question", "Bạn có chắc chắn muốn sửa không?", Alert.AlertType.CONFIRMATION);
            a.showAndWait().ifPresent(res -> {
                if (res == ButtonType.OK) {

                    if (!this.txtThanhVien_hoThanhVien.getText().isEmpty() && !this.txtThanhVien_tenThanhVien.getText().isEmpty()
                            && !this.txtThanhVien_soDienThoai.getText().isEmpty() && this.dpThanhVien_ngaySinh.getValue() != null) {

                        String idThanhVien = this.txtThanhVien_id.getText();
                        String hoThanhVien = this.txtThanhVien_hoThanhVien.getText();
                        String tenThanhVien = this.txtThanhVien_tenThanhVien.getText();
                        String soDienThoai = this.txtThanhVien_soDienThoai.getText();

                        LocalDate ngaySinh_LocalDate = this.dpThanhVien_ngaySinh.getValue();
                        Date ngaySinh_Date = Date.valueOf(ngaySinh_LocalDate);

                        List<ThanhVien> thanhViens;
                        try {
                            thanhViens = thanhVienService.getThanhViens(null, null, null, null);
                            int count = 0;
                            for (ThanhVien thanhVien : thanhViens) {
                                if (thanhVien.getSoDienThoai().equals(soDienThoai) && !Integer.toString(thanhVien.getIdThanhVien()).equals(idThanhVien)) {
                                    break;
                                } else {
                                    count++;
                                }
                            }

                            if (count == thanhViens.size()) {
                                try {
                                    thanhVienService.updateThanhVien(idThanhVien, hoThanhVien, tenThanhVien, ngaySinh_Date, soDienThoai);
//                                thanhVienService.updateNhanVien(idThanhVien, hoThanhVien, tenThanhVien, ngaySinh_Date, soDienThoai);
                                    this.loadTableDataThanhVien(this.tbThanhVien, null);
                                    MessageBox.getBox("Thông báo", "Cập nhật thành viên thành công!", Alert.AlertType.INFORMATION).show();
                                    this.loadALL();
                                } catch (SQLException ex) {
                                    MessageBox.getBox("Thông báo", "Cập nhật thành viên thất bại!", Alert.AlertType.INFORMATION).show();
                                    Logger.getLogger(QuanLyThanhVienController.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            } else {
                                MessageBox.getBox("Thông báo", "Số điện thoại này đã tồn tại!", Alert.AlertType.CONFIRMATION).show();
                            }

                        } catch (SQLException ex) {
                            Logger.getLogger(QuanLyThanhVienController.class.getName()).log(Level.SEVERE, null, ex);
                        }

                    } else {
                        MessageBox.getBox("Question", "Vui lòng nhập đầy đủ thông tin", Alert.AlertType.INFORMATION).show();
                    }
                }
            });

        } else {
            MessageBox.getBox("Question", "Hãy chọn thành viên cần cập nhật", Alert.AlertType.INFORMATION).show();
        }
    }

    private void addTextChangeThanhVien(TextField keyword_hoThanhVien, TextField keyword_tenThanhVien, TextField keyword_soDienThoai, TableView tableView) {
        if (keyword_hoThanhVien != null) {
            keyword_hoThanhVien.textProperty().addListener(e -> {
                try {
                    this.loadTableDataThanhVien(tableView, null, keyword_hoThanhVien.getText(), null, null);
                } catch (SQLException ex) {
                    Logger.getLogger(FormNhanVienBanHangController.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
        }

        if (keyword_tenThanhVien != null) {
            keyword_tenThanhVien.textProperty().addListener(e -> {
                try {
                    this.loadTableDataThanhVien(tableView, null, null, keyword_tenThanhVien.getText(), null);
                } catch (SQLException ex) {
                    Logger.getLogger(FormNhanVienBanHangController.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
        }

        if (keyword_soDienThoai != null) {
            keyword_soDienThoai.textProperty().addListener(e -> {
                try {
                    this.loadTableDataThanhVien(tableView, null, null, null, keyword_soDienThoai.getText());
                } catch (SQLException ex) {
                    Logger.getLogger(FormNhanVienBanHangController.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
        }
    }
}
