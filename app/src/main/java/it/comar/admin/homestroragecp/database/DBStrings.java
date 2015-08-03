package it.comar.admin.homestroragecp.database;

/**
 * Created by Fabrizio on 28/07/2015.
 */
public class DBStrings {

    public static final String TBL_Cassetti="CASSETTI";
    public static final String Cassetti_ID="_id";
    public static final String Cassetti_NOME="NOME";
    public static final String Cassetti_ICONA_PATH="ICONA_PATH";
    public static final String Cassetti_ICONA_BLOB="ICONA_BLOB";

    public static final String TBL_Oggetti="OGGETTI";
    public static final String Oggetti_ID="_id";
    public static final String Oggetti_NOME="NOME";
    public static final String Oggetti_ICONA_PATH="ICONA_PATH";
    public static final String Oggetti_ICONA_BLOB="ICONA_BLOB";
    public static final String Oggetti_PRESENTE="PRESENTE";
    public static final String Oggetti_CASSETTO_ID="CASSETTO_ID";

    public static final String TBL_TAG="TAG";
    public static final String Tag_NOME="NOME";

    public static final String TBL_Oggetti_Tag="OGGETTI_TAG";
    public static final String Oggetti_Tag_OGGETTI_ID="OGGETTI_ID";
    public static final String Oggetti_Tag_TAG_NOME="TAG_NOME";

    public static final String TBL_Cassetti_Tag="CASSETTI_TAG";
    public static final String Cassetti_Tag_CASSETTI_ID="CASSETTI_ID";
    public static final String Cassetti_Tag_TAG_NOME="TAG_NOME";
}
