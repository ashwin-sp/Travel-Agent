package actio.ashcompany.com.travelagentv11;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.Window;

import Adapter.Tabs;

public class Menus extends FragmentActivity implements ActionBar.TabListener {

    public static ViewPager viewPager;
    public Tabs mAdapter;
    public ActionBar actionBar;
    // Tab titles
    private String[] tabs = {"Places", "About us", "Contact", "Information"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(android.os.Build.VERSION.SDK_INT < 11) {
            requestWindowFeature(Window.FEATURE_NO_TITLE);
        }

        setContentView(R.layout.activity_menus);

        // Initilization
        viewPager = (ViewPager) findViewById(R.id.pager);
        actionBar = getActionBar();
        mAdapter = new Tabs(getSupportFragmentManager());

        viewPager.setAdapter(mAdapter);
        getActionBar().setHomeButtonEnabled(false);
        getActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);


        // Adding Tabs
        for (String tab_name : tabs) {
            actionBar.addTab(actionBar.newTab().setText(tab_name)
                    .setTabListener(this));
        }

        String value = getIntent().getExtras().getString("key");
        if(value.equals("1"))
        {
            actionBar.setSelectedNavigationItem(0);
        }
        else if(value.equals("2"))
        {
            actionBar.setSelectedNavigationItem(1);
        }
        else if(value.equals("3"))
        {
            actionBar.setSelectedNavigationItem(2);
        }
        else if(value.equals("4"))
        {
            actionBar.setSelectedNavigationItem(3);
        }
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                // on changing the page
                // make respected tab selected
                actionBar.setSelectedNavigationItem(position);
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
            }
        });

    }
    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

    }
}
