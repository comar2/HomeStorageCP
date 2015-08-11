package it.comar.admin.homestroragecp;

import android.app.ActionBar;
import android.app.Dialog;

import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;

import android.content.Intent;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

import android.net.Uri;
import android.os.Bundle;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;


import it.comar.admin.homestroragecp.database.DBManager;
import it.comar.arduino.service.AdkService;

/**
 * Attivita principale della applicazione.
 */
public class MainActivity
        extends FragmentActivity
        implements DrawersScrollVertFragment.OnFragmentInteractionListener,
        DrawerItemFragment.OnDrawerItemFragmentInteractionListener,
        AggiungiOggettoDialog.AggiungiOggettoListener
{

    private FrameLayout leftFragment;
    private FrameLayout rightFrame;
    private DrawerItemsAdapter dia;

    //TODO FARE metodo
    public void onFragmentInteraction(Uri uri){

    }
    public void OnDrawerItemFragmentInteraction(int pos) {
        // The user selected the headline of an article from the HeadlinesFragment
        // Do something here to display that article
        //Toast.makeText(this, "Main TOST" + "; position: " + Integer.toString(pos), Toast.LENGTH_SHORT).show();

        // The user selected the headline of an article from the HeadlinesFragment

        // Capture the article fragment from the activity layout
        DrawersScrollVertFragment scrollFrag = (DrawersScrollVertFragment)
                getSupportFragmentManager().findFragmentById(R.id.fragmentRight);

        if (scrollFrag != null) {
            // If article frag is available, we're in two-pane layout...

            // Call a method in the ArticleFragment to update its content
            scrollFrag.updateScrollItemView(pos);

        } /*else {
//            // If the frag is not available, we're in the one-pane layout and must swap frags...
//
//            // Create fragment and give it an argument for the selected article
//            ArticleFragment newFragment = new ArticleFragment();
//            Bundle args = new Bundle();
//            args.putInt(ArticleFragment.ARG_POSITION, position);
//            newFragment.setArguments(args);
//            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//
//            // Replace whatever is in the fragment_container view with this fragment,
//            // and add the transaction to the back stack so the user can navigate back
//            transaction.replace(R.id.fragment_container, newFragment);
//            transaction.addToBackStack(null);
//
//            // Commit the transaction
//            transaction.commit();
        }*/
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        //getWindow().requestFeature(Window.FEATURE_ACTION_BAR);


        ActionBar actionBar = getActionBar();

        actionBar.hide();
// more stuff here...
        actionBar.show();

        //Il fragment che contiene l'elenco dei cassetti
        leftFragment = (FrameLayout) findViewById(R.id.fragmentLeft);
        //Il fragment che contiene la visualizzazione delle caratteristiche del cassetto e del suo contenuto
        rightFrame = (FrameLayout) findViewById(R.id.fragmentRight);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        if (savedInstanceState == null) {//se si e alla prima eseczione di onCreate

            // Crea una istanza di DrawerItemFragment e la aggiunge nel container R.id.fragmentLeft
            fragmentTransaction.add(R.id.fragmentLeft, DrawerItemFragment.newInstance("DrawerItemFragment", "bbbb"));
            fragmentTransaction.commit();

            fragmentTransaction = fragmentManager.beginTransaction();

            // Crea una istanza di DrawerScrollVertFragment e la aggiunge nel container R.id.fragmentRight
            fragmentTransaction.add(R.id.fragmentRight, DrawersScrollVertFragment.newInstance("DrawersScrollVertFragment", "bb"));
            fragmentTransaction.commit();
        }

        //avvio il servizio per controllare arduino
        Intent intent = new Intent(this, AdkService.class);
        // add infos for the service which file to download and where to store
        //intent.putExtra(AdkServiceOld.GENERIC_MSG, "index.html");
        startService(intent);

        //se non ci sono cassetti li creo
        DBManager db = new DBManager(this);

        if (db.query_cassetto().getCount() == 0){

            for (int i = 1; i <= 19; i++) {
                String nome_record = "Cassetto " + i;
                String path_record = i < 10 ? "/storage/emulated/0/Android/data/it.comar.admin.homestoragecp/files.cassetti/c0" + i : "/storage/emulated/0/Android/data/it.comar.admin.homestoragecp/files.cassetti/c" + i;
                byte[] b_img = new byte[1];
                b_img[0] = 1;
                db.save_cassetto(nome_record, path_record, b_img);
            }
            /*
            //TODO rimuovere dopo i test
            //popolo il database come test
            for (int i = 1; i <= 19; i++) {
                String path = i < 10 ? "/storage/emulated/0/Android/data/it.comar.admin.homestroragecp/files/cassetti/c0" + i : "/storage/emulated/0/Android/data/it.comar.admin.homestroragecp/files/cassetti/c" + i;
                //System.out.println(path);
                File f = new File(path);
                if (f.exists()) {
                    File[] files = f.listFiles();

                    ArrayList<String> arrayFiles = new ArrayList<String>();
                    if (files.length == 0) {
                    } else {
                        for (int j = 0; j < files.length; j++)
                            arrayFiles.add(files[j].getAbsolutePath());
                    }

                    for (int k = 0; k < arrayFiles.size(); k++) {
                        String nome_record = "Oggetto " + (k + 1);
                        String path_record = arrayFiles.get(k);
                        byte[] b_img = new byte[1];
                        b_img[0] = 1;
                        db.save_oggetto(nome_record, path_record, true, b_img, i);
                    }
                } else {
                    System.out.println("cartella non esistente" + i);
                }
            }*/
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

        if (id == R.id.menu_main_users) {
            Dialog menuuser = new Dialog(this);
            menuuser.setTitle("pippo");
            menuuser.show();
            return true;
        }

        if (id == R.id.menu_main_man_mov) {
            Intent intent = new Intent(this, Activity_ManualMov.class);
            this.startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void setDrawerItemsAdapter(DrawerItemsAdapter drawerItemsAdapter){
        dia = drawerItemsAdapter;
    }

//TODO ATTENZIONE NON SALVA IL BLOB
    // The dialog fragment receives a reference to this Activity through the
    // Fragment.onAttach() callback, which it uses to call the following methods
    // defined by the NoticeDialogFragment.NoticeDialogListener interface
    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        // User touched the dialog's positive button
        //System.out.println("pigiato ok");

        int numcassetto = ((AggiungiOggettoDialog)dialog).getNumcassetto();

        String nome = ((AggiungiOggettoDialog)dialog).getNome();

        if(nome.equals("")){nome = "Oggetto";}

        Uri uriImg = ((AggiungiOggettoDialog)dialog).getUriImmagine();

        String original_path="";
        if (uriImg!=null) {

            original_path = getPath(getApplicationContext(), uriImg);

        }
        //System.out.println(original_path);
        File source = new File(original_path);

        DBManager db = new DBManager(this);

        int incrementale = db.query_max_id_oggetto();
        incrementale++;

        String destinationPath = numcassetto < 10 ? "/storage/emulated/0/Android/data/it.comar.admin.homestroragecp/files/cassetti/c0" + numcassetto+"/"+incrementale+".jpg" : "/storage/emulated/0/Android/data/it.comar.admin.homestroragecp/files/cassetti/c" + numcassetto+"/"+incrementale+".jpg";
        File destination = new File(destinationPath);
        //System.out.println(destinationPath);

        byte[] b_img = new byte[1];
        b_img[0] = 1;

        boolean salvataggio_riuscito = true;
        if (uriImg!=null) {
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
            db.save_oggetto(nome, destinationPath, true, b_img, numcassetto);
            Toast.makeText(getApplicationContext(), "oggetto salvato", Toast.LENGTH_SHORT).show();
            dia.setAggiornaDb();
            dia.notifyDataSetChanged();
            //
        }
        else{
            Toast.makeText(getApplicationContext(), "oggetto non salvato", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {
        // User touched the dialog's negative button
        //System.out.println("pigiato cancel");
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
