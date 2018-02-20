package youssef.com.commercialstudent;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by mohamed on 14/02/2018.
 */

public class Main2Activity extends AppCompatActivity {



    private TextView teams;
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private Madapter madapter;
    private Toolbar mytoolbar;
    private ImageView profileimage;
    FirebaseAuth mauth;
    Button saveinformation;

    FirebaseAuth.AuthStateListener mAlistener;
    Typeface i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

      /*  Firstterm fragment=new Firstterm();
        Secondterm fragmentb=new Secondterm();
        FragmentManager manager=getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction transaction=manager.beginTransaction();
       transaction.add(R.id.main,fragment,"first");
        transaction.add(R.id.main,fragmentb,"second");
        transaction.commit();*/



        saveinformation=(Button)findViewById(R.id.saveinfo);

        mauth=FirebaseAuth.getInstance();
        profileimage=(ImageView)findViewById(R.id.profimag) ;
        mytoolbar=(Toolbar)findViewById(R.id.tool);
        setSupportActionBar(mytoolbar);
        tabLayout=(TabLayout)findViewById(R.id.tap);
        viewPager=(ViewPager)findViewById(R.id.pager);
        madapter=new Madapter(getSupportFragmentManager());
        viewPager.setAdapter(madapter);
        tabLayout.setTabsFromPagerAdapter(madapter);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        saveinformation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        loaduserinfo();
    }


    protected void onStart() {
        super.onStart();

        if (mauth.getCurrentUser() == null) {
            finish();
            startActivity(new Intent(Main2Activity.this, MainActivity.class));
        }
    }

        private void loaduserinfo() {
            String photourl;
            String usernamee;
            FirebaseUser user = mauth.getCurrentUser();
            if (user != null) {
                if (user.getPhotoUrl() != null) {
                    photourl = user.getPhotoUrl().toString();
                    GlideApp.with(Main2Activity.this)
                            .load(photourl)
                            .into(profileimage);
                }
                if (user.getDisplayName() != null) {
                    usernamee = user.getDisplayName().toString();
                    getSupportActionBar()
                            .setTitle(usernamee);
                }


            }
        }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
          if (item.getItemId()==R.id.logout)
          {
              mauth.signOut();
              finish();
              startActivity(new Intent(Main2Activity.this,MainActivity.class));
          }

        return true;
    }
}



class  Madapter extends FragmentStatePagerAdapter {

    public Madapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment;
        if (position==0)
            return fragment=new Firstterm ();
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
            return "First term";
        if (position==1)
            return "Second term";
        if (position==2)
            return "Tap 3";
        return null;
    }


}