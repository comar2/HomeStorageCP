package it.comar.admin.homestroragecp;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.plus.PlusOneButton;

/**
 * A fragment with a Google +1 button.
 * Activities that contain this fragment must implement the
 * {@link OnDrawerItemFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DrawerItemFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DrawerItemFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    private OnDrawerItemFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DrawerItemFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DrawerItemFragment newInstance(String param1, String param2) {
        DrawerItemFragment fragment = new DrawerItemFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public DrawerItemFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_drawer_item, container, false);
//
//        //Find the +1 button
//        mPlusOneButton = (PlusOneButton) view.findViewById(R.id.plus_one_button);
//
//        return view;

//        final MainActivityFragmentAdapter adapter = new MainActivityFragmentAdapter(this.getActivity());
        ListView listView = (ListView) view.findViewById(R.id.listView);

//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
  //              android.R.layout.simple_list_item_1, CassettiUrl.getCassettoUrl(2,this.getActivity()));
        DrawersListAdapter ladapt = new DrawersListAdapter(getActivity());

        listView.setAdapter(ladapt);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView parent, View view, int position, long id) {

                String selectedValue = (String) parent.getAdapter().getItem(position).toString();

                // When clicked, show a toast with the TextView text
                Toast.makeText(getActivity(), "selectedValue: " + selectedValue + "; position: " + Integer.toString(position), Toast.LENGTH_SHORT).show();
                if (mListener != null) {
                    mListener.OnDrawerItemFragmentInteraction(position);
                }

            }
        });

//        listView.setOnScrollListener(new SampleScrollListener(activity));
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
//                String url = adapter.getItem(position);
//                activity.showDetails(url);
//            }
//        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

    }

//    // TODO: Rename method, update argument and hook method into UI event
//    public void onButtonPressed(Uri uri) {
//        if (mListener != null) {
//            mListener.OnDrawerItemFragmentInteraction(uri);
//        }
//    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnDrawerItemFragmentInteractionListener) activity;
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


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnDrawerItemFragmentInteractionListener {
        // TODO: Update argument type and name
        public void OnDrawerItemFragmentInteraction(int position);
    }

}
