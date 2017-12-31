package actio.ashcompany.com.travelagentv11;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.PendingIntent;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.telephony.SmsManager;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import actio.ashcompany.com.travelagentv11.adapter.PlacesAdapter;
import actio.ashcompany.com.travelagentv11.model.PlacesPOJO;

/**
 * Created by admin on 4/7/2015.
 */

public class Bangalore extends Activity {
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;

    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    private String[] mPlanetTitles;
    static int count=1;
    private static String SENT = "SMS_SENT";
    private static String DELIVERED = "SMS_DELIVERED";
    private static int MAX_SMS_MESSAGE_LENGTH = 160;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(android.os.Build.VERSION.SDK_INT < 11) {
            requestWindowFeature(Window.FEATURE_NO_TITLE);
        }

        setContentView(R.layout.activity_delhi);

        mTitle = mDrawerTitle = getTitle();
        mPlanetTitles = getResources().getStringArray(R.array.Bangalore_array);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        // set a custom shadow that overlays the main content when the drawer opens
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        // set up the drawer's list view with items and click listener
        mDrawerList.setAdapter(new ArrayAdapter<String>(this,
                R.layout.drawer_list_item, mPlanetTitles));
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

        // enable ActionBar app icon to behave as action to toggle nav drawer
        getActionBar().setDisplayHomeAsUpEnabled(true);


