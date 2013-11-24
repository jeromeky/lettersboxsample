package com.arkanan.lettersboxsample;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.arkanan.lettersbox.factory.LettersBoxFactory;
import com.arkanan.lettersbox.model.EnterLettersBoxStyle;
import com.arkanan.lettersbox.model.LettersBoxArg;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;


public class MainActivity extends ActionBarActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Activity myActivity = this;

        //Declare linearlayout where lettersbox will show
        LinearLayout enterLetters = (LinearLayout) this.findViewById(R.id.linearlayout_enter_letters);
        LinearLayout randomLetters = (LinearLayout) this.findViewById(R.id.linearlayout_random_letters);
        final TextView hint = (TextView) this.findViewById(R.id.hint);
        Button buttonHint = (Button) this.findViewById(R.id.button_hint);
        Button buttonAnswer = (Button) this.findViewById(R.id.button_answer);
        final Button buttonNext= (Button) this.findViewById(R.id.button_next);

        //Create LettersBoxArg which will contains all need to create lettersbox
        LettersBoxArg arg = new LettersBoxArg(this, enterLetters, randomLetters);

        //Create a list of words to guess
        List<String> listOfWords = new ArrayList<String>();
        listOfWords.add("Bruce Wayne");
        listOfWords.add("Tony Stark");
        listOfWords.add("Peter Parker");

        final LinkedList<String> listOfHints = new LinkedList<String>();
        listOfHints.add("Batman");
        listOfHints.add("Iron Man");
        listOfHints.add("Spiderman");

        hint.setText(listOfHints.poll());

        //Create a EnterLettersBoxStyle (or RandomLettersBoxStyle) if you want to custom letters
        //Otherwise it will take the default style
        EnterLettersBoxStyle enterLettersBoxStyle = new EnterLettersBoxStyle();
        enterLettersBoxStyle.setSizeUnit(TypedValue.COMPLEX_UNIT_DIP);
        enterLettersBoxStyle.setSize(10);

        //Set all settings to arg object
        arg.setWords(listOfWords)
                .setMaxLetters(20)
                .setMaxLine(2)
                .setHintButton(buttonHint)
                .setAnswerButton(buttonAnswer)
                .setEnterLettersStyle(enterLettersBoxStyle);

        //Use LettersBoxFactory to construct lettersbox
        //You have to override two methods :
        // - onFullEnterLetters : this method is call when user type all enter letters
        // - onEmptyListWords : this method is call when there is no more words in the list
        final LettersBoxFactory factory = new LettersBoxFactory(arg) {

            @Override
            public void onFullEnterLetters(boolean result) {
                if(result){
                    buttonNext.setVisibility(View.VISIBLE);
                    Toast toast = Toast.makeText(myActivity.getBaseContext(), "Correct", 2000);
                    toast.show();
                }
                else{
                    Toast toast = Toast.makeText(myActivity.getBaseContext(), "Incorrect", 2000);
                    toast.show();
                }
            }

            @Override
            public void onEmptyListWords() {
                Toast toast = Toast.makeText(myActivity.getBaseContext(), "No more words    ", 5000);
                toast.show();

            }

            @Override
            public void onClickHintButton() {

            }

            @Override
            public void onClickAnswerButton() {
                buttonNext.setVisibility(View.VISIBLE);
            }
        };

        factory.construct();

        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setVisibility(View.GONE);
                factory.reloadWithNextWord();
                hint.setText(listOfHints.poll());
            }
        });
    }


}
