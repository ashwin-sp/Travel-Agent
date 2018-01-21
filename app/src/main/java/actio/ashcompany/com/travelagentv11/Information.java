package actio.ashcompany.com.travelagentv11;

import android.arch.lifecycle.ViewModelProviders;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import actio.ashcompany.com.travelagentv11.factory.LoggerFactory;
import actio.ashcompany.com.travelagentv11.model.LoggerViewModel;


public class Information extends Fragment {

    /**
     * A placeholder fragment containing a simple view.
     */


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.activity_information, container, false);
       // databasehelper db = new databasehelper(getActivity());


        accessDB(new Callback() {
            @Override
            public void postExecute(LoggerViewModel loggerViewModel) {
                if (Register.count1[loggerViewModel.getReg()] ==1) {
                    TextView t23 = (TextView) rootView.findViewById(R.id.textView23);

                    String text = loggerViewModel.getName();//this is the method to query
                    t23.setText(text);
                    TextView t24 = (TextView) rootView.findViewById(R.id.textView24);
                    String text1 = String.valueOf(loggerViewModel.getPhno());//this is the method to query
                    t24.setText(text1);
                }
                if(Register.count2[loggerViewModel.getReg()] ==1)
                {

                    TextView t27 = (TextView) rootView.findViewById(R.id.textView32);
                    String text4 = loggerViewModel.getName();//this is the method to query
                    t27.setText(text4);
                    TextView t28 = (TextView) rootView.findViewById(R.id.textView33);
                    String text5 = String.valueOf(loggerViewModel.getPhno());//this is the method to query
                    t28.setText(text5);
                }

                if(Register.count3[loggerViewModel.getReg()] ==1)
                {

                    TextView t27 = (TextView) rootView.findViewById(R.id.textView38);
                    String text2 = loggerViewModel.getName();//this is the method to query
                    t27.setText(text2);
                    TextView t28 = (TextView) rootView.findViewById(R.id.textView39);
                    String text3 = String.valueOf(loggerViewModel.getPhno());//this is the method to query
                    t28.setText(text3);

                }
            }
        });
        return rootView;
    }
    void accessDB(final Callback callback)
    {
        new Thread(new Runnable() {
            @Override
            public void run() {
                LoggerViewModel loggerViewModel = ViewModelProviders.of(Information.this, new LoggerFactory(Information.this.getActivity().getApplication(), Login.un, Login.pd)).get(LoggerViewModel.class);
                callback.postExecute(loggerViewModel);
            }
        }).start();
    }
    public interface Callback
    {
        void postExecute(LoggerViewModel loggerViewModel);
    }
}





