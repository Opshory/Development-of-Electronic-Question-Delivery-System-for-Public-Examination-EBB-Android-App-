package bd.ac.kuet.ebb;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.gitonway.lee.niftymodaldialogeffects.lib.NiftyDialogBuilder;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.sdsmdg.tastytoast.TastyToast;

import static com.gitonway.lee.niftymodaldialogeffects.lib.Effectstype.Flipv;

public class Home extends AppCompatActivity {


    private LinearLayout UserRegister, PublistQuestion;
    private LinearLayout NoticePost;
    private LinearLayout BoardNotice;
    private LinearLayout InstitutionMy;
    private LinearLayout UsAbout;
    private LinearLayout GetStudentID;
    private LinearLayout PostQuestion;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    public static String AccType;
    public String userID;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


        setContentView(R.layout.activity_home);
        overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);
        init();
    }

    public void init()
    {
        UserRegister = (LinearLayout) findViewById(R.id.RegisterUser);
        NoticePost = (LinearLayout) findViewById(R.id.PostNotice);
        BoardNotice = (LinearLayout) findViewById(R.id.NoticeBoard);
        InstitutionMy = (LinearLayout) findViewById(R.id.MyInstitution);
        PublistQuestion = (LinearLayout) findViewById(R.id.PublishQuestion);
        UsAbout = (LinearLayout) findViewById(R.id.AboutUs);
        GetStudentID = (LinearLayout) findViewById(R.id.getStuID);
        PostQuestion = (LinearLayout) findViewById(R.id.PostQuestion);
        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        userID = firebaseUser.getUid();
        progressDialog = new ProgressDialog(this);

//        progressDialog.setMessage("Please Wait...");
//        progressDialog.setCancelable(false);
//        progressDialog.show();
//        final SpotsDialog dialog = new SpotsDialog(this, R.style.Custom);
//        dialog.setCancelable(false);
//        dialog.show();
        final KProgressHUD hud = KProgressHUD.create(this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please Wait...")
                .setCancellable(false)
                .setAnimationSpeed(3)
                .setDimAmount(0.7f)
                .show();

//  checking the user account..........

        databaseReference.child(userID).child("Account_type").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren())
                {
                    Account_type account_type;
                    account_type = ds.getValue(Account_type.class);
                    AccType = account_type.type;
                    if(AccType.equals("user")) {
                        //UserRegister.setBackgroundColor(Color.parseColor("#C79491"));
                        NoticePost.setVisibility(View.INVISIBLE);
                        NoticePost.setClickable(false);
                        UserRegister.setVisibility(View.INVISIBLE);
                        UserRegister.setClickable(false);
                        PostQuestion.setVisibility(View.INVISIBLE);
                        PostQuestion.setClickable(false);
                        PublistQuestion.setVisibility(View.INVISIBLE);
                        PublistQuestion.setClickable(false);
                    }
                    //progressDialog.cancel();
                    //dialog.cancel();
                    hud.dismiss();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //progressDialog.cancel();
                //dialog.cancel();
                hud.dismiss();
            }
        });



        UserRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(AccType.equals("admin")) {
                    Intent intent = new Intent(Home.this, Register.class);
                    startActivity(intent);
                }
                else
                {
                    TastyToast.makeText(Home.this, "Sorry! Only admin has access here", TastyToast.LENGTH_SHORT, TastyToast.WARNING).show();
                }
            }
        });

        InstitutionMy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this, Details.class);
                startActivity(intent);
            }
        });

        UsAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this, About_us.class);
                startActivity(intent);
            }
        });

        BoardNotice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this, Notice_Board.class);
                startActivity(intent);
            }
        });

        NoticePost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this, Post_Notice.class);
                startActivity(intent);
            }
        });

        GetStudentID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this, Student_Id.class);
                startActivity(intent);
            }
        });

        PostQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this, QuestionBasic.class);
                startActivity(intent);
            }
        });

        PublistQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this, Publish_Question.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onBackPressed() {


        final NiftyDialogBuilder dialogBuilder=NiftyDialogBuilder.getInstance(this);

        dialogBuilder                     //.withTitle(null)  no title
                .withTitleColor("#3abe9f")                                  //def
                .withDividerColor("#11000000")                              //def
                .withMessage("Sure to EXIT?\n\n")                     //.withMessage(null)  no Msg
                .withMessageColor("#4e4e4e")                              //def  | withMessageColor(int resid)
                .withDialogColor("#f5f5f5")                               //def  | withDialogColor(int resid)
                .withDuration(500)                                          //def
                .withEffect(Flipv)
                .withIcon(R.drawable.logo)
                .withButton2Text("YES")//def Effectstype.Slidetop
                .withButton1Text("NO")//def gone                     //def gone
                .isCancelableOnTouchOutside(false)
                .setButton1Click(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogBuilder.cancel();
                    }
                })//def    | isCancelable(true)
                .setButton2Click(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                        overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out);
                    }
                })
                .show();


//        final AlertDialog.Builder builder = new AlertDialog.Builder(Home.this);
//        builder.setMessage("Do you want to exit?");
//        builder.setCancelable(true);
//        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                dialog.cancel();
//            }
//        });
//        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//
//                finish();
//                overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out);
//            }
//        });
//        AlertDialog alertDialog = builder.create();
//        alertDialog.show();
    }


}
