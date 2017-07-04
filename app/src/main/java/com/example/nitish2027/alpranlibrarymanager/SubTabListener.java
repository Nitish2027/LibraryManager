package com.example.nitish2027.alpranlibrarymanager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by Nitish2027 on 7/4/2017.
 */

public class SubTabListener extends FragmentStatePagerAdapter {

    //integer to count number of tabs
    int tabCount;

    //Constructor to the class
    public SubTabListener(FragmentManager fm, int tabCount) {
        super(fm);
        //Initializing tab count
        this.tabCount= tabCount;
    }

    //Overriding method getItem
    @Override
    public Fragment getItem(int position) {
        //Returning the current tabs
        switch (position) {
            case 0:
                SubEmpFragmentTab tab1 = new SubEmpFragmentTab();
                return tab1;
            case 1:
                SubBooksFragmentTab tab2 = new SubBooksFragmentTab();
                return tab2;
            case 2:
                SubStdFragmentTab tab3 = new SubStdFragmentTab();
                return tab3;
            default:
                return null;
        }
    }

    //Overriden method getCount to get the number of tabs
    @Override
    public int getCount() {
        return tabCount;
    }
}
