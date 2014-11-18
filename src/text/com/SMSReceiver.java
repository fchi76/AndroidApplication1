package text.com;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.Toast;
 
 
public class SMSReceiver extends BroadcastReceiver {
   
    @Override
    public void onReceive(Context context, Intent intent){
        //get message passed in
        Bundle bundle = intent.getExtras();
        //SmsMessage[] messages=null;
        String str = "";
        if(bundle != null)
        {
            SmsMessage[] messages = getMessagesFromIntent(intent);
            for (SmsMessage message : messages) {
                //messages[i] = SmsMessage.createFromPdu(pdus);
                str += "Message from" + message.getDisplayOriginatingAddress();
                str += ": ";
                str += message.getMessageBody().toString();
                //str += "\n";
            }
            //display the message
            Toast.makeText(context, str, Toast.LENGTH_SHORT).show();
           
            //send a broadcast intent to update the SMS received in a textview
            Intent broadcastIntent = new Intent();
            broadcastIntent.setAction("SMS_RECEIVED_ACTION");
            broadcastIntent.putExtra("sms",str);
            context.sendBroadcast(broadcastIntent);
        }
    }
    public static SmsMessage[] getMessagesFromIntent(Intent intent) {
        Object[] messages = (Object[]) intent.getSerializableExtra("pdus");
        byte[][] pduObjs = new byte[messages.length][];

        for (int i = 0; i < messages.length; i++) {
            pduObjs[i] = (byte[]) messages[i];
        }
        byte[][] pdus = new byte[pduObjs.length][];
        int pduCount = pdus.length;
        SmsMessage[] msgs = new SmsMessage[pduCount];
        for (int i = 0; i < pduCount; i++) {
            pdus[i] = pduObjs[i];
            msgs[i] = SmsMessage.createFromPdu(pdus[i]);
        }
        return msgs;
    }
}   