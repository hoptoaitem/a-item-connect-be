package com.aitem.connect.helper;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Random;

public class AitemConnectHelper {

    public static int getRandomNumber(int n) {
        int m = (int) Math.pow(10, n - 1);
        return m + new Random().nextInt(9 * m);
    }
}
