package it.comar.admin.homestroragecp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Fabrizio on 28/07/2015.
 */
public class DBManager {

    private DBOpenHelper dboh;

    public static enum Tabelle{
        CASSETTI,
        OGGETTI,
        TAG
    }

    private Map<Tabelle,String> dizionario_tabelle =  new HashMap<Tabelle,String>();

    public DBOpenHelper getDBOH(){return dboh;}

    public DBManager(Context ctx){
        dboh = new DBOpenHelper(ctx);
        dizionario_tabelle.put(Tabelle.CASSETTI, DBStrings.TBL_Cassetti);
        dizionario_tabelle.put(Tabelle.OGGETTI, DBStrings.TBL_Oggetti);
        dizionario_tabelle.put(Tabelle.TAG, DBStrings.TBL_TAG);
    }

    /**
     * salva un record nel database.
     * @param tabella l'enum per sapere su quale tabella vada eseguito il salvataggio
     * @param cv il content in cui si trovano i dati da salvare
     */
    public void save(Tabelle tabella, ContentValues cv)
    {
        SQLiteDatabase db = dboh.getWritableDatabase();

        try
        {
            String str = (String) dizionario_tabelle.get(tabella);
            db.insert(str, null,cv);
        }
        catch (SQLiteException sqle)
        {
            // Gestione delle eccezioni
        }
    }

    public boolean delete(Tabelle tabella,long id)
    {
        SQLiteDatabase db=dboh.getWritableDatabase();
        try
        {
            switch(tabella){
                case CASSETTI:
                    if (db.delete(DBStrings.TBL_Cassetti, DBStrings.Cassetti_ID+"=?", new String[]{Long.toString(id)})>0)
                        return true;
                    return false;
                case OGGETTI:
                    if (db.delete(DBStrings.TBL_Oggetti, DBStrings.Oggetti_ID+"=?", new String[]{Long.toString(id)})>0)
                        return true;
                    return false;
                case TAG:
                    return false;
            }

        }
        catch (SQLiteException sqle)
        {
            return false;
        }
        return false;
    }

    //TODO eliminare quando si e completato salva.
    public void save_cassetto(String nome, String path, byte[] bit_array_immagine)
    {
        SQLiteDatabase db=dboh.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(DBStrings.Cassetti_NOME, nome);
        cv.put(DBStrings.Cassetti_ICONA_PATH, path);
        cv.put(DBStrings.Cassetti_ICONA_BLOB,bit_array_immagine);
        try
        {
            db.insert(DBStrings.TBL_Cassetti, null,cv);
        }
        catch (SQLiteException sqle)
        {
            // Gestione delle eccezioni
        }
    }
    //TODO eliminare quando si e completato salva.
    public void save_oggetto(String nome, String path, boolean presente , byte[] bit_array_immagine, int cassetto_id)
    {
        SQLiteDatabase db=dboh.getWritableDatabase();
        //System.out.println(cassetto_id+" "+nome + " " + path);
        ContentValues cv = new ContentValues();
        cv.put(DBStrings.Oggetti_NOME, nome);
        cv.put(DBStrings.Oggetti_ICONA_PATH, path);
        cv.put(DBStrings.Oggetti_ICONA_BLOB,bit_array_immagine);
        cv.put(DBStrings.Oggetti_CASSETTO_ID,cassetto_id);
        cv.put(DBStrings.Oggetti_PRESENTE, presente);
        try
        {
            System.out.println(db.insert(DBStrings.TBL_Oggetti, null, cv));
        }
        catch (SQLiteException sqle)
        {
            // Gestione delle eccezioni
        }
    }

    public void update_cassetto(String nome, String path){

        SQLiteDatabase db=dboh.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(DBStrings.Cassetti_ICONA_PATH, path);
        try
        {
            int i = db.update(DBStrings.TBL_Cassetti, cv, DBStrings.Cassetti_NOME + " = ?", new String[]{nome} );
        }
        catch (SQLiteException sqle)
        {
            // Gestione delle eccezioni
        }
    }

    public void update_cassetto(int id, String path){

        SQLiteDatabase db=dboh.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(DBStrings.Cassetti_ICONA_PATH, path);
        try
        {
            int i = db.update(DBStrings.TBL_Cassetti, cv, DBStrings.Cassetti_ID + " = ?", new String[]{Integer.toString(id)} );
        }
        catch (SQLiteException sqle)
        {
            // Gestione delle eccezioni
        }
    }

    public void update_oggetto(int id, Boolean presente){

        SQLiteDatabase db=dboh.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(DBStrings.Oggetti_PRESENTE, presente);
        try
        {
            int i = db.update(DBStrings.TBL_Oggetti, cv, DBStrings.Oggetti_ID + " = ?", new String[]{Integer.toString(id)} );
        }
        catch (SQLiteException sqle)
        {
            // Gestione delle eccezioni
        }
        db.close();
    }

    public boolean delete_cassetto(long id)
    {
        SQLiteDatabase db=dboh.getWritableDatabase();
        try
        {
            if (db.delete(DBStrings.TBL_Cassetti, DBStrings.Cassetti_ID+"=?", new String[]{Long.toString(id)})>0)
                return true;
            return false;
        }
        catch (SQLiteException sqle)
        {
            return false;
        }

    }


/*
Cursor query (String table,
                String[] columns,
                String selection,
                String[] selectionArgs,
                String groupBy,
                String having,
                String orderBy,
                String limit)
 */
    public Cursor query_cassetto()
    {
        Cursor crs=null;
        try
        {
            SQLiteDatabase db=dboh.getReadableDatabase();
            //dovrebbe cercare tutti i record della tabella cassetti
            crs=db.query(DBStrings.TBL_Cassetti, null, null, null, null, null, null, null);
        }
        catch(SQLiteException sqle)
        {
            return null;
        }
        return crs;
    }

    public Cursor query_oggetto(int idCassetto)
    {
        Cursor crs=null;
        try
        {
            SQLiteDatabase db=dboh.getReadableDatabase();
            //dovrebbe cercare tutti i record della tabella oggetti del dato cassetto

            String selectQuery = "SELECT * FROM OGGETTI WHERE CASSETTO_ID = ?";
            crs = db.rawQuery(selectQuery, new String[]{Integer.toString(idCassetto)});
        }
        catch(SQLiteException sqle)
        {
            return null;
        }
        return crs;
    }

    public /*Cursor*/ Boolean query_oggetto_id_presente(int id)
    {
        Cursor crs=null;
        Boolean pres;
        try
        {
            SQLiteDatabase db=dboh.getReadableDatabase();

            String selectQuery = "SELECT PRESENTE FROM OGGETTI WHERE _ID = ?";
            crs = db.rawQuery(selectQuery, new String[] { Integer.toString(id) });
            crs.moveToFirst();
            pres = crs.getInt(crs.getColumnIndex(DBStrings.Oggetti_PRESENTE))!=0?true:false;
            crs.close();db.close();
        }
        catch(SQLiteException sqle)
        {
            return null;
        }

        return pres;

    }

    public /*Cursor*/ int query_max_id_oggetto()
    {
        Cursor crs=null;
        int maxid;
        try
        {
            SQLiteDatabase db=dboh.getReadableDatabase();

            String selectQuery = "SELECT MAX(_ID) FROM OGGETTI";
            crs = db.rawQuery(selectQuery, null);
            if (crs.getCount()>0)
            {
                crs.moveToFirst();
                maxid = crs.getInt(crs.getPosition());
            }
            else
                maxid=0;
            crs.close();db.close();
        }
        catch(SQLiteException sqle)
        {
            return -1;
        }

        return maxid;

    }

}