package bd.ac.kuet.ebb;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.sdsmdg.tastytoast.TastyToast;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Notice_Details extends AppCompatActivity {

    public static int position;
    public static ArrayList<Notice> notice = new ArrayList<Notice>();
    private TextView TittleDetails;
    private TextView DescriptionDetails;
    private TextView DateTimeDetails;
    private ImageView ImageDetails;
    private Button PreNotice;
    private Button NextNotice;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    private StorageReference storageReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_notice__details);
        overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);

        init();

    }

    public void init()
    {

        TittleDetails = (TextView) findViewById(R.id.DetailsTittle);
        DescriptionDetails = (TextView) findViewById(R.id.DetailsDescription);
        ImageDetails = (ImageView) findViewById(R.id.DetailsImage);
        DateTimeDetails = (TextView) findViewById(R.id.DetailsDateTime);
        PreNotice = (Button) findViewById(R.id.previousNotice);
        NextNotice = (Button) findViewById(R.id.nextNotice);
        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        storageReference = FirebaseStorage.getInstance().getReference();




        position = Notice_Board.positionClicked;
        notice = Notice_Board.notices;
        //showToast();


        showNotice();







        PreNotice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(position > 0) {
                    position--;
                    showNotice();
                }
                else
                {
                    TastyToast.makeText(Notice_Details.this, "No more previous notice!!!", TastyToast.LENGTH_SHORT, TastyToast.WARNING).show();
                }
            }
        });


        NextNotice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(position < notice.size()-1) {
                    position++;
                    showNotice();
                }
                else
                {
                    TastyToast.makeText(Notice_Details.this, "No more next notice!!!", TastyToast.LENGTH_SHORT, TastyToast.WARNING).show();
                }
            }
        });



    }

    public void showNotice()
    {
        TittleDetails.setText(notice.get(position).Tittle);
        DescriptionDetails.setText(notice.get(position).Description);
        DateTimeDetails.setText("Posted: "+notice.get(position).Date+" @ "+notice.get(position).Time);
        loadImage();

    }

//    public void showToast()
//    {
//        Toast.makeText(Notice_Details.this, "Clicked "+position, Toast.LENGTH_SHORT).show();
//        Toast.makeText(Notice_Details.this, notice.get(position).Tittle, Toast.LENGTH_SHORT).show();
//        Toast.makeText(Notice_Details.this, notice.get(position).Description, Toast.LENGTH_SHORT).show();
//    }

    public void loadImage()
    {
//        final ProgressDialog mProgress = new ProgressDialog(this);
//        mProgress.setMessage("Please wait...");
//        mProgress.setCancelable(false);
//        mProgress.show();
//        final SpotsDialog dialog = new SpotsDialog(this, R.style.Custom);
//        dialog.setCancelable(false);
//        dialog.show();
        final KProgressHUD hud = KProgressHUD.create(this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setCancellable(false)
                .setAnimationSpeed(3)
                .setDimAmount(0.7f)
                .show();
        String dateTime = notice.get(position).Attactment;
        FirebaseUser user = firebaseAuth.getCurrentUser();
        storageReference.child("Notice_Board").child("images").child(dateTime).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                //Toast.makeText(UserAccount.this, uri.toString(), Toast.LENGTH_SHORT).show();
                Picasso.with(Notice_Details.this).load(uri.toString()).into(ImageDetails);
                //mProgress.cancel();
                //dialog.cancel();
                hud.dismiss();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Picasso.with(Notice_Details.this).load(R.drawable.noattactment).into(ImageDetails);
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
