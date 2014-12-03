package agendacultural.model;

import java.util.Date;

/**
 * Class que representa um evento
 *
 */
public class Evento {

	private int id;
	private Local local;
	private TipoEvento tipoEvento;
	private String nome;
	private Date datahora;
	private String imagemUrl;
	
	public Evento(){
	}
	
	public Evento(int id, Local local, TipoEvento tipoEvento, String nome,
			Date datahora, String imagemUrl) {
		super();
		this.id = id;
		this.local = local;
		this.tipoEvento = tipoEvento;
		this.nome = nome;
		this.datahora = datahora;
		this.imagemUrl = imagemUrl;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Local getLocal() {
		return local;
	}

	public void setLocal(Local local) {
		this.local = local;
	}

	public TipoEvento getTipoEvento() {
		return tipoEvento;
	}

	public void setTipoEvento(TipoEvento tipoEvento) {
		this.tipoEvento = tipoEvento;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Date getDatahora() {
		return datahora;
	}

	public void setDatahora(Date datahora) {
		this.datahora = datahora;
	}

	public String getImagemUrl() {
		return imagemUrl;
	}

	public void setImagemUrl(String imagemUrl) {
		this.imagemUrl = imagemUrl;
	}

	
	
}
