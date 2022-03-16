package sample.OtherPojos;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class TableUser {

    private StringProperty name;


    private StringProperty   title;

    private StringProperty   Approver;



    public TableUser(String id, String title, String appriver) {
        this.name = new SimpleStringProperty(id);
        this.title = new SimpleStringProperty(title);
        this.Approver = new SimpleStringProperty(appriver);
    }



    public String getName() {
        return name.get();
    }


    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getTitle() {
        return title.get();
    }

    public StringProperty titleProperty() {
        return title;
    }

    public void setTitle(String title) {
        this.title.set(title);
    }

    public String getApprover() {
        return Approver.get();
    }

    public StringProperty approverProperty() {
        return Approver;
    }

    public void setApprover(String approver) {
        this.Approver.set(approver);
    }
}
