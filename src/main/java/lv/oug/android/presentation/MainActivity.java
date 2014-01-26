package lv.oug.android.presentation;

import android.support.v4.app.Fragment;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.*;
import android.widget.*;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.squareup.otto.Bus;
import lv.oug.android.R;
import lv.oug.android.infrastructure.common.StringService;
import lv.oug.android.presentation.about.AboutFragment;
import lv.oug.android.presentation.home.HomeFragment;
import lv.oug.android.presentation.navigation.NavigationDrawerAdapter;
import lv.oug.android.presentation.navigation.NavigationItem;
import lv.oug.android.presentation.news.NewsFragment;

import javax.inject.Inject;

public class MainActivity extends SherlockFragmentActivity {

    @Inject
    Bus bus;

    @Inject
    StringService stringService;

    @Inject
    NavigationDrawerAdapter drawerAdapter;

    @InjectView(R.id.drawer_layout)
    DrawerLayout drawerLayout;

    @InjectView(R.id.left_drawer)
    ListView drawerList;

    CharSequence title;

    CharSequence drawerTitle;

    ActionBarDrawerToggle drawerToggle;

    @Override
    protected void onCreate(Bundle state) {
        super.onCreate(state);

        // Android constructs Activity instances so we must find the ObjectGraph instance and inject this.
        BaseApplication.inject(this);
        setContentView(R.layout.navigation_drawer);
        ButterKnife.inject(this);

        initDrawerLayout(state);
    }

    @Override
    protected void onResume() {
        super.onResume();
        bus.register(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        bus.unregister(this);
    }

    @Override
    protected void onPostCreate(Bundle state) {
        super.onPostCreate(state);
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(com.actionbarsherlock.view.MenuItem item) {
        if (drawerLayout.isDrawerOpen(drawerList)) {
            drawerLayout.closeDrawer(drawerList);
        } else {
            drawerLayout.openDrawer(drawerList);
        }
        return super.onOptionsItemSelected(item);
    }

    private void initDrawerLayout(Bundle state) {
        drawerList.setAdapter(drawerAdapter);
        drawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        drawerList.setOnItemClickListener(new ListView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectItem(position);
            }
        });

        title = drawerTitle = getTitle();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        // ActionBarDrawerToggle ties together the the proper interactions
        // between the sliding drawer and the action bar app icon
        drawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                drawerLayout,         /* DrawerLayout object */
                R.drawable.ic_drawer,  /* nav drawer image to replace 'Up' caret */
                R.string.drawer_open,  /* "open drawer" description for accessibility */
                R.string.drawer_close  /* "close drawer" description for accessibility */
        ) {
            public void onDrawerClosed(View view) {
                getSupportActionBar().setTitle(title);
                invalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView) {
                getSupportActionBar().setTitle(drawerTitle);
                invalidateOptionsMenu();
            }
        };
        drawerLayout.setDrawerListener(drawerToggle);

        if (state == null) {
            String tag = stringService.loadString(R.string.nav_home);
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.content_frame, new HomeFragment(), tag)
                    .commit();
        }
    }

    @Override
    public void setTitle(CharSequence title) {
        this.title = title;
        getSupportActionBar().setTitle(title);
    }

    private void selectItem(int position) {
        NavigationItem navigationItem = drawerAdapter.get(position);
        String selectedItem = stringService.loadString(navigationItem.getTitleId());

        Fragment fragment = getSupportFragmentManager().findFragmentByTag(selectedItem);
        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.content_frame);

        navigationAction(navigationItem, selectedItem, fragment, currentFragment);

        drawerList.setItemChecked(position, true);
        setTitle(selectedItem);
        drawerLayout.closeDrawer(drawerList);
    }

    private void navigationAction(NavigationItem navigationItem, String selectedItem, Fragment fragment, Fragment currentFragment) {
        if(! (currentFragment.equals(fragment))){
            if(navigationItem == NavigationItem.HOME) {
                changeFragment(new HomeFragment(), selectedItem);
            } else if(navigationItem == NavigationItem.NEWS) {
                changeFragment(new NewsFragment(), selectedItem);
            } else if(navigationItem == NavigationItem.ABOUT)  {
                changeFragment(new AboutFragment(), selectedItem);
            }
        }
    }

    public void changeFragment(Fragment fragment) {
        changeFragment(fragment, null);
    }

    public void changeFragment(Fragment fragment, String tag) {
        getSupportFragmentManager()
            .beginTransaction()
            .replace(R.id.content_frame, fragment, tag)
            .addToBackStack(null)
            .commit();
    }
}
