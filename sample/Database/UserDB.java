package sample.Database;


import org.zoodb.internal.server.DiskAccessOneFile;
import org.zoodb.jdo.ZooJdoHelper;
import org.zoodb.tools.ZooHelper;
import sample.Main;
import sample.ObjectSizeCalculator;

import javax.jdo.Extent;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;

public class UserDB {

    private static final String DB_FILE_path = Paths.get("").toAbsolutePath().toString()+"\\UserDB";


    private PersistenceManager pm;

    public Extent<MyUser> Users;


    public void CreateDb() {
        SetUp();

        if(!ZooJdoHelper.dbExists(DB_FILE_path))
        ZooJdoHelper.openOrCreateDB(DB_FILE_path);

    }

public void SetUp(){

        DiskAccessOneFile.allowReadConcurrency(true);
    }

    private static void closeDB(PersistenceManager pm) {
        if (pm.currentTransaction().isActive()) {
            pm.currentTransaction().rollback();
        }
        pm.close();
        pm.getPersistenceManagerFactory().close();
    }




    public  void DeleteUser(int id) {

        SetUp();

        pm = ZooJdoHelper.openDB(DB_FILE_path);
        pm.currentTransaction().begin();
        pm.setMultithreaded(true);
        Query q = pm.newQuery(MyUser.class, "ID == " + id);
        Collection<MyUser> params = (Collection<MyUser>)q.execute();

        for(MyUser d : params)
        {
            pm.deletePersistent(d);
        }

        pm.currentTransaction().commit();
        closeDB(pm);
        pm = null;

    }

    public void EditUser(int EditId, String NewText,String ClmName){

        SetUp();

        String m = Integer.toString(EditId);

            pm = ZooJdoHelper.openDB(DB_FILE_path);
            pm.currentTransaction().begin();
            pm.setMultithreaded(true);
            ArrayList<MyUser> u = new ArrayList<>();
            //using Java method in query>
            Query q = pm.newQuery(MyUser.class, "ID == " + m);

            Collection<MyUser> params = (Collection<MyUser>)q.execute();



          if(Main.Hebrew==true){


              for(MyUser d : params) {
                  if (ClmName.equals("שם")){
                      if(ObjectSizeCalculator.getObjectSize(NewText)+ObjectSizeCalculator.getObjectSize(d.getTitle())<=450)
                          d.setName(NewText);
                  }

                  else if (ClmName.equals("תפקיד")) {
                      if(ObjectSizeCalculator.getObjectSize(NewText)+ObjectSizeCalculator.getObjectSize(d.getName())<=450)
                          d.setTitle(NewText);
                  }
              }

          }else{


              for(MyUser d : params) {
                  if (ClmName.equals("Name")){
                      if(ObjectSizeCalculator.getObjectSize(NewText)+ObjectSizeCalculator.getObjectSize(d.getTitle())<=450)
                          d.setName(NewText);
                  }

                  else if (ClmName.equals("Title")) {
                      if(ObjectSizeCalculator.getObjectSize(NewText)+ObjectSizeCalculator.getObjectSize(d.getName())<=450)
                          d.setTitle(NewText);
                  }
              }

          }

            pm.currentTransaction().commit();
            closeDB(pm);
            pm = null;






                }


    public void insertUser(MyUser user) {

        SetUp();

        long i = ObjectSizeCalculator.getObjectSize(user);

        if(ObjectSizeCalculator.getObjectSize(user)<=512) {
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



    public ArrayList<MyUser> queryForUsers() {

        SetUp();


        ArrayList<MyUser> users = new ArrayList<>();


        pm = ZooJdoHelper.openDB(DB_FILE_path);
        pm.currentTransaction().setNontransactionalRead(true);
        pm.setMultithreaded(true);


         Users = pm.getExtent(MyUser.class);
        System.out.println(">> Query for People instances returned results:");
        for (MyUser user : Users) {
            users.add(user);
        }

        closeDB(pm);
        pm = null;



        return users;

    }

public void deleteDB(){


        ZooHelper.removeDb(DB_FILE_path);


}


    public int FindHigestID() {

        SetUp();


        pm = ZooJdoHelper.openDB(DB_FILE_path);
        pm.currentTransaction().setNontransactionalRead(true);
        pm.setMultithreaded(true);

        Extent<MyUser> courses = pm.getExtent(MyUser.class);
        MyUser maxID = null;
        int max = -1;
        for (MyUser c: courses) {
            if (c.getID() > max) {
                max = c.getID();
                maxID = c;
            }
        }

        closeDB(pm);
        pm = null;

        return max+1;
    }

}
