package com.marciorr.concretesolutionsproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText caixaBusca;
    private Button botaoBusca;
    private String nomeLinguagem;
    Handler handler;
    public static String enviaUsuario;
    private Toolbar toolbarPrincipal;

    public MainActivity(){
        handler = new Handler();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        caixaBusca.setText("");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Localizar componentes na tela
        caixaBusca = findViewById(R.id.caixa_busca);
        botaoBusca = findViewById(R.id.botao_busca);
        toolbarPrincipal = findViewById(R.id.toolbar_principal);

        //Criar a Toolbar
        toolbarPrincipal.setTitle(R.string.app_name);
        toolbarPrincipal.setTitleTextColor(getResources().getColor(R.color.colorWhite));
        setSupportActionBar( toolbarPrincipal );

        //Criar botão Buscar
        botaoBusca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                nomeLinguagem = caixaBusca.getText().toString();
                if(nomeLinguagem.isEmpty()){
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(botaoBusca.getWindowToken(), 0);
                    Toast.makeText(MainActivity.this, "Digite um nome de usuário!", Toast.LENGTH_LONG).show();
                }else{
                    enviaUsuario = "";
                    Intent intent = new Intent(getApplicationContext(), PopularesActivity.class);
                    intent.putExtra("linguagem", nomeLinguagem);
                    startActivity( intent );
                }
            }
        });
    }
}