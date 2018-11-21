package dev_app.controller.workers.bugs_controller;

import db.DBProcessor;
import entity.Bug;
import entity.Category;
import entity.Worker;
import general.Log;
import general.SQLQuery;
import javafx.beans.value.ChangeListener;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.sql.SQLException;

public class FindBug {
    private TextField bug_name;
    private TextField bug_id;
    private TextField cat_id;
    private TextField cat_name;
    private TextField test_id;
    private TextField test_name;
    private DatePicker date;
    private TextField fixer_id;
    private TextField fixer_name;
    private Button find;
    private BugsController bugsController;

    private ListDate prj;
    private ListDate tester;
    private ListDate fixer;
    private ListDate bug;

    public FindBug(TextField bug_id, TextField bug_name, TextField cat_id, TextField cat_name, TextField test_id, TextField test_name, DatePicker date, TextField fixer_id, TextField fixer_name, Button find, BugsController bugsController) {
        this.bug_id = bug_id;
        this.bug_name = bug_name;
        this.cat_id = cat_id;
        this.cat_name = cat_name;
        this.test_id = test_id;
        this.test_name = test_name;
        this.date = date;
        this.fixer_id = fixer_id;
        this.fixer_name = fixer_name;
        this.find = find;
        this.bugsController = bugsController;
    }

    public void initialize() {
        bug_id.textProperty().addListener(listenerBug);
        bug_name.textProperty().addListener(listenerBug);
        cat_id.textProperty().addListener(listenerCat);
        cat_name.textProperty().addListener(listenerCat);
        test_id.textProperty().addListener(listenerTester);
        test_name.textProperty().addListener(listenerTester);
        fixer_id.textProperty().addListener(listenerFixer);
        fixer_name.textProperty().addListener(listenerFixer);

        find.setOnAction(e -> {
            try {
                SQLQuery.FindBug(bug_id.getText(), test_id.getText(), fixer_id.getText(), date.getValue(), cat_id.getText());
            } catch (SQLException e1) {
                Log.Print("error", e1.getLocalizedMessage());
                e1.printStackTrace();
            }
        });
    }

    public void setCatField(ListDate date) {
        cat_id.setText(String.valueOf(date.id));
        cat_name.setText(date.name);

        prj = date;
    }

    public void setTesterField(ListDate date) {
        test_id.setText(String.valueOf(date.id));
        test_name.setText(date.name);

        tester = date;
    }

    public void setFixerField(ListDate date) {
        fixer_id.setText(String.valueOf(date.id));
        fixer_name.setText(date.name);

        fixer = date;
    }

    public void setBugField(ListDate date) {
        bug_id.setText(String.valueOf(date.id));
        bug_name.setText(date.name);

        bug = date;
    }

    ChangeListener<String> listenerBug = (observable, oldValue, newValue) -> {
        try {
            String query = "select " + Bug.NAME + " as name, " + Bug.ID + " as id from " + Bug.TABLE + " where " + Bug.FOUNDER + " is NULL and " + Bug.DONE + " = \'0\'";

            if (!bug_name.getText().isEmpty() && !bug_id.getText().isEmpty())
                query += " and " + Bug.NAME + " like \'" + bug_name.getText() + "%\' and " + Bug.ID + " = \'" + bug_id.getText() + "\'";
            else if (!bug_name.getText().isEmpty())
                query += " and " + Bug.NAME + " like \'" + bug_name.getText() + "%\'";
            else if (!bug_id.getText().isEmpty())
                query += " and " + Bug.ID + " = \'" + bug_id.getText() + "%\'";
            bugsController.state = BugsController.State.FIND_BUG_BUG;
            bugsController.setList(DBProcessor.getStatement().executeQuery(query));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    };

    ChangeListener<String> listenerCat = (observable, oldValue, newValue) -> {
        String query = "";
        try {
            query = "select " + Category.NAME + " as name, " + Category.ID + " as id from " + Category.TABLE;
            if (!cat_name.getText().isEmpty() && !cat_id.getText().isEmpty())
                query +=  " where " + Category.NAME + " like \'" + cat_name.getText() + "%\' and " + Category.ID + " = \'" + cat_id.getText() + "\'";
            else if (!cat_name.getText().isEmpty())
                query += " where " + Category.NAME + " like \'" + cat_name.getText() + "%\'";
            else if (!cat_id.getText().isEmpty())
                query += " where " + Category.ID + " = \'" + cat_id.getText() + "%\'";

            bugsController.state = BugsController.State.FIND_BUG_CAT;
            bugsController.setList(DBProcessor.getStatement().executeQuery(query));
        } catch (SQLException e) {
            System.out.println(query);
            e.printStackTrace();
        }
    };

    ChangeListener<String> listenerTester = (observable, oldValue, newValue) -> {
        String query = "";
        try {
            query = "select " + Worker.NAME + " as name, " + Worker.ID + " as id from " + Worker.TABLE;

            if (!test_name.getText().isEmpty() && !test_id.getText().isEmpty())
                query += " where " + Worker.NAME + " like \'" + test_name.getText() + "%\' and " + Worker.ID + " = \'" + test_id.getText() + "\'";
            else if (!test_name.getText().isEmpty())
                query += " where " + Worker.NAME + " like \'" + test_name.getText() + "%\'";
            else if (!test_id.getText().isEmpty())
                query += " where " + Worker.ID + " = \'" + test_id.getText() + "%\'";
            bugsController.state = BugsController.State.FIND_BUG_TESTER;
            bugsController.setList(DBProcessor.getStatement().executeQuery(query));
        } catch (SQLException e) {
            System.out.println(query);
            e.printStackTrace();
        }
    };

    ChangeListener<String> listenerFixer = (observable, oldValue, newValue) -> {
        String query = "";
        try {
            query = "select " + Worker.NAME + " as name, " + Worker.ID + " as id from " + Worker.TABLE;

            if (!fixer_name.getText().isEmpty() && !fixer_id.getText().isEmpty())
                query += " where " + Worker.NAME + " like \'" + fixer_name.getText() + "%\' and " + Worker.ID + " = \'" + fixer_id.getText() + "\'";
            else if (!fixer_name.getText().isEmpty())
                query += " where " + Worker.NAME + " like \'" + fixer_name.getText() + "%\'";
            else if (!fixer_id.getText().isEmpty())
                query += " where " + Worker.ID + " = \'" + fixer_id.getText() + "%\'";
            bugsController.state = BugsController.State.FIND_BUG_FIXER;
            bugsController.setList(DBProcessor.getStatement().executeQuery(query));
        } catch (SQLException e) {
            System.out.println(query);
            e.printStackTrace();
        }
    };
}
