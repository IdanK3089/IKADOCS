package sample.OtherPojos;

import org.zoodb.api.impl.ZooPC;

public class HeaderObj extends ZooPC {

    int id;

    String Header;

    public HeaderObj(){}


    public int getId() {
   zooActivateRead();
        return id;
    }

    public void setId(int id) {
       zooActivateWrite();
        this.id = id;
    }

    public String getHeader() {
       zooActivateRead();
        return Header;
    }

    public void setHeader(String header) {
      zooActivateWrite();
        this.Header = header;
    }
}
