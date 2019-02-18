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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static bd.ac.kuet.ebb.QuestionBasic.Ssubject;
import static bd.ac.kuet.ebb.R.id.year;

public class ImStudent extends AppCompatActivity {

    private Spinner Cattagory, Board, Set, Subject;
    private EditText Year, StudentID, StudentPassword;
    private Button Go;

    private String[] cattagorylist;
    private String[] Boardlist;
    private String[] Setlist;
    private String[] Subjectlist;

    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    private boolean validUser = false;

    private Info_Student info_student = new Info_Student();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_im_student);
        overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);
        init();
    }


    public void init()
    {
        Cattagory = (Spinner) findViewById(R.id.cattagory);
        Board = (Spinner) findViewById(R.id.board);
        Set = (Spinner) findViewById(R.id.set);
        Subject = (Spinner) findViewById(R.id.subject);
        Year = (EditText) findViewById(year);
        StudentID = (EditText) findViewById(R.id.SUserID);
        StudentPassword = (EditText) findViewById(R.id.SPassword);
        Go = (Button) findViewById(R.id.go);
        firebaseAuth = FirebaseAuth.getInstance();
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
                QuestionBasic.Scattagory = parent.getItemAtPosition(position).toString();
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
                QuestionBasic.Sset = parent.getItemAtPosition(position).toString();
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
                QuestionBasic.Ssubject = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Year.getText().toString().isEmpty() || StudentID.getText().toString().trim().isEmpty() || StudentPassword.getText().toString().trim().isEmpty())
                {
                    Toast.makeText(getBaseContext(), "Some fields are empty", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    QuestionBasic.Syear = Year.getText().toString();
                    databaseReference.child("Students").child(QuestionBasic.Scattagory + " " + QuestionBasic.Syear).child(QuestionBasic.Sboard).child(Ssubject).child("StudentAccount").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for(DataSnapshot ds:dataSnapshot.getChildren())
                            {
                                info_student = ds.getValue(Info_Student.class);
                                //Toast.makeText(getBaseContext(), info_student.ID + "\n" + info_student.Password, Toast.LENGTH_SHORT).show();
                                if(info_student.ID.toString().equals(StudentID.getText().toString().trim()) && info_student.Password.toString().equals(StudentPassword.getText().toString().trim()))
                                {
                                    validUser = true;
                                    break;
                                }
                            }
                            if(validUser)
                            {
                                QuestionBasic.Syear = Year.getText().toString();
                                databaseReference.child("Questions").child(QuestionBasic.Scattagory + " " + QuestionBasic.Syear).child(QuestionBasic.Sboard).child(QuestionBasic.Ssubject).child(QuestionBasic.Sset).child("QuestionBasic").addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        QBasic qBasic = new QBasic();
                                        for(DataSnapshot ds:dataSnapshot.getChildren())
                                        {
                                            qBasic = ds.getValue(QBasic.class);
                                            QuestionBasic.Snote = qBasic.Note;
                                            QuestionBasic.Stime = qBasic.Time;
                                            QuestionBasic.Smarks = qBasic.Marks;
                                        }
                                        if(qBasic.isValid())
                                        {
                                            Intent intent = new Intent(ImStudent.this, Waiting.class);
                                            startActivity(intent);
                                        }
                                        else
                                        {
                                            Toast.makeText(getBaseContext(), "Question is not available!", Toast.LENGTH_SHORT).show();
                                        }

                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });
                            }
                            else
                            {
                                Toast.makeText(getBaseContext(), "UserID and Password don't match", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });






                }
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out);
    }
}
