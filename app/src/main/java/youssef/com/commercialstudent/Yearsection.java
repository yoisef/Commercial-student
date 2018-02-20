package youssef.com.commercialstudent;

import android.graphics.Typeface;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

public class Yearsection extends AppCompatActivity {

    private TextView teams;
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private Madapter madapter;
    Typeface i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yearsection);

      /*  Firstterm fragment=new Firstterm();
        Secondterm fragmentb=new Secondterm();
        FragmentManager manager=getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction transaction=manager.beginTransaction();
       transaction.add(R.id.main,fragment,"first");
        transaction.add(R.id.main,fragmentb,"second");
        transaction.commit();*/






        tabLayout=(TabLayout)findViewById(R.id.tap);
        viewPager=(ViewPager)findViewById(R.id.pager);
        madapter=new Madapter(getSupportFragmentManager());
        viewPager.setAdapter(madapter);
        tabLayout.setTabsFromPagerAdapter(madapter);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
    }
}
 class  sadapter extends FragmentStatePagerAdapter {

    public sadapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment;
        if (position==0)
            return fragment=new Firstterm();
        if  (position==1)
            return fragment=new Secondterm();
         /*if (position==2)
        return fragment=new Fragc();
        */
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (position==0)
            return "Materials";
        if (position==1)
            return "Doctors";
        if (position==2)
            return "Books";
        return null;
    }


}