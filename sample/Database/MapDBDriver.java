package sample.Database;

import org.mapdb.*;
import org.mapdb.serializer.SerializerArrayTuple;
import sample.Main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.file.Paths;
import java.util.*;

public class MapDBDriver {

    DB db;

    BTreeMap<Integer,Object[]> map;

    public int addChildParam(int parentId, String text ){

       int key = map.lastKey()+1;
        map.put(key,new Object[]{
                parentId,text});
        db.commit();

        NitStandardParam p = new NitStandardParam();
        p.setId(key);
        p.setParent_id(parentId);
        p.setName(text);

        Main.LoadedToMemory.add(p);

        return key;
    }


    public void addEnteryToDB(int id,int parentId, String text ){


        map.put(id,new Object[]{
                parentId,text});
        System.out.printf("Commited-"+id+"\n");
    }



    public void OpenDB(){


         db = DBMaker.fileDB(Paths.get("").toAbsolutePath().toString()+"\\MapStandardDB").fileLockDisable().fileMmapEnableIfSupported().transactionEnable().make();
   //   Map<String,Object> t =   db.getAll();
         map = db.treeMap("Standards").valueSerializer(new SerializerArrayTuple(Serializer.INTEGER, Serializer.STRING))
                .keySerializer(Serializer.INTEGER).createOrOpen();
        System.out.printf("Opened DB");

    }

    public void OpenReadOnlyDB(){

        File f = new File(Paths.get("").toAbsolutePath().toString()+"\\MapStandardDB");

        if(f.exists()){

            db = DBMaker.fileDB(Paths.get("").toAbsolutePath().toString()+"\\MapStandardDB").fileLockDisable().fileMmapEnableIfSupported().readOnly().make();
            map = db.treeMap("Standards").valueSerializer(new SerializerArrayTuple(Serializer.INTEGER, Serializer.STRING))
                    .keySerializer(Serializer.INTEGER).createOrOpen();
        }
       else
        {  db = DBMaker.fileDB(Paths.get("").toAbsolutePath().toString()+"\\MapStandardDB").fileLockDisable().fileMmapEnableIfSupported().transactionEnable().make();

        //   Map<String,Object> t =   db.getAll();
        map = db.treeMap("Standards").valueSerializer(new SerializerArrayTuple(Serializer.INTEGER, Serializer.STRING))
                .keySerializer(Serializer.INTEGER).createOrOpen();}
        System.out.printf("Opened Read Only DB");

    }



    public void DeleteDB() {

        File myObj = new File(Paths.get("").toAbsolutePath().toString() + "\\MapStandardDB");

        if (myObj.exists())
        {

            if(myObj.delete())
            {
                System.out.println("File deleted successfully");
            }
            else
            {
                System.out.println("Failed to delete the file");
            }

        }



        System.out.printf("Deleted DB");

    }



    public void EditParam(int EditId, String Newtext){

        //I need to Change the memory data as Well!!!

        Object[] j;
        if(map.containsKey(EditId)){
         j = map.get(EditId);
         j[1] = Newtext;
         map.replace(EditId,j);
         db.commit();
        }

        MemoryHandler.EditMemory(EditId,Newtext);


    }






    public void deleteItself(int id){

        if(map.containsKey(id)){
            map.remove(id);
            db.commit();
        }

    }

    public void recursiveDelete(int Id){

      ArrayList<NitStandardParam> n = getAll();
      ArrayList<Integer> m = FindList(Id,n,new ArrayList<>());

      for(int i =0;i<m.size();i++)
      {
          if(map.containsKey(m.get(i)))
              map.remove(m.get(i));
      }


     db.commit();

        System.out.printf("");


    }





    public ArrayList<NitStandardParam> getAll(){



        ArrayList<NitStandardParam> j = new ArrayList<>();

        Set <Map.Entry<Integer,Object[]>> k =  map.getEntries();


        try{

            for(Map.Entry m:k){

                   Object[] g = (Object[])m.getValue();

                   NitStandardParam p = new NitStandardParam();
                   p.setParent_id((int)g[0]);
                   p.setId((int)m.getKey());
                   p.setName(g[1].toString());

                   j.add(p);

            }

        }catch(Exception e){

            db.close();

            File myObj = new File(Paths.get("").toAbsolutePath().toString()+"\\MapStandardDB");
            if (myObj.delete()) {
                System.out.println("Deleted the file: " + myObj.getName());
            } else {
                System.out.println("Failed to delete the file.");
            }

         OpenDB();


            for(int i=0;i<j.size();i++)
            addEnteryToDB(j.get(i).getId(),j.get(i).getParent_id(),j.get(i).getName());

            db.commit();

            OpenReadOnlyDB();

            System.out.printf("Restored DB");
          //  if(map.containsKey(j.get(j.size()-1).getId()))
        //    map.remove(j.get(j.size()-1).getId());
           db.close();

/*
         for(int i=0;i<j.size();i++)

         {
             map.put(j.get(i).getId(),new Object[]{
                     j.get(i).getParent_id(),j.get(i).getName()});
         }


         db.commit();


 */
        }

return j;
    }

    public void CloseDB(){

        db.close();
    }

public ArrayList<Integer> FindList(int id,ArrayList<NitStandardParam> k, ArrayList<Integer> r){

        r.add(id);

        for(int j=0;j<r.size();j++){
            for(int i=0;i<k.size();i++){
                if(r.get(j)==k.get(i).getParent_id()){
                    r.add(k.get(i).getId());
                    k.remove(i);
                    i=0;
                }
            }
        }



return r;
    }


    public NitStandardParam FindParam(int ParentID){

       ArrayList<NitStandardParam> p  = getAll();

        for(int i =0;i<p.size();i++){

            if(ParentID == p.get(i).getId())
                return p.get(i);


        }

        return null;
    }






}
