package dev_app.controller.workers.report_controller;

import dev_app.controller.workers.bugs_controller.ListDate;
import general.SQLQuery;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

import java.sql.SQLException;
import java.util.HashMap;

public class ReportController {

    private Button dev;
    private Button empl;
    private ListView<ReportData> list;

    public ReportController(Button dev, Button empl, ListView<ReportData> list) {
        this.dev = dev;
        this.empl = empl;
        this.list = list;

        list.setCellFactory(e -> new ReportCellFactory());
    }

    public void initialize() {
        dev.setOnAction(e -> {
            showDev();
        });
        empl.setOnAction(e -> {
            showEmpl();
        });

    }


    public void showDev() {
        try {
            HashMap<String, ReportData> data = SQLQuery.getProjectsReport();
            ObservableList<ReportData> items = FXCollections.observableArrayList();
            for (ReportData d : data.values()) {
                items.add(d);
            }
            list.setItems(items);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void showEmpl() {
        try {
            HashMap<String, ReportData> data = SQLQuery.getEmplsReport();
            ObservableList<ReportData> items = FXCollections.observableArrayList();
            for (ReportData d : data.values()) {
                items.add(d);
            }
            list.setItems(items);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
