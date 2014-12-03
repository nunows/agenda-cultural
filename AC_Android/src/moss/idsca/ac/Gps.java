package moss.idsca.ac;


import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

/**
 * Class que permite obter dados do GPS do telemóvel 
 * 
 * Localização pode ser obtida atraves de GPS ou com dados da rede (GSM, Wifi)
 *
 */
public class Gps {

	private LocationManager lm;
	private GpsListener gps;
	private GpsListener network;
	private IGps igps;

	public Gps(LocationManager lm, IGps igps) {
		this.lm = lm;
		this.igps = igps;

		gps = new GpsListener();
		network = new GpsListener();
	}

	/**
	 * Método que regista o GPS para obter dados
	 */
	public void register() {
		lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10000, 5, gps);
		lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, network);
	}

	/**
	 * Método que remove updates do GPS
	 */
	public void remove() {
		lm.removeUpdates(gps);
		lm.removeUpdates(network);
	}

	/**
	 * Método que obtem localização actual 
	 * @return @Location
	 */
	public Location getLocation() {
		if (gps.getLocation() != null) {
			return gps.getLocation();
		} else if (network.getLocation() != null) {
			return network.getLocation();
		} else {
			return null;
		}
	}

	/**
	 * Inner class que implenta o interface @LocationListener que permite obter estados do GPS
	 *
	 */
	private class GpsListener implements LocationListener {
		private Location location;

		@Override
		public void onLocationChanged(Location location) {
			this.location = location;
			
			igps.actualizar();
		}

		@Override
		public void onProviderDisabled(String provider) {
			igps.activarGps();			
		}

		@Override
		public void onProviderEnabled(String provider) {
		}

		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
		}

		public Location getLocation() {
			return location;
		}
	}
	
}
