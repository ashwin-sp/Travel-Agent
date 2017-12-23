package actio.ashcompany.com.travelagentv11;

import android.app.Activity;
import android.app.Fragment;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.speech.RecognizerIntent;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;


public class Home extends FragmentActivity {

    private TextView txtSpeechInput;
    private ImageButton btnSpeak;
    private final int REQ_CODE_SPEECH_INPUT = 200;
    private ImageButton btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        txtSpeechInput = (TextView) findViewById(R.id.textView11);
        btnSpeak = (ImageButton) findViewById(R.id.imageButton);
        btnSpeak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                promptSpeechInput();
            }
        });
        btn =(ImageButton)findViewById(R.id.imageButton2);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i= new Intent(Home.this,Menus.class);
                i.putExtra("key","1");
                startActivity(i);
            }
        });
    }

    public void promptSpeechInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                "Say something");
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getApplicationContext(),
                    "Your device doesn't support speech input",
                    Toast.LENGTH_SHORT).show();
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {

                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    if (result.get(0).equals("menu")) {
                        Intent i = new Intent(Home.this, Menus.class);
                        i.putExtra("key","1");
                        startActivity(i);
                    }
                    else if(result.get(0).equals("1"))
                    {
                        Intent j=new Intent(Home.this,Menus.class);
                        j.putExtra("key","1");
                        startActivity(j);

                    }
                    else if(result.get(0).equals("2"))
                    {
                        Intent j=new Intent(Home.this,Menus.class);
                        j.putExtra("key","2");
                        startActivity(j);

                    }
                    else if(result.get(0).equals("3"))
                    {
                        Intent j=new Intent(Home.this,Menus.class);
                        j.putExtra("key","3");
                        startActivity(j);

                    }
                    else if(result.get(0).equals("4"))
                    {
                        Intent k=new Intent(Home.this,Menus.class);
                        k.putExtra("key","4");
                        startActivity(k);

                    }
                    else {
                        txtSpeechInput.setText(result.get(0));
                    }
                }
                break;
            }

        }
    }
}

