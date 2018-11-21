package dev_app.controller.workers;


import db.DBProcessor;
import general.Log;
import general.SQLQuery;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;

import java.sql.SQLException;

public class CreateWorker {
    TextField name;
    TextField surname;
    Button send;
    CheckBox dev;
    CheckBox tester;

    public CreateWorker(TextField name, TextField surname, Button send, CheckBox dev, CheckBox tester) {
        this.name = name;
        this.surname = surname;
        this.send = send;
        this.dev = dev;
        this.tester = tester;
    }

    public void initialize() {
        tester.setSelected(true);
        dev.setSelected(false);

        send.setOnAction(e -> {
            String n = name.getText();
            String sn = surname.getText();
            int isDev = 0;
            if ((dev.isSelected())) {
                isDev = 1;
            } else {
                isDev = 1;
            }


            try {
                SQLQuery.AddWorker(n, sn, String.valueOf(isDev));
            } catch (SQLException e1) {
                Log.Print("error", e1.getLocalizedMessage());
            }
        });

        tester.selectedProperty().addListener(e -> {
            dev.setSelected(!tester.isSelected());
        });
        dev.selectedProperty().addListener(e -> {
            tester.setSelected(!dev.isSelected());
        });
    }
    enum Type {TESTER, DEV};
}
