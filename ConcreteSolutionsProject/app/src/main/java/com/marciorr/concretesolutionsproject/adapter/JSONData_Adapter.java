package com.marciorr.concretesolutionsproject.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.marciorr.concretesolutionsproject.PullsActivity;
import com.marciorr.concretesolutionsproject.R;
import com.marciorr.concretesolutionsproject.model.JSONData_Model;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;

public class JSONData_Adapter extends ArrayAdapter<JSONData_Model>{

	private ArrayList<JSONData_Model> populares;
	private Context context;
	public static int posicao;

	public JSONData_Adapter(Context c, ArrayList<JSONData_Model> objects) {
		super(c, 0, objects);
		this.populares = objects;
		this.context = c;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {

		posicao = position;

		View view = null;

		//Verifica se a lista está vazia
		if( populares!=null ){

			//Inicializar objeto para montagem da View
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);

			//Monta View a partir do xml
			view = inflater.inflate(R.layout.custom_listview, parent, false);

			//Recupera elemento para exibição
			TextView nomeRepo = view.findViewById(R.id.nomeRepoId);
			TextView descricaoRepo = view.findViewById(R.id.descricaoRepoId);
			TextView forks = view.findViewById(R.id.forksId);
			TextView stars = view.findViewById(R.id.starsId);
			TextView nomeUsuario = view.findViewById(R.id.nomeUsuarioId);
			ImageView fotoUsuario = view.findViewById(R.id.fotoUsuarioId);

			//Recupera as dimensões da tela
			DisplayMetrics metrics = context.getResources().getDisplayMetrics();
			int width = (metrics.widthPixels)/5;
			int height = width;
			fotoUsuario.getLayoutParams().width = width;
			fotoUsuario.getLayoutParams().height = height;

			final JSONData_Model dados = populares.get( position );

			//Redimensiona o poster padrão
			String fotoUsuarioURL = dados.getFotoUsuario();
			Drawable drPoster = context.getResources().getDrawable(R.drawable.poster);
			Bitmap bitmap = ((BitmapDrawable) drPoster).getBitmap();
			Drawable redimPoster = new BitmapDrawable(context.getResources(), Bitmap.createScaledBitmap(bitmap, width, height, true));


			nomeRepo.setText( dados.getNomeRepo() );
			nomeUsuario.setText( dados.getNomeUsuario() );
			descricaoRepo.setText( dados.getDescricaoRepo() );
			forks.setText( "" + dados.getForksRepo() );
			stars.setText( "" + dados.getStarsRepo() );
			Picasso.get()
					.load(fotoUsuarioURL)
					.placeholder( redimPoster )
					.error( redimPoster )
					.resize(width, 0)
					.into(fotoUsuario);

			view.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View arg0) {

					Intent intent = new Intent(context, PullsActivity.class);
					String nomeRepo = dados.getNomeRepo();
					String nomeUsuario = dados.getNomeUsuario();
					String nomePulls = nomeUsuario + "/" + nomeRepo + "/";
					intent.putExtra("pulls", nomePulls);
					intent.putExtra("repo", nomeRepo);
					context.startActivity(intent);

				}
			});

		}

		return view;

	}
}