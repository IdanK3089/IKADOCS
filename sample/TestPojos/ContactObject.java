package sample.TestPojos;

import org.zoodb.api.impl.ZooPC;

public class ContactObject extends ZooPC {


    int id;

    int parent_id;

    String name;

    String email;

    String phone;


    public ContactObject(){



    }


    public int getId() {
       zooActivateRead();
        return id;
    }

    public void setId(int id) {
     zooActivateWrite();
        this.id = id;
    }

    public int getParent_id() {
      zooActivateRead();
        return parent_id;
    }

    public void setParent_id(int parent_id) {
      zooActivateWrite();
        this.parent_id = parent_id;
    }

    public String getName() {
       zooActivateRead();
        return name;
    }

    public void setName(String name) {
       zooActivateWrite();
        this.name = name;
    }

    public String getEmail() {
      zooActivateRead();
        return email;
    }

    public void setEmail(String email) {
      zooActivateWrite();
        this.email = email;
    }

    public String getPhone() {
    zooActivateRead();
        return phone;
    }

    public void setPhone(String phone) {
    zooActivateWrite();
        this.phone = phone;
    }
}
