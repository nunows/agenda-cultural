package moss.idsca.ac.teventos;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import moss.idsca.ac.R;
import moss.idsca.ac.Utils;
import moss.idsca.ac.eventos.EventosList;

import org.apache.http.client.ClientProtocolException;

import ws.TipoEvento;
import ws.WebService;
import android.app.ListActivity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;


/**
 * Lista Tipo de Eventos
 * @author nuno
 *
 */
public class TipoEventosList extends ListActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);		
		setContentView(R.layout.loading);			 
		new TipoEventosTask().execute();		
	}

	/**
	 * inicia nova Activity, lista eventos por tipo
	 */
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);					
		Intent i = new Intent(this, EventosList.class);
		
		//id tipo evento
		i.putExtra("idTipoEvento", v.getTag().toString());
		//opção = 2 (eventos por tipo)
		i.putExtra("op", 2);
		
		startActivity(i);
	}
	

	/**
	 * Class que realiza em backgroud pedido ao webservice e cria GUI
	 * 
	 */
	private class TipoEventosTask extends AsyncTask<Void, Void, List<TipoEvento>> {

		@Override
		protected List<TipoEvento> doInBackground(Void... params) {
			List<TipoEvento> dados = null;
			
			WebService ws = new WebService();
	        
	        try {
	        	dados = ws.getTipoEventos();
			} catch (ClientProtocolException e) {
				Log.d("nuno", e.getMessage());
				//Utils.createToast(TipoEventosList.this,e.getMessage());
			} catch (URISyntaxException e) {
				//Utils.createToast(TipoEventosList.this,e.getMessage());
				Log.d("nuno", e.getMessage());
			} catch (IOException e) {
				//Utils.createToast(TipoEventosList.this,e.getMessage());
				Log.d("nuno", e.getMessage());
			}
			
			return dados;
		}

		@Override
		protected void onPostExecute(List<TipoEvento> result) {
			super.onPostExecute(result);
			setContentView(R.layout.listview);			
			setListAdapter(new TipoEventosAdapter(TipoEventosList.this,R.layout.tipoeventos_item, result));
		}
	}
	
	

}
