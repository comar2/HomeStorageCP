package it.comar.admin.homestroragecp;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

//import android.app.Fragment;
//import android.app.FragmentManager;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import fr.castorflex.android.verticalviewpager.VerticalViewPager;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DrawersScrollVertFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DrawersScrollVertFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DrawersScrollVertFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DrawersScrollVertFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DrawersScrollVertFragment newInstance(String param1, String param2) {
        DrawersScrollVertFragment fragment = new DrawersScrollVertFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public DrawersScrollVertFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        ArrayList<String> listaCassetti;
        listaCassetti = ConfigArmadio.getDrawersNamesList();
        Random rand = new Random();

        // BEGIN_INCLUDE (populate_tabs)
        /**
         * Populate our tab list with tabs. Each item contains a title, indicator color and divider
         * color, which are used by {@link SlidingTabLayout}.
         */
        int index = 1;
        for (String itm : listaCassetti) {
            mTabs.add(new SamplePagerItem(itm,
                    Color.rgb(rand.nextInt(200) + 55, rand.nextInt(200) + 55, rand.nextInt(255)),
                    Color.rgb(rand.nextInt(200) + 55, rand.nextInt(255), rand.nextInt(255)),
                    index));
            index++;
        }

//        mTabs.add(new SamplePagerItem(
//                "PAG 1",//getString(R.string.tab_stream), // Title
//                Color.BLUE, // Indicator color
//                Color.GRAY // Divider color
//        ));
//
//        mTabs.add(new SamplePagerItem(
//                "PAG 2",//getString(R.string.tab_messages), // Title
//                Color.RED, // Indicator color
//                Color.GRAY // Divider color
//        ));
//
//        mTabs.add(new SamplePagerItem(
//                "PAG 3",//getString(R.string.tab_photos), // Title
//                Color.YELLOW, // Indicator color
//                Color.GRAY // Divider color
//        ));
//
//        mTabs.add(new SamplePagerItem(
//                "PAG 4",//getString(R.string.tab_notifications), // Title
//                Color.GREEN, // Indicator color
//                Color.GRAY // Divider color
//        ));
//
//        mTabs.add(new SamplePagerItem(
//                "PAG 5",//getString(R.string.tab_notifications), // Title
//                Color.LTGRAY, // Indicator color
//                Color.BLUE // Divider color
//        ));
//        // END_INCLUDE (populate_tabs)

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_drawers_scroll_vert, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        Bundle args = getArguments();
//        if (args != null) {
//            TextView title = (TextView) view.findViewById(R.id.textView);
//            title.setText("Title: " + args.getCharSequence(KEY_TITLE));
//        }

        VerticalViewPager verticalViewPager = (VerticalViewPager) view.findViewById(R.id.verticalviewpager);

        verticalViewPager.setAdapter(new SampleFragmentPagerAdapter(getFragmentManager()));
        //verticalViewPager.setPageMargin(getResources().getDimensionPixelSize(R.dimen.pagemargin));
        verticalViewPager.setPageMarginDrawable(new ColorDrawable(getResources().getColor(android.R.color.holo_green_dark)));
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnDrawerItemFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public void updateScrollItemView(int position) {
        VerticalViewPager verticalViewPager = (VerticalViewPager) getActivity().findViewById(R.id.verticalviewpager);
        verticalViewPager.setCurrentItem(position, true);

    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

    /**
     * This class represents a tab to be displayed by {@link ViewPager} and it's associated
     * {@link DrawersScrollVertFragment}.
     */
    static class SamplePagerItem {
        private final CharSequence mTitle;
        private final int mIndicatorColor;
        private final int mDividerColor;
        private final int mPos;

        SamplePagerItem(CharSequence title, int indicatorColor, int dividerColor, int pos) {
            mTitle = title;
            mIndicatorColor = indicatorColor;
            mDividerColor = dividerColor;
            mPos = pos;
        }

        /**
         * @return A new {@link Fragment} to be displayed by a {@link ViewPager}
         */
        Fragment createFragment() {
            return Drawer_ContentFragment.newInstance(mTitle, mIndicatorColor, mDividerColor, mPos);
        }

        /**
         * @return the title which represents this tab. In this sample this is used directly by
         * {@link android.support.v4.view.PagerAdapter#getPageTitle(int)}
         */
        CharSequence getTitle() {
            return mTitle;
        }

        /**
         * @return the color to be used for indicator on the {@link DrawersScrollVertFragment}
         */
        int getIndicatorColor() {
            return mIndicatorColor;
        }

        /**
         * @return the color to be used for right divider on the {@link DrawersScrollVertFragment}
         */
        int getDividerColor() {
            return mDividerColor;
        }
    }

    /**
     * Simple Fragment used to display some meaningful content for each page in the sample's
     * {@link android.support.v4.view.ViewPager}.
     */
    public static class Drawer_ContentFragment extends Fragment {

        private static final String KEY_TITLE = "title";
        private static final String KEY_INDICATOR_COLOR = "indicator_color";
        private static final String KEY_DIVIDER_COLOR = "divider_color";
        private static final String KEY_POS = "drawer_position";

        /**
         * @return a new instance of {@link Drawer_ContentFragment}, adding the parameters into a bundle and
         * setting them as arguments.
         */
//        public static Drawer_ContentFragment newInstance(CharSequence title, int indicatorColor,
//                                                  int dividerColor) {
//            Bundle bundle = new Bundle();
//            bundle.putCharSequence(KEY_TITLE, title);
//            bundle.putInt(KEY_INDICATOR_COLOR, indicatorColor);
//            bundle.putInt(KEY_DIVIDER_COLOR, dividerColor);
//
//            Drawer_ContentFragment fragment = new Drawer_ContentFragment();
//            fragment.setArguments(bundle);
//
//            return fragment;
//        }
        public static Drawer_ContentFragment newInstance(CharSequence title, int indicatorColor,
                                                         int dividerColor, int pos) {
            Bundle bundle = new Bundle();
            bundle.putCharSequence(KEY_TITLE, title);
            bundle.putInt(KEY_INDICATOR_COLOR, indicatorColor);
            bundle.putInt(KEY_DIVIDER_COLOR, dividerColor);
            bundle.putInt(KEY_POS, pos);

            Drawer_ContentFragment fragment = new Drawer_ContentFragment();
            fragment.setArguments(bundle);

            return fragment;
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            int drawerPos = 0;
            Bundle args = getArguments();
            if (args != null) {
                if (args.containsKey(KEY_POS)) {
                    drawerPos = args.getInt(KEY_POS);
                } else {
                    drawerPos = 2;
                }

            }

            // Inflate the layout for this fragment
            View view = inflater.inflate(R.layout.drawer_content_page, container, false);
            ListView drawerItems = (ListView) view.findViewById(R.id.listView1);

            //ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, CassettiUrl.getCassettoUrl(drawerPos,this.getActivity()));
            //DrawersListAdapter ladapt = new DrawersListAdapter(getActivity());
            DrawerItemsAdapter ladapt = new DrawerItemsAdapter(getActivity(), drawerPos);

            drawerItems.setAdapter(ladapt);


            return view;
        }

        @Override
        public void onViewCreated(View view, Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);

            Bundle args = getArguments();

            if (args != null) {
                TextView title = (TextView) view.findViewById(R.id.item_title);
                title.setText("Title: " + args.getCharSequence(KEY_TITLE));

                int indicatorColor = args.getInt(KEY_INDICATOR_COLOR);
                TextView indicatorColorView = (TextView) view.findViewById(R.id.item_indicator_color);
                indicatorColorView.setText("Indicator: #" + Integer.toHexString(indicatorColor));
                indicatorColorView.setTextColor(indicatorColor);

                int dividerColor = args.getInt(KEY_DIVIDER_COLOR);
                TextView dividerColorView = (TextView) view.findViewById(R.id.item_divider_color);
                dividerColorView.setText("Divider: #" + Integer.toHexString(dividerColor));
                dividerColorView.setTextColor(dividerColor);
            }
        }
    }

    /**
     * List of {@link SamplePagerItem} which represent this sample's tabs.
     */
    private List<SamplePagerItem> mTabs = new ArrayList<SamplePagerItem>();

    /**
     * The {@link FragmentPagerAdapter} used to display pages in this sample. The individual pages
     * are instances of {@link Drawer_ContentFragment} which just display three lines of text. Each page is
     * created by the relevant {@link SamplePagerItem} for the requested position.
     * <p>
     * The important section of this class is the {@link #getPageTitle(int)} method which controls
     * what is displayed in the {@link DrawersScrollVertFragment}.
     */
    class SampleFragmentPagerAdapter extends FragmentPagerAdapter {

        SampleFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        /**
         * Return the {@link android.support.v4.app.Fragment} to be displayed at {@code position}.
         * <p>
         * Here we return the value returned from {@link SamplePagerItem#createFragment()}.
         */
        @Override
        public Fragment getItem(int i) {
            return mTabs.get(i).createFragment();
        }

        @Override
        public int getCount() {
            return mTabs.size();
        }

        // BEGIN_INCLUDE (pageradapter_getpagetitle)

        /**
         * Return the title of the item at {@code position}. This is important as what this method
         * returns is what is displayed in the {@link DrawersScrollVertFragment}.
         * <p>
         * Here we return the value returned from {@link SamplePagerItem#getTitle()}.
         */
        @Override
        public CharSequence getPageTitle(int position) {
            return mTabs.get(position).getTitle();
        }
        // END_INCLUDE (pageradapter_getpagetitle)

    }
}
