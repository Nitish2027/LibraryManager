package com.example.nitish2027.alpranlibrarymanager;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class StudentShow extends AppCompatActivity {

    private String TAG = StudentShow.class.getSimpleName();

    private ProgressDialog progressDialog;
    private ListView listView;

    // URL to get contacts JSON
    //private static String data = "http://api.androidhive.info/contacts/";

    ArrayList<HashMap<String, String>> studentlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_show);

        studentlist = new ArrayList<>();

        listView = (ListView) findViewById(R.id.list);

        new ShowStudents().execute();
    }

    /**
     * Async task class to get json by making HTTP call
     */
    private class ShowStudents extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            progressDialog = new ProgressDialog(StudentShow.this);
            progressDialog.setMessage("Please wait...");
            progressDialog.setCancelable(false);
            progressDialog.show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            StringRequest stringRequest = new StringRequest(
                    Request.Method.POST,
                    Constants.SHOW_STUDENTS,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            progressDialog.dismiss();
                            try {
                                JSONObject obj = new JSONObject(response);

                                if (obj != null) {
                                    try {
                                        JSONObject jsonObj = new JSONObject(response);

                                        // Getting JSON Array node
                                        JSONArray result = jsonObj.getJSONArray("result");

                                        // looping through All Contacts
                                        for (int i = 0; i < result.length(); i++) {
                                            JSONObject c = result.getJSONObject(i);

                                            String studentid = c.getString("studentid");
                                            String fname = c.getString("fname");
                                            String lname = c.getString("lname");

                                            // tmp hash map for single student
                                            HashMap<String, String> student = new HashMap<>();

                                            // adding each child node to HashMap key => value
                                            student.put("studentid", studentid);
                                            student.put("fname", fname);
                                            student.put("lname", lname);

                                            // adding student to studentlist
                                            studentlist.add(student);
                                        }
                                    } catch (final JSONException e) {
                                        Log.e(TAG, "Json parsing error: " + e.getMessage());
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                Toast.makeText(getApplicationContext(),
                                                        "Json parsing error: " + e.getMessage(),
                                                        Toast.LENGTH_LONG)
                                                        .show();
                                            }
                                        });

                                    }
                                } else {
                                    Log.e(TAG, "Couldn't get json from server.");
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(getApplicationContext(),
                                                    "Couldn't get json from server. Check LogCat for possible errors!",
                                                    Toast.LENGTH_LONG)
                                                    .show();
                                        }
                                    });

                                }


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            progressDialog.dismiss();

                            AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());

                            builder.setMessage(error.getMessage() + "! Server Not Working Properly. Try Again Later!");
                            builder.setCancelable(false);
                            builder.setPositiveButton("Okay",null);

                            AlertDialog alert = builder.create();
                            alert.show();

                        }
                    }
            ){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String,String> params = new HashMap<>();
                    return params;
                }
            };

            RequestHandler.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            if (progressDialog.isShowing())
                progressDialog.dismiss();
            /**
             * Updating parsed JSON data into ListView
             * */
            ListAdapter adapter = new SimpleAdapter(
                    StudentShow.this,
                    studentlist,
                    R.layout.students_list_item,
                    new String[]{"studentid", "fname", "lname"},
                    new int[]{R.id.studentid, R.id.fname, R.id.lname});

            listView.setAdapter(adapter);
        }

    }
}
