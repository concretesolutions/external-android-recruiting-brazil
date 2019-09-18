package com.marciorr.concretesolutionsproject.network;

import com.marciorr.concretesolutionsproject.PullsActivity;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClientInstance {

    private static Retrofit retrofit;
    private static String BASE_URL = "https://api.github.com/repos/";

    //Cria a instância do Retrofit e completa a URL com o Usuário
    public static Retrofit getRetrofitInstance() {

        if (retrofit == null) {
            String pullsRecebidos = PullsActivity.enviaPulls;
            BASE_URL = BASE_URL + pullsRecebidos;
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }else{
            String pullsRecebidos = PullsActivity.enviaPulls;
            BASE_URL = BASE_URL + pullsRecebidos;
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        BASE_URL = "https://api.github.com/repos/";
        return retrofit;
    }
}
