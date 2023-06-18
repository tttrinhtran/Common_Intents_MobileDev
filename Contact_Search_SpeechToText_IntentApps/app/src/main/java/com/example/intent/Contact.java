package com.example.intent;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Contact extends AppCompatActivity {

    private Button button;
    private Button nextButton;
    private EditText MailBox;

    private EditText PhoneBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.phonecall_layout);

        button = findViewById(R.id.button);
        MailBox = findViewById(R.id.MailBox);
        PhoneBox = findViewById(R.id.PhoneNumberBox);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String _sMail = MailBox.getText().toString(), _sPhone = PhoneBox.getText().toString();
                if (!_sMail.isEmpty() && !_sPhone.isEmpty()){
                    Intent intent = new Intent(ContactsContract.Intents.Insert.ACTION);
                    intent.setType(ContactsContract.RawContacts.CONTENT_TYPE);

                    intent.putExtra(ContactsContract.Intents.Insert.EMAIL, _sMail)

                            .putExtra(ContactsContract.Intents.Insert.EMAIL_TYPE,
                                    ContactsContract.CommonDataKinds.Email.TYPE_WORK)

                            .putExtra(ContactsContract.Intents.Insert.PHONE, _sPhone)

                            .putExtra(ContactsContract.Intents.Insert.PHONE_TYPE,
                                    ContactsContract.CommonDataKinds.Phone.TYPE_WORK);

                    try{
                        startActivity(intent);
                    }catch (Exception ActivityNotFoundException){
                        Toast.makeText(Contact.this, "Intent does not found", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(Contact.this, "Please full fill the information", Toast.LENGTH_SHORT).show();
                }

            }
        });

        nextButton = findViewById(R.id.nextButton);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( Contact.this, searching.class );
                startActivity(intent);
            }
        });
    }
}