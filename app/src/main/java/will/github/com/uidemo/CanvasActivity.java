package will.github.com.uidemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import will.github.com.uidemo.custom_view.MiSportView;

public class CanvasActivity extends AppCompatActivity {

    MiSportView mi_sport;
    private Button bt_connect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_canvas);
        mi_sport = findViewById(R.id.mi_sport);
        bt_connect = findViewById(R.id.bt_connect);

        bt_connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mi_sport.setConnect(true);
            }
        });

    }
}
