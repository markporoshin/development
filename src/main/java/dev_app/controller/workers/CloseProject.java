package dev_app.controller.workers;

import entity.Project;
import general.SQLQuery;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.sql.ResultSet;
import java.sql.SQLException;


public class CloseProject {
    private TextField id;
    private TextField name;
    private DatePicker endDate;
    private Button end;

    public CloseProject(TextField id, TextField name, DatePicker endDate, Button end) {
        this.id = id;
        this.name = name;
        this.endDate = endDate;
        this.end = end;
    }

    public void initialize() {
        end.setOnAction(e -> {
            try {
                ResultSet set = SQLQuery.select(new Project(id.getText(), name.getText())); set.next();
                SQLQuery.CloseProject(new Project(set));
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        });
    }
}
