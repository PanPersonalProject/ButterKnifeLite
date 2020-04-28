package pan.lib.butterknifelite;

import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.Nullable;

import pan.lib.butterknife_annotation.BindView;

public class SecondActivity extends MainActivity {

    @BindView(R.id.button)
    Button button;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        button.setText("success");
    }
}
