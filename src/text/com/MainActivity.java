package text.com;

import android.app.Activity;
import android.app.PendingIntent;
import text.com.MyLanguage;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;
import com.memetix.mst.language.Language;
import com.memetix.mst.detect.Detect;
import com.memetix.mst.translate.Translate;

public class MainActivity extends Activity
{
    /** Called when the activity is first created. */
    Button sendSMS;
    EditText msgTxt;
    EditText numTxt;
    IntentFilter intentFilter;
    List<String> spinnerArray =  new ArrayList<String>();
    Language language;
    //private static Translator translator;
    //private Locale myLocale;
    
    private BroadcastReceiver intentReceiver = new BroadcastReceiver(){
            @Override
            public void onReceive(Context context, Intent intent)
            {
              String seperator = "", cur = "", new_txt ="";
               //display the message in the textview
            //Locale current = getResources().getConfiguration().locale;
            TextView inTxt = (TextView)findViewById(R.id.textMsg);
            cur = inTxt.getText().toString();
      
                try {
                    Log.v("dfdfdfdfd", new_txt);
                    String translated = translateIt(intent.getExtras().getString("sms").toString()).toString();
                     if(cur.isEmpty()){
                    new_txt = translated;
                    }else{
                       new_txt = cur+"\n"+translated;
                     }
                    inTxt.setText(new_txt);
                    //inTxt.setTextLocale(current);
                } catch (Exception ex) {
                    inTxt.setText(new_txt);
                    Log.v("ERROR main", ex.toString());
                }
            }
            
             public String translateIt(String text) throws Exception{
      String translatedText = "";
          String langPref = "Language";
            SharedPreferences prefs = getSharedPreferences("CommonPrefs", Activity.MODE_PRIVATE);
            String language = prefs.getString(langPref, "");
            Log.v("Saved Pref", language);
        try {
            Translate.setClientId("android_application_1");
            Translate.setClientSecret("a24BkZY9irPCqhRuiVf9CXPaFfXUPiOIRCUZpKO4+mQ=");
            String to_lang = MyLanguage.returnLang(language);
            Log.v("MaiiiiinLang", to_lang);
        translatedText = Translate.execute(text, Language.valueOf(to_lang.toString()));
        Log.v("TRANSLATED", translatedText);
        
    } catch (Exception ex) {
                    Log.v("ERROR IT", ex.toString());
                }
        return translatedText;
    }
           };
    
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        //translator = new Translator("AIzaSyA6Y2yZT9ZxkmuV9JXn-rpLcYntSpMSFFw");
        //loadLocale();
        //intent to filter for SMS messages received
        intentFilter = new IntentFilter();
        intentFilter.addAction("SMS_RECEIVED_ACTION");
        
        sendSMS = (Button) findViewById(R.id.sendBtn);
        msgTxt = (EditText) findViewById(R.id.message);
        numTxt = (EditText) findViewById(R.id.numberTxt);
        sendSMS.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String myMsg = msgTxt.getText().toString();
                String theNumber = numTxt.getText().toString();
                sendMsg(theNumber, myMsg);
            }
            
        });     
    }
    

            protected void sendMsg(String theNumber, String myMsg) {
                String SENT = "Message Sent";
                String DELIVERED = "Message Delivered";
                
                PendingIntent sentP1 = PendingIntent.getBroadcast(this, 0, new Intent(SENT), 0);
                PendingIntent deliveredP1 = PendingIntent.getBroadcast(this, 0, new Intent(DELIVERED), 0);
                
                registerReceiver(new BroadcastReceiver()
                {
                    public void onReceive(Context arg0, Intent arg1)
                    {        
                            switch(getResultCode())
                            {
                                case Activity.RESULT_OK:
                                   Toast.makeText(MainActivity.this, "SMS sent", Toast.LENGTH_LONG).show();
                                    break;
                                case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                                   Toast.makeText(getBaseContext(), "Generic Failure", Toast.LENGTH_LONG).show();
                                    break;
                                case SmsManager.RESULT_ERROR_NO_SERVICE:
                                   Toast.makeText(getBaseContext(), "No Service", Toast.LENGTH_LONG).show();
                                    break;
                            }     
                            }    
                    
                },new IntentFilter(SENT)); 
                
                
                registerReceiver(new BroadcastReceiver()
                {
                    public void onReceive(Context arg0, Intent arg1)
                    {        
                            switch(getResultCode())
                            {
                                case Activity.RESULT_OK:
                                    Toast.makeText(getBaseContext(), "SMS delivered", Toast.LENGTH_LONG).show();
                                    break;
                                case Activity.RESULT_CANCELED:
                                    Toast.makeText(getBaseContext(), "SMS not delivered", Toast.LENGTH_LONG).show();
                                    break;
                            }
                    }
                }, new IntentFilter(DELIVERED));    
                            
                
                
                SmsManager sms = SmsManager.getDefault();
                sms.sendTextMessage(theNumber, null, myMsg, sentP1, deliveredP1);
            }
            
            @Override
            protected void onResume(){
                //register the receiver
                registerReceiver(intentReceiver, intentFilter);
//                if (!(PreferenceManager.getDefaultSharedPreferences(
//            getApplicationContext()).getString("listLanguage", "en")
//            .equals(""))) {
//        refresh();}
                super.onResume();
            }
            @Override
            protected void onPause(){
                //unregister the receiver
                unregisterReceiver(intentReceiver);
                super.onPause();
            }
            
    public void btnSelect_Lang_Click(View oView) {
      Intent myIntent = new Intent(this, LanguageSelector.class);
      startActivity(myIntent);
      finish();
    }
    
    //GoogleAPI.setHttpReferrer("testproject");
        //GoogleAPI.setKey("AIzaSyA6Y2yZT9ZxkmuV9JXn-rpLcYntSpMSFFw");
       // English AUTO_DETECT -> gERMAN Change this if u wanna other languages
    
    //       //Language language = translator.languages("ar")[0];
//        String translatedText = "";
//        
//       Translation translation = translator.translate(text, null, "ar");
//       Log.v("TRANSLATED", translation.getTranslatedText());
   
            
}
