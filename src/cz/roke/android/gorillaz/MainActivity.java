package cz.roke.android.gorillaz;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {

	private static final String TAG = "MainActivity";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		Button butStart = (Button) findViewById(R.id.butStart);
		butStart.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(v.getContext(), GorillazActivity.class);
				startActivityForResult(intent, 0);
			}
		});

		Button butHelp = (Button) findViewById(R.id.butHelp);
		butHelp.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(v.getContext(), InfoActivity.class);
				intent.putExtra("type", 1);

				startActivityForResult(intent, 0);
			}
		});

		Button butAbout = (Button) findViewById(R.id.butAbout);
		butAbout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(v.getContext(), InfoActivity.class);
				intent.putExtra("type", 2);

				startActivityForResult(intent, 0);
			}
		});

		Button butExit = (Button) findViewById(R.id.butExit);
		butExit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}

}
