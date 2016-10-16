package com.maninder.skylarktest.setcontents;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.maninder.skylarktest.Injection;
import com.maninder.skylarktest.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * For this Application I used MVP(Model View Presenter) + Clean Architecture, with this approach we have an
 * maintainable and testable Application.
 * <p>
 * This type of approach make the code, independent from framework, independent from UI and independent from database.
 * <p>
 * This class hold {@link SetContentsPresenter} that is the Presenter for this Activity
 */
public class MainActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbarLayout;


    private SetContentsPresenter mSetContentsPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        collapsingToolbarLayout.setTitleEnabled(true);

        SetContentsFragment setContentsFragment = (SetContentsFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);
        if (setContentsFragment == null) {
            setContentsFragment = SetContentsFragment.getINSTANCE();
            getSupportFragmentManager().beginTransaction().add(R.id.contentFrame, setContentsFragment).commit();
        }

        //Create my Presenter that hold Set content Use case and Application handler
        mSetContentsPresenter = new SetContentsPresenter(
                setContentsFragment,
                Injection.provideUseCaseHandler(),
                Injection.provideGetSetContents(getApplicationContext()),
                Injection.provideImageUrlRequest(getApplicationContext()));

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
