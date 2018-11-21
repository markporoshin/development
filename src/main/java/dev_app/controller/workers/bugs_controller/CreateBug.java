package dev_app.controller.workers.bugs_controller;

import db.DBProcessor;
import entity.Bug;
import entity.Project;
import entity.Worker;
import general.Log;
import general.SQLQuery;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.sql.SQLException;

public class CreateBug {
    private TextField bug_name;
    private TextField prj_id;
    private TextField prj_name;
    private TextField dev_id;
    private TextField dev_name;
    private DatePicker date;
    private Button create;
    private BugsController bugsController;

    private ListDate prj;
    private ListDate dev;

    public CreateBug(TextField bug_name, TextField prj_id, TextField prj_name, TextField dev_id, TextField dev_name, DatePicker date, Button create, BugsController bugsController) {
        this.bug_name = bug_name;
        this.prj_id = prj_id;
        this.prj_name = prj_name;
        this.dev_id = dev_id;
        this.dev_name = dev_name;
        this.date = date;
        this.create = create;
        this.bugsController = bugsController;
    }


    public void initialize() {
        prj_id.textProperty().addListener(listenerPrj);
        prj_name.textProperty().addListener(listenerPrj);
        dev_id.textProperty().addListener(listenerDev);
        dev_name.textProperty().addListener(listenerDev);

        create.setOnAction(e -> {
            try {
                SQLQuery.AddBug(bug_name.getText(), prj_id.getText(), dev_id.getText(), date.getValue());
            } catch (SQLException e1) {
                Log.Print("Error", e1.getLocalizedMessage());
                e1.printStackTrace();
            }
        });
    }


    public void setPrjField(ListDate date) {
        prj_id.setText(String.valueOf(date.id));
        prj_name.setText(date.name);

        prj = date;
    }

    public void setDevField(ListDate date) {
        dev_id.setText(String.valueOf(date.id));
        dev_name.setText(date.name);

        dev = date;
    }

    ChangeListener<String> listenerPrj = (observable, oldValue, newValue) -> {
        try {
            String query = "select " + Project.NAME + " as name, " + Project.ID + " as id from " + Project.TABLE;
            if (!prj_name.getText().isEmpty() && !prj_id.getText().isEmpty())
                query += " where " + Project.NAME + " like \'" + prj_name.getText() + "%\' and " + Project.ID + " = \'" + prj_id.getText() + "\'";
            else if (!prj_name.getText().isEmpty())
                query += " where " + Project.NAME + " like \'" + prj_name.getText() + "%\'";
            else if (!prj_id.getText().isEmpty())
                query += " where " + Project.ID + " = \'" + prj_id.getText() + "%\'";

            bugsController.state = BugsController.State.CREATE_BUG_PRJ;
            bugsController.setList(DBProcessor.getStatement().executeQuery(query));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    };

    ChangeListener<String> listenerDev = (observable, oldValue, newValue) -> {
        try {
            String query = "select " + Worker.NAME + " as name, " + Worker.ID + " as id from " + Worker.TABLE;

            if (!dev_name.getText().isEmpty() && !dev_id.getText().isEmpty())
                query += " where " + Worker.NAME + " like \'" + dev_name.getText() + "%\' and " + Worker.ID + " = \'" + dev_id.getText() + "\'";
            else if (!dev_name.getText().isEmpty())
                query += " where " + Worker.NAME + " like \'" + dev_name.getText() + "%\'";
            else if (!dev_id.getText().isEmpty())
                query += " where " + Worker.ID + " = \'" + dev_id.getText() + "%\'";
            bugsController.state = BugsController.State.CREATE_BUG_DEV;
            bugsController.setList(DBProcessor.getStatement().executeQuery(query));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    };





}
