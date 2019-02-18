package bd.ac.kuet.ebb;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.sdsmdg.tastytoast.TastyToast;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class Details extends AppCompatActivity {


    private Button InformationEdit;
    private CircleImageView IconInstitute;
    private TextView InfoName;
    private TextView InfoEstd;
    private TextView InfoEiin;
    private TextView InfoBoard;
    private TextView InfoVersion;
    private TextView InfoHead;
    private TextView InfoDivision;
    private TextView InfoRoom;
    private TextView InfoSeat;
    private TextView InfoTeacher;
    private TextView InfoAddress;
    private TextView InfoGpo;
    private TextView InfoPhone;
    private TextView InfoMobile;
    private TextView InfoWebsite;
    private TextView InfoEmail;
    public static String ValueName;
    public static String ValueEstd;
    public static String ValueEiin;
    public static String ValueBoard;
    public static String ValueVersion;
    public static String ValueHead;
    public static String ValueDivision;
    public static String ValueRoom;
    public static String ValueSeat;
    public static String ValueTeacher;
    public static String ValueAddress;
    public static String ValueGpo;
    public static String ValuePhone;
    public static String ValueMobile;
    public static String ValueWebsite;
    public static String ValueEmail;

    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    private ProgressDialog progressDialog;
    private StorageReference storageReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


        setContentView(R.layout.activity_details);
        overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);


        init();

    }

    public void init()
    {
        InformationEdit = (Button) findViewById(R.id.edit_informations);
        IconInstitute = (CircleImageView) findViewById(R.id.inst_logo);
        InfoName = (TextView) findViewById(R.id.info_name);
        InfoEstd = (TextView) findViewById(R.id.info_estd);
        InfoEiin = (TextView) findViewById(R.id.info_eiin);
        InfoBoard = (TextView) findViewById(R.id.info_board);
        InfoVersion = (TextView) findViewById(R.id.info_version);
        InfoHead = (TextView) findViewById(R.id.info_head);
        InfoDivision = (TextView) findViewById(R.id.info_division);
        InfoRoom = (TextView) findViewById(R.id.info_room);
        InfoSeat = (TextView) findViewById(R.id.info_seat);
        InfoTeacher = (TextView) findViewById(R.id.info_teacher);
        InfoAddress = (TextView) findViewById(R.id.info_address);
        InfoGpo = (TextView) findViewById(R.id.info_gpo);
        InfoPhone = (TextView) findViewById(R.id.info_phone);
        InfoMobile = (TextView) findViewById(R.id.info_mobile);
        InfoWebsite = (TextView) findViewById(R.id.info_website);
        InfoEmail = (TextView) findViewById(R.id.info_email);



        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        storageReference = FirebaseStorage.getInstance().getReference();
//        progressDialog = new ProgressDialog(this);
//        progressDialog.setMessage("Loading...");
//        progressDialog.setCancelable(false);
//        progressDialog.show();
//        final SpotsDialog dialog = new SpotsDialog(this, R.style.Custom);
//        dialog.setCancelable(false);
//        dialog.show();
        final KProgressHUD hud = KProgressHUD.create(this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Loading...")
                .setCancellable(false)
                .setAnimationSpeed(4)
                .setDimAmount(0.7f)
                .show();
// Database for user info..........

        final FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        final String userID = firebaseUser.getUid();
        databaseReference.child(userID).child("Institute_info").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren())
                {
                    Info_Institute info_institute;
                    info_institute = ds.getValue(Info_Institute.class);
                    InfoName.setText(info_institute.Name);
                    InfoEstd.setText(info_institute.Estd);
                    InfoEiin.setText(info_institute.Eiin);
                    InfoBoard.setText(info_institute.Board);
                    InfoVersion.setText(info_institute.Version_);
                    InfoHead.setText(info_institute.Head);
                    InfoDivision.setText(info_institute.Division);
                    InfoRoom.setText(info_institute.Room);
                    InfoSeat.setText(info_institute.Seat);
                    InfoTeacher.setText(info_institute.Teacher);
                    InfoAddress.setText(info_institute.Address);
                    InfoGpo.setText(info_institute.Gpo);
                    InfoPhone.setText(info_institute.Phone);
                    InfoMobile.setText(info_institute.Mobile);
                    InfoWebsite.setText(info_institute.Website);
                    InfoEmail.setText(info_institute.Email);



                    ValueName = InfoName.getText().toString().trim();
                    ValueEstd = InfoEstd.getText().toString().trim();
                    ValueEiin = InfoEiin.getText().toString().trim();
                    ValueBoard = InfoBoard.getText().toString().trim();
                    ValueVersion = InfoVersion.getText().toString().trim();
                    ValueHead = InfoHead.getText().toString().trim();
                    ValueDivision = InfoDivision.getText().toString().trim();
                    ValueRoom = InfoRoom.getText().toString().trim();
                    ValueSeat = InfoSeat.getText().toString().trim();
                    ValueTeacher = InfoTeacher.getText().toString().trim();
                    ValueAddress = InfoAddress.getText().toString().trim();
                    ValueGpo = InfoGpo.getText().toString().trim();
                    ValuePhone = InfoPhone.getText().toString().trim();
                    ValueMobile = InfoMobile.getText().toString().trim();
                    ValueWebsite = InfoWebsite.getText().toString().trim();
                    ValueEmail = InfoEmail.getText().toString().trim();

                    //progressDialog.cancel();
                    //dialog.cancel();
                    hud.dismiss();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                TastyToast.makeText(Details.this, "Error Loading!!!", TastyToast.LENGTH_SHORT, TastyToast.ERROR).show();
                //progressDialog.cancel();
                //dialog.cancel();
                hud.dismiss();
            }
        });





        InformationEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Details.this, Edit_Informations.class);
                startActivity(intent);
            }
        });

        loadImage();

    }

    public void loadImage()
    {
        final KProgressHUD hud = KProgressHUD.create(this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please Wait...")
                .setCancellable(false)
                .setAnimationSpeed(3)
                .setDimAmount(0.7f)
                .show();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        storageReference.child(user.getUid()).child("images/logo.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                //Toast.makeText(UserAccount.this, uri.toString(), Toast.LENGTH_SHORT).show();
                Picasso.with(Details.this).load(uri.toString()).into(IconInstitute);
                //mProgress.cancel();
                //dialog.cancel();
                hud.dismiss();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                //mProgress.cancel();
                //dialog.cancel();
                hud.dismiss();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out);
    }
}
