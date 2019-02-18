package bd.ac.kuet.ebb;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sdsmdg.tastytoast.TastyToast;

import static bd.ac.kuet.ebb.R.id.addSub;

public class QuestionDetails extends AppCompatActivity {


    private EditText QuestionNo, QuestionTittle, SubQuestionNo, Question, Mark;
    private Button Save, AddSubQ, AddNewQ, Preview, Edit, Finish;
    public String SQuestionNo, SQuestionTittle, SSubQuestionNo, SQuestion, SMark, ESQuestionTittle="", ESQuestion="";
    public static int lockA=7, lockB=395, keyA=223, keyB=395;

    public MultiQuestion multiQuestion = new MultiQuestion();

    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    private DatabaseReference mref;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_question_details);
        overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);



        init();

    }


    public void init()
    {
        QuestionNo = (EditText) findViewById(R.id.questionNo);
        QuestionTittle = (EditText) findViewById(R.id.questionTittle);
        SubQuestionNo = (EditText) findViewById(R.id.subQuestionNo);
        Question = (EditText) findViewById(R.id.question);
        Mark = (EditText) findViewById(R.id.mark);

        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();



        Save = (Button) findViewById(R.id.save);
        AddSubQ = (Button) findViewById(addSub);
        AddNewQ = (Button) findViewById(R.id.addNew);
        Preview = (Button) findViewById(R.id.preview);





        Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                SQuestionNo = QuestionNo.getText().toString();
                SQuestionTittle = QuestionTittle.getText().toString();
                SSubQuestionNo = SubQuestionNo.getText().toString();
                SQuestion = Question.getText().toString();
                SMark = Mark.getText().toString();
                mref = FirebaseDatabase.getInstance().getReference();
                mref.child("Questions").child(QuestionBasic.Scattagory + " " + QuestionBasic.Syear).child(QuestionBasic.Sboard).child(QuestionBasic.Ssubject).child(QuestionBasic.Sset).child("QuestionDetails").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        multiQuestion = new MultiQuestion();
                        for(DataSnapshot ds : dataSnapshot.getChildren())
                        {
                            multiQuestion = ds.getValue(MultiQuestion.class);
                            //Toast.makeText(getBaseContext(), multiQuestion.QNo.toString() + " " + SQuestionNo.toString(), Toast.LENGTH_SHORT).show();
                            if(multiQuestion.QNo.toString().trim().equals(SQuestionNo.toString().trim()))
                            {
                                //Toast.makeText(getBaseContext(), "matched", Toast.LENGTH_SHORT).show();
                                break;
                            }
                            else
                            {
                                //Toast.makeText(getBaseContext(), "unmatched QNo", Toast.LENGTH_SHORT).show();
                                multiQuestion = null;
                                multiQuestion = new MultiQuestion();
                            }
                        }
                        //Toast.makeText(getBaseContext(), "Triggered", Toast.LENGTH_SHORT).show();
                        saveQuestion();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }
        });


        AddSubQ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addSubQuestion();
            }
        });

        AddNewQ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNewQuestion();
            }
        });

        Preview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                previewQuestion();
            }
        });


    }


    public void saveQuestion()
    {
        if(QuestionNo.getText().toString().isEmpty() || SubQuestionNo.getText().toString().isEmpty() || Question.getText().toString().isEmpty() || Mark.getText().toString().isEmpty())
        {
            TastyToast.makeText(QuestionDetails.this, "Fill all the fields", TastyToast.LENGTH_SHORT, TastyToast.WARNING).show();
        }
        else
        {



            for(int i = 0; i < SQuestionTittle.length(); i++)
            {
                char x = SQuestionTittle.charAt(i);
                int ax = (int) x;
                int temp = ax;
                for(int j = 1; j < lockA; j++)
                {
                    temp *= ax;
                    temp = temp%lockB;
                }
                x = (char)temp;
                ESQuestionTittle += x;

            }
            SQuestionTittle = ESQuestionTittle;
            ESQuestionTittle = "";
            for(int i = 0; i < SQuestion.length(); i++)
            {
                char x = SQuestion.charAt(i);
                int ax = (int) x;
                int temp = ax;
                for(int j = 1; j < lockA; j++)
                {
                    temp *= ax;
                    temp = temp%lockB;
                }
                x = (char)temp;
                ESQuestion += x;
            }

            SQuestion = ESQuestion;
            ESQuestion="";

            //databaseReference.child("Questions").child(QuestionBasic.Scattagory + " " + QuestionBasic.Syear).child(QuestionBasic.Ssubject).child(QuestionBasic.Sset).child("QuestionDetails").child(SQuestionNo).child(SQuestionNo + "_").child("temp").setValue("1");
            //databaseReference.child("Questions").child(QuestionBasic.Scattagory + " " + QuestionBasic.Syear).child(QuestionBasic.Ssubject).child(QuestionBasic.Sset).child("QuestionDetails").child(SQuestionNo).child(SQuestionNo + "_").child("temp").setValue("0");



            SingleQuestion singleQuestion = new SingleQuestion(SSubQuestionNo, SQuestion, SMark);
            multiQuestion.QuestionTittle = SQuestionTittle;
            multiQuestion.QNo = SQuestionNo;
            multiQuestion.singleQuestions.add(singleQuestion);
            //Toast.makeText(getBaseContext(), multiQuestion.singleQuestions.size(), Toast.LENGTH_SHORT).show();
            //databaseReference.child("Questions").child(QuestionBasic.Scattagory + " " + QuestionBasic.Syear).child(QuestionBasic.Ssubject).child(QuestionBasic.Sset).child("QuestionDetails").child(SQuestionNo).child("QuestionTittle").child("QuestionTittle_").setValue(multiQuestion);
            databaseReference.child("Questions").child(QuestionBasic.Scattagory + " " + QuestionBasic.Syear).child(QuestionBasic.Sboard).child(QuestionBasic.Ssubject).child(QuestionBasic.Sset).child("QuestionDetails").child(SQuestionNo).setValue(multiQuestion);
            multiQuestion = null;
            Toast.makeText(getBaseContext(), "Saved", Toast.LENGTH_SHORT).show();
        }
    }


    public void addSubQuestion()
    {
        SubQuestionNo.setText("");
        Question.setText("");
        Mark.setText("");
    }

    public void addNewQuestion()
    {
        QuestionNo.setText("");
        QuestionTittle.setText("");
        SubQuestionNo.setText("");
        Question.setText("");
        Mark.setText("");
    }

    public void previewQuestion()
    {
        Intent intent = new Intent(getBaseContext(), Preview.class);
        startActivity(intent);
    }





    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out);
    }
}
