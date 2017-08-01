package com.codingblocks.gurleen.cloud9;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codingblocks.gurleen.cloud9.Activities.Main2Activity;
import com.codingblocks.gurleen.cloud9.Adapters.Adapter;
import com.codingblocks.gurleen.cloud9.Adapters.Adapter2;
import com.codingblocks.gurleen.cloud9.DataModels.MainRecyclerViewData;

import java.util.ArrayList;

/**
 * Created by hp on 7/28/2017.
 */

public class MainFragement extends Fragment {






        ArrayList<MainRecyclerViewData> arrayList = new ArrayList<>();


        public MainFragement newInstance ()
        {

            Bundle bundleobj = new Bundle();


 MainFragement mainFragement = new MainFragement();
            return  mainFragement;

        }


        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

            Bundle b= getArguments();


            View v = inflater.inflate(R.layout.main_fragment_layout,container,false);
            ((Main2Activity)getActivity()).MainFragementMethod(v);







            return v;
        }







    }











