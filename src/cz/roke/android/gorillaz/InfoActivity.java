package cz.roke.android.gorillaz;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;

public class InfoActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.info);

		RelativeLayout l = (RelativeLayout) findViewById(R.id.layout);
		switch (getIntent().getExtras().getInt("type")) {
			case 1:
				l.setBackgroundResource(R.drawable.menu_help);
				break;

			case 2:
				l.setBackgroundResource(R.drawable.menu_about_screen);
				break;
		}

		Button butBack = (Button) findViewById(R.id.butBack);
		butBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}

}
