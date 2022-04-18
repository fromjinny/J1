package com.example.j1;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CClient implements Parcelable {

@SerializedName("id")
@Expose
private String id;
@SerializedName("username")
@Expose
private String username;
@SerializedName("password")
@Expose
private String password;
@SerializedName("imagepath")
@Expose
private String imagepath;

    protected CClient(Parcel in) {
        id = in.readString();
        username = in.readString();
        password = in.readString();
        imagepath = in.readString();
    }
    //new implement
    public static final Creator<CClient> CREATOR = new Creator<CClient>() {
        @Override
        public CClient createFromParcel(Parcel in) {
            return new CClient(in);
        }

        @Override
        public CClient[] newArray(int size) {
            return new CClient[size];
        }
    };

    public String getId() {
return id;
}

public void setId(String id) {
this.id = id;
}

public String getUsername() {
return username;
}

public void setUsername(String username) {
this.username = username;
}

public String getPassword() {
return password;
}

public void setPassword(String password) {
this.password = password;
}

public String getImagepath() {
return imagepath;
}

public void setImagepath(String imagepath) {
this.imagepath = imagepath;
}

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(username);
        parcel.writeString(password);
        parcel.writeString(imagepath);
    }
}