package it.comar.admin.homestroragecp;

import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import it.comar.admin.homestroragecp.cassetto.DrawerItem;
import it.comar.admin.homestroragecp.database.DBManager;
import it.comar.admin.homestroragecp.database.DBStrings;

/**
 * Created by admin on 15/06/2015.
 */
public class ConfigArmadio {
    //private static int mNumCassetti = 19;

    public static int getNumCassetti(DBManager db){
        Cursor crs=db.query_cassetto();
        return crs.getColumnCount();
    }
    public static List<DrawerItem> getDrawersList(DBManager db){
        List<DrawerItem> drawersList = new ArrayList<DrawerItem>();

        Cursor crs=db.query_cassetto();
        crs.moveToFirst();
        for (;crs.getPosition() < crs.getCount(); crs.moveToNext()){
            drawersList.add(new DrawerItem(crs.getPosition(),"Cassetto_"+ crs.getInt(crs.getColumnIndex(DBStrings.Cassetti_ID))));
        }


        return drawersList;
    }

    public static ArrayList<String> getDrawersNamesList(DBManager db){
        ArrayList<String> drawersNamesList = new ArrayList<String>();

        Cursor crs=db.query_cassetto();
        crs.moveToFirst();
        for (; crs.getPosition() < crs.getCount(); crs.moveToNext()){
            //System.out.println(crs.getColumnIndex(DBStrings.Cassetti_NOME) + " " + crs.getPosition() + " " +crs.getCount());
            //System.out.println(crs.getString(crs.getColumnIndex(DBStrings.Cassetti_NOME)));
            drawersNamesList.add( crs.getString(crs.getColumnIndex(DBStrings.Cassetti_NOME)));
        }

        return drawersNamesList;
    }
}
