package sample.Database;

import javafx.beans.property.SimpleBooleanProperty;
import org.zoodb.api.impl.ZooPC;



public class MyUser extends ZooPC {

    private int ID;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getApprover() {
        return Approver;
    }

    private String name;


    private String title;

    private String Approver;

    private String UserName;


    public String getUserName() {
    zooActivateRead();
        return UserName;
    }

    public void setUserName(String userName) {
       zooActivateWrite();
        UserName = userName;
    }



    public boolean isChecked() {
        return checked.get();
    }

    public void setChecked(boolean checked) {
        this.checked.set(checked);
    }

    private SimpleBooleanProperty checked = new SimpleBooleanProperty(false);
    // other columns here

    public SimpleBooleanProperty checkedProperty() {
        return this.checked;
    }

    public java.lang.Boolean getChecked() {
        return this.checkedProperty().get();
    }

    public void setChecked(final java.lang.Boolean checked) {
        this.checkedProperty().set(checked);
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        zooActivateWrite();
        this.title = title;
    }

    public String GetApprover() {

        return this.Approver;
    }

    public void setApprover(String approver) {
        zooActivateWrite();
        Approver = approver;
    }

    @SuppressWarnings("unused")
    public MyUser() {
        // All persistent classes need a no-args constructor.
        // The no-args constructor can be private.
    }

    public MyUser(String name,String Title,String Approver) {
        // no activation required
        this.name = name;
        this.title = Title;
        this.Approver = Approver;
    }

    public void setName(String name) {
        //activate and flag as dirty
        zooActivateWrite();
        this.name = name;
    }

    public String getName() {
        //activate

        zooActivateRead();

        return this.name;
    }





}
