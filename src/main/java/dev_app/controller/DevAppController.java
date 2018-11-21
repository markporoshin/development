package dev_app.controller;

import dev_app.controller.workers.CloseProject;
import dev_app.controller.workers.CreateProject;
import dev_app.controller.workers.CreateWorker;
import dev_app.controller.workers.TransferWorker;
import dev_app.controller.workers.bugs_controller.BugsController;
import dev_app.controller.workers.bugs_controller.ListDate;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class DevAppController {

    @FXML
    private TextField p_name;

    @FXML
    private TextField p_surname;

    @FXML
    private CheckBox p_dev;

    @FXML
    private CheckBox p_tester;

    @FXML
    private Button p_send;

    @FXML
    private TextField r_id;

    @FXML
    private TextField r_name;

    @FXML
    private TextField r_surname;

    @FXML
    private TextField r_oldName;

    @FXML
    private Button r_send;

    @FXML
    private TextField r_oldId;

    @FXML
    private TextField r_newName;

    @FXML
    private TextField r_newId;

    @FXML
    private TextField cb_prj_id;

    @FXML
    private TextField cb_prj_name;

    @FXML
    private TextField cb_dev_id;

    @FXML
    private TextField cb_dev_name;

    @FXML
    private DatePicker cb_date;

    @FXML
    private Button cb_create;

    @FXML
    private TextField cb_bug_name;

    @FXML
    private TextField fb_bug_id;

    @FXML
    private TextField fb_bug_name;

    @FXML
    private TextField fb_cat_id;

    @FXML
    private TextField fb_cat_name;

    @FXML
    private TextField fb_test_id;

    @FXML
    private TextField fb_test_name;

    @FXML
    private DatePicker fb_date;

    @FXML
    private TextField fb_fixer_id;

    @FXML
    private TextField fb_fixer_name;

    @FXML
    private Button fb_find;

    @FXML
    private TextField fixb_bug_name;

    @FXML
    private TextField fixb_bug_id;

    @FXML
    private DatePicker fixb_date;

    @FXML
    private Button fixb_fix;

    @FXML
    private ListView<ListDate> bags_list;

    @FXML
    private TextField cp_name;

    @FXML
    private DatePicker cp_start;

    @FXML
    private DatePicker cp_end;

    @FXML
    private Button cp_go;

    @FXML
    private Button ep_end;

    @FXML
    private TextField ep_id;

    @FXML
    private TextField ep_name;

    @FXML
    private DatePicker ep_dataEnd;

    @FXML
    private Button report_dev;

    @FXML
    private Button report_empl;

    @FXML
    private ListView<?> report_list;

    @FXML
    void initialize() {
        new CreateWorker(p_name, p_surname, p_send, p_dev, p_tester).initialize();
        new TransferWorker(r_id, r_name, r_surname, r_oldId, r_oldName, r_newId, r_newName, r_send).initialize();
        new CreateProject(cp_name, cp_start, cp_end, cp_go).initialize();
        new CloseProject(ep_id, ep_name, ep_dataEnd, ep_end).initialize();
        new BugsController(cb_bug_name, cb_prj_id,cb_prj_name,cb_dev_id,cb_dev_name,cb_date,cb_create,
                fb_bug_id, fb_bug_name, fb_cat_id, fb_cat_name,fb_test_id,fb_test_name,fb_date,fb_fixer_id,fb_fixer_name,fb_find,
                fixb_bug_id, fixb_bug_name, fixb_date,fixb_fix, bags_list).initialize();
    }
}
