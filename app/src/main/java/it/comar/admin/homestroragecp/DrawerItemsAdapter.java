package it.comar.admin.homestroragecp;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import it.comar.admin.homestroragecp.database.DBManager;
import it.comar.admin.homestroragecp.database.DBStrings;
import it.comar.admin.homestroragecp.database.DBUpdateAsyncTask;
import it.comar.arduino.service.AdkService;

/*TODO se i dati vengono prelevati da un cursor ottenuto da un DB forse è meglio farlo ereditare da cursoradapter, piuttosto che da baseadapter e fare le modifiche del caso al codice*/
final class DrawerItemsAdapter extends BaseAdapter{
    private final Context context;

    //private final ArrayList<String> urls;// = new ArrayList<String>();
    private final int numcassetto;

    private DBManager db=null;
    private Cursor crs;
    public DrawerItemsAdapter(Context context, int pos) {
        this.context = context;
        numcassetto = pos;

        db=new DBManager(context);
        crs=db.query_oggetto(numcassetto);

        //urls = new ArrayList<String>(CassettiUrl.getCassettoUrl3(pos, this.context));
    }

    @Override
    public View getView(final int position, View view, ViewGroup parent) {
        final ViewHolder holder;
        crs=db.query_oggetto(numcassetto);
        crs.moveToPosition(position);
        final int id = crs.getInt(crs.getColumnIndex(DBStrings.Oggetti_ID));
        //System.out.println("creazione vista");
        //System.out.println("id" + id);


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
                            //System.out.println("bottone pigiato");

                            //Cursor c=db.query_oggetto_id_presente(id);

                            //System.out.println("id view " + v.getId());
                            //System.out.println("position " + position);
                            //System.out.println("id oggetto " + id);

                            //c.moveToFirst();
                            //int pres =c.getInt(c.getColumnIndex(DBStrings.Oggetti_PRESENTE));
                            Boolean pres = db.query_oggetto_id_presente(id);
                            // Your code that you want to execute on this button click
                           /* Toast.makeText(v.getContext(), "presente: " + Integer.toString(pres)
                                                            + "\n holder.numcassetto: " + Integer.toString(holder.numcassetto)
                                                            + "\n holder.objid: " + Integer.toString(holder.objid)
                                    , Toast.LENGTH_SHORT).show();
                            */
                            //System.out.println("presente " + pres);

                            DBUpdateAsyncTask asynctask = new DBUpdateAsyncTask(db.getDBOH());

                            if (!pres/*==0*/) {

                                //db.update_oggetto(id, true);
                                asynctask.execute(new Integer[]{new Integer(id), new Integer(1)});
                                holder.image.setImageAlpha(255);
                                holder.getButton.setText("Estrai");
                            }
                            else{
                                //db.update_oggetto(id, false);
                                asynctask.execute(new Integer[]{new Integer(id), new Integer(0)});
                                holder.image.setImageAlpha(120);
                                holder.getButton.setText("Inserisci");
                            }
                            //System.out.println("Eseguito aggiornamento icona");


                            String command = AdkService.SEND_MSG_CHIAMA_CASSETTO;
                            String params = Integer.toString(holder.numcassetto-1); //La macchina indicizza con base  0, io chiamo i cassetti con base 1
                            Intent intent = new Intent(AdkService.SEND_ADK_STRING);
                            intent.putExtra(AdkService.MSG_COMMAND, command);
                            intent.putExtra(AdkService.MSG_PARAMS, params);
                            context.sendBroadcast(intent);

                            //System.out.println("inviato intent");
                        }
                    }
                )
            );

            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        // Get the image URL for the current position.

        String url = "file://" + crs.getString(crs.getColumnIndex(DBStrings.Oggetti_ICONA_PATH)); //getItem(position);


        String nome = crs.getString(crs.getColumnIndex(DBStrings.Oggetti_NOME));
        holder.text.setText(nome);

        //System.out.println(nome);
        //System.out.println(url);

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
            holder.image.setImageAlpha(120);
            holder.getButton.setText("Inserisci");
        }
        else{
            holder.image.setImageAlpha(255);
            holder.getButton.setText("Estrai");
        }

        return view;
    }

    @Override
    public int getCount() {
        return crs.getCount();//urls.size();
    }

    @Override
    public String getItem(int position) {
        return crs.getString(crs.getColumnIndex(DBStrings.Oggetti_ICONA_PATH));
        //return urls.get(position);
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
