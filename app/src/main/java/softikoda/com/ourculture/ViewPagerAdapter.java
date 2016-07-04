package softikoda.com.ourculture;

/**
 * Created by johnolwamba on 2/20/2016.
 */
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by hp1 on 21-01-2015.
 */
public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    CharSequence Titles[]; // This will Store the Titles of the Tabs which are Going to be passed when ViewPagerAdapter is created
    int NumbOfTabs; // Store the number of tabs, this will also be passed when the ViewPagerAdapter is created


    // Build a Constructor and assign the passed Values to appropriate values in the class
    public ViewPagerAdapter(FragmentManager fm,CharSequence mTitles[], int mNumbOfTabsumb) {
        super(fm);

        this.Titles = mTitles;
        this.NumbOfTabs = mNumbOfTabsumb;
    }

    //This method return the fragment for the every position in the View Pager
    @Override
    public Fragment getItem(int position) {

        if(position == 0) // if the position is 0 we are returning the First tab
        {
            foods tab1 = new foods();
            Bundle args = new Bundle();
            tab1.setArguments(args);
            return tab1;
        }
        else if (position == 1)            // As we are having 2 tabs if the position is now 0 it must be 1 so we are returning second tab
        {
            courtesy tab2 = new courtesy();
            Bundle args = new Bundle();
            tab2.setArguments(args);
            return tab2;

        }
        else if (position == 2)            // As we are having 2 tabs if the position is now 0 it must be 1 so we are returning second tab
        {
            events tab3 = new events();
            Bundle args = new Bundle();
            tab3.setArguments(args);
            return tab3;

        }
        else if(position == 3){
            artifacts tab4 = new artifacts();
            Bundle args = new Bundle();
            tab4.setArguments(args);
            return tab4;


        }
        else {
            translator tab5 = new translator();
            Bundle args = new Bundle();
            tab5.setArguments(args);
            return tab5;
        }


    }

    // This method return the titles for the Tabs in the Tab Strip

    @Override
    public CharSequence getPageTitle(int position) {
        return Titles[position];
    }

    // This method return the Number of tabs for the tabs Strip

    @Override
    public int getCount() {
        return NumbOfTabs;
    }
}