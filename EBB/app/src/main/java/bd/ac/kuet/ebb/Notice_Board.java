package bd.ac.kuet.ebb;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Collections;

public class Notice_Board extends AppCompatActivity {


    public static ArrayList<Notice> notices = new ArrayList<Notice>();
    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;
    private StorageReference storageReference;
    //private int i = 0;
    private ListView NoticeList;
    private NoticeListAdapter noticeListAdapter;
    public static int positionClicked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_notice__board);
        overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);



        init();


    }


    public void init()
    {
        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        storageReference = FirebaseStorage.getInstance().getReference();
        NoticeList = (ListView) findViewById(R.id.noticeList);

        databaseReference.child("Notice_Board").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                notices.clear();
                for(DataSnapshot ds:dataSnapshot.getChildren())
                {
                    Notice notice = new Notice();
                    notice = ds.getValue(Notice.class);
                    notices.add(notice);
                    //Notice newNotice = notices.get(i);
                    //Toast.makeText(Notice_Board.this, newNotice.Description.toString(), Toast.LENGTH_SHORT).show();
                    //i++;
                }
                Collections.reverse(notices);
                generateList();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }


    public void generateList()
    {
        noticeListAdapter = new NoticeListAdapter(Notice_Board.this, notices);
        NoticeList.setAdapter(noticeListAdapter);
        NoticeList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(Notice_Board.this, "Clicked to"+position, Toast.LENGTH_SHORT).show();
                positionClicked = position;
                Intent intent = new Intent(Notice_Board.this, Notice_Details.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out);
    }
}
