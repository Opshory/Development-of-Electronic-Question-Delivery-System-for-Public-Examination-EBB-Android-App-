package bd.ac.kuet.ebb;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Preview extends AppCompatActivity {



    private TextView Set, Tittle, Board, Marks, Time, Subject, Note;

    private ListView QuestionList;
    public static ArrayList<QuestionSet> questionSets = new ArrayList<QuestionSet>();
    public static ArrayList<SingleQuestion> singleQuestions = new ArrayList<SingleQuestion>();
    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;
    private MultiQuestion multiQuestion;
    private QuestionListAdapter questionListAdapter;
    private SingleQuestion singleQuestion;
    private String Header, QuestionSet;
    private LinearLayout HideShow, ListHeader;
    private ImageView HideIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_preview);
        overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);
        init();
    }

    public void init()
    {
        Set = (TextView) findViewById(R.id.set);
        Tittle = (TextView) findViewById(R.id.Tittle);
        Board = (TextView) findViewById(R.id.board);
        Marks = (TextView) findViewById(R.id.marks);
        Time = (TextView) findViewById(R.id.time);
        Subject = (TextView) findViewById(R.id.subject);
        Note = (TextView) findViewById(R.id.note);
        QuestionList = (ListView) findViewById(R.id.questionList);
        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        HideShow = (LinearLayout) findViewById(R.id.hideShow);
        ListHeader = (LinearLayout) findViewById(R.id.listHeader);
        HideIcon = (ImageView) findViewById(R.id.hideIcon);

        HideIcon.setBackgroundResource(R.drawable.uparrow);


        HideShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ListHeader.getVisibility() == View.VISIBLE)
                {
                    //ListHeader.animate().translationY(-500);
                    HideIcon.setBackgroundResource(R.drawable.downarrow);
                    ListHeader.setVisibility(View.GONE);
                }
                else
                {
                    //ListHeader.animate().translationY(0);
                    HideIcon.setBackgroundResource(R.drawable.uparrow);
                    ListHeader.setVisibility(View.VISIBLE);
                }
            }
        });





        Subject.setText("Subject: " + QuestionBasic.Ssubject);
        Set.setText(QuestionBasic.Sset);
        if(!QuestionBasic.Snote.isEmpty())
        {
            Note.setText("[Note: " + QuestionBasic.Snote + "]");
        }
        Tittle.setText(QuestionBasic.Scattagory + " Examination " + QuestionBasic.Syear);
        Board.setText(QuestionBasic.Sboard + " Board");
        Marks.setText("Full Marks: "  + QuestionBasic.Smarks);
        Time.setText("Time: " + QuestionBasic.Stime + " Minutes");

        //Toast.makeText(getBaseContext(), Header + "\n" + QuestionSet, Toast.LENGTH_SHORT).show();


        databaseReference.child("Questions").child(QuestionBasic.Scattagory + " " + QuestionBasic.Syear).child(QuestionBasic.Sboard).child(QuestionBasic.Ssubject).child(QuestionBasic.Sset).child("QuestionDetails").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                questionSets = new ArrayList<QuestionSet>();
                for(DataSnapshot ds:dataSnapshot.getChildren())
                {

                    Header = "";
                    QuestionSet = "";
                    singleQuestions = new ArrayList<SingleQuestion>();
                    multiQuestion = ds.getValue(MultiQuestion.class);


                    String Tittle;
                    String DTittle = "";
                    Tittle = multiQuestion.QuestionTittle;
                    for(int j = 0; j < Tittle.length(); j++)
                    {
                        char x = Tittle.charAt(j);
                        int ax = (int) x;
                        int temp = ax;
                        for(int k = 1; k < QuestionDetails.keyA; k++)
                        {
                            temp *= ax;
                            temp = temp%QuestionDetails.keyB;
                        }
                        x = (char)temp;
                        DTittle += x;

                    }
                    multiQuestion.QuestionTittle = DTittle;




                    Header += (multiQuestion.QNo + ") " + multiQuestion.QuestionTittle);
                    singleQuestions = multiQuestion.singleQuestions;
                    for(int i = 0; i < singleQuestions.size(); i++)
                    {
                        singleQuestion = singleQuestions.get(i);




                        String Ques;
                        String DQues = "";
                        Ques = singleQuestion.Question;
                        for(int j = 0; j < Ques.length(); j++)
                        {
                            char x = Ques.charAt(j);
                            int ax = (int) x;
                            int temp = ax;
                            for(int k = 1; k < QuestionDetails.keyA; k++)
                            {
                                temp *= ax;
                                temp = temp%QuestionDetails.keyB;
                            }
                            x = (char)temp;
                            DQues += x;

                        }
                        singleQuestion.Question = DQues;





                        QuestionSet += (singleQuestion.SubQNo + ") " + singleQuestion.Question + " (" + singleQuestion.Mark + ")\n");
                    }
                    questionSets.add(new QuestionSet(Header, QuestionSet));
                    //Toast.makeText(getBaseContext(), Header + "\n" + QuestionSet, Toast.LENGTH_SHORT).show();
                }

                generateQuestion();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }


    public void generateQuestion()
    {
        questionListAdapter = new QuestionListAdapter(Preview.this, questionSets);
        QuestionList.setAdapter(questionListAdapter);
        QuestionList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });

    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out);
    }
}
