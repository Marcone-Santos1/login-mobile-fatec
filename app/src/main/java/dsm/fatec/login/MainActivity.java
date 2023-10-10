package dsm.fatec.login;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

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

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    EditText email;
    EditText password;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void loadSignUp(View view) {
        Intent intent = new Intent(this, signup.class);
        startActivity(intent);
    }

    public void login(View view) {
        email = (EditText) findViewById(R.id.editTextTextEmailAddress11);
        password = (EditText) findViewById(R.id.editTextTextPassword11);
        button = (Button) findViewById(R.id.button2);

        button.setOnClickListener(fn -> {

            if (email.getText().toString().length() <= 8 || password.getText().toString().length() <= 8) {
                Toast.makeText(MainActivity.this, "Todos os Campos devem ser preenchidos!", Toast.LENGTH_SHORT).show();
            } else {
                doLogin(email, password);
            }
//            System.out.println(validation());
//            if (!validation()) {
//                Toast.makeText(signup.this, "Todos os Campos devem ser preenchidos!", Toast.LENGTH_SHORT).show();
//            } else {
//                Log.v("EditText", email.getText().toString());
//                Log.v("EditText", password.getText().toString());
//                Log.v("EditText", passwordConfirmation.getText().toString());
//                this.createUser(email, password);
//            }

        });
    }

    public void doLogin(EditText email, EditText password) {
        mAuth = FirebaseAuth.getInstance();
        mAuth.signInWithEmailAndPassword(email.getText().toString(), password.getText().toString()).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    // Sign in success, update UI with the signed-in user's information
                    Toast.makeText(MainActivity.this, "Usuário logado com sucesso.", Toast.LENGTH_SHORT).show();
                    FirebaseUser user = mAuth.getCurrentUser();
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithEmail:failure", task.getException());
                    Toast.makeText(MainActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}