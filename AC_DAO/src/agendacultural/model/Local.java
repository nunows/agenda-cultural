package agendacultural.model;

/**
 * Class que representa um Local
 *
 */
public class Local {

	private int id;
	private String nome;
	private String morada;
	private float latitude;
	private float longitude;
	private float distancia;
	
	public Local(){
	}

	public Local(int id, String nome, String morada, float latitude,
			float longitude, float distancia) {
		this.id = id;
		this.nome = nome;
		this.morada = morada;
		this.latitude = latitude;
		this.longitude = longitude;
		this.distancia = distancia;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getMorada() {
		return morada;
	}

	public void setMorada(String morada) {
		this.morada = morada;
	}

	public float getLatitude() {
		return latitude;
	}

	public void setLatitude(float latitude) {
		this.latitude = latitude;
	}

	public float getLongitude() {
		return longitude;
	}

	public void setLongitude(float longitude) {
		this.longitude = longitude;
	}

	public float getDistancia() {
		return distancia;
	}

	public void setDistancia(float distancia) {
		this.distancia = distancia;
	}
	
	
	
}
