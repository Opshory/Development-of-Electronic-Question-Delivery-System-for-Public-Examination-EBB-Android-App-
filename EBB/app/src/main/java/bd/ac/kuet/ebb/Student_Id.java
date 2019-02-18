package bd.ac.kuet.ebb;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import static bd.ac.kuet.ebb.QuestionBasic.Scattagory;
import static bd.ac.kuet.ebb.QuestionBasic.Ssubject;

public class Student_Id extends AppCompatActivity {


    private EditText NoOfStudent;
    private Button Submit;
    private EditText Year;
    private Spinner Cattagory, Board, Subject;
    private String[] cattagorylist;
    private String[] Boardlist;
    private String[] Subjectlist;

    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_student__id);
        overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);


        init();

    }

    public void init()
    {
        NoOfStudent = (EditText) findViewById(R.id.noOfStudent);
        Submit = (Button) findViewById(R.id.submit);
        Cattagory = (Spinner) findViewById(R.id.cattagory);
        Board = (Spinner) findViewById(R.id.board);
        Subject = (Spinner) findViewById(R.id.subject);
        Year = (EditText) findViewById(R.id.year);
        databaseReference = FirebaseDatabase.getInstance().getReference();


        this.cattagorylist = new String[]
                {
                        "HSC", "SSC", "JSC", "PSC"
                };
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, cattagorylist);
        Cattagory.setAdapter(adapter1);
        Cattagory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(QuestionBasic.this, parent.getItemAtPosition(position)+" selected", Toast.LENGTH_SHORT).show();
                Scattagory = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        this.Boardlist = new String[]
                {
                        "Rajshahi", "Dhaka", "Jessore", "Dinajpur", "Sylhet", "Comilla", "Barishal", "Chittagong"
                };
        ArrayAdapter<String>adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, Boardlist);
        Board.setAdapter(adapter2);
        Board.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(QuestionBasic.this, parent.getItemAtPosition(position)+" selected", Toast.LENGTH_SHORT).show();
                QuestionBasic.Sboard = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });




        this.Subjectlist = new String[]
                {
                        "English", "Bangla"
                };
        ArrayAdapter<String>adapter4 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, Subjectlist);
        Subject.setAdapter(adapter4);
        Subject.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(QuestionBasic.this, parent.getItemAtPosition(position)+" selected", Toast.LENGTH_SHORT).show();
                Ssubject = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(NoOfStudent.getText().toString().trim().isEmpty() || Year.getText().toString().trim().isEmpty())
                {
                    Toast.makeText(getBaseContext(), "Fill the fields", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    QuestionBasic.Syear = Year.getText().toString().trim();
                    requestSubmitted();
                }
            }
        });

    }

    public void requestSubmitted()
    {
        boolean possible = true;
        String StudentNo = NoOfStudent.getText().toString().trim();
        int n = 0;
        try
        {
            n = Integer.parseInt(StudentNo);
        }
        catch (Exception e)
        {
            //nothing
        }
        if(n > 10000)
        {
            possible = false;
            Toast.makeText(Student_Id.this, "Number Too Large", Toast.LENGTH_SHORT).show();
        }
        //Toast.makeText(Student_Id.this, String.valueOf(n), Toast.LENGTH_SHORT).show();
        char temp[] = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz".toCharArray();
            Random random = new Random();
            ArrayList<String> UserID = new ArrayList<String>();
            ArrayList<String> UserPass = new ArrayList<String>();
            if(n != 0 && possible)
            {

                for(int i = 0; i < n; i++)
                {
                    String ID = new String();
                    for(int j = 0; j < 10; j++)
                    {
                        int x = random.nextInt(61) + 0;
                        ID += temp[x];
                    }
                    if(!UserID.contains(ID))
                    {
                        UserID.add(ID);
                    }
                    else
                    {
                        i--;
                    }
                }

                for(int i = 0; i < n; i++)
                {
                    String Password = new String();
                    for(int j = 0; j < 10; j++)
                    {
                        int x = random.nextInt(9) + 0;
                        Password += temp[x];
                    }
                    if(!UserID.contains(Password))
                    {
                        UserPass.add(Password);
                    }
                    else
                    {
                        i--;
                    }
                }
                String text = new String();
                for(int i = 0; i < n; i++) {
                    text += "UserID: "+UserID.get(i)+"        "+"Password: "+UserPass.get(i)+"\n\n";
                    Info_Student info_student = new Info_Student();
                    info_student.ID = UserID.get(i).toString().trim();
                    info_student.Password = UserPass.get(i).toString().trim();
                    databaseReference.child("Students").child(QuestionBasic.Scattagory + " " + QuestionBasic.Syear).child(QuestionBasic.Sboard).child(Ssubject).child("StudentAccount").push().setValue(info_student);
                }
            Document doc = new Document();

            try {
                String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/EBBD";

                File dir = new File(path);
                if(!dir.exists())
                    dir.mkdirs();

                File file = new File(dir, QuestionBasic.Scattagory + " "+QuestionBasic.Syear+ " "+ QuestionBasic.Ssubject+ " StudentIDPassword.pdf");
                FileOutputStream fOut = new FileOutputStream(file);

                PdfWriter.getInstance(doc, fOut);

                //open the document
                doc.open();

                Paragraph p1 = new Paragraph(text);
                p1.setAlignment(Paragraph.ALIGN_LEFT); 

                //add paragraph to document
                doc.add(p1);

            } catch (DocumentException de) {
                Log.e("PDFCreator", "DocumentException:" + de);
            } catch (IOException e) {
                Log.e("PDFCreator", "ioException:" + e);
            }
            finally {
                doc.close();
            }
            viewPdf(QuestionBasic.Scattagory + " "+QuestionBasic.Syear+ " "+ QuestionBasic.Ssubject+ " StudentIDPassword.pdf", "EBBD");

        }
        else
        {
            Toast.makeText(Student_Id.this, "Try Again", Toast.LENGTH_SHORT).show();
        }
        

    }

    private void viewPdf(String file, String directory) {

        File pdfFile = new File(Environment.getExternalStorageDirectory() + "/" + directory + "/" + file);
        Uri path = Uri.fromFile(pdfFile);

        // Setting the intent for pdf reader
        Intent pdfIntent = new Intent(Intent.ACTION_VIEW);
        pdfIntent.setDataAndType(path, "application/pdf");
        pdfIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        try {
            startActivity(pdfIntent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(Student_Id.this, "Can't read pdf file", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out);
    }
}
