package it.comar.admin.homestroragecp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

final class DrawerItemsAdapter extends BaseAdapter {
    private final Context context;
    private final ArrayList<String> urls;// = new ArrayList<String>();

    public DrawerItemsAdapter(Context context, int pos) {
        this.context = context;
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
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder holder;
        if (view == null) {
            //view = LayoutInflater.from(context).inflate(R.layout.sample_list_detail_item, parent, false);
            view = LayoutInflater.from(context).inflate(R.layout.drawer_list_item, parent, false);
            holder = new ViewHolder();
            holder.image = (ImageView) view.findViewById(R.id.photo);
            holder.text = (TextView) view.findViewById(R.id.url);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        // Get the image URL for the current position.
        String url = getItem(position);

        holder.text.setText(url);

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
