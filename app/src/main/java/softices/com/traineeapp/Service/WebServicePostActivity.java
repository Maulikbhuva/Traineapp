package softices.com.traineeapp.Service;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class WebServicePostActivity extends AppCompatActivity {
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_service_post);
        textView = findViewById(R.id.txt_Post);

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        final String url = "https://reqres.in/api/users";
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
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
                params.put("job", "leader");

                return params;
            }
        };
        requestQueue.add(postRequest);
    }

    private void parseJsonResponse(String response) {
        try {
            String strBuilder = "";
            JSONObject jsonObject=new JSONObject(response);
            String name = jsonObject.getString("name");
            String job = jsonObject.getString("job");
            int id = jsonObject.getInt("id");
            String createdAt = jsonObject.getString("createdAt");
            strBuilder = strBuilder + "name :- " + name + "\n" + "job :- " + job +"\n" +
                    "id :- " + id + "\n" +"createdAt :- " + createdAt + "\n\n";
            textView.setText(strBuilder);
        } catch (Exception e) {
            Log.e("parseJsonResponse", e + "");
        }

    }
}
