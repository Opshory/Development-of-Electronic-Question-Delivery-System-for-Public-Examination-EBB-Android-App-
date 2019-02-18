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
import com.sdsmdg.tastytoast.TastyToast;

import static bd.ac.kuet.ebb.QuestionBasic.Ssubject;
import static bd.ac.kuet.ebb.R.id.year;

public class Publish_Question extends AppCompatActivity {



    private Spinner Cattagory, Board, Set, Subject;
    private EditText Year;
    private Button Preview, Publish, HaltPublishing;
    private Button DayUp, MonthUp, YearUp, DayDown, MonthDown, YearDown, HourUp, HourDown, MinuteUp, MinuteDown, SecondUp, SecondDown;
    private Button Finish;
    private EditText Day, Month, Yr, Hour, Minute, Second;



    private String[] cattagorylist;
    private String[] Boardlist;
    private String[] Setlist;
    private String[] Subjectlist;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_publish__question);
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
        Preview = (Button) findViewById(R.id.preview);
        Publish = (Button) findViewById(R.id.publish);
        HaltPublishing = (Button) findViewById(R.id.haltpublishing);
        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        DayUp = (Button) findViewById(R.id.dayup);
        DayDown = (Button) findViewById(R.id.daydown);
        MonthUp = (Button) findViewById(R.id.monthup);
        MonthDown = (Button) findViewById(R.id.monthdown);
        YearUp = (Button) findViewById(R.id.yearup);
        YearDown = (Button) findViewById(R.id.yeardown);
        HourUp = (Button) findViewById(R.id.hourup);
        HourDown = (Button) findViewById(R.id.hourdown);
        MinuteUp = (Button) findViewById(R.id.minuteup);
        MinuteDown = (Button) findViewById(R.id.minutedown);
        SecondUp = (Button) findViewById(R.id.secondup);
        SecondDown = (Button) findViewById(R.id.seconddown);
        Day = (EditText) findViewById(R.id.day);
        Month = (EditText) findViewById(R.id.month);
        Yr = (EditText) findViewById(R.id.yr);
        Hour = (EditText) findViewById(R.id.hour);
        Minute = (EditText) findViewById(R.id.minute);
        Second = (EditText) findViewById(R.id.second);
        Finish = (Button) findViewById(R.id.finishExam);
        Day.setText("1");
        Month.setText("1");
        Yr.setText("2017");
        Hour.setText("00");
        Minute.setText("00");
        Second.setText("00");





        DayUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int x = Integer.parseInt(Day.getText().toString());
                if(x <= 30)
                {
                    x++;
                }
                else
                {
                    x = 1;
                }
                Day.setText(String.valueOf(x));
            }
        });
        DayDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int x = Integer.parseInt(Day.getText().toString());
                if(x >= 2)
                {
                    x--;
                }
                else
                {
                    x = 31;
                }
                Day.setText(String.valueOf(x));
            }
        });
        MonthUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int x = Integer.parseInt(Month.getText().toString());
                if(x <= 11)
                {
                    x++;
                }
                else
                {
                    x = 1;
                }
                Month.setText(String.valueOf(x));
            }
        });
        MonthDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int x = Integer.parseInt(Month.getText().toString());
                if(x >= 2)
                {
                    x--;
                }
                else
                {
                    x = 12;
                }
                Month.setText(String.valueOf(x));
            }
        });
        YearUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int x = Integer.parseInt(Yr.getText().toString());
                if(x <= 9998)
                {
                    x++;
                }
                else
                {
                    x = 2017;
                }
                Yr.setText(String.valueOf(x));
            }
        });
        YearDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int x = Integer.parseInt(Yr.getText().toString());
                if(x >= 2018)
                {
                    x--;
                }
                else
                {
                    x = 9999;
                }
                Yr.setText(String.valueOf(x));
            }
        });
        HourUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int x = Integer.parseInt(Hour.getText().toString());
                if(x <= 22)
                {
                    x++;
                }
                else
                {
                    x = 0;
                }
                Hour.setText(String.valueOf(x));
            }
        });
        HourDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int x = Integer.parseInt(Hour.getText().toString());
                if(x >= 1)
                {
                    x--;
                }
                else
                {
                    x = 23;
                }
                Hour.setText(String.valueOf(x));
            }
        });
        MinuteUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int x = Integer.parseInt(Minute.getText().toString());
                if(x <= 58)
                {
                    x++;
                }
                else
                {
                    x = 0;
                }
                Minute.setText(String.valueOf(x));
            }
        });
        MinuteDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int x = Integer.parseInt(Minute.getText().toString());
                if(x >= 1)
                {
                    x--;
                }
                else
                {
                    x = 59;
                }
                Minute.setText(String.valueOf(x));
            }
        });
        SecondUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int x = Integer.parseInt(Second.getText().toString());
                if(x <= 58)
                {
                    x++;
                }
                else
                {
                    x = 0;
                }
                Second.setText(String.valueOf(x));
            }
        });
        SecondDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int x = Integer.parseInt(Second.getText().toString());
                if(x >= 1)
                {
                    x--;
                }
                else
                {
                    x = 59;
                }
                Second.setText(String.valueOf(x));
            }
        });








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



        Preview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String year = Year.getText().toString();
                if(year.isEmpty())
                {
                    Toast.makeText(getBaseContext(), "Year is Empty!", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    QuestionBasic.Syear = year;
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
                                Intent intent = new Intent(Publish_Question.this, Preview.class);
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
            }
        });


        Publish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(Year.getText().toString().isEmpty())
                {
                    Toast.makeText(getBaseContext(), "Year is Empty!", Toast.LENGTH_SHORT).show();
                }
                else {
                    QuestionBasic.Syear = Year.getText().toString();
                    String dd, mm, yy, hh, mi, ss, dt;
                    dd = String.format("%02d", Integer.parseInt(Day.getText().toString()));
                    mm = String.format("%02d", Integer.parseInt(Month.getText().toString()));
                    yy = String.format("%04d", Integer.parseInt(Yr.getText().toString()));
                    hh = String.format("%02d", Integer.parseInt(Hour.getText().toString()));
                    mi = String.format("%02d", Integer.parseInt(Minute.getText().toString()));
                    ss = String.format("%02d", Integer.parseInt(Second.getText().toString()));
                    dt = dd + "/" + mm + "/" + yy + " " + hh + ":" + mi + ":" + ss;

                    databaseReference.child("Questions").child(QuestionBasic.Scattagory + " " + QuestionBasic.Syear).child(QuestionBasic.Sboard).child(QuestionBasic.Ssubject).child(QuestionBasic.Sset).child("QuestionBasic").child("QuestionBasic_").child("PublishDate").setValue(dt);

                    TastyToast.makeText(getBaseContext(), "Question Published", TastyToast.LENGTH_SHORT, TastyToast.SUCCESS).show();
                }
            }
        });

        HaltPublishing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Year.getText().toString().isEmpty())
                {
                    Toast.makeText(getBaseContext(), "Year is Empty!", Toast.LENGTH_SHORT).show();
                }
                else {
                    QuestionBasic.Syear = Year.getText().toString();

                    databaseReference.child("Questions").child(QuestionBasic.Scattagory + " " + QuestionBasic.Syear).child(QuestionBasic.Sboard).child(QuestionBasic.Ssubject).child(QuestionBasic.Sset).child("QuestionBasic").child("QuestionBasic_").child("PublishDate").setValue("never");

                    TastyToast.makeText(getBaseContext(), "Publish Halted", TastyToast.LENGTH_SHORT, TastyToast.SUCCESS).show();
                }
            }
        });

        Finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Year.getText().toString().isEmpty())
                {
                    Toast.makeText(getBaseContext(), "Year is Empty!", Toast.LENGTH_SHORT).show();
                }
                else {
                    QuestionBasic.Syear = Year.getText().toString().trim();
                    databaseReference.child("Students").child(QuestionBasic.Scattagory + " " + QuestionBasic.Syear).child(QuestionBasic.Sboard).child(Ssubject).removeValue();
                    TastyToast.makeText(getBaseContext(), "All student is removed successfully", TastyToast.LENGTH_SHORT, TastyToast.SUCCESS).show();
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
