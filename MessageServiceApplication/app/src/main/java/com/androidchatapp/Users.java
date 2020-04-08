package com.androidchatapp;

import android.app.ActivityManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Users extends AppCompatActivity {
    ListView usersList;
    TextView noUsersText;
    ArrayList<String> al = new ArrayList<>();
    int totalUsers = 0;
    ProgressDialog pd;
    EditText userName;
    Button startService;
    Button seeMessage;
    EditText personName;
    Button signOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);
        userName = (EditText) findViewById(R.id.userName);
        pd = new ProgressDialog(Users.this);
        pd.setMessage("Loading...");
        pd.show();

        String url = "https://myapplication-f9aa9.firebaseio.com/users.json";
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                doOnSuccess(s);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                System.out.println("" + volleyError);
            }
        });

        RequestQueue rQueue = Volley.newRequestQueue(Users.this);
        rQueue.add(request);
        signOut = (Button) findViewById(R.id.signOut);
        startService = (Button) findViewById(R.id.startService);
        seeMessage = (Button) findViewById(R.id.messageBox);
        personName = (EditText) findViewById(R.id.seeMessage);
        startService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < al.size(); i++)
                    if (al.get(i).equals(String.valueOf(userName.getText()))) {
                        if (!isServiceRunning(RandomMessageService.class)) {
                            UserDetails.chatWith = al.get(i);
                            startService(new Intent(getBaseContext(), RandomMessageService.class));
                        } else {
                            Toast.makeText(getBaseContext(), "service already running", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Enter Correct User Name", Toast.LENGTH_SHORT).show();
                    }
            }
        });
        seeMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < al.size(); i++) {
                    if (al.get(i).equals(String.valueOf(personName.getText()))) {
                        UserDetails.chatWith = al.get(i);
                        startActivity(new Intent(Users.this, Chat.class));
                    }
                }
            }
        });
        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Users.this, Login.class);
                startActivity(intent);
                stopService(new Intent(getBaseContext(),RandomMessageService.class));
                finish();
            }
        });
    }

    public void doOnSuccess(String s) {
        try {
            JSONObject obj = new JSONObject(s);
            Iterator i = obj.keys();
            String key = "";
            while (i.hasNext()) {
                key = i.next().toString();
                if (!key.equals(UserDetails.username)) {
                    al.add(key);
                }
                totalUsers++;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        pd.dismiss();
    }

    private boolean isServiceRunning(Class<?> randomMessageService) {
        ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> runningServices = activityManager.getRunningServices(Integer.MAX_VALUE);
        for (int i = 0; i < runningServices.size(); i++) {
            if (runningServices.get(i).service.getClassName().equals(randomMessageService)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        Toast.makeText(Users.this, "Sign Out!!", Toast.LENGTH_LONG).show();
        this.finish();
    }
}