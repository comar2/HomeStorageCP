package it.comar.admin.homestroragecp.share;

import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;
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
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import it.comar.admin.homestroragecp.R;
import it.comar.admin.homestroragecp.database.DBManager;
import it.comar.arduino.service.AdkService;

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
                Uri uri = (Uri) extras.getParcelable(Intent.EXTRA_STREAM);
                path = getPath(context,uri); //parseUriToFilename

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
                        // TODO Auto-generated method stub
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
            //TODO inserisci l'oggetto nel database
            int incrementale = db.query_max_id_oggetto();
            incrementale++;

            String destinationPath = cassettoselez < 10 ? "/storage/emulated/0/Android/data/it.comar.admin.homestroragecp/files/cassetti/c0" + cassettoselez +"/"+incrementale+".jpg" : "/storage/emulated/0/Android/data/it.comar.admin.homestroragecp/files/cassetti/c" + cassettoselez +"/"+incrementale+".jpg";
            File destination = new File(destinationPath);
            File source = new File(path);
            //System.out.println(destinationPath);

            byte[] b_img = new byte[1];
            b_img[0] = 1;

            String nome=text.getText().toString();
            if(nome.equals("")){nome = "Oggetto";}

            boolean salvataggio_riuscito = true;
            if (path!=null) {
                try {
                    copyFileUsingFileStreams(source, destination);
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


    private static void copyFileUsingFileStreams(File source, File dest) throws IOException {
        InputStream input = null;
        OutputStream output = null;

        try {
            input = new FileInputStream(source);
            output = new FileOutputStream(dest);
            byte[] buf = new byte[1024];
            int bytesRead;
            while ((bytesRead = input.read(buf)) > 0) {
                output.write(buf, 0, bytesRead);
            }

        } finally {
            //input.close();
            //output.close();
        }
    }


    /**
     * Get a file path from a Uri. This will get the the path for Storage Access
     * Framework Documents, as well as the _data field for the MediaStore and
     * other file-based ContentProviders. paulburke
     *
     * @param context The context.
     * @param uri The Uri to query.
     */
    public static String getPath(final Context context, final Uri uri) {

        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }

                // TODO handle non-primary volumes
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[] {
                        split[1]
                };

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {

            // Return the remote address
            if (isGooglePhotosUri(uri))
                return uri.getLastPathSegment();

            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

    /**
     * Get the value of the data column for this Uri. This is useful for
     * MediaStore Uris, and other file-based ContentProviders.
     *
     * @param context The context.
     * @param uri The Uri to query.
     * @param selection (Optional) Filter used in the query.
     * @param selectionArgs (Optional) Selection arguments used in the query.
     * @return The value of the _data column, which is typically a file path.
     */
    public static String getDataColumn(Context context, Uri uri, String selection,
                                       String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {
                column
        };

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }


    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is Google Photos.
     */
    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }

}
