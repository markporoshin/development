package dev_app.controller.workers;

import general.SQLQuery;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.sql.SQLException;

public class CreateCategory {

    private TextField nc_name;
    private TextArea nc_dsr;
    private Button nc_create;

    public CreateCategory(TextField nc_name, TextArea nc_dsr, Button nc_create) {
        this.nc_name = nc_name;
        this.nc_dsr = nc_dsr;
        this.nc_create = nc_create;
    }

    public void initialize() {
        nc_create.setOnAction(e -> {
            try {
                SQLQuery.CreateCategory(nc_name.getText(), nc_dsr.getText());
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        });
    }
}
