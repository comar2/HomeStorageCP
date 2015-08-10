package it.comar.arduino.service;

/**
 * Created by admin on 30/07/2015.
 */

import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.hardware.usb.UsbAccessory;
import android.hardware.usb.UsbManager;
import android.os.Environment;
import android.os.IBinder;
import android.os.ParcelFileDescriptor;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationCompat.Builder;
import android.util.Log;

import it.comar.admin.homestroragecp.R;

public class AdkService extends Service {

    public static final String NOTIFICATION 	= "com.example.test01.android.service.adkreceiver";
    public static final String SEND_ADK_STRING 	= "com.example.test01.adkservice01.send_adk_string";

    public static final String MSG_COMMAND = "msg_command";
    public static final String MSG_PARAMS = "msg_params";
    public static final String MSG_ADK = "msg_adk";
    public static final String ADK_MANAGER = "adk_manager";

    public static final String PREFS_NAME = "ArduinoPrefsFile";

    private static final char SEND_MSG_START = '<';
    private static final char SEND_MSG_SEP = ',';
    private static final char SEND_MSG_PARSEP = ' '; //parameter separator
    private static final char SEND_MSG_END = '>';
    private static final String SEND_MSG_UDOOREADY = "<UdooReady,1>";		//inviato allo startup per dire ad arduino che l'app android è pronta
    private static final String SEND_MSG_NO_CASSETTI = "NO_CASSETTI";		//inviato allo startup se nelle preferenze non sono memorizzati cassetti
    private static final String SEND_MSG_ID_CASSETTO = "ID_CASSETTO"; 		//inviato allo startup per mandare ad arduino l'elenco cassetti memorizzati con preference
    private static final String SEND_MSG_FINE_CASSETTI = "FINE_CASSETTI";	//inviato allo startup per dire ad arduino che l'elenco cassetti è finito

    public static final String SEND_MSG_CHIAMA_CASSETTO = "CHIAMA_CASSETTO"; //invia messaggio di chiamata cassetto
    public static final String SEND_MSG_RESET_CASSETTI = "RESET_CASSETTI"; //forza avvio ciclo lettura tag e reset dati

    public static final String SEND_MSG_START_CICLO_AUTO = "START_CICLO_AUTO"; //avvio il ciclo di selezione casuale cassetti
    public static final String SEND_MSG_STOP_CICLO_AUTO = "STOP_CICLO_AUTO"; //arresto il ciclo di selezione casuale cassetti
    public static final String SEND_MSG_STOP = "STOP"; //arresto la movimentazione


    private static final String RCV_MSG_RIK_CASSETTI = "RIK_CASSETTI"; 		//messaggio ricevuto di richiesta elenco cassetti
    private static final String RCV_MSG_START_LETTURA_TAG_CASSETTI = "START_LETTURA_TAG_CASSETTI"; //messaggio ricevuto da ciclo lettura cassetti

    private static final String RCV_MSG_NEW_ID_CASSETTO = "NEW_ID_CASSETTO"; //messaggio ricevuto da ciclo lettura cassetti
    private static final String RCV_MSG_FINE_LETTURA_TAG_CASSETTI = "FINE_LETTURA_TAG_CASSETTI"; //messaggio ricevuto al termine del ciclo lettura cassetti, come paramentro ha il num totale di cassetti trovati

    private static final String RCV_MSG_ARDUINO_LOG = "ARDUINO_LOG"; //messaggio ricevuto come log di Arduino

    public static final int notifyID = 1972;

    private String TAG= "AdkService01";


    ///
    private UsbAccessory mAccessory;
    private UsbManager mUsbManager;
    private ParcelFileDescriptor mFileDescriptor;
    private FileInputStream mInputStream;
    private FileOutputStream mOutputStream;


    private Thread thread;

    private Builder mBuilder;
    private NotificationManager mNotificationManager;
    private PendingIntent mPermissionIntent;

    private MyRunnableThread myThread;
    private static int myArmadioNumCassetti = 1;
    private List<String> listTagCassetti = new ArrayList();

