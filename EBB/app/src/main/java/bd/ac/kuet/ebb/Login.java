package bd.ac.kuet.ebb;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import com.gitonway.lee.niftymodaldialogeffects.lib.NiftyDialogBuilder;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.sdsmdg.tastytoast.TastyToast;

import static bd.ac.kuet.ebb.R.id.LPassword;
import static com.gitonway.lee.niftymodaldialogeffects.lib.Effectstype.Shake;


public class Login extends AppCompatActivity {

    private Button LoginButton, IMStudent;
    private EditText EmailL;
    private EditText PasswordL;
    private CheckBox PasswordShow;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;
    private DatabaseReference databaseReference;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        //full screen activity

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //------------------------------------------


        setContentView(R.layout.activity_login);

        //page transition effect---------------------
        overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);

        init();
    }


    public void init() {
        LoginButton = (Button) findViewById(R.id.buttonLogin);
        IMStudent = (Button) findViewById(R.id.imstudent);
        EmailL = (EditText) findViewById(R.id.LEmail);
        PasswordL = (EditText) findViewById(LPassword);
        PasswordShow = (CheckBox) findViewById(R.id.LShowPassword);
        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);
        databaseReference = FirebaseDatabase.getInstance().getReference();


        //the app cant run without internet
        //this portion is checking active internet connection on a device


        boolean connected = false;
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            //we are connected to a network
            connected = true;
        } else
            connected = false;

        if(connected == false)
        {
            NiftyDialogBuilder dialogBuilder=NiftyDialogBuilder.getInstance(this);

            dialogBuilder                     //.withTitle(null)  no title
                    .withTitleColor("#ff7f23")                                  //def
                    .withDividerColor("#11000000")                              //def
                    .withMessage("Upss!\nNo Internet Connection\n\n")                     //.withMessage(null)  no Msg
                    .withMessageColor("#ff7f23")                              //def  | withMessageColor(int resid)
                    .withDialogColor("#454545")                               //def  | withDialogColor(int resid)
                    .withDuration(200)                                          //def
                    .withEffect(Shake)
                    .withIcon(R.drawable.logo)                                   //def Effectstype.Slidetop
                    .withButton1Text("OK")                                      //def gone                     //def gone
                    .isCancelableOnTouchOutside(false)                           //def    | isCancelable(true)
                    .setButton1Click(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            finish();
                            overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out);
                        }
                    })
                    .show();


        }


        LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser();
            }
        });


        IMStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, ImStudent.class);
                startActivity(intent);
            }
        });




        PasswordShow.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    PasswordL.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
                else
                {
                    PasswordL.setInputType(129);
                }
            }
        });




    }


//function to loged in-------------------------
    //check if a account is there or not----------------

    public void loginUser()
    {
//        progressDialog.setMessage("Logging in...");
//        progressDialog.setCancelable(false);
//        progressDialog.show();
//        final SpotsDialog dialog = new SpotsDialog(Login.this, R.style.Custom);
//        dialog.show();
//        dialog.setCancelable(false);

        final KProgressHUD hud = KProgressHUD.create(Login.this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Loggin in")
                .setDetailsLabel("Please Wait...")
                .setCancellable(false)
                .setAnimationSpeed(3)
                .setDimAmount(0.7f)
                .show();


        final String EmailLValue = EmailL.getText().toString().trim();
        final String PasswordLValue = PasswordL.getText().toString().trim();


        if(!EmailLValue.isEmpty() && !PasswordLValue.isEmpty()) {

            firebaseAuth.signInWithEmailAndPassword(EmailLValue, PasswordLValue)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
//                                progressDialog.cancel();
                                //dialog.cancel();
                                hud.dismiss();
                                //Toast.makeText(Login.this, "successfull", Toast.LENGTH_SHORT).show();
                                FirebaseUser user = firebaseAuth.getCurrentUser();



                                Intent intent = new Intent(Login.this, Home.class);
                                finish();
                                startActivity(intent);
                                //Toast.makeText(StartActivity.this, user.getEmail().toString() , Toast.LENGTH_SHORT).show();
                            }
                            else {
                                TastyToast.makeText(Login.this, "Incorrect Email or Password", TastyToast.LENGTH_SHORT, TastyToast.ERROR).show();
//                                progressDialog.cancel();
                                //dialog.cancel();
                                hud.dismiss();
                            }
                        }
                    });

        }
        else {
            //progressDialog.cancel();
            //dialog.cancel();
            hud.dismiss();
            //Toast.makeText(Login.this, "Invalid Email or Password", Toast.LENGTH_SHORT).show();
            TastyToast.makeText(Login.this, "Invalid Email or Password", TastyToast.LENGTH_SHORT, TastyToast.ERROR).show();
        }

    }




    @Override
    public void onBackPressed() {
        super.onBackPressed();



        //transition effect------------------
        overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out);
    }
}
