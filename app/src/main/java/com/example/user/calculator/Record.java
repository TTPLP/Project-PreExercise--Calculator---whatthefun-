package com.example.user.calculator;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by USER on 2016/01/25.
 */
public class Record extends AppCompatActivity{
    private int iRecord[] = new int[]{R.id.txtR1, R.id.txtR2, R.id.txtR3, R.id.txtR4, R.id.txtR5,
            R.id.txtR6, R.id.txtR7, R.id.txtR8, R.id.txtR9, R.id.txtR10};
    private TextView txtRecord[] = new TextView[10];
    private Button btnReturn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);

        for (int i = 0; i < iRecord.length; i++){
            txtRecord[i] = (TextView)findViewById(iRecord[i]);
        }
        Button btnReturn = (Button)findViewById(R.id.btnReturn);
        btnReturn.setOnClickListener(btnReturnListener);

//        Intent intent = new Intent();
//        Bundle bundle = new Bundle();
        String[] result = this.getIntent().getExtras().getStringArray("result");
        for(int i = 0; i < 10; i++){
            txtRecord[i].setText(result[i]);
        }
    }

    private Button.OnClickListener btnReturnListener = new Button.OnClickListener() {

        @Override
        public void onClick(View v) {
            finish();;
        }
    };
}

