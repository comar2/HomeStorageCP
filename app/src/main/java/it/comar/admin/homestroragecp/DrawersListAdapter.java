package it.comar.admin.homestroragecp;

import android.content.Context;
import android.database.Cursor;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import it.comar.admin.homestroragecp.database.DBManager;
import it.comar.admin.homestroragecp.database.DBStrings;


final class DrawersListAdapter extends CursorAdapter {
    //private final Context context;
    //private final ArrayList<String> urls;// = new ArrayList<String>();

    public DrawersListAdapter(Context context, Cursor c, int flags) {
        super(context,c,flags);

        DBManager db = new DBManager(context);
        changeCursor(db.query_cassetto());
    }

    //prepara la nuova view eseguito solo  se non c'è ancora una view pronta
    //come riferimento:
    // http://grepcode.com/file/repo1.maven.org/maven2/org.robolectric/android-all/4.4_r1-robolectric-1/android/widget/CursorAdapter.java#CursorAdapter.getView%28int%2Candroid.view.View%2Candroid.view.ViewGroup%29
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        Cursor crs = getCursor();

        View view =  LayoutInflater.from(context).inflate(R.layout.sample_list_detail_item, parent, false);// inflater.inflate(layout, parent, false);

        final ViewHolder holder;
        holder = new ViewHolder();
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

        String url =  crs.getString(crs.getColumnIndex(DBStrings.Cassetti_ICONA_PATH));

        String nome = crs.getString(crs.getColumnIndex(DBStrings.Cassetti_NOME));
        holder.text.setText(nome);

        // Trigger the download of the URL asynchronously into the image view.

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

}
