package sample.Database;

import org.zoodb.internal.server.DiskAccessOneFile;
import org.zoodb.jdo.ZooJdoHelper;
import sample.ObjectSizeCalculator;
import sample.TestPojos.LoginUser;

import javax.jdo.Extent;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;

public class LoginDB {





    private static final String DB_FILE_path = Paths.get("").toAbsolutePath().toString()+"\\UserDB";

    PersistenceManager pm;

    public void CreateDb() {
        SetUp();

        if(!ZooJdoHelper.dbExists(DB_FILE_path))
            ZooJdoHelper.openOrCreateDB(DB_FILE_path);

    }



    private static void closeDB(PersistenceManager pm) {
        if (pm.currentTransaction().isActive()) {
            pm.currentTransaction().rollback();
        }
        pm.close();
        pm.getPersistenceManagerFactory().close();
    }

    public void SetUp(){

        DiskAccessOneFile.allowReadConcurrency(true);
    }

    public LoginUser LoadUser(String Pass,String Name){
        SetUp();

          LoginUser user = new LoginUser();

        Extent<LoginUser> list;



        pm = ZooJdoHelper.openDB(DB_FILE_path);
        pm.currentTransaction().setNontransactionalRead(true);
        pm.setMultithreaded(true);

        list =  pm.getExtent(LoginUser.class);


        for(LoginUser l: list){

        String dbUserName = l.getUserName();
        String dbPass = l.getPassWord();

            if(dbPass.equals(Pass)){
                   if(dbUserName.equals(Name))

                user.setSignaturePath(l.getSignaturePath());
                user.setApprover(l.isApprover());
                user.setUserName(l.getUserName());
                user.setId(l.getId());
                user.setPassWord(l.getPassWord());

            }


        }



        pm.close();



       return user;

    }

    public int getUserIdByNameAndPass(String Name,String Pass){

        SetUp();

          Extent<LoginUser> list;
       ArrayList<LoginUser>  Users = new ArrayList<>();

        int n=-1;



        pm = ZooJdoHelper.openDB(DB_FILE_path);
        pm.currentTransaction().setNontransactionalRead(true);
        pm.setMultithreaded(true);

        list =  pm.getExtent(LoginUser.class);


        for(LoginUser l: list){

            Users.add(l);
        }


        for (LoginUser user : Users) {

            if(user.getPassWord().equals(Pass)&&user.getUserName().equals(Name)){


                n=user.getId();
            }


        }

        closeDB(pm);
        pm = null;



        return n;

    }



    public int getUserIdByName(String Name){

        SetUp();

        Extent<LoginUser> list;
        ArrayList<LoginUser>  Users = new ArrayList<>();

        int n=-1;



        pm = ZooJdoHelper.openDB(DB_FILE_path);
        pm.currentTransaction().setNontransactionalRead(true);
        pm.setMultithreaded(true);

        list =  pm.getExtent(LoginUser.class);


        for(LoginUser l: list){

            Users.add(l);
        }



        if(Users!=null)
        for (LoginUser user : Users) {

            if(user.getUserName().equals(Name)){

                n=user.getId();
            }


        }

        closeDB(pm);
        pm = null;



        return n;

    }

    public  void DeleteUser(int id) {

        SetUp();

        pm = ZooJdoHelper.openDB(DB_FILE_path);
        pm.currentTransaction().begin();
        pm.setMultithreaded(true);

        Query q = pm.newQuery(LoginUser.class, "id == " + id);
        Collection<LoginUser> params = (Collection<LoginUser>)q.execute();


        for(LoginUser d : params)
        {
            pm.deletePersistent(d);
        }

        pm.currentTransaction().commit();
        closeDB(pm);
        pm = null;

    }

    public void EditUser(int EditId,String NewPassword){

        String m = Integer.toString(EditId);

        if(ObjectSizeCalculator.getObjectSize(NewPassword)<500) {

            pm = ZooJdoHelper.openDB(DB_FILE_path);
            pm.currentTransaction().begin();
            pm.setMultithreaded(true);
            ArrayList<LoginUser> u = new ArrayList<>();
            //using Java method in query>
            Query q = pm.newQuery(LoginUser.class, "id == " + m);

            Collection<LoginUser> params = (Collection<LoginUser>)q.execute();





            for(LoginUser d : params) {
                if(ObjectSizeCalculator.getObjectSize(NewPassword)+ObjectSizeCalculator.getObjectSize(d.getSignaturePath())
                        +ObjectSizeCalculator.getObjectSize(d.getUserName())<=450)

                d.setPassWord(NewPassword);

            }
            pm.currentTransaction().commit();
            closeDB(pm);
            pm = null;

        }



    }


    public void insertUser(LoginUser user) {

        int id = FindHigestID();


        SetUp();

        if(ObjectSizeCalculator.getObjectSize(user)<500) {

            user.setId(FindHigestID());

            PersistenceManager pm = ZooJdoHelper.openDB(DB_FILE_path);
            pm.currentTransaction().begin();
            pm.setMultithreaded(true);
            //Storing the User In the Database
            pm.makePersistent(user);



            pm.currentTransaction().commit();
            closeDB(pm);
            pm = null;

        }

    }

    public int FindHigestID() {


        pm = ZooJdoHelper.openDB(DB_FILE_path);
        pm.currentTransaction().setNontransactionalRead(true);
        pm.setMultithreaded(true);
        Extent<LoginUser> courses = pm.getExtent(LoginUser.class);
        LoginUser maxID = null;
        int max = -1;
        for (LoginUser c: courses) {
            if (c.getId() > max) {
                max = c.getId();
                maxID = c;
            }
        }

        closeDB(pm);
        pm = null;

        return max+1;
    }



}
