package com.myapp.bt_esp32;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.graphics.Color;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Set;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    Button btn_connect;
    Button btn_test;
    TextView txt_top;
    TextView txt_bottom;

//            bt.getName();
//            bt.getAddress();

    public static String deviceAddress = "30:AE:A4:38:74:D2";
    public static String deviceName = "ESP32test2";
    public static BluetoothDevice result = null;
    public static BluetoothAdapter bluetoothAdapter=BluetoothAdapter.getDefaultAdapter();
    public static BluetoothSocket socket;
    public static OutputStream outputStream;
    public static InputStream inputStream;
    public static final UUID PORT_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"); //Serial Port Service ID
    public static int serial_msg;
    public boolean bt_conn = false;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_connect = (Button) findViewById(R.id.btn_connect);
        btn_connect.setBackgroundColor(Color.CYAN);

        btn_test = (Button) findViewById(R.id.btn_test);

        txt_top = (TextView) findViewById(R.id.txt_top);
        txt_bottom = (TextView) findViewById(R.id.txt_bottom);

    }

    public void buttonOnClick(View view) {
        int the_id = view.getId();

        if (the_id == R.id.btn_send_1) {
            String data_to_send = "ABC";
            if (socket!=null) {
                try {
                    socket.getOutputStream().write(data_to_send.getBytes());
                    Toast.makeText(getApplicationContext(), "Send: "+data_to_send, Toast.LENGTH_SHORT).show();
                    txt_top.setText("Send: "+data_to_send + System.getProperty ("line.separator") + txt_top.getText().toString());
                }
                catch (IOException e) {
                    Toast.makeText(getApplicationContext(),"Post Error",Toast.LENGTH_SHORT).show();
                }
            } else Toast.makeText(getApplicationContext(),"Not Connected to Device",Toast.LENGTH_SHORT).show();
        }

        if (the_id == R.id.btn_send_2) {
            String data_to_send = "333";
            if (socket!=null) {
                try {
                    socket.getOutputStream().write(data_to_send.getBytes());
                    Toast.makeText(getApplicationContext(), "Send: "+data_to_send, Toast.LENGTH_SHORT).show();
                    txt_top.setText("Send: "+data_to_send + System.getProperty ("line.separator") + txt_top.getText().toString());
                }
                catch (IOException e) {
                    Toast.makeText(getApplicationContext(),"Post Error",Toast.LENGTH_SHORT).show();
                }
            } else Toast.makeText(getApplicationContext(),"Not Connected to Device",Toast.LENGTH_SHORT).show();
        }

        if (the_id == R.id.btn_send_3) {
            String data_to_send = "Cat";
            if (socket!=null) {
                try {
                    socket.getOutputStream().write(data_to_send.getBytes());
                    Toast.makeText(getApplicationContext(), "Send: "+data_to_send, Toast.LENGTH_SHORT).show();
                    txt_top.setText("Send: "+data_to_send + System.getProperty ("line.separator") + txt_top.getText().toString());
                }
                catch (IOException e) {
                    Toast.makeText(getApplicationContext(),"Post Error",Toast.LENGTH_SHORT).show();
                }
            } else Toast.makeText(getApplicationContext(),"Not Connected to Device",Toast.LENGTH_SHORT).show();
        }




        if (the_id == R.id.btn_read_data) {

            Toast.makeText(getApplicationContext(), "this does nothing. click TEST", Toast.LENGTH_SHORT).show();
            /*
            try {
                //serial_msg = Integer.parseInt(new String(String.valueOf(inputStream.read())));
                //String temp = String.valueOf((char) serial_msg);
                String temp2 = String.valueOf(inputStream.read());
                txt_bottom.setText(temp2 + System.getProperty ("line.separator") + txt_bottom.getText().toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
            */

        }


        if (the_id == R.id.btn_test) {

            if (socket!=null) {
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //Do something after 100ms
                        //Toast.makeText(getApplicationContext(), "check", Toast.LENGTH_SHORT).show();

                        try {
                            //serial_msg = Integer.parseInt(new String(String.valueOf(inputStream.read())));
                            //String temp = String.valueOf((char) serial_msg);
                            String temp2 = String.valueOf(inputStream.read());
                            txt_bottom.setText(temp2 + "-" + txt_bottom.getText().toString());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

//some comment
                        handler.postDelayed(this, 2000);
                    }
                }, 1500);

                btn_test.setEnabled(false);
            } else Toast.makeText(getApplicationContext(),"Not Connected to Device",Toast.LENGTH_SHORT).show();
        }







if (the_id == R.id.btn_connect) {
    //Toast.makeText(this, "connect", Toast.LENGTH_SHORT).show();



    //************ Copied Code ************
    if (bt_conn == false){
        Set<BluetoothDevice> devices = bluetoothAdapter.getBondedDevices();
        if (devices != null) {
            for (BluetoothDevice device : devices) {
                //if (deviceAddress.equals(device.getAddress())) {
                if (deviceName.equals(device.getName())) {
                    Toast.makeText(getApplicationContext(),"ESP32 Pairing Found",Toast.LENGTH_SHORT).show();
                    txt_top.setText("ESP32 Pairing Found" + System.getProperty ("line.separator") + txt_top.getText().toString());
                    result = device;
                    boolean connected=true;
                    try {
                        socket = result.createRfcommSocketToServiceRecord(PORT_UUID);
                        socket.connect();
                        Toast.makeText(getApplicationContext(),"Connected",Toast.LENGTH_SHORT).show();
                        txt_top.setText("Connected" + System.getProperty ("line.separator") + txt_top.getText().toString());
                    } catch (IOException e) {
                        e.printStackTrace();
                        connected=false;
                        Toast.makeText(getApplicationContext(),"Connection Failed",Toast.LENGTH_SHORT).show();
                        txt_top.setText("Connection Failed" + System.getProperty ("line.separator") + txt_top.getText().toString());
                    }
                    if(connected)//put indicator
                    {
                        btn_connect.setBackgroundColor(Color.GREEN);
                        //conn_ttl.setVisibility(View.VISIBLE);
                        //srch_ttl.setVisibility(View.INVISIBLE);
                        //splash_btn.setEnabled(true);
                        //splash_btn.setBackgroundResource(R.drawable.btn_splash_nor);
                        try {
                            outputStream=socket.getOutputStream();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        try {
                            inputStream=socket.getInputStream();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        bt_conn = true;

                        /*
                        new Thread(new Runnable() {
                            public void run() {
                                while (true){
                                    if (bt_conn == true) {
                                        //read data from serial then convert it from decimal to char
                                        try {
                                            serial_msg = Integer.parseInt(new String(String.valueOf(inputStream.read())));
                                            //txt_top.setText("xxx" + System.getProperty ("line.separator") + txt_top.getText().toString());
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                        //send converted data to another string in another activity
                                        //ActivityControlCenter.read_msg = String.valueOf((char) serial_msg);
                                        Toast.makeText(getApplicationContext(),"Runnable",Toast.LENGTH_SHORT).show();
                                        SystemClock.sleep(5000);
                                    }
                                }
                            }
                        }).start();
                        */

                    }
                    break;
                }
            }
        }
    }
    //************ Copied Code ************



        }  //end button connect


    }

}
