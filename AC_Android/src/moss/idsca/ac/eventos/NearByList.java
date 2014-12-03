package moss.idsca.ac.eventos;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import moss.idsca.ac.App;
import moss.idsca.ac.Gps;
import moss.idsca.ac.IGps;
import moss.idsca.ac.R;
import moss.idsca.ac.Utils;

import org.apache.http.client.ClientProtocolException;

import ws.Evento;
import ws.WebService;
import android.app.ListActivity;
import android.content.Intent;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

public class NearByList extends ListActivity implements IGps {

	private static final int DISTANCIA = 25;

	private App app;
	private List<Evento> eventos;
	private TextView txtObter;
	private Gps gps;
	private LocationManager lm;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.loading_nearby);
		txtObter = (TextView) findViewById(R.id.txtObter);

		app = (App) getApplication();

		// obtém LocationManager
		lm = (LocationManager) getSystemService(LOCATION_SERVICE);

		// lança Listener do Gps
		gps = new Gps(lm, this);		
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

	@Override
	public void actualizar() {
		// obteve sinal gps, realiza pedido
		new EventosTask().execute();
	}

	@Override
	public void alertar(String texto) {
		// TODO Auto-generated method stub

	}

	@Override
	public void activarGps() {
		setContentView(R.layout.loading_nearby);
		txtObter.setText(R.string.gps_off);
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
				eventos = ws.getEventosNearBy((float) gps.getLocation()
						.getLatitude(), (float) gps.getLocation()
						.getLongitude(), DISTANCIA);
			} catch (ClientProtocolException e) {
				Utils.createToast(NearByList.this, e.getMessage());
			} catch (URISyntaxException e) {
				Utils.createToast(NearByList.this, e.getMessage());
			} catch (IOException e) {
				Utils.createToast(NearByList.this, e.getMessage());
			}
			return eventos;
		}

		@Override
		protected void onPostExecute(List<Evento> result) {
			super.onPostExecute(result);
			setContentView(R.layout.listview);
			setListAdapter(new EventoAdapter(NearByList.this,
					R.layout.eventos_item, result));
		}
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		gps.remove();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		gps.register();
	}

}
