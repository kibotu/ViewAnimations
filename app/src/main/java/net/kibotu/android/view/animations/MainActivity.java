package net.kibotu.android.view.animations;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import com.common.android.utils.ContextHelper;
import com.common.android.utils.extensions.FragmentExtensions;
import net.kibotu.android.view.animations.ui.*;

import static com.common.android.utils.extensions.FragmentExtensions.replaceByFading;
import static com.common.android.utils.extensions.FragmentExtensions.replaceToBackStackBySlidingHorizontally;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ContextHelper.setContext(this);

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        replaceByFading(new ComplexAnimationFragment());
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            FragmentExtensions.popBackStack();
        } else
            super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * @source: <a href="http://www.androidhive.info/2013/06/android-working-with-xml-animations">android-working-with-xml-animations</a>
     */
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        BaseFragment fragment = null;
        switch (id) {
            case R.id.blink:
                fragment = new BlinkAnimationFragment();
                break;
            case R.id.bounce:
                fragment = new BounceAnimationFragment();
                break;
            case R.id.cross_fade:
                fragment = new CrossFadeAnimationFragment();
                break;
            case R.id.custom:
                fragment = new CustomAnimationFragment();
                break;
            case R.id.complex:
                fragment = new ComplexAnimationFragment();
                break;
            case R.id.fade_in:
                fragment = new FadeInAnimationFragment();
                break;
            case R.id.fade_out:
                fragment = new FadeOutAnimationFragment();
                break;
            case R.id.move:
                fragment = new TranslateAnimationFragment();
                break;
            case R.id.rotate:
                fragment = new RotateAnimationFragment();
                break;
            case R.id.sequential:
                fragment = new SequentialAnimationFragment();
                break;
            case R.id.slide_down:
                fragment = new SlideDownAnimationFragment();
                break;
            case R.id.slide_up:
                fragment = new SlideUpAnimationFragment();
                break;
            case R.id.parallel:
                fragment = new ParallelAnimationFragment();
                break;
            case R.id.zoom_in:
                fragment = new ZoomInAnimationFragment();
                break;
            case R.id.zoom_out:
                fragment = new ZoomOutAnimationFragment();
                break;
        }

        if (fragment != null) {
            replaceToBackStackBySlidingHorizontally(fragment);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
