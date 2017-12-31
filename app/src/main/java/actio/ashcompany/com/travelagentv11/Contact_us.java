package actio.ashcompany.com.travelagentv11;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class Contact_us extends Fragment {

    /**
     * A placeholder fragment containing a simple view.
     */


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_contact_us, container, false);
        TextView txt = rootView.findViewById(R.id.textView27);
        txt.setText("Ashwin S \n+91-9789859912");
        return rootView;
    }
}

