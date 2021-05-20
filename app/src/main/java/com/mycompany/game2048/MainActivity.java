package com.mycompany.game2048;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.ProgressBar;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.mycompany.game2048.databinding.ActivityMainBinding;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    MyViewModel viewModel;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewModel = ViewModelProviders.of(this).get(MyViewModel.class);
        ActivityMainBinding binding = DataBindingUtil
                .setContentView(this, R.layout.activity_main);

        LiveData<Integer[][]> liveData = viewModel.getData();

        liveData.observe(this, new Observer<Integer[][]>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onChanged(Integer[][] integers) {
                binding.setMtx(integers);
                changeProgressBar(integers);
            }
        });
        viewModel.createMatrix();
        binding.setColor(getColorInts());
        findViewById(R.id.restart).setOnClickListener(view -> viewModel.restart());
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void changeProgressBar(Integer[][] integers) {
        ProgressBar bar = findViewById(R.id.progressBar);
        bar.setProgress(Arrays.stream(integers)
                .mapToInt(value -> Arrays.stream(value).max(Integer::compareTo).get())
                .max().getAsInt());
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private Integer[] getColorInts() {
        Integer[] res = new Integer[(int) (Math.log(2048) / Math.log(2) + 1)];
        res[0] = getColor(R.color.colorBoxGray);
        res[1] = getColor(R.color.colorBox2);
        res[2] = getColor(R.color.colorBox4);
        res[3] = getColor(R.color.colorBox8);
        res[4] = getColor(R.color.colorBox16);
        res[5] = getColor(R.color.colorBox32);
        res[6] = getColor(R.color.colorBox64);
        res[7] = getColor(R.color.colorBox64);
        res[8] = getColor(R.color.colorBox64);
        res[9] = getColor(R.color.colorBox64);
        res[10] = getColor(R.color.colorBox64);
        res[11] = getColor(R.color.colorBox64);
        return res;

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return gestureDetector.onTouchEvent(event);
    }

    GestureDetector.SimpleOnGestureListener simpleOnGestureListener
            = new GestureDetector.SimpleOnGestureListener() {
        static final float MIN_SWIPE = 100;

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
                               float velocityY) {
            float deltaX = e1.getX() - e2.getX();
            float deltaY = e1.getY() - e2.getY();

            if (Math.abs(deltaX) > Math.abs(deltaY)) {
                if (deltaX > MIN_SWIPE) {
                    Log.d("myLogs", "left");
                    viewModel.swipe(Matrix.LEFT);
                } else if (deltaX < -MIN_SWIPE) {
                    Log.d("myLogs", "right");
                    viewModel.swipe(Matrix.RIGHT);
                }
            } else {
                if (deltaY > MIN_SWIPE) {
                    Log.d("myLogs", "up");
                    viewModel.swipe(Matrix.UP);
                } else if (deltaY < -MIN_SWIPE) {
                    Log.d("myLogs", "down");
                    viewModel.swipe(Matrix.DOWN);
                }
            }
            return true;
        }
    };
    GestureDetector gestureDetector = new GestureDetector(getBaseContext(),
            simpleOnGestureListener);
}