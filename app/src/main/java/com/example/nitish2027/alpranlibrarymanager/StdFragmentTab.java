package com.example.nitish2027.alpranlibrarymanager;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.app.Fragment;

/**
 * Created by Nitish2027 on 7/1/2017.
 */

public class StdFragmentTab extends android.support.v4.app.Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.stdfragment, container, false);
        return rootView;
    }
}
