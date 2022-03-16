package sample.TestPojos;

import org.zoodb.api.impl.ZooPC;


public class SignatureTableModel extends ZooPC {

    int id;

    String ReportName;

    String ReportPath;

    String Date;

    public String getDate() {
        zooActivateRead();
        return Date;
    }

    public void setDate(String date) {
    zooActivateWrite();
        Date = date;
    }

    public int getId() {
        zooActivateRead();
        return id;
    }

    public void setId(int id) {
      zooActivateWrite();
        this.id = id;
    }

    public String getReportName() {
        zooActivateRead();
        return ReportName;
    }

    public void setReportName(String reportName) {
    zooActivateWrite();
        ReportName = reportName;
    }

    public String getReportPath() {
       zooActivateRead();
        return ReportPath;
    }

    public void setReportPath(String reportPath) {
       zooActivateWrite();
        ReportPath = reportPath;
    }


}
