module com.tuantran.fxsaleapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens com.tuantran.fxsaleapp to javafx.fxml;
    exports com.tuantran.fxsaleapp;
}
