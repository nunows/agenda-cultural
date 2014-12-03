package moss.idsca.ac.eventos;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import org.apache.http.client.ClientProtocolException;

import moss.idsca.ac.App;
import moss.idsca.ac.R;
import moss.idsca.ac.Utils;
import ws.Evento;
import ws.WebService;
import android.app.ListActivity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;


public class EventosList extends ListActivity {

	private App app;
	private int op;
	private int idTipoEvento;
	private List<Evento> eventos;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {	
		super.onCreate(savedInstanceState);
		setContentView(R.layout.loading);	
		
		app = (App) getApplication();
		
		idTipoEvento = Integer.parseInt(getIntent().getStringExtra("idTipoEvento"));
		op = getIntent().getIntExtra("op", 0);
		
		new EventosTask().execute();
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		
		int idEvento = (int) id;
		
		app.setEvento(eventos.get(idEvento));
		Intent i = new Intent(this, EventosDetail.class);
		i.putExtra("idEvento", idEvento);		
		startActivity(i);
	}
	
	
	/**
	 * Class que realiza em backgroud pedido ao webservice e cria GUI
	 * 
	 */
	private class EventosTask extends AsyncTask<Void, Void, List<Evento>> {

		@Override
		protected List<Evento> doInBackground(Void... params) {
			WebService ws = new WebService();
			
			try {				
				if (op == 1) {
					//obtém últimos eventos
					eventos = ws.getEventos();
				} else if (op == 2) {
					//obtém eventos por tipo
					eventos = ws.getEventosByTipo(idTipoEvento);	
				}				
			} catch (ClientProtocolException e) {
				Utils.createToast(EventosList.this,e.getMessage());
			} catch (URISyntaxException e) {
				Utils.createToast(EventosList.this,e.getMessage());
			} catch (IOException e) {
				Utils.createToast(EventosList.this,e.getMessage());
			}
			return eventos;
		}

		@Override
		protected void onPostExecute(List<Evento> result) {		
			super.onPostExecute(result);
			setContentView(R.layout.listview);			
			setListAdapter(new EventoAdapter(EventosList.this,R.layout.eventos_item, result));
		}		
	}





	
}
