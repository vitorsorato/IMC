package com.vitorsorato.exercicioum;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.jaeger.library.StatusBarUtil;

import java.lang.reflect.Array;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    private NumberPicker PES1, PES2, ALT1, ALT2;
    private TextView TRES, TCLA;
    private Float PESO, ALTU;
    private double IMC;
    private ImageView ANIM;
    private Integer COD, CNOW, RES;

    String[] CLASS = {"",
            "Abaixo do Peso",
            "Peso Normal",
            "Sobrepeso",
            "Obesidade Grau I",
            "Obesidade Grau II",
            "Obesidade Grau III"};

    Integer[] ARES = {
            R.drawable.anim_1_2,
            R.drawable.anim_2_1,
            R.drawable.anim_2_3,
            R.drawable.anim_3_2,
            R.drawable.anim_3_4,
            R.drawable.anim_4_3,
            R.drawable.anim_4_5,
            R.drawable.anim_5_4,
            R.drawable.anim_5_6,
            R.drawable.anim_6_5,};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        StatusBarUtil.setTransparent(this);
        setContentView(R.layout.activity_main);

        PES1 = findViewById(R.id.PES1);
        PES2 = findViewById(R.id.PES2);
        ALT1 = findViewById(R.id.ALT1);
        ALT2 = findViewById(R.id.ALT2);
        TRES = findViewById(R.id.TRES);
        TCLA = findViewById(R.id.TCLA);
        ANIM = findViewById(R.id.ANIM);

        ANIM.setImageResource(R.drawable.anim_2_1);
        COD = 2;

        PES1.setMinValue(0);
        PES1.setMaxValue(400);
        PES1.setValue(64);
        PES1.setOnScrollListener(new NumberPicker.OnScrollListener() {
            @Override
            public void onScrollStateChange(NumberPicker numberPicker, int i) {
                Change();
            }
        });

        PES2.setMinValue(0);
        PES2.setMaxValue(9);
        PES2.setValue(0);
        PES2.setOnScrollListener(new NumberPicker.OnScrollListener() {
            @Override
            public void onScrollStateChange(NumberPicker numberPicker, int i) {
                Change();
            }
        });

        ALT1.setMinValue(0);
        ALT1.setMaxValue(3);
        ALT1.setValue(1);
        ALT1.setOnScrollListener(new NumberPicker.OnScrollListener() {
            @Override
            public void onScrollStateChange(NumberPicker numberPicker, int i) {
                Change();
            }
        });

        ALT2.setMinValue(0);
        ALT2.setMaxValue(99);
        ALT2.setValue(70);
        ALT2.setOnScrollListener(new NumberPicker.OnScrollListener() {
            @Override
            public void onScrollStateChange(NumberPicker numberPicker, int i) {
                Change();
            }
        });
    }
    public void Change(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                PESO = Float.valueOf(PES1.getValue() + "." + PES2.getValue());
                ALTU = Float.valueOf(ALT1.getValue() + "." + ALT2.getValue());
                IMC = PESO/(Math.pow(ALTU,2));

                CNOW = COD;
                COD = (IMC < 18.5)? 1
                        :(IMC >= 18.5 && IMC < 25) ? 2
                            :(IMC >= 25 && IMC < 30) ? 3
                                :(IMC >= 30 && IMC < 35) ? 4
                                    :(IMC >= 35 && IMC < 40) ? 5 : 6;

                TRES.setText(String.format("%.2f", IMC));
                TCLA.setText(CLASS[COD]);

                if((!COD.equals(CNOW++) && (CNOW > COD+2))){
                    CNOW = COD++;
                }
                if((!COD.equals(CNOW--) && (CNOW < COD-2))){
                    CNOW = COD--;
                }

                if(!COD.equals(CNOW)) {
                    RES = (CNOW == 1 && COD == 2)? ARES[0]
                            :(CNOW == 2 && COD == 1)? ARES[1]
                                :(CNOW == 2 && COD == 3)? ARES[2]
                                    :(CNOW == 3 && COD == 2)? ARES[3]
                                        :(CNOW == 3 && COD == 4)? ARES[4]
                                            :(CNOW == 4 && COD == 3)? ARES[5]
                                                :(CNOW == 4 && COD == 5)? ARES[6]
                                                    :(CNOW == 5 && COD == 4)? ARES[7]
                                                        :(CNOW == 5 && COD == 6)? ARES[8]
                                                            :(CNOW == 6 && COD == 5)? ARES[9]:ARES[1];
                        ANIM.setImageResource(RES);
                        Drawable D = ANIM.getDrawable();
                        if (D instanceof Animatable) {
                            ((Animatable) D).start();
                        }
                    }
                }
        }, 100);
    }
}