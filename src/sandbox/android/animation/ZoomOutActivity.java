package sandbox.android.animation;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

public class ZoomOutActivity extends Activity implements AnimationListener {

	ImageView imgPoster;
	Button btnStart;

	// Animation
	Animation animZoomOut;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_zoom_out);

		imgPoster = (ImageView) findViewById(R.id.imgLogo);
		btnStart = (Button) findViewById(R.id.btnStart);

		// load the animation
		animZoomOut = AnimationUtils.loadAnimation(getApplicationContext(),
				R.anim.zoom_out);
		
		// set animation listener
		animZoomOut.setAnimationListener(this);

		// button click event
		btnStart.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// start animation
				imgPoster.startAnimation(animZoomOut);
			}
		});

	}

	@Override
	public void onAnimationEnd(Animation animation) {
		// Take any action after completing the animation

		// check for zoom in animation
		if (animation == animZoomOut) {	
		}

	}

	@Override
	public void onAnimationRepeat(Animation animation) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onAnimationStart(Animation animation) {
		// TODO Auto-generated method stub

	}

}