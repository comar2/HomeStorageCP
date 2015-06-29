package it.comar.admin.homestroragecp;

import android.app.Application;
import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by admin on 10/06/2015.
 */
final class CassettiUrl {
    static protected Context context;
    static protected final String mainfolderName = "cassetti";
    static protected final String folderPrefix = "c";
    static protected final String urlPrefix = "file://";

    public static String[] getCassettoUrlNew(int cass, Context appcontext){
        final String mainfldPath; //main folder path
        final String drawerPath;
        boolean ret;
        List<String> urlList = new ArrayList<String>();
        File[] files;
        String stmp;


        context = appcontext.getApplicationContext();
        File fexd = context.getExternalFilesDir(mainfolderName);

        //TODO verificare di avere permessi lettura scrittura
        if (!fexd.exists()){
            ret = fexd.mkdirs();
        }
        else if (fexd.isFile()){
            ret = fexd.delete();
            ret = fexd.mkdirs();
        }

        mainfldPath =  new String(fexd.getAbsolutePath());
        drawerPath = mainfldPath + File.separator + mainfolderName + File.separator + folderPrefix + String.format("%02d",cass);
        fexd = new File(drawerPath);
        if (!fexd.exists()){
            ret = fexd.mkdirs();
        }
        else if (fexd.isFile()){
            ret = fexd.delete();
            ret = fexd.mkdirs();
        }

        if (!fexd.isDirectory())
        {
            fexd.mkdirs();
        }
        else {
            //urls = fexd.list();
            files = fexd.listFiles();
            if (files != null) {
                for (File file : files) {
                    stmp = file.getAbsolutePath();
                   urlList.add(urlPrefix+file.getAbsolutePath());
                }
            }
        }
        String[] urls = new String[urlList.size()];
        urls = urlList.toArray(urls);
        return  urls;

    }

    public static ArrayList<String> getCassettoUrlList(int cass, Context appcontext){
        final String mainfldPath; //main folder path
        final String drawerPath;
        boolean ret;
        ArrayList<String> urlList = new ArrayList<String>();
        File[] files;
        String stmp;


        context = appcontext.getApplicationContext();
        File fexd = context.getExternalFilesDir(mainfolderName);

        //TODO verificare di avere permessi lettura scrittura
        if (!fexd.exists()){
            ret = fexd.mkdirs();
        }
        else if (fexd.isFile()){
            ret = fexd.delete();
            ret = fexd.mkdirs();
        }

        mainfldPath =  new String(fexd.getAbsolutePath());
        drawerPath = mainfldPath + File.separator + mainfolderName + File.separator + folderPrefix + String.format("%02d",cass);
        fexd = new File(drawerPath);
        if (!fexd.exists()){
            ret = fexd.mkdirs();
        }
        else if (fexd.isFile()){
            ret = fexd.delete();
            ret = fexd.mkdirs();
        }

        if (!fexd.isDirectory())
        {
            fexd.mkdirs();
        }
        else {
            //urls = fexd.list();
            files = fexd.listFiles();
            if (files != null) {
                for (File file : files) {
                    stmp = file.getAbsolutePath();
                    urlList.add(urlPrefix+file.getAbsolutePath());
                }
            }
        }
        //String[] urls = new String[urlList.size()];
        //urls = urlList.toArray(urls);
        return  urlList;

    }

    public static String[] getCassettoUrl(int cass, Context appcontext){
        context = appcontext.getApplicationContext();

        String[] urls = new String[]{"cc", "dd"};

        //File fdd = Environment.getDataDirectory();// .getActivity().getApplicationContext().getExternalFilesDir("cassetti");
        File fdd =  context.getFilesDir();
        //File fesd = Environment.getExternalStorageDirectory(); //this.getActivity().getApplicationContext().getExternalFilesDir("cassetti");
        File fesd = context.getExternalFilesDir("cassetti");

        boolean ret;
        String dataDir;
        String extDir;
        String stmp;
        List<String> pathList = new ArrayList<String>();
        File[] files;
        dataDir = new String(fdd.getAbsolutePath());
        extDir  = new String(fesd.getAbsolutePath());
        fesd = new File(dataDir+File.separator+"cassetti"+File.separator+"c01");
        ret = fesd.mkdirs();
        ret = fesd.isFile();
        ret = fesd.isDirectory();
        ret = fesd.exists();
        ret = fesd.isHidden();
        ret = fesd.canRead();
        ret = fesd.canWrite();
        ret = fesd.setReadable(true, false);
        ret = fesd.setWritable(true,false);
        //ret = fesd.delete();
        if (!fesd.isDirectory())
        {
            fesd.mkdirs();
        }
        else {
            files = fesd.listFiles();
            if (files != null) {
                for (File file : files) {
                    stmp = file.getAbsolutePath();
                    pathList.add(file.getAbsolutePath());
                }
            }
        }

        fesd = new File(dataDir+File.separator+"zippo");
        if (!fesd.isDirectory())
        {
            fesd.mkdir();
        }

        ret = true;
        stmp = new String(dataDir+File.separator+"cassetti"+File.separator+"c01");
        fesd  = new File(stmp);
        ret = fesd.isFile();
        ret = fesd.isDirectory();
        ret = fesd.exists();
        ret = fesd.isHidden();
        ret = fesd.canRead();
        ret = fesd.setReadable(true);
        ret = fesd.setWritable(true);
        //ret = fesd.delete();
        stmp = new String(dataDir+File.separator+"cassetti"+File.separator+"images-1.jpeg");
        fesd  = new File(stmp);
        ret = fesd.isFile();
        ret = fesd.isDirectory();
        ret = fesd.exists();
        ret = fesd.isHidden();
        ret = fesd.setReadable(true, false);
        ret = fesd.setWritable(true, false);
        ret = fesd.delete();
        stmp = new String(dataDir+File.separator+"cassetti"+File.separator+"c02");
        fesd  = new File(stmp);
        ret = fesd.setReadable(true, false);
        ret = fesd.setWritable(true, false);
        //ret = fesd.delete();
        stmp = new String(dataDir+File.separator+"cassetti"+File.separator+"cc");
        fesd  = new File(stmp);
        ret = fesd.setReadable(true, false);
        ret = fesd.setWritable(true, false);
        //ret = fesd.delete();

        //stmp = new String(extDir+File.separator+"cassetti"+File.separator+"c01");
        stmp = new String(extDir+File.separator+"c01");
        fesd  = new File(stmp);

        if (fesd.isDirectory())
        {
            urls = fesd.list();

            files = fesd.listFiles();
            if (files != null) {
                for (File file : files) {
                    stmp = file.getAbsolutePath();
                    pathList.add("file://"+file.getAbsolutePath());
                }
            }
        }
        urls = pathList.toArray(urls);
        return  urls;
    }

