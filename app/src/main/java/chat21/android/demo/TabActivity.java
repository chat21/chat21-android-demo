package chat21.android.demo;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import java.util.HashMap;
import java.util.Map;

import org.chat21.android.core.ChatManager;
import org.chat21.android.ui.ChatUI;

public class TabActivity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.view_pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        ChatUI.getInstance().processRemoteNotification(getIntent());

    }

    @Override
    protected void onResume() {
        ChatManager.getInstance().getMyPresenceHandler().connect();
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        ChatManager.getInstance().getMyPresenceHandler().dispose();
        super.onDestroy();
    }

    //    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_tab, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        private int tabsCount;
        private Map<String, Item> tabsMap;
        private String[] tabsTags;

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);

            tabsMap = new HashMap<>();

            // create a map with all tabs
            tabsMap.put(getString(R.string.tag_home),
                    new Item(getString(R.string.tab_home_title), 1));
            tabsMap.put(getString(R.string.tag_chat),
                    new Item(getString(R.string.tab_chat_title), 1));
            tabsMap.put(getString(R.string.tag_profile),
                    new Item(getString(R.string.tab_profile_title), 1));

            // retrieve tab tags
            tabsTags = getResources().getStringArray(R.array.tab_tags);
            tabsCount = tabsTags.length; // count tab tags
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.

            String tabTag = getTagByPosition(position);
            if (tabTag.equals(getString(R.string.tag_home))) {
                return HomeFragment.newInstance();
            } else if (tabTag.equals(getString(R.string.tag_chat))) {
                return ChatFragment.newInstance();
            } else if (tabTag.equals(getString(R.string.tag_profile))) {
                return UserProfileFragment.newInstance();
            } else {
                // default load home
                return HomeFragment.newInstance();
            }
        }

        @Override
        public int getCount() {
            return tabsCount;
        }

        private String getTagByPosition(int position) {
            return tabsTags[position];
        }

        private String getTitleByTag(String tag) {
            return tabsMap.get(tag).getTitle();
        }

        private int getIconByTag(String tag) {
            return tabsMap.get(tag).getIcon();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            String tabTag = getTagByPosition(position);
            return getTitleByTag(tabTag);
        }

        private class Item {
            private String title;
            private int icon;

            private Item(String title, int icon) {
                setTitle(title);
                setIcon(icon);
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public int getIcon() {
                return icon;
            }

            public void setIcon(int icon) {
                this.icon = icon;
            }

            @Override
            public String toString() {
                return "Item{" +
                        "title='" + title + '\'' +
                        ", icon='" + icon + '\'' +
                        '}';
            }
        }
    }
}