package com.example.khalid.encryption_application;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.security.MessageDigest;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class AES extends AppCompatActivity {

    EditText input,password;
    TextView output,result;
    Button encrypt,decrypt;
    String outputString;
    String AES = "AES";
    long intial;
    long finl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aes);

        input = (EditText)findViewById(R.id.input);
        password =(EditText)findViewById(R.id.password);
        output = (TextView)findViewById(R.id.output);
        encrypt= (Button)findViewById(R.id.btnencrypt);
        decrypt = (Button)findViewById(R.id.btndecrypt);
        result = (TextView)findViewById(R.id.result);


        //onclick listen for the encrypt button
        encrypt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    intial= ((Long)  System.currentTimeMillis());
                    outputString = encrypt(input.getText().toString(),password.getText().toString());
                    output.setText(outputString);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });


        //onclick listen for the decrypt button
        decrypt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    intial= ((Long)  System.currentTimeMillis());
                    outputString =decrypt(outputString,password.getText().toString());
                } catch (Exception e) {
                    Toast.makeText(AES.this, "WRONG PASSWORD!!", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
                output.setText(outputString);
            }
        });
    }




    //encrypting method
    private String encrypt(String data, String password) throws Exception{
        SecretKeySpec key = generatekey(password);
        Cipher cipher= Cipher.getInstance(AES);
        cipher.init(Cipher.ENCRYPT_MODE,key);
        byte[] encVal = cipher.doFinal(data.getBytes());   //one tap step: by ine click on encrypt
        String encryptValue = Base64.encodeToString(encVal, Base64.DEFAULT);
        finl= ((Long)  System.currentTimeMillis());
        //Toast.makeText(this,"Encryption Time"+String.valueOf(finl-intial),Toast.LENGTH_LONG).show();
        result.setText("Encryption Time    "+String.valueOf(finl-intial)+"mls");
        return encryptValue;

    }

    //decrypt method
    private String decrypt(String outputString, String password) throws Exception{
        SecretKeySpec key = generatekey(password);
        Cipher cipher= Cipher.getInstance(AES);
        cipher.init(Cipher.DECRYPT_MODE,key);
        byte[] decodevalue = Base64.decode(outputString, Base64.DEFAULT);
        byte[] decVal =cipher.doFinal(decodevalue);
        String decryptValue = new String (decVal);
        finl= ((Long)  System.currentTimeMillis());
        //Toast.makeText(this,"Encryption Time"+String.valueOf(finl-intial),Toast.LENGTH_LONG).show();
        result.setText("Decryption Time    "+String.valueOf(finl-intial)+"mls");

        return decryptValue;
    }

    private SecretKeySpec generatekey(String password) throws  Exception{
        final MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] bytes = password.getBytes("UTF-8");
        digest.update(bytes,0, bytes.length);
        byte[] key = digest.digest();
        SecretKeySpec secretKeySpec = new SecretKeySpec(key, "AES");
        return secretKeySpec;
    }
}