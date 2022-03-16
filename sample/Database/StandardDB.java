package sample.Database;



import org.zoodb.internal.server.DiskAccessOneFile;
import org.zoodb.jdo.ZooJdoHelper;
import org.zoodb.tools.ZooHelper;
import sample.ObjectSizeCalculator;
import sample.TestPojos.SignatureTableModel;
import sample.TestPojos.TableModel;
import sample.Tree.StandardParam;

import javax.jdo.Extent;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Properties;


public class StandardDB {

    private static final String DB_FILE_path = Paths.get("").toAbsolutePath().toString()+"\\IKADB";


    private PersistenceManager pm;


    public void SetUp(){

        DiskAccessOneFile.allowReadConcurrency(true);
    }

   public StandardDB(){


         CreateDb();

    }


    public void deleteDB(){

       ZooHelper.removeDb(DB_FILE_path);


    }

    public TableModel findMinMax(int id){
        SetUp();

       TableModel u = new TableModel();

        PersistenceManager pm = ZooJdoHelper.openDB(DB_FILE_path);
        pm.currentTransaction().setNontransactionalRead(true);
        pm.setMultithreaded(true);
        DiskAccessOneFile.allowReadConcurrency(true);


        Query q = pm.newQuery(StandardParam.class, "id == " + Integer.toString(id));

        Collection<StandardParam> params = (Collection<StandardParam>)q.execute();


        for(StandardParam d : params)
        {
            u.setId(d.getId());
            u.setPropertyName(d.getName());
        }

        closeDB(pm);
        pm = null;

        return u;

    }


    public List<TableModel> getNeededList(int NeededParentId){
        SetUp();

        List<TableModel> u = new ArrayList<>();
        PersistenceManager pm = ZooJdoHelper.openDB(DB_FILE_path);


        pm.setMultithreaded(true);
        pm.currentTransaction().setNontransactionalRead(true);
        DiskAccessOneFile.allowReadConcurrency(true);


        Query q = pm.newQuery(StandardParam.class, "parent_id == " + Integer.toString(NeededParentId));

        Collection<StandardParam> params = (Collection<StandardParam>)q.execute();


        for(StandardParam d : new ArrayList<>(params))
        {
            u.add(new TableModel(d.getName(),d.getId()));
        }

        closeDB(pm);
        pm = null;

       return u;
    }


public void addMinMax(int parentID){

    SetUp();

    addChildParam(parentID,"0");
    addChildParam(parentID,"0");



}

    public void addNewStandardParam( String text ){

         StandardParam standard = new StandardParam();

         int last = FindHigestID();

          standard.setId(last+1);
          standard.setName(text);

        if(ObjectSizeCalculator.getObjectSize(text)<500) {

            PersistenceManager pm = ZooJdoHelper.openDB(DB_FILE_path);
            pm.currentTransaction().begin();
            pm.setMultithreaded(true);

            //Storing the User In the Database
            pm.makePersistent(standard);



            pm.currentTransaction().commit();
            closeDB(pm);
            pm = null;

        }





     }




    public int addChildParam(int parentId, String text ){
        SetUp();

        StandardParam standard = new StandardParam();
        int last = FindHigestID();

        standard.setId(last+1);
        standard.setName(text);
        standard.setParent_id(parentId);

        PersistenceManager pm = ZooJdoHelper.openDB(DB_FILE_path);
        pm.currentTransaction().begin();
        pm.setMultithreaded(true);

        //Storing the User In the Database
        pm.makePersistent(standard);



        pm.currentTransaction().commit();
        closeDB(pm);
        pm = null;

return last+1;


    }

    public ArrayList<StandardParam> GetListByID(int id){
        SetUp();

        ArrayList<StandardParam> u = new ArrayList<>();
        //using Java method in query>
        PersistenceManager pm = ZooJdoHelper.openDB(DB_FILE_path);

        pm.currentTransaction().setNontransactionalRead(true);
        pm.setMultithreaded(true);


        Query q = pm.newQuery(StandardParam.class, "parent_id == " + Integer.toString(id));

        Collection<StandardParam> params = (Collection<StandardParam>)q.execute();

           for(StandardParam param:params){


               u.add(param);


           }

        closeDB(pm);
        pm = null;

           return u;
    }


    public void EditParam(int EditId, String Newtext){

        SetUp();

         String m = Integer.toString(EditId);


            pm = ZooJdoHelper.openDB(DB_FILE_path);
            pm.currentTransaction().begin();
            pm.setMultithreaded(true);

            ArrayList<StandardParam> u = new ArrayList<>();
            //using Java method in query>
            Query q = pm.newQuery(StandardParam.class, "id == " + m);

            Collection<StandardParam> params = (Collection<StandardParam>)q.execute();

            for(StandardParam d : params)
            {

                if(ObjectSizeCalculator.getObjectSize(Newtext)<=450)
                    d.setName(Newtext);
            }

            pm.currentTransaction().commit();
            closeDB(pm);
            pm = null;






    }

