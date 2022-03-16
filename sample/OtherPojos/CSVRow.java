package sample.OtherPojos;


import java.util.ArrayList;
import java.util.List;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author Douglas
 */
public class CSVRow {

   String ColumnName;

    public String getColumnName() {
        return ColumnName;
    }

    public void setColumnName(String columnName) {
        ColumnName = columnName;
    }

    public void setColumns(List<SimpleStringProperty> columns) {
        this.columns = columns;
    }

    private List<SimpleStringProperty> columns;

    public CSVRow() {
        this.columns = new ArrayList<>();
    }

    public CSVRow(List<SimpleStringProperty> columns) {
        this.columns = columns;
    }

    public List<SimpleStringProperty> getColumns() {
        return columns;
    }

    public void addColumn(int index, String value) {
        if (index <= columns.size()) {
            columns.add(index, new SimpleStringProperty(value));
        }
    }

    public void addColumn(int index) {
        addColumn(index, "");
    }

    public boolean isEmpty() {
        if (columns == null || columns.isEmpty()) {
            return true;
        }
        for (SimpleStringProperty prop : columns) {
            if (!prop.getValueSafe().isEmpty()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public String toString() {
        return "CSVRow: " + columns;
    }

    public void removeColumn(int index) {
        if (index < columns.size()) {
            columns.remove(index);
        }
    }
}