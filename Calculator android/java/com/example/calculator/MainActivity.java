package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView screen;
    private Button AC, power, back, div, one, two, three, four, five, six, seven, eight, nine, zero, sub, add, mux, dot, ans, equal;
    private String input, Answer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        screen = findViewById(R.id.screen);
        AC = findViewById(R.id.ac);
        power = findViewById(R.id.power);
        back = findViewById(R.id.back);
        div = findViewById(R.id.div);
        one = findViewById(R.id.n1);
        two = findViewById(R.id.n2);
        three = findViewById(R.id.n3);
        four = findViewById(R.id.n4);
        five = findViewById(R.id.n5);
        six = findViewById(R.id.n6);
        seven = findViewById(R.id.n7);
        eight = findViewById(R.id.n8);
        nine = findViewById(R.id.n9);
        zero = findViewById(R.id.n0);
        sub = findViewById(R.id.sub);
        add = findViewById(R.id.add);
        mux = findViewById(R.id.mux);
        dot = findViewById(R.id.dot);
        ans = findViewById(R.id.ans);
        equal = findViewById(R.id.equal);

    }

    public void ButtonClick(View view) {
        Button btn = (Button) view;
        String data = btn.getText().toString();
        switch (data) {
            case "AC":
                input = "";
                break;
            case "ANS":
                input += Answer;
                break;
            case "*":
                Solve();
                input += "*";
                break;
            case "^":
                Solve();
                input += "^";
                break;
            case "=":
                Solve();
                Answer = input;
                break;
            case "BACK":
                String newText = input.substring(0, input.length() - 1);
                input = newText;
                break;
            default:
                if (input == null) {
                    input = "";
                }
                if (data.equals("+") || data.equals("-") || data.equals("/")) {
                    Solve();
                }
                input += data;
        }
        screen.setText(input);
    }

    private void Solve() {
        if (input.split("\\*").length == 2) {
            String number[] = input.split("\\*");
            try {
                double mul = Double.parseDouble(number[0]) * Double.parseDouble(number[1]);
                input = mul+"";
            } catch (Exception e) {

            }

        } else if (input.split("/").length == 2) {
            String number[] = input.split("/");
            try {
                double div = Double.parseDouble(number[0]) / Double.parseDouble(number[1]);
                input = div+"";
            } catch (Exception e) {

            }
        } else if (input.split("\\^").length == 2) {
            String number[] = input.split("\\^");
            try {
                double pow = Math.pow(Double.parseDouble(number[0]), Double.parseDouble(number[1]));
                input = pow+"";
            } catch (Exception e) {

            }
        } else if (input.split("\\+").length == 2) {
            String number[] = input.split("\\+");
            try {
                double add = Double.parseDouble(number[0]) + Double.parseDouble(number[1]);
                input = add+"";
            } catch (Exception e) {

            }
        } else if (input.split("-").length > 1) {
            String number[] = input.split("-");
            if (number[0] == "" && number.length == 2) {
                number[0] = 0 + "";
            }
            try {
                double sub = 0;
                if (number.length == 2) {
                    sub = Double.parseDouble(number[0]) - Double.parseDouble(number[1]);
                } else if (number.length == 3) {
                    sub = Double.parseDouble(number[1]) - Double.parseDouble(number[2]);
                }
                input = sub + "";
            }
                catch (Exception e) {

            }
        }
        String n[] = input.split("\\.");
        if (n.length > 1) {
            if (n[1].equals("0")) {
                input = n[0];
            }
        }
      screen.setText(input);
    }
}