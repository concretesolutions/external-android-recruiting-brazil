package com.marciorr.concretesolutionsproject.adapter;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.cardview.widget.CardView;
import com.marciorr.concretesolutionsproject.R;
import com.marciorr.concretesolutionsproject.model.RetroUser;
import com.squareup.picasso.Picasso;
import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.CustomViewHolder> {

    private List<RetroUser> dataList;
    private Context context;

    public CustomAdapter(Context context, List<RetroUser> dataList){
        this.context = context;
        this.dataList = dataList;
    }

    //Cria a lista de repositórios
    class CustomViewHolder extends RecyclerView.ViewHolder {

        public final View mView;

        TextView descricaoPull;
        TextView nomePull;
        TextView nomeUsuario;
        TextView criacao;
        String fotoUsuarioURL;
        String htmlURL;
        ImageView fotoUsuario;
        private CardView cardViewFriend;

        CustomViewHolder(View itemView) {
            super(itemView);
            mView = itemView;

            descricaoPull = mView.findViewById(R.id.descricao_pullId);
            nomePull = mView.findViewById(R.id.nome_pullId);
            nomeUsuario = mView.findViewById(R.id.nomeUsuarioId);
            criacao = mView.findViewById(R.id.criacaoId);
            fotoUsuario = mView.findViewById(R.id.fotoUsuarioId);
            cardViewFriend = mView.findViewById(R.id.card_view_friend);
        }
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.custom_row, parent, false);
        return new CustomViewHolder(view);
    }

    //Define posições na lista e adiciona o evento de clique
    @Override
    public void onBindViewHolder(final CustomViewHolder holder, final int position) {

        //Recupera as dimensões da tela
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        int width = (metrics.widthPixels)/5;
        int height = width;
        holder.fotoUsuario.getLayoutParams().width = width;
        holder.fotoUsuario.getLayoutParams().height = height;


        //Redimensiona o poster padrão
        Drawable drPoster = context.getResources().getDrawable(R.drawable.poster);
        Bitmap bitmap = ((BitmapDrawable) drPoster).getBitmap();
        Drawable redimPoster = new BitmapDrawable(context.getResources(), Bitmap.createScaledBitmap(bitmap, width, height, true));

        holder.nomePull.setText(dataList.get(position).getTitle());
        holder.descricaoPull.setText(dataList.get(position).getBody());
        holder.criacao.setText(dataList.get(position).getCreated_at().replace("T", " ").replace("Z", ""));
        holder.nomeUsuario.setText(dataList.get(position).getUser().login);
        holder.fotoUsuarioURL = dataList.get(position).getUser().avatar_url;
        holder.htmlURL = dataList.get(position).getHtml_url();
        Picasso.get()
                .load(holder.fotoUsuarioURL)
                .placeholder( redimPoster )
                .error( redimPoster )
                .resize(width, 0)
                .into(holder.fotoUsuario);

        holder.cardViewFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Uri uri = Uri.parse(holder.htmlURL);
                Intent likeIng = new Intent(Intent.ACTION_VIEW, uri);

                try {
                    context.startActivity(likeIng);
                } catch (ActivityNotFoundException e) {
                    context.startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse(holder.htmlURL)));
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }
}
