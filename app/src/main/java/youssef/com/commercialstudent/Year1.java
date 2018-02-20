package youssef.com.commercialstudent;

import android.content.Context;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Year1 extends AppCompatActivity {
    FirebaseDatabase database;
    DatabaseReference myref;
    DatabaseReference doc;
    TextView mohasbaa;
    TextView eqtsaad;
    TextView ryadamalya;
    TextView edara;
    TextView tgrbaa;
    ArrayList<String> matriels;
    ListView listView;
    private StorageReference mstorage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_year1);
        matriels=new ArrayList<String>();
        listView=(ListView)findViewById(R.id.list);
        mohasbaa=(TextView)findViewById(R.id.mohasba);
        eqtsaad=(TextView)findViewById(R.id.eqtsad);
        ryadamalya=(TextView)findViewById(R.id.malya);
        edara=(TextView)findViewById(R.id.edara);
        mstorage = FirebaseStorage.getInstance().getReference();

        final ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,R.layout.row,R.id.Material,matriels);

        listView.setAdapter(adapter);


         // Make Listview run on ScrollView
        listView.setOnTouchListener(new View.OnTouchListener() {
            // Setting on Touch Listener for handling the touch inside ScrollView
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // Disallow the touch request for parent scroll on touch of child view
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });


        database=FirebaseDatabase.getInstance();
        myref=database.getReference("الترم الاول").child("الفرقة الاولي").child("المواد");
        doc=database.getReference("الترم الاول").child("الفرقة الاولي").child("الدكاترة والمعيدين");
        doc.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Map<String,String>map=( Map<String,String>) dataSnapshot.getValue();
                String mohasba=map.get("مبادئ المحاسبة");
                String edaraaa=map.get("مبادئ الادارة");
                String ryada=map.get("رياضة مالية");
                String eqtsadkole=map.get("اقتصاد كلي");
                mohasbaa.setText(mohasba);
                edara.setText(edaraaa);
                ryadamalya.setText(ryada);
                eqtsaad.setText(eqtsadkole);



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        myref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

               String as=dataSnapshot.getValue().toString();
                matriels.add(as);
                adapter.notifyDataSetChanged();


            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                String as=dataSnapshot.getValue().toString();


            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                String as=dataSnapshot.getValue().toString();
                matriels.remove(as);
                adapter.notifyDataSetChanged();


            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }



    }

