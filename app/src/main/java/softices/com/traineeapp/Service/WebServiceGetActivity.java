package softices.com.traineeapp.Service;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import softices.com.traineeapp.R;

public class WebServiceGetActivity extends AppCompatActivity {
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_service_get);
        textView = findViewById(R.id.txt_Get);

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        final String url = "https://reqres.in/api/users?page=2";
// prepare the Request
        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // display response
                        parseJsonResponse(response);
                        Log.d("Response", response.toString());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Error.Response", url);
                    }
                }
        );

// add it to the RequestQueue
        requestQueue.add(getRequest);
    }

    private void parseJsonResponse(JSONObject object) {
        try {
            String stringBuilder = "";
            String page = object.getString("page");
            String per_page = object.getString("per_page");
            String total = object.getString("total");
            String total_pages = object.getString("total_pages");
            JSONArray jsonArray = object.getJSONArray("data");
            stringBuilder = stringBuilder + "page :- " + page + "\n" + "per_page :- " + per_page +"\n" +
                    "total :- " + total + "\n" +"total_pages :- " + total_pages + "\n\n";
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                String id = jsonObject1.getString("id");
                String first_name = jsonObject1.getString("first_name");
                String last_name = jsonObject1.getString("last_name");
                String avatar = jsonObject1.getString("avatar");
                stringBuilder = stringBuilder +"\t"+ "id :- " + id + "\n\t" + "first_name :- " + first_name +"\n\t" +
                        "last_name :- " + last_name + "\n\t" +"avatar :- " + avatar + "\n\n\t\t";
            }
            textView.setText(stringBuilder);
        } catch (Exception e) {
            Log.e("parseJsonResponse", e + "");
        }

    }
}
