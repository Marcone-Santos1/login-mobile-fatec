package dsm.fatec.login;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import java.util.Objects;

public class signup extends AppCompatActivity {

    private FirebaseAuth mAuth;
    EditText email;
    EditText password;
    EditText passwordConfirmation;
    Button button;
    CheckBox checkBox;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
    }

    public void loadTermsUse(View view) {
        Intent intent = new Intent(this, termsOfUse.class);
        startActivity(intent);
    }

    public void signUp(View view) {
        email = (EditText) findViewById(R.id.editTextTextEmailAddress2);
        password = (EditText) findViewById(R.id.editTextTextPassword7);
        passwordConfirmation = (EditText) findViewById(R.id.editTextTextPassword8);
        button = (Button) findViewById(R.id.button);
        checkBox = (CheckBox) findViewById(R.id.checkBox2);

        button.setOnClickListener(fn -> {
            if (!checkBox.isChecked()) {
                Toast.makeText(signup.this, "Aceite o termo de uso.", Toast.LENGTH_SHORT).show();
            } else {
                System.out.println(validation());
                if (!validation()) {
                    Toast.makeText(signup.this, "Todos os Campos devem ser preenchidos!", Toast.LENGTH_SHORT).show();
                } else {
                    Log.v("EditText", email.getText().toString());
                    Log.v("EditText", password.getText().toString());
                    Log.v("EditText", passwordConfirmation.getText().toString());
                    this.createUser(email, password);
                }
            }
        });
    }

    private boolean validation() {
        if (password.getText().toString().length() <= 8 || passwordConfirmation.getText().toString().length() <= 8) {

            if (!password.getText().toString().equals(passwordConfirmation.getText().toString())) {
                Toast.makeText(signup.this, "As senhas devem ser idênticas.", Toast.LENGTH_SHORT).show();
                return false;
            }
            return false;
        }

        return true;
    }

    private void createUser(EditText email, EditText password) {
        mAuth = FirebaseAuth.getInstance();
        mAuth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            Toast.makeText(signup.this, "Usuário criado com sucesso.", Toast.LENGTH_SHORT).show();
                            FirebaseUser user = mAuth.getCurrentUser();
                        } else {
                            if (Objects.equals(task.getException().getMessage(), "The email address is already in use by another account.")) {
                                Toast.makeText(signup.this, "Usuário já existe", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(signup.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });

    }
}