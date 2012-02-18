package cz.roke.android.gorillaz;

import cz.roke.android.gorillaz.network.Communication;
import cz.roke.android.gorillaz.network.ServerSide;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class GorillazActivity extends Activity {

	private static final String TAG = GorillazActivity.class.getName();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// setContentView(R.layout.main);

		// debugStartGame();
		debugStartServer();
	}

	private ServerSide ss;

	private void debugStartServer() {
		if (ss == null) {
			ss = new ServerSide();
			ss.exec();
		} else {
			Log.i(TAG, "Server is already running!");
		}
	}

	private void debugStartGame() {

		// TODO Rovnou do hry
		setContentView(R.layout.game);

		// TODO Ukazkove tlacitko
		Button but = (Button) findViewById(R.id.moje);
		but.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Toast.makeText(v.getContext(), "Kliknuto", Toast.LENGTH_SHORT).show();
			}

		});
	}
}