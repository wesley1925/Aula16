package com.example.wesley.exerciciojsonsqlite.controller;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;

import com.example.wesley.exerciciojsonsqlite.R;
import com.example.wesley.exerciciojsonsqlite.data.AeroportosDb;
import com.example.wesley.exerciciojsonsqlite.model.Voo;
import com.example.wesley.exerciciojsonsqlite.network.VooRequester;

public class MainActivity extends ActionBarActivity {

    Spinner spinnerOrigem;
    Spinner spinnerDestino;

    Button btnConsultar;
    String origem,destino;
    ArrayList<Voo> voos;
    //final String servidor = "jbossews-cerveja.rhcloud.com";
    //final String servidor = "10.0.2.2:8080/arqdesis_json";
    final String servidor = "192.168.1.12:8080";
    VooRequester requester;
    ProgressBar mProgress;
    Intent intent;
    Context contexto;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.contexto = this;
        setupViews();

    }

    @Override
    protected void onRestart(){
        super.onRestart();
        spinnerDestino.setSelection(0);
        spinnerOrigem.setSelection(0);
    }

    private void setupViews() {
        origem = "";
        destino = "";
        btnConsultar = (Button) findViewById(R.id.botao_enviar);
        mProgress = (ProgressBar) findViewById(R.id.carregando);

        spinnerOrigem = (Spinner) findViewById(R.id.dropdown_origem);
        new CarregaSpinnerAeroportos().execute(AeroportosDb.AEROPORTOS);
        spinnerOrigem.setOnItemSelectedListener(new OrigemSelecionado());

        spinnerDestino = (Spinner) findViewById(R.id.dropdown_destino);
        new CarregaSpinnerAeroportos().execute(AeroportosDb.AEROPORTOS);
        spinnerDestino.setOnItemSelectedListener(new DestinoSelecionada());


        mProgress.setVisibility(View.INVISIBLE);

    }

    private class OrigemSelecionado implements AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            origem = (String) parent.getItemAtPosition(position);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }

    private class DestinoSelecionada implements AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            destino = (String) parent.getItemAtPosition(position);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }
    public void consultarVoos(View view) {
        consultar(view, SIMPLES);
    }

    public void consultarVoosMelhor(View view) {
        consultar(view, MELHOR);
    }

    private String anoAjuste(String ano){
        if(ano.length() == 3)return  "0"+ano;
        if(ano.length() == 2)return  "00"+ano;
        if(ano.length() == 1)return  "000"+ano;
        return ano;
    }
    // constante static para identificar o parametro
    public final static String VOOS = "br.usjt.rafael.aula.VOOS";
    public final static String SIMPLES = "br.usjt.rafael.aula.SIMPLES";
    public final static String MELHOR = "br.usjt.rafael.aula.MELHOR";
    public final static String MODO = "br.usjt.rafael.aula.MODO";
    //será chamado quando o usuário clicar em enviar
    public void consultar(View view, String modo){
        final String passarOrigem = this.origem.equals("Escolha um aeroporto")?"":origem;
        final String passarDestino = this.destino.equals("Escolha um aeroporto")?"":destino;
        final String passarModo = modo;
        requester = new VooRequester();
        if(requester.isConnected(this)) {
            intent = new Intent(this, ListaVooActivity.class);

            mProgress.setVisibility(View.VISIBLE);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        voos = requester.get("http://" + servidor + "/ProjetoARQDESIS_Java/ListagemVooJson", passarOrigem, passarDestino);
                        //voos = requester.get("http://" + servidor + "/selecao.json", pEstilo, pCor, pPais);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                intent.putExtra(VOOS, voos);
                                intent.putExtra(MODO,passarModo);
                                mProgress.setVisibility(View.INVISIBLE);
                                startActivity(intent);
                            }
                        });

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        } else {
            Toast toast = Toast.makeText(this, "Rede indisponível!", Toast.LENGTH_LONG);
            toast.show();
        }
    }

    private class CarregaSpinnerAeroportos extends AsyncTask<String, Void, ArrayList<String>>{

        @Override
        protected ArrayList<String> doInBackground(String... params) {
            AeroportosDb db = new AeroportosDb(contexto);
            ArrayList<String> lista = db.selecionaAeroportos();
            if(lista.size() == 1)
                db.insereCor();
            lista = db.selecionaAeroportos();
            return lista;
        }

        @Override
        protected void onPostExecute(ArrayList<String> result){
            ArrayAdapter<String> aeroportosAdapter = new ArrayAdapter<String>(contexto,
                    android.R.layout.simple_spinner_item, result);
            spinnerOrigem.setAdapter(aeroportosAdapter);
            spinnerDestino.setAdapter(aeroportosAdapter);
        }
    }


}

