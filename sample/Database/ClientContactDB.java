package sample.Database;

import org.zoodb.internal.server.DiskAccessOneFile;
import org.zoodb.jdo.ZooJdoHelper;
import sample.Main;
import sample.ObjectSizeCalculator;
import sample.TestPojos.ClientObject;
import sample.TestPojos.ContactObject;

import javax.jdo.Extent;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;

public class ClientContactDB {


    private PersistenceManager pm;

    private static final String DB_FILE_path = Paths.get("").toAbsolutePath().toString()+"\\ClientDB";




    public int FindHigestID() {
        SetUp();

        pm = ZooJdoHelper.openDB(DB_FILE_path);
        pm.currentTransaction().setNontransactionalRead(true);
        pm.setMultithreaded(true);

        Extent<ClientObject> courses = pm.getExtent(ClientObject.class);
        ClientObject maxID = null;
        int max = -1;
        for (ClientObject c: courses) {
            if (c.getId()> max) {
                max = c.getId();
                maxID = c;
            }
        }

        closeDB(pm);
        pm = null;

        return max+1;
    }



    public  void DeleteUser(int id) {

        SetUp();

        pm = ZooJdoHelper.openDB(DB_FILE_path);
        pm.currentTransaction().begin();
        pm.setMultithreaded(true);

        Query q = pm.newQuery(ClientObject.class, "id == " + id);
        Collection<ClientObject> params = (Collection<ClientObject>)q.execute();

        for(ClientObject d : params)
        {
            pm.deletePersistent(d);
        }

        pm.currentTransaction().commit();
        closeDB(pm);
        pm = null;

    }



    public ArrayList<ContactObject> GetListByID(int id){
        SetUp();

        ArrayList<ContactObject> u = new ArrayList<>();
        //using Java method in query>
        PersistenceManager pm = ZooJdoHelper.openDB(DB_FILE_path);

        pm.currentTransaction().setNontransactionalRead(true);
        pm.setMultithreaded(true);

        Query q = pm.newQuery(ContactObject.class, "parent_id == " + Integer.toString(id));

        Collection<ContactObject> params = (Collection<ContactObject>)q.execute();

        for(ContactObject param:params){


            u.add(param);


        }

        closeDB(pm);
        pm = null;

        return u;
    }



    public void EditUser(int EditId, String NewText,String ClmName){


        SetUp();


            pm = ZooJdoHelper.openDB(DB_FILE_path);
            pm.currentTransaction().begin();
            pm.setMultithreaded(true);


            Query q = pm.newQuery(ClientObject.class, "id == " + EditId);

            Collection<ClientObject> params = (Collection<ClientObject>)q.execute();

            if(Main.Hebrew==true){

                for(ClientObject d : params) {
                    if (ClmName.equals("לקוח"))
                    {
                        if(ObjectSizeCalculator.getObjectSize(NewText)+ObjectSizeCalculator.getObjectSize(d.getAdress())<=450)
                            d.setCompanyName(NewText);

                    }

                    else if (ClmName.equals("כתובת")) {

                        if(ObjectSizeCalculator.getObjectSize(NewText)+ObjectSizeCalculator.getObjectSize(d.getCompanyName())<=450)

                            d.setAdress(NewText);
                    }
                }

            }else{


                for(ClientObject d : params) {
                    if (ClmName.equals("Company"))
                    {
                        if(ObjectSizeCalculator.getObjectSize(NewText)+ObjectSizeCalculator.getObjectSize(d.getAdress())<=450)
                            d.setCompanyName(NewText);

                    }

                    else if (ClmName.equals("Adress")) {

                        if(ObjectSizeCalculator.getObjectSize(NewText)+ObjectSizeCalculator.getObjectSize(d.getCompanyName())<=450)

                            d.setAdress(NewText);
                    }
                }
            }



            pm.currentTransaction().commit();
            closeDB(pm);
            pm = null;






    }







    public void insertClientContact(ClientObject client) {

        SetUp();

        if(ObjectSizeCalculator.getObjectSize(client)<500){

            PersistenceManager pm = ZooJdoHelper.openDB(DB_FILE_path);
            pm.currentTransaction().begin();
            pm.setMultithreaded(true);

            pm.makePersistent(client);

            pm.currentTransaction().commit();
            closeDB(pm);
            pm = null;

        }




    }





    public ArrayList<ClientObject> queryForUsers() {

        ArrayList<ClientObject> Clients = new ArrayList<>();
        ArrayList<ContactObject> Contacts = new ArrayList<>();

        SetUp();

        pm = ZooJdoHelper.openDB(DB_FILE_path);
        pm.currentTransaction().setNontransactionalRead(true);
        pm.setMultithreaded(true);




              Extent<ClientObject> E = pm.getExtent(ClientObject.class);
              for (ClientObject client : E) {
                  Clients.add(client);
              }

        closeDB(pm);
        pm = null;

              return Clients;


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

    public void CreateDb() {
        if(!ZooJdoHelper.dbExists(DB_FILE_path))
            ZooJdoHelper.openOrCreateDB(DB_FILE_path);

    }

}
