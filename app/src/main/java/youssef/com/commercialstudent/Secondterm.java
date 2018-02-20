package youssef.com.commercialstudent;

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

public class Secondterm extends Fragment{
    TextView text;
    TextView aola;
    TextView thany;
    TextView thalt;
    TextView raba;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fag2,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        /*text=(TextView)view.findViewById(R.id.teamss);
        aola=(TextView)view.findViewById(R.id.a);
        thany=(TextView)view.findViewById(R.id.b);
        thalt=(TextView)view.findViewById(R.id.c);
        raba=(TextView)view.findViewById(R.id.d);

        Typeface u=Typeface.createFromAsset(getActivity().getAssets(),"Fonts/ss.ttf");
        aola.setTypeface(u);
        thalt.setTypeface(u);
        thany.setTypeface(u);
        raba.setTypeface(u);
        text.setTypeface(u);*/

    }
}
