package com.example.nitish2027.alpranlibrarymanager;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Nitish2027 on 7/4/2017.
 */

public class SubStdFragmentTab  extends android.support.v4.app.Fragment implements View.OnClickListener {

    private Button studentinfo, studentshow;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        // create your view using LayoutInflater
        return inflater.inflate(R.layout.substdfragment, container, false);
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // do your variables initialisations here except Views!!!
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // initialise your views

        studentinfo = (Button) view.findViewById(R.id.studentinfo);
        studentinfo.setOnClickListener(this);

        studentshow = (Button) view.findViewById(R.id.studentshow);
        studentshow.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        if(view == studentinfo){
            Intent intent = new Intent(getActivity().getApplicationContext(), StudentInfo.class);
            startActivity(intent);
        }

        if(view == studentshow){
            Intent intent = new Intent(getActivity().getApplicationContext(), StudentShow.class);
            startActivity(intent);
        }
    }
}
