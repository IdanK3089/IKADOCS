package sample.Database;

import org.zoodb.internal.server.DiskAccessOneFile;
import org.zoodb.jdo.ZooJdoHelper;
import sample.ObjectSizeCalculator;
import sample.OtherPojos.HeaderObj;

import javax.jdo.Extent;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;

public class HeaderDB {

    private static final String DB_FILE_path = Paths.get("").toAbsolutePath().toString()+"\\HeaderDB";


    private PersistenceManager pm;



    public void DeleteHeader(int id) {

        SetUp();

        pm = ZooJdoHelper.openDB(DB_FILE_path);
        pm.currentTransaction().begin();
        pm.setMultithreaded(true);

        Query q = pm.newQuery(HeaderObj.class, "id == " + id);
        Collection<HeaderObj> params = (Collection<HeaderObj>)q.execute();

        for(HeaderObj d : params)
        {
            pm.deletePersistent(d);
        }

        pm.currentTransaction().commit();
        closeDB(pm);
        pm = null;

    }






    public ArrayList<HeaderObj> queryForHeaders() {

        ArrayList<HeaderObj> StateList = new ArrayList<>();
        SetUp();


        pm = ZooJdoHelper.openDB(DB_FILE_path);
        pm.currentTransaction().setNontransactionalRead(true);
        pm.setMultithreaded(true);


        Extent<HeaderObj> States = pm.getExtent(HeaderObj.class);
        System.out.println(">> Query for People instances returned results:");


        for (HeaderObj state : States) {
            StateList.add(state);
        }

        closeDB(pm);
        pm = null;



        return StateList;

    }

    public void insertHeader(HeaderObj user) {

        SetUp();

        if(ObjectSizeCalculator.getObjectSize(user)<500){

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


    public int FindHigestID() {

        SetUp();

        pm = ZooJdoHelper.openDB(DB_FILE_path);
        pm.currentTransaction().setNontransactionalRead(true);
        pm.setMultithreaded(true);
        Extent<HeaderObj> courses = pm.getExtent(HeaderObj.class);
        int max = -1;
        for (HeaderObj c: courses) {
            if (c.getId() > max) {
                max = c.getId();
            }
        }

        closeDB(pm);
        pm = null;

        return max+1;
    }


    public void SetUp(){

        DiskAccessOneFile.allowReadConcurrency(true);
    }



}
