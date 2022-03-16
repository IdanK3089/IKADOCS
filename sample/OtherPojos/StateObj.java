package sample.OtherPojos;

import org.zoodb.api.impl.ZooPC;

import java.util.ArrayList;

public class StateObj extends ZooPC {

    int id;

    String stateName;

    String StatePath;



    public String getStatePath() {
      zooActivateRead();
        return StatePath;
    }

    public void setStatePath(String statePath) {
       zooActivateWrite();
        StatePath = statePath;
    }

    public int getId() {
       zooActivateRead();
        return id;
    }

    public void setId(int id) {
       zooActivateWrite();
        this.id = id;
    }




    public String getStateName() {
        zooActivateRead();
        return stateName;
    }

    public void setStateName(String stateName) {
      zooActivateWrite();
        this.stateName = stateName;
    }

    public StateObj(){


    }


}
