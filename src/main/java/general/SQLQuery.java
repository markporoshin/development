package general;

import com.sun.xml.internal.xsom.impl.scd.Iterators;
import db.DBProcessor;
import dev_app.controller.workers.report_controller.DiagramData;
import dev_app.controller.workers.report_controller.ReportData;
import entity.*;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.LinkedList;

public class SQLQuery {

    public static void AddWorker(String name, String surname, String type) throws SQLException {
        String query = new Insert(Worker.TABLE, Worker.NAME, Worker.SURNAME, Worker.PRJ_1, Worker.PRJ_2, Worker.IS_DEV).
                values(name, surname, "-1", "-1", type);
        DBProcessor.getStatement().execute(query);
    }

    public static void AddProject(String name, Date start, Date end, Boolean isDone) throws SQLException {
        String query = new Insert(Project.TABLE, Project.NAME, Project.START, Project.DEADLINE, Project.DONE).
                values(name, start.toString(), end.toString(), String.valueOf(isDone ? 1 : 0));
        DBProcessor.getStatement().execute(query);
    }

    public static void AddBug(String name, String prj_id, String dev_id, LocalDate when) throws SQLException {
        String query = new Insert(Bug.TABLE, Bug.NAME, Bug.PRJ_ID, Bug.CREATOR, Bug.START, Bug.DONE).
                values(name, String.valueOf(prj_id), String.valueOf(dev_id), when.toString(), "0");
        DBProcessor.getStatement().execute(query);
    }

    public static void TransferWorker(Worker w, Project o, Project n) throws SQLException {
        {
            String query;
            if (w.prj1_id == o.id)
                query = "update " + Worker.TABLE + " set " + Worker.PRJ_1 + " = " + n.id;
            else if (w.prj2_id == o.id)
                query = "update " + Worker.TABLE + " set " + Worker.PRJ_2 + " = " + n.id;
            else
                throw new SQLException();
            query += " where " + Worker.ID + " = " + w.id;
            DBProcessor.getStatement().execute(query);
        }
        {
            String query = new Update(History.TABLE).set(History.END + " = \'" + String.valueOf(LocalDate.now()) + "\'").
                    where(History.END + " is NULL", History.EMP + " = \'" + w.id + "\'", History.TO + " = \'" + o.id + "\'").get();
            DBProcessor.getStatement().execute(query);
        }
        {
            String query = new Insert(History.TABLE, History.EMP, History.FROM, History.TO, History.START).
                    values(String.valueOf(w.id), String.valueOf(o.id), String.valueOf(n.id), String.valueOf(LocalDate.now()));
            DBProcessor.getStatement().execute(query);
        }
    }

    public static void FindBug(String bug_id, String finder_id, String fixer_id, LocalDate when, String cat_id) throws SQLException {
        String query = new Update(Bug.TABLE).
                set(Bug.FOUNDER + " = \'" + finder_id + '\'', Bug.FIXER + " = \'" + fixer_id + '\'',
                        Bug.DEADLINE + " = \'" + when + '\'', Bug.CATEGORY + " = \'" + cat_id + '\'').
                where(Bug.ID + " = \'" + bug_id + '\'').get();
        DBProcessor.getStatement().execute(query);
    }

    public static void FixBug(String bug_id, LocalDate date) throws SQLException {
        String query = new Update(Bug.TABLE).set(Bug.END + " = \'" + date + "\'", Bug.DONE + " = \'1\'").
                where(Bug.ID + " = \'" + bug_id + '\'').get();
        DBProcessor.getStatement().execute(query);
    }

    public static void CloseProject(Project prj) throws SQLException {
        String query = "update " + Project.TABLE + " set " + Project.DONE + " = \'1\', " + Project.END + " = \'" + prj.end.toString() + "\' where " + Project.ID + " = \'" + prj.id + "\'";
        DBProcessor.getStatement().execute(query);
    }

    public static ResultSet select(Worker worker) throws SQLException {
        String id = "";
        if (worker.id != -1)
            id = Worker.ID + " = \'" + worker.id + '\'';
        String name = "";
        if (worker.name != null && !worker.name.isEmpty())
            id = Worker.NAME + " = \'" + worker.name + '\'';
        String surname = "";
        if (worker.surname != null && !worker.name.isEmpty())
            id = Worker.SURNAME + " = \'" + worker.surname + '\'';
        String query = new Select("*").from(Worker.TABLE).where(id, name, surname).get();
        return DBProcessor.getStatement().executeQuery(query);
    }

