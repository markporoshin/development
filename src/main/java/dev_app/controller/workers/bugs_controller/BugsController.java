package dev_app.controller.workers.bugs_controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BugsController {
    CreateBug createBug;
    FindBug findBug;
    FixBug fixBug;
    ListView<ListDate> list;
    public State state;
    public BugsController(TextField cb_bug_name, TextField cb_prj_id, TextField cb_prj_name, TextField cb_dev_id,
                          TextField cb_dev_name, DatePicker cb_date, Button cb_create,
                          TextField fb_bug_id, TextField fb_bug_name, TextField fb_cat_id, TextField fb_cat_name, TextField fb_test_id,
                          TextField fb_test_name, DatePicker fb_date, TextField fb_fixer_id,
                          TextField fb_fixer_name, Button fb_find, TextField fixb_bug_id, TextField fixb_bug_name,  DatePicker fixb_date,
                          Button fixb_fix, ListView<ListDate> bags_list) {

        createBug = new CreateBug(cb_bug_name, cb_prj_id, cb_prj_name, cb_dev_id, cb_dev_name, cb_date, cb_create, this);
        findBug = new FindBug(fb_bug_id, fb_bug_name, fb_cat_id,fb_cat_name,fb_test_id,fb_test_name,fb_date,fb_fixer_id,fb_fixer_name,fb_find, this);
        fixBug = new FixBug(fixb_bug_name, fixb_bug_id, fixb_date, fixb_fix, this);
        this.list = bags_list;
    }

    public void initialize() {
        createBug.initialize();
        findBug.initialize();
        fixBug.initialize();
        list.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            switch (state) {
                case CREATE_BUG_PRJ:
                    createBug.setPrjField(newValue);
                    break;
                case CREATE_BUG_DEV:
                    createBug.setDevField(newValue);
                    break;
                case FIND_BUG_BUG:
                    findBug.setBugField(newValue);
                    break;
                case FIND_BUG_CAT:
                    findBug.setCatField(newValue);
                    break;
                case FIND_BUG_FIXER:
                    findBug.setFixerField(newValue);
                    break;
                case FIND_BUG_TESTER:
                    findBug.setTesterField(newValue);
                    break;
                case FIX_BUG_BUG:
                    fixBug.setBugField(newValue);
                    break;

            }
        });
    }

    public void setList(ResultSet set) throws SQLException {
        ObservableList<ListDate> items = FXCollections.observableArrayList();
        while (set.next()) {
            items.add(new ListDate(set));
        } if (!items.isEmpty()) list.setItems(items);
    }

    enum State {
        CREATE_BUG_PRJ, CREATE_BUG_DEV,
        FIND_BUG_BUG, FIND_BUG_CAT, FIND_BUG_TESTER, FIND_BUG_FIXER,
        FIX_BUG_BUG
    }
}
