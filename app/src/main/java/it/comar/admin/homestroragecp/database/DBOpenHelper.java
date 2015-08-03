package it.comar.admin.homestroragecp.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Fabrizio on 28/07/2015.
 */
public class DBOpenHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME="DBhscp10";

    DBOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //invocato solo se l'applicazione invocando il costruttore non ha rilevato la presenza di un database con nome DATABASE_NAME
    @Override
    public void onCreate(SQLiteDatabase db) {
        //Query di creazione delle tabelle del db
        String CreazioneDB ="CREATE TABLE \""+ DBStrings.TBL_Cassetti+ "\" (\n\""+
                DBStrings.Cassetti_ID +         "\" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,\n\"" +
                DBStrings.Cassetti_NOME +       "\" TEXT,\n\"" +
                DBStrings.Cassetti_ICONA_BLOB + "\" BLOB,\n\"" +
                DBStrings.Cassetti_ICONA_PATH + "\" TEXT\n);\n";
        db.beginTransaction();
        try {
            db.execSQL(CreazioneDB);
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }

        CreazioneDB ="CREATE TABLE \""+ DBStrings.TBL_Oggetti+ "\" (\n\""+
                DBStrings.Oggetti_ID +              "\" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,\n\"" +
                DBStrings.Oggetti_NOME +            "\" TEXT,\n\"" +
                DBStrings.Oggetti_ICONA_BLOB +      "\" BLOB,\n\"" +
                DBStrings.Oggetti_ICONA_PATH +      "\" TEXT,\n\"" +
                DBStrings.Oggetti_PRESENTE +        "\" INTEGER,\n\"" +
                DBStrings.Oggetti_CASSETTO_ID +     "\" INTEGER,\n" +
                "FOREIGN KEY("+DBStrings.Oggetti_CASSETTO_ID+") REFERENCES "+DBStrings.TBL_Cassetti+"("+DBStrings.Cassetti_ID+") \n ); \n";
        db.beginTransaction();
        try {
            db.execSQL(CreazioneDB);
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
        CreazioneDB ="CREATE TABLE \""+ DBStrings.TBL_TAG+ "\" (\n\""+
                DBStrings.Tag_NOME +            "\" TEXT PRIMARY KEY NOT NULL\n);\n";

        db.beginTransaction();
        try {
            db.execSQL(CreazioneDB);
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }

        CreazioneDB ="CREATE TABLE \""+ DBStrings.TBL_Oggetti_Tag+ "\" (\n\""+
                DBStrings.Oggetti_Tag_OGGETTI_ID +  "\" INTEGER,\n\"" +
                DBStrings.Oggetti_Tag_TAG_NOME +    "\" TEXT,\n" +
                "FOREIGN KEY("+DBStrings.Oggetti_Tag_OGGETTI_ID+") REFERENCES "+DBStrings.TBL_Oggetti+"("+DBStrings.Oggetti_ID+")\n" +
                "FOREIGN KEY("+DBStrings.Oggetti_Tag_TAG_NOME+") REFERENCES "+DBStrings.TBL_TAG+"("+DBStrings.Tag_NOME+") \n ); \n";
        db.beginTransaction();
        try {
            db.execSQL(CreazioneDB);
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }

        CreazioneDB ="CREATE TABLE \""+ DBStrings.TBL_Cassetti_Tag+ "\" (\n\""+
                DBStrings.Cassetti_Tag_CASSETTI_ID +  "\" INTEGER,\n\"" +
                DBStrings.Cassetti_Tag_TAG_NOME +    "\" TEXT,\n" +
                "FOREIGN KEY("+DBStrings.Cassetti_Tag_CASSETTI_ID+") REFERENCES "+DBStrings.TBL_Cassetti+"("+DBStrings.Cassetti_ID+")\n" +
                        "FOREIGN KEY("+DBStrings.Cassetti_Tag_TAG_NOME+") REFERENCES "+DBStrings.TBL_TAG+"("+DBStrings.Tag_NOME+") \n ); \n"
                 ;
        //System.out.println(CreazioneDB);
        //faccio eseguire la query per creare le tabelle

        db.beginTransaction();
        try {
            db.execSQL(CreazioneDB);
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }

    //metodo per l'aggiornamento del db, nel caso si trovi una versione piu vecchia
    // di quella indicata in DATABASE_VERSION.
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


    @Override
    protected void finalize() throws Throwable {
        this.close();
        super.finalize();
    }
}

