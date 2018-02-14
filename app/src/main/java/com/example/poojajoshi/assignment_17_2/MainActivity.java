package com.example.poojajoshi.assignment_17_2;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.os.IBinder;
import android.content.ComponentName;
import android.content.ServiceConnection;
import android.content.Context;
import android.content.Intent;
import com.example.poojajoshi.assignment_17_2.MyService.MyBinder;

public class MainActivity extends AppCompatActivity {
    MyService myService;
    boolean isService = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // get the handle of textView and Button. Set the onClick listener for button
        final TextView tv = findViewById(R.id.textView);

        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // get the timestamp from service and update the on textview.
                if ( isService ) {
                    tv.setText(myService.getTimestamp());
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        // start service
        Intent intent = new Intent(this, MyService.class);
        startService(intent);
        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (isService) {
            unbindService(serviceConnection);
            isService = false;
        }
    }

    // create service connection handle
    private ServiceConnection serviceConnection = new ServiceConnection() {

        @Override
        public void onServiceDisconnected(ComponentName name) {
            isService = false;
        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            MyBinder myBinder = (MyBinder) service;
            myService = myBinder.getService();
            isService = true;
        }
    };
}
