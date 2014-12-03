package agendacultural.ws;

import java.sql.SQLException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;

import agendacultural.bd.Dao;

import com.google.gson.Gson;

/**
 * Webservice Tipo Eventos
 * @author nuno
 *
 */
@Path("/tipoeventos")
public class TipoEventos {

	private Gson gson = new Gson();

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String getAll() {

		String dados = null;

		try {
			dados = gson.toJson(Dao.getTipoEventos());
		} catch (SQLException e) {
			new WebApplicationException();
		} catch (ClassNotFoundException e) {
			new WebApplicationException();
		}
		return dados;

	}

}