        // ActionBarDrawerToggle ties together the the proper interactions
        // between the sliding drawer and the action bar app icon
        mDrawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                mDrawerLayout,         /* DrawerLayout object */
                R.drawable.ic_drawer,  /* nav drawer image to replace 'Up' caret */
                R.string.drawer_open,  /* "open drawer" description for accessibility */
                R.string.drawer_close  /* "close drawer" description for accessibility */
        ) {
            public void onDrawerClosed(View view) {
                getActionBar().setTitle(mTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            public void onDrawerOpened(View drawerView) {
                getActionBar().setTitle(mDrawerTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        if (savedInstanceState == null) {
            selectItem(0);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_bangalore, menu);
        return super.onCreateOptionsMenu(menu);
    }

    /* Called whenever we call invalidateOptionsMenu() */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // If the nav drawer is open, hide action items related to the content view
        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
        menu.findItem(R.id.action_websearch).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // The action bar home/up action should open or close the drawer.
        // ActionBarDrawerToggle will take care of this.
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle action buttons
        switch(item.getItemId()) {
            case R.id.action_websearch:
                if(!getActionBar().getTitle().equals("Register")) {
                    Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);
                    intent.putExtra(SearchManager.QUERY, getActionBar().getTitle());
                    // catch event that there's no activity to handle intent
                    if (intent.resolveActivity(getPackageManager()) != null) {
                        startActivity(intent);
                    } else {
                        Toast.makeText(this, R.string.app_not_available, Toast.LENGTH_LONG).show();
                    }
                    return true;
                }
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /* The click listner for ListView in the navigation drawer */
    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }
    }

    private void selectItem(int position) {
        // update the main content by replacing fragments
        Fragment fragment = new PlanetFragment();
        Bundle args = new Bundle();
        args.putInt(PlanetFragment.ARG_PLANET_NUMBER, position);
        fragment.setArguments(args);

        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
        // update selected item and title, then close the drawer
        mDrawerList.setItemChecked(position, true);
        setTitle(mPlanetTitles[position]);
        mDrawerLayout.closeDrawer(mDrawerList);
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getActionBar().setTitle(mTitle);
    }


    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    /**
     * Fragment that appears in the "content_frame", shows a planet
     */
    public static class PlanetFragment extends Fragment {
        public static final String ARG_PLANET_NUMBER = "planet_number";
        ArrayList<PlacesPOJO> arrayList = new ArrayList<>();
        PlacesAdapter placesAdapter;

        public PlanetFragment() {
            // Empty constructor required for fragment subclasses
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            View rootView = inflater.inflate(R.layout.fragment_single_place, container, false);
            int i = getArguments().getInt(ARG_PLANET_NUMBER);
            final String bangalore= getResources().getStringArray(R.array.Bangalore_array)[i];
            TextView t= (TextView) rootView.findViewById(R.id.textView18);
            final TextView t2= (TextView) rootView.findViewById(R.id.textView19);
            final TextView t3= (TextView) rootView.findViewById(R.id.textView20);
            final TextView t4= (TextView) rootView.findViewById(R.id.textView21);
          /*  ImageButton i3= (ImageButton) rootView.findViewById(R.id.imageButton3);
            ImageButton i4= (ImageButton) rootView.findViewById(R.id.imageButton4);
            ImageButton i5= (ImageButton) rootView.findViewById(R.id.imageButton5);
            ImageButton i6= (ImageButton) rootView.findViewById(R.id.imageButton6);
            ImageButton i7= (ImageButton) rootView.findViewById(R.id.imageButton7);
            ImageButton i8= (ImageButton) rootView.findViewById(R.id.imageButton8);*/
            RecyclerView sv= rootView.findViewById(R.id.scrollView3);
            sv.setHasFixedSize(true);

            // use a Grid layout manager
            GridLayoutManager mLayoutManager = new GridLayoutManager(getActivity(),2);
            sv.setLayoutManager(mLayoutManager);


            arrayList.add(new PlacesPOJO("Skyline Building", "http://www.asiagreenbuildings.com/wp-content/uploads/2014/02/Bangalore-skyline-India.jpg","http://www.karnataka.com/real-estate/about-skyline-constructions-bangalore/" ));
            arrayList.add(new PlacesPOJO("Iskcon Temple","http://res.cloudinary.com/himanshujain/image/upload/v1462693965/activity/ISKCON_Temple_Bangalore_koguri.jpg","http://www.iskconbangalore.org/" ));
            arrayList.add(new PlacesPOJO("Tipu Sultan Palace","https://www.indianholiday.com/pictures/travelguide/attractions/bangalore-palace-4068.jpeg", "http://www.mapsofindia.com/my-india/travel/tipu-fort-or-bangalore-fort"));
            arrayList.add(new PlacesPOJO("Lal Bagh","https://travelwithdarshik.files.wordpress.com/2014/01/lal-bagh-bangalore.jpg", "http://www.horticulture.kar.nic.in/lalbagh.htm"));
            arrayList.add(new PlacesPOJO("Jayamahal Palace","https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQcnhxIwWC19lxXQc88SmLYbZV-MADqhoETRKyoaaDhGc0hVmklCQ", "http://jayamahalpalace.in/"));
            arrayList.add(new PlacesPOJO("Cubbon Park","https://upload.wikimedia.org/wikipedia/commons/thumb/4/40/Bangalore_City_Central_Library.jpg/270px-Bangalore_City_Central_Library.jpg", "http://www.horticulture.kar.nic.in/cubbon.htm"));


            placesAdapter = new PlacesAdapter(arrayList, getActivity());
            sv.setAdapter(placesAdapter);
            placesAdapter.notifyDataSetChanged();

            Button b4= (Button) rootView.findViewById(R.id.button4);
            b4.setVisibility(View.GONE);
            t2.setVisibility(View.GONE);
            t3.setVisibility(View.GONE);
            t4.setVisibility(View.GONE);
            sv.setVisibility(View.GONE);
            if(bangalore.equals("About Bangalore"))
            {
                getActivity().setTitle(bangalore);
                t.setText("Bangalore, officially known as Bengaluru, is the capital of the South Indian state of Karnataka. It has a population of about 8.42 million and a metropolitan population of about 8.52 million, making it the third most populous city and fifth most populous urban agglomeration in India. Located in southern India on the Deccan Plateau, at a height of over 900 m (3,000 ft) above sea level, Bengaluru is known for its pleasant climate throughout the year. Its elevation is the highest among the major large cities of India.[6]\n" +
                        "\n" +
                        "\n" +
                        "\n" +
                        "Coordinates: 12°58′N 77°34′E\n" +
                        "\n" +
                        "\n" +
                        "Area\n" +
                        " • Urban \t741 km2 (286 sq mi)\n" +
                        "Elevation \t920 m (3,020 ft)\n" +
                        "Population  \t8,425,970\n" +
                        "\n" +
                        "\n" +
                        "Official language \tKannada\n" +
                        "Spoken languages \tKannada, English,Hindi\n ");

            }
            else if(bangalore.equals("Tourist operators in Bangalore"))
            {
                getActivity().setTitle(bangalore);
                t.setClickable(true);
                t.setMovementMethod(LinkMovementMethod.getInstance());
                String text = "<a href='http://www.mapsofindia.com/travel-agents/bangalore.html'> Click this link to know the list of Tourist operators in Bangalore </a>";
                t.setText(Html.fromHtml(text));
            }
            else if(bangalore.equals("Places to visit in Bangalore"))
            {
                getActivity().setTitle(bangalore);
                sv.setVisibility(View.VISIBLE);
             /*   i3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.karnataka.com/real-estate/about-skyline-constructions-bangalore/"));
                        startActivity(i);
                    }
                });
                i4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.iskconbangalore.org/"));
                        startActivity(i);
                    }
                });
                i5.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.mapsofindia.com/my-india/travel/tipu-fort-or-bangalore-fort"));
                        startActivity(i);
                    }
                });
                i6.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.horticulture.kar.nic.in/lalbagh.htm"));
                        startActivity(i);
                    }
                });
                i7.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("http://jayamahalpalace.in/"));
                        startActivity(i);
                    }
                });
                i8.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.horticulture.kar.nic.in/cubbon.htm"));
                        startActivity(i);
                    }
                });*/
            }
            else if(bangalore.equals("Register")) {
                getActivity().setTitle(bangalore);
                t.setText("The registration page");
                final databasehelper db= new databasehelper(getActivity());
                b4.setVisibility(View.VISIBLE);
                b4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(Register.count3[db.getyourdata2(Login.un, Login.pd)] ==1){
                            Toast.makeText(getActivity(), "Already Registered", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Register.count3[db.getyourdata2(Login.un, Login.pd)]=1;
                            String nam=db.getyourdata(Login.un,Login.pd);
                            String pno=db.getyourdata1(Login.un,Login.pd);

                            try
                            {
                                t2.setText("Registered successfully");
                                t3.setText(nam);
                                t4.setText(pno);
                                Calendar c = Calendar.getInstance();
                                SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
                                String formattedDate = df.format(c.getTime());
                                sendSMS(pno,"Registered for Bangalore successfully for "+db.getyourdata(Login.un,Login.pd)+" Registration no:"+db.getyourdata2(Login.un,Login.pd)+" through Travel Agentv1.1 app on "+formattedDate,getActivity());
                                sendSMS("9789859912","Registered for Bangalore successfully for "+db.getyourdata(Login.un,Login.pd)+" Registration no:"+db.getyourdata2(Login.un,Login.pd)+" through Travel Agentv1.1 app on "+formattedDate,getActivity());
                                fn();
                            }
                            catch(Exception e)
                            {
                                e.printStackTrace();
                                Toast.makeText(getActivity(), "Registered successfully", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });

            }

            return rootView;
        }
        public int sendSMS(String phoneNumber, String message, Context mContext) {


            PendingIntent piSent = PendingIntent.getBroadcast(mContext, 0, new Intent(SENT), 0);
            PendingIntent piDelivered = PendingIntent.getBroadcast(mContext, 0,new Intent(DELIVERED), 0);
            SmsManager smsManager = SmsManager.getDefault();

            int length = message.length();
            if(length > MAX_SMS_MESSAGE_LENGTH) {
                ArrayList<String> messagelist = smsManager.divideMessage(message);
                smsManager.sendMultipartTextMessage(phoneNumber, null, messagelist, null, null);

            }
            else {
                smsManager.sendTextMessage(phoneNumber, null, message, piSent, piDelivered);

            }
            return 1;
        }
        public void fn()
        {
            Toast.makeText(getActivity(), "SMS Sent", Toast.LENGTH_SHORT).show();
            Intent k = new Intent(getActivity(), Menus.class);
            k.putExtra("key", "4");
            startActivity(k);

        }
    }
}
