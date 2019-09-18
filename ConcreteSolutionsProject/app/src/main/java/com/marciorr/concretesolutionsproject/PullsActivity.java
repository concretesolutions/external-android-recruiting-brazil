package com.marciorr.concretesolutionsproject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.marciorr.concretesolutionsproject.adapter.CustomAdapter;
import com.marciorr.concretesolutionsproject.model.RetroUser;
import com.marciorr.concretesolutionsproject.network.GetDataService;
import com.marciorr.concretesolutionsproject.network.RetrofitClientInstance;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PullsActivity extends AppCompatActivity {

    public String nomePulls;
    private String nomeRepo;
    private ImageView avatar;
    private TextView loginUsuarioTV;
    public static String enviaPulls;
    private CustomAdapter adapter;
    private RecyclerView recyclerView;
    ProgressDialog progressDoalog;
    private Toolbar toolbarUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pulls);

        //Recupera as Intents
        Intent i = getIntent();
        nomePulls = i.getStringExtra("pulls");
        nomeRepo = i.getStringExtra("repo");

        //Localiza os componentes na tela
        toolbarUsuario = findViewById(R.id.toolbar_usuario);

        //Cria a Toolbar
        toolbarUsuario.setTitle(nomeRepo);
        toolbarUsuario.setTitleTextColor(getResources().getColor(R.color.colorWhite));
        setSupportActionBar( toolbarUsuario );
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //Envia o nome do Usuário para a instância do Retrofit
        enviaPullsRFCI();

        progressDoalog = new ProgressDialog(PullsActivity.this);
        progressDoalog.setMessage("Carregando....");
        progressDoalog.show();

        //Cria um handle para a instância do Retrofit
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);


        Call<List<RetroUser>> call = service.getAllUsers();
        call.enqueue(new Callback<List<RetroUser>>() {

            @Override
            public void onResponse(Call<List<RetroUser>> call, Response<List<RetroUser>> response) {
                progressDoalog.dismiss();
                generateDataList(response.body());
            }

            @Override
            public void onFailure(Call<List<RetroUser>> call, Throwable t) {
                progressDoalog.dismiss();
                Toast.makeText(PullsActivity.this, "Algo deu errado...Tente mais tarde!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    //Método para gerar a lista usando o RecyclerView com o CustomAdapter
    private void generateDataList(List<RetroUser> userList) {
        recyclerView = findViewById(R.id.customRecyclerView);
        adapter = new CustomAdapter(this, userList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(PullsActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    void enviaPullsRFCI(){
        enviaPulls  = nomePulls;
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}