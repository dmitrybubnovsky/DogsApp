package com.andersen.dogsapp.activities.with.textview;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.andersen.dogsapp.R;

import static android.support.constraint.ConstraintSet.WRAP_CONTENT;

public class ActTextViewInConstLayout extends AppCompatActivity {
    private static final int MARGIN = 8;
    private TextView textView1;
    private TextView textView2;
    private TextView textView3;
    private ConstraintLayout parentConstraintLayout;
    private static final int ID_TV1 = 161;
    private static final int ID_TV2 = 162;
    private static final int ID_TV3 = 163;

    private TextView createTextView (int id, String str){
        TextView textView = new TextView(this);
        textView.setId(id);
        parentConstraintLayout.addView(textView);
        textView.setText(str);
        return textView;
    }

    private void createConstrSet(int textViewId, int constrAlignHor, int constrAlignVer){
        ConstraintSet set = new ConstraintSet();
        set.clone(parentConstraintLayout);
        int id = textViewId;
        set.constrainWidth(id, WRAP_CONTENT);
        set.constrainHeight(id, WRAP_CONTENT);
        set.connect(id, constrAlignHor, parentConstraintLayout.getId(), constrAlignHor, MARGIN);
        set.connect(id, constrAlignVer, parentConstraintLayout.getId(), constrAlignVer, MARGIN);
        set.applyTo(parentConstraintLayout);
    }

    private void createConstrSet(int textViewId){
        ConstraintSet set = new ConstraintSet();
        set.clone(parentConstraintLayout);
        int id = textViewId;
        set.constrainWidth(id, WRAP_CONTENT);
        set.constrainHeight(id, WRAP_CONTENT);
        set.connect(id, ConstraintSet.LEFT, parentConstraintLayout.getId(), ConstraintSet.LEFT, 0);
        set.connect(id, ConstraintSet.RIGHT, parentConstraintLayout.getId(), ConstraintSet.RIGHT, 0);
        set.connect(id, ConstraintSet.TOP, parentConstraintLayout.getId(), ConstraintSet.TOP, 0);
        set.connect(id, ConstraintSet.BOTTOM, parentConstraintLayout.getId(), ConstraintSet.BOTTOM, 0);
        set.applyTo(parentConstraintLayout);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sixth);

        parentConstraintLayout = findViewById(R.id.parent_Constraint);


        textView1 = CustomTextView
                             .getInstance(this, ID_TV1, "FIRST Custom instance")
                             .getCustomTextView();
        createConstrSet(textView1.getId(), ConstraintSet.LEFT, ConstraintSet.TOP);
        parentConstraintLayout.addView(textView1);

        textView2 = CustomTextView
                             .getInstance(this, ID_TV2, "SECOND TEXTVIEW")
                             .getCustomTextView();
        createConstrSet(textView2.getId());
        parentConstraintLayout.addView(textView2);

        textView3 = CustomTextView
                .getInstance(this, ID_TV3, "Third III Text")
                .getCustomTextView();
        createConstrSet(textView3.getId(), ConstraintSet.RIGHT, ConstraintSet.BOTTOM);
        parentConstraintLayout.addView(textView3);
    }
}
