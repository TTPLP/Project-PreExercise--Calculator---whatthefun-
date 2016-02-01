package com.example.user.calculator;
//there still is a bug while double click btn_operator
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private String input1 = "", input2 = "", pre_operator, cur_operator;
    private String record = "";    //the text will be saved
    private String tmp = "";
    private String answer = "";
    private Double dou_input2, dou_input1 , dou_answer;
    boolean isComplete = false, isPressed = false; // Is user finish his input, Is btnEqual pressed
    private EditText edtInput;
    private Button btnClear, btnDelete;
    private int NumBtnID[] = new int[]{R.id.btnN0, R.id.btnN1, R.id.btnN2, R.id.btnN3,//the number button
            R.id.btnN4, R.id.btnN5, R.id.btnN6, R.id.btnN7, R.id.btnN8, R.id.btnN9, R.id.btnDot};
    private Button btnDivid, btnMultiple, btnMinus, btnAdd, btnEqual;
    private Button btnShowRecord;
    DB db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DB(getApplicationContext());
        edtInput = (EditText) findViewById(R.id.edtInput);
        btnClear = (Button) findViewById(R.id.btnClear);
        btnDelete = (Button) findViewById(R.id.btnDelete);
        btnDivid = (Button) findViewById(R.id.btnDivid);
        btnMultiple = (Button) findViewById(R.id.btnMultiple);
        btnMinus = (Button) findViewById(R.id.btnMinus);
        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnEqual = (Button) findViewById(R.id.btnEqual);
        btnShowRecord = (Button) findViewById(R.id.btnShowRecord);

        btnDelete.setOnClickListener(tool);
        btnClear.setOnClickListener(tool);

        for (int i = 0; i < NumBtnID.length; i++) {//set number button listener
            ((Button) findViewById(NumBtnID[i])).setOnClickListener(Number);
        }
        btnDivid.setOnClickListener(calculate);
        btnMultiple.setOnClickListener(calculate);
        btnMinus.setOnClickListener(calculate);
        btnAdd.setOnClickListener(calculate);
        btnEqual.setOnClickListener(btnEqualListener);
        btnShowRecord.setOnClickListener(btnShowRecordListener);
    }

    //while user click tool button
    private Button.OnClickListener tool = new Button.OnClickListener() {//while user click toolbtn
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btnClear://all are default
                    input1 = "";
                    input2 = "";
                    edtInput.setText("");
                    record = "";
                    answer = "";
                    tmp = "";
                    pre_operator = "";
                    break;
                case R.id.btnDelete://delete the last figure
                    String s = edtInput.getText().toString();
                    if (s.length() > 0) {
                        s = s.substring(0, s.length() - 1);
                        edtInput.setText(s);
                    }
            }
        }
    };

    //while user click number
    private Button.OnClickListener Number = new Button.OnClickListener() {//while user click number 0-9

        @Override
        public void onClick(View v) {
            isPressed = false;
            if (isComplete) {
                edtInput.setText("");
//                tmp = "";
                record += pre_operator;
            }
            String input = edtInput.getText().toString();
            Button btn = (Button) findViewById(v.getId());
            edtInput.setText(input + btn.getText());
            tmp += btn.getText();
            isComplete = false;
        }
    };

    //while user click operator
    private Button.OnClickListener calculate = new Button.OnClickListener() {
        @Override
        public void onClick(View v) {
            isComplete = true;//input is complete
            isPressed = false;//btnEqual doesn't double pressed
            record += " " + edtInput.getText().toString() + " ";

//            if (answer != "") {
//                input1 = answer;
//            }
            if (input1.equals("")) {
                input1 = tmp;
            } else{
                input2 = tmp;
                dou_input2 = Double.valueOf(input2);//turn to type Double
                dou_input1 = Double.valueOf(input1);
                dou_answer = 0.0;
                switch (pre_operator) {
                    case "/":
                        dou_answer = dou_input1 / dou_input2;
                        break;
                    case "*":
                        dou_answer = dou_input1 * dou_input2;
                        break;
                    case "-":
                        dou_answer = dou_input1 - dou_input2;
                        break;
                    case "+":
                        dou_answer = dou_input1 + dou_input2;
                        break;
                }
                answer = dou_answer.toString();
                edtInput.setText(answer);
                input1 = answer;
            }
            switch (v.getId()) {
                case R.id.btnDivid:
                    cur_operator = "/";
                    break;
                case R.id.btnMultiple:
                    cur_operator = "*";
                    break;
                case R.id.btnMinus:
                    cur_operator = "-";
                    break;
                case R.id.btnAdd:
                    cur_operator = "+";
                    break;
            }
            pre_operator = cur_operator;
            tmp = "";
        }
    };

    //while click btnEqual
    private Button.OnClickListener btnEqualListener = new Button.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (!isPressed && tmp != "") {
                record += " " + edtInput.getText().toString();
                input2 = tmp;
//                if(input1 == ""){
//                    input1 = answer;
//                }
                dou_input2 = Double.valueOf(input2);//turn to type Double
                dou_input1 = Double.valueOf(input1);
                dou_answer = 0.0;
                switch (pre_operator) {
                    case "/":
                        dou_answer = dou_input1 / dou_input2;
                        break;
                    case "*":
                        dou_answer = dou_input1 * dou_input2;
                        break;
                    case "-":
                        dou_answer = dou_input1 - dou_input2;
                        break;
                    case "+":
                        dou_answer = dou_input1 + dou_input2;
                        break;
                }
                answer = dou_answer.toString();
                record += " = " + answer;
                db.insert(record);
                edtInput.setText(answer);
                tmp = answer;
                record = "";
                input1 = "";
                input2 = "";
                isPressed = true;
            }
        }
    };

    //show record
    private Button.OnClickListener btnShowRecordListener = new Button.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent();
            intent.setClass(MainActivity.this, Record.class);
            String[] result;
            result = db.getLast();
            Bundle bundle = new Bundle();
            bundle.putStringArray("result", result);
            intent.putExtras(bundle);
            startActivity(intent);
        }
    };
}
