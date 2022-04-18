package com.example.j1.Retrofit2;

public class APIUltils {
    public static final String Base_Url="http://10.3.18.212/usermanagement/";
    public static DataClient getData(){
        return RetrofitClient.getClient(Base_Url).create(DataClient.class);
    }
}
//"http://10.3.18.212/usermanagement/" kunigunda 
//"http://192.168.0.235/usermanagement/"; own flat wifi
