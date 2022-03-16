package sample.Database;

import org.zoodb.internal.server.DiskAccessOneFile;
import org.zoodb.jdo.ZooJdoHelper;
import sample.Main;
import sample.ObjectSizeCalculator;
import sample.TestPojos.ContactObject;

import javax.jdo.Extent;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;

public class ContactDB {


    private PersistenceManager pm;

    private static final String DB_FILE_path = Paths.get("").toAbsolutePath().toString()+"\\ClientDB";




    public int FindHigestID() {

        SetUp();


        pm = ZooJdoHelper.openDB(DB_FILE_path);
        pm.currentTransaction().setNontransactionalRead(true);
        pm.setMultithreaded(true);

        Extent<ContactObject> courses = pm.getExtent(ContactObject.class);
        ContactObject maxID = null;
        int max = -1;
        for (ContactObject c: courses) {
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

        Query q = pm.newQuery(ContactObject.class, "id == " + id);
        Collection<ContactObject> params = (Collection<ContactObject>)q.execute();

        for(ContactObject d : params)
        {
            pm.deletePersistent(d);
        }

        pm.currentTransaction().commit();
        closeDB(pm);
        pm = null;

    }






    public void EditUser(int EditId, String NewText,String ClmName){


        SetUp();

        if(ObjectSizeCalculator.getObjectSize(NewText)<500){
            pm = ZooJdoHelper.openDB(DB_FILE_path);
            pm.currentTransaction().begin();
            pm.setMultithreaded(true);

            Query q = pm.newQuery(ContactObject.class, "id == " + EditId);

            Collection<ContactObject> params = (Collection<ContactObject>)q.execute();

            if(Main.Hebrew==true){

                for(ContactObject d : params) {
                    if (ClmName.equals("שם")){
                        if(ObjectSizeCalculator.getObjectSize(NewText)+ObjectSizeCalculator.getObjectSize(d.getEmail())+
                                ObjectSizeCalculator.getObjectSize(d.getPhone())<=450)
                            d.setName(NewText);

                    }
                    else if (ClmName.equals("אימייל")) {

                        if(ObjectSizeCalculator.getObjectSize(NewText)+ObjectSizeCalculator.getObjectSize(d.getName())+
                                ObjectSizeCalculator.getObjectSize(d.getPhone())<=450)
                            d.setEmail(NewText);
                    } else if(ClmName.equals("טלפון")){
                        if(ObjectSizeCalculator.getObjectSize(NewText)+ObjectSizeCalculator.getObjectSize(d.getName())+
                                ObjectSizeCalculator.getObjectSize(d.getEmail())<=450)
                            d.setPhone(NewText);

                    }






                }

            } else{


                for(ContactObject d : params) {
                    if (ClmName.equals("Name")){
                        if(ObjectSizeCalculator.getObjectSize(NewText)+ObjectSizeCalculator.getObjectSize(d.getEmail())+
                                ObjectSizeCalculator.getObjectSize(d.getPhone())<=450)
                            d.setName(NewText);

                    }
                    else if (ClmName.equals("Email")) {

                        if(ObjectSizeCalculator.getObjectSize(NewText)+ObjectSizeCalculator.getObjectSize(d.getName())+
                                ObjectSizeCalculator.getObjectSize(d.getPhone())<=450)
                            d.setEmail(NewText);
                    } else if(ClmName.equals("Phone")){
                        if(ObjectSizeCalculator.getObjectSize(NewText)+ObjectSizeCalculator.getObjectSize(d.getName())+
                                ObjectSizeCalculator.getObjectSize(d.getEmail())<=450)
                            d.setPhone(NewText);

                    }






                }
            }




            pm.currentTransaction().commit();
            closeDB(pm);
            pm = null;



        }




    }


    public void insertClientContact(ContactObject contact) {

        SetUp();

        if(ObjectSizeCalculator.getObjectSize(contact)<500){

            PersistenceManager pm = ZooJdoHelper.openDB(DB_FILE_path);
            pm.currentTransaction().begin();
            pm.setMultithreaded(true);

            pm.makePersistent(contact);

            pm.currentTransaction().commit();
            closeDB(pm);
            pm = null;

        }



    }

    public void SetUp(){

        DiskAccessOneFile.allowReadConcurrency(true);
    }
    public ArrayList<ContactObject> queryForUsers() {

        ArrayList<ContactObject> Clients = new ArrayList<>();
        ArrayList<ContactObject> Contacts = new ArrayList<>();

        SetUp();


        pm = ZooJdoHelper.openDB(DB_FILE_path);
        pm.currentTransaction().setNontransactionalRead(true);




        Extent<ContactObject> E = pm.getExtent(ContactObject.class);
        for (ContactObject ContactObject : E) {
            Clients.add(ContactObject);
        }

        closeDB(pm);
        pm = null;

        return Clients;


    }








    private static void closeDB(PersistenceManager pm) {
        if (pm.currentTransaction().isActive()) {
            pm.currentTransaction().rollback();
        }
        pm.close();
        pm.getPersistenceManagerFactory().close();
    }

    public void CreateDb() {
        SetUp();

        if(!ZooJdoHelper.dbExists(DB_FILE_path))
            ZooJdoHelper.openOrCreateDB(DB_FILE_path);

    }





}
