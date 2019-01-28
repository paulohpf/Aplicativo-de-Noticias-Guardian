package br.com.paulohpfranco.aplicativonoticias;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<News>> {

    //private static final String GUARGIAN_REQUEST_URL = "https://content.guardianapis.com/search?q=debate&tag=politics/politics&from-date=2014-01-01&api-key=test&show-tags=contributor";
    private static final String GUARGIAN_REQUEST_URL = "https://content.guardianapis.com/${query}?show-tags=contributor&api-key=test";
    private TextView mEmptyStateTextView;

    private static final int NEWSAPP_LOADER_ID = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mEmptyStateTextView = findViewById(R.id.empty_view);
        ListView newsListView = findViewById(R.id.list);

        newsListView.setEmptyView(mEmptyStateTextView);

        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {

            LoaderManager loaderManager = getLoaderManager();

            loaderManager.initLoader(NEWSAPP_LOADER_ID, null, this);
        } else {
            View loadingIndicator = findViewById(R.id.loading_indicator);
            loadingIndicator.setVisibility(View.GONE);
            mEmptyStateTextView.setText(R.string.no_internet_connection);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Infla o menu de configurações
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent settingsIntent = new Intent(this, SettingsActivity.class);
            startActivity(settingsIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public Loader<ArrayList<News>> onCreateLoader(int i, Bundle bundle) {

        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);

        // getString retrieves a String value from the preferences. The second parameter is the default value for this preference.
        String sectionSearch = sharedPrefs.getString(
                getString(R.string.settings_section_key),
                getString(R.string.settings_section_default));

        Log.v(MainActivity.class.getSimpleName(), "URL:"+GUARGIAN_REQUEST_URL.replace("${query}", sectionSearch));

        return new NewsLoader(this, GUARGIAN_REQUEST_URL.replace("${query}", sectionSearch));
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<News>> loader, ArrayList<News> data) {
        View loadingIndicator = findViewById(R.id.loading_indicator);
        loadingIndicator.setVisibility(View.GONE);

        // Seta o texto do estado vazio para mostrar "Nenhum terremoto encontrado."
        mEmptyStateTextView.setText((R.string.no_news));

        if (data != null && !data.isEmpty()) {
            updateUi(data);
        }
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<News>> loader) {

    }

    private void updateUi(ArrayList<News> news) {
        // Find a reference to the {@link ListView} in the layout
        ListView NewsListView = findViewById(R.id.list);

        final NewsListAdapter adapter = new NewsListAdapter(getApplicationContext(), news);

        NewsListView.setAdapter(adapter);

        NewsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                News currentNews = adapter.getItem(position);

                Uri newsUri = Uri.parse(currentNews.getNewsURL());

                Intent websiteIntent = new Intent(Intent.ACTION_VIEW, newsUri);

                startActivity(websiteIntent);
            }
        });
    }
}
