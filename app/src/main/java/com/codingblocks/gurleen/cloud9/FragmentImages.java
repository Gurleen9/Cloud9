package com.codingblocks.gurleen.cloud9;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.codingblocks.gurleen.cloud9.Activities.Main2Activity;
import com.codingblocks.gurleen.cloud9.Adapters.Adapter;
import com.codingblocks.gurleen.cloud9.DataModels.ImageFolderData;

import java.util.ArrayList;


public class FragmentImages extends Fragment {





    public  FragmentImages  newInstance ()
    {


     Bundle bundleobj = new Bundle();
        FragmentImages fragmentImages = new FragmentImages();
        return fragmentImages;




    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        Bundle b= getArguments();


        View v = inflater.inflate(R.layout.fragment_fragment_images,container,false);
        ((Main2Activity)getActivity()).FragementImagesMethod(v);








        return v;
    }







}
