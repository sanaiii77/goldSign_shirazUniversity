package Student;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.sanai.testapp.MainActivity;
import com.sanai.testapp.R;

import jango.Django;
import jango.Teacher;

public class ResultFragmnet extends Fragment {

    TextView textView ;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_result_fragmnet,container,false);
        textView = view.findViewById(R.id.finalResultForStudent);
        if (Main2Activity.tmt == null){
        }else {
            String string = "";
            string += "استاد امضای طلایی شما :" + " \n";
            Teacher t = Django.getTeacher(Main2Activity.tmt.getTeacher_of_tmt_PK());
            string += t.getTeacherName() + " " + t.getTeacherFamilyName();
            textView.setText(string);
        }
        return  view;
    }
}
