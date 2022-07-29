package com.edgar.lytodifferentcolor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.edgar.lytodifferentcolor.Object._BaseValues;

public class EndgameNotify extends AppCompatActivity {
    private TextView tvExit, tvRestart;
    private TextView tvLevelsEndgame, tvScoreEndGame;
    private int level, score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_endgame_notify);

        Init(); //  Hàm khởi tạo
        CreateMapping(); // Hàm ánh xạ
        SetUp(); // Hàm setup các đối tượng, thông số cần thiết
    }

    private void Init() {
        level = this.getIntent().getIntExtra("level", -1);
        score = this.getIntent().getIntExtra("score", -1);
    }

    private void CreateMapping() {
        tvLevelsEndgame = (TextView) findViewById(R.id.tvLevelsEndgame);
        tvScoreEndGame = (TextView) findViewById(R.id.tvScoreEndGame);
        tvRestart = (TextView) findViewById(R.id.tvRestart);
        tvExit= (TextView) findViewById(R.id.tvExit);
    }

    private void SetUp() {
        // show level, score của người chơi đạt được lên màn hình endgame
        tvLevelsEndgame.setText(String.valueOf(level));
        tvScoreEndGame.setText(String.valueOf(score));

        // handle event button
        tvRestart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // kết thúc activity Endgame và start activity Main mới
                Intent intent = new Intent(EndgameNotify.this, MainActivity.class);
                finish();
                startActivity(intent);
            }
        });

        tvExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

}
