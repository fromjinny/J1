package com.example.j1;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.j1.Retrofit2.APIUltils;
import com.example.j1.Retrofit2.DataClient;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class regActivity2 extends AppCompatActivity {

    ImageView imgreg;
    EditText edtuser,edtpass;
    Button btcancel,btconfirm;
    int Request_Code_Image=123;
    String realpath="";
    String username;
    String password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg2);
        mapp();//mapping
        imgreg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent,Request_Code_Image);
            }
        });
        btconfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                username=edtuser.getText().toString();
                password=edtpass.getText().toString();
                if(username.length()>0 && password.length()>0){
                    File file= new File(realpath);
                    String file_path= file.getAbsolutePath();
                    String[] filenamearr= file_path.split("\\.");
                    file_path= filenamearr[0]+System.currentTimeMillis()+"."+filenamearr[1];
                    RequestBody requestBody= RequestBody.create(MediaType.parse("multipart/form-data"),file);
                    MultipartBody.Part body= MultipartBody.Part.createFormData("uploaded_file",file_path,requestBody);
                    DataClient dataClient= APIUltils.getData();
                    retrofit2.Call<String> callback= dataClient.UploadPhoto(body);
                    callback.enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(retrofit2.Call<String> call, Response<String> response) {
                            if (response!=null){
                                String message= response.body();
                                if (message.length()>0){
                                    DataClient insertdata= APIUltils.getData();
                                    retrofit2.Call<String> callback=insertdata.InsertData(username,password,APIUltils.Base_Url+"image/"+message);
                                    callback.enqueue(new Callback<String>() {
                                        @Override
                                        public void onResponse(Call<String> call, Response<String> response) {
                                            String result = response.body();
                                            if(result.equals("SUCCESSFUL")){
                                                Toast.makeText(regActivity2.this, "Sucessful added", Toast.LENGTH_SHORT).show();
                                                finish();
                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<String> call, Throwable t) {

                                        }
                                    });

                                }
                            }

                        }

                        @Override
                        public void onFailure(retrofit2.Call<String> call, Throwable t) {
                            Log.d("BBB",t.getMessage());
                        }
                    });

                }else {
                    Toast.makeText(regActivity2.this, "Don't leave your username/password empty", Toast.LENGTH_SHORT).show();

                }

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode==Request_Code_Image &&resultCode==RESULT_OK && data!=null){
            Uri uri =data.getData();
            realpath=getRealPathFromUri(uri);
            try {
                InputStream inputStream= getContentResolver().openInputStream(uri);
                Bitmap bitmap= BitmapFactory.decodeStream(inputStream);
                imgreg.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void mapp() {
        imgreg=findViewById(R.id.imageviewreg);
        edtuser=findViewById(R.id.edittextreguser);
        edtpass=findViewById(R.id.edittextregpass);
        btcancel=findViewById(R.id.buttoncancel);
        btconfirm=findViewById(R.id.buttonconfirm);
    }
    public String getRealPathFromUri (Uri contentUri){

        String path = null;
        String[] proj= {MediaStore.MediaColumns.DATA};
        Cursor cursor= getContentResolver().query(contentUri,proj,null, null, null);
        if (cursor.moveToFirst()){
            int column_index=cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
            path=cursor.getString(column_index);

        }
        cursor.close();
        return path;

    }
    public void cancel(View view){
        startActivity(new Intent(regActivity2.this,MainActivity.class));
    }

}
