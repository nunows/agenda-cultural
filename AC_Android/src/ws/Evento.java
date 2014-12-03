package ws;

/**
 * 
 * Class que representa um Evento, usada pela biblioteca Gson
 *
 */
public class Evento {

	private int id;
	private Local local;
	private TipoEvento tipoEvento;
	private String nome;
	private String datahora;
	private String imagemUrl;
	
	public int getId() {
		return id;
	}
	public Local getLocal() {
		return local;
	}
	
	public TipoEvento getTipoEvento() {
		return tipoEvento;
	}
	
	public String getNome() {
		return nome;
	}
	
	public String getDatahora() {
		return datahora;
	}
	
	public String getImagemUrl() {
		return imagemUrl;
	}

	

	
	
}
