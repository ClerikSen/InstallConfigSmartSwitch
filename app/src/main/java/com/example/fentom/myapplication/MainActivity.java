package com.example.fentom.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private UDPHelper udp;
    private Pinger p;
    private TextView text;
    private TextView textView;
    private TextView textView2;
    private TextView textView3;
    private TextView textView4;
    private TextView textView5;
    private TextView textView6;

    private Button button;
    private EditText name;
    private EditText ssid;
    private EditText pass;
    private EditText mqtt_user;
    private EditText mqtt_port;
    private EditText mqtt_pass;
    private EditText ipBrok1;
    private EditText ipBrok2;
    private EditText ipBrok3;
    private EditText ipBrok4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        text = (TextView) findViewById(R.id.text);
        textView = (TextView) findViewById(R.id.textView);
        textView2 = (TextView) findViewById(R.id.textView2);
        textView3 = (TextView) findViewById(R.id.textView3);
        textView4 = (TextView) findViewById(R.id.textView4);
        textView5 = (TextView) findViewById(R.id.textView5);
        textView6 = (TextView) findViewById(R.id.textView6);
        button = (Button) findViewById(R.id.button2);
        name = (EditText) findViewById(R.id.editText11);
        ssid = (EditText) findViewById(R.id.editText12);
        pass = (EditText) findViewById(R.id.editText13);
        mqtt_user = (EditText) findViewById(R.id.editText14);
        mqtt_port = (EditText) findViewById(R.id.editText15);
        mqtt_pass = (EditText) findViewById(R.id.editText16);
        ipBrok1 = (EditText) findViewById(R.id.editText26);
        ipBrok2 = (EditText) findViewById(R.id.editText28);
        ipBrok3 = (EditText) findViewById(R.id.editText29);
        ipBrok4 = (EditText) findViewById(R.id.editText27);
        p = new Pinger();
        p.start();
    }

    private class Pinger extends Thread {

        private boolean running;
        @Override
        public void run() {
            udp = new UDPHelper();
            udp.start();
            running = true;
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        udp.send("1"+(char)name.getText().toString().length()+name.getText().toString());
                        udp.send("2"+(char)ssid.getText().toString().length()+ssid.getText().toString());
                        udp.send("3"+(char)pass.getText().toString().length()+pass.getText().toString());
                        udp.send("4"+(char)mqtt_user.getText().toString().length()+mqtt_user.getText().toString());
                        udp.send("5"+(char)mqtt_port.getText().toString().length()+mqtt_port.getText().toString());
                        udp.send("6"+(char)mqtt_pass.getText().toString().length()+mqtt_pass.getText().toString());
                        udp.send("7"+(char)(ipBrok1.getText().toString().length()+ipBrok2.getText().toString().length()+ipBrok3.getText().toString().length()+ipBrok4.getText().toString().length()+3)+ipBrok1.getText().toString()+"."+ipBrok2.getText().toString()+"."+ipBrok3.getText().toString()+"."+ipBrok4.getText().toString());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            try {
                DatagramSocket datagramSocket = new DatagramSocket(8888);
                byte[] buffer = new byte[50];
                DatagramPacket packet = new DatagramPacket(buffer,buffer.length);
                while(running){
                    datagramSocket.receive(packet);
                    byte[] buf = packet.getData();
                    String s = new String(buf);
                    setText(s);

                }
            } catch (IOException e) {
                e.printStackTrace();
            }


        }

        public void setText(final String s){
            runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    int ind = Integer.parseInt(""+s.charAt(0));
                    int count = (int)s.charAt(1);
                    String s1 = "";
                    for(int i=0;i<count;i++){
                        s1+=s.charAt(i+2);
                    }
                    if(ind==1){
                        text.setText(s1);
                    }else if(ind==2){
                        textView.setText(s1);
                    }else if(ind==3){
                        textView2.setText(s1);
                    }else if(ind==4){
                        textView3.setText(s1);
                    }else if(ind==5){
                        textView4.setText(s1);
                    }else if(ind==6){
                        textView5.setText(s1);
                    }else if(ind==7){
                        textView6.setText(s1);
                    }

                    // Stuff that updates the UI

                }
            });

        }

    }


}
