package sample.Database;

import org.dizitart.no2.Document;
import org.dizitart.no2.mapper.Mappable;
import org.dizitart.no2.mapper.NitriteMapper;
import org.dizitart.no2.objects.Id;

import java.io.Serializable;

public class NitStandardParam implements Serializable {

   @Id
    private int id;


    public NitStandardParam(){
    }

    public void setId(int id) {

        this.id = id;
    }

    private int parent_id;

    String name;

    public String getName() {

        return name;
    }

    public void setName(String name) {

        this.name = name;
    }

    public int getId() {

        return id;
    }



    public int getParent_id() {

        return parent_id;
    }

    public void setParent_id(int parent_id) {

        this.parent_id = parent_id;
    }



}
