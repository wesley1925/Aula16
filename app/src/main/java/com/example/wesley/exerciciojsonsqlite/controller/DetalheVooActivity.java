package com.example.wesley.exerciciojsonsqlite.controller;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.text.NumberFormat;
import java.util.Locale;

import com.example.wesley.exerciciojsonsqlite.R;
import com.example.wesley.exerciciojsonsqlite.model.Voo;
import com.example.wesley.exerciciojsonsqlite.network.VooRequester;
import com.example.wesley.exerciciojsonsqlite.util.Util;

public class DetalheVooActivity extends ActionBarActivity {
    TextView vooNome;
    TextView vooOrigem;
    ImageView vooImageView;
    TextView vooPreco;
    TextView vooDestino;


    VooRequester requester;
    ProgressBar mProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhe_voo);

        Intent intent = getIntent();
        final Voo voo = (Voo)intent.getSerializableExtra(ListaVooActivity.VOO);
        setupViews(voo);

        if(!voo.getNome().equals(Voo.NAO_ENCONTRADO)) {
            requester = new VooRequester();
            if (requester.isConnected(this)) {

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            mProgress.setVisibility(View.VISIBLE);
                            final Bitmap img = requester.getImage(voo.getImagem());
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    vooImageView.setImageBitmap(img);
                                    mProgress.setVisibility(View.INVISIBLE);
                                }
                            });

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            } else {
                Resources res = getResources();
                Drawable drawable = res.getDrawable(R.drawable.nao_encontrado);
                vooImageView.setImageDrawable(drawable);
                Toast toast = Toast.makeText(this, "Rede indisponível!", Toast.LENGTH_LONG);
                toast.show();
            }
        } else {
            Resources res = getResources();
            Drawable drawable = res.getDrawable(R.drawable.nao_encontrado);
            vooImageView.setImageDrawable(drawable);
        }

    }

    private void setupViews(Voo voo) {

        vooNome = (TextView) findViewById(R.id.txt_voo_nome);
        vooNome.setText(voo.getNome());


        vooOrigem = (TextView) findViewById(R.id.txt_voo_origem);
        vooOrigem.setText(voo.getOrigem());

        vooImageView= (ImageView) findViewById(R.id.voo_image_view);
        Drawable drawable = Util.getDrawable(this, voo.getImagem());
        vooImageView.setImageDrawable(drawable);

        vooPreco = (TextView) findViewById(R.id.txt_voo_preco);
        Locale locale = new Locale("pt", "BR");
        NumberFormat formatter = NumberFormat.getCurrencyInstance(locale);
        vooPreco.setText(""+formatter.format(voo.getPreco()));

        vooDestino= (TextView) findViewById(R.id.txt_voo_destino);
        vooDestino.setText(voo.getDestino());

    }

}
