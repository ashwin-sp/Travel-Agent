package actio.ashcompany.com.travelagentv11.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import actio.ashcompany.com.travelagentv11.About_us;
import actio.ashcompany.com.travelagentv11.Contact_us;
import actio.ashcompany.com.travelagentv11.Information;
import actio.ashcompany.com.travelagentv11.Places;

/**
 * Created by admin on 3/28/2015.
 */
public class Tabs extends FragmentStatePagerAdapter {
    public Tabs(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int index) {

        switch (index) {
            case 0:

                return new Places();
            case 1:

                return new About_us();
            case 2:

                return new Contact_us();
            case 3:
                return new Information();
        }

        return null;
    }

    @Override
    public int getCount() {
        // get item count - equal to number of tabs
        return 4;
    }
}
