package com.example.wesley.exerciciojsonsqlite.util;

import android.widget.ImageView;
import android.widget.TextView;


public class ViewHolder {
    private ImageView fotinhoVoo;
    private TextView dadosVoo, detalhesVoo;

    public ViewHolder(ImageView fotinhoVoo, TextView dadosVoo, TextView detalhesVoo) {
        this.fotinhoVoo = fotinhoVoo;
        this.dadosVoo = dadosVoo;
        this.detalhesVoo = detalhesVoo;
    }

    public ImageView getFotinhoVoo() {
        return fotinhoVoo;
    }

    public void setFotinhoVoo(ImageView fotinhoVoo) {
        this.fotinhoVoo = fotinhoVoo;
    }

    public TextView getDadosVoo() {
        return dadosVoo;
    }

    public void setDadosVoo(TextView dadosVoo) {
        this.dadosVoo = dadosVoo;
    }

    public TextView getDetalhesVoo() {
        return detalhesVoo;
    }

    public void setDetalhesVoo(TextView detalhesVoo) {
        this.detalhesVoo = detalhesVoo;
    }
}
