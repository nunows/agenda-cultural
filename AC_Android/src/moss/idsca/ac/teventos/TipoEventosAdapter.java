package moss.idsca.ac.teventos;

import java.util.List;

import moss.idsca.ac.R;

import ws.TipoEvento;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class TipoEventosAdapter extends ArrayAdapter<TipoEvento> {

	Context context;
	int textViewResourceId;
	List<TipoEvento> objects;

	public TipoEventosAdapter(Context context, int textViewResourceId,
			List<TipoEvento> objects) {
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
			holder.txt = (TextView) row.findViewById(R.id.txt01);

			row.setTag(holder);
		} else {
			holder = (DataHolder) row.getTag();
		}

		TipoEvento dados = objects.get(position);
		holder.txt.setText(dados.getNome());
		row.setTag(dados.getId());
		return row;
	}

	private static class DataHolder {
		TextView txt;
	}

}