    public static ArrayList<String> getCassettoUrl2(int cass, Context appcontext){
        context = appcontext.getApplicationContext();

        String[] urls = new String[]{"cc", "dd"};
        ArrayList<String>  urlsList = new ArrayList<String>();

        //File fdd = Environment.getDataDirectory();// .getActivity().getApplicationContext().getExternalFilesDir("cassetti");
        File fdd =  context.getFilesDir();
        //File fesd = Environment.getExternalStorageDirectory(); //this.getActivity().getApplicationContext().getExternalFilesDir("cassetti");
        File fesd = context.getExternalFilesDir("cassetti");

        boolean ret;
        String dataDir;
        String extDir;
        String stmp;
        List<String> pathList = new ArrayList<String>();
        File[] files;
        dataDir = new String(fdd.getAbsolutePath());
        extDir  = new String(fesd.getAbsolutePath());
        fesd = new File(dataDir+File.separator+"cassetti"+File.separator+"c01");
        ret = fesd.mkdirs();
        ret = fesd.isFile();
        ret = fesd.isDirectory();
        ret = fesd.exists();
        ret = fesd.isHidden();
        ret = fesd.canRead();
        ret = fesd.canWrite();
        ret = fesd.setReadable(true, false);
        ret = fesd.setWritable(true,false);
        //ret = fesd.delete();
        if (!fesd.isDirectory())
        {
            fesd.mkdirs();
        }
        else {
            files = fesd.listFiles();
            if (files != null) {
                for (File file : files) {
                    stmp = file.getAbsolutePath();
                    pathList.add(file.getAbsolutePath());
                }
            }
        }

        fesd = new File(dataDir+File.separator+"zippo");
        if (!fesd.isDirectory())
        {
            fesd.mkdir();
        }

        ret = true;
        stmp = new String(dataDir+File.separator+"cassetti"+File.separator+"c01");
        fesd  = new File(stmp);
        ret = fesd.isFile();
        ret = fesd.isDirectory();
        ret = fesd.exists();
        ret = fesd.isHidden();
        ret = fesd.canRead();
        ret = fesd.setReadable(true);
        ret = fesd.setWritable(true);
        //ret = fesd.delete();
        stmp = new String(dataDir+File.separator+"cassetti"+File.separator+"images-1.jpeg");
        fesd  = new File(stmp);
        ret = fesd.isFile();
        ret = fesd.isDirectory();
        ret = fesd.exists();
        ret = fesd.isHidden();
        ret = fesd.setReadable(true, false);
        ret = fesd.setWritable(true, false);
        ret = fesd.delete();
        stmp = new String(dataDir+File.separator+"cassetti"+File.separator+"c02");
        fesd  = new File(stmp);
        ret = fesd.setReadable(true, false);
        ret = fesd.setWritable(true, false);
        //ret = fesd.delete();
        stmp = new String(dataDir+File.separator+"cassetti"+File.separator+"cc");
        fesd  = new File(stmp);
        ret = fesd.setReadable(true, false);
        ret = fesd.setWritable(true, false);
        //ret = fesd.delete();

        //stmp = new String(extDir+File.separator+"cassetti"+File.separator+"c01");
        stmp = new String(extDir+File.separator+"c01");
        fesd  = new File(stmp);

        if (fesd.isDirectory())
        {
            urls = fesd.list();

            files = fesd.listFiles();
            if (files != null) {
                for (File file : files) {
                    stmp = file.getAbsolutePath();
                    pathList.add("file://"+file.getAbsolutePath());
                }
            }
        }
        urls = pathList.toArray(urls);
        Collections.addAll(urlsList,urls);
        return  urlsList;
    }

    private CassettiUrl() {
        // No instances.
    }
}
