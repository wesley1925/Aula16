package com.example.wesley.exerciciojsonsqlite.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.SectionIndexer;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.Hashtable;
import java.util.Locale;

import com.example.wesley.exerciciojsonsqlite.R;
import com.example.wesley.exerciciojsonsqlite.model.Voo;
import com.example.wesley.exerciciojsonsqlite.util.Util;
import com.example.wesley.exerciciojsonsqlite.util.ViewHolder;

/**
 * Created by asbonato on 9/6/15.
 */
public class VooAdapter extends BaseAdapter implements SectionIndexer
{
    Activity context;
    Voo[] voos;
    Object[] sectionHeaders;
    Hashtable<Integer, Integer> positionForSectionMap;
    Hashtable<Integer, Integer> sectionForPositionMap;

    public VooAdapter(Activity context, Voo[] voos){
        this.context = context;
        this.voos = voos;
        sectionHeaders = SectionIndexBuilder.BuildSectionHeaders(voos);
        positionForSectionMap = SectionIndexBuilder.BuildPositionForSectionMap(voos);
        sectionForPositionMap = SectionIndexBuilder.BuildSectionForPositionMap(voos);

    }
    @Override
    public int getCount() {
        return voos.length;
    }

    @Override
    public Object getItem(int position) {
        if(position >= 0 && position < voos.length)
            return voos[position];
        else
            return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //o list view recicla os layouts para melhor performance
        //o layout reciclado vem no parametro convert view
        View view = convertView;
        //se nao recebeu um layout para reutilizar deve inflar um
        if(view == null) {
            //um inflater transforma um layout em uma view
            LayoutInflater inflater = (LayoutInflater) context.getSystemService
                    (Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.linha_voo, parent, false);

            ImageView fotinhoCerveja = (ImageView)view.findViewById(R.id.fotinhoVooImageView);
            TextView nomeCerveja = (TextView)view.findViewById(R.id.dadosVooTextView);
            TextView detalhesCerveja = (TextView)view.findViewById(R.id.detalhesVooTextView);
            //faz cache dos widgets instanciados na tag da view para reusar quando houver reciclagem
            view.setTag(new ViewHolder(fotinhoCerveja, nomeCerveja, detalhesCerveja));
        }
        //usa os widgets cacheados na view reciclada
        ViewHolder holder = (ViewHolder)view.getTag();
        //carrega os novos valores
        Drawable drawable = Util.getDrawable(context, voos[position].getImagem());
        holder.getFotinhoVoo().setImageDrawable(drawable);
        Locale locale = new Locale("pt", "BR");
        NumberFormat formatter = NumberFormat.getCurrencyInstance(locale);
        holder.getDadosVoo().setText(
                voos[position].getNome()
        );
        holder.getDetalhesVoo().setText(voos[position].getOrigem()+" - "+voos[position].getDestino()+"\n"+"\n"+String.format(formatter.format(voos[position].getPreco())));

        return view;
    }
//metodos da interface SectionIndexer


    @Override
    public Object[] getSections() {
        return sectionHeaders;
    }

    @Override
    public int getPositionForSection(int sectionIndex) {
        return positionForSectionMap.get(sectionIndex).intValue();
    }

    @Override
    public int getSectionForPosition(int position) {
        return sectionForPositionMap.get(position).intValue();
    }
}
