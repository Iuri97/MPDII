// Iuri Insali S1504620

package com.example.mobileplatdevcwii;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;


import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;



public class RSSParser extends AppCompatActivity {

    //RSS feed from Traffic Scotland
    private String incidentsUrl = "https://learn-eu-central-1-prod-fleet01-xythos.s3-eu-central-1.amazonaws.com/5c1a4465d06f0/7178906?response-content-disposition=inline%3B%20filename%2A%3DUTF-8%27%27currentIncidents.txt&response-content-type=text%2Fplain&X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Date=20200421T112221Z&X-Amz-SignedHeaders=host&X-Amz-Expires=21600&X-Amz-Credential=AKIAZH6WM4PLYI3L4QWN%2F20200421%2Feu-central-1%2Fs3%2Faws4_request&X-Amz-Signature=fbe315e7bd5569c404edb8b825ebf2797f6c3e89b10ac075849c5b2b04535cbd";
    private String roadworksUrl = "https://trafficscotland.org/rss/feeds/roadworks.aspx";
    private String plannedRoadworksUrl = "https://trafficscotland.org/rss/feeds/plannedroadworks.aspx";


    private TextView urlInput;
    private String result = "";
    private ListView listView;

    DatePickerDialog.OnDateSetListener dateSetListener;


    List<Roadwork> RoadworkList;
    ListViewAdapter listViewAdapter;



    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.rssparserlayout);


        RoadworkList = new ArrayList<>();
        listView = findViewById(R.id.list);


        if (MainActivity.getIsCurrentIncidents() == true)
        {
            getSupportActionBar().setTitle("Current Incidents II S1504620");
            startProgress(incidentsUrl);
        }
        else if (MainActivity.getIsRoadworks() == true)
        {
            getSupportActionBar().setTitle("Current Roadworks II S1504620");
            startProgress(roadworksUrl);
        }
        else if (MainActivity.getIsPlannedRoadworks() == true)
        {
            getSupportActionBar().setTitle("Planned Roadworks II S1504620");
            startProgress(plannedRoadworksUrl);
        }


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

                Roadwork item = (Roadwork)adapterView.getItemAtPosition(position);

                Intent intent = new Intent(getApplicationContext(), MapsMarkerActivity.class);
                intent.putExtra("Roadwork", item);
                System.out.println(item.getLat());


                startActivity(intent);
            }
        });

    }

    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.options_menu, menu);

        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.searchIcon));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                listViewAdapter.getFilter().filter(newText);
                return false;
            }
        });
        return true;
    }


    public void startProgress(String theUrl)
    {

        Task newTask = new Task(theUrl);
        newTask.execute();
    }
    class Task extends AsyncTask <Void, Void, List<Roadwork>>
    {
        private String url;

        public Task(String aurl)
        {
            url = aurl;
        }


        @Override
        protected List<Roadwork> doInBackground(Void... aVoid)
        {
            URL aurl;
            URLConnection urlConnection;
            BufferedReader bufferedReader = null;
            String inputLine = "";
            Roadwork RoadworkItem = null;

            Log.e("MyTag", "in run");

            try
            {
                Log.e("MyTag", "in try");
                aurl = new URL(url);
                urlConnection = aurl.openConnection();
                bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

                while ((inputLine = bufferedReader.readLine()) != null)
                {
                    result = result + inputLine;
                    Log.e("MyTag", inputLine);
                }
                bufferedReader.close();
            }
            catch (IOException ex)
            {
                Log.e("MyTag", "IOException");
            }

            if (result != null)
            {
                try
                {
                    XmlPullParserFactory parserFactory = XmlPullParserFactory.newInstance();
                    parserFactory.setNamespaceAware(true);

                    XmlPullParser pullParser = parserFactory.newPullParser();
                    pullParser.setInput(new StringReader(result));

                    int event = pullParser.getEventType();

                    while (event != XmlPullParser.END_DOCUMENT)
                    {
                        switch (event)
                        {
                            case XmlPullParser.START_DOCUMENT:
                                break;
                            case XmlPullParser.START_TAG:
                                //Setting the Current Incidents item object
                                if (pullParser.getName().equalsIgnoreCase("item"))
                                {
                                    RoadworkItem = new Roadwork("", "", "", "");
                                }
                                else if (RoadworkItem != null)
                                {
                                    if (pullParser.getName().equalsIgnoreCase("title"))
                                    {
                                        RoadworkItem.setTitle(pullParser.nextText().trim());
                                    }
                                    else if (pullParser.getName().equalsIgnoreCase("description")) {
                                        RoadworkItem.setDescription(pullParser.nextText().trim());
                                    }
                                    else if (pullParser.getName().equalsIgnoreCase("point"))
                                    {
                                        String latLonString = pullParser.nextText().trim();
                                        String lonString  = latLonString.substring(latLonString.indexOf("-"));
                                        String latString = latLonString.replaceAll(lonString, "");

                                        RoadworkItem.setLat(latString);
                                        RoadworkItem.setLon(lonString);

                                    }
                                }
                                break;
                            case XmlPullParser.END_TAG:
                                if (pullParser.getName().equalsIgnoreCase("item") && RoadworkItem != null)
                                {
                                    RoadworkList.add(RoadworkItem);
                                    System.out.println(RoadworkItem.getDescription() + RoadworkItem.getLat());
                                }
                                break;
                        }
                        event = pullParser.next();
                    }
                }
                catch (XmlPullParserException e)
                {
                    e.printStackTrace();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }


            }
            return RoadworkList;
        }
        @Override
        protected void onPostExecute(List<Roadwork> Roadwork) {
            listViewAdapter = new ListViewAdapter(getApplicationContext(), R.layout.list_view, Roadwork);
            listView.setAdapter(listViewAdapter);
        }
    }

}

