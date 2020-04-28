package pan.lib.butterknifelite;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import pan.lib.butterknife.ButterKnife;
import pan.lib.butterknife_annotation.BindView;

public class SecondActivity extends AppCompatActivity {

    @BindView(R.id.tv_second)
    TextView tvSecond;
    @BindView(R.id.iv_second)
    ImageView ivSecond;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        ButterKnife.bind(this);
    }
}
