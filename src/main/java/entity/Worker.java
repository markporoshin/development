package entity;

import com.sun.corba.se.spi.orbutil.threadpool.Work;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Worker {

    public static final String TABLE = "development.employees";
    public static final String ID = "emp_id";
    public static final String NAME = "emp_name";
    public static final String SURNAME = "emp_surname";
    public static final String PRJ_1 = "prj_id_1";
    public static final String PRJ_2 = "prj_id_2";
    public static final String IS_DEV = "is_dev";
    public static final String IS_SACK = "is_sack";

    public int id;
    public int prj1_id;
    public int prj2_id;

    public String name;
    public String surname;

    public Worker(int id, int prj1_id, int prj2_id, String name, String surname) {
        this.id = id;
        this.prj1_id = prj1_id;
        this.prj2_id = prj2_id;
        this.name = name;
        this.surname = surname;
    }

    public Worker(String...args) {
        if (args.length > 0 ) {
            this.id = -1;
            if (args[0] != null && !args[0].isEmpty())
                this.id = Integer.parseInt(args[0]);
        }if (args.length > 1 ) {
            this.name = args[1].isEmpty() ? null : args[1];
        }if (args.length > 2 )
            this.surname = args[2].isEmpty() ? null : args[2];
        if (args.length > 3 ) {
            this.prj1_id = -1;
            if (args[3] != null && !args[3].isEmpty())
                this.prj1_id = Integer.parseInt(args[3]);
        }
        if (args.length > 4 ){
            this.prj2_id = -1;
            if (args[4] != null && !args[4].isEmpty())
                this.prj2_id = Integer.parseInt(args[4]);
        }

    }

    public Worker(ResultSet set) throws SQLException {
        this(set.getInt(Worker.ID),
                set.getInt(Worker.PRJ_1),
                set.getInt(Worker.PRJ_2),
                set.getString(Worker.NAME),
                set.getString(Worker.SURNAME));
    }

    @Override
    public String toString() {
        return "Worker{" +
                "id=" + id +
                ", prj1_id=" + prj1_id +
                ", prj2_id=" + prj2_id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                '}';
    }
}
