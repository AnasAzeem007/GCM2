package com.anas.gcm2;

import static com.anas.gcm2.CommonUtilities.SENDER_ID;
import static com.anas.gcm2.CommonUtilities.SERVER_URL;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

public class RegisterActivity extends Activity {

	// alert dialog manager
    AlertDialogManager alert = new AlertDialogManager();
     
    // Internet detector
    ConnectionDetector cd;
     
    // UI elements
    EditText txtName;
    EditText txtEmail;
     
    // Register button
    Button btnRegister, btnUnregister;
    
 
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
         
        cd = new ConnectionDetector(getApplicationContext());
 
        // Check if Internet present
        if (!cd.isConnectingToInternet()) {
            // Internet Connection is not present
            alert.showAlertDialog(RegisterActivity.this,
                    "Internet Connection Error",
                    "Please connect to working Internet connection", false);
            // stop executing code by return
            return;
        }
 
        // Check if GCM configuration is set
        if (SERVER_URL == null || SENDER_ID == null || SERVER_URL.length() == 0
                || SENDER_ID.length() == 0) {
            // GCM sernder id / server url is missing
            alert.showAlertDialog(RegisterActivity.this, "Configuration Error!",
                    "Please set your Server URL and GCM Sender ID", false);
            // stop executing code by return
             return;
        }
         
        txtName = (EditText) findViewById(R.id.txtName);
        txtEmail = (EditText) findViewById(R.id.txtEmail);
        btnRegister = (Button) findViewById(R.id.btnRegister);
        btnUnregister = (Button) findViewById(R.id.btnUnregister);
        /*
         * Click event on Register button
         * */
        btnRegister.setOnClickListener(new View.OnClickListener() {
             
            @Override
            public void onClick(View arg0) {
                // Read EditText dat
                String name = txtName.getText().toString();
                String email = txtEmail.getText().toString();
                 
                // Check if user filled the form
                if(name.trim().length() > 0 && email.trim().length() > 0){
                    // Launch Main Activity
                    Intent i = new Intent(getApplicationContext(), MainActivity.class);
                     
                    // Registering user on our server                   
                    // Sending registraiton details to MainActivity
                    i.putExtra("name", name);
                    i.putExtra("email", email);
                    startActivity(i);
                    //finish();
                }else{
                    // user doen't filled that data
                    // ask him to fill the form
                    alert.showAlertDialog(RegisterActivity.this, "Registration Error!", "Please enter your details", false);
                }
            }
        });
        btnUnregister.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(getApplicationContext(), Unregistered.class);
				startActivity(i);
				
			}
		});
        txtEmail.setOnEditorActionListener(new OnEditorActionListener(){  

            @Override 
            public boolean onEditorAction(TextView arg0, int arg1, KeyEvent arg2) { 
                if(arg1 == EditorInfo.IME_ACTION_GO)  
                {
                	String name = txtName.getText().toString();
                    String email = txtEmail.getText().toString();
                     
                    // Check if user filled the form
                    if(name.trim().length() > 0 && email.trim().length() > 0){
                        // Launch Main Activity
                        Intent i = new Intent(getApplicationContext(), MainActivity.class);
                         
                        // Registering user on our server                   
                        // Sending registraiton details to MainActivity
                        i.putExtra("name", name);
                        i.putExtra("email", email);
                        startActivity(i);
                        //finish();
                    }else{
                        // user doen't filled that data
                        // ask him to fill the form
                        alert.showAlertDialog(RegisterActivity.this, "Registration Error!", "Please enter your details", false);
                    }
                }
                return false; 
            } 

        }); 
    }

}
