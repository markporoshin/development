package part_2;

import db.DBProcessor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.Locale;
import java.util.Random;
import java.util.UUID;

public class Main {

    private static Random rand = new Random();

    public static void main(String[] args) throws SQLException {
        //createTable("little");
        //fillTable("little", 1000);
        researchAddAndDelete("research.little", 1000, 5);
    }

    public static void createTable(String name) throws SQLException {
        String query = "CREATE TABLE `research`.`" + name + "` (\n" +
                "  `id` INT NOT NULL AUTO_INCREMENT,\n" +
                "  `name` VARCHAR(45) NULL,\n" +
                "  `surname` VARCHAR(45) NULL,\n" +
                "  PRIMARY KEY (`id`));";
        DBProcessor.getStatement().execute(query);
    }
    public static void fillTable(String name, int size) throws SQLException {
        for (int i = 0; i < size; i++) {
            String query = "insert into `research`.`" + name + "` (name, surname) values ('"+generateString()+"','"+generateString()+"')";
            DBProcessor.getStatement().execute(query);
        }
    }
    public static String generateString() {
        String uuid = UUID.randomUUID().toString();
        return uuid;
    }


    public static void researchSelectByKey(String name, int size) throws SQLException {

        int N = 100000;
        long start = System.nanoTime();
        for(int i = 0; i < N; i++) {
            String query = "select * from " + name + " where id = " + (Math.abs(rand.nextInt()) % size);
            DBProcessor.getStatement().execute(query);
        }
        long finish = System.nanoTime();
        String time = String.format(Locale.US, "%.5f", (double)(finish - start) / N / 1e9);
        System.out.println("researchSelectByKey("+name+") : " + time);
        //System.out.println("researchSelect("+name+") : " + (finish - start) / N);
    }
    public static void researchSelectByField(String name, int size) throws SQLException {

        int N = 100;

        ResultSet set  = DBProcessor.getStatement().executeQuery("select name from " + name);
        LinkedList<String> list = new LinkedList<>();
        while (set.next()) list.add(set.getString("name"));

        long start = System.nanoTime();
        for(int i = 0; i < N; i++) {
            String query = "select * from " + name + " where name = \'" + list.get(i) + "\'";
            DBProcessor.getStatement().execute(query);
        }
        long finish = System.nanoTime();
        String time = String.format(Locale.US, "%f", (double)(finish - start) / N / 1e9);
        System.out.println("researchSelectByField("+name+") : " + time);
        //System.out.println("researchSelect("+name+") : " + (finish - start) / N);
    }
    public static void researchSelectByMask(String name, int size) throws SQLException {

        int N = 1000;

        ResultSet set  = DBProcessor.getStatement().executeQuery("select name from " + name);
        LinkedList<String> list = new LinkedList<>();
        while (set.next()) list.add(set.getString("name"));

        long start = System.nanoTime();
        for(int i = 0; i < N; i++) {
            int index = (Math.abs(rand.nextInt()) % list.size());
            String query = "select * from " + name + " where name like \'" + list.get(index).charAt(0) + "%\'";
            DBProcessor.getStatement().execute(query);
        }
        long finish = System.nanoTime();
        String time = String.format(Locale.US, "%f", (double)(finish - start) / N / 1e9);
        System.out.println("researchSelectByField("+name+") : " + time);
        //System.out.println("researchSelect("+name+") : " + (finish - start) / N);
    }

    public static void researchAddAndDelete(String name, int size, int offset) throws SQLException {
        int count = 1;
        int N = 100;
        {
            long start = System.nanoTime();
            for (int i = 0; i < N; i++) {
                addRows(name, size, offset + count*i, count);
            }
            long finish = System.nanoTime();
            String time = String.format(Locale.US, "%f", (double) (finish - start) / N / 1e9);
            System.out.println("researchAdd(" + name + ") : " + time);
        }
        {
            long start = System.nanoTime();
            for (int i = 0; i < N; i++) {
                deleteRows(name, size, offset + count*i, count);
            }
            long finish = System.nanoTime();
            String time = String.format(Locale.US, "%f", (double) (finish - start) / N / 1e9);
            System.out.println("researchDelete(" + name + ") : " + time);
        }
    }
    public static void addRows(String name, int size, int offset, int count) throws SQLException {
        for (int i = 0; i < count; i++) {
            String query = "insert into " + name + "(id, name, surname) values ('" + (size + offset + i) + "','"+generateString()+"','"+generateString()+"')";
            DBProcessor.getStatement().execute(query);
        }
    }
    public static void deleteRows(String name, int size, int offset, int count) throws SQLException {
        for (int i = 0; i < count; i++) {
            String query = "delete from " + name + " where id = " + (size + offset + i);
            DBProcessor.getStatement().execute(query);
        }
    }

