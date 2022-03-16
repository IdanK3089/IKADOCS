package sample.Database;

import javafx.beans.property.SimpleStringProperty;
import org.dizitart.no2.objects.filters.ObjectFilters;
import sample.Main;
import sample.OtherPojos.MinMaxCellObject;
import sample.OtherPojos.MinMaxTableObj;
import sample.TestPojos.TableModel;

import java.util.*;

public class MemoryHandler {


    public static int FindParentId(int id){



        ArrayList<Integer> n= new ArrayList<>();

        for(NitStandardParam p:Main.LoadedToMemory){


            if(p.getId()==id)
                n.add(p.getParent_id());


        }


        return n.get(0);

    }



    public static int FindHigestID() {

        List<NitStandardParam> m = new ArrayList<>();

        int size = Main.LoadedToMemory.size();


// add elements
        Collections.sort(Main.LoadedToMemory, Comparator.comparingLong(NitStandardParam::getId));

    //    List<NitStandardParam>  g = StandardStore.find(ObjectFilters.gte("id",size)).toList();



        int max = Main.LoadedToMemory.get(size-1).getId()+1;

        return max;
    }

    public NitStandardParam getParameter(int id) {

        NitStandardParam m = null;

        for(NitStandardParam p:Main.LoadedToMemory){

            if(p.getId()==id){
                return p;
            }
        }

        return m;
    }

    public static ArrayList<Integer> findChildrenID(int id){

        ArrayList<Integer> n= new ArrayList<>();


        for(NitStandardParam p:Main.LoadedToMemory){

            if(p.getParent_id()==id){

                n.add(p.getId());
            }


        }


        return n;
    }


    public static boolean haveChildren(int id){

        ArrayList<TableModel> u = new ArrayList<>();
        //using Java method in query>

        for(NitStandardParam p:Main.LoadedToMemory){

            if(p.getParent_id()==id){
                u.add(new TableModel(p.getName(),p.getId()));


            }
        }


        if(u.isEmpty())
            return false;
        else
            return true;

    }


    public static ArrayList<NitStandardParam> GetListByID(int id){


        ArrayList<NitStandardParam> u = new ArrayList<>();
        //using Java method in query>

        for(NitStandardParam p:Main.LoadedToMemory){

            if(p.getParent_id()==id){
                u.add(p);

            }
        }

        return u;
    }

    public static List<TableModel> getNeededList(int NeededParentId){


        List<TableModel> u = new ArrayList<>();

        for(NitStandardParam p:Main.LoadedToMemory){

            if(p.getParent_id()==NeededParentId){

                u.add(new TableModel(p.getName(),p.getId()));


            }
        }

        return u;
    }


    public static List<MinMaxCellObject> getNeededParamList(int NeededParentId){


        List<MinMaxCellObject> u = new ArrayList<>();

        for(NitStandardParam p:Main.LoadedToMemory){

            if(p.getParent_id()==NeededParentId){

                u.add(new MinMaxCellObject(new SimpleStringProperty(p.getName()),p.getParent_id(),p.getId()));


            }
        }

        return u;
    }


    public static MinMaxCellObject findMinMax(int id){


        MinMaxCellObject u = null;

        for(NitStandardParam p:Main.LoadedToMemory){

            if(p.getParent_id()==id){
                 u = new MinMaxCellObject(new SimpleStringProperty(p.getName()),p.getParent_id(),p.getId());
            }

        }

        return u;

    }


    public static void EditMemory(int id,String text){



        for(NitStandardParam p:Main.LoadedToMemory){

            if(p.getId()==id){
               p.setName(text);
            }

        }

    }



}
