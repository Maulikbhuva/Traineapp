package softices.com.traineeapp.Service;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import softices.com.traineeapp.R;

public class WebServiceActivity extends AppCompatActivity {
    private Button get;
    private Button post;
    private Button put;
    private Button delete;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_service);
        get =findViewById(R.id.btn_get);
        post = findViewById(R.id.btn_post);
        put = findViewById(R.id.btn_put);
        delete = findViewById(R.id.btn_delete);
        get.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent androidsolved_intent = new Intent(getApplicationContext(), WebServiceGetActivity.class);
                startActivity(androidsolved_intent);
            }
        });
        put.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent androidsolved_intent = new Intent(getApplicationContext(), WebServicePutActivity.class);
                startActivity(androidsolved_intent);
            }
        });
        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent androidsolved_intent = new Intent(getApplicationContext(), WebServicePostActivity.class);
                startActivity(androidsolved_intent);
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent androidsolved_intent = new Intent(getApplicationContext(), WebServiceDeleteActivity.class);
                startActivity(androidsolved_intent);
            }
        });
        }
}