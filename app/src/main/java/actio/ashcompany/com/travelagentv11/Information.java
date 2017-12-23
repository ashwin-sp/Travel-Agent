package actio.ashcompany.com.travelagentv11;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class Information extends Fragment {

    /**
     * A placeholder fragment containing a simple view.
     */


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

            View rootView = inflater.inflate(R.layout.activity_information, container, false);
        databasehelper db = new databasehelper(getActivity());
            if (Register.count1[db.getyourdata2(Login.un, Login.pd)] ==1) {
                TextView t23 = (TextView) rootView.findViewById(R.id.textView23);

                String text = db.getyourdata(Login.un,Login.pd);//this is the method to query
                t23.setText(text);
                TextView t24 = (TextView) rootView.findViewById(R.id.textView24);
                String text1 = db.getyourdata1(Login.un,Login.pd);//this is the method to query
                t24.setText(text1);


            }
            if(Register.count2[db.getyourdata2(Login.un, Login.pd)] ==1)
            {

                TextView t27 = (TextView) rootView.findViewById(R.id.textView32);
                String text4 = db.getyourdata(Login.un,Login.pd);//this is the method to query
                t27.setText(text4);
                TextView t28 = (TextView) rootView.findViewById(R.id.textView33);
                String text5 = db.getyourdata1(Login.un,Login.pd);//this is the method to query
                t28.setText(text5);

            }

            if(Register.count3[db.getyourdata2(Login.un, Login.pd)] ==1)
            {

                TextView t27 = (TextView) rootView.findViewById(R.id.textView38);
                String text2 = db.getyourdata(Login.un,Login.pd);//this is the method to query
                t27.setText(text2);
                TextView t28 = (TextView) rootView.findViewById(R.id.textView39);
                String text3 = db.getyourdata1(Login.un,Login.pd);//this is the method to query
                t28.setText(text3);

            }

            return rootView;
    }
}





