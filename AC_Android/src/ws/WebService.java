package ws;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * Class que contém todos os método necessário para realizar pedidos ao webservice
 * @author nuno
 *
 */
public class WebService {

	private static final String WS_URL = "http://192.168.1.69:8080/AC_WebServices";

	private DefaultHttpClient httpclient;
	private Gson gson;

	public WebService() {
		httpclient = new DefaultHttpClient();
		gson = new Gson();
	}

	/**
	 * Método que permite realizar pedidos HTTP do tipo GET
	 * @param url String url do endpoint
	 * @return @Reader
	 * @throws URISyntaxException
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	private Reader get(String url) throws URISyntaxException,
			ClientProtocolException, IOException {

		URI uri = new URI(WS_URL + url);
		InputStream data = null;
		Reader reader = null;

		HttpGet method = new HttpGet(uri);
		HttpResponse response = httpclient.execute(method);
		data = response.getEntity().getContent();

		reader = new InputStreamReader(data);

		return reader;
	}

	/**
	 * Método que retorna colecção com o Tipo de Eventos
	 * @return @List<@TipoEvento>
	 * @throws ClientProtocolException
	 * @throws URISyntaxException
	 * @throws IOException
	 */
	public List<TipoEvento> getTipoEventos() throws ClientProtocolException, URISyntaxException, IOException {
		Reader r = get("/tipoeventos");
		List<TipoEvento> list = gson.fromJson(r, new TypeToken<List<TipoEvento>>(){}.getType());
		return list;
	}
	
	/**
	 * Método que retorna colecção com últimos Eventos
	 * @return @List<@Evento>
	 * @throws ClientProtocolException
	 * @throws URISyntaxException
	 * @throws IOException
	 */
	public List<Evento> getEventos() throws ClientProtocolException, URISyntaxException, IOException{
		Reader r = get("/eventos");
		List<Evento> eventos = gson.fromJson(r, new TypeToken<List<Evento>>(){}.getType());
		return eventos;
	}
	
	/**
	 * Método que retorna Evento
	 * @param id ID do evento
	 * @return @Evento
	 * @throws ClientProtocolException
	 * @throws URISyntaxException
	 * @throws IOException
	 */
	public Evento getEvento(int id) throws ClientProtocolException, URISyntaxException, IOException{
		Reader r = get("/eventos/" + id);
		Evento e = gson.fromJson(r, Evento.class);
		return e;
	}
	
	/**
	 * Método que retorna eventos pelo tipo
	 * @param id ID do tipo de evento
	 * @return @List<@Evento>
	 * @throws ClientProtocolException
	 * @throws URISyntaxException
	 * @throws IOException
	 */
	public List<Evento> getEventosByTipo(int id) throws ClientProtocolException, URISyntaxException, IOException{
		Reader r = get("/eventos/tipo/" + id);
		List<Evento> eventos = gson.fromJson(r, new TypeToken<List<Evento>>(){}.getType());
		return eventos;
	}
	
	/**
	 * Método que retorna eventos geo-referenciados perto do utilizador, atraves da latitude e longitude
	 * @param lat latitude
	 * @param lon longitude
	 * @param distance Distancia máxima
	 * @return @List<@Evento> 
	 * @throws ClientProtocolException
	 * @throws URISyntaxException
	 * @throws IOException
	 */
	public List<Evento> getEventosNearBy(float lat, float lon, int distance) throws ClientProtocolException, URISyntaxException, IOException{
		Reader r = get("/eventos/nearby/"+ lat + "/"+ lon +"/" + distance);
		List<Evento> eventos = gson.fromJson(r, new TypeToken<List<Evento>>(){}.getType());
		return eventos;
	}
		

}