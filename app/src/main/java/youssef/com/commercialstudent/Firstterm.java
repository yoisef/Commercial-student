package youssef.com.commercialstudent;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by mohamed on 20/12/2017.
 */

public class Firstterm extends Fragment {
    TextView text;
    TextView aola;
    TextView thany;
    TextView thalt;
    TextView raba;
    ViewGroup elmlk;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frag1, container, false);


    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        aola=(TextView)view.findViewById(R.id.text1);
        thany=(TextView)view.findViewById(R.id.text2);
        thalt=(TextView)view.findViewById(R.id.text3);
        raba=(TextView)view.findViewById(R.id.text4);

        Typeface u=Typeface.createFromAsset(getActivity().getAssets(),"Fonts/ss.ttf");
        aola.setTypeface(u);
        thalt.setTypeface(u);
        thany.setTypeface(u);
        raba.setTypeface(u);


       aola.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent (getActivity(),Year1.class);
                startActivity(i);

            }
        });
    }

    }