    public boolean haveChildren(int id){

        SetUp();

        ArrayList<TableModel> u = new ArrayList<>();
        //using Java method in query>

        PersistenceManager pm = ZooJdoHelper.openDB(DB_FILE_path);
        pm.currentTransaction().setNontransactionalRead(true);
        pm.setMultithreaded(true);

        Query q = pm.newQuery(StandardParam.class, "parent_id == " + Integer.toString(id));

        Collection<StandardParam> params = (Collection<StandardParam>)q.execute();


        for(StandardParam d : params)
        {

            u.add(new TableModel(d.getName(),d.getId()));
        }

        closeDB(pm);
        pm = null;

         if(u.isEmpty())
             return false;
         else
             return true;

    }

public ArrayList<Integer> findChildrenID(int id){

    ArrayList<Integer> n= new ArrayList<>();

    SetUp();

    PersistenceManager pm = ZooJdoHelper.openDB(DB_FILE_path);
    pm.currentTransaction().setNontransactionalRead(true);
    pm.setMultithreaded(true);

    Query q = pm.newQuery(StandardParam.class, "parent_id == " + Integer.toString(id));

    Collection<StandardParam> params = (Collection<StandardParam>)q.execute();


    for(StandardParam d : params)
    {

        n.add(d.getId());
    }

    closeDB(pm);
    pm = null;
    return n;
}


    public void DeleteParam(int DeleteId){

        SetUp();


        String m = Integer.toString(DeleteId);

        pm = ZooJdoHelper.openDB(DB_FILE_path);
        pm.currentTransaction().begin();
        pm.setMultithreaded(true);

        recursiveDelete(DeleteId);



        pm.currentTransaction().commit();

        closeDB(pm);
        pm = null;




    }


    public void deleteItself(int id){

        SetUp();


        pm = ZooJdoHelper.openDB(DB_FILE_path);
        pm.currentTransaction().begin();
        pm.setMultithreaded(true);
        Query q = pm.newQuery(StandardParam.class, "id == " + id);
        Collection<StandardParam> params = (Collection<StandardParam>)q.execute();

        for(StandardParam d : params)
        {
            pm.deletePersistent(d);
        }

        pm.currentTransaction().commit();
        closeDB(pm);
        pm = null;

    }

    public void recursiveDelete(int Id){


        pm.setMultithreaded(true);

        //using Java method in query>
        Query q = pm.newQuery(StandardParam.class, "parent_id == " + Id);

        Collection<StandardParam> params = (Collection<StandardParam>)q.execute();

        if(params.isEmpty()==true) {
            return;
        }
        for(StandardParam d : params)
        {
            recursiveDelete(d.getId());
            pm.deletePersistent(d);
        }

    }

    public ArrayList<StandardParam> queryForParams() {

        ArrayList<StandardParam> users = new ArrayList<>();

        SetUp();

        pm = ZooJdoHelper.openDB(DB_FILE_path);
        pm.currentTransaction().setNontransactionalRead(true);
        pm.setMultithreaded(true);


         Extent<StandardParam> Users;
         Users = pm.getExtent(StandardParam.class);
        System.out.println(">> Query for People instances returned results:");
        for (StandardParam user : Users) {
            users.add(user);
        }

        closeDB(pm);
        pm = null;



        return users;

    }

    public ArrayList<StandardParam> AllSTDSpecificData(int id) {

        SetUp();

        ArrayList<StandardParam> u = new ArrayList<>();
        //using Java method in query>
        PersistenceManager pm = ZooJdoHelper.openDB(DB_FILE_path);

        pm.currentTransaction().setNontransactionalRead(true);
        pm.setMultithreaded(true);


        Query q = pm.newQuery(StandardParam.class, "parent_id >" + 0);

        Collection<StandardParam> params = (Collection<StandardParam>)q.execute();

        for(StandardParam param:params){


            u.add(param);


        }

        closeDB(pm);
        pm = null;

        return u;
    }



public int FindParentId(int id){
    int NeededID = -1;
    SetUp();


    pm = ZooJdoHelper.openDB(DB_FILE_path);
    pm.currentTransaction().setNontransactionalRead(true);
    pm.setMultithreaded(true);
    DiskAccessOneFile.allowReadConcurrency(true);


    Query q = pm.newQuery(StandardParam.class, "id == " + id);
    Collection<StandardParam> params = (Collection<StandardParam>)q.execute();

    for(StandardParam d : params)
    {
        NeededID = d.getParent_id();
    }

    closeDB(pm);
    pm = null;


    return NeededID;

}



    public void CreateDb() {


        SetUp();


     if(!ZooHelper.getDataStoreManager().dbExists(DB_FILE_path))
     {

         ZooHelper.getDataStoreManager().createDb(DB_FILE_path);




         PersistenceManager pm = ZooJdoHelper.openDB(DB_FILE_path);
         pm.currentTransaction().begin();
         pm.setMultithreaded(true);
         DiskAccessOneFile.allowReadConcurrency(true);
         ZooJdoHelper.createIndex(pm, StandardParam.class, "id", true);

         StandardParam standard = new StandardParam();
         standard.setName("Base");
         standard.setId(0);
         standard.setParent_id(-1);

         pm.makePersistent(standard);
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
        DiskAccessOneFile.allowReadConcurrency(true);

        Extent<StandardParam> courses = pm.getExtent(StandardParam.class);
        StandardParam maxID = null;
        int max = -1;
        for (StandardParam c: courses) {
            if (c.getId() > max) {
                max = c.getId();
                maxID = c;
            }
        }

        closeDB(pm);
        pm = null;

        return max;
    }


    }



