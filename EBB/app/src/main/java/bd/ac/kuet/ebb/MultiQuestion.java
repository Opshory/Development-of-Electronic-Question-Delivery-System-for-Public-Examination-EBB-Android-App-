package bd.ac.kuet.ebb;

import java.util.ArrayList;

import static com.itextpdf.xmp.XMPMetaFactory.reset;

/**
 * Created by Shanto on 11/26/2017.
 */

public class MultiQuestion {
    public String QNo;
    public String QuestionTittle;
    public ArrayList<SingleQuestion> singleQuestions = new ArrayList<SingleQuestion>();

    public MultiQuestion() {
        reset();
    }

    public MultiQuestion(String questionTittle, ArrayList<SingleQuestion> singleQuestions, String qNo) {
        QuestionTittle = questionTittle;
        this.singleQuestions = singleQuestions;
        QNo = qNo;
    }
}
