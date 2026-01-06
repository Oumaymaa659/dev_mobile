package com.example.darcaftan.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.darcaftan.R;
import com.example.darcaftan.models.CartItem;

import java.util.List;

/**
 * Adaptateur personnalisé pour afficher les articles du panier
 */
public class CartAdapter extends BaseAdapter {
    private Context context;
    private List<CartItem> cartItems;
    private OnItemDeleteListener deleteListener;

    public interface OnItemDeleteListener {
        void onItemDelete(int position);
    }

    public CartAdapter(Context context, List<CartItem> cartItems) {
        this.context = context;
        this.cartItems = cartItems;
    }

    public void setOnItemDeleteListener(OnItemDeleteListener listener) {
        this.deleteListener = listener;
    }

    @Override
    public int getCount() {
        return cartItems.size();
    }

    @Override
    public Object getItem(int position) {
        return cartItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_panier, parent, false);
            holder = new ViewHolder();
            holder.imgCaftan = convertView.findViewById(R.id.img_caftan);
            holder.textNomCaftan = convertView.findViewById(R.id.text_nom_caftan);
            holder.textPrixCaftan = convertView.findViewById(R.id.text_prix_caftan);
            holder.textTailleCaftan = convertView.findViewById(R.id.text_taille_caftan);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        CartItem item = cartItems.get(position);

        // Afficher les données
        holder.imgCaftan.setImageResource(item.getImageResId());
        holder.textNomCaftan.setText(item.getTitre());
        holder.textPrixCaftan.setText("Prix : " + item.getPrix());

        // Afficher la taille si disponible
        if (item.getTaille() != null && !item.getTaille().isEmpty()) {
            holder.textTailleCaftan.setText("Taille : " + item.getTaille());
            holder.textTailleCaftan.setVisibility(View.VISIBLE);
        } else {
            holder.textTailleCaftan.setVisibility(View.GONE);
        }

        return convertView;
    }

    static class ViewHolder {
        ImageView imgCaftan;
        TextView textNomCaftan;
        TextView textPrixCaftan;
        TextView textTailleCaftan;
    }
}
