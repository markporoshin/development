package dev_app.controller.workers;

import general.Log;
import general.SQLQuery;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.sql.Date;
import java.sql.SQLException;

public class CreateProject {
    private TextField name;
    private DatePicker start;
    private DatePicker end;

    private Button go;

    public CreateProject(TextField name, DatePicker start, DatePicker end, Button go) {
        this.name = name;
        this.start = start;
        this.end = end;
        this.go = go;
    }

    public void initialize() {
        go.setOnAction(e -> {
            try {
                SQLQuery.AddProject(name.getText(), Date.valueOf(start.getValue()), Date.valueOf(end.getValue()), false);
            } catch (SQLException e1) {
                Log.Print("Error", e1.getStackTrace().toString() + e1.getLocalizedMessage());
            }
        });
    }
}