    public static ResultSet select(Project prj) throws SQLException {
        String id = "";
        String title = "";

        if (prj.id != -1) {
            id = Project.ID + " = \'" + prj.id + '\'';
        }if (prj.name != null && !prj.name.isEmpty()) {
            title = Project.NAME + " = \'" + prj.name + '\'';
        }
        String query = new Select("*").from(Project.TABLE).where(id, title).get();
        return DBProcessor.getStatement().executeQuery(query);
    }

    public static HashMap<String, ReportData> getProjectsReport() throws SQLException {
        HashMap<String, ReportData> data = new HashMap<>();
        String query = "select prj_name, (null) as emp_name, (null) as emp_surname \n" +
                "from\n" +
                "development.projects as prj\n" +
                "where prj_id > 0\n" +
                "union\n" +
                "select prj_name, emp_name, emp_surname from\n" +
                "development.projects as prj\n" +
                "right outer join \n" +
                "development.employees as empl\n" +
                "on prj.prj_id = empl.prj_id_1\n" +
                "where prj_id > 0\n" +
                "union\n" +
                "select prj_name, emp_name, emp_surname from \n" +
                "development.projects as prj \n" +
                "right outer join \n" +
                "development.employees as empl\n" +
                "on prj.prj_id = empl.prj_id_2\n" +
                "where prj_id > 0";

        ResultSet set = DBProcessor.getStatement().executeQuery(query);

        while(set.next()) {
            String prj_name = set.getString("prj_name");

            if(!data.containsKey(prj_name)) {
                data.put(prj_name, new ReportData(prj_name));
            }
            if (set.getString("emp_name") != null) {
                String name = set.getString("emp_name");
                String surname = set.getString("emp_surname");
                ReportData buf = data.get(prj_name);
                buf.addEmplToPrj(name, surname);
            }

        }

        return  data;
    }

    public static HashMap<String, ReportData> getEmplsReport() throws SQLException {
        HashMap<String, ReportData> data = new HashMap<>();
        String query = "select (null) as date_start, (null) as date_end, emp_name, emp_surname, (null) as prj_name FROM\n" +
                "development.employees as e\n" +
                "union\n" +
                "SELECT date_start, date_end, emp_name, emp_surname, prj_name FROM\n" +
                "development.history as h\n" +
                "left join\n" +
                "development.employees as e\n" +
                "on h.emp_id = e.emp_id\n" +
                "left join\n" +
                "development.projects as p\n" +
                "on h.prj_to = p.prj_id\n" +
                "order by date_start";
        ResultSet set = DBProcessor.getStatement().executeQuery(query);

        while(set.next()) {
            String name = set.getString("emp_name");
            String surname = set.getString("emp_surname");
            if(!data.containsKey(name+surname)) {
                data.put(name+surname, new ReportData(name, surname));
            }


            ReportData buf = data.get(name+surname);
            Date start = set.getDate("date_start");
            Date end = set.getDate("date_end");
            String prj_name = set.getString("prj_name");
            if (start != null && prj_name != null)
                buf.addTransferToEmpl(start, end, prj_name);


        }

        return data;
    }

    public static void CreateCategory(String name, String surname) throws SQLException {
        String query = new Insert(Category.TABLE, Category.NAME, Category.DESCRIPTION).
                values(name, surname);
        DBProcessor.getStatement().execute(query);
    }

    public static LinkedList<DiagramData> getDiagramData() throws SQLException {
        LinkedList<DiagramData> res = new LinkedList<>();
        String queery = "select prj_name, sum(period) as p from\n" +
                "(SELECT  prj_name, sum((if(date_end is null, to_days(now()), to_days(date_end)) - to_days(date_start))) as period FROM \n" +
                "development.history  as h\n" +
                "left join\n" +
                "development.projects as p\n" +
                "on prj_id = prj_to\n" +
                "GROUP BY prj_to\n" +
                "union\n" +
                "SELECT  prj_name, (0) as period FROM development.projects) as t\n" +
                "GROUP BY prj_name";
        ResultSet set = DBProcessor.getStatement().executeQuery(queery);
        while (set.next()) {
            res.add(new DiagramData(set.getString("prj_name"), set.getInt("p")));
        }
        return res;
    }

