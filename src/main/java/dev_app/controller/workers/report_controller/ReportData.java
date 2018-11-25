package dev_app.controller.workers.report_controller;

import java.util.Date;
import java.util.LinkedList;

public class ReportData {
    enum Type {PRJ, EMPL};

    Type t;
    PrjData prj;
    EmpData empl;

    public ReportData(String prj_name) {
        t = Type.PRJ;
        prj = new PrjData(prj_name);
    }

    public void addEmplToPrj(String name, String surname) {
        prj.empls.add(name + " " + surname);
    }

    public void addTransferToEmpl(Date start, Date end, String prj_name) {
        empl.history.add(new EmpData.History(start, end, prj_name));
    }


    public ReportData(String name, String surname) {
        t = Type.EMPL;
        empl = new EmpData(name, surname);
    }


    class PrjData {
        public PrjData(String prj_name) {
            this.prj_name = prj_name;
            this.empls = new LinkedList<>();
        }

        String prj_name;
        LinkedList<String> empls;
    }
    static class EmpData {
        String name;
        String surname;
        LinkedList<History> history;

        public EmpData(String name, String surname) {
            this.name = name;
            this.surname = surname;
            history = new LinkedList<>();
        }

        static class History {
            public History(Date start, Date end, String prj_name) {
                this.start = start;
                this.end = end;
                this.prj_name = prj_name;
            }

            Date start;
            Date end;
            String prj_name;
        }
    }
}
