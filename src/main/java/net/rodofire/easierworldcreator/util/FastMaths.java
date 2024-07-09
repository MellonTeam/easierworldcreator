package net.rodofire.easierworldcreator.util;

import net.rodofire.easierworldcreator.Easierworldcreator;

//Own implementation of maths focused on better performance since that precision is not needed that much
public class FastMaths {
    public static void registerMaths() {
        Easierworldcreator.LOGGER.info("registering Maths");
    }

    //fast but unprecise, it is useful to use this when using high radius values
    private static final int FAST_TABLE_SIZE = 360;
    private static final double[] cosfastTable = new double[FAST_TABLE_SIZE];
    private static final double[] sinfastTable = new double[FAST_TABLE_SIZE];

    //took more time but more precise, you should use this when you need to generate big things
    private static final int PRECISE_TABLE_SIZE = 3600;
    private static final double[] cospreciseTable = new double[PRECISE_TABLE_SIZE];
    private static final double[] sinpreciseTable = new double[PRECISE_TABLE_SIZE];

    static {
        for (int i = 0; i < 360; i++) {
            cosfastTable[i] = Math.cos(Math.toRadians(i));
            sinfastTable[i] = Math.sin(Math.toRadians(i));
        }
        for (int i = 0; i < 3600; i++) {
            double a = (double) i / 10;
            cospreciseTable[i] = Math.cos(Math.toRadians(a));
            sinpreciseTable[i] = Math.sin(Math.toRadians(a));
        }
    }

    public static double getFastSin(double x) {
        x = ((x % 360) + 360) % 360;
        return sinfastTable[((int) x + 360) % FAST_TABLE_SIZE];
    }

    public static double getFastCos(double x) {
        x = x % 360;
        return cosfastTable[((int) x + 360) % FAST_TABLE_SIZE];
    }

    public static double getFastTan(double x) {
        if ((int) x % 180 == 90) {
            Easierworldcreator.LOGGER.error("thrown error, tan can't accept values equal to 90° +- 180° (div by 0 error)");
            return 0;
        }
        return sinfastTable[((int) x) % 360] / cosfastTable[((int) x) % 360];
    }

    public static double getPreciseSin(double x) {
        return sinpreciseTable[((int) x * 10) % PRECISE_TABLE_SIZE];
    }

    public static double getPreciseCos(double x) {
        return cospreciseTable[((int) x * 10) % PRECISE_TABLE_SIZE];
    }

    public static double getPreciseTan(double x) {
        if ((int) x % 180 == 90) {
            Easierworldcreator.LOGGER.error("thrown error, tan can't accept values equal to 90° +- 180° (div by 0 error)");
            return 0;
        }
        return sinpreciseTable[((int) x * 10) % PRECISE_TABLE_SIZE] / cospreciseTable[((int) x * 10) % PRECISE_TABLE_SIZE];
    }


    //get 3d length
    public static double getlength(int x, int y, int z) {
        return getFastsqrt(x * x + y * y + z * z);
    }

    //get 2d length
    public static double getLength(int x, int z) {
        return getFastsqrt(x * x + z * z);
    }



    public static double getFastsqrt(float number) {

        if (number < 0) {
            throw new IllegalArgumentException("Bound must be positive inside of sqrt");
        }

        if (number == 0 || number == 1) {
            return number;
        }

        float x = number;
        float prev;

        //precision wanted
        float epsilon = 0.05f;

        do {
            prev = x;
            x = (x + number / x) / 2;
        } while (Math.abs(x - prev) > epsilon);
        return x;
    }
}
