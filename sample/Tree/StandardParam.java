package sample.Tree;

import org.zoodb.api.impl.ZooPC;

public class StandardParam extends ZooPC {


    private int id;

    public void setId(int id) {
    zooActivateWrite();
        this.id = id;
    }

    private int parent_id;

    String name;

    public String getName() {
     zooActivateRead();
        return name;
    }

    public void setName(String name) {
     zooActivateWrite();
        this.name = name;
    }

    public int getId() {
        zooActivateRead();
        return id;
    }



    public int getParent_id() {
        zooActivateRead();
        return parent_id;
    }

    public void setParent_id(int parent_id) {
        zooActivateWrite();
        this.parent_id = parent_id;
    }

    public StandardParam(){



    }




}
