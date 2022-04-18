package com.example.j1;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.j1.Retrofit2.APIUltils;
import com.example.j1.Retrofit2.DataClient;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginOkActivity2 extends AppCompatActivity {

    Button btdelete;
    TextView txtuser,txtpass;
    ImageView imgclient;
    ArrayList<CClient> cClientArrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_ok2);
        mappingfunc();
        inituserinfo();
        btdelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder dialog= new AlertDialog.Builder(LoginOkActivity2.this);
                dialog.setTitle("Are you sure! ");
                dialog.setMessage("By deleting your account, " +
                        "your data will be removed from our system " +
                        "and you will not be able to control your home!");
                dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String namefolder = cClientArrayList.get(0).getImagepath();
                        namefolder = namefolder.substring(namefolder.lastIndexOf("/"));
                        DataClient dataClient= APIUltils.getData();
                        Call<String> callback= dataClient.DeleteData(cClientArrayList.get(0).getId(),namefolder);
                        callback.enqueue(new Callback<String>() {
                            @Override
                            public void onResponse(Call<String> call, Response<String> response) {
                                String results= response.body();
                                if (results.equals("deleted")){
                                    Toast.makeText(LoginOkActivity2.this, "Successful deleted", Toast.LENGTH_LONG).show();
                                    finish();
                                }
                            }
                            @Override
                            public void onFailure(Call<String> call, Throwable t) {
                                Toast.makeText(LoginOkActivity2.this, "Failed, Please try again!", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
                //Log.d("BBB", namefolder);
                dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                AlertDialog alertDialog= dialog.create();
                alertDialog.show();
            }
        });
    }

    private void inituserinfo() {
        Intent intent = getIntent();
        cClientArrayList=intent.getParcelableArrayListExtra("arrayclient");
        //Log.d("AAA",cClientArrayList.get(0).getUsername());
        //Log.d("AAA",cClientArrayList.get(0).getPassword());
        txtuser.setText(" " + cClientArrayList.get(0).getUsername() + " !");
        txtpass.setText("Password: " + cClientArrayList.get(0).getPassword());
        Picasso.get().load(cClientArrayList.get(0).getImagepath()).into(imgclient);

    }


    private void mappingfunc() {
        btdelete=findViewById(R.id.buttondelete);
        txtuser=findViewById(R.id.infousername);
        txtpass=findViewById(R.id.infopassword);
        imgclient=findViewById(R.id.logimage);
    }

    public void back(View view){
        startActivity(new Intent(LoginOkActivity2.this,MainActivity.class));

    }
}