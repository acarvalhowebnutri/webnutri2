package br.com.josecarlosestevao.appnutriv1.Usuario;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

import br.com.josecarlosestevao.appnutriv1.R;
import br.com.josecarlosestevao.appnutriv1.Receita.Receita;

/**
 * Created by Dell on 20/11/2017.
 */

public class AdaptadorListaReceitaRecebidas extends BaseExpandableListAdapter {

    private List<String> lstGrupos;
    private HashMap<String, List<Receita>> lstItensGrupos;
    private Context context;

    public AdaptadorListaReceitaRecebidas(Context context, List<String> grupos, HashMap<String, List<Receita>> itensGrupos) {
        // inicializa as variáveis da classe
        this.context = context;
        lstGrupos = grupos;
        lstItensGrupos = itensGrupos;
    }


    @Override
    public int getGroupCount() {
        return lstGrupos.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return lstItensGrupos.get(getGroup(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return lstGrupos.get(groupPosition);

    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return lstItensGrupos.get(getGroup(groupPosition)).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(
                    Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.grupo, null);
        }

        TextView tvGrupo = (TextView) convertView.findViewById(R.id.tvGrupo);
        TextView tvQtde = (TextView) convertView.findViewById(R.id.tvQtde);

        tvGrupo.setText((String) getGroup(groupPosition));
        tvQtde.setText(String.valueOf(getChildrenCount(groupPosition)));

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(
                    Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.item_grupo, null);
        }

        TextView tvItem = (TextView) convertView.findViewById(R.id.tvItem);
        TextView tvValor = (TextView) convertView.findViewById(R.id.tvValor);

        Receita produto = (Receita) getChild(groupPosition, childPosition);
        tvItem.setText(produto.getAlimento());
        tvValor.setText(String.valueOf(produto.getId()));

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}