    ////


//TODO rimuovere testo
//	// Handler that receives messages from the thread
//	private final class ServiceHandler extends Handler {
//		public ServiceHandler(Looper looper) {
//			super(looper);
//		}
//		@Override
//		public void handleMessage(Message msg) {
//			// Normally we would do some work here, like download a file.
//			// For our sample, we just sleep for 5 seconds.
//			long endTime = System.currentTimeMillis() + 5*1000;
//			while (System.currentTimeMillis() < endTime) {
//				synchronized (this) {
//					try {
//						wait(endTime - System.currentTimeMillis());
//					} catch (Exception e) {
//					}
//				}
//			}
//			// Stop the service using the startId, so that we don't stop
//			// the service in the middle of handling another job
//			stopSelf(msg.arg1);
//		}
//	}


    public AdkService() {
		/*
		 onCreate()
    		The system calls this method when the service is first created,
    		to perform one-time setup procedures (before it calls either onStartCommand() or onBind()).
    		If the service is already running, this method is not called.
		onDestroy()
    		The system calls this method when the service is no longer used and is being destroyed.
    		Your service should implement this to clean up any resources such as threads,
    		registered listeners, receivers, etc. This is the last call the service receives.
		 */
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        //throw new UnsupportedOperationException("Not yet implemented");
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
//		Log.d(TAG, "onStartup " + mAccessory );
//
//		mAccessory = (UsbAccessory) intent.getParcelableExtra(UsbManager.EXTRA_ACCESSORY);
//		if (mAccessory != null) {
//			openAccessory(mAccessory);
//		}
//
//		return super.onStartCommand(intent, flags, startId);


        //TODO do something useful


        return Service.START_NOT_STICKY;

		/*
	     	Service.START_STICKY 				Service is restarted if it gets terminated. Intent data passed to
	     										the onStartCommand method is null. Used for services which manages
	     										their own state and do not depend on the Intent data.

			Service.START_NOT_STICKY 			Service is not restarted. Used for services which are periodically
												triggered anyway. The service is only restarted if the runtime
												has pending startService() calls since the service termination.

			Service.START_REDELIVER_INTENT 		Similar to Service.START_STICKY but the original Intent is
												re-delivered to the onStartCommand method.


			Tip:
			You can check if the service was restarted via the Intent.getFlags() method.
			START_FLAG_REDELIVERY (in case the service was started with Service.START_REDELIVER_INTENT)
			or START_FLAG_RETRY (in case the service was started with Service.START_STICKY) is passed.

		 */
    }

