package softices.com.traineeapp.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import softices.com.traineeapp.R;

public class BroadcastreceiverActivity extends AppCompatActivity {
    private Button battery;
    private Button contact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_broadcast_receiver);
        battery = findViewById(R.id.btn_Battery);
        contact = findViewById(R.id.btn_Contact);
        battery.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
// TODO Auto-generated method stub

                Intent androidsolved_intent = new Intent(getApplicationContext(), BatteryActivity.class);
                startActivity(androidsolved_intent);
                /**/
            }
        });
        contact.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
// TODO Auto-generated method stub

                Intent androidsolved_intent = new Intent(getApplicationContext(), AllcontactActivity.class);
                startActivity(androidsolved_intent);
                /**/
            }
        });



    }
}
