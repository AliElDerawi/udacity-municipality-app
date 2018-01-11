package it.communikein.udacity_municipality.di.module;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import it.communikein.udacity_municipality.ui.HomeFragment;
import it.communikein.udacity_municipality.ui.list.pois.PoisFragment;
import it.communikein.udacity_municipality.ui.list.news.NewsFragment;
import it.communikein.udacity_municipality.ui.list.reports.ReportsFragment;

@Module
public abstract class FragmentsBuilderModule {

    @ContributesAndroidInjector
    abstract HomeFragment contributeHomeFragment();

    @ContributesAndroidInjector
    abstract NewsFragment contributeNewsFragment();

    @ContributesAndroidInjector
    abstract PoisFragment contributePoisFragment();

    @ContributesAndroidInjector
    abstract ReportsFragment contributeReportsFragment();

}
