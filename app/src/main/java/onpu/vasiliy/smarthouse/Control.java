package onpu.vasiliy.smarthouse;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import onpu.vasiliy.smarthouse.fragments.Alarms;
import onpu.vasiliy.smarthouse.fragments.Bathroom;
import onpu.vasiliy.smarthouse.fragments.Garage;
import onpu.vasiliy.smarthouse.fragments.Hall;
import onpu.vasiliy.smarthouse.fragments.Kitchen;

public class Control extends AppCompatActivity {
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control);

        viewPager = findViewById(R.id.viewpager);
        tabLayout = findViewById(R.id.tabs);

        setupViewPager(viewPager);

        tabLayout.setupWithViewPager(viewPager);
        setupTabsIcon();
    }

    private void setupViewPager(ViewPager viewPager){
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new Hall(), getResources().getString(R.string.hall));
        adapter.addFragment(new Bathroom(), getResources().getString(R.string.bathroom));
        adapter.addFragment(new Kitchen(), getResources().getString(R.string.kitchen));
        adapter.addFragment(new Garage(), getResources().getString(R.string.garage));
        adapter.addFragment(new Alarms(), getResources().getString(R.string.alarms));
        viewPager.setAdapter(adapter);
    }

    private void setupTabsIcon(){
        tabLayout.getTabAt(0).setIcon(R.drawable.ic_hall);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_bathroom);
        tabLayout.getTabAt(2).setIcon(R.drawable.ic_kitchen);
        tabLayout.getTabAt(3).setIcon(R.drawable.ic_garage);
        tabLayout.getTabAt(4).setIcon(R.drawable.ic_alert);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter{
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        private ViewPagerAdapter(FragmentManager manager){
            super(manager);
        }

        @Override
        public Fragment getItem(int i) {
            return mFragmentList.get(i);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        private void addFragment(Fragment fragment, String title){
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}
