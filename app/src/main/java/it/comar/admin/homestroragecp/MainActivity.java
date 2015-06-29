package it.comar.admin.homestroragecp;

import android.app.ActionBar;
import android.app.Dialog;


//import android.app.FragmentManager;
//import android.app.FragmentTransaction;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

import android.net.Uri;
import android.os.Bundle;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;


public class MainActivity extends FragmentActivity
        implements MainActivityFragmentRight.OnFragmentInteractionListener,
        DrawersScrollVertFragment.OnFragmentInteractionListener,
        DrawerItemFragment.OnDrawerItemFragmentInteractionListener
{

    private FrameLayout leftFragment;
    private FrameLayout rightFrame;

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

        } else {
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
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        setContentView(R.layout.main);

        //getWindow().requestFeature(Window.FEATURE_ACTION_BAR);


        ActionBar actionBar = getActionBar();
        actionBar.hide();
// more stuff here...
        actionBar.show();

        leftFragment = (FrameLayout) findViewById(R.id.fragmentLeft);
        rightFrame = (FrameLayout) findViewById(R.id.fragmentRight);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        if (savedInstanceState == null) {
//            getSupportFragmentManager().beginTransaction()
//                    .add(R.id.fragmentLeft,MainActivityFragmentRight_ListItems.newInstance())
//                    .commit();
            //fragmentTransaction.add(R.id.fragmentLeft, MainActivityFragmentLeft.newInstance("frammento left"));
            // Create an instance of ExampleFragment
            fragmentTransaction.add(R.id.fragmentLeft, DrawerItemFragment.newInstance("aa", "bbbb"));
            fragmentTransaction.commit();

            fragmentTransaction = fragmentManager.beginTransaction();
            //fragmentTransaction.add(R.id.fragmentRight, MainActivityFragmentRight_ListItems.newInstance());
            //fragmentTransaction.add(R.id.fragmentRight, MainActivityFragmentRight.newInstance("aaa","bbbb"));

            fragmentTransaction.add(R.id.fragmentRight, DrawersScrollVertFragment.newInstance("aa","bb"));
            fragmentTransaction.commit();


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

        return super.onOptionsItemSelected(item);
    }

//    void showDetails(String url) {
//        getSupportFragmentManager().beginTransaction()
//                .replace(R.id.fragmentLeft, MainActivityFragmentRight_ListItems.newInstance(url))
//                .addToBackStack(null)
//                .commit();
//    }

    /*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem logToggle = menu.findItem(R.id.menu_toggle_log);
        logToggle.setVisible(findViewById(R.id.sample_output) instanceof ViewAnimator);
        logToggle.setTitle(mLogShown ? R.string.sample_hide_log : R.string.sample_show_log);

        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.menu_toggle_log:
                mLogShown = !mLogShown;
                ViewAnimator output = (ViewAnimator) findViewById(R.id.sample_output);
                if (mLogShown) {
                    output.setDisplayedChild(1);
                } else {
                    output.setDisplayedChild(0);
                }
                supportInvalidateOptionsMenu();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    */
}
