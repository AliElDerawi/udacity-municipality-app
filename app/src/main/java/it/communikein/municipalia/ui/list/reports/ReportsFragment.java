package it.communikein.municipalia.ui.list.reports;


import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;
import it.communikein.municipalia.R;
import it.communikein.municipalia.data.model.Report;
import it.communikein.municipalia.databinding.SimpleListBinding;
import it.communikein.municipalia.ui.MainActivity;
import it.communikein.municipalia.ui.detail.ReportDetailActivity;
import it.communikein.municipalia.ui.list.news.NewsFragment;
import it.communikein.municipalia.viewmodel.ReportsViewModel;
import it.communikein.municipalia.viewmodel.factory.ReportsViewModelFactory;

/**
 * A simple {@link Fragment} subclass.
 */
public class ReportsFragment extends Fragment implements ReportsAdapter.ReportClickCallback {

    private static final String LOG_TAG = NewsFragment.class.getSimpleName();

    /*  */
    private SimpleListBinding mBinding;

    /* */
    @Inject
    ReportsViewModelFactory viewModelFactory;

    /* */
    private ReportsViewModel mViewModel;


    @Override
    public void onAttach(Context context) {
        AndroidSupportInjection.inject(this);
        super.onAttach(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        /* Inflate the layout for this fragment */
        mBinding = DataBindingUtil.inflate(inflater, R.layout.simple_list, container, false);

        /*
         * A LinearLayoutManager is responsible for measuring and positioning item views within a
         * RecyclerView into a linear list. This means that it can produce either a horizontal or
         * vertical list.
         *
         * The third parameter (reverseLayout) should be true if you want to reverse your
         * layout. Generally, this is only true with horizontal lists that need to support a
         * right-to-left layout.
         */
        LinearLayoutManager layoutManager = new LinearLayoutManager(
                getActivity(),
                LinearLayoutManager.VERTICAL,
                false);
        mBinding.listRecyclerview.setLayoutManager(layoutManager);

        /*
         * Use this setting to improve performance if you know that changes in content do not
         * change the child layout size in the RecyclerView
         */
        mBinding.listRecyclerview.setHasFixedSize(true);

        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setTitle();
        hideTabs();

        /* Create a new ReportsAdapter. It will be responsible for displaying the list's items */
        final ReportsAdapter mAdapter = new ReportsAdapter(null);

        /* Setting the adapter attaches it to the RecyclerView in our layout. */
        mBinding.listRecyclerview.setAdapter(mAdapter);

        initViewModel(mAdapter);
        initFab();
    }

    private void hideTabs() {
        if (getActivity() != null) {
            ((MainActivity) getActivity()).hideTabsLayout();
        }
    }

    private void initViewModel(ReportsAdapter adapter) {
        mViewModel = ViewModelProviders
                .of(this, viewModelFactory)
                .get(ReportsViewModel.class);

        mViewModel.getObservableReports().observe(this, list -> {
            if (list != null) {
                Log.d(LOG_TAG, "Updating the reports list. " + list.size() + " elements.");
                adapter.setList((ArrayList<Report>) list);
            }
        });
    }

    private void initFab() {
        mBinding.fab.setOnClickListener(v -> {
            // TODO: Call intent to start new activity
        });
    }

    /**
     * Change the Activity's ActionBar title.
     */
    private void setTitle() {
        if (getActivity() != null) {
            /* Get a reference to the MainActivity ActionBar */
            ActionBar actionBar = ((MainActivity) getActivity()).getSupportActionBar();
            /* If there is an ActionBar, set it's title */
            if (actionBar != null)
                actionBar.setTitle(R.string.title_reports);
        }
    }

    @Override
    public void onListReportClick(Report report) {
        Intent intent = new Intent(getActivity(), ReportDetailActivity.class);
        startActivity(intent);
    }
}
