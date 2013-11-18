package com.arkanan.lettersboxsample;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.arkanan.lettersbox.factory.LettersBoxFactory;
import com.arkanan.lettersbox.model.EnterLettersBoxStyle;
import com.arkanan.lettersbox.model.LettersBoxArg;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Activity myActivity = this;

        LinearLayout enterLetters = (LinearLayout) this.findViewById(R.id.linearlayout_enter_letters);
        LinearLayout randomLetters = (LinearLayout) this.findViewById(R.id.linearlayout_random_letters);
        LettersBoxArg arg = new LettersBoxArg(this, enterLetters, randomLetters);

        List<String> strings = new ArrayList<String>();
        strings.add("robin des bois");
        strings.add("spiderman");
        strings.add("batman");

        EnterLettersBoxStyle enterLettersBoxStyle = new EnterLettersBoxStyle();
        enterLettersBoxStyle.setSizeUnit(TypedValue.COMPLEX_UNIT_DIP);
        enterLettersBoxStyle.setSize(10);

        arg.setWords(strings).setMaxLetters(20).setMaxLine(2).setEnterLettersStyle(enterLettersBoxStyle );
        new LettersBoxFactory(arg) {

            @Override
            public void onFullEnterLetters(boolean result) {
                if(result)
                    this.reloadWithNextWord();
                else{
                    Toast toast = Toast.makeText(myActivity.getBaseContext(), "wrong words", 2000);
                    toast.show();
                }
            }

            @Override
            public void onEmptyListWords() {
                Toast toast = Toast.makeText(myActivity.getBaseContext(), "empty list", 5000);
                toast.show();

            }
        }.construct();
    }

}
