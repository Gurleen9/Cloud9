package com.codingblocks.gurleen.cloud9.Adapters;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.codingblocks.gurleen.cloud9.Activities.Main2Activity;
import com.codingblocks.gurleen.cloud9.DataModels.MainRecyclerViewData;
import com.codingblocks.gurleen.cloud9.FragmentImages;
import com.codingblocks.gurleen.cloud9.R;
import com.codingblocks.gurleen.cloud9.DataModels.Upload;

import java.util.ArrayList;

/**
 * Created by hp on 7/25/2017.
 */

public class Adapter2 extends RecyclerView.Adapter<Adapter2.ViewHolder1> {


    ArrayList<MainRecyclerViewData> arrayList = new ArrayList<>();
    Context c;
    ArrayList<Upload> arrayList1 = new ArrayList<>();

    Main2Activity obj;






    public Adapter2(ArrayList<MainRecyclerViewData> arrayList, ArrayList<Upload> arrayList1, Context c, Main2Activity obj)

    {
        this.obj=obj;
        this.arrayList = arrayList;
        this.arrayList1 = arrayList1;
        this.c=c;

    }

    @Override
    public Adapter2.ViewHolder1 onCreateViewHolder(ViewGroup parent, int viewType) {


        LayoutInflater l = LayoutInflater.from(c);
        View v =l.inflate(R.layout.row_mainrecyclerview,parent,false);
        Adapter2.ViewHolder1 vh1 = new ViewHolder1(v);

        return vh1;


    }


    @Override
    public void onBindViewHolder(Adapter2.ViewHolder1 holder, int position) {



        MainRecyclerViewData s = arrayList.get(position);

    holder.folderName.setText(s.getFolderName());
        holder.folderIcon.setImageBitmap(s.getFolderIcon());
        holder.folderItemCount.setText(s.getFolderContents());

        holder.folderIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("TAG","Gettin clicked");
                FragmentManager fm = obj.getSupportFragmentManager();

                FragmentImages fragment = new FragmentImages();
               fragment = fragment.newInstance();
            FragmentTransaction tx = fm.beginTransaction();
                tx.replace( R.id.mainFrameLayout,fragment ).addToBackStack( "tag" ).commit();




            }
        });





    }














    @Override
    public int getItemCount()

    {
        return arrayList.size();


    }








    public class ViewHolder1 extends RecyclerView.ViewHolder
    { ImageView folderIcon;
        TextView folderName;
        TextView folderItemCount;
        public ViewHolder1(View itemView)
        {



            super(itemView);
            folderIcon = (ImageView)itemView.findViewById(R.id.folderIcon);
            folderName = (TextView)itemView.findViewById(R.id.folderName);
            folderItemCount = (TextView)itemView.findViewById(R.id.folderContents);



        }



    }



}


