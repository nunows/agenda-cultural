package moss.idsca.ac.eventos;

import java.util.List;

import moss.idsca.ac.R;

import ws.Evento;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class EventoAdapter extends ArrayAdapter<Evento> {

	Context context;
	int textViewResourceId;
	List<Evento> objects;
	
	public EventoAdapter(Context context, int textViewResourceId,
			List<Evento> objects) {
		super(context, textViewResourceId, objects);
		this.context = context;
		this.textViewResourceId = textViewResourceId;
		this.objects = objects;		
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
		DataHolder holder = null;

		if (row == null) {
			LayoutInflater inflater = ((Activity) context).getLayoutInflater();
			row = inflater.inflate(textViewResourceId, parent, false);

			holder = new DataHolder();
			holder.txtNome = (TextView) row.findViewById(R.id.txtNome);
			holder.txtData =  (TextView) row.findViewById(R.id.txtData);
			holder.txtDistancia =  (TextView) row.findViewById(R.id.txtDistancia);

			row.setTag(holder);
		} else {
			holder = (DataHolder) row.getTag();
		}

		Evento dados = objects.get(position);
		holder.txtNome.setText(dados.getNome());
		holder.txtData.setText(dados.getDatahora());
		
		if(dados.getLocal().getDistancia() > 0) {
			holder.txtDistancia.setText(holder.txtDistancia.getText() + " " + String.valueOf(dados.getLocal().getDistancia()));
		} else {
			holder.txtDistancia.setText("");
		}
			
		return row;
	}

	private static class DataHolder {
		TextView txtNome;
		TextView txtData;
		TextView txtDistancia;
	}
	
}
