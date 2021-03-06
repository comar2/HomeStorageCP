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
import java.util.Collections;
import java.util.List;

import it.comar.admin.homestroragecp.database.DBManager;
import it.comar.admin.homestroragecp.database.DBStrings;

final class DrawersListAdapter extends BaseAdapter {
    private final Context context;
    private final ArrayList<String> urls;// = new ArrayList<String>();

    private DBManager db=null;


    public DrawersListAdapter(Context context) {
        this.context = context;
        db=new DBManager(context);
        //Collections.addAll(urls, Data.URLS);
        //Collections.addAll(urls,ConfigArmadio.getDrawersNamesList());
        //List<SomeBean> newList = new ArrayList<SomeBean>(otherList);
        //urls = new ArrayList<String>(CassettiUrl.getCassettoUrlList(2, this.context));
        //urls = new ArrayList<String>(CassettiUrl.getCassettoUrl2(2, this.context));

        urls = /*db.query_cassetto();*/new ArrayList<String>(ConfigArmadio.getDrawersNamesList(db));

        //Collections.addAll(urls,CassettiUrl.getCassettoUrl(2,this.context));
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        Cursor crs=db.query_cassetto();
        ViewHolder holder;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.sample_list_detail_item, parent, false);
            holder = new ViewHolder();
            holder.image = (ImageView) view.findViewById(R.id.photo);
            holder.text = (TextView) view.findViewById(R.id.url);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        // Get the image URL for the current position.
        //String url = getItem(position);
        //System.out.println(position + " " + crs.getPosition());
        crs.moveToPosition(position);
        //System.out.println(crs.getPosition());
        String url =  crs.getString(crs.getColumnIndex(DBStrings.Oggetti_ICONA_PATH)); //getItem(position);
        System.out.println("... " +url);
        String nome = crs.getString(crs.getColumnIndex(DBStrings.Oggetti_NOME));
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
        return urls.size();
    }

    @Override
    public String getItem(int position) {
        return urls.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    static class ViewHolder {
        ImageView image;
        TextView text;
    }
}
