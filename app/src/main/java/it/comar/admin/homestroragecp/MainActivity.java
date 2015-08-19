package it.comar.admin.homestroragecp;

import android.app.ActionBar;
import android.app.Dialog;

import android.content.Intent;
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
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import it.comar.admin.homestroragecp.database.DBManager;
import it.comar.admin.homestroragecp.utility.FileManip;
import it.comar.admin.homestroragecp.utility.UriToPath;
import it.comar.arduino.service.AdkService;

import static it.comar.admin.homestroragecp.CassettiUrl.createFolders;

/**
 * Attivita principale della applicazione.
 */
public class MainActivity
        extends FragmentActivity
        implements DrawersScrollVertFragment.OnFragmentInteractionListener,
        DrawerItemFragment.OnDrawerItemFragmentInteractionListener,
        DrawersScrollVertFragment.Drawer_ContentFragment.OnDrawer_ContentFragmentInteractionListener/*,
        AggiungiOggettoDialog.AggiungiOggettoListener*/
{

    private FrameLayout leftFragment;
    private FrameLayout rightFrame;
    private DrawerItemsAdapter dia;

    //TODO FARE metodo
    public void onFragmentInteraction(Uri uri){//////////////////////TODOe lui

    }

    /**
     * metodo per fare aggiornare l'immagine del cassetto quando viene scelta una nuova immagine.
     * @param cassetto il numero del cassetto la cui immagine è cambiata
     */
    public void On_cambio_immagine_Drawer_ContentFragmentInteraction(int cassetto){
        DrawerItemFragment dif = (DrawerItemFragment)
                getSupportFragmentManager().findFragmentById(R.id.fragmentLeft);

        dif.Aggiorna_Immagini_Cassetti(cassetto);
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
            DrawerItemFragment dif =DrawerItemFragment.newInstance("DrawerItemFragment", "bbbb");
            fragmentTransaction.add(R.id.fragmentLeft, dif);
            fragmentTransaction.commit();

            fragmentTransaction = fragmentManager.beginTransaction();

            // Crea una istanza di DrawerScrollVertFragment e la aggiunge nel container R.id.fragmentRight
            DrawersScrollVertFragment dsvf = DrawersScrollVertFragment.newInstance("DrawersScrollVertFragment", "bb");
            fragmentTransaction.add(R.id.fragmentRight, dsvf);
            fragmentTransaction.commit();


        }

        //avvio il servizio per controllare arduino
        Intent intent = new Intent(this, AdkService.class);
        // add infos for the service which file to download and where to store
        //intent.putExtra(AdkServiceOld.GENERIC_MSG, "index.html");
        startService(intent);

        ArrayList<String> paths_list = createFolders(19,getApplicationContext());

        //se non ci sono cassetti nel db li aggiungo.
        DBManager db = new DBManager(this);
        int numero_cassetti = db.query_cassetto().getCount();
        if (numero_cassetti == 0){

            for (int i = 1; i <= 19; i++) {
                String nome_record = "Cassetto " + i;
                String path_record = paths_list.get(i-1);

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







}
