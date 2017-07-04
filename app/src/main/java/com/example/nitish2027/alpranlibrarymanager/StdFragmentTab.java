package com.example.nitish2027.alpranlibrarymanager;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Nitish2027 on 7/1/2017.
 */

public class StdFragmentTab extends android.support.v4.app.Fragment implements View.OnClickListener{

    private EditText etusername, etpassword;
    private Button btnlogin, btnsignup;
    private ProgressDialog progressDialog;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,  Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        // create your view using LayoutInflater
        return inflater.inflate(R.layout.stdfragment, container, false);
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // do your variables initialisations here except Views!!!
    }

    public void onViewCreated(View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        // initialise your views
        if(SharedPrefManager.getInstance(this.getActivity()).studentIsLoggedIn()){
            getActivity().finish();
            startActivity(new Intent(this.getActivity(), StdActivity.class));
            return;
        }

        etusername = (EditText) view.findViewById(R.id.username);
        etpassword = (EditText) view.findViewById(R.id.password);
        btnlogin = (Button) view.findViewById(R.id.loginbtn);
        btnsignup = (Button) view.findViewById(R.id.btnsignup);
        progressDialog = new ProgressDialog(this.getActivity());
        progressDialog.setMessage("Please Wait..!");
        btnlogin.setOnClickListener(this);
        btnsignup.setOnClickListener(this);

        CheckBox cbPassword = (CheckBox) view.findViewById(R.id.cbPass);

        cbPassword.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // checkbox status is changed from uncheck to checked.
                if (!isChecked) {
                    // show password
                    etpassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                } else {
                    // hide password
                    etpassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
            }
        });

    }

    private void StudentLogin(){
        final String str_username = etusername.getText().toString().trim();
        final String str_password = etpassword.getText().toString().trim();

        progressDialog.show();

        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                Constants.STUDENT_LOGIN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {
                            JSONObject obj = new JSONObject(response);
                            if(!obj.getBoolean("error")){
                                SharedPrefManager.getInstance(getActivity().getApplicationContext())
                                        .studentLogin(
                                                obj.getString("studentid")
                                        );
                                startActivity(new Intent(getActivity().getApplicationContext(), StdActivity.class));
                                getActivity().finish();
                            }
                            else {
                                Toast.makeText(
                                        getActivity().getApplicationContext(),
                                        obj.getString("message"),
                                        Toast.LENGTH_LONG
                                ).show();

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

                        AlertDialog.Builder builder = new AlertDialog.Builder(
                                getActivity());

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
                params.put("studentid",str_username);
                params.put("pass",str_password);
                return params;
            }
        };

        RequestHandler.getInstance(this.getActivity()).addToRequestQueue(stringRequest);
    }

    @Override
    public void onClick(View view) {
        if(view == btnlogin){
            StudentLogin();
        }

        if(view == btnsignup){
            Intent iAdd = new Intent(this.getActivity(), StudentSignUpActivity.class);
            startActivity(iAdd);
        }
    }
}
