package com.example.calculadoraguia;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import android.widget.TextView;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class MainActivity extends AppCompatActivity {

    private TextView txtPantalla;
    private TextView txtOperacion;
    private String numeroActual = "0";
    private double  primerNumero = 0;
    private String operacionActual = "";
    private boolean nuevoNumero = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowCompat.enableEdgeToEdge(getWindow());
        setContentView(R.layout.activity_main);
        txtPantalla =
                findViewById(R.id.txtPantalla);
        txtOperacion =
                findViewById(R.id.txtOperacion);

        txtPantalla.setText(numeroActual);

        findViewById(R.id.btn0).setOnClickListener(v -> agregarNumero("0"));
        findViewById(R.id.btn1).setOnClickListener(v -> agregarNumero("1"));
        findViewById(R.id.btn2).setOnClickListener(v -> agregarNumero("2"));
        findViewById(R.id.btn3).setOnClickListener(v -> agregarNumero("3"));
        findViewById(R.id.btn4).setOnClickListener(v -> agregarNumero("4"));
        findViewById(R.id.btn5).setOnClickListener(v -> agregarNumero("5"));
        findViewById(R.id.btn6).setOnClickListener(v -> agregarNumero("6"));
        findViewById(R.id.btn7).setOnClickListener(v -> agregarNumero("7"));
        findViewById(R.id.btn8).setOnClickListener(v -> agregarNumero("8"));
        findViewById(R.id.btn9).setOnClickListener(v -> agregarNumero("9"));

        findViewById(R.id.btnSumar).setOnClickListener(v-> seleccionarOperacion("+"));
        findViewById(R.id.btnRestar).setOnClickListener(v-> seleccionarOperacion("-"));
        findViewById(R.id.btnMultiplicar).setOnClickListener(v-> seleccionarOperacion("*"));
        findViewById(R.id.btnDividir).setOnClickListener(v-> seleccionarOperacion("/"));

        findViewById(R.id.btnIgual).setOnClickListener(v -> calcularResultado());
        findViewById(R.id.btnC).setOnClickListener(v -> limpiarCalculadora());
        findViewById(R.id.btnBorrar).setOnClickListener(v -> borrarDigito());
        findViewById(R.id.btnPunto).setOnClickListener(v ->agregarPunto());


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    private void agregarNumero(String numero)
    {
        if (nuevoNumero) {
            numeroActual = numero;
            nuevoNumero = false;
        } else if (numeroActual.equals("0")) {
            numeroActual = numero;
        } else {
            numeroActual += numero;
        }
        txtPantalla.setText(numeroActual);
    }
    private void seleccionarOperacion(String operation) {
        try {
            primerNumero = Double.parseDouble(numeroActual);
            operacionActual = operation;
            nuevoNumero = true;

            String simbolo = operation;

            if (operation.equals("x")) {
                simbolo = "x";
            } else if (operation.equals("/")) {
                simbolo = "÷";
            }

            txtOperacion.setText(numeroActual + " " + simbolo);
        } catch (NumberFormatException e) {
            txtPantalla.setText("Error");
        }

    }


    private void calcularResultado() {

        if (operacionActual.isEmpty() || nuevoNumero) {
            return;
        }

        BigDecimal n1 = BigDecimal.valueOf(primerNumero);
        BigDecimal n2 = new BigDecimal(numeroActual);
        BigDecimal resultado;

        switch (operacionActual) {
            case "+":
                resultado = n1.add(n2);
                break;

            case "-":
                resultado = n1.subtract(n2);
                break;

            case "*":
                resultado = n1.multiply(n2);
                break;

            case "/":
                if (n2.compareTo(BigDecimal.ZERO) == 0) {
                    txtPantalla.setText("Error");
                    txtOperacion.setText(
                            "No se puede dividir entre cero"
                    );
                    numeroActual = "0";
                    operacionActual = "";
                    nuevoNumero = true;
                    return;
                }

                resultado = n1.divide(
                        n2, 10, RoundingMode.HALF_UP
                );
                break;

            default:
                return;
        }

        numeroActual = resultado
                .stripTrailingZeros()
                .toPlainString();

        txtPantalla.setText(numeroActual);
        txtOperacion.setText("");
        operacionActual = "";
        nuevoNumero = true;
    }

    private void    limpiarCalculadora() {
        numeroActual = "0";
        primerNumero = 0;
        operacionActual = "";
        nuevoNumero = false;

        txtPantalla.setText("0");
        txtOperacion.setText("");
    }
    private void borrarDigito() {
        if (nuevoNumero) {
            return;
        }
        if (numeroActual.length() > 1) {
            numeroActual = numeroActual.substring(0, numeroActual.length() - 1);
        }else {
            numeroActual = "0";
        }
        if (numeroActual.equals("-")) {
            numeroActual = "0";
        }
        txtPantalla.setText(numeroActual);
    }
    private void agregarPunto(){
        if (nuevoNumero) {
            numeroActual = "0.";
            nuevoNumero = false;
        } else if (!numeroActual.contains(".")){
            numeroActual += ".";
        }
        txtPantalla.setText(numeroActual);
    }

}