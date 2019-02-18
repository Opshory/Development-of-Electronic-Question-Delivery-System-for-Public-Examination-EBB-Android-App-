package bd.ac.kuet.ebb;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
import com.sdsmdg.tastytoast.TastyToast;

public class QuestionBasic extends AppCompatActivity {


    private Spinner Cattagory, Board, Set, Subject;
    private EditText Year;

    private String[] cattagorylist;
    private String[] Boardlist;
    private String[] Setlist;
    private String[] Subjectlist;
    private EditText Marks, Time, Note;
    private Button Save, Preview, Next;
    public static String Scattagory, Syear, Sboard, Sset, Smarks, Stime, Ssubject, Snote;


    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_question_basic);
        overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);
        init();
    }

    public void init() {
        Cattagory = (Spinner) findViewById(R.id.cattagory);
        Board = (Spinner) findViewById(R.id.board);
        Set = (Spinner) findViewById(R.id.set);
        Subject = (Spinner) findViewById(R.id.subject);
        Year = (EditText) findViewById(R.id.year);
        Marks = (EditText) findViewById(R.id.marks);
        Time = (EditText) findViewById(R.id.time);
        Note = (EditText) findViewById(R.id.note);
        Save = (Button) findViewById(R.id.save);
        Next = (Button) findViewById(R.id.next);
        Preview = (Button) findViewById(R.id.preview);

        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();


        this.cattagorylist = new String[]
                {
                    "HSC", "SSC", "JSC", "PSC"
                };
        ArrayAdapter<String>adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, cattagorylist);
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
                Sboard = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        this.Setlist = new String[]
                {
                        "Set A", "Set B", "Set C", "Set D", "Set E", "Set F"
                };
        ArrayAdapter<String>adapter3 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, Setlist);
        Set.setAdapter(adapter3);
        Set.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(QuestionBasic.this, parent.getItemAtPosition(position)+" selected", Toast.LENGTH_SHORT).show();
                Sset = parent.getItemAtPosition(position).toString();
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



        Preview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Year.getText().toString().isEmpty() || Marks.getText().toString().isEmpty() || Time.getText().toString().isEmpty())
                {
                    Toast.makeText(QuestionBasic.this, "Fill all the box!", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Syear = Year.getText().toString();
                    Smarks = Marks.getText().toString();
                    Stime = Time.getText().toString();
                    Snote = Note.getText().toString();
//                    String temp = Scattagory + " " + Syear + " " + Sboard + " " + Sset + " " + SquestionTille + " " + Smarks + " " + Stime;
//                    Toast.makeText(QuestionBasic.this, temp, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(QuestionBasic.this, Preview.class);
                    startActivity(intent);
                }
            }
        });


        Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveInformation();
            }
        });


        Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(Year.getText().toString().isEmpty() || Marks.getText().toString().isEmpty() || Time.getText().toString().isEmpty())
                {
                    Toast.makeText(QuestionBasic.this, "Fill all the box!", Toast.LENGTH_SHORT).show();
                }
                else {
                    saveInformation();
                    Intent intent = new Intent(QuestionBasic.this, QuestionDetails.class);
                    startActivity(intent);
                }
            }
        });

    }

    public void saveInformation()
    {
        if(Year.getText().toString().isEmpty() || Marks.getText().toString().isEmpty() || Time.getText().toString().isEmpty())
        {
            Toast.makeText(QuestionBasic.this, "Fill all the box!", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Syear = Year.getText().toString();
            Smarks = Marks.getText().toString();
            Stime = Time.getText().toString();
            Snote = Note.getText().toString();
            QBasic qBasic = new QBasic(Scattagory, Syear, Sboard, Sset, Smarks, Stime, Snote, "never");

            databaseReference.child("Questions").child(Scattagory + " " + Syear).child(Sboard).child(Ssubject).child(Sset).child("QuestionBasic").child("QuestionBasic_").setValue(qBasic);
            TastyToast.makeText(QuestionBasic.this, "Informations Saved!", TastyToast.LENGTH_SHORT, TastyToast.SUCCESS).show();
        }
    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out);
    }
}
