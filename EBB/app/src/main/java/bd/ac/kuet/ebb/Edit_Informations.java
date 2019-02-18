package bd.ac.kuet.ebb;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.gitonway.lee.niftymodaldialogeffects.lib.NiftyDialogBuilder;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.sdsmdg.tastytoast.TastyToast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.gitonway.lee.niftymodaldialogeffects.lib.Effectstype.Flipv;

public class Edit_Informations extends AppCompatActivity {


    private static final int PICK_IMAGE_REQUEST = 1234;
    private Button SaveInfo;
    private Button CancelInfo;
    private CircleImageView IconInstitute;
    private Uri filePath;


    private EditText EditName;
    private EditText EditEstd;
    private EditText EditEiin;
    private EditText EditBoard;
    private EditText EditVersion;
    private EditText EditHead;
    private EditText EditDivision;
    private EditText EditRoom;
    private EditText EditSeat;
    private EditText EditTeacher;
    private EditText EditAddress;
    private EditText EditGpo;
    private EditText EditPhone;
    private EditText EditMobile;
    private EditText EditWebsite;
    private EditText EditEmail;

    private DatabaseReference databaseReference;
    private StorageReference storageReferance;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


        setContentView(R.layout.activity_edit__informations);
        overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);

        init();

    }


    public void init()
    {

        SaveInfo = (Button) findViewById(R.id.save_info);
        CancelInfo = (Button) findViewById(R.id.cancel_info);
        IconInstitute = (CircleImageView) findViewById(R.id.institute_logo);


        EditName = (EditText) findViewById(R.id.edit_name);
        EditEstd = (EditText) findViewById(R.id.edit_estd);
        EditEiin = (EditText) findViewById(R.id.edit_eiin);
        EditBoard = (EditText) findViewById(R.id.edit_board);
        EditVersion = (EditText) findViewById(R.id.edit_version);
        EditHead = (EditText) findViewById(R.id.edit_head);
        EditDivision = (EditText) findViewById(R.id.edit_division);
        EditRoom = (EditText) findViewById(R.id.edit_room);
        EditSeat = (EditText) findViewById(R.id.edit_seat);
        EditTeacher = (EditText) findViewById(R.id.edit_teacher);
        EditAddress = (EditText) findViewById(R.id.edit_address);
        EditGpo = (EditText) findViewById(R.id.edit_gpo);
        EditPhone = (EditText) findViewById(R.id.edit_phone);
        EditMobile = (EditText) findViewById(R.id.edit_mobile);
        EditWebsite = (EditText) findViewById(R.id.edit_website);
        EditEmail = (EditText) findViewById(R.id.edit_email);

        databaseReference = FirebaseDatabase.getInstance().getReference();
        storageReferance = FirebaseStorage.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);


        EditName.setText(Details.ValueName);
        EditEstd.setText(Details.ValueEstd);
        EditEiin.setText(Details.ValueEiin);
        EditBoard.setText(Details.ValueBoard);
        EditVersion.setText(Details.ValueVersion);
        EditHead.setText(Details.ValueHead);
        EditDivision.setText(Details.ValueDivision);
        EditRoom.setText(Details.ValueRoom);
        EditSeat.setText(Details.ValueSeat);
        EditTeacher.setText(Details.ValueTeacher);
        EditAddress.setText(Details.ValueAddress);
        EditGpo.setText(Details.ValueGpo);
        EditPhone.setText(Details.ValuePhone);
        EditMobile.setText(Details.ValueMobile);
        EditWebsite.setText(Details.ValueWebsite);
        EditEmail.setText(Details.ValueEmail);



        SaveInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveInformation();
            }
        });


        CancelInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Edit_Informations.this, Details.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });


        IconInstitute.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {


                final NiftyDialogBuilder dialogBuilder=NiftyDialogBuilder.getInstance(Edit_Informations.this);

                dialogBuilder                     //.withTitle(null)  no title
                        .withTitleColor("#ff7f23")                                  //def
                        .withDividerColor("#11000000")                              //def
                        .withMessage("Change Logo?\n\n")                     //.withMessage(null)  no Msg
                        .withMessageColor("#4e4e4e")                              //def  | withMessageColor(int resid)
                        .withDialogColor("#f5f5f5")                               //def  | withDialogColor(int resid)
                        .withDuration(500)                                          //def
                        .withEffect(Flipv)
                        .withIcon(R.drawable.logo)
                        .withButton2Text("YES")//def Effectstype.Slidetop
                        .withButton1Text("NO")                                      //def gone                     //def gone
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
                                ChoseImage();
                            }
                        })
                        .show();


