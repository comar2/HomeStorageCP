package it.comar.admin.homestroragecp;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

//import android.support.v4.content.ContextCompat;

import java.io.File;


/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragmentRight_ListItems extends Fragment {

    public MainActivityFragmentRight_ListItems() {
    }

    public static MainActivityFragmentRight_ListItems newInstance() {
        return new MainActivityFragmentRight_ListItems();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //CassettiUrl.getCassettoUrl(2,this.getActivity());

        if (isExternalStorageReadable()) {
            Log.d("Files", "isExternalStorageReadable: true" );
        }
        else Log.d("Files", "isExternalStorageReadable: false." );

        File privf = this.getActivity().getApplicationContext().getFilesDir();
        Log.d("Files", "this.getActivity().getApplicationContext().getFilesDir(): " + privf.getAbsolutePath().toString());
        File listf[] = privf.listFiles();
        String privnames[] = privf.list();


        File extf = this.getActivity().getApplicationContext().getExternalFilesDir("cassetti");
        Log.d("Files", "this.getActivity().getApplicationContext().getExternalFilesDir(null): " + extf.getAbsolutePath().toString());
        File extlist[] = extf.listFiles();
        String extnames[] = extf.list();

        File pubf = this.getActivity().getApplicationContext().getDir("pippo", Context.MODE_WORLD_WRITEABLE);
        Log.d("Files", "this.getActivity().getApplicationContext().getDir(\"pippo\", Context.MODE_WORLD_WRITEABLE)): " + pubf.getAbsolutePath().toString());
        if (! pubf.exists()) {
            pubf.mkdir();
        }
        else {
            String abs = new String (pubf.getAbsolutePath().toString());
        }


        String path = Environment.getExternalStorageDirectory().toString()+"/Download";
        Log.d("Files", "Path: " + path);

        File root = new File(Environment
                .getExternalStorageDirectory()
                .getAbsolutePath());
       /*
        File f = new File(path);

        File file[] = f.listFiles();
        Log.d("Files", "Size: "+ file.length);
        for (int i=0; i < file.length; i++)
        {
            Log.d("Files", "FileName:" + file[i].getName());
        }
*/
        File f = new File(path);
        File[] r = root.listFiles();

        if (r != null) {

            for (File inrFile : r) {
                if (inrFile.isDirectory()) {
                    // is directory
                    Log.d("Files", "DirName:" + inrFile.getName());
                }
                if (inrFile.isFile()) {
                    // is directory
                    Log.d("Files", "FileName:" + inrFile.getName());
                }
            }
            File files[] = f.listFiles();
            String filename[] = f.list();
            for (File inFile : files) {
                if (inFile.isDirectory()) {
                    // is directory
                    Log.d("Files", "DirName:" + inFile.getName());
                }
                if (inFile.isFile()) {
                    // is directory
                    Log.d("Files", "FileName:" + inFile.getName());
                }
            }
        }

        final MainActivityFragmentAdapter adapter = new MainActivityFragmentAdapter(this.getActivity());

        ListView listView = (ListView) LayoutInflater.from(this.getActivity())
                .inflate(R.layout.sample_list_detail_list, container, false);
        listView.setAdapter(adapter);

//        listView.setOnScrollListener(new SampleScrollListener(activity));
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
//                String url = adapter.getItem(position);
//                activity.showDetails(url);
//            }
//        });
        return listView;
        //return inflater.inflate(R.layout.fragment_main, container, false);
        //return inflater.inflate(R.layout.sample_list_detail_list, container, false);

    }

    /* Checks if external storage is available for read and write */
    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    /* Checks if external storage is available to at least read */
    public boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        }
        return false;
    }



}
