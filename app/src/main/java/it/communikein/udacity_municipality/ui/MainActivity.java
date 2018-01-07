package it.communikein.udacity_municipality.ui;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;
import it.communikein.udacity_municipality.R;
import it.communikein.udacity_municipality.databinding.ActivityMainBinding;
import it.communikein.udacity_municipality.ui.list.news.NewsEventsFragment;
import it.communikein.udacity_municipality.ui.list.pois.PoisFragment;

public class MainActivity extends AppCompatActivity implements HasSupportFragmentInjector {

    private static final String TAG = MainActivity.class.getSimpleName();

    @Inject
    DispatchingAndroidInjector<Fragment> dispatchingAndroidInjector;

    private ActivityMainBinding mBinding;


    private final List<Fragment> fragments = new ArrayList<>();

    public static String FRAGMENT_SELECTED_TAG;

    private static final int INDEX_FRAGMENT_NEWS = 0;
    private static final int INDEX_FRAGMENT_POIS = 1;

    public static final String TAG_FRAGMENT_NEWS = "tab-news";
    public static final String TAG_FRAGMENT_POIS = "tab-pois";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        initUI();
    }

    private void initUI() {
        buildFragmentsList();

        mBinding.navigation.setOnNavigationItemSelectedListener(item ->
                switchFragment(item.getItemId()));
        int navId = getNavIdFromFragmentTag(FRAGMENT_SELECTED_TAG);
        mBinding.navigation.setSelectedItemId(navId);
    }

    private void buildFragmentsList() {
        fragments.add(INDEX_FRAGMENT_NEWS, new NewsEventsFragment());
        fragments.add(INDEX_FRAGMENT_POIS, new PoisFragment());
    }

    private boolean switchFragment(int tab_id) {
        int index = INDEX_FRAGMENT_NEWS;

        switch (tab_id) {
            case R.id.navigation_home:
                break;
            case R.id.navigation_news_and_events:
                index = INDEX_FRAGMENT_NEWS;
                FRAGMENT_SELECTED_TAG = TAG_FRAGMENT_NEWS;
                break;
            case R.id.navigation_pois:
                index = INDEX_FRAGMENT_POIS;
                FRAGMENT_SELECTED_TAG = TAG_FRAGMENT_POIS;
                break;
            default:
                return false;
        }

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.tab_container, fragments.get(index), FRAGMENT_SELECTED_TAG)
                .commit();
        return true;
    }

    private int getNavIdFromFragmentTag(String tag) {
        int id = R.id.navigation_news_and_events;

        if (tag != null) switch(tag) {
            case TAG_FRAGMENT_NEWS:
                id = R.id.navigation_news_and_events;
                break;

            case TAG_FRAGMENT_POIS:
                id = R.id.navigation_pois;
                break;

            default:
                id = R.id.navigation_news_and_events;
                break;
        }

        return id;
    }


    @Override
    public DispatchingAndroidInjector<Fragment> supportFragmentInjector() {
        return dispatchingAndroidInjector;
    }

}