//                final AlertDialog.Builder builder = new AlertDialog.Builder(Edit_Informations.this);
//                builder.setMessage("Change Logo?");
//                builder.setCancelable(true);
//                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.cancel();
//                    }
//                });
//                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//
//                        //Toast.makeText(Edit_Informations.this, "Logo changing", Toast.LENGTH_SHORT).show();
//                        ChoseImage();
//                    }
//                });
//                AlertDialog alertDialog = builder.create();
//                alertDialog.show();
                return true;
            }
        });



    }

    public void saveInformation()
    {
//        progressDialog.setMessage("Saving Changes...");
//        progressDialog.show();
        final KProgressHUD hud = KProgressHUD.create(this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Saving Changes...")
                .setCancellable(false)
                .setAnimationSpeed(4)
                .setDimAmount(0.7f)
                .show();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

        String Name = EditName.getText().toString().trim();
        String Estd = EditEstd.getText().toString().trim();
        String Eiin = EditEiin.getText().toString().trim();
        String Board = EditBoard.getText().toString().trim();
        String Version_ = EditVersion.getText().toString().trim();
        String Head = EditHead.getText().toString().trim();
        String Division = EditDivision.getText().toString().trim();
        String Room = EditRoom.getText().toString().trim();
        String Seat = EditSeat.getText().toString().trim();
        String Teacher = EditTeacher.getText().toString().trim();
        String Address = EditAddress.getText().toString().trim();
        String Gpo = EditGpo.getText().toString().trim();
        String Phone = EditPhone.getText().toString().trim();
        String Mobile = EditMobile.getText().toString().trim();
        String Website = EditWebsite.getText().toString().trim();
        String Email = EditEmail.getText().toString().trim();



        Info_Institute info_institute = new Info_Institute(Name, Estd, Eiin, Board, Version_, Head, Division, Seat, Room, Address, Teacher, Gpo, Phone, Mobile, Website, Email);

        try
        {
            databaseReference.child(firebaseUser.getUid()).child("Institute_info").child("Institute_info_").setValue(info_institute);
            //progressDialog.cancel();
            hud.dismiss();
            TastyToast.makeText(Edit_Informations.this, "Information Saved", TastyToast.LENGTH_SHORT, TastyToast.SUCCESS).show();
            Intent intent = new Intent(Edit_Informations.this, Details.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);

        }
        catch (Exception e)
        {
            //progressDialog.cancel();
            hud.dismiss();
            TastyToast.makeText(Edit_Informations.this, "Network Error!!!", TastyToast.LENGTH_SHORT, TastyToast.ERROR).show();
        }



    }



    public void ChoseImage()
    {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(intent.ACTION_GET_CONTENT);
        startActivityForResult(intent.createChooser(intent, "Select an image"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null)
        {
            filePath = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                IconInstitute.setImageBitmap(bitmap);
                uploadImage();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public void uploadImage() throws IOException {
        if(filePath != null) {
//            progressDialog.setTitle("Uploading...");
//            progressDialog.setCancelable(false);
//            progressDialog.show();
//            FirebaseUser user = firebaseAuth.getCurrentUser();
//            StorageReference myRef = storageReferance.child(user.getUid()).child("images/logo.jpg");
//
//            myRef.putFile(filePath)
//                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                        @Override
//                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                            progressDialog.cancel();
//                            //Toast.makeText(Edit_Informations.this, "Uploading Successful!", Toast.LENGTH_SHORT).show();
//                        }
//                    })
//                    .addOnFailureListener(new OnFailureListener() {
//                        @Override
//                        public void onFailure(@NonNull Exception exception) {
//
//                        }
//                    }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
//                        @Override
//                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
//                            double progress = (100.0 * taskSnapshot.getBytesTransferred())/taskSnapshot.getTotalByteCount();
//                            progressDialog.setMessage((int)progress + "% Uploading...");
//                        }
//                    });




//            progressDialog.setTitle("Uploading...");
//            progressDialog.setCancelable(false);
//            progressDialog.show();

            final KProgressHUD hud = KProgressHUD.create(this)
                    .setStyle(KProgressHUD.Style.BAR_DETERMINATE)
                    .setLabel("Uploading...")
                    .setMaxProgress(100)
                    .setCancellable(false)
                    .show();


            FirebaseUser user = firebaseAuth.getCurrentUser();

            StorageReference childRef2 = storageReferance.child(user.getUid()).child("images/logo.jpg");
            Bitmap bmp = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.JPEG, 10, baos);
            byte[] data = baos.toByteArray();
            //uploading the image
            UploadTask uploadTask2 = childRef2.putBytes(data);
            uploadTask2.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    //progressDialog.cancel();
                    hud.dismiss();
                    Intent intent = new Intent(Edit_Informations.this, Details.class);
                    intent.setFlags(intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    //Toast.makeText(Edit_Informations.this, "Upload successful", Toast.LENGTH_LONG).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    double progress = (100.0 * taskSnapshot.getBytesTransferred())/taskSnapshot.getTotalByteCount();
                    //progressDialog.setMessage((int)progress + "% Uploading...");
                    hud.setProgress((int)progress);
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
