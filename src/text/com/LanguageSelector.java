package text.com;

import android.app.Activity;
import android.app.PendingIntent;
import text.com.MyLanguage;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class LanguageSelector extends Activity
{
    /** Called when the activity is first created. */
    List<String> spinnerArray =  new ArrayList<String>();
    MyLanguage language;
    //private Locale myLocale;
    
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
            doUIStuff();
    } 
     void doUIStuff(){
    setContentView(R.layout.languages);
        //loadLocale();
        //intent to filter for SMS messages received
        spinnerArray = MyLanguage.getAll();
         ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, spinnerArray);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner sItems = (Spinner) findViewById(R.id.langSpinner);
         sItems.setAdapter(adapter);
         sItems.setOnItemSelectedListener(new OnItemSelectedListener(){
           public void onItemSelected(AdapterView<?> parent, View view, 
            int pos, long id) {
        // An item was selected. You can retrieve the selected item using
        String selected_lang = (String) parent.getItemAtPosition(pos);
            changeLang(selected_lang);
            //Toast.makeText(this, "Locale now set to "+selected_lang+" !", Toast.LENGTH_LONG).show();
            //refresh();
        //Toast.makeText(MainActivity.this, selected_lang, Toast.LENGTH_LONG).show();
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }  
         });
}
    
    
public void saveLocale(String lang)
{
    String langPref = "Language";
    SharedPreferences prefs = getSharedPreferences("CommonPrefs", Activity.MODE_PRIVATE);
    SharedPreferences.Editor editor = prefs.edit();
    editor.putString(langPref, lang);
    editor.commit();
}


//public void loadLocale()
//{
//    String langPref = "Language";
//    SharedPreferences prefs = getSharedPreferences("CommonPrefs", Activity.MODE_PRIVATE);
//    String language = prefs.getString(langPref, "");
//    changeLang(language);
//}

public void changeLang(String lang)
{
    
   Locale myLocale = new Locale(lang);
    saveLocale(lang);
    Configuration config = new Configuration();
    config.locale = myLocale;
    super.onConfigurationChanged(config);
    Locale.setDefault(config.locale);
    getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
    
}

 public void btnGo_Click(View oView) {	
    Intent myIntent = new Intent(this, MainActivity.class);
    startActivity(myIntent);
    finish();
}

}

