package it.comar.admin.homestroragecp;

import android.content.Context;
import android.database.Cursor;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;


import it.comar.admin.homestroragecp.database.DBManager;
import it.comar.admin.homestroragecp.database.DBStrings;

/*TODO se i dati vengono prelevati da un cursor ottenuto da un DB forse è meglio farlo ereditare da cursoradapter, piuttosto che da baseadapter e fare le modifiche del caso al codice*/
final class DrawersListAdapter extends BaseAdapter  {
    private final Context context;
    //private final ArrayList<String> urls;// = new ArrayList<String>();

    private DBManager db=null;
    private Cursor crs;

    public DrawersListAdapter(Context context) {
        this.context = context;
        db=new DBManager(context);
        crs=db.query_cassetto();
        //urls = /*db.query_cassetto();*/new ArrayList<String>(ConfigArmadio.getDrawersNamesList(db));

    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        crs=db.query_cassetto();
        final ViewHolder holder;
        crs.moveToPosition(position);

        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.sample_list_detail_item, parent, false);

            holder = new ViewHolder();
            holder.numcassetto = crs.getInt(crs.getColumnIndex(DBStrings.Cassetti_ID));
            holder.image = (ImageView) view.findViewById(R.id.photo);
            holder.text = (TextView) view.findViewById(R.id.url);


            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        // Get the image URL for the current position.
        //String url = getItem(position);
        //System.out.println(position + " " + crs.getPosition());

        //System.out.println(crs.getPosition());
        String url =  crs.getString(crs.getColumnIndex(DBStrings.Cassetti_ICONA_PATH)); //getItem(position);
        //System.out.println("... " +url);
        String nome = crs.getString(crs.getColumnIndex(DBStrings.Cassetti_NOME));
        holder.text.setText(nome);

        // Trigger the download of the URL asynchronously into the image view.

        Picasso.with(context)
                .load(url)
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.error)
                .resizeDimen(R.dimen.list_detail_image_size, R.dimen.list_detail_image_size)
                .centerInside()
                .tag(context)
                .into(holder.image);

        return view;
    }

    @Override
    public int getCount() {
        return crs.getCount();
    }

    @Override
    public String getItem(int position) {
        return crs.getString(crs.getColumnIndex(DBStrings.Cassetti_ICONA_PATH));
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    static class ViewHolder {
        ImageView image;
        TextView text;
        int numcassetto;
    }

}
