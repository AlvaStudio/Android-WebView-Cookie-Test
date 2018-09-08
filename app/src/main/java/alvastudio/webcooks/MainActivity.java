package alvastudio.webcooks;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //final String link = "http://m66e085.winfortuna.com/?lp=rp4&trackCode=aff_24911c_34_GooglePlay_fortunaapp";

        final EditText linkEdit = (EditText)findViewById(R.id.linkEdit);

        Button b1 = (Button)findViewById(R.id.btn_1);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (linkEdit.getText().toString().contains("http")) {
                    startInside(linkEdit.getText().toString());
                }
            }
        });

        Button b2 = (Button)findViewById(R.id.btn_2);
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (linkEdit.getText().toString().contains("http")) {
                    startOutside(linkEdit.getText().toString());
                }
            }
        });
    }

    void startInside(String urlString) {
        Intent intent = new Intent(getApplicationContext(), WebActivity.class);
        intent.putExtra("url", urlString);
        //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    void startOutside(String urlString) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(urlString));
        browserIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(browserIntent);
    }
}
