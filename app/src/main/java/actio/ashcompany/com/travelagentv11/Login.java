package actio.ashcompany.com.travelagentv11;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class Login extends Activity {

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
                boolean validLogin = validateLogin(un, pd);
                if(un.equals("")){
                    Toast.makeText(getApplicationContext(), "Username Empty", Toast.LENGTH_SHORT).show();
                }else if(pd.equals("")){
                    Toast.makeText(getApplicationContext(), "Password Empty", Toast.LENGTH_SHORT).show();
                }else{

                    if(validLogin){
                        //Show a dialog of login successful
                        Toast.makeText(getApplicationContext(), "Login Successful", Toast.LENGTH_SHORT).show();
                        Intent h = new Intent(Login.this, Home.class);
                        //Close views before starting Dashboard
                        h.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(h);
                        finish();
                    }
                }


            }
        });
    }
    public void register(View view)
    {
        Intent i=new Intent(Login.this,Register.class);
        startActivity(i);
    }

    public boolean validateLogin(String userName, String userPass) {

        databasehelper db = new databasehelper(getApplicationContext());
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
        }
    }//validate Login
    public void onBackPressed() {
        Log.d("CDA", "onBackPressed Called");
        Intent setIntent = new Intent(Intent.ACTION_MAIN);
        setIntent.addCategory(Intent.CATEGORY_HOME);
        setIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(setIntent);
    }
}