package com.example.j1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.j1.Retrofit2.APIUltils;
import com.example.j1.Retrofit2.DataClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    Button btnreg, btnlog;
    EditText edtusername,edtpassword;
    String username;
    String password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mappingfuction();
        btnreg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,regActivity2.class);
                startActivity(intent);
            }
        });
        btnlog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                username=edtusername.getText().toString();
                password=edtpassword.getText().toString();
                if(username.length()>0 &&password.length()>0){
                    DataClient dataClient = APIUltils.getData();
                    Call<List<CClient>> callback=dataClient.Logindata(username,password);
                    callback.enqueue(new Callback<List<CClient>>() {
                        @Override
                        public void onResponse(Call<List<CClient>> call, Response<List<CClient>> response) {
                            ArrayList<CClient> arrayclient= (ArrayList<CClient>) response.body();
                            if(arrayclient.size()>0){

                                /*Log.d("BBB",arrayclient.get(0).getUsername());
                                Log.d("BBB",arrayclient.get(0).getPassword());
                                Log.d("BBB",arrayclient.get(0).getImagepath());*/
                                Intent intent = new Intent(MainActivity.this,LoginOkActivity2.class);
                                intent.putExtra("arrayclient",arrayclient);
                                startActivity(intent);

                            }

                        }

                        @Override
                        public void onFailure(Call<List<CClient>> call, Throwable t) {
                            Toast.makeText(MainActivity.this, "This user is not available. Please try again!", Toast.LENGTH_SHORT).show();

                        }
                    });



                }

            }
        });
    }

    private void mappingfuction() {
        btnlog=findViewById(R.id.buttonlogin);
        btnreg=findViewById(R.id.buttonregister);
        edtpassword=findViewById(R.id.edittextpassword);
        edtusername=findViewById(R.id.edittextusername);
    }
}