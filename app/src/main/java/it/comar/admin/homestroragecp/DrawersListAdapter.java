package it.comar.admin.homestroragecp;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.File;

import it.comar.admin.homestroragecp.database.DBManager;
import it.comar.admin.homestroragecp.database.DBStrings;


final class DrawersListAdapter extends CursorAdapter {
    //private final Context context;
    //private final ArrayList<String> urls;// = new ArrayList<String>();

    private boolean aggiornadb;
    DBManager db;
    public DrawersListAdapter(Context context, Cursor c, int flags) {
        super(context,c,flags);

        //la lista dei cassetti va recuperata solo una volta (suppongo che non ci si metta a mettere e togliere cassetti mentre la macchina è in funzione
        db = new DBManager(context);
        changeCursor(db.query_cassetto());
    }

    //prepara la nuova view eseguito solo  se non c'è ancora una view pronta
    //come riferimento:
    // http://grepcode.com/file/repo1.maven.org/maven2/org.robolectric/android-all/4.4_r1-robolectric-1/android/widget/CursorAdapter.java#CursorAdapter.getView%28int%2Candroid.view.View%2Candroid.view.ViewGroup%29
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {

        Cursor crs = getCursor();

        View view =  LayoutInflater.from(context).inflate(R.layout.sample_list_detail_item, parent, false);// inflater.inflate(layout, parent, false);

        final ViewHolder holder = new ViewHolder();
        holder.numcassetto = crs.getInt(crs.getColumnIndex(DBStrings.Cassetti_ID));
        holder.image = (ImageView) view.findViewById(R.id.photo);
        holder.text = (TextView) view.findViewById(R.id.url);

        view.setTag(holder);

        //FillHolder(context, crs, holder);

        return view;
    }

    // collega i dati alla view
    @Override
    public void bindView(View view, Context context, Cursor crs) {

        final ViewHolder holder;
        holder = (ViewHolder) view.getTag();

        FillHolder(context, crs, holder);
    }

    private void FillHolder(Context context, Cursor crs, ViewHolder holder){

        String url = "file://" +  crs.getString(crs.getColumnIndex(DBStrings.Cassetti_ICONA_PATH));

        String nome = crs.getString(crs.getColumnIndex(DBStrings.Cassetti_NOME));
        holder.text.setText(nome);

        // Trigger the download of the URL asynchronously into the image view.
        // Picasso mantiene una cache delle immagini, indicizzate col percorso del file, dunque se sovrascrivo una immagine e voglio renderla visibile
        // è necessario fargli sapere che la copia che ha in cache non è più valida.
        if (aggiorna && c-1==crs.getPosition()) {
            File file = new File(crs.getString(crs.getColumnIndex(DBStrings.Cassetti_ICONA_PATH)));
            Picasso.with(activity).invalidate(file);
            aggiorna=false;
        }
        Picasso.with(context)
                .load(url)
                .placeholder(R.drawable.error/*placeholder*/)
                .error(R.drawable.error)
                .resizeDimen(R.dimen.list_detail_image_size, R.dimen.list_detail_image_size)
                .centerInside()
                .tag(context)
                .into(holder.image);


    }

    static class ViewHolder {
        ImageView image;
        TextView text;
        int numcassetto;
    }

    @Override
    public void notifyDataSetChanged(){
        System.out.println("aggiornamento DRAWERLISTADAPTER");

        super.notifyDataSetChanged();
    }


    //variabili per la gestione dell'aggiornamento
    private boolean aggiorna=false;
    private int c=0;
    private Activity activity;

    /**
     * funzione per notificare la modifica di una delle immagini. l'adapter caricherà un nuovo elemento e cancellerà la cache di picasso relativa a tale immagine
     * @param cassetto
     * @param a
     */
    public void nuovo_cursore(int cassetto, Activity a){
        swapCursor(db.query_cassetto());
        aggiorna=true;
        c=cassetto;
        activity=a;
    }
}
