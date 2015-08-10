package it.comar.admin.homestroragecp;

/**
 * Created by Fabrizio on 06/08/2015.
 */

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;



public class AggiungiOggettoDialog extends DialogFragment {

    private View view;
    private static final int REQUEST_CODE = 1;

    private Uri uriimmagine;

    private static final String ARG_NUMCASSETTO = "numcassetto";
    //Parametri che vengono ricevuti in ingresso con la funzione newInstance
    private int numcassetto;

    public static AggiungiOggettoDialog newInstance(int numero_cassetto) {
        AggiungiOggettoDialog fragment = new AggiungiOggettoDialog();
        Bundle args = new Bundle();
        args.putInt(ARG_NUMCASSETTO, numero_cassetto);
        //si salvano i parametri in modo che restino costanti anche se si riesegue oncreate o simili
        fragment.setArguments(args);
        return fragment;
    }

    public String getNome(){
        EditText editText = (EditText) view.findViewById(R.id.editTextNomeOggetto);
        return editText.getText().toString();
    }

    public Uri getUriImmagine(){

        return uriimmagine;
    }

    public int getNumcassetto(){
        return numcassetto;
    }

    public AggiungiOggettoDialog() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //recupero i dati passati alla crezione del fragment.
        if (getArguments() != null) {
            numcassetto = getArguments().getInt(ARG_NUMCASSETTO);
        }
    }


    @Override
    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();
        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        view=inflater.inflate(R.layout.dialog_aggiunta_oggetto, null);
        builder.setView(view);
        //non può uscire col tasto indietro(non funziona...)
        builder.setCancelable(false);


        builder.setPositiveButton(R.string.aggiungi_oggetto_a_cassetto, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                aoListener.onDialogPositiveClick(AggiungiOggettoDialog.this);
            }
        })
                .setNegativeButton(R.string.annulla, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        aoListener.onDialogNegativeClick(AggiungiOggettoDialog.this);
                    }
                });
        // Create the AlertDialog object and return it
        AlertDialog AD = builder.create();

        Button bottone_scelta_immagine =(Button) view.findViewById(R.id.btn_scelta_immagine);

        bottone_scelta_immagine.setOnClickListener(
                (new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        Intent intent = new Intent();
                        intent.setType("image/*");
                        intent.setAction(Intent.ACTION_GET_CONTENT);
                        startActivityForResult(Intent.createChooser(intent, "Select picture"), REQUEST_CODE);
                        System.out.println("intent ricerca immagine avviato");
                    }
                }
                )
        );

        return AD;
    }

    /* The activity that creates an instance of this dialog fragment must
     * implement this interface in order to receive event callbacks.
     * Each method passes the DialogFragment in case the host needs to query it. */
    public interface AggiungiOggettoListener {
        void onDialogPositiveClick(DialogFragment dialog);
        void onDialogNegativeClick(DialogFragment dialog);
    }

    // Use this instance of the interface to deliver action events
    AggiungiOggettoListener aoListener;

    // Override the Fragment.onAttach() method to instantiate the AggiungiOggettoListener
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the AggiungiOggettoListener so we can send events to the host
            aoListener = (AggiungiOggettoListener) activity;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(activity.toString()
                    + " must implement AggiungiOggettoListener");
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (requestCode == REQUEST_CODE) {
            // Make sure the request was successful
            if (resultCode == Activity.RESULT_OK) {
                // The user picked a photo
                // The Intent's data Uri identifies which contact was selected.

                uriimmagine=data.getData();
                System.out.print(uriimmagine.toString());
            }
        }
    }
}
