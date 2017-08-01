package com.codingblocks.gurleen.cloud9.Adapters;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codingblocks.gurleen.cloud9.Activities.Main2Activity;
import com.codingblocks.gurleen.cloud9.R;
import com.codingblocks.gurleen.cloud9.DataModels.Upload;

import java.util.ArrayList;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

/**
 * Created by hp on 7/25/2017.
 */










public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder1> {


    ArrayList<Upload> arrayList = new ArrayList<>();
    Context c;

    Main2Activity obj;






    public Adapter(ArrayList<Upload> arrayList, Context c,Main2Activity obj)

    {
        this.obj=obj;
        this.arrayList = arrayList;
        this.c=c;


    }

    @Override
    public Adapter.ViewHolder1 onCreateViewHolder(ViewGroup parent, int viewType) {


          LayoutInflater l = LayoutInflater.from(c);
                View v =l.inflate(R.layout.inside_image_folder_row,parent,false);
                Adapter.ViewHolder1 vh1 = new ViewHolder1(v);

                return vh1;


    }


    @Override
    public void onBindViewHolder(Adapter.ViewHolder1 holder, int position) {
        Upload upload = arrayList.get(position);



        Glide.with(c).load(upload.getUrl()).into(holder.imageIcon);




        holder.imageName.setText(upload.getName());

               holder.moreButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.e("TAG","Gettin clicked");

                        LayoutInflater inf = (LayoutInflater)c.getSystemService(LAYOUT_INFLATER_SERVICE);
                        View view = inf.inflate(R.layout.imagefolder_popup_menu,null);

                        final Dialog mBottomSheetDialog = new Dialog (obj,
                                R.style.MaterialDialogSheet);
                        mBottomSheetDialog.setContentView (view);
                        mBottomSheetDialog.setCancelable (true);
                        mBottomSheetDialog.getWindow ().setLayout (LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT);
                        mBottomSheetDialog.getWindow ().setGravity (Gravity.BOTTOM);
                        mBottomSheetDialog.show ();


                    }
                });





            }














    @Override
    public int getItemCount()

    {
            return arrayList.size();


    }








    public class ViewHolder1 extends RecyclerView.ViewHolder
    { ImageView imageIcon;
        TextView imageName;
        ImageButton moreButton;

        public ViewHolder1(View itemView)
        {



            super(itemView);
           imageIcon = (ImageView)itemView.findViewById(R.id.fileIcon);
            imageName = (TextView)itemView.findViewById(R.id.fileName);
                 moreButton = (ImageButton)itemView.findViewById(R.id.moreButton);




        }



    }



}

