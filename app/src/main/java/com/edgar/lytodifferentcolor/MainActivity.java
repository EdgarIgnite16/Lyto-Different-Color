package com.edgar.lytodifferentcolor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.edgar.lytodifferentcolor.Adapter.ColorPointAdapter;
import com.edgar.lytodifferentcolor.Object.ColorPointObject;
import com.edgar.lytodifferentcolor.Object.PlayerObject;
import com.edgar.lytodifferentcolor.Object._BaseValues;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity  extends AppCompatActivity {
    private ArrayList<ColorPointObject> arrColorPoint = new ArrayList<>();
    private PlayerObject player;
    private String originalColor, differentColor;
    private CountDownTimer countDownTimer, GIFCat;
    private ColorPointAdapter colorPointAdapter;

    private GridView gvListColorPoint;
    private TextView tvLevels, tvTimes, tvScore;
    private ImageView ivGIF;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Init(); //  Hàm khởi tạo
        CreateMapping(); // Hàm ánh xạ
        SetUp(); // Hàm setup các đối tượng, thông số cần thiết
    }

    // hàm khởi tạo
    private void Init() {
        // Khởi tạo level cho màn chơi
        // Mặc định từ level 1 -> 5 là 60 giây
        ConfigLevelGameplay(1, 60, 3, 3);

        // hàm render ra bảng màu trong gameplay
        this.CreateDataColor();

        // khởi tạo adapter
        colorPointAdapter = new ColorPointAdapter(MainActivity.this, R.layout.item_color_point, arrColorPoint);

        // khởi tạo thông tin người chơi
        player = new PlayerObject("admin", MainActivity.this);
        player.getData(); // cập nhật điểm số hiện tại của người chơi bằng cách lấy điểm số đã được lưu trước đó trong máy
    }

    // Khởi tạo ánh xạ
    private void CreateMapping() {
        gvListColorPoint = (GridView) findViewById(R.id.gvListColorPoint);
        tvLevels = (TextView) findViewById(R.id.tvLevels);
        tvTimes = (TextView) findViewById(R.id.tvTimes);
        ivGIF = (ImageView) findViewById(R.id.ivGIF);
        tvScore = (TextView) findViewById(R.id.tvScore);
    }

    // Set up các thông số và chức năng cần thiết
    private void SetUp() {
        // config level màn chơi
        tvLevels.setText(String.valueOf(_BaseValues.level));

        // load giao diện người chơi
        tvScore.setText(String.valueOf(player.getScore()));

        // config cấu hình mặc định cho bảng màu
        gvListColorPoint.setNumColumns(_BaseValues.numCol);
        gvListColorPoint.setAdapter(colorPointAdapter);

        // tạo sự kiện click cho từng item trong bảng màu
        gvListColorPoint.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // kiếm tra có click đúng differentColor hay không
                if(arrColorPoint.get(i).getIdColor().equals(differentColor)) {
                    int score = player.getScore() + 10;
                    NotifyTrueQues(score);
                } else {
                    int score = player.getScore() - 5;
                    NotifyFalseQues(score);
                }
            }
        });

        // Sau khi khởi tạo hết giao diện => Thời gian countdown gameplay sẽ bắt đầu, giao diện GIF sẽ chạy
        this.UpdateTimeRealtime(_BaseValues.second);
        this.CreateGIFAnimation(_BaseValues.second);
    }

    //==============================================================================================//
    // hàm khởi tạo bảng màu
    private void CreateDataColor() {
        // clear lại danh sách bảng màu trước khi khởi tạo bảng màu mới
        arrColorPoint.clear();

        // Lấy random index của mã màu và nhận mã màu của originalColor và differentColor tương ứng
        int index_ColorId =  new Random().nextInt(_BaseValues.sizeOfListColor);
        originalColor = _BaseValues.originalColor[index_ColorId];
        differentColor = _BaseValues.differentColor[index_ColorId];

        // Khởi tạo bảng color point tổng thể
        while(arrColorPoint.size() < _BaseValues.totalPointGridView) {
            arrColorPoint.add(new ColorPointObject(originalColor));
        }

        // set color point bằng mã màu khác với bảng màu chung hiện tại
        int index_ColorRandomChange = new Random().nextInt(_BaseValues.totalPointGridView);
        arrColorPoint.get(index_ColorRandomChange).idColor = differentColor;
    }

    // hàm cấu hình các thông số màn chơi
    private void ConfigLevelGameplay(int level, int second, int numCol, int numRow) {
        // config các thông số mặc định của màn chơi
        _BaseValues.level = level;
        _BaseValues.second = second;
        _BaseValues.numRow = numRow;
        _BaseValues.numCol = numCol;
        _BaseValues.totalPointGridView = _BaseValues.numCol * _BaseValues.numCol;
    }

    // hàm thực thi update lại adapter
    private void AdapterUpdate() {
        colorPointAdapter.update(arrColorPoint);
    }

    // hàm xử lí việc chọn đúng ô màu
    private void NotifyTrueQues(int score) {
        // update level màn chơi
        UpdateLevelGameplay();

        // load lại bảng màu mới và cập nhật adapter
        CreateDataColor();
        AdapterUpdate();

        // update điểm số của người chơi và in ra màn hình
        player.setScore(score); // cập nhật dữ liệu đối tượng
        player.setData(); // cập nhật điểm mới
        tvScore.setText(String.valueOf(player.getScore()));
    }

    // hàm xử lí việc chọn sai ô màu
    private void NotifyFalseQues(int score) {
        // update điểm số của người chơi và in ra màn hình
        player.setScore(score); // cập nhật dữ liệu đối tượng
        player.setData(); // cập nhật điểm mới
        tvScore.setText(String.valueOf(player.getScore()));
    }

    // hàm update level gameplay
    private void UpdateLevelGameplay() {
        // tăng level hiện tại lên 1
        _BaseValues.level += 1;

        // Điều kiện để unlock số dòng và số cột của bảng màu dựa theo levels màn chơi
        int level = _BaseValues.level;
        if (level >= 1 && level < 5) {
            ConfigLevelGameplay(level, 60, 3, 3);
        } else if (level >= 5 && level < 10) {
            ConfigLevelGameplay(level, 50, 3, 4);
        } else if(level >= 10 && level < 15) {
            ConfigLevelGameplay(level, 45,4, 5);
        } else if(level >= 15 && level < 20) {
            ConfigLevelGameplay(level, 40,5, 6);
        } else if(level >= 20 && level < 25) {
            ConfigLevelGameplay(level, 35, 6, 7);
        } else if(level >= 25 && level < 30) {
            ConfigLevelGameplay(level, 30, 7, 8);
        } else if(level >= 30 && level < 35){
            ConfigLevelGameplay(level, 20, 8, 8);
        } else if(level >= 35 && level < 40){
            ConfigLevelGameplay(level, 15, 8, 8);
        } else {
            ConfigLevelGameplay(level, 10, 8, 8);
        }

        // update lại level gameplay lên màn hình
        tvLevels.setText(String.valueOf(_BaseValues.level));
        gvListColorPoint.setNumColumns(_BaseValues.numCol);

        // điều kiện mỗi màn 5 10 15 sẽ reset thời gian chơi của tổng lần lượt 5 màn
        if (level % 5 == 0) {
            // update lại time countDown
            countDownTimer.cancel();
            GIFCat.cancel();
            UpdateTimeRealtime(_BaseValues.second);
            CreateGIFAnimation(_BaseValues.second);
        }
    }

    // hàm xử lí update thời gian realtime
    private void UpdateTimeRealtime(int second) {
        countDownTimer = new CountDownTimer((long) second * 1000,  1) {
            @Override
            public void onTick(long l) {
                int millis = (int) l;
                int second = millis / 1000;
                int milliS = (millis % 1000) / 10;
                String timeShow = ConvertStringSecondTime(second, milliS);
                tvTimes.setText(timeShow);
            }

            @Override
            public void onFinish() {
                // Cờ kết thúc
                _BaseValues.isTimeout = true;

                // Cập nhật trạng thái view sau khi kết thúc
                tvTimes.setText(String.format("%s:%s", "00", "00"));
                gvListColorPoint.setOnItemClickListener(null);
                _BaseValues.endgameScore = player.getScore();

                // Sau khi kết thúc trò chơi, điểm số của người chơi sẽ tự động mặc định về giá trị ban đầu
                player.setScore(100);
                player.setData();

                // Khởi tạo một activity mới show ra thông báo kết thúc game
                Intent intent = new Intent(MainActivity.this, EndgameNotify.class);
                intent.putExtra("level", _BaseValues.level);
                intent.putExtra("score", player.getScore());
                startActivity(intent);

                // Thông báo trò chơi kết thúc
                Toast.makeText(MainActivity.this, "Trò chơi đã kết thúc!", Toast.LENGTH_SHORT).show();

                // kết thúc activity Main và Activity Endgame sẽ được mở lên
                // trong case user click button back thì sẽ thoát app thay vì back lại màn hình chơi game
                finish();
            }
        };

        countDownTimer.start();
    }

    // hàm xử lí hiển thị thời gian dạng ss:mm
    private String ConvertStringSecondTime(int second, int millis) {
        StringBuilder temp = new StringBuilder();
        if(second < 10) {
            temp.append(String.format("0%s", second));
        } else {
            temp.append(String.format("%s", second));

        }
        temp.append(":");

        if(millis < 10) {
            temp.append(String.format("0%s", millis));
        } else {
            temp.append(String.format("%s", millis));
        }

        return String.valueOf(temp);
    }

    // Hàm tạo ảnh chuyển động
    private void CreateGIFAnimation(int second) {
        ivGIF.setImageResource(R.drawable.kitty_1); // default state

        GIFCat = new CountDownTimer((long) second * 1000,  1000) {
            private int srcImage = R.drawable.kitty_1;
            @Override
            public void onTick(long l) {
                srcImage = (srcImage == R.drawable.kitty_2 ? R.drawable.kitty_1 : R.drawable.kitty_2);
                ivGIF.setImageResource(srcImage);
            }

            @Override
            public void onFinish() {
            }
        };
        GIFCat.start();
    }
}