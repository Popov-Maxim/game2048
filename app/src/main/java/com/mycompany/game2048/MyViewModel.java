package com.mycompany.game2048;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MyViewModel extends ViewModel {
    private MutableLiveData<Integer[][]> data;
    private Matrix matrix;

    public LiveData<Integer[][]> getData() {
        if (data == null) {
            data = new MutableLiveData<>();
        }
        return data;
    }

    public void createMatrix() {
        if (matrix == null) {
            matrix = new Matrix(4);
            data.setValue(matrix.getMatrix());
        }
    }

    public void restart() {
        matrix.restart();
        data.setValue(matrix.getMatrix());
    }

    public void swipe(int way) {
        boolean step = false;
        if (matrix.processMessage(way)) {
            data.setValue(matrix.getMatrix());
        }
    }
}
