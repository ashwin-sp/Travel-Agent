package actio.ashcompany.com.travelagentv11;

import android.app.Activity;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import actio.ashcompany.com.travelagentv11.factory.LoggerFactory;
import actio.ashcompany.com.travelagentv11.model.LoggerModel;
import actio.ashcompany.com.travelagentv11.model.LoggerViewModel;


public class Register extends AppCompatActivity {

    EditText name,age,address,username,password,phno;
   /* databasehelper db;
    SQLiteDatabase sb;*/
    String gender;
    static int regime=0;
    static int[] count1=new int[1000];
    static int[] count2=new int[1000];
    static int[] count3=new int[1000];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        name = (EditText) findViewById(R.id.editText3);
        age = (EditText) findViewById(R.id.editText4);
        address = (EditText) findViewById(R.id.editText5);
        username = (EditText) findViewById(R.id.editText6);
        password = (EditText) findViewById(R.id.editText7);
        phno= (EditText) findViewById(R.id.editText8);

    }
    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.radioButton:
                if (checked) {
                   gender="male";
                    break;
                }
            case R.id.radioButton2:
                if (checked) {
                    gender="female";
                    break;
                }
        }
    }
    public void submit(View view) {

        check();
    }
    public void check() {
        if (validate()) {

            /*
             * Insert the Values to the Database
             */
            regime++;

            LoggerViewModel loggerViewModel = ViewModelProviders.of(this, new LoggerFactory(this.getApplication(), Login.un, Login.pd)).get(LoggerViewModel.class);
            loggerViewModel.insert(new LoggerModel(name.getText().toString(),

                    Integer.parseInt(age.getText().toString())
                           , gender
                           ,address.getText().toString()
                           ,username.getText().toString()
                            , password.getText().toString()
                            ,regime
                           ,Integer.parseInt(phno.getText().toString())

            ));
     /*       sb.execSQL("insert into LOGGER"
                    + " values('"
                    + name.getText().toString()
                    + "',"
                    + "'"
                    + age.getText().toString()
                    + "',"
                    + "'"
                    + gender
                    + "',"
                    + "'"
                    + address.getText().toString()
                    + "',"
                    + "'"
                    + username.getText().toString()
                    + "',"
                    + "'"
                    + password.getText().toString()
                    + "',"
                    + "'"
                    + regime
                    + "',"
                    + "'"
                    + phno.getText().toString()
                    + "')");*/

            count1[loggerViewModel.getReg()]=0;
            count2[loggerViewModel.getReg()]=0;
            count3[loggerViewModel.getReg()]=0;

            // Call the Methods
            Toast.makeText(getApplicationContext(),
                    "Registered Successfully...", Toast.LENGTH_LONG)
                    .show();
            navigate();

        } else {
            // USER Reference Display Message
            Toast.makeText(getApplicationContext(),
                    "Please Fill All the Mandatory Fields", Toast.LENGTH_LONG)
                    .show();
        }
    }
    public boolean validate() {

        if (name.getText().toString().equalsIgnoreCase("")) {
            name.requestFocus();
            return false;
        }

        else if (age.getText().toString().equalsIgnoreCase("")) {
            age.requestFocus();
            return false; }
        else if (gender.equalsIgnoreCase("")) {
            Toast.makeText(getApplicationContext(),"Please select a gender", Toast.LENGTH_SHORT).show();
            return false; }
        else if (address.getText().toString().equalsIgnoreCase("")) {
            address.requestFocus();
            return false; }
        else if (username.getText().toString().equalsIgnoreCase("")) {
            username.requestFocus();
            return false;
        } else if (password.getText().toString().equalsIgnoreCase("")) {
            password.requestFocus();
            return false;
        }else if (phno.getText().toString().equalsIgnoreCase("")) {
            phno.requestFocus();
            return false;
        }

        return true;
    }
    public void navigate() {

        startActivity(new Intent(Register.this, Login.class));
        finish();
      /*  db.close();
        sb.close();*/
    }

}
