package it.comar.admin.homestroragecp.database;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import 	android.os.AsyncTask;
import android.widget.Toast;

import java.security.Policy;

/**
 * Created by Fabrizio on 04/08/2015.
 */
public class DBUpdateAsyncTask extends AsyncTask<Integer, Void, Boolean>{

    private DBOpenHelper dboh;
    public DBUpdateAsyncTask(DBOpenHelper dbohelp){
        dboh = dbohelp;
    }

    @Override
    protected Boolean doInBackground(Integer... params) {
        System.out.println("avvio salvataggio su db da thread");
        Integer id = params[0];
        Integer presente = params[1];

        SQLiteDatabase db=dboh.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(DBStrings.Oggetti_PRESENTE, presente!=0);
        try
        {
            int i = db.update(DBStrings.TBL_Oggetti, cv, DBStrings.Oggetti_ID + " = ?", new String[]{Integer.toString(id)} );
        }
        catch (SQLiteException sqle)
        {
            // Gestione delle eccezioni
        }
        //db.close();

        return true;
    }

    @Override
    protected void onPostExecute(Boolean result) {
        System.out.println("fine salvataggio su db da thread");

    }
}
