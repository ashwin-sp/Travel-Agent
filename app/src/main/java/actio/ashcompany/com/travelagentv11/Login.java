package actio.ashcompany.com.travelagentv11;

import android.app.Activity;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.lang.ref.WeakReference;

import actio.ashcompany.com.travelagentv11.factory.LoggerFactory;
import actio.ashcompany.com.travelagentv11.model.LoggerViewModel;


public class Login extends AppCompatActivity {

    EditText username;
    EditText password;
    Button btnl;
    Button btnr;
    static String un;
    static String pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        username = (EditText) findViewById(R.id.editText);
        password = (EditText) findViewById(R.id.editText2);
        btnl = (Button) findViewById(R.id.button);
        btnr = (Button) findViewById(R.id.button2);
        btnl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                un=username.getText().toString();
                pd=password.getText().toString();
                if(un.equals("")){
                    Toast.makeText(getApplicationContext(), "Username Empty", Toast.LENGTH_SHORT).show();
                }else if(pd.equals("")){
                    Toast.makeText(getApplicationContext(), "Password Empty", Toast.LENGTH_SHORT).show();
                }else{
                    validateLogin(un, pd, new ValidateCallback() {
                        @Override
                        public void onValidateResult(final boolean result) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if(result)
                                    {
                                        Toast.makeText(getApplicationContext(), "Login Successful", Toast.LENGTH_SHORT).show();
                                        Intent h = new Intent(Login.this, Home.class);
                                        //Close views before starting Dashboard
                                        h.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        startActivity(h);
                                        finish();
                                    }
                                    else {
                                        Toast.makeText(getApplicationContext(), "Incorrect Login..\nTry Again", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                    });
                }


            }
        });
    }
    public void register(View view)
    {
        Intent i=new Intent(Login.this,Register.class);
        startActivity(i);
    }

    public void validateLogin(final String userName,final String userPass, final ValidateCallback callback) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                LoggerViewModel loggerViewModel = ViewModelProviders.of(Login.this, new LoggerFactory(Login.this.getApplication(), userName, userPass)).get(LoggerViewModel.class);
                if(loggerViewModel.getName() != null && !loggerViewModel.getName().isEmpty())
                {
                    callback.onValidateResult(true);
                }
                else
                {
                    callback.onValidateResult(false);
                }
            }
        }).start();


       /* databasehelper db = new databasehelper(getApplicationContext());
        SQLiteDatabase sb = db.getReadableDatabase();

        //SELECT
        String[] columns = {"username"};

        //WHERE clause
        String selection = "username=? AND password=?";

        //WHERE clause arguments
        String[] selectionArgs = {userName, userPass};
        Cursor c;

        try{
            //SELECT userId FROM login WHERE username=userName AND password=userPass
            c = sb.query("LOGGER", columns, selection, selectionArgs, null, null, null);
            c.moveToFirst();

            int i = c.getCount();
            c.close();
            if(i <= 0){
                Toast.makeText(getApplicationContext(), "Incorrect Login..\nTry Again", Toast.LENGTH_SHORT).show();
                return false;
            }

            return true;
        }catch(Exception e){
            e.printStackTrace();
            return false;
        } finally {
            db.close();
        }*/
    }//validate Login
    public void onBackPressed() {
        Log.d("CDA", "onBackPressed Called");
        Intent setIntent = new Intent(Intent.ACTION_MAIN);
        setIntent.addCategory(Intent.CATEGORY_HOME);
        setIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(setIntent);
    }

    public interface ValidateCallback
    {
        public void onValidateResult(boolean result);
    }
}