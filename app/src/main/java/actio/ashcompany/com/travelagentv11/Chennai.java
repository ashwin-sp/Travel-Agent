package actio.ashcompany.com.travelagentv11;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.PendingIntent;
import android.app.SearchManager;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
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
import actio.ashcompany.com.travelagentv11.factory.LoggerFactory;
import actio.ashcompany.com.travelagentv11.model.LoggerViewModel;
import actio.ashcompany.com.travelagentv11.model.PlacesPOJO;

/**
 * Created by admin on 4/7/2015.
 */

public class Chennai extends AppCompatActivity {
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
        mPlanetTitles = getResources().getStringArray(R.array.Chennai_array);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        // set a custom shadow that overlays the main content when the drawer opens
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        // set up the drawer's list view with items and click listener
        mDrawerList.setAdapter(new ArrayAdapter<String>(this,
                R.layout.drawer_list_item, mPlanetTitles));
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

        // enable ActionBar app icon to behave as action to toggle nav drawer
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


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
                getSupportActionBar().setTitle(mTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            public void onDrawerOpened(View drawerView) {
                getSupportActionBar().setTitle(mDrawerTitle);
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
        inflater.inflate(R.menu.menu_chennai, menu);
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
                if(!getSupportActionBar().getTitle().equals("Register")) {
                    Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);
                    intent.putExtra(SearchManager.QUERY, getSupportActionBar().getTitle());
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
        fragment.setRetainInstance(true); // <-- this is important - otherwise the fragment manager will crash when reading the fragment
        // update selected item and title, then close the drawer
        mDrawerList.setItemChecked(position, true);
        setTitle(mPlanetTitles[position]);
        mDrawerLayout.closeDrawer(mDrawerList);
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getSupportActionBar().setTitle(mTitle);
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
    @SuppressLint("ValidFragment")
    public class PlanetFragment extends Fragment {
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
            final String chennai= getResources().getStringArray(R.array.Chennai_array)[i];
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


            arrayList.add(new PlacesPOJO("Marina Beach", "http://s3.india.com/travel/wp-content/uploads/2014/10/Marina.jpg","http://www.chennai.org.uk/beaches/marina-beach.html" ));
            arrayList.add(new PlacesPOJO("MGR Memorial","http://img1.holidayiq.com/images/attractions/1344443750_8481.jpg","http://www.chennai.org.uk/monuments/mgr-memorial.html" ));
            arrayList.add(new PlacesPOJO("Vivekanandha Building","http://s3.amazonaws.com/vivekanandahouse.org_media/wp-content/uploads/2011/05/12154525/VHouse.jpg", "http://www.vivekanandahouse.org/"));
            arrayList.add(new PlacesPOJO("Ripon Building","http://im.hunt.in/cg/chennai/City-Guide/ripon.jpg", "http://www.chennaionline.in/city-guide/ripon-building-in-chennai"));
            arrayList.add(new PlacesPOJO("MA Chidambaram Stadium","http://im.hunt.in/cg/chennai/City-Guide/MA%20Stadium.jpg", "http://www.chennaionline.in/city-guide/ma-chidambaram-stadium-in-chennai"));
            arrayList.add(new PlacesPOJO("Fisherman's Cove","https://vivanta.tajhotels.com/content/dam/vivanta/hotels/VBT-Fisherman's_Cove/images/Quick_Peek/Cottages_16x7-02-Copy.jpg.transform.heroHomeDesktop2x.image.jpg", "http://www.vivantabytaj.com/fishermans-cove-chennai/overview.html"));


            placesAdapter = new PlacesAdapter(arrayList, getActivity());
            sv.setAdapter(placesAdapter);
            placesAdapter.notifyDataSetChanged();

            Button b4= (Button) rootView.findViewById(R.id.button4);
            b4.setVisibility(View.GONE);
            t2.setVisibility(View.GONE);
            t3.setVisibility(View.GONE);
            t4.setVisibility(View.GONE);
            sv.setVisibility(View.GONE);
            if(chennai.equals("About Chennai"))
            {
                getActivity().setTitle(chennai);
                t.setText("Chennai (also known as, formerly Madras) is the capital city of the Indian state of Tamil Nadu. Located on the Coromandel Coast off the Bay of Bengal, it is the biggest industrial and commercial centre in South India, and a major cultural, economic and educational centre. Chennai is known as the \"Detroit of India\" for its automobile industry. Chennai is the fifth-largest city and fourth-most populous metropolitan area in the country and 31st-largest urban area in the world.\n" +
                        "\n" +
                        "\n" +
                        "\n" +
                        "\n" +
                        "\n" +
                        "Coordinates: 13°5′N 80°16′E\n" +
                        "\n" +
                        "\n" +
                        "Area[3]\n" +
                        " • Metropolis \t426 km2 (164.8 sq mi)\n" +
                        " • Metro \t1,189 km2 (426 sq mi)\n" +
                        "Elevation \t6 m (20 ft)\n" +
                        "Population (2011)[4]\n" +
                        " • Metropolis \t4,681,087\n" +
                        "\n" +
                        "\n" +
                        "Official language \tTamil\n" +
                        "Spoken languages \tTamil, English\n ");

            }
            else if(chennai.equals("Tourist operators in Chennai"))
            {
                getActivity().setTitle(chennai);
                t.setClickable(true);
                t.setMovementMethod(LinkMovementMethod.getInstance());
                String text = "<a href='http://www.mapsofindia.com/travel-agents/chennai.html'> Click this link to know the list of Tourist operators in Chennai </a>";
                t.setText(Html.fromHtml(text));
            }
            else if(chennai.equals("Places to visit in Chennai"))
            {
                getActivity().setTitle(chennai);
                sv.setVisibility(View.VISIBLE);
              /*  i3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.chennai.org.uk/beaches/marina-beach.html"));
                        startActivity(i);
                    }
                });
                i4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.mgrhome.org/memorial.html"));
                        startActivity(i);
                    }
                });
                i5.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.vivekanandahouse.org/"));
                        startActivity(i);
                    }
                });
                i6.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.chennaicorporation.gov.in/departments/building/history.htm"));
                        startActivity(i);
                    }
                });
                i7.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.iplt20.com/venues/1/m-a-chidambaram-stadium"));
                        startActivity(i);
                    }
                });
                i8.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.vivantabytaj.com/fishermans-cove-chennai/overview.html"));
                        startActivity(i);
                    }
                });*/
            }
            else if(chennai.equals("Register")) {
                getActivity().setTitle(chennai);
                t.setText("The registration page");
                b4.setVisibility(View.VISIBLE);
                b4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        accessDB(new Callback() {
                            @Override
                            public void postExecute(final LoggerViewModel loggerViewModel) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        if(Register.count3[loggerViewModel.getReg()] ==1){
                                            Toast.makeText(getActivity(), "Already Registered", Toast.LENGTH_SHORT).show();
                                        }
                                        else{
                                            Register.count3[loggerViewModel.getReg()]=1;
                                            String nam=loggerViewModel.getName();
                                            String pno=String.valueOf(loggerViewModel.getPhno());
                                            try
                                            {
                                                t2.setText("Registered successfully");
                                                t3.setText(nam);
                                                t4.setText(pno);
                                                Calendar c = Calendar.getInstance();
                                                SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
                                                String formattedDate = df.format(c.getTime());
                                                sendSMS(pno,"Registered for Chennai successfully for "+loggerViewModel.getName()+" Registration no:"+loggerViewModel.getReg()+" through Travel Agent app on "+formattedDate,getActivity());
                                                sendSMS("9789859912","Registered for Chennai successfully for "+loggerViewModel.getName()+" Registration no:"+loggerViewModel.getReg()+" through Travel Agent app on "+formattedDate,getActivity());
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
                        });
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
        void accessDB(final Callback callback)
        {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    LoggerViewModel loggerViewModel = ViewModelProviders.of(Chennai.this, new LoggerFactory(Chennai.this.getApplication(), Login.un, Login.pd)).get(LoggerViewModel.class);
                    callback.postExecute(loggerViewModel);
                }
            }).start();
        }
    }
    public interface Callback
    {
        public void postExecute(LoggerViewModel loggerViewModel);
    }
}
