package com.codingblocks.gurleen.cloud9.DataModels;

import android.graphics.Bitmap;

/**
 * Created by hp on 7/25/2017.
 */

public class ImageFolderData {

    Bitmap image;
    String imageName;

    public ImageFolderData(Bitmap image, String imageName) {
        this.image = image;
        this.imageName = imageName;
    }


    public Bitmap getImage() {
        return image;
    }

    public String getImageName() {
        return imageName;
    }


    public void setImage(Bitmap image) {
        this.image = image;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }
}
