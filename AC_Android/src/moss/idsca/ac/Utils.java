package moss.idsca.ac;

import android.content.Context;
import android.widget.Toast;

/**
 * Class com método utilitarios.
 * @author nuno
 *
 */
public class Utils {

	
	
	/**
	 * Método que permite criar notificações do tipo Toast
	 * @param context Contexto actual da notificação
	 * @param texto Texto da notificação
	 */
	public static void createToast(Context context, String texto) {			
		Toast toast = Toast.makeText(context, texto, Toast.LENGTH_SHORT);
		toast.show();	
	}
	
}
