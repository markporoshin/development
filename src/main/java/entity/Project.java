package entity;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Project {
    public static final String TABLE = "development.projects";
    public static final String ID = "prj_id";
    public static final String NAME = "prj_name";
    public static final String START = "prj_start";
    public static final String DEADLINE = "prj_deadline";
    public static final String END = "prj_end";
    public static final String DONE = "prj_done";

    public int id;

    public String name;

    public Date begin;
    public Date end;

    public boolean isDone;

    public Project(int id, String name, Date begin, Date end) {
        this.id = id;
        this.name = name;
        this.begin = begin;
        this.end = end;
    }

    public Project(String...args) {
        if (args.length > 0) {
            this.id = -1;
            if (args[0] != null && !args[0].isEmpty())
                this.id = Integer.parseInt(args[0]);
        } if (args.length > 1) {
            this.name = args[1];
        }
    }

    public Project(ResultSet set) throws SQLException {
        this(set.getInt(Project.ID),
                set.getString(Project.NAME),
                set.getDate(Project.START),
                set.getDate(Project.DEADLINE));
        this.isDone = set.getBoolean(Project.DONE);
    }

    @Override
    public String toString() {
        return "Project{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", begin=" + begin +
                ", end=" + end +
                '}';
    }
}
