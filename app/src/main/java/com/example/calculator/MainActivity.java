package com.example.calculator;

import android.content.ComponentName;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final EditText EDIT_TEXT = (EditText) findViewById(R.id.editText_Operation);
        Button btnOne = (Button) findViewById(R.id.button_one);
        Button btnTwo = (Button) findViewById(R.id.button_two);
        Button btnThree = (Button) findViewById(R.id.button_three);
        Button btnFour = (Button) findViewById(R.id.button_four);
        Button btnFive = (Button) findViewById(R.id.button_five);
        Button btnSix = (Button) findViewById(R.id.button_six);
        Button btnSeven = (Button) findViewById(R.id.button_seven);
        Button btnEight = (Button) findViewById(R.id.button_eight);
        Button btnNine = (Button) findViewById(R.id.button_nine);
        Button btnZero = (Button) findViewById(R.id.button_zero);
        Button btnFrstParenth = (Button) findViewById(R.id.button_first_parentheses);
        Button btnScondParenth = (Button) findViewById(R.id.button_scnd_parentheses);
        Button btnPlus = (Button) findViewById(R.id.button_plus);
        Button btnSubstract = (Button) findViewById(R.id.button_subtract);
        Button btnDivide = (Button) findViewById(R.id.button_divide);
        Button btnMultiple = (Button) findViewById(R.id.button_multiple);
        Button btnPorcent = (Button) findViewById(R.id.button_porcent);
        Button btnDot = (Button) findViewById(R.id.button_dot);
        Button btnClean = (Button) findViewById(R.id.button_clean);
        Button btnEquals = (Button) findViewById(R.id.button_equal);


        btnOne.setOnClickListener(this);
        btnTwo.setOnClickListener(this);
        btnThree.setOnClickListener(this);
        btnFour.setOnClickListener(this);
        btnFive.setOnClickListener(this);
        btnSix.setOnClickListener(this);
        btnSeven.setOnClickListener(this);
        btnEight.setOnClickListener(this);
        btnNine.setOnClickListener(this);
        btnZero.setOnClickListener(this);
        btnFrstParenth.setOnClickListener(this);
        btnScondParenth.setOnClickListener(this);
        btnPlus.setOnClickListener(this);
        btnSubstract.setOnClickListener(this);
        btnDivide.setOnClickListener(this);
        btnMultiple.setOnClickListener(this);
        btnClean.setOnClickListener(this);
        btnEquals.setOnClickListener(this);
        btnPorcent.setOnClickListener(this);
        btnDot.setOnClickListener(this);

        EDIT_TEXT.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                Intent intent = new Intent();
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    String message = "El resultado de la operación aritmetica " + EDIT_TEXT.getText() + " es: \n\n";
                    String result = calculateOperation();
                    intent.setClass(getApplicationContext(), ResultActivity.class);

                    if (!result.isEmpty()) {
                        message += result;
                        intent.putExtra("RESULT_OPERATION", message);
                        startActivity(intent);
                    }
                    return true;
                }
                return false;
            }
        });
    }

    private void writeOperation(String value) {
        EditText mEditText = (EditText) findViewById(R.id.editText_Operation);
        String operation = mEditText.getText().toString();
        operation += value;
        mEditText.setText(operation);
        mEditText.setSelection(mEditText.getText().length());
    }

    private String calculateOperation() {
        EditText mEditText = (EditText) findViewById(R.id.editText_Operation);
        String operation = mEditText.getText().toString();
        Double result = 0.0;
        String firstNumber = "";
        String secondNumber = "";
        String operador = "";
        Boolean error = false;
        int contOperatorTogether = 0;
        try {
            for (int i = 0; i < operation.length(); i++) {
                if (i == 0) {
                    if (isNumber(operation.charAt(0) + "")) {
                        firstNumber += operation.charAt(0);
                    } else {
                        if (operation.charAt(0) == '-') {
                            firstNumber += operation.charAt(0);
                        } else {
                            Toast.makeText(getApplicationContext(), "El primer caracter no es correcto", Toast.LENGTH_LONG).show();
                            error = true;
                            break;
                        }
                    }
                } else {
                    if ((!isNumber(operation.charAt(0) + "")) && !isNumber(operation.charAt(1) + "")) {
                        Toast.makeText(getApplicationContext(), "No se permiten dos operadores al inicio de la operación", Toast.LENGTH_LONG).show();
                        error = true;
                        error = true;
                        break;
                    }
                    if (operador.isEmpty()) {
                        if (isNumber(operation.charAt(i) + "")) {
                            firstNumber += operation.charAt(i);

                        } else {
                            if (operation.charAt(i) == '.') {
                                if (!firstNumber.contains("."))
                                    firstNumber += operation.charAt(i);
                                else {
                                    Toast.makeText(getApplicationContext(), "El primer número debe poseer solo un punto para los decimales", Toast.LENGTH_LONG).show();
                                    error = true;
                                    break;
                                }
                            } else {
                                operador = operation.charAt(i) + "";
                            }
                        }
                    } else {
                        if (isNumber(operation.charAt(i) + "")) {
                            secondNumber += operation.charAt(i);

                        } else {
                            if (operation.charAt(i) == '.') {
                                if (!secondNumber.contains("."))
                                    secondNumber += operation.charAt(i);
                                else {
                                    Toast.makeText(getApplicationContext(), "El segundo número debe poseer solo un punto para los decimales", Toast.LENGTH_LONG).show();
                                    error = true;
                                    break;
                                }
                            } else {
                                contOperatorTogether++;
                                if ((!isNumber(operation.charAt(i - 1) + "") && !isNumber(operation.charAt(i) + "")) && (operation.charAt(i) == '-')) {
                                    secondNumber += operation.charAt(i);
                                } else {
                                    Toast.makeText(getApplicationContext(), "La operación aritmetica es mal digitada", Toast.LENGTH_LONG).show();
                                    error = true;
                                    break;
                                }
                            }
                        }

                    }
                }
            }
            if (contOperatorTogether > 1) {
                Toast.makeText(getApplicationContext(), "La operación aritmetica es mal digitada", Toast.LENGTH_LONG).show();
                error = true;
            }
            if (!error) {
                result = resultOperation(Double.parseDouble(firstNumber), Double.parseDouble(secondNumber), operador);
                return result.toString();
            } else {
                return "";
            }
        } catch (ArithmeticException ex) {
            Toast.makeText(getApplicationContext(), "ERROR Aritmético, revisar operación", Toast.LENGTH_LONG).show();
            return "";
        }
    }

    private double resultOperation(Double firstNumber, Double secondNumber, String operator) {
        double result = 0;

        switch (operator) {
            case "+":
                result = firstNumber + secondNumber;
                break;
            case "-":
                result = firstNumber - secondNumber;
                break;
            case "*":
                result = firstNumber * secondNumber;
                break;
            case "/":
                if (secondNumber == 0) {
                    throw new ArithmeticException();
                }
                result = firstNumber / secondNumber;

                break;
            default:
                break;
        }


        return result;
    }

    private boolean isNumber(String value) {
        try {
            int num = Integer.parseInt(value);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    @Override
    public void onClick(View v) {
        EditText mEditText = (EditText) findViewById(R.id.editText_Operation);
        Intent intent = new Intent();
        switch (v.getId()) {

            case R.id.button_one:
                writeOperation("1");
                break;
            case R.id.button_two:
                writeOperation("2");
                break;
            case R.id.button_three:
                writeOperation("3");
                break;
            case R.id.button_four:
                writeOperation("4");
                break;
            case R.id.button_five:
                writeOperation("5");
                break;
            case R.id.button_six:
                writeOperation("6");
                break;
            case R.id.button_seven:
                writeOperation("7");
                break;
            case R.id.button_eight:
                writeOperation("8");
                break;
            case R.id.button_nine:
                writeOperation("9");
                break;
            case R.id.button_zero:
                writeOperation("0");

                break;
            case R.id.button_first_parentheses:
//                writeOperation("(");

                break;
            case R.id.button_scnd_parentheses:
//                writeOperation(")");

                break;
            case R.id.button_plus:
                writeOperation("+");

                break;
            case R.id.button_divide:
                writeOperation("/");

                break;

            case R.id.button_multiple:
                writeOperation("*");

                break;

            case R.id.button_subtract:
                writeOperation("-");

                break;
            case R.id.button_porcent:
//                writeOperation("%");

                break;
            case R.id.button_dot:
                writeOperation(".");

                break;
            case R.id.button_clean:
                mEditText.setText("");
                break;
            case R.id.button_equal:
                String message = "El resultado de la operación aritmetica " + mEditText.getText() + " es: \n\n";
                String result = calculateOperation();
                intent.setClass(getApplicationContext(), ResultActivity.class);

                if (!result.isEmpty()) {
                    message += result;
                    intent.putExtra("RESULT_OPERATION", message);
                    startActivity(intent);
                }
                break;
            default:
                break;
        }
    }
}
