package cardinalsolutions.com.kittenexplodr;

import android.support.annotation.DrawableRes;

public class KittenModel {

    private static long ID_COUNTER = 0;
    private static int[] IMAGES = {R.drawable.kitten1, R.drawable.kitten2, R.drawable.kitten3, R.drawable.kitten4};

    private final long id;
    @DrawableRes
    private int imageResource;
    private boolean isExploded;

    public KittenModel() {
        this.id = ID_COUNTER++;
        this.imageResource = IMAGES[(int) (id % IMAGES.length)];
        this.isExploded = false;
    }

    public long getId() {
        return id;
    }

    @DrawableRes
    public int getImageResource() {
        return imageResource;
    }

    public boolean isExploded() {
        return isExploded;
    }
}
