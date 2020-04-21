// Iuri Insali S1504620

package com.example.mobileplatdevcwii;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    //Declare variables and components
    public Button incidentsButton;
    public Button plannedRoadworksButton;
    public Button roadworksButton;
    public Button mapsButton;
    public static Boolean isCurrentIncidents;
    public static Boolean isPlannedRoadworks;
    public static Boolean isRoadworks;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        incidentsButton = findViewById(R.id.incidentsButton);
        incidentsButton.setOnClickListener(this);
        plannedRoadworksButton = findViewById(R.id.plannedRoadworksButton);
        plannedRoadworksButton.setOnClickListener(this);
        roadworksButton = findViewById(R.id.roadworksButton);
        roadworksButton.setOnClickListener(this);
    }

    //Returns Current Incidents
    public static Boolean getIsCurrentIncidents()
    {
        return isCurrentIncidents;
    }

    //Returns Planned Roadworks
    public static Boolean getIsPlannedRoadworks()
    {
        return isPlannedRoadworks;
    }

    //Returns Current Roadworks
    public static Boolean getIsRoadworks()
    {
        return isRoadworks;
    }


    public void onClick(View theView)
    {
        Intent theIntent;
        if (theView == incidentsButton)
        {
            isCurrentIncidents = true;
            isRoadworks = false;
            isPlannedRoadworks = false;
            theIntent = new Intent(getApplicationContext(), RSSParser.class);
            startActivity(theIntent);
        }
        else if (theView == plannedRoadworksButton)
        {
            isRoadworks = false;
            isCurrentIncidents = false;
            isPlannedRoadworks = true;
            theIntent = new Intent(getApplicationContext(), RSSParser.class);
            startActivity(theIntent);
        }
        else if (theView == roadworksButton)
        {
            isCurrentIncidents = false;
            isPlannedRoadworks = false;
            isRoadworks = true;
            theIntent = new Intent(getApplicationContext(), RSSParser.class);
            startActivity(theIntent);
        }

        else if(theView == mapsButton)
        {
            theIntent = new Intent(getApplicationContext(), RSSParser.class);
            startActivity(theIntent);
        }
    }


}
