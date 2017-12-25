package actio.ashcompany.com.travelagentv11;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.PendingIntent;
import android.app.SearchManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.os.Bundle;
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
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import actio.ashcompany.com.travelagentv11.retrofit.RetrofitCallBuilder;

public class Delhi extends Activity {
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;

    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    private String[] mPlanetTitles;
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

        RetrofitCallBuilder.INSTANCE.initRetroBuilder();
        RetrofitCallBuilder.INSTANCE.getData(getResources().getString(R.string.key), getResources().getString(R.string.cx), "vivekananda+statue+chennai");

        mTitle = mDrawerTitle = getTitle();
        mPlanetTitles = getResources().getStringArray(R.array.delhi_array);
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
        inflater.inflate(R.menu.menu_delhi, menu);
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

        public PlanetFragment() {
            // Empty constructor required for fragment subclasses
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            View rootView = inflater.inflate(R.layout.fragment_delhi, container, false);
            int i = getArguments().getInt(ARG_PLANET_NUMBER);
            final String delhi= getResources().getStringArray(R.array.delhi_array)[i];
            TextView t= (TextView) rootView.findViewById(R.id.textView18);
            final TextView t2= (TextView) rootView.findViewById(R.id.textView19);
            final TextView t3= (TextView) rootView.findViewById(R.id.textView20);
            final TextView t4= (TextView) rootView.findViewById(R.id.textView21);
            ImageButton i3= (ImageButton) rootView.findViewById(R.id.imageButton3);
            ImageButton i4= (ImageButton) rootView.findViewById(R.id.imageButton4);
            ImageButton i5= (ImageButton) rootView.findViewById(R.id.imageButton5);
            ImageButton i6= (ImageButton) rootView.findViewById(R.id.imageButton6);
            ImageButton i7= (ImageButton) rootView.findViewById(R.id.imageButton7);
            ImageButton i8= (ImageButton) rootView.findViewById(R.id.imageButton8);

            ScrollView sv= (ScrollView) rootView.findViewById(R.id.scrollView3);
            Button b4= (Button) rootView.findViewById(R.id.button4);

            b4.setVisibility(View.GONE);

            t2.setVisibility(View.GONE);
            t3.setVisibility(View.GONE);
            t4.setVisibility(View.GONE);
            sv.setVisibility(View.GONE);

            if(delhi.equals("About Delhi"))
            {
                getActivity().setTitle(delhi);
                t.setText("Delhi, officially the National Capital Territory of Delhi, is the capital territory of India.It has a population of about 11 million and a metropolitan population of about 16.3 million, making it the second most populous city and second most populous urban agglomeration in India.Such is the nature of urban expansion in Delhi that its growth has expanded beyond the NCT to incorporate towns in neighbouring states and at its largest extent can count a population of about 25 million residents as of 2014\n" +
                        "\n" +
                        "\n" +
                        "\n" +
                        "\n" +
                        "Coordinates: 28°36′36″N 77°13′48″E\n" +
                        "\n" +
                        "\n" +
                        "\n" +
                        "Area\n" +
                        " • Metropolis/Megacity \t1,484.0 km2 (573.0 sq mi)\n" +
                        " • Water \t18 km2 (6.9 sq mi)\n" +
                        " • Metro \t46,208 km2 (17,841 sq mi)\n" +
                        "Elevation \t0–125 m (0–409 ft)\n" +
                        "Population \n" +
                        " • Metropolis/Megacity \t11,007,835\n" +
                        "\n" +
                        "\n" +
                        "\n" +
                        "\n" +
                        "Official language \tHindi\n" +
                        "Spoken languages \tEnglish,Hindi\n");

            }
            else if(delhi.equals("Tourist operators in Delhi"))
            {
                getActivity().setTitle(delhi);
                t.setClickable(true);
                t.setMovementMethod(LinkMovementMethod.getInstance());
                String text = "<a href='http://www.mapsofindia.com/travel-agents/delhi.html'> Click this link to know the list of Tourist operators in Delhi </a>";
                t.setText(Html.fromHtml(text));
            }
            else if(delhi.equals("Places to visit in Delhi"))
            {
                getActivity().setTitle(delhi);
                sv.setVisibility(View.VISIBLE);
                i3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.redfortdelhi.co.in/"));
                        startActivity(i);
                    }
                });
                i4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.jamamasjid.in/"));
                        startActivity(i);
                    }
                });
                i5.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.bahaihouseofworship.in/"));
                        startActivity(i);
                    }
                });
                i6.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.qutubminar.org/"));
                        startActivity(i);
                    }
                });
                i7.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.delhitourism.gov.in/delhitourism/tourist_place/india_gate.jsp"));
                        startActivity(i);
                    }
                });
                i8.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("http://asi.nic.in/asi_monu_whs_humayuntomb.asp"));
                        startActivity(i);
                    }
                });
            }
            else if(delhi.equals("Register")) {
                getActivity().setTitle(delhi);
                t.setText("The registration page");
                b4.setVisibility(View.VISIBLE);
                final databasehelper db=new databasehelper(getActivity());
                b4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if((Register.count1[db.getyourdata2(Login.un, Login.pd)])==1) {
                            Toast.makeText(getActivity(), "Already Registered ", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Register.count1[db.getyourdata2(Login.un, Login.pd)]=1;
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
                                sendSMS(pno,"Registered for Delhi successfully for "+db.getyourdata(Login.un,Login.pd)+" Registration no:"+db.getyourdata2(Login.un,Login.pd)+" through Travel Agentv1.1 app on "+formattedDate,getActivity());
                                sendSMS("9789859912","Registered for Delhi successfully for "+db.getyourdata(Login.un,Login.pd)+" Registration no:"+db.getyourdata2(Login.un,Login.pd)+" through Travel Agentv1.1 app on "+formattedDate,getActivity());
                                fn();
                            }
                            catch(Exception e)
                            {
                                e.printStackTrace();
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
