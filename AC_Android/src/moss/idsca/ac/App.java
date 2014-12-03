package moss.idsca.ac;

import ws.Evento;

import android.app.Application;


public class App extends Application {

	private Evento evento;

	public Evento getEvento() {
		return evento;
	}

	public void setEvento(Evento evento) {
		this.evento = evento;
	}
}
