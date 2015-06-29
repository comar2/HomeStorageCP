package it.comar.admin.homestroragecp;

import java.util.ArrayList;
import java.util.List;

import it.comar.admin.homestroragecp.cassetto.DrawerItem;

/**
 * Created by admin on 15/06/2015.
 */
public class ConfigArmadio {
    private static int mNumCassetti = 19;

    public static int getNumCassetti(){
        return mNumCassetti;
    }
    public static List<DrawerItem> getDrawersList(){
        List<DrawerItem> drawersList = new ArrayList<DrawerItem>();

        for (int i = 1; i <= mNumCassetti; i++){
            drawersList.add(new DrawerItem(i,"Cassetto_"+ Integer.toString(i)));
        }


        return drawersList;
    }

    public static ArrayList<String> getDrawersNamesList(){
        ArrayList<String> drawersNamesList = new ArrayList<String>();

        for (int i = 1; i<= mNumCassetti; i++){
            drawersNamesList.add("Cassetto_"+ Integer.toString(i));
        }

        return drawersNamesList;
    }
}
