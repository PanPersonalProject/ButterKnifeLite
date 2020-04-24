package pan.lib.butterknifelite;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import pan.lib.butterknife.ButterKnife;
import pan.lib.butterknife_annotation.BindView;

/**
 * Author:         panqi
 * CreateDate:     2020/4/23 18:04
 */
public class MainActivity extends AppCompatActivity {
    @BindView(R.id.tvContent)
    public TextView tvContent;
    @BindView(R.id.ivBg)
    public ImageView ivBg;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        tvContent.setText("BINDING VIEW SUCCESS");
        ivBg.setBackgroundColor(Color.BLACK);
    }
}

