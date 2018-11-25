package dev_app.controller.workers.report_controller;

import general.SQLQuery;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;

import java.sql.SQLException;
import java.util.LinkedList;

public class DiagramController {
    private StackedBarChart<String, Integer> d_diagram;
    private Button refresh;

    public DiagramController(StackedBarChart<String, Integer> d_diagram, Button refresh) {
        this.d_diagram = d_diagram;
        this.refresh = refresh;
    }

    public void initialize() {
        refresh.setOnAction(e->{refresh();});
    }

    public void refresh() {
        try {
            LinkedList<DiagramData> data = SQLQuery.getDiagramData();
            ObservableList<XYChart.Series<String, Integer>> stackBarChartData = FXCollections.observableArrayList();
            XYChart.Series<String, Integer> series = new XYChart.Series<>();
            series.setName("человекодни");
            for (DiagramData d: data) {
                series.getData().add(new XYChart.Data<>(d.name, d.days));
            } stackBarChartData.add(series);
            d_diagram.setData(stackBarChartData);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
