package softices.com.traineeapp.Service;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import softices.com.traineeapp.R;

public class ServiceActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btnStart;
    private Button btnStop;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);
        btnStart=findViewById(R.id.btn_start);
        btnStop = findViewById(R.id.btn_stop);

        btnStart.setOnClickListener(this);
        btnStop.setOnClickListener(this);
    }
    @Override
    public void onClick(View view) {
        if (view == btnStart) {
            //start the service here
            startService(new Intent(this, MyService.class));
        } else if (view == btnStop) {
            //stop the service here
            stopService(new Intent(this, MyService.class));
        }
    }
}
