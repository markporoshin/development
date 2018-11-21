package dev_app.controller.workers.bugs_controller;

import db.DBProcessor;
import entity.Bug;
import general.Log;
import general.SQLQuery;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.sql.SQLException;

public class FixBug {


    private TextField bug_name;
    private TextField bug_id;
    private DatePicker date;
    private Button fix;
    private BugsController bugsController;

    private ListDate bug;

    public FixBug(TextField bug_name, TextField bug_id, DatePicker date, Button fix, BugsController bugsController) {
        this.bug_name = bug_name;
        this.bug_id = bug_id;
        this.date = date;
        this.fix = fix;
        this.bugsController = bugsController;
    }




    public void initialize() {
        bug_id.textProperty().addListener(listenerBug);
        bug_name.textProperty().addListener(listenerBug);

        fix.setOnAction(e -> {
            try {
                SQLQuery.FixBug(bug_id.getText(), date.getValue());
            } catch (SQLException e1) {
                Log.Print("error", e1.getLocalizedMessage());
                e1.printStackTrace();
            }
        });
    }

    public void setBugField(ListDate date) {
        bug_id.setText(String.valueOf(date.id));
        bug_name.setText(date.name);

        bug = date;
    }

    ChangeListener<String> listenerBug = (observable, oldValue, newValue) -> {
        try {
            String query = "select " + Bug.NAME + " as name, " + Bug.ID + " as id from " + Bug.TABLE + " where " + Bug.FOUNDER + " is not NULL and " + Bug.DONE + " = \'0\'";

            if (!bug_name.getText().isEmpty() && !bug_id.getText().isEmpty())
                query += " and " + Bug.NAME + " like \'" + bug_name.getText() + "%\' and " + Bug.ID + " = \'" + bug_id.getText() + "\'";
            else if (!bug_name.getText().isEmpty())
                query += " and " + Bug.NAME + " like \'" + bug_name.getText() + "%\'";
            else if (!bug_id.getText().isEmpty())
                query += " and " + Bug.ID + " = \'" + bug_id.getText() + "%\'";
            bugsController.state = BugsController.State.FIX_BUG_BUG;
            bugsController.setList(DBProcessor.getStatement().executeQuery(query));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    };
}
