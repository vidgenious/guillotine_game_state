package edu.up.game_state_project;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * @author William Cloutier
 * @author Moses Karemera
 * @author Maxwell McAtee
 */

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView text = findViewById(R.id.TextDisplay);
        Button but = findViewById(R.id.TestButton);
        Listener list = new Listener(text);
        but.setOnClickListener(list);

    }
}