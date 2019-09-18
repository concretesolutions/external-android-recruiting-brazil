package com.marciorr.concretesolutionsproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;
import com.marciorr.concretesolutionsproject.adapter.JSONData_Adapter;
import com.marciorr.concretesolutionsproject.model.JSONData_Model;
import com.marciorr.concretesolutionsproject.network.RemoteFetch;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;

public class PopularesActivity extends AppCompatActivity {

    private String nomeLinguagem;
    private int pagAtual;
    private ListView lvPopulares;
    private int numResultados;
    private ArrayList<JSONData_Model> listaPopulares;
    private Toolbar toolbarLista;
    private JSONData_Adapter adapter;
    Handler handler;

    public PopularesActivity(){
        handler = new Handler();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_populares);

        //Recupera as Intents
        Intent i = getIntent();
        nomeLinguagem = i.getStringExtra("linguagem");

        //Localiza componentes na tela
        lvPopulares = findViewById(R.id.lv_populares);
        toolbarLista = findViewById(R.id.toolbar_lista);

        //Cria a Toolbar
        toolbarLista.setTitle( nomeLinguagem );
        toolbarLista.setTitleTextColor(getResources().getColor(R.color.primary_light));
        setSupportActionBar( toolbarLista );
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbarLista.getNavigationIcon().setColorFilter(getResources().getColor(R.color.primary_light), PorterDuff.Mode.SRC_ATOP);

        //Cria a lista de populares
        listaPopulares = new ArrayList<>();
        adapter = new JSONData_Adapter(PopularesActivity.this, listaPopulares);
        lvPopulares.setAdapter(adapter);
        //adapter.notifyDataSetChanged();

        //Define a página inicial
        pagAtual = 1;

        //Faz a busca da lista
        listaPopulares.clear();
        String popularesURL = "search/repositories?q=language:" + nomeLinguagem + "&sort=stars&page=" + pagAtual;
        buscaPopularesGit( popularesURL );

    }

    //Faz a busca do objeto JSON
    private void buscaPopularesGit(final String populares){
        new Thread(){
            public void run(){
                final JSONObject json = RemoteFetch.getJSON(PopularesActivity.this, populares);
                if(json == null){
                    handler.post(new Runnable(){
                        public void run(){
                            Toast.makeText(PopularesActivity.this,
                                    "Linguagem não encontrada!",
                                    Toast.LENGTH_LONG).show();
                        }
                    });
                } else {
                    handler.post(new Runnable(){
                        public void run(){
                            renderPopulares(json);
                        }
                    });
                }
            }
        }.start();
    }

    //Renderiza objeto JSON
    private void renderPopulares(JSONObject json){
        try {

            String dados = "";
            dados = json.getString("items");
            JSONArray dadosArray = new JSONArray( dados );

            int pos = 0;
            int tamanhoLista = dadosArray.length();

            while( pos < tamanhoLista ) {

                JSONObject dadosObject = dadosArray.getJSONObject(pos);
                JSONObject dadosUser = new JSONObject(dadosObject.getString("owner"));

                String nomeRepo = dadosObject.getString("name");
                String descricaoRepo = dadosObject.getString("description");
                String nomeUsuario = dadosUser.getString("login");
                String fotoUsuario = dadosUser.getString("avatar_url");
                int forksRepo = dadosObject.getInt("forks_count");
                int starsRepo = dadosObject.getInt("stargazers_count");

                listaPopulares.add(new JSONData_Model(nomeRepo, descricaoRepo, nomeUsuario, fotoUsuario, forksRepo, starsRepo));

                pos++;

                if(pos == tamanhoLista){
                    pagAtual++;
                    if(pagAtual == 35){
                        break;
                    }

                    /*Para não exceder o número de requests por minuto da API do GitHub e evitar
                    que o usuário avançasse páginas muito rapidamente e fosse bloqueado pela API*/
                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            String popularesURL = "search/repositories?q=language:" + nomeLinguagem + "&sort=stars&page=" + pagAtual;
                            buscaPopularesGit( popularesURL );
                        }
                    }, 6000);


                }

            }
            adapter.notifyDataSetChanged();

            numResultados = listaPopulares.size();
            String pluralResultado;
            if( numResultados > 1 ){
                pluralResultado = "results";
            }else{
                pluralResultado = "result";
            }
            toolbarLista.setTitle( nomeLinguagem.replace("+", " ") + " (" + numResultados + " " + pluralResultado + ")" );

        }catch(Exception e){
            Log.e("Concrete Sol Project", "One or more fields not found in the JSON data");
        }
    }
}