    public static void researchUpdateByKey(String name, int size) throws SQLException {
        int N = 10000;
        long start = System.nanoTime();
        for(int i = 0; i < N; i++) {
            //int index = (Math.abs(rand.nextInt()) % chars.length);
            String query = "update " + name + " set overpayment = " + (rand.nextInt() % 50) + " where id = " + (Math.abs(rand.nextInt()) % size);
            DBProcessor.getStatement().execute(query);
        }
        long finish = System.nanoTime();
        String time = String.format(Locale.US, "%f", (double)(finish - start) / N / 1e9);
        System.out.println("researchUpdate("+name+") : " + time);
    }

    public static void researchUpdateByField(String name, int size) throws SQLException {
        int N = 1000;
        long start = System.nanoTime();
        for(int i = 0; i < N; i++) {
            String query = "update " + name + " set overpayment = " + (rand.nextInt() % 50) + " where name = \'" + generateString() + "\'";
            DBProcessor.getStatement().execute(query);
        }
        long finish = System.nanoTime();
        String time = String.format(Locale.US, "%f", (double)(finish - start) / N / 1e9);
        System.out.println("researchUpdate("+name+") : " + time);
    }

    public static void researchDeleteByField(String name, int size) throws SQLException {
        DBProcessor.getStatement().execute("delete from research.buf");
        String clone = "insert into research.buf select * from " + name;
        DBProcessor.getStatement().execute(clone);


        ResultSet set  = DBProcessor.getStatement().executeQuery("select name from " + name);
        LinkedList<String> list = new LinkedList<>();
        while (set.next()) list.add(set.getString("name"));

        int N = 200;
        long start = System.nanoTime();
        for(int i = 0; i < N; i++) {
            //int index = (Math.abs(rand.nextInt()) % GenerateTable.name.length);
            String query = "delete from " + name + " where name = \'" + list.get(i) + "\'";
            DBProcessor.getStatement().execute(query);
        }
        long finish = System.nanoTime();
        String time = String.format(Locale.US, "%f", (double)(finish - start) / N / 1e9);
        System.out.println("researchDeleteByField("+name+") : " + time);

        String delete = "delete from " + name + " where id >= 0";
        DBProcessor.getStatement().execute(delete);

        String query = "insert into " + name + " select * from research.buf";
        DBProcessor.getStatement().execute(query);
        DBProcessor.getStatement().execute("delete from research.buf");
    }


    public static void researchArch1(String name, int size) throws SQLException {
        System.out.println("optimize " + name + ":" + String.format("%f", arch2(name, size)));
    }
    public static double arch(String name, int size) throws SQLException {
        DBProcessor.getStatement().execute("delete from research.buf");
        DBProcessor.getStatement().execute("insert into research.buf select * from " + name);

        DBProcessor.getStatement().execute("delete from " + name + " where id > " + (size - 200));

        long start = System.nanoTime();
        DBProcessor.getStatement().execute("optimize table " + name);
        long finish = System.nanoTime();

        Double time = (double)(finish - start) / 1e9;

        DBProcessor.getStatement().execute("delete from " + name);
        DBProcessor.getStatement().execute("insert into " + name + " select * from research.buf");
        DBProcessor.getStatement().execute("delete from research.buf");
        return  time;
    }

    public static double arch2(String name, int size) throws SQLException {
        DBProcessor.getStatement().execute("delete from research.buf");
        DBProcessor.getStatement().execute("insert into research.buf select * from " + name);

        DBProcessor.getStatement().execute("delete from " + name + " where id > " + (200));

        long start = System.nanoTime();
        DBProcessor.getStatement().execute("optimize table " + name);
        long finish = System.nanoTime();

        Double time = (double)(finish - start) / 1e9;

        DBProcessor.getStatement().execute("delete from " + name);
        DBProcessor.getStatement().execute("insert into " + name + " select * from research.buf");
        DBProcessor.getStatement().execute("delete from research.buf");
        return  time;
    }

}
