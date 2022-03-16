package sample.Database;

import org.zoodb.internal.server.DiskAccessOneFile;
import org.zoodb.jdo.ZooJdoHelper;
import sample.ObjectSizeCalculator;
import sample.TestPojos.SignatureTableModel;

import javax.jdo.Extent;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;

public class ReportDB {

    private PersistenceManager pm;

    public Extent<SignatureTableModel> Users;

    private static final String DB_FILE_path = Paths.get("").toAbsolutePath().toString()+"\\ReportDB";


    public void insertUser(SignatureTableModel Report) {
        SetUp();

        Report.setId(FindHigestID());




            PersistenceManager pm = ZooJdoHelper.openDB(DB_FILE_path);
            pm.currentTransaction().begin();
            pm.setMultithreaded(true);
            //Storing the User In the Database
            pm.makePersistent(Report);



            pm.currentTransaction().commit();
            closeDB(pm);
            pm = null;



    }


    public ArrayList<SignatureTableModel> queryForReports() {

        ArrayList<SignatureTableModel> users = new ArrayList<>();

        SetUp();

        pm = ZooJdoHelper.openDB(DB_FILE_path);
        pm.currentTransaction().setNontransactionalRead(true);
        pm.setMultithreaded(true);


        Users = pm.getExtent(SignatureTableModel.class);
        System.out.println(">> Query for People instances returned results:");
        for (SignatureTableModel user : Users) {
            users.add(user);
        }

        closeDB(pm);
        pm = null;



        return users;

    }



    public  void DeleteUser(int id) {

        SetUp();


        pm = ZooJdoHelper.openDB(DB_FILE_path);
        pm.currentTransaction().begin();
        pm.setMultithreaded(true);
        Query q = pm.newQuery(SignatureTableModel.class, "id == " + id);
        Collection<SignatureTableModel> params = (Collection<SignatureTableModel>)q.execute();

        for(SignatureTableModel d : params)
        {
            pm.deletePersistent(d);
        }

        pm.currentTransaction().commit();
        closeDB(pm);
        pm = null;

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


    public int FindLowestID(){

        int min = FindHigestID()-1;

        SetUp();

        pm = ZooJdoHelper.openDB(DB_FILE_path);
        pm.currentTransaction().setNontransactionalRead(true);
        pm.setMultithreaded(true);

        Extent<SignatureTableModel> courses = pm.getExtent(SignatureTableModel.class);

        for (SignatureTableModel c: courses) {
            if (c.getId() < min) {
                min = c.getId();
            }
        }

        closeDB(pm);
        pm = null;

        return min;
    }


    public void SetUp(){

        DiskAccessOneFile.allowReadConcurrency(true);
    }


    public int FindHigestID() {
        SetUp();

        pm = ZooJdoHelper.openDB(DB_FILE_path);
        pm.currentTransaction().setNontransactionalRead(true);
        pm.setMultithreaded(true);

        Extent<SignatureTableModel> courses = pm.getExtent(SignatureTableModel.class);
        SignatureTableModel maxID = null;
        int max = -1;
        for (SignatureTableModel c: courses) {
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
