package com.codingblocks.gurleen.cloud9;

import android.net.Uri;

/**
 * Created by hp on 7/21/2017.
 */

public class UserData {

    String name;
    String email;
    Uri photoUrl;

    public UserData(String name, String email,  Uri photoUrl) {
        this.name = name;
        this.email = email;
        this.photoUrl = photoUrl;
    }


    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public Uri getPhotoUrl() {
        return photoUrl;
    }


    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhotoUrl(Uri photoUrl) {
        this.photoUrl = photoUrl;
    }
}
