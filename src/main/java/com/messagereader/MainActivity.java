package com.messagereader;

import com.database.DBHandler;

import java.util.ArrayList;
import java.util.StringTokenizer;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private DBHandler db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView mt = (TextView) findViewById(R.id.message);

        db = new DBHandler(this);

        String message = "";

        if (savedInstanceState != null){

            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                message = "Nothing";
                Toast.makeText(this, "Nothing", Toast.LENGTH_SHORT).show();
            } else {
                message = extras.getString("message");
                Toast.makeText(this, ""+message, Toast.LENGTH_SHORT).show();

            }
        }
        else{
            Toast.makeText(this, "Empty", Toast.LENGTH_SHORT).show();
            message = "Empty";
        }

        mt.setText(message);

        ArrayList<MessageObj> messages = db.getAllMessages();

        for (MessageObj msg: messages){

            mt.append("\n"+ msg.getAdded_date()+" "+msg.getMessage()+" "+msg.getStatus()+"");
            if (msg.getMessage().contains("Money Received from")){

                Toast.makeText(this, "MPESA Trasaction Detected", Toast.LENGTH_SHORT).show();
                StringTokenizer st = new StringTokenizer(msg.getMessage(), " ");
                while (st.hasMoreElements()) {
                    Log.d("Token ", st.nextToken());
                }
            }
            else{
              //  Toast.makeText(this, "OOPS, NOt MPE", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
