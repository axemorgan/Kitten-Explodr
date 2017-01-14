package cardinalsolutions.com.kittenexplodr;

@SuppressWarnings("WeakerAccess")
public class KittenModel {

    private static long ID_COUNTER = 0;
    private static final String IMAGE_BASE_URL = "http://placekitten.com/400/400?image=";
    private static final int IMAGE_COUNT = 16;

    private final long id;
    private final String imageUrl;
    private boolean isExploded;

    public KittenModel() {
        this.id = ID_COUNTER++;
        this.imageUrl = IMAGE_BASE_URL + id % (IMAGE_COUNT + 1);
        this.isExploded = false;
    }

    public long getId() {
        return id;
    }

    public String getIamgeUrl() {
        return imageUrl;
    }

    public boolean isExploded() {
        return isExploded;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        KittenModel that = (KittenModel) o;

        return id == that.id;

    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }
}
