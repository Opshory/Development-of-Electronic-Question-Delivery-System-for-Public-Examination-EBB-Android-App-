package bd.ac.kuet.ebb;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class Waiting extends AppCompatActivity {

    private TextView TimeLabel;
    private DatabaseReference databaseReference;
    static final String DATEFORMAT = "dd/MM/yyyy HH:mm:ss";
    private QBasic qBasic1 = new QBasic();
    private Thread t;
    private Button GoPreview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_waiting);
        overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);
        init();
    }

    public void init()
    {
        TimeLabel = (TextView) findViewById(R.id.timelabel);
        Typeface tf = Typeface.createFromAsset(getAssets(),"fonts/digital-7.ttf");
        TimeLabel.setTypeface(tf);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        GoPreview = (Button) findViewById(R.id.gopreview);

        databaseReference.child("Questions").child(QuestionBasic.Scattagory + " " + QuestionBasic.Syear).child(QuestionBasic.Sboard).child(QuestionBasic.Ssubject).child(QuestionBasic.Sset).child("QuestionBasic").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                QBasic qBasic = new QBasic();
                for(DataSnapshot ds:dataSnapshot.getChildren())
                {
                    qBasic1 = ds.getValue(QBasic.class);
                    if(!qBasic1.PublishDate.equals("never")) {


                        t = new Thread() {

                            @Override
                            public void run() {
                                try {
                                    while (!isInterrupted()) {
                                        Thread.sleep(500);
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                Date dt = GetUTCdatetimeAsDate();
                                                SimpleDateFormat sdf = new SimpleDateFormat(DATEFORMAT);
                                                Calendar calendar = Calendar.getInstance();
                                                calendar.setTime(dt);
                                                calendar.add(Calendar.HOUR, 6);
                                                String ss = sdf.format(calendar.getTime());
                                                //TimeLabel.setText(qBasic1.PublishDate);
                                                //TimeLabel.setText(ss);
                                                Date d1 = null;
                                                Date d2 = null;

                                                try {
                                                    d1 = sdf.parse(ss);
                                                    d2 = sdf.parse(qBasic1.PublishDate);
                                                } catch (ParseException e) {
                                                    e.printStackTrace();
                                                }
                                                long diff = d2.getTime() - d1.getTime();
                                                long diffSeconds = diff / 1000 % 60;
                                                long diffMinutes = diff / (60 * 1000) % 60;
                                                long diffHours = diff / (60 * 60 * 1000);
                                                if(diffSeconds <= 0 && diffMinutes <= 0 && diffHours <= 0)
                                                {
                                                    TimeLabel.setText("Exam Started!");
                                                    GoPreview.setVisibility(View.VISIBLE);
                                                    Intent intent = new Intent(getBaseContext(), Preview.class);
                                                    startActivity(intent);
                                                    getThread().interrupt();
                                                }
                                                else {
                                                    TimeLabel.setText(String.format("%02d", diffHours) + ":" + String.format("%02d", diffMinutes) + ":" + String.format("%02d", diffSeconds));
                                                }
                                                }

                                            public Thread getThread(){
                                                return t;
                                            }
                                        });
                                    }
                                } catch (InterruptedException e) {
                                }
                            }
                        };


                        t.start();




                    }
                    else
                    {
                        TimeLabel.setText("N/A");
                    }
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        GoPreview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), Preview.class);
                startActivity(intent);
            }
        });
    }


    public static Date GetUTCdatetimeAsDate()
    {
        //note: doesn't check for null
        return StringDateToDate(GetUTCdatetimeAsString());
    }

    public static String GetUTCdatetimeAsString()
    {
        final SimpleDateFormat sdf = new SimpleDateFormat(DATEFORMAT);
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        final String utcTime = sdf.format(new Date());

        return utcTime;
    }

    public static Date StringDateToDate(String StrDate)
    {
        Date dateToReturn = null;
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATEFORMAT);

        try
        {
            dateToReturn = (Date)dateFormat.parse(StrDate);
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }

        return dateToReturn;
    }




    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out);
    }
}
