
package com.example.pinkpony008.project_1;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.view.View.OnTouchListener;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends Activity implements OnTouchListener{

    Button finger;
    ImageView point;
    TextView p;

    public float x;
    public float y;
    String sDown;
    String sMove;
    String sUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        p = new TextView(this);
        p.setOnTouchListener(this);
        setContentView(p);
        //p.setVisibility(View.INVISIBLE);

        finger = (Button) findViewById(R.id.finger);
        point = (ImageView) findViewById(R.id.point);


    }
    @Override
    public boolean onTouch(View v,MotionEvent e) {
        x = e.getX();
        y = e.getY();
        switch (e.getAction()) {
            case MotionEvent.ACTION_DOWN: // нажатие
                sDown = "Down: x = " + x + ", y = " + y;
                sMove = ""; sUp = "";
                break;
            case MotionEvent.ACTION_MOVE: // движение
                sMove = "Move: x = " + x + ", y = " + y;

                break;
            case MotionEvent.ACTION_UP: // отпускание
            case MotionEvent.ACTION_CANCEL:
                sMove = "";
                sUp = "Up: x = " + x + ", y = " + y;
                break;
        }
        p.setText(sDown + "\n" + sMove + "\n" + sUp);

        return true;
    }
}