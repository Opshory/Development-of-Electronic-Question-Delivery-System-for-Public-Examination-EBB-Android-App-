package bd.ac.kuet.ebb;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.sdsmdg.tastytoast.TastyToast;

public class Register extends AppCompatActivity {


    private Button RegisterButton;
    private EditText EmailR;
    private EditText PasswordR;
    private EditText PasswordRR;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;
    private DatabaseReference databaseReference;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_register);
        overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);


        init();

    }


    public void init()
    {
        RegisterButton = (Button) findViewById(R.id.buttonRegister);
        EmailR = (EditText) findViewById(R.id.REmail);
        PasswordR = (EditText) findViewById(R.id.RPassword);
        PasswordRR = (EditText) findViewById(R.id.RRPassword);
        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);
        databaseReference = FirebaseDatabase.getInstance().getReference();



        RegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });




    }



    public final static boolean isValidEmail(CharSequence target) {
        if (target == null) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }



    private void registerUser()
    {
        final String EmailRValue = EmailR.getText().toString().trim();
        final String PasswordRValue = PasswordR.getText().toString().trim();
        final String PasswordRRValue = PasswordRR.getText().toString().trim();


        if(!isValidEmail(EmailRValue))
        {
            TastyToast.makeText(Register.this, "Invalid Email", TastyToast.LENGTH_SHORT, TastyToast.WARNING).show();
        }
        else if(PasswordRValue.length() < 8)
        {
            TastyToast.makeText(Register.this, "Choose at least 8 Character", TastyToast.LENGTH_SHORT, TastyToast.WARNING).show();
            PasswordR.requestFocus();
        }
        else if(!PasswordRValue.equals(PasswordRRValue))
        {
            TastyToast.makeText(Register.this, "Password Don't Match", TastyToast.LENGTH_SHORT, TastyToast.WARNING).show();
        }
        else
        {
//            progressDialog.setMessage("Registering...");
//            progressDialog.show();
//            final SpotsDialog dialog = new SpotsDialog(this, R.style.Custom);
//            dialog.setCancelable(false);
//            dialog.show();

            final KProgressHUD hud = KProgressHUD.create(this)
                    .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                    .setLabel("Registering")
                    .setDetailsLabel("Please Wait...")
                    .setCancellable(false)
                    .setAnimationSpeed(3)
                    .setDimAmount(0.7f)
                    .show();
            firebaseAuth.createUserWithEmailAndPassword(EmailRValue, PasswordRRValue)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful())
                            {
                                TastyToast.makeText(Register.this, "Registration Successful", TastyToast.LENGTH_SHORT, TastyToast.SUCCESS).show();
                                //progressDialog.cancel();
                                //dialog.cancel();
                                hud.dismiss();
                                //UserInformation userInformation = new UserInformation(userName, userEmail, Gender, institution, department, dob);
                                FirebaseUser user = firebaseAuth.getCurrentUser();
                                //databaseReference.child("BasicInformations").child(user.getUid()).setValue(userInformation);

                                Info_Institute info_institute = new Info_Institute("", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "");

                                    databaseReference.child(user.getUid()).child("Institute_info").child("Institute_info_").setValue(info_institute);
                                    databaseReference.child(user.getUid()).child("Account_type").child("type").setValue("user");

                            }
                            else
                            {
                                TastyToast.makeText(Register.this, "Email already exist!", TastyToast.LENGTH_SHORT, TastyToast.WARNING).show();
                                //progressDialog.dismiss();
                                //progressDialog.cancel();
                                //dialog.cancel();
                                hud.dismiss();
                            }
                        }
                    });
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out);
    }
}
