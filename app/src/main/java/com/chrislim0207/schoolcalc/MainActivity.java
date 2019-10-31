package com.chrislim0207.schoolcalc;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Stack;

import mathjs.niltonvasques.com.mathjs.MathJS;

public class MainActivity extends AppCompatActivity {

    private TextView mEquationTextView;
    private TextView mAnswerTextView;

    private String expression = "";
    private String for_answer = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mEquationTextView = findViewById(R.id.equationTextView1);
        mAnswerTextView = findViewById(R.id.answerTextView1);

        if (savedInstanceState != null){

            String equation = savedInstanceState.getString("equation");
            mEquationTextView.setText(equation);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (!mEquationTextView.getText().toString().equals("")){
            outState.putString("equation", mEquationTextView.getText().toString());
        }
    }

    public void equals(View view) {
//        MathJS math = new MathJS();
//        String answer = math.eval(calculate);
//        mAnswerTextView.setText(answer);
//        math.destroy();

        mAnswerTextView.setText(String.valueOf(calculate(for_answer)));

        for_answer = "";
        expression = "";
    }

    public int calculate(String s) {
        // delte white spaces
        s = s.replaceAll(" ", "");

        Stack<String> stack = new Stack<String>();
        char[] arr = s.toCharArray();

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == ' ')
                continue;

            if (arr[i] >= '0' && arr[i] <= '9') {
                sb.append(arr[i]);

                if (i == arr.length - 1) {
                    stack.push(sb.toString());
                }
            } else {
                if (sb.length() > 0) {
                    stack.push(sb.toString());
                    sb = new StringBuilder();
                }

                if (arr[i] != ')') {
                    stack.push(new String(new char[] { arr[i] }));
                } else {
                    // when meet ')', pop and calculate
                    ArrayList<String> t = new ArrayList<String>();
                    while (!stack.isEmpty()) {
                        String top = stack.pop();
                        if (top.equals("(")) {
                            break;
                        } else {
                            t.add(0, top);
                        }
                    }

                    int temp = 0;
                    if (t.size() == 1) {
                        temp = Integer.valueOf(t.get(0));
                    } else {
                        for (int j = t.size() - 1; j > 0; j = j - 2) {
                            if (t.get(j - 1).equals("-")) {
                                temp += 0 - Integer.valueOf(t.get(j));
                            } else {
                                temp += Integer.valueOf(t.get(j));
                            }
                        }
                        temp += Integer.valueOf(t.get(0));
                    }
                    stack.push(String.valueOf(temp));
                }
            }
        }

        ArrayList<String> t = new ArrayList<String>();
        while (!stack.isEmpty()) {
            String elem = stack.pop();
            t.add(0, elem);
        }

        int temp = 0;
        for (int i = t.size() - 1; i > 0; i = i - 2) {
            if (t.get(i - 1).equals("-")) {
                temp += 0 - Integer.valueOf(t.get(i));
            } else {
                temp += Integer.valueOf(t.get(i));
            }
        }
        temp += Integer.valueOf(t.get(0));

        return temp;
    }

    public void symbol(View view) {
        Button pressed = (Button) view;
        String symbol = pressed.getText().toString();

        if (symbol.equals("×")){
            for_answer+= "*";
        }
        else if (symbol.equals("%")){
            for_answer += "*0.01";
        }
        else if (symbol.equals("÷")){
            for_answer += "/";
        }
        else {
            for_answer += symbol;
        }
        expression += symbol;
        mEquationTextView.setText(expression);
    }

    public void number(View view) {
        Button pressed = (Button) view;
        String number = pressed.getText().toString();
        expression += number;
        for_answer += number;
        mEquationTextView.setText(expression);
    }

    public void change_sign(View view) {

    }

    public void clear(View view) {
        expression = "";
        mEquationTextView.setText(expression);
        mAnswerTextView.setText("");
    }
}
