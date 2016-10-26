package com.maninder.skylarktest.setcontents;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.maninder.skylarktest.R;
import com.maninder.skylarktest.SkylarkApplication;
import com.maninder.skylarktest.setcontents.injection.DaggerSetContentsComponent;
import com.maninder.skylarktest.setcontents.injection.SetContentsModule;

import javax.inject.Inject;

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

    @Inject
    SetContentsPresenter mSetContentsPresenter;

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

        //Create the Presenter
        DaggerSetContentsComponent.builder()
                .setContentsModule(new SetContentsModule(setContentsFragment,
                        ((SkylarkApplication) getApplication()).getSkylarkRepositoryComponent().getSkylarkRepository()))
                .build()
                .inject(this);
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
