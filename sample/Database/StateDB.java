package sample.Database;

import org.zoodb.internal.server.DiskAccessOneFile;
import org.zoodb.jdo.ZooJdoHelper;
import sample.ObjectSizeCalculator;
import sample.OtherPojos.StateObj;

import javax.jdo.Extent;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;

public class StateDB {

    private static final String DB_FILE_path = Paths.get("").toAbsolutePath().toString()+"\\StateDB";


    private PersistenceManager pm;

    public void SetUp(){

        DiskAccessOneFile.allowReadConcurrency(true);
    }

    public void DeleteState(int id) {

        SetUp();


        pm = ZooJdoHelper.openDB(DB_FILE_path);
        pm.currentTransaction().begin();
        pm.setMultithreaded(true);
        Query q = pm.newQuery(StateObj.class, "id == " + id);
        Collection<StateObj> params = (Collection<StateObj>)q.execute();

        for(StateObj d : params)
        {
            pm.deletePersistent(d);
        }

        pm.currentTransaction().commit();
        closeDB(pm);
        pm = null;

    }






    public ArrayList<StateObj> queryForStates() {

        ArrayList<StateObj> StateList = new ArrayList<>();
        SetUp();


        pm = ZooJdoHelper.openDB(DB_FILE_path);
        pm.currentTransaction().setNontransactionalRead(true);
        pm.setMultithreaded(true);


        Extent<StateObj> States = pm.getExtent(StateObj.class);
        System.out.println(">> Query for People instances returned results:");


        for (StateObj state : States) {
            StateList.add(state);
        }

        closeDB(pm);
        pm = null;



        return StateList;

    }

    public void CreateDb() {
        if(!ZooJdoHelper.dbExists(DB_FILE_path))
            ZooJdoHelper.openOrCreateDB(DB_FILE_path);

    }

    public void insertState(StateObj user) {

        SetUp();

        if(ObjectSizeCalculator.getObjectSize(user)<500) {
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


        Extent<StateObj> courses = pm.getExtent(StateObj.class);
        int max = -1;
        for (StateObj c: courses) {
            if (c.getId() > max) {
                max = c.getId();
            }
        }

        closeDB(pm);
        pm = null;

        return max+1;
    }
}
