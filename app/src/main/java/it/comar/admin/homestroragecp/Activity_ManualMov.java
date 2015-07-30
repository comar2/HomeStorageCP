package it.comar.admin.homestroragecp;

import it.comar.arduino.service.AdkService;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.NumberPicker.OnValueChangeListener;
import android.widget.TextView;


//import com.example.test01rfid.adktoolkit.*;


public class Activity_ManualMov extends Activity implements OnClickListener {
    private TextView textView_adk;
    private TextView textView_adkmsg;
    private TextView textView_LogArduino;
    private AutoCompleteTextView autoTextServiceView;
    private Button btnSendCommand;
    private Button btnChiamaCassetto;
    private Button btnResetCassetti;

    private Button btnStartCicloAuto;
    private Button btnStopCicloAuto;

    private Button btnStop;


    private NumberPicker numCassetto_Picker;

    private Integer cassettoselez = 1;

    private static final String USB_ACCESSORY_ATTACHED = "android.hardware.usb.action.USB_ACCESSORY_ATTACHED";

//	  protected AdkManager localAdkManager;


    private BroadcastReceiver receiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            Log.d("ACTION", "Action: = " + action);
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                String string = bundle.getString(AdkService.MSG_COMMAND);
                String adkstring = bundle.getString(AdkService.MSG_ADK);

//	        int resultCode = bundle.getInt(AdkServiceOld.RESULT);
//	        if (resultCode == RESULT_OK) {
//	          Toast.makeText(MainActivity.this,
//	              "Download complete. Download URI: " + string,
//	              Toast.LENGTH_LONG).show();
//	          textView.setText("Download done");
//	        } else {
//	          Toast.makeText(MainActivity.this, "Download failed",
//	              Toast.LENGTH_LONG).show();
//	          textView.setText("Download failed");
//	        }
                if (string != null) textView_adk.setText(string);
                if (adkstring != null) {
                    //textView_adkmsg.setText(adkstring);
                    StringBuilder sbuilder = new StringBuilder(textView_LogArduino.getText());
                    sbuilder.append("\n" + adkstring);
                    textView_LogArduino.setText(sbuilder.toString());
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mov_manuali);

//		localAdkManager = new AdkManager((UsbManager) getSystemService(Context.USB_SERVICE));
//		//register a BroadcastReceiver to catch UsbManager.ACTION_USB_ACCESSORY_DETACHED action
//		registerReceiver(mAdkManager.getUsbReceiver(), mAdkManager.getDetachedFilter());

        if (getIntent().getAction() != null && getIntent().getAction().equals(USB_ACCESSORY_ATTACHED)) {
            Intent service = new Intent(this, AdkService.class);
            service.putExtras(getIntent());
            startService(service);

            //Intent launch = new Intent(this, MainActivity.class);
            Intent launch = new Intent(this, Activity_ManualMov.class);
            launch.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(launch);
        }

        registerReceiver(receiver, new IntentFilter(AdkService.NOTIFICATION));

        textView_adk = (TextView) findViewById(R.id.textView_adk);
        textView_LogArduino = (TextView) findViewById(R.id.textViewArduinoLog);

        textView_LogArduino.setMovementMethod(new ScrollingMovementMethod());
        autoTextServiceView = (AutoCompleteTextView) findViewById(R.id.autoTextServiceView);
        btnSendCommand = (Button) findViewById(R.id.btnSendCommand);
        btnSendCommand.setOnClickListener(this);
        btnChiamaCassetto = (Button) findViewById(R.id.btnChiamaCassetto);
        btnChiamaCassetto.setOnClickListener(this);
        btnResetCassetti = (Button) findViewById(R.id.btnResetCassetti);
        btnResetCassetti.setOnClickListener(this);

        btnStartCicloAuto = (Button) findViewById(R.id.btnStartCicloAuto);
        btnStartCicloAuto.setOnClickListener(this);
        btnStopCicloAuto = (Button) findViewById(R.id.btnStopCicloAuto);
        btnStopCicloAuto.setOnClickListener(this);

        btnStop = (Button) findViewById(R.id.btnStop);
        btnStop.setOnClickListener(this);

        numCassetto_Picker = (NumberPicker) findViewById(R.id.numCassetto_Picker);
        numCassetto_Picker.setMinValue(1);
        numCassetto_Picker.setMaxValue(19);
        numCassetto_Picker.setWrapSelectorWheel(false);
        numCassetto_Picker.setOnValueChangedListener(new OnValueChangeListener() {

            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                // TODO Auto-generated method stub
                Log.d("onValueChange", "numCassetti: " + AdkService.getNumCassetti());
                if (newVal > 0 && newVal <= AdkService.getNumCassetti()) {
                    cassettoselez = newVal - 1;
                }
            }
        });
        numCassetto_Picker.setWrapSelectorWheel(true);

        Intent intent = new Intent(this, AdkService.class);
        // add infos for the service which file to download and where to store
        //intent.putExtra(AdkServiceOld.GENERIC_MSG, "index.html");
        startService(intent);

        textView_adk.setText("Service started");

    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onPause() {
        super.onPause();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);

    }

    @Override
    public void onClick(View v) {
        if (v == btnSendCommand) {
            String msg = autoTextServiceView.getText().toString();
            Intent intent = new Intent(AdkService.SEND_ADK_STRING);
            intent.putExtra(AdkService.MSG_COMMAND, autoTextServiceView.getText().toString());
            sendBroadcast(intent);
        }
        if (v == btnChiamaCassetto) {
            String command = AdkService.SEND_MSG_CHIAMA_CASSETTO;
            String params = cassettoselez.toString();
            Intent intent = new Intent(AdkService.SEND_ADK_STRING);
            intent.putExtra(AdkService.MSG_COMMAND, command);
            intent.putExtra(AdkService.MSG_PARAMS, params);
            sendBroadcast(intent);
        }
        if (v == btnResetCassetti) {
            String command = AdkService.SEND_MSG_RESET_CASSETTI;
            Intent intent = new Intent(AdkService.SEND_ADK_STRING);
            intent.putExtra(AdkService.MSG_COMMAND, command);
            sendBroadcast(intent);
        }
        if (v == btnStartCicloAuto) {
            String command = AdkService.SEND_MSG_START_CICLO_AUTO;
            Intent intent = new Intent(AdkService.SEND_ADK_STRING);
            intent.putExtra(AdkService.MSG_COMMAND, command);
            sendBroadcast(intent);
        }
        if (v == btnStopCicloAuto) {
            String command = AdkService.SEND_MSG_STOP_CICLO_AUTO;
            Intent intent = new Intent(AdkService.SEND_ADK_STRING);
            intent.putExtra(AdkService.MSG_COMMAND, command);
            sendBroadcast(intent);
        }
        if (v == btnStop) {
            String command = AdkService.SEND_MSG_STOP;
            Intent intent = new Intent(AdkService.SEND_ADK_STRING);
            intent.putExtra(AdkService.MSG_COMMAND, command);
            sendBroadcast(intent);
        }
    }

}
