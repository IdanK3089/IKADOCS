package sample.OtherPojos;

import javafx.beans.property.SimpleStringProperty;

public class MinMaxCellObject {

    private SimpleStringProperty text;
    private int parentId;
    private int id;

    public MinMaxCellObject(SimpleStringProperty text, int parentId, int id) {
        this.text = text;
        this.parentId = parentId;
        this.id = id;
    }

    public SimpleStringProperty getText() {
        return text;
    }

    public void setText(SimpleStringProperty text) {
        this.text = text;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
