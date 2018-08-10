package onpu.vasiliy.smarthouse;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LogIn extends AppCompatActivity implements View.OnClickListener{
    private EditText etIP;
    private EditText etPassword;
    private Button btnConnect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.log_in);

        etIP = findViewById(R.id.etIP);
        etPassword = findViewById(R.id.etPassword);
        btnConnect = findViewById(R.id.btnConnect);
        btnConnect.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnConnect:
                if(isPasswordCorrect(view)){
                    if (view != null) {
                        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    }

                    Intent intend = new Intent(this, Control.class);
                    startActivity(intend);
                }; break;
        }
    }

    private boolean isPasswordCorrect(View view){
        String password = etPassword.getText().toString();
        if(password.equals("8888")){
            return true;
        }else {
            //Snackbar.make(view,R.string.wrong_password,Snackbar.LENGTH_SHORT).show();
            Toast toast = Toast.makeText(view.getContext(),R.string.wrong_password,Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.TOP,10,10);
            toast.show();

            etPassword.getText().clear();
            return false;
        }
    }
}
