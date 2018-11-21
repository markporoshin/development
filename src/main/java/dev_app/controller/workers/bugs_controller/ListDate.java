package dev_app.controller.workers.bugs_controller;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ListDate {
    int id;
    String name;

    public ListDate(ResultSet set) throws SQLException {
        this.id = set.getInt("id");
        this.name = set.getString("name");
    }

    @Override
    public String toString() {
        return "id = " + id + ", name = '" + name + '\'';
    }
}
