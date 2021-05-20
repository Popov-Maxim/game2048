package com.mycompany.game2048;

import android.util.Log;

import java.util.Arrays;
import java.util.Random;

public class Matrix {
    private final int SIZE;
    public static final int WIN = 2048;

    public static final int LEFT = 4;
    public static final int RIGHT = 6;
    public static final int UP = 8;
    public static final int DOWN = 2;
    private int matrix[][];

    public Matrix() {
        this(4);
    }

    public Matrix(int size) {
        SIZE = size;
        matrix = new int[SIZE][SIZE];
        addElement();
    }

    public void restart() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                matrix[i][j] = 0;
            }
        }
        addElement();
    }

    public void printMatrix() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                System.out.printf("%5d", matrix[i][j]);
            }
            System.out.println();
        }
    }

    public int get(int i, int j) {
        return matrix[i][j];
    }

    public Integer[][] getMatrix() {
        Integer[][] res = new Integer[SIZE][SIZE];
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                res[i][j] = matrix[i][j];
            }
        }
        return res;
    }

    private boolean full() {
        boolean full = true;
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (matrix[i][j] == 0) {
                    full = false;
                    break;
                }
            }
        }
        return full;
    }

    public boolean processMessage(int message) {
        switch (message) {
            case LEFT:
                if (!left()) {
                    return false;
                }
                break;
            case RIGHT:
                if (!right()) {
                    return false;
                }
                break;
            case UP:
                if (!up()) {
                    return false;
                }
                break;
            case DOWN:
                if (!down()) {
                    return false;
                }
                break;
            default:
                break;
        }
        if (!full()) {
            addElement();
            if (!hasStep()) {
                Log.d("myGame", "GameOver");
            }
        } else if (finish()) {
            Log.d("myGame", "WIN");
        }
        return true;
    }

    public boolean finish() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (matrix[i][j] >= WIN) {
                    return true;
                }
            }
        }
        return false;
    }

    private void addElement() {
        Random random = new Random();
        int i = random.nextInt(SIZE);
        int j = random.nextInt(SIZE);
        while (matrix[i][j] != 0) {
            i = random.nextInt(SIZE);
            j = random.nextInt(SIZE);
        }
        matrix[i][j] = (random.nextInt(2) + 1) * 2;
    }

    public boolean left() {
        boolean step = false;
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (matrix[i][j] != 0) {
                    int temp = matrix[i][j];
                    matrix[i][j] = 0;
                    for (int k = j; k >= 0; k--) {
                        if (matrix[i][k] != 0) {
                            if (matrix[i][k] == temp) {
                                matrix[i][k] *= 2;
                                step = true;
                            } else {
                                matrix[i][k + 1] = temp;
                                if (k + 1 != j) {
                                    step = true;
                                }
                            }
                            break;
                        } else if (k == 0) {
                            matrix[i][k] = temp;
                            if (k != j) {
                                step = true;
                            }
                        }
                    }
                }
            }
        }
        return step;
    }

    public boolean right() {
        boolean step = false;
        for (int i = 0; i < SIZE; i++) {
            for (int j = SIZE - 1; j >= 0; j--) {
                if (matrix[i][j] != 0) {
                    int temp = matrix[i][j];
                    matrix[i][j] = 0;
                    for (int k = j; k < SIZE; k++) {
                        if (matrix[i][k] != 0) {
                            if (matrix[i][k] == temp) {
                                matrix[i][k] *= 2;
                                step = true;
                            } else {
                                matrix[i][k - 1] = temp;
                                if (k - 1 != j) {
                                    step = true;
                                }
                            }
                            break;
                        } else if (k == SIZE - 1) {
                            matrix[i][k] = temp;
                            if (k != j) {
                                step = true;
                            }
                        }
                    }
                }
            }
        }
        return step;
    }

    public boolean up() {
        boolean step = false;
        for (int j = 0; j < SIZE; j++) {
            for (int i = 0; i < SIZE; i++) {
                if (matrix[i][j] != 0) {
                    int temp = matrix[i][j];
                    matrix[i][j] = 0;
                    for (int k = i; k >= 0; k--) {
                        if (matrix[k][j] != 0) {
                            if (matrix[k][j] == temp) {
                                matrix[k][j] *= 2;
                                step = true;
                            } else {
                                matrix[k + 1][j] = temp;
                                if (k + 1 != i) {
                                    step = true;
                                }
                            }
                            break;
                        } else if (k == 0) {
                            matrix[k][j] = temp;
                            if (k != i) {
                                step = true;
                            }
                        }
                    }
                }
            }
        }
        return step;
    }

    public boolean down() {
        boolean step = false;
        for (int j = 0; j < SIZE; j++) {
            for (int i = SIZE - 1; i >= 0; i--) {
                if (matrix[i][j] != 0) {
                    int temp = matrix[i][j];
                    matrix[i][j] = 0;
                    for (int k = i; k < SIZE; k++) {
                        if (matrix[k][j] != 0) {
                            if (matrix[k][j] == temp) {
                                matrix[k][j] *= 2;
                                step = true;
                            } else {
                                matrix[k - 1][j] = temp;
                                if (k - 1 != i) {
                                    step = true;
                                }
                            }
                            break;
                        } else if (k == SIZE - 1) {
                            matrix[k][j] = temp;
                            if (k != i) {
                                step = true;
                            }
                        }
                    }
                }
            }
        }
        return step;
    }

    public boolean hasStep() {
        return hasLeft() || hasRight() || hasUp() || hasDown();
    }

    private boolean hasLeft() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (matrix[i][j] != 0) {
                    int temp = matrix[i][j];
                    for (int k = j - 1; k >= 0; k--) {
                        if (matrix[i][k] != 0) {
                            if (matrix[i][k] == temp) {
                                return true;
                            } else if (k + 1 != j) {
                                return true;
                            }
                            break;
                        } else if (k == 0) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    private boolean hasRight() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = SIZE - 1; j >= 0; j--) {
                if (matrix[i][j] != 0) {
                    int temp = matrix[i][j];
                    for (int k = j + 1; k < SIZE; k++) {
                        if (matrix[i][k] != 0) {
                            if (matrix[i][k] == temp) {
                                return true;
                            } else if (k - 1 != j) {
                                return true;
                            }
                            break;
                        } else if (k == SIZE - 1) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    private boolean hasUp() {
        for (int j = 0; j < SIZE; j++) {
            for (int i = 0; i < SIZE; i++) {
                if (matrix[i][j] != 0) {
                    int temp = matrix[i][j];
                    for (int k = i - 1; k >= 0; k--) {
                        if (matrix[k][j] != 0) {
                            if (matrix[k][j] == temp) {
                                return true;
                            } else if (k + 1 != i) {
                                return true;
                            }
                            break;
                        } else if (k == 0) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    private boolean hasDown() {
        for (int j = 0; j < SIZE; j++) {
            for (int i = SIZE - 1; i >= 0; i--) {
                if (matrix[i][j] != 0) {
                    int temp = matrix[i][j];
                    for (int k = i + 1; k < SIZE; k++) {
                        if (matrix[k][j] != 0) {
                            if (matrix[k][j] == temp) {
                                return true;
                            } else if (k - 1 != i) {
                                return true;
                            }
                            break;
                        } else if (k == SIZE - 1) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
}
