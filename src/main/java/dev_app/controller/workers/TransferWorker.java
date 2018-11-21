package dev_app.controller.workers;


import db.DBProcessor;
import entity.Project;
import entity.Worker;
import general.SQLQuery;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TransferWorker {
    TextField id;
    TextField name;
    TextField surname;

    TextField oldPrjId;
    TextField oldPrjName;
    TextField newPrjId;
    TextField newPrjName;

    Button send;
    public TransferWorker(TextField id, TextField name, TextField surname, TextField oldPrjId,
                          TextField oldPrjName, TextField newPrjId, TextField newPrjName,
                          Button send) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.oldPrjId = oldPrjId;
        this.oldPrjName = oldPrjName;
        this.newPrjId = newPrjId;
        this.newPrjName = newPrjName;
        this.send = send;
    }

    public void initialize() {
        send.setOnAction(e -> {
            if (!oldPrjId.getText().isEmpty() || !oldPrjName.getText().isEmpty()) {
                try {
                    ResultSet w = SQLQuery.select(new Worker(id.getText(), name.getText(), surname.getText()));
                    w.next();
                    Worker current = new Worker(w);
                    ResultSet o = SQLQuery.select(new Project(oldPrjId.getText(), oldPrjName.getText()));
                    o.next();
                    Project oldPrj = new Project(o);
                    ResultSet n = SQLQuery.select(new Project(newPrjId.getText(), newPrjName.getText()));
                    n.next();
                    Project newPrj = new Project(n);
                    SQLQuery.TransferWorker(current, oldPrj, newPrj);
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            } else {
                try {
                    ResultSet w = SQLQuery.select(new Worker(id.getText(), name.getText(), surname.getText()));
                    w.next();
                    Worker current = new Worker(w);
                    Project oldPrj = new Project("-1");
                    ResultSet n = SQLQuery.select(new Project(newPrjId.getText(), newPrjName.getText()));
                    n.next();
                    Project newPrj = new Project(n);
                    SQLQuery.TransferWorker(current, oldPrj, newPrj);
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }

            }
        });
    }
}
