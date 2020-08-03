package com.example.khalid.encryption_application;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;

import javax.crypto.Cipher;

public class RSA extends AppCompatActivity {

    EditText input;
    TextView output,originalText,encryptTime,decryptTime,enctext,dectext;
    Button encrypt,decrypt;
    long intial;
    long finl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        try {

            generateKey();

        } catch (Exception e1) {

            e1.printStackTrace();

        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rsa);

        input = (EditText)findViewById(R.id.input);
        encrypt= (Button)findViewById(R.id.btnencrypt);
        output = (TextView)findViewById(R.id.output);
        originalText =(TextView)findViewById(R.id.OriginText);
        decrypt = (Button)findViewById(R.id.btndecrypt);
        encryptTime =(TextView)findViewById(R.id.encryptTime);
        decryptTime=(TextView)findViewById(R.id.DecryptTime);

        enctext =(TextView)findViewById(R.id.enctext);
        dectext=(TextView)findViewById(R.id.dectext);



       // Toast.makeText(this,m.toString(),Toast.LENGTH_LONG).show();
        //onclick for encryption
        //uses a public key to encrypt
        encrypt.setOnClickListener(new View.OnClickListener() {

                public void onClick(View v) {
                    intial= ((Long)  System.currentTimeMillis());
                    try {
                        output.setText(encrypt(input.getText().toString()));
                        enctext.setVisibility(View.VISIBLE);
                        decrypt.setVisibility(View.VISIBLE);
                    } catch (Exception e) {
// TODO Auto-generated catch block
                        e.printStackTrace();
                    } };
            });


        //onclick for decrypt button
        //uses a private key to decrypt
        decrypt.setOnClickListener(new View.OnClickListener() {

                public void onClick(View v) {
                    intial= ((Long)  System.currentTimeMillis());

                    try {
                         dectext.setVisibility(View.VISIBLE);
                        originalText.setText(String.valueOf(decrypt(output.getText().toString())));
                    } catch (Exception e) {
// TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            });
        }



        private final static String RSA = "RSA";

        public static PublicKey ek;

        public static PrivateKey dk;



    public static void generateKey() throws Exception {

        KeyPairGenerator gen = KeyPairGenerator.getInstance(RSA);

        gen.initialize(512, new SecureRandom());

        KeyPair keyPair = gen.generateKeyPair();

        ek = keyPair.getPublic();

        dk = keyPair.getPrivate();
    }



    private  byte[] encrypt(String text, PublicKey pubRSA)

            throws Exception {
        Cipher cipher = Cipher.getInstance(RSA);
        cipher.init(Cipher.ENCRYPT_MODE, pubRSA);

        finl= ((Long)  System.currentTimeMillis());
        //Toast.makeText(this,"Encryption Time"+String.valueOf(finl-intial),Toast.LENGTH_LONG).show();
        encryptTime.setText("Encryption Time    "+String.valueOf(finl-intial)+"mls");
        return cipher.doFinal(text.getBytes());
    }



    public final  String encrypt(String text) {

        try {
            return byte2hex(encrypt(text, ek));
        } catch (Exception e) {

            e.printStackTrace();
        }

        return null;
    }



    public final  String decrypt(String data) {

        try {
            return new String(decrypt(hex2byte(data.getBytes())));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }



    private  byte[] decrypt(byte[] src) throws Exception {

        Cipher cipher = Cipher.getInstance(RSA);

        cipher.init(Cipher.DECRYPT_MODE, dk);
        finl= ((Long)  System.currentTimeMillis());

        encryptTime.setVisibility(View.VISIBLE);
        decryptTime.setVisibility(View.VISIBLE);
        decryptTime.setText("Decryption Time  "+String.valueOf(finl-intial)+"mls");
        //Toast.makeText(this,"Decryption Time"+ String.valueOf(finl-intial),Toast.LENGTH_LONG).show();
        return cipher.doFinal(src);

    }



    //additional functions
    //converting to hexa
    public static String byte2hex(byte[] b) {
        String hs = "";

        String stmp = "";
        for (int n = 0; n < b.length; n++) {
            stmp = Integer.toHexString(b[n] & 0xFF);
            if (stmp.length() == 1)
                hs += ("0" + stmp);
            else
                hs += stmp;
        }
        return hs.toUpperCase();
    }



    public static byte[] hex2byte(byte[] b) {
        if ((b.length % 2) != 0)
            throw new IllegalArgumentException("hello");
        byte[] b2 = new byte[b.length / 2];
        for (int n = 0; n < b.length; n += 2) {
            String item = new String(b, n, 2);

            b2[n / 2] = (byte) Integer.parseInt(item, 16);
        }
        return b2;
    }
}

