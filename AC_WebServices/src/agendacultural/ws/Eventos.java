package agendacultural.ws;

import java.sql.SQLException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;

import agendacultural.bd.Dao;

import com.google.gson.Gson;

/**
 * 
 * Webservice Eventos
 *
 */
@Path("/eventos")
public class Eventos {

	private static int MANY = 10;
	private Gson gson = new Gson();

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String getEventos() {

		String dados = null;

		try {
			dados = gson.toJson(Dao.getEventos(MANY));
		} catch (SQLException e) {
			new WebApplicationException();
		} catch (ClassNotFoundException e) {
			new WebApplicationException();
		}

		return dados;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{id}")
	public String getEvento(@PathParam("id") int id) {
		String dados = null;
		try {
			dados = gson.toJson(Dao.getEvento(id));
		} catch (SQLException e) {

			new WebApplicationException();
		} catch (ClassNotFoundException e) {

			new WebApplicationException();
		}

		return dados;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/tipo/{id}")
	public String getEventosByTipo(@PathParam("id") int id) {
		String dados = null;
		try {
			dados = gson.toJson(Dao.getEventosByTipo(id, MANY));
		} catch (SQLException e) {
			new WebApplicationException();
		} catch (ClassNotFoundException e) {
			new WebApplicationException();
		}
		return dados;
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/nearby/{lat}/{lon}/{distance}")
	public String getEventosNearBy(@PathParam("lat") float lat, @PathParam("lon") float lon, @PathParam("distance") int distance) {
		String dados = null;
		try {
			dados = gson.toJson(Dao.getEventosNearBy(lat, lon, distance));
		} catch (SQLException e) {
			new WebApplicationException();
		} catch (ClassNotFoundException e) {
			new WebApplicationException();
		}
		return dados;
	}

}
