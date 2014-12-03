package moss.idsca.ac.eventos;

import ws.Evento;
import moss.idsca.ac.App;
import moss.idsca.ac.R;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;

public class EventosDetail extends Activity {

	private App app;
	private Evento evento;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		app = (App) getApplication();

		setContentView(R.layout.evento_detail);
		WebView webview = (WebView) findViewById(R.id.webView1);
		TextView txtNome = (TextView) findViewById(R.id.txtNome);
		TextView txtData = (TextView) findViewById(R.id.txtData);
		TextView txtLocalNome = (TextView) findViewById(R.id.txtLocalNome);
		TextView txtLocalMorada = (TextView) findViewById(R.id.txtLocalMorada);

		evento = app.getEvento();

		txtNome.setText(evento.getNome());
		txtData.setText(evento.getDatahora());
		txtLocalNome.setText(evento.getLocal().getNome());
		txtLocalMorada.setText(evento.getLocal().getMorada());

		webview.loadUrl(evento.getImagemUrl());
	}

	
	public void showMap(View view) {

		String url = "http://maps.google.com/maps?q="
				+ evento.getLocal().getLatitude() + ","
				+ evento.getLocal().getLongitude()+"&t=m&z=16&vpsrc=0";

		Intent i = new Intent(Intent.ACTION_VIEW);
		i.setData(Uri.parse(url));
		startActivity(i);
	}

}
