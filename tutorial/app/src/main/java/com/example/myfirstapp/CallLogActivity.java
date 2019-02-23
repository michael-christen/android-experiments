package com.example.myfirstapp;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CallLog;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.Date;


public class CallLogActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call_log);

        // Capture the layout's TextView and set the string as its text
        TextView textView = findViewById(R.id.textView);

        textView.setText(getCallDetails(getApplicationContext()));
    }

    private static String getCallDetails(Context context) {
        StringBuffer stringBuffer = new StringBuffer();
        Cursor cursor = context.getContentResolver().query(CallLog.Calls.CONTENT_URI, null, null, null, CallLog.Calls.DATE + " DESC");
        int number = cursor.getColumnIndex(CallLog.Calls.NUMBER);
        int type = cursor.getColumnIndex(CallLog.Calls.TYPE);
        int date = cursor.getColumnIndex(CallLog.Calls.DATE);
        int duration = cursor.getColumnIndex(CallLog.Calls.DURATION);
        while (cursor.moveToNext()) {
            String phNumber = cursor.getString(number);
            String callType = cursor.getString(type);
            String callDate = cursor.getString(date);
            Date callTimestamp = new Date(Long.valueOf(callDate));
            String callDuration = cursor.getString(duration);
            String dir = null;
            int dircode = Integer.parseInt(callType);
            switch (dircode) {
                case CallLog.Calls.OUTGOING_TYPE:
                    dir = "OUTGOING";
                    break;
                case CallLog.Calls.INCOMING_TYPE:
                    dir = "INCOMING";
                    break;
                case CallLog.Calls.MISSED_TYPE:
                    dir = "MISSED";
                    break;
                default:
                    dir = "UNKNOWN(" + callType + ")";
                    break;
            }
            // Parse contact from phone #
            String contactName = phNumber;
            if (!Uri.encode(phNumber).isEmpty()) {
                Uri uri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI, Uri.encode(phNumber));
                String[] projection = new String[]{ContactsContract.PhoneLookup.DISPLAY_NAME};
                Cursor contactCursor = context.getContentResolver().query(uri, projection, null, null, null);
                if (contactCursor != null) {
                    if (contactCursor.moveToFirst()) {
                        contactName = contactCursor.getString(0);
                    }
                    contactCursor.close();
                }
            }
            stringBuffer.append("\nContact: " + contactName + " \nType: " + dir + " \nDate: " + callTimestamp +
                    " \nDuration(s): " + callDuration);
            stringBuffer.append("\n-------------------");
        }
        cursor.close();
        return stringBuffer.toString();
    }
}