    public static void sackDevelopmers(int M) throws SQLException {
        String query = "update \n" +
                "development.employees\n" +
                "left join\n" +
                "(SELECT count(bug_id) as bugs, creator_id \n" +
                "FROM development.bugs \n" +
                "where (to_days(now()) - to_days(bug_start)) < 30 group by creator_id) as t\n" +
                "on emp_id = creator_id\n" +
                "set is_sack = (if(bugs > " + M +", 1, 0))";
        DBProcessor.getStatement().execute(query);
    }

    public static void sackTesters(int M) throws SQLException {
        String query = "update \n" +
                "development.employees\n" +
                "left join\n" +
                "(SELECT count(bug_id) as bugs, fixer_id \n" +
                "FROM development.bugs \n" +
                "where (to_days(now()) - to_days(bug_deadline)) > 30 \n" +
                "and fixer_id is not null\n" +
                "group by fixer_id) as t\n" +
                "on emp_id = fixer_id\n" +
                "set is_sack = (if(bugs > " + M + ", 1, 0))";
        DBProcessor.getStatement().execute(query);
    }

    public static  ResultSet selectSuckerFixers(int M) throws SQLException {
        String query = "select * from (SELECT count(bug_id) as bugs, fixer_id \n" +
                "FROM development.bugs \n" +
                "where (to_days(now()) - to_days(bug_deadline)) > 30 \n" +
                "and fixer_id is not null\n" +
                "and bug_done = 0\n" +
                "group by fixer_id) as t\n" +
                "where t.bugs > " + M;
        return DBProcessor.getStatement().executeQuery(query);
    }

    public static  ResultSet selectSuckerDevs(int M) throws SQLException {
        String query = "select * from (SELECT count(bug_id) as bugs, creator_id \n" +
                "FROM development.bugs \n" +
                "where (to_days(now()) - to_days(bug_start)) < 30 \n" +
                "and bug_done = 0\n" +
                "group by creator_id) as t\n" +
                "where t.bugs > " + M;
        return DBProcessor.getStatement().executeQuery(query);
    }

    static class Insert {
        private String table;
        private StringBuilder args;
        private StringBuilder values;

        public Insert(String table, String...args) {
            int i= 0;
            this.table = table;
            this.args = new StringBuilder();
            for (String arg : args) {
                this.args.append(arg);
                if (i < args.length - 1)
                    this.args.append(',');
                i++;
            }
        }

        public String values(String...args) {
            int i= 0;
            this.values = new StringBuilder();
            for (String arg : args) {
                this.values.append('\'' + arg + '\'');
                if (i < args.length - 1)
                    this.values.append(',');
                i++;
            }
            String query = "insert into " + table + " (" + this.args.toString() + ") values (" + this.values.toString() + ")";
            return query;
        }
    }

    static class Select {
        private String table;
        private StringBuilder where;
        private StringBuilder columns;
        public Select(String...columns) {
            this.where = new StringBuilder();
            this.columns = new StringBuilder();
            for (String str : columns) {
                this.columns.append(str);
                this.columns.append(',');
            } this.columns.deleteCharAt(this.columns.length()-1);
        }

        public Select from(String table) {
            this.table = table;
            return this;
        }

        public Select where(String...conds) {
            where.append(" where ");
            for (String str: conds) {
                if (!str.isEmpty() && str != null) {
                    where.append(str);
                    this.where.append(" and ");
                }
            } where.delete(where.length()-5 , where.length()-1);
            return this;
        }

        public String get() {
            return "select " + columns + " from " + table + where;
        }

    }

    static class Update {
        String table;
        private StringBuilder set;
        private StringBuilder where;
        public Update(String table) {
            this.table = table;
            set = new StringBuilder();
            where = new StringBuilder();
        }

        public Update set(String...set) {
            this.set.append(" set ");
            for (String str: set) {
                if (!str.isEmpty() && str != null) {
                    this.set.append(str);
                    this.set.append(',');
                }
            } this.set.deleteCharAt(this.set.length()-1);
            return this;
        }

        public Update where(String...conds) {
            where.append(" where ");
            for (String str: conds) {
                if (!str.isEmpty() && str != null) {
                    where.append(str);
                    this.where.append(" and ");
                }
            } //where.deleteCharAt(where.length()-1);
            where.delete(where.length()-5 , where.length()-1);
            return this;
        }

        public String get() {
            return "update " + table + set + where;
        }
    }






}

/*
update development.employees
inner join
(SELECT count(bug_id) as bugs, creator_id
FROM development.bugs
where (to_days(now()) - to_days(bug_start)) < 30 group by creator_id) as t
set is_sack = 1
where is_dev = 1 and t.bugs > 3
*/
