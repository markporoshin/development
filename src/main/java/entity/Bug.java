package entity;

import java.sql.Date;

public class Bug {
    public static final String TABLE = "development.bugs";
    public static final String ID = "bug_id";
    public static final String NAME = "bug_name";
    public static final String CATEGORY = "bug_category";
    public static final String START = "bug_start";
    public static final String END = "bug_end";
    public static final String CREATOR = "creator_id";
    public static final String FOUNDER = "founder_id";
    public static final String FIXER = "fixer_id";
    public static final String DONE = "bug_done";
    public static final String PRJ_ID = "project_id";
    public static final String DEADLINE = "bug_deadline";


    String  category;

    int id;
    int creator_id;
    int founder_id;
    int corrector_id;

    Date start;
    Date end;

    Boolean done;

    public Bug(String category, int id, int creator_id, int founder_id, int corrector_id, Date start, Date end, Boolean done) {
        this.category = category;
        this.id = id;
        this.creator_id = creator_id;
        this.founder_id = founder_id;
        this.corrector_id = corrector_id;
        this.start = start;
        this.end = end;
        this.done = done;
    }

    @Override
    public String toString() {
        return "Bug{" +
                "category='" + category + '\'' +
                ", id=" + id +
                ", creator_id=" + creator_id +
                ", founder_id=" + founder_id +
                ", corrector_id=" + corrector_id +
                ", start=" + start +
                ", end=" + end +
                ", done=" + done +
                '}';
    }
}
