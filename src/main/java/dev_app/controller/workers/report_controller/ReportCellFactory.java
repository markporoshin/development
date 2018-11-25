package dev_app.controller.workers.report_controller;

import javafx.scene.control.ListCell;

import static dev_app.controller.workers.report_controller.ReportData.Type.PRJ;

public class ReportCellFactory extends ListCell<ReportData> {

    @Override
    protected void updateItem(ReportData item, boolean empty) {
        super.updateItem(item, empty);

        if (item == null || empty)
            return;
        if (item.t == PRJ) {
            setText(item.prj.prj_name + "\n" + getEmpl(item));
        } else {
            setText(item.empl.name + " " + item.empl.surname + "\n" + getHistory(item));
        }
    }



    private String getEmpl(ReportData item) {
        StringBuilder builder = new StringBuilder();
        for (String empl : item.prj.empls) {
            builder.append("\t" + empl + "\n");
        }
        return builder.toString();
    }


    private String getHistory(ReportData item) {
        StringBuilder builder = new StringBuilder();
        for (ReportData.EmpData.History h : item.empl.history) {
            builder.append("\t" + h.prj_name + "\n\tдата прихода на проект: " + h.start.toLocaleString() + "\n\tдата ухода с проекта:  " + (h.end ==  null  ? " до сих пор тут ": h.end.toLocaleString()) + "\n");
        }
        return builder.toString();
    }

}
/*
* */