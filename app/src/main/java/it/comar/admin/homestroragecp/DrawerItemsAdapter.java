package it.comar.admin.homestroragecp;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.DropBoxManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import it.comar.admin.homestroragecp.database.DBManager;
import it.comar.admin.homestroragecp.database.DBStrings;


final class DrawerItemsAdapter extends BaseAdapter {
    private final Context context;
    private final ArrayList<String> urls;// = new ArrayList<String>();
    private final int numcassetto;

    private DBManager db=null;

    public DrawerItemsAdapter(Context context, int pos) {
        this.context = context;
        numcassetto = pos;

        db=new DBManager(context);

        //Collections.addAll(urls, Data.URLS);
        //Collections.addAll(urls,ConfigArmadio.getDrawersNamesList());
        //List<SomeBean> newList = new ArrayList<SomeBean>(otherList);
        //urls = new ArrayList<String>(CassettiUrl.getCassettoUrlList(2, this.context));
        //urls = new ArrayList<String>(CassettiUrl.getCassettoUrl2(2, this.context));


        urls = new ArrayList<String>(CassettiUrl.getCassettoUrl3(pos, this.context));
        //urls = new ArrayList<String>(CassettiUrl.getCassettoUrlList(pos, this.context));
        //java.util.Collections.addAll(urls,CassettiUrl.getCassettoUrl(2,this.context));


    }

    @Override
    public View getView(final int position, View view, ViewGroup parent) {
        final ViewHolder holder;
        Cursor crs=db.query_oggetto();
        crs.moveToPosition(position);
        final int id = crs.getInt(crs.getColumnIndex(DBStrings.Oggetti_ID));


        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.drawer_list_item, parent, false);
            holder = new ViewHolder();
            holder.image = (ImageView) view.findViewById(R.id.photo);
            holder.text = (TextView) view.findViewById(R.id.url);
            holder.getButton = (Button) view.findViewById(R.id.get_button);
            holder.numcassetto = numcassetto;
            holder.objid = id;


            holder.getButton.setOnClickListener(
                    (new View.OnClickListener()
                        {
                            @Override
                            public void onClick(View v)
                            {

                                Cursor c=db.query_oggetto_id(id);
                                System.out.println(v.getId());
                                System.out.println(position);
                                System.out.println(id);
                                c.moveToFirst();
                                int pres =c.getInt(c.getColumnIndex(DBStrings.Oggetti_PRESENTE));

                                // Your code that you want to execute on this button click
                                Toast.makeText(v.getContext(), "position: " + Integer.toString(position)
                                                                + "\n holder.numcassetto: " + Integer.toString(holder.numcassetto)
                                                                + "\n holder.objid: " + Integer.toString(holder.objid)

                                        , Toast.LENGTH_SHORT).show();

                                System.out.println(pres);
                                if (pres==0) {

                                    db.update_oggetto(id, true);
                                    holder.image.setImageAlpha(255);
                                }
                                else{
                                    db.update_oggetto(id, false);
                                    holder.image.setImageAlpha(120);
                                }

                            }

                        })
            );
//            holder.getButton.setOnClickListener(
//                    (new AdapterView.OnItemClickListener()
//                    {
//                        @Override
//                        public void onItemClick(AdapterView<?> arg0, View arg1,int position, long arg3)
//                        {
//                            Toast.makeText(arg0.getActivity(), "" + position, Toast.LENGTH_SHORT).show();
//                        }
//                    })
//            );
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        // Get the image URL for the current position.

        String url = "file://" + crs.getString(crs.getColumnIndex(DBStrings.Oggetti_ICONA_PATH)); //getItem(position);

        String nome = crs.getString(crs.getColumnIndex(DBStrings.Oggetti_NOME));
        holder.text.setText(nome);

        System.out.println(nome);
        System.out.println(url);

        // Trigger the download of the URL asynchronously into the image view.
        Picasso.with(context)
                .load(url)
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.error)
                .resizeDimen(R.dimen.list_detail_image_size, R.dimen.list_detail_image_size)
                .centerInside()
                .tag(context)

                .into(holder.image);

        if (crs.getInt(crs.getColumnIndex(DBStrings.Oggetti_PRESENTE))==0) {
            holder.image.setImageAlpha(255);
        }
        else{
            holder.image.setImageAlpha(120);
        }
/*
        String nome=crs.getString(crs.getColumnIndex(DBStrings.Cassetti_NOME));
        String path=crs.getString(crs.getColumnIndex(DBStrings.Cassetti_ICONA_PATH));

        TextView txt=(TextView) v.findViewById(R.id.txt_subject);
        txt.setText(nome);

        txt=(TextView) v.findViewById(R.id.txt_date);
        txt.setText(path);

        byte[] blob_img = crs.getBlob(crs.getColumnIndex(DBStrings.Cassetti_ICONA_BLOB));
        if(blob_img!=null) {
            Bitmap img = BitmapFactory.decodeByteArray(blob_img, 0, blob_img.length);
            ImageView imgview = (ImageView) v.findViewById(R.id.img);
            imgview.setImageBitmap(img);
        }

        ImageButton imgbtn=(ImageButton) v.findViewById(R.id.btn_delete);
        imgbtn.setOnClickListener(clickListener);*/


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
        Button getButton;
        int numcassetto;
        int  objid;
    }
}
