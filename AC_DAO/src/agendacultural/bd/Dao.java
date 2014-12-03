package agendacultural.bd;

import java.sql.Connection;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import agendacultural.model.Evento;
import agendacultural.model.Local;
import agendacultural.model.TipoEvento;


/**
 * Class DAO (data access object)
 * @author nuno
 *
 */

public class Dao {

	/**
	 * Obtém todos os Tipos de Eventos
	 * 
	 * @return List<{@link}TipoEvento>
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public static List<TipoEvento> getTipoEventos() throws SQLException,
			ClassNotFoundException {
		// Colecção
		List<TipoEvento> tipoEventos = new LinkedList<TipoEvento>();
		// abre ligação
		Connection con = DBUtils.open();
		CallableStatement ct = null;
		// query
		ct = con.prepareCall("{CALL SpTipoEventos}");

		// cria resultset com dados da query
		ResultSet rs = ct.executeQuery();
		// le todos os registos do ResultSet
		while (rs.next()) {
			// cria objectos
			TipoEvento te = new TipoEvento(rs.getInt("id"),
					rs.getString("nome"));
			// adiciona a colecção
			tipoEventos.add(te);
		}
		// fecha ligação
		DBUtils.close(ct, con);
		// devolve resultado
		return tipoEventos;
	}

	/**
	 * Obtém todos os eventos
	 * 
	 * @param many
	 *            Número de registos a obter
	 * @return List<{@link}Evento>
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public static List<Evento> getEventos(int many) throws SQLException,
			ClassNotFoundException {
		// Colecção
		List<Evento> eventos = new LinkedList<Evento>();
		// abre ligação
		Connection con = DBUtils.open();
		CallableStatement ct = null;
		// query
		ct = con.prepareCall("{CALL SpEventos(?)}");
		// parametros
		ct.setInt(1, many);
		// cria resultset com dados da query
		ResultSet rs = ct.executeQuery();
		// le todos os registos do ResultSet
		while (rs.next()) {
			// cria objectos
			Evento e = new Evento(rs.getInt("eventos.id"), new Local(0,
					rs.getString("locais.nome"), rs.getString("locais.morada"),
					rs.getFloat("locais.latitude"),
					rs.getFloat("locais.longitude"), 0), new TipoEvento(0,
					rs.getString("tipo_eventos.nome")),
					rs.getString("eventos.nome"), 
					rs.getDate("datahora"), 
					rs.getString("imagem_url"));

			// adiciona a colecção
			eventos.add(e);
		}
		rs.close();
		// fecha ligação
		DBUtils.close(ct, con);
		// devolve resultado
		return eventos;
	}

	/**
	 * Obtém evento pelo seu ID
	 * 
	 * @param id
	 *            ID do evento
	 * @return {@linkEvento}
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public static Evento getEvento(int id) throws SQLException,
			ClassNotFoundException {

		// abre ligação
		Connection con = DBUtils.open();
		CallableStatement ct = null;
		// query
		ct = con.prepareCall("{CALL SpEvento(?)}");
		// parametros
		ct.setInt(1, id);
		// cria resultset com dados da query
		ResultSet rs = ct.executeQuery();
		// le o registo do ResultSet
		rs.next();
		// cria objecto
		Evento evento = new Evento(rs.getInt("eventos.id"), new Local(0,
				rs.getString("locais.nome"), rs.getString("locais.morada"),
				rs.getFloat("locais.latitude"),
				rs.getFloat("locais.longitude"), 0), new TipoEvento(0,
				rs.getString("tipo_eventos.nome")),
				rs.getString("eventos.nome"), 
				rs.getDate("datahora"), 
				rs.getString("imagem_url"));

		// fecha ligação
		DBUtils.close(ct, con);
		// devolve resultado
		return evento;
	}

	/**
	 * Obtém todos os eventos por Tipo Evento
	 * 
	 * @param id
	 *            ID Tipo Evento
	 * @param many
	 *            Número de registos a obter
	 * @return List<{@link}Evento>
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public static List<Evento> getEventosByTipo(int id, int many)
			throws SQLException, ClassNotFoundException {
		// Colecção
		List<Evento> eventos = new LinkedList<Evento>();
		// abre ligação
		Connection con = DBUtils.open();
		CallableStatement ct = null;
		// query
		ct = con.prepareCall("{CALL SpEventosTipo(?,?)}");
		// parametros
		ct.setInt(1, id);
		ct.setInt(2, many);
		// cria resultset com dados da query
		ResultSet rs = ct.executeQuery();
		// le todos os registos do ResultSet
		while (rs.next()) {
			// cria objectos
			Evento evento = new Evento(rs.getInt("eventos.id"), new Local(0,
					rs.getString("locais.nome"), rs.getString("locais.morada"),
					rs.getFloat("locais.latitude"),
					rs.getFloat("locais.longitude"), 0), new TipoEvento(0,
					rs.getString("tipo_eventos.nome")),
					rs.getString("eventos.nome"), 
					rs.getDate("eventos.datahora"), 
					rs.getString("eventos.imagem_url"));
			
			// adiciona a colecção
			eventos.add(evento);
		}
		// fecha ligação
		DBUtils.close(ct, con);
		// devolve resultado
		return eventos;
	}

	/**
	 * Obtém eventos por coordenadas geográficas
	 * 
	 * @param latitude
	 *            Latitude
	 * @param longitude
	 *            Longitude
	 * @param distance
	 *            Distância máxima em km
	 * @return List<{@link}Evento>
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public static List<Evento> getEventosNearBy(float latitude,
			float longitude, int distance) throws SQLException,
			ClassNotFoundException {

		// Colecção
		List<Evento> eventos = new LinkedList<Evento>();
		// abre ligação
		Connection con = DBUtils.open();
		CallableStatement ct = null;
		// query
		ct = con.prepareCall("{CALL SpEventosNearBy(?,?,?)}");
		// parametros
		ct.setFloat(1, latitude);
		ct.setFloat(2, longitude);
		ct.setInt(3, distance);
		// cria resultset com dados da query
		ResultSet rs = ct.executeQuery();
		// le todos os registos do ResultSet
		while (rs.next()) {
			// cria objectos
			Evento evento = new Evento(rs.getInt("eventos.id"), new Local(0,
					rs.getString("locais.nome"), rs.getString("locais.morada"),
					rs.getFloat("locais.latitude"),
					rs.getFloat("locais.longitude"), rs.getFloat("distancia")),
					new TipoEvento(0, rs.getString("tipo_eventos.nome")),
					rs.getString("eventos.nome"), 
					rs.getDate("eventos.datahora"), 
					rs.getString("eventos.imagem_url"));

			// adiciona a colecção
			eventos.add(evento);
		}
		// fecha ligação
		DBUtils.close(ct, con);
		// devolve resultado
		return eventos;
	}

}
