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
import android.widget.ImageView;

import com.gitonway.lee.niftymodaldialogeffects.lib.NiftyDialogBuilder;
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
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.sdsmdg.tastytoast.TastyToast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static bd.ac.kuet.ebb.R.id.clearAll;
import static com.gitonway.lee.niftymodaldialogeffects.lib.Effectstype.Shake;

public class Post_Notice extends AppCompatActivity {


    private static final int PICK_IMAGE_REQUEST = 1234;
    private Button Post;
    private Button Attach;
    private Button ClearAll;
    private EditText noticeTittle;
    private EditText noticeDescription;
    private ImageView NoticeImage;
    private Uri filePath;
    private String date;
    private String time;
    private String Attachment;
    private String AttachValue;


    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    private StorageReference storageReference;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


        setContentView(R.layout.activity_post__notice);
        overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);

        init();

    }



    public void init()
    {
        Post = (Button) findViewById(R.id.PostNotice);
        Attach = (Button) findViewById(R.id.Attach);
        noticeDescription = (EditText) findViewById(R.id.noticeDescription);
        noticeTittle = (EditText) findViewById(R.id.noticeTittle);
        NoticeImage = (ImageView) findViewById(R.id.noticeAttachment);
        ClearAll = (Button) findViewById(clearAll);

        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        storageReference = FirebaseStorage.getInstance().getReference();
        progressDialog = new ProgressDialog(this);



        date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        time = new SimpleDateFormat("HH:mm:ss").format(new Date());

        Attach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImagte();
            }
        });


        Post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //uploadImage();
                try {
                    postNotice();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        ClearAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                final NiftyDialogBuilder dialogBuilder=NiftyDialogBuilder.getInstance(Post_Notice.this);

                dialogBuilder                     //.withTitle(null)  no title
                        .withTitleColor("#ff7f23")                                  //def
                        .withDividerColor("#11000000")                              //def
                        .withMessage("Are You Sure?\n\n")                     //.withMessage(null)  no Msg
                        .withMessageColor("#ff7f23")                              //def  | withMessageColor(int resid)
                        .withDialogColor("#454545")                               //def  | withDialogColor(int resid)
                        .withDuration(200)                                          //def
                        .withEffect(Shake)
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
                                clearAllNotice();
                                TastyToast.makeText(Post_Notice.this, "All notices have been cleared", TastyToast.LENGTH_SHORT, TastyToast.SUCCESS).show();
                            }
                        })
                        .show();



//                final AlertDialog.Builder builder = new AlertDialog.Builder(Post_Notice.this);
//                builder.setMessage("Are you sure?");
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
//                        TastyToast.makeText(Post_Notice.this, "All notices have been cleared", TastyToast.LENGTH_SHORT, TastyToast.SUCCESS).show();
//                        clearAllNotice();
//                    }
//                });
//                AlertDialog alertDialog = builder.create();
//                alertDialog.show();
            }
        });



    }



    public void clearAllNotice()
    {


            databaseReference.child("Notice_Board").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for(DataSnapshot ds : dataSnapshot.getChildren())
                    {
                        Notice notice;
                        notice = ds.getValue(Notice.class);
                        AttachValue = notice.Attactment.toString().trim();
                        //Toast.makeText(Post_Notice.this, AttachValue, Toast.LENGTH_SHORT).show();
                        //Toast.makeText(Post_Notice.this, clearAllPressed, Toast.LENGTH_SHORT).show();
                        storageReference.child("Notice_Board").child("images").child(AttachValue).delete();
                    }
                    databaseReference.child("Notice_Board").removeValue();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
    }




    public void postNotice() throws IOException {
//        progressDialog.setMessage("Posting...");
//        progressDialog.setCancelable(false);
//        progressDialog.show();

        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

//        Toast.makeText(Post_Notice.this, date, Toast.LENGTH_SHORT).show();
//        Toast.makeText(Post_Notice.this, time, Toast.LENGTH_SHORT).show();

        String Tittle = noticeTittle.getText().toString().trim();
        String Description = noticeDescription.getText().toString().trim();
        if(Tittle.isEmpty() || Description.isEmpty())
        {
            //progressDialog.cancel();
            TastyToast.makeText(Post_Notice.this, "Post Description with a Tittle", TastyToast.LENGTH_SHORT, TastyToast.WARNING).show();
        }

        else {

            Attachment = date+" "+time;
            Notice notice = new Notice(Tittle, Description, date, time, Attachment);

            try {
                uploadImage();
                databaseReference.child("Notice_Board").child(date + " " + time).setValue(notice);
                //progressDialog.cancel();
                TastyToast.makeText(Post_Notice.this, "Notice Posted", TastyToast.LENGTH_SHORT, TastyToast.SUCCESS).show();
                Intent intent = new Intent(Post_Notice.this, Home.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);

            } catch (Exception e) {
               // progressDialog.cancel();
                TastyToast.makeText(Post_Notice.this, "Network Error!!!", TastyToast.LENGTH_SHORT, TastyToast.ERROR).show();
            }
        }
    }

    public void chooseImagte()
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
                NoticeImage.setImageBitmap(bitmap);
                //uploadImage();
            } catch (IOException e) {

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


            StorageReference childRef2 = storageReference.child("Notice_Board").child("images/"+date+" "+time);
            Bitmap bmp = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.JPEG, 50, baos);
            byte[] data = baos.toByteArray();
            //uploading the image
            UploadTask uploadTask2 = childRef2.putBytes(data);
            uploadTask2.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    progressDialog.cancel();
                    //Toast.makeText(Edit_Informations.this, "Upload successful", Toast.LENGTH_LONG).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
//                    double progress = (100.0 * taskSnapshot.getBytesTransferred())/taskSnapshot.getTotalByteCount();
//                    progressDialog.setMessage((int)progress + "% Uploading...");
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
