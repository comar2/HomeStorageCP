package it.comar.admin.homestroragecp.share;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import it.comar.admin.homestroragecp.R;
import it.comar.admin.homestroragecp.database.DBManager;
import it.comar.admin.homestroragecp.utility.FileManip;
import it.comar.admin.homestroragecp.utility.UriToPath;


public class LoadImageFromIntentActivity extends Activity implements View.OnClickListener {

    private Button btn_ok;
    private Button btn_annulla;
    private Button btn_casscorrennte;
    private NumberPicker numCassetto_Picker;
    private ImageView image;
    private EditText text;


    private Integer cassettoselez = 1;
    private String path;

    DBManager db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_image_from_intent);

        numCassetto_Picker = (NumberPicker) findViewById(R.id.numCass_np);
        btn_ok= (Button)findViewById(R.id.aggiungi_oggetto_btn);
        btn_annulla= (Button)findViewById(R.id.cancel_btn);
        btn_casscorrennte=(Button)findViewById(R.id.casscorrennte_btn);
        image = (ImageView) findViewById(R.id.img_ogg);
        text = (EditText) findViewById(R.id.nome_oggetto_txt);

        Context context = getApplicationContext();

        db = new DBManager(context);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        String action = intent.getAction();

        // if this is from the share menu
        if (Intent.ACTION_SEND.equals(action)) {
            if (extras.containsKey(Intent.EXTRA_STREAM)) {
                // Get resource path
                Uri uri = extras.getParcelable(Intent.EXTRA_STREAM);
                path = UriToPath.getPath(context, uri); //parseUriToFilename

                if (path != null) {
                    Picasso.with(context)
                            .load("file://" + path)
                            .placeholder(R.drawable.error/*placeholder*/)
                            .error(R.drawable.error)
                            .resizeDimen(R.dimen.list_detail_image_size, R.dimen.list_detail_image_size)
                            .centerInside()
                            .tag(context)
                            .into(image);
                }


                numCassetto_Picker.setMinValue(1);
                numCassetto_Picker.setMaxValue(19);
                numCassetto_Picker.setWrapSelectorWheel(false);

                numCassetto_Picker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {

                    @Override
                    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                        if (newVal > 0 && newVal <= 19) {
                            cassettoselez = newVal;
                        }
                    }
                });
                numCassetto_Picker.setWrapSelectorWheel(true);

                btn_ok.setOnClickListener(this);
                btn_annulla.setOnClickListener(this);
                btn_casscorrennte.setOnClickListener(this);

            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_load_image_from_intent, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onClick(View v) {
        if (v.equals(btn_ok)){
            int incrementale = db.query_max_id_oggetto();
            incrementale++;

            String destinationPath = cassettoselez < 10 ? "/storage/emulated/0/Android/data/it.comar.admin.homestroragecp/files/cassetti/c0" + cassettoselez +"/"+incrementale+".jpg" : "/storage/emulated/0/Android/data/it.comar.admin.homestroragecp/files/cassetti/c" + cassettoselez +"/"+incrementale+".jpg";
            File destination = new File(destinationPath);
            File source = new File(path);
            
            byte[] b_img = new byte[1];
            b_img[0] = 1;

            String nome=text.getText().toString();
            if(nome.equals("")){nome = "Oggetto";}

            boolean salvataggio_riuscito = true;
            if (path!=null) {
                try {
                    FileManip.copyFileUsingFileStreams(source, destination);
                }
                catch (FileNotFoundException e){
                    salvataggio_riuscito = false;
                }catch (IOException e) {
                    e.printStackTrace();
                    salvataggio_riuscito = false;
                }
            }
            if (salvataggio_riuscito) {

                db.save_oggetto(nome, destinationPath, true, b_img, cassettoselez);
                Toast.makeText(getApplicationContext(), "oggetto salvato", Toast.LENGTH_SHORT).show();
                //
            }
            else{
                Toast.makeText(getApplicationContext(), "oggetto non salvato", Toast.LENGTH_SHORT).show();
            }

            finish();
            return;
        }
        if(v.equals(btn_annulla)){
            //TODO Chiudi l'activity senza far nulla
            Toast.makeText(getApplicationContext(), "oggetto non salvato", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
        if(v.equals(btn_casscorrennte)){
            //TODO richiedi al service in quale cassetto siamo
            System.out.println("Bottone non implementato");
        }
    }

}
