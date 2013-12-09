package sandbox.android.animation;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

public class CrossfadeActivity extends Activity implements AnimationListener {

	TextView txtMessage1, txtMessage2;
	Button btnStart;

	// Animation
	Animation animFadeIn, animFadeOut;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_crossfade);

		txtMessage1 = (TextView) findViewById(R.id.txtMessage1);
		txtMessage2 = (TextView) findViewById(R.id.txtMessage2);
		btnStart = (Button) findViewById(R.id.btnStart);

		// load animations
		animFadeIn = AnimationUtils.loadAnimation(getApplicationContext(),
				R.anim.fade_in);
		animFadeOut = AnimationUtils.loadAnimation(getApplicationContext(),
				R.anim.fade_out);

		// set animation listeners
		animFadeIn.setAnimationListener(this);
		animFadeOut.setAnimationListener(this);

		// button click event
		btnStart.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// make fade in element visible
				txtMessage2.setVisibility(View.VISIBLE);
				// start fade in animation
				txtMessage2.startAnimation(animFadeIn);
				
				// start fade out animation
				txtMessage1.startAnimation(animFadeOut);
			}
		});

	}

	@Override
	public void onAnimationEnd(Animation animation) {
		// Take any action after completing the animation

		// if animation is fade out hide them after completing animation
		if (animation == animFadeOut) {
			
			// hide faded out element
			txtMessage1.setVisibility(View.GONE);
		}
		
		if(animation == animFadeIn){
			// do something after fade in completed
			
			// set visibility of fade in element
			txtMessage2.setVisibility(View.VISIBLE);
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
