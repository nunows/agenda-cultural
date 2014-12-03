package moss.idsca.ac;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import moss.idsca.ac.eventos.EventosList;
import moss.idsca.ac.eventos.NearByList;
import moss.idsca.ac.teventos.TipoEventosList;

import org.apache.http.client.ClientProtocolException;

import ws.Evento;
import ws.TipoEvento;
import ws.WebService;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

/**
 * Actividade principal
 *
 */
public class Main extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);           
    }
    
    public void categorias(View view) {
    	Intent intent = new Intent(this, TipoEventosList.class);
		startActivity(intent);
    }
    
 
    public void eventos(View view) {
    	Intent intent = new Intent(this, EventosList.class);
    	
    	//id tipo evento
    	intent.putExtra("idTipoEvento", "0");
		//opção = 1 (eventos)
    	intent.putExtra("op", 1);
		
    	
		startActivity(intent);
    }
    
    public void nearby(View view) {
    	Intent intent = new Intent(this, NearByList.class);
		startActivity(intent);
    }
    
}