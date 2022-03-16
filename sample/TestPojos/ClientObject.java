package sample.TestPojos;

import org.zoodb.api.impl.ZooPC;

public class ClientObject extends ZooPC {

    int id;

    String companyName;

    String adress;


    public ClientObject(){




    }


    public int getId() {
        zooActivateRead();
        return id;
    }

    public void setId(int id) {
        zooActivateWrite();
        this.id = id;
    }

    public String getCompanyName() {
       zooActivateRead();
        return companyName;
    }

    public void setCompanyName(String companyName) {
       zooActivateWrite();
        this.companyName = companyName;
    }

    public String getAdress() {
       zooActivateRead();
        return adress;
    }

    public void setAdress(String adress) {
      zooActivateWrite();
        this.adress = adress;
    }
}
