package softices.com.traineeapp.Service;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import softices.com.traineeapp.R;

public class WebServicePutActivity extends AppCompatActivity {
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_service_put);
        textView = findViewById(R.id.txt_Put);

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        final String url = "https://reqres.in/api/users/2";
        StringRequest putRequest = new StringRequest(Request.Method.PUT,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // response
                        parseJsonResponse(response);
                  Log.d("Response", response);
                    }

                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.d("Error.Response", url);
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("name", "morpheus");
                params.put("job", "zion resident");
                return params;
            }

        };
        requestQueue.add(putRequest);
    }
    private void parseJsonResponse(String response) {
        try {
            String strBuilder = "";
            JSONObject jsonObject=new JSONObject(response);
            String name = jsonObject.getString("name");
            String job = jsonObject.getString("job");
            String updatedAt = jsonObject.getString("updatedAt");
            strBuilder = strBuilder + "name :- " + name + "\n" + "job :- " + job +"\n" +
                   "createdAt :- " + updatedAt + "\n\n";
            textView.setText(strBuilder);
        } catch (Exception e) {
            Log.e("parseJsonResponse", e + "");
        }
    }
}
