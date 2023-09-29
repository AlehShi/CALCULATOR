package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private TextView textResult;
    private TextView textCurrent;
    private TextView textOperation;
    private TextView textEquals;
    Button[] buttonsOper;
    String prevOperation="=";
    String operand1 = "0";
    String operand2 = "0";
    String result = "0";
    boolean isDouble=false;
    boolean isNegative=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // привязка к объектам
        // регуляторы размера шрифта
        SeekBar seekBarButtons=(SeekBar)  findViewById(R.id.seekBarButtons);
        SeekBar seekBarResult=(SeekBar)  findViewById(R.id.seekBarResult);
        //TextView
        textResult      =(TextView) findViewById(R.id.textResult);
        textCurrent     =(TextView) findViewById(R.id.textCurrent);
        textOperation   =(TextView) findViewById(R.id.textOperation);
        textEquals      =(TextView) findViewById(R.id.textEquals);
        //кнопки
        Button [] buttonsNum = new Button[]
        {findViewById(R.id.btn0),findViewById(R.id.btn1),findViewById(R.id.btn2),findViewById(R.id.btn3),
                findViewById(R.id.btn4),findViewById(R.id.btn5),findViewById(R.id.btn6),
                findViewById(R.id.btn7),findViewById(R.id.btn8),findViewById(R.id.btn9)
                ,findViewById(R.id.btnDot)};
         buttonsOper = new Button[] {
                findViewById(R.id.btnDivide), findViewById(R.id.btnMult), findViewById(R.id.btnMinus),
                findViewById(R.id.btnPlus),findViewById(R.id.btnSqrt),findViewById(R.id.btnХ2),findViewById(R.id.btnХn),
                findViewById(R.id.btnEquals)    };
        Button[] buttonsOther = new Button[]{
                findViewById(R.id.btnClear),findViewById(R.id.btnDelete) } ;
        //Ресурсы
        Resources resources = getResources();
        int intPositiv = resources.getColor(R.color.intPositiv,  null);
        int intNegative = resources.getColor(R.color.intNegative,  null);
        int doublePositiv = resources.getColor(R.color.doublePositiv,  null);
        int doubleNegative = resources.getColor(R.color.doubleNegative,  null);
        int sizeTextButtons = 20;
        //Слушатель seekBarButtons
        seekBarButtons.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            for(int i=0; i<buttonsNum.length; i++) {
                buttonsNum[i].setTextSize(Integer.valueOf(progress));
            }
            for(int i=0; i<buttonsOper.length; i++) {
                buttonsOper[i].setTextSize(Integer.valueOf(progress));
                }
            for(int i=0; i<buttonsOther.length; i++) {
                buttonsOther[i].setTextSize(Integer.valueOf(progress));
                }
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {       }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {            }
        });
        //Слушатель seekBarResult
        seekBarResult.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            textResult.setTextSize(Integer.valueOf(progress));
            textCurrent.setTextSize(Integer.valueOf(progress));
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        for(int i=0; i<buttonsNum.length; i++) {
            buttonsNum[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    btnNumAction(v);
                }
            });
        }
            for(int i=0; i<buttonsOper.length; i++) {
                buttonsOper[i].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        btnOperAction(v);
                    }
                });
        }
        buttonsOther[0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            prevOperation="=";
            operand1 = "0";
            operand2 = "0";
            result = "0";
            textCurrent.setText("0");
            textOperation.setText("");
            textResult.setText("");
            }
        });

        // кнопа удаления
        buttonsOther[1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str=textCurrent.getText().toString();
                str=str.substring(0, str.length() - 1);
                if(str.length()==0){
                    textCurrent.setText("0");
                }
                else {
                    textCurrent.setText(str);
                }
                str=textOperation.getText().toString();
                if (str.length()==0) {
                    str="0";
                };
                str=str.substring(0, str.length() - 1);
                textOperation.setText(str);
            }
        });
    }


    // действие по кнопкам цифр и "."
    private void btnNumAction(View view) {
        Button btn = (Button) view;
        String number = btn.getText().toString();
        String currentText = textCurrent.getText().toString();
        textCurrent.setText(textCurrent.getText() + number);
       // String currentText = textCurrent.getText().toString();
        if (currentText.equals("0")&&(!number.equals("."))) {
            textCurrent.setText(number);
            textOperation.setText(textOperation.getText() + number);
        } else
        {
            textOperation.setText(textOperation.getText() + number);
        }
    }
    private void btnOperAction(View view){
        Button btn = (Button) view;
        String operation = btn.getText().toString();
        String number = textCurrent.getText().toString();
        String str=textOperation.getText().toString();
        textCurrent.setText("0");
        boolean lastIsOper=false;
        str=str.substring(str.length() , 1);
        for(int i=0; i<buttonsOper.length; i++) {
            if (str.equals(buttonsOper[i].getText().toString())) {
                lastIsOper=true;
                break;
            }
            }

        if (!lastIsOper)
            textOperation.setText(textOperation.getText()+operation);
//        isDouble=number.contains(".")||operand1.contains(".");

            Double res=0.0;
            switch (prevOperation) {
                case "=":
                    break;
                case "+":
                    //textOperation.setText(textOperation.getText()+"=");
                    res = Double.parseDouble(operand1) + Double.parseDouble(number);
                    break;
                case "-":
                    res = Double.parseDouble(operand1) - Double.parseDouble(number);
                    break;
                case "/":
                    //textOperation.setText(textOperation.getText()+"=");
                    res = Double.parseDouble(operand1) / Double.parseDouble(number);
                    break;
                case "x":
                    //textOperation.setText(textOperation.getText()+"=");
                    res = Double.parseDouble(operand1) * Double.parseDouble(number);
                    break;
            }
        result = res.toString();
        if (res % 1 == 0) {
            Integer resInt=res.intValue();
            result = resInt.toString();
        }
            textResult.setText(result);


      switch (operation){
            case "=":
                textOperation.append(result);
                textCurrent.setText(result);
                break;
            case "+":
                operand1=number;
                operand2="0";
                break;
        }

        prevOperation=operation;
    }

}