    @Override
    public void onCreate() {
		/*
		 onCreate()
    		The system calls this method when the service is first created,
    		to perform one-time setup procedures (before it calls either onStartCommand() or onBind()).
    		If the service is already running, this method is not called.
		 */
        //registro i receivers
        //registro il PERMISSION RECEIVER
        mPermissionIntent = PendingIntent.getBroadcast(this, 0, new Intent(ACTION_USB_PERMISSION), 0);

        //registro gli intent per il mio broadcast receiver
        IntentFilter filter = new IntentFilter(ACTION_USB_PERMISSION);
        filter.addAction(UsbManager.ACTION_USB_ACCESSORY_DETACHED);
        filter.addAction(SEND_ADK_STRING);
        registerReceiver(mMyBroadcastReceiver, filter);

//		///registro il DETACHED RECEIVER
//		IntentFilter filter2 = new IntentFilter(UsbManager.ACTION_USB_ACCESSORY_DETACHED);
//		registerReceiver(mMyBroadcastReceiver, filter2);

        //rilevo gli accessory presenti


        try
        {
            mUsbManager = (UsbManager) getSystemService(Context.USB_SERVICE);

            UsbAccessory[] accessoryList = mUsbManager.getAccessoryList();
            if (accessoryList != null) {
                mAccessory = (accessoryList == null ? null : accessoryList[0]);
            }

//            HashMap<String, UsbDevice> usbDeviceHashMap = new HashMap<String, UsbDevice>();
//            usbDeviceHashMap = mUsbManager.getDeviceList();
//            if (!usbDeviceHashMap.isEmpty()) {
//                UsbAccessory[] accessoryList = mUsbManager.getAccessoryList();
//                mAccessory = (accessoryList == null ? null : accessoryList[0]);
//            }

        }
        catch (Exception ex ) {
            Log.d(TAG, "class AdkService.onCreate() exception = " + ex.getMessage());
        }

        //Richiedo permesso per usbAccessory
        if (mAccessory != null) {

//			while(!mUsbManager.hasPermission(mAccessory)){
//				mUsbManager.requestPermission(mAccessory, mPermissionIntent);
//			}
//			openAccessory(mAccessory);

            if(!mUsbManager.hasPermission(mAccessory)){
                mUsbManager.requestPermission(mAccessory, mPermissionIntent);
                return;
            }
            else
            {
                openAccessory(mAccessory); //openAccessory invia anche messaggio  UdooReady

            }

        }


        // Start up the thread running the service.  Note that we create a
        // separate thread because the service normally runs in the process's
        // main thread, which we don't want to block.  We also make it
        // background priority so CPU-intensive work will not disrupt our UI.
        //	    HandlerThread thread = new HandlerThread("ServiceStartArguments",
        //	            Process.THREAD_PRIORITY_BACKGROUND);
        //	    thread.start();
        //
        //	    // Get the HandlerThread's Looper and use it for our Handler
        //	    mServiceLooper = thread.getLooper();
        //	    mServiceHandler = new ServiceHandler(mServiceLooper);



        //TODO capire cosa fa
        mNotificationManager =(NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        mBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("ADK Service")
                .setContentText("Started");
        startForeground(notifyID, mBuilder.build());

        super.onCreate();

        ///************************
    }

    private void analizeMessage(String msg) {
        Intent intent = new Intent(NOTIFICATION);
        intent.putExtra(MSG_COMMAND, msg);
        sendBroadcast(intent);  //replico il msg per le activity in ascolto

        String fullmsg = "";
        String cmd = "";
        String par = "";

        //creo espressione regolare per capire se il messaggio ricevuto rispetta i tag di inizio e fine <>
        Pattern pattern = Pattern.compile(SEND_MSG_START + ".+" +SEND_MSG_END);
        // in case you would like to ignore case sensitivity,
        // you could use this statement:
        // Pattern pattern = Pattern.compile("\\s+", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(msg);
        // check all occurance
        while (matcher.find()) {
            System.out.print("Start index: " + matcher.start());
            System.out.print(" End index: " + matcher.end() + " ");
            System.out.println(matcher.group());

            fullmsg = msg.substring(matcher.start() + 1 , matcher.end() - 1);
            //separo comando da parametri
            String arcmd[] = fullmsg.split(",");
            cmd = arcmd.length > 0 ? arcmd[0] : "";
            par = arcmd.length > 1 ? arcmd[1] : "";

        }

        if (cmd.equals(RCV_MSG_RIK_CASSETTI)){
            onMsgRikcassetti();

        }


        //TODO
        if (cmd.equals(RCV_MSG_START_LETTURA_TAG_CASSETTI)){
            onMsgStartLetturaTagCassetti();

        }

        //TODO
        if (cmd.equals(RCV_MSG_NEW_ID_CASSETTO)){
            onMsgNewIdCassetto(par);

        }

        //TODO
        if (cmd.equals(RCV_MSG_FINE_LETTURA_TAG_CASSETTI)){
            onMsgFineLetturaTagCassetti(par);

        }

        //TODO
        if (cmd.equals(RCV_MSG_ARDUINO_LOG)){
            onMsgArduinoLog(par);
        }


    }
    private void onMsgArduinoLog(String par) {
        // TODO Auto-generated method stub
        Log.d(TAG, "onMsgArduinoLog = " + par);
        publishResultsAdk(par);

    }

    private void onMsgFineLetturaTagCassetti(String par) {
        // TODO Auto-generated method stub

        String elecoCassetti = "";

        if (myArmadioNumCassetti == Integer.valueOf(par)){
            //access via Iterator
            Iterator iterator = listTagCassetti.iterator();
            while(iterator.hasNext()){
                elecoCassetti += (String) iterator.next() + ",";
            }

            // We need an Editor object to make preference changes.
            // All objects are from android.context.Context
            SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
            SharedPreferences.Editor editor = settings.edit();
            editor.putString("elencoCassetti", elecoCassetti);

            // Commit the edits!
            editor.commit();
        }
        else {
            Log.d(TAG, "onMsgFineLetturaTagCassetti errore: num cassetti non conforme");
            publishResultsAdk("onMsgFineLetturaTagCassetti errore: num cassetti non conforme");
            //TODO richiesta di rilettura
        }


    }

    private void onMsgNewIdCassetto(String par) {
        // TODO Auto-generated method stub
        Log.d(TAG, "myArmadioNumCassetti = " +myArmadioNumCassetti + "; param: " +par);
        try
        {
            if (listTagCassetti.size() > 0 ){
                if ( !par.equals(listTagCassetti.get(0))  ){
                    publishResultsAdk("listTagCassetti.get(0) = " + listTagCassetti.get(0));
                    listTagCassetti.add(par);
                    publishResultsAdk("onMsgNewIdCassetto tag : " + par + "ar[" +myArmadioNumCassetti +
                            "] = "	+ listTagCassetti.get(myArmadioNumCassetti));
                    myArmadioNumCassetti++;
                }
            }
            else { //primo elemento ricevuto
                listTagCassetti.add(par);
                publishResultsAdk("onMsgNewIdCassetto listTagCassetti.size==0 tag : " + par + "listTagCassetti[" +myArmadioNumCassetti +
                        "] = "	+ listTagCassetti.get(myArmadioNumCassetti));
                myArmadioNumCassetti++;
            }
        }
        catch (Exception ex ) {
            publishResultsAdk("onMsgNewIdCassetto Exception : " + ex.getMessage());
        }

    }

    private void onMsgStartLetturaTagCassetti() {
        // TODO Auto-generated method stub
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        String cassetti = settings.getString("elencoCassetti", "");
        cassetti = "";

        myArmadioNumCassetti = 0;
        listTagCassetti.clear();
    }

    private void onMsgRikcassetti() {
        // TODO Auto-generated method stub
        // Restore preferences
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        String cassetti = settings.getString("elencoCassetti", "");

        if ( ! cassetti.equals("")){
            String cass[] = cassetti.split(",");
            for (int i = 0; i < cass.length; i++){
                sendAdkCommand(SEND_MSG_START + SEND_MSG_ID_CASSETTO +
                        SEND_MSG_SEP + cass[i] + SEND_MSG_PARSEP + i + SEND_MSG_END);
            }
            myArmadioNumCassetti  = cass.length;

            //invio FINE_CASSETTI
            String msg = SEND_MSG_START + SEND_MSG_FINE_CASSETTI +
                    SEND_MSG_SEP + myArmadioNumCassetti + SEND_MSG_END;
            sendAdkCommand(msg);
            Log.d(TAG, "onMsgRikcassetti: " + msg);

        }
        else {
            sendAdkCommand(SEND_MSG_START + SEND_MSG_NO_CASSETTI + SEND_MSG_SEP + "0" + SEND_MSG_END);
//	    	   Random rnd = new Random(888888888);
//	    	   for (int i =0; i < 10; i++){
//	    		   Integer id = rnd.nextInt();
//	    		   java.text.DecimalFormat myFormatter = new java.text.DecimalFormat("0000000000");
//	    		   String rfid = myFormatter.format(Math.abs(id));
//
//
//	    		   sendAdkCommand(SEND_MSG_START + SEND_MSG_ID_CASSETTO +
//	    				   SEND_MSG_SEP + rfid + SEND_MSG_PARSEP + i + SEND_MSG_END);
//
//	    		   String msg = SEND_MSG_START + SEND_MSG_ID_CASSETTO +
//	    				   SEND_MSG_SEP + rfid + SEND_MSG_PARSEP + i + SEND_MSG_END;
//
//	    		   Log.d(TAG, "onMsgRikcassetti: " + msg);
//
//	    		   //if (i==5) i++;
//	    		   if (i == 9){
//	    			 //invio FINE_CASSETTI
//	        		   msg = SEND_MSG_START + SEND_MSG_FINE_CASSETTI +
//	        				   SEND_MSG_SEP + 10 + SEND_MSG_END;
//	        		   sendAdkCommand(msg);
//	        		   Log.d(TAG, "onMsgRikcassetti: " + msg);
//	        		   myArmadioNumCassetti = 10;
//	    		   }
//	    	   }
        }

    }

    private void publishResults(String msg) {
        Intent intent = new Intent(NOTIFICATION);
        intent.putExtra(MSG_COMMAND, msg);
        sendBroadcast(intent);
    }


    private void publishResultsAdk(String msg) {
        Intent intent = new Intent(NOTIFICATION);
        intent.putExtra(MSG_ADK, msg);
        sendBroadcast(intent);
    }

    //
    public class USBControlMyRunnableThread extends MyRunnableThread{

        //		public USBControlMyRunnableThread(Handler ui) {
        //			super(getApplicationContext(), ui);
        //		}
        public USBControlMyRunnableThread(ParcelFileDescriptor parFileDescriptor) {
            //super(getApplicationContext());
            super(parFileDescriptor);
        }

        @Override
        public void onReceive(byte[] msg) {
            String recvmsg;

            recvmsg = msg.toString();

            Log.i("USBCtrlMyRunnableThread", "onReceive: " + recvmsg);

            //			int i = 0;
            //
            //			switch (msg[i+1]) {
            //			case BATTERY_LEVEL:
            //				int battery_level = (int)msg[i+2];
            //				remoteConnection.send("batt="+battery_level);
            //				battery.text.setText("Batt: " + battery_level + "%");
            //				int temp = battery_level/20;
            //				switch(temp){
            //				case 5:
            //					temp = R.drawable.stat_sys_battery_100;
            //					break;
            //				case 4:
            //					temp = R.drawable.stat_sys_battery_80;
            //					break;
            //				case 3:
            //					temp = R.drawable.stat_sys_battery_60;
            //					break;
            //				case 2:
            //					temp = R.drawable.stat_sys_battery_40;
            //					break;
            //				case 1:
            //					temp = R.drawable.stat_sys_battery_20;
            //					break;
            //				case 0:
            //					temp = R.drawable.stat_sys_battery_0;
            //					break;
            //				}
            //				battery.led.setImageResource(temp);
            //
            //				break;
            //			}
        }

        @Override
        public void onNotify(String msg) {
            //console(msg);
            Log.i("USBCtrlMyRunnableThread", "onNotify: " + msg);
        }

        @Override
        public void onConnected() {
            //usb.enable();
            Log.i("USBCtrlMyRunnableThread", "onConnected() " );
        }

        @Override
        public void onDisconnected() {
            Log.i("USBCtrlMyRunnableThread", "onDisconnected() " );
            //TODO
            //usb.pause();
            //finish();
        }

        byte[] msg = new byte[3];

        void setSpeed(int speed){
            //TODO
            //			msg[0] = SYNC;
            //			msg[1] = SPEED;
            //			msg[2] = (byte) speed;
            //			usbConnection.send(msg);
        }

        void setTurn(int turn){
            //TODO
            //			msg[0] = SYNC;
            //			msg[1] = TURN;
            //			msg[2] = (byte)turn;
            //			usbConnection.send(msg);
        }

        void driveEnabled(boolean drive){
            //TODO
            //			msg[0] = SYNC;
            //			msg[1] = DRIVE;
            //			if (drive == true){
            //				msg[2] = ENABLED;
            //				console("Motors Enabled\n");
            //				motors.enable();
            //			}else{
            //				msg[2] = DISABLED;
            //				console("Motors Disabled\n");
            //				motors.disable();
            //			}
            //			usbConnection.send(msg);
            //			remoteConnection.send("drive="+Boolean.toString(drive));
        }

    }

    //fine
    ///runnable
    public abstract class MyRunnableThread implements Runnable{

//		private static final String ACTION_USB_PERMISSION = "com.example.test01.action.USB_PERMISSION";

        //		// An instance of accessory and manager
//		private UsbAccessory mAccessory;
//		private UsbManager mManager;
        boolean connected = false;
        private ParcelFileDescriptor mFileDescriptor;
        private FileInputStream input;
        private FileOutputStream output;


//		private Thread thread;

        private boolean running;
        //private AdkManager mAdkManager;
        private int count = 0;
        private int mByteRead;

//		private String adkmsg = "";
        ///

        ///

//		//Receiver for connect/disconnect events
//		BroadcastReceiver mMyBroadcastReceiver = new BroadcastReceiver() {
//			public void onReceive(Context context, Intent intent) {
//				String action = intent.getAction();
//
//				if (ACTION_USB_PERMISSION.equals(action)) {
//					synchronized (this) {
//						UsbAccessory accessory = (UsbAccessory) intent.getParcelableExtra(UsbManager.EXTRA_ACCESSORY);
//						if (intent.getBooleanExtra(
//								UsbManager.EXTRA_PERMISSION_GRANTED, false)) {
//							openAccessory(accessory);
//						} else {
//
//						}
//
//					}
//				} else if (UsbManager.ACTION_USB_ACCESSORY_DETACHED.equals(action)) {
//					UsbAccessory accessory = (UsbAccessory) intent.getParcelableExtra(UsbManager.EXTRA_ACCESSORY);
//					if (accessory != null && accessory.equals(mAccessory)) {
//						closeAccessory();
//					}
//				}
//
//			}
//		};

        public MyRunnableThread(ParcelFileDescriptor parFileDescriptor/*Context context*//*Context main, Handler ui*/)
        {
            //super("USBControlSender");
            super();

            mFileDescriptor = parFileDescriptor;
            if (mFileDescriptor != null) {
                FileDescriptor fd = mFileDescriptor.getFileDescriptor();
                input = new FileInputStream(fd);
                output = new FileOutputStream(fd);
            }

//			mManager = (UsbManager) context.getSystemService(Context.USB_SERVICE);
//			UsbAccessory[] accessoryList = mManager.getAccessoryList();
//			PendingIntent mPermissionIntent = PendingIntent.getBroadcast(context, 0,new Intent(ACTION_USB_PERMISSION), 0);
//			IntentFilter filter = new IntentFilter(ACTION_USB_PERMISSION);
//			context.registerReceiver(mMyBroadcastReceiver, filter);
//
//			UsbAccessory mAccessory = (accessoryList == null ? null : accessoryList[0]);
//			if (mAccessory != null) {
//
//				while(!mManager.hasPermission(mAccessory)){
//					mManager.requestPermission(mAccessory, mPermissionIntent);
//				}
//				openAccessory(mAccessory);
//
//			}

        }

        public String readString() {
            byte[] buffer = new byte[255];
            StringBuilder stringBuilder = new StringBuilder();

            try {
                mByteRead = input.read(buffer, 0, buffer.length);
                for (int i = 0; i < mByteRead; i++) {
                    stringBuilder.append((char) buffer[i]);
                }
            } catch (IOException e) {
                Log.e(TAG, "readString() -> error:" + e.getMessage());
                closeAccessory();
                running = false;

            }

            return stringBuilder.toString();
        }

        public byte readByte() {
            byte[] buffer = new byte[1];

            try {
                mByteRead = input.read(buffer, 0, buffer.length);
            } catch (IOException e) {
                Log.e(TAG,  "readByte() -> error:" + e.getMessage());
                closeAccessory();
                running = false;
            }

            return buffer[0];
        }

        public boolean serialAvailable() {
            return mByteRead >= 0;
        }

        @Override
        public void run() {
            // TODO Auto-generated method stub

            while(running){
                String mymsg = "";
                try{
                    count++;
                    if ((count % 10) == 1){
                        //publishResults("test_" + count);
                        Log.i("MyRunnableThread", "publishResults(\"test\")" + count);
                    }
                    //Handle incoming messages
                    if (input !=null && count > 500) {
                        mymsg = readString();
                        if (serialAvailable()){
                            Log.i("MyRunnableThread", "message: " + mymsg);
                            analizeMessage(mymsg);
//							publishResults(mymsg);
//							Thread.sleep(1000L);
                        }

                    }
                }
                catch (final Exception e){
                    //					UIHandler.post(new Runnable() {
                    //						public void run() {
                    //							onNotify("USB Receive Failed " + e.toString() + "\n");
                    //							closeAccessory();
                    //						}
                    //					});
                    publishResults("USB Receive Failed " + e.toString() + "\n");
                    closeAccessory();
                    running = false;
                }
            }

        }

        public void start(){
            if(!running){
                running = true;
                thread = new Thread(this, "MyRunnableThread");
                thread.start();
            }
        }

        public void stop(){
            if(running){
                running = false;
                Log.d("MyRunnableThread", "MyRunnableThread stop()," );
                //closeAccessory();
                thread = null;
            }
        }

//		// Sets up filestreams
//		private void openAccessory(UsbAccessory accessory) {
//			mAccessory = accessory;
//			mFileDescriptor = mManager.openAccessory(accessory);
//			if (mFileDescriptor != null) {
//				FileDescriptor fd = mFileDescriptor.getFileDescriptor();
//				input = new FileInputStream(fd);
//				output = new FileOutputStream(fd);
//			}
//			this.start();
//			onConnected();
//		}

//		// Cleans up accessory
//		public void closeAccessory() {
//
//			//halt i/o
//			//TODO
//			//			controlSender.getLooper().quit();
//			//			controlListener.interrupt();
//
//			try {
//				if (mFileDescriptor != null) {
//					mFileDescriptor.close();
//				}
//			} catch (IOException e) {
//			} finally {
//				mFileDescriptor = null;
//				mAccessory = null;
//			}
//
//			onDisconnected();
//		}

//		//Removes the usb receiver
//		public void destroyReceiver() {
//			//TODO
//			//context.unregisterReceiver(mMyBroadcastReceiver);
//
//			Log.i("MyRunnableThread", "destroyReceiver()" );
//		}

        public abstract void onReceive(byte[] msg);

        public abstract void onNotify(String msg);

        public abstract void onConnected();

        public abstract void onDisconnected();

    }
    //fine runnable




    private void openAccessory(UsbAccessory accessory) {
        Log.d(TAG, "openAccessory: " + accessory);
        //UsbManager mUsbManager = (UsbManager) getApplicationContext().getSystemService(Context.USB_SERVICE);

        if (mAccessory != null && mUsbManager != null)
        {
            mFileDescriptor = mUsbManager.openAccessory(accessory);
            if (mFileDescriptor != null) {
                FileDescriptor fd = mFileDescriptor.getFileDescriptor();
                mInputStream = new FileInputStream(fd);
                mOutputStream = new FileOutputStream(fd);
                //thread = new Thread(null, this, "ADKserviceThread");
                //thread.start();

                if (myThread == null) myThread = new USBControlMyRunnableThread(mFileDescriptor);
                myThread.start();

                sendAdkCommand(SEND_MSG_UDOOREADY);

            } // start runnable
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "AdkService -> onDestroy()before closeAcceasory()" );
        closeAccessory();


        stopForeground(true);
        unregisterReceiver(mMyBroadcastReceiver);

    }

    private void closeAccessory() {
        Log.d(TAG, "AdkService01 closeAcceasory()" );
        myThread.stop();
        try {
            if (mFileDescriptor != null) {
                mFileDescriptor.close();
                Log.d(TAG, "AdkService01 closeAcceasory() mFileDescriptor.close()");
            }
        } catch (IOException e) {
            Log.d(TAG, "AdkService01 closeAcceasory() exception: " + e.getMessage());
        } finally {
            mFileDescriptor = null;
            mAccessory = null;
            stopSelf();
        }

    }


    //private static final String ACTION_USB_PERMISSION = "com.android.example.USB_PERMISSION";
    private static final String ACTION_USB_PERMISSION = "com.example.test01.USB_PERMISSION";
    private final BroadcastReceiver mMyBroadcastReceiver = new BroadcastReceiver() {

        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (ACTION_USB_PERMISSION.equals(action)) {
                synchronized (this) {
                    UsbAccessory accessory = (UsbAccessory) intent.getParcelableExtra(UsbManager.EXTRA_ACCESSORY);

                    if (intent.getBooleanExtra(UsbManager.EXTRA_PERMISSION_GRANTED, false)) {
                        //Richiedo permesso per usbAccessory
                        if (mAccessory != null && mUsbManager != null) {

                            if(mUsbManager.hasPermission(mAccessory)){
                                openAccessory(mAccessory); //openAccessory avvia anche thread
                            }
                            else
                            {
                                Log.d(TAG, "mAccessory permission denied for accessory " + mAccessory);
                            }
                        }
                    }
                    else {
                        Log.d(TAG, "permission denied for accessory " + accessory);
                    }
                }
            }
            else if(UsbManager.ACTION_USB_ACCESSORY_DETACHED.equals(action)) {
                closeAccessory();
                stopSelf();
            }
            else if(SEND_ADK_STRING.equals(action)) {
                String strCommand = intent.getStringExtra(MSG_COMMAND);
                String strParams = intent.getStringExtra(MSG_PARAMS);
                String myCommand = SEND_MSG_START + strCommand + SEND_MSG_SEP + strParams +SEND_MSG_END;
                sendAdkCommand(myCommand);
            }
        }
    };

    //invia comandi all'adkservice?
    protected void sendAdkCommand(String text) {
        // TODO Auto-generated method stub
        byte[] buffer = text.getBytes();

        try {
            if (mOutputStream != null){
                mOutputStream.write(buffer);
            }
        } catch (IOException e) {
            Log.e(TAG, "AdkService sendAdkCommand() error: " + e.getMessage());
        }
    }

    public static Integer getNumCassetti(){
        return myArmadioNumCassetti;
    }
}

