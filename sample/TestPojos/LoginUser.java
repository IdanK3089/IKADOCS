package sample.TestPojos;

import org.zoodb.api.impl.ZooPC;

public class LoginUser extends ZooPC {


    public int getId() {

      zooActivateRead();
        return id;
    }

    public void setId(int id) {
      zooActivateWrite();

        this.id = id;
    }

    int id;

    String UserName;

    String PassWord;

    String SignaturePath;

    boolean Approver = false;

    public boolean isApprover() {
        zooActivateRead();
        return Approver;
    }

    public void setApprover(boolean approver) {
      zooActivateWrite();
        Approver = approver;
    }

    public String getSignaturePath() {
       zooActivateRead();
        return SignaturePath;
    }

    public void setSignaturePath(String signaturePath) {
       zooActivateWrite();
        SignaturePath = signaturePath;
    }

    public LoginUser(){}


    public String getUserName() {
        zooActivateRead();
         return UserName;
    }

    public void setUserName(String userName) {
      zooActivateWrite();
        UserName = userName;
    }

    public String getPassWord() {
       zooActivateRead();
        return PassWord;
    }

    public void setPassWord(String passWord) {
     zooActivateWrite();
        PassWord = passWord;
    }
}
