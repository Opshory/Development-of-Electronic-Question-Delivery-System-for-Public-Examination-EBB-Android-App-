package bd.ac.kuet.ebb;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

public class About_us extends AppCompatActivity {

    private LinearLayout layProfile1;
    private LinearLayout layProfile2;
    private LinearLayout layProfile3;
    private TextView Details1;
    private TextView Details2;
    private TextView Details3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_about_us);
        overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);


        init();

    }


    public void init()
    {
        layProfile1 = (LinearLayout) findViewById(R.id.lay_profile1);
        layProfile2 = (LinearLayout) findViewById(R.id.lay_profile2);
        layProfile3 = (LinearLayout) findViewById(R.id.lay_profile3);
        Details1 = (TextView) findViewById(R.id.details1);
        Details2 = (TextView) findViewById(R.id.details2);
        Details3 = (TextView) findViewById(R.id.details3);

        layProfile1.setVisibility(View.GONE);
        layProfile2.setVisibility(View.GONE);
        layProfile3.setVisibility(View.GONE);

        layProfile1.animate().translationY(500);
        layProfile2.animate().translationY(500);
        layProfile3.animate().translationY(500);


        Details1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Details1.getText().toString().equals("Contacts"))
                {
                    layProfile1.setVisibility(View.VISIBLE);
                    layProfile1.animate().translationY(0);
                    Details1.setText("Hide");
                }
                else
                {
                    //layProfile1.setVisibility(View.VISIBLE);
                    layProfile1.animate().translationY(500);
                    Details1.setText("Contacts");
                }
            }
        });

        Details2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Details2.getText().toString().equals("Contacts"))
                {
                    layProfile2.setVisibility(View.VISIBLE);
                    layProfile2.animate().translationY(0);
                    Details2.setText("Hide");

                }
                else
                {
                    //layProfile2.setVisibility(View.VISIBLE);
                    layProfile2.animate().translationY(500);
                    Details2.setText("Contacts");
                }
            }
        });

        Details3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Details3.getText().toString().equals("Contacts"))
                {
                    layProfile3.setVisibility(View.VISIBLE);
                    layProfile3.animate().translationY(0);
                    Details3.setText("Hide");
                }
                else
                {
                    //layProfile3.setVisibility(View.VISIBLE);
                    layProfile3.animate().translationY(500);
                    Details3.setText("Contacts");
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
