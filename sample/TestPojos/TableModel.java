package sample.TestPojos;

public class TableModel {

    private  String PropertyName;

    private int id;

    public TableModel(String name,int id){


        PropertyName = name;
        this.id = id;


    }

    public TableModel(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPropertyName() {
        return PropertyName;
    }

    public void setPropertyName(String propertyName) {
        PropertyName = propertyName;
    }

    public TableModel(String propertyName) {
        PropertyName = propertyName;
    }
}
