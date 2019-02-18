package bd.ac.kuet.ebb;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Shanto on 11/3/2017.
 */



public class NoticeListAdapter extends BaseAdapter {
    Activity context;
    ArrayList<Notice> noticeList;
    public static LayoutInflater inflater = null;

    public NoticeListAdapter(Activity context, ArrayList<Notice> noticeList) {
        this.context = context;
        this.noticeList = noticeList;
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return noticeList.size();
    }

    @Override
    public Notice getItem(int position) {
        return noticeList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    StorageReference storageReference = FirebaseStorage.getInstance().getReference();

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View itemView = convertView;
        itemView = (itemView == null) ? inflater.inflate(R.layout.list, null) : itemView;
        TextView NoticeTittle = (TextView) itemView.findViewById(R.id.noticeTittle);
        TextView NoticeDescription = (TextView) itemView.findViewById(R.id.noticeDescription);
        TextView DateTime = (TextView) itemView.findViewById(R.id.dateTime);
        final CircleImageView NoticeImage = (CircleImageView) itemView.findViewById(R.id.notice_Image);
        Notice selectedNotice = noticeList.get(position);
        NoticeTittle.setText(selectedNotice.Tittle);
        NoticeDescription.setText(selectedNotice.Description);

        try {

            storageReference.child("Notice_Board").child("images").child(noticeList.get(position).Attactment).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    //Toast.makeText(UserAccount.this, uri.toString(), Toast.LENGTH_SHORT).show();
                    Picasso.with(context).load(uri.toString()).into(NoticeImage);

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Picasso.with(context).load(R.drawable.corruptedimage).into(NoticeImage);
                }
            });
        }
        catch (Exception e)
        {
            //do nothing
            //prevents from program being crashed..
        }
        DateTime.setText("Posted "+selectedNotice.Date +" @ "+ selectedNotice.Time);
        return itemView;
    }
}
