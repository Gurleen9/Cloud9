package com.codingblocks.gurleen.cloud9.DataModels;

import android.graphics.Bitmap;

/**
 * Created by hp on 7/25/2017.
 */

public class MainRecyclerViewData {


   Bitmap folderIcon;
    String folderName;
    String folderContents;

    public MainRecyclerViewData(Bitmap folderIcon, String folderName, String folderContents) {
        this.folderIcon = folderIcon;
        this.folderName = folderName;
        this.folderContents = folderContents;
    }


    public Bitmap getFolderIcon() {
        return folderIcon;
    }

    public String getFolderName() {
        return folderName;
    }

    public String getFolderContents() {
        return folderContents;
    }


    public void setFolderIcon(Bitmap folderIcon) {
        this.folderIcon = folderIcon;
    }

    public void setFolderName(String folderName) {
        this.folderName = folderName;
    }

    public void setFolderContents(String folderContents) {
        this.folderContents = folderContents;
    }
}
