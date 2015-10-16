package com.example.wesley.exerciciojsonsqlite.network;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;

import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

import com.example.wesley.exerciciojsonsqlite.model.Voo;

/**
 * Created by asbonato on 2/6/15.
 * Vai fazer a requisição na API REST
 */
public class VooRequester {
    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");
    OkHttpClient client = new OkHttpClient();

    public ArrayList<Voo> get(String url, String pOrigem, String pDestino) throws IOException {

        ArrayList<Voo> lista = new ArrayList<>();

        //acentuacao nao funciona se mandar via get, mesmo usando URLEncode.encode(String,UTF-8)
        RequestBody formBody = new FormEncodingBuilder()
                .addEncoded("origem", pOrigem)
                .addEncoded("destino", pDestino).build();
        Request request = new Request.Builder()
                .url(url)
                .post(formBody)
                .build();

        Response response = client.newCall(request).execute();

        String jsonStr = response.body().string();

        NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));

        try {
            JSONArray root = new JSONArray(jsonStr);
            JSONObject item = null;
            for (int i = 0; i < root.length(); i++ ) {
                item = (JSONObject)root.get(i);

                String nome = item.getString("nome");
                JSONObject origemO = item.getJSONObject("aeroportoOrigem");
                JSONObject destinoO = item.getJSONObject("aeroportoDestino");
                String origem = origemO.getString("nome");
                String destino = destinoO.getString("nome");
                //String imagem = item.getString("marca");
                String imagem = "marca1";
                //double preco = item.getDouble("preco");
                double preco = 1399.00;


                lista.add(new Voo(nome,origem,destino,imagem,preco));
            }
        } catch(JSONException e){
            e.printStackTrace();
        }
        finally {
            if(lista.size() == 0)
                //Voo.NAO_ENCONTRADA
                lista.add(new Voo(Voo.NAO_ENCONTRADO,pOrigem, pDestino, "voo_nenhum", 0.0));
        }
        return lista;
    }
    public Bitmap getImage(String url) throws IOException {

        Bitmap img = null;

        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = client.newCall(request).execute();

        InputStream is = response.body().byteStream();

        img = BitmapFactory.decodeStream(is);

        is.close();

        return img;
    }

    public boolean isConnected(Context context) {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) context
                        .getSystemService(Context.CONNECTIVITY_SERVICE);
        return connectivityManager.getActiveNetworkInfo() != null
                && connectivityManager.getActiveNetworkInfo().isConnected();
    }


}
