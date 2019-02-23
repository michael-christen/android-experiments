package com.example.myfirstapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /** Called when the user taps the Send button */
    public void sendMessage(View view) {
        Intent intent = new Intent(this, DisplayMessageActivity.class);
        EditText editText = (EditText) findViewById(R.id.editText);
        String message = editText.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }

    public void tabClick(View view) {
        final int id = view.getId();
        Map<Integer, Class<?>> buttonId2Activity = new HashMap<Integer, Class<?>>() {{
            put(R.id.buttonCalls, CallLogActivity.class);
        }};
        Class<?> cls = buttonId2Activity.get(id);
        if (cls != null) {
            Intent intent = new Intent(this, cls);
            startActivity(intent);
        } else {
            Toast toast = Toast.makeText(getApplicationContext(), "Unknown button", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        int action = event.getAction();
        int keyCode = event.getKeyCode();
        switch (keyCode) {
            case KeyEvent.KEYCODE_VOLUME_UP:
                if (action == KeyEvent.ACTION_DOWN) {
                    Log.d("key", "up");
                    Toast toast = Toast.makeText(getApplicationContext(), "Volume Up", Toast.LENGTH_SHORT);
                    toast.show();
                }
                return true;
            case KeyEvent.KEYCODE_VOLUME_DOWN:
                if (action == KeyEvent.ACTION_DOWN) {
                    Log.d("key", "down");
                    Toast toast = Toast.makeText(getApplicationContext(), "Volume Down", Toast.LENGTH_SHORT);
                    toast.show();
                }
                return true;
            default:
                return super.dispatchKeyEvent(event);
        }
    }
}
