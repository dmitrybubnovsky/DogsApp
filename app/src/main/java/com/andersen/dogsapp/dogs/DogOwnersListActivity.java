package com.andersen.dogsapp.dogs;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.andersen.dogsapp.R;
import static com.andersen.dogsapp.R.color.colorCustomBlueGrey;

public class DogOwnersListActivity extends AppCompatActivity {
    private static final String TAG = "#";

    private LayoutInflater layoutInflater;
    private LinearLayout scrollView;
    private View inflatedView;
    private Toolbar toolbar;

    private String owner[];
    private String kindOfDog[];
    private int dogsQuantity[];

    private String ownerName;
    private String kindOfDogElement;
    private int dogsQuantElem;

    TextView textViewName;
    TextView textViewPreffereDog;
    TextView textViewDogQuant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_dogs_app);

        toolbar = new DogToolBar().get(this, R.string.toolbar_title_owners_list, colorCustomBlueGrey);
        setSupportActionBar(toolbar);

        Resources resources = getResources();
        owner = resources.getStringArray(R.array.owners);
        kindOfDog = resources.getStringArray(R.array.kind_of_dogs);
        dogsQuantity = new int[]{1,2,3,4,6,8,5,3,7,9};

        scrollView = findViewById(R.id.scroll_child_linlayout);
        layoutInflater = getLayoutInflater();

        for(int i = 0; i< owner.length; i++){
            ownerName = owner[i];
            kindOfDogElement = kindOfDog[i];
            dogsQuantElem = dogsQuantity[i];
            Log.d(TAG,kindOfDog[i]);

            // instantiate view-reference with inflated view
            // and set tag for that view
            inflatedView = layoutInflater.inflate(R.layout.owners_item, scrollView, false);

/*           по данному блоку есть вопрос
*            TextView textViewName = inflatedView.findViewById(R.id.owner_name);
*            textViewName.setText(ownerName);
*            textViewName.setTextAppearance(this, R.style.TextViewTitleItem);
*/
            textViewName = createTextView(inflatedView, R.id.owner_name, ownerName, R.style.TextViewTitleItem);
            textViewPreffereDog = createTextView(inflatedView, R.id.preffered_dog, kindOfDogElement, R.style.TextViewSubTitle);
            textViewDogQuant = createTextView(inflatedView, R.id.dogs_quantity, ""+dogsQuantElem);

            inflatedView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    TextView textVName = view.findViewById(R.id.owner_name);
                    String ownNm = textVName.getText().toString();

                    TextView textViewQuant = view.findViewById(R.id.dogs_quantity);
                    Integer dogQuant = Integer.parseInt(textViewQuant.getText().toString());

                    Intent i = new Intent (getApplicationContext(), OwnerDogsActivity.class);
                    i.putExtra(OwnerDogsActivity.EXTRA_OWNER_NAME, ownNm);
                    i.putExtra(OwnerDogsActivity.EXTRA_DOGS_QUANTITY, dogQuant);
                    startActivity(i);
                }
            });
            scrollView.addView(inflatedView);
        }
    }

    protected TextView createTextView(View view, int id, String str, int style){
        TextView textView = view.findViewById(id);
        textView.setText(str);
        textView.setTextAppearance(this, style);
        return textView;
    }

    protected TextView createTextView(View view, int id, String str){
        TextView textView = view.findViewById(id);
        textView.setText(str);
        return textView;
    }
}