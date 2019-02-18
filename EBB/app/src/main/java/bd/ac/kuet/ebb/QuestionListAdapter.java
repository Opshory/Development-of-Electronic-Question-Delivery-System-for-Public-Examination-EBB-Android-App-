package bd.ac.kuet.ebb;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Shanto on 11/30/2017.
 */

public class QuestionListAdapter extends BaseAdapter {
    Activity context;
    ArrayList<QuestionSet> questionSets;
    public static LayoutInflater inflater = null;


    public QuestionListAdapter(Activity context, ArrayList<QuestionSet> questionSets) {
        this.context = context;
        this.questionSets = questionSets;
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return questionSets.size();
    }

    @Override
    public QuestionSet getItem(int position) {
        return questionSets.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View itemView = convertView;
        itemView = (itemView == null) ? inflater.inflate(R.layout.question_set_list, null) : itemView;
        TextView Header = (TextView) itemView.findViewById(R.id.Heading);
        TextView Ques = (TextView) itemView.findViewById(R.id.questionSet);
        QuestionSet SelectedQuestionSet = questionSets.get(position);
        Header.setText(SelectedQuestionSet.Heading);
        Ques.setText(SelectedQuestionSet.QuestionSet);

        return itemView;
    }
}
