package cseb.tech.smarthublms.Models;

public class AdminHomeItemsModel {
    private int iconResId;
    private String title;
    private Class<?> fragmentClass;

    public AdminHomeItemsModel(int iconResId, String title, Class<?> fragmentClass) {
        this.iconResId = iconResId;
        this.title = title;
        this.fragmentClass = fragmentClass;
    }

    public int getIconResId() {
        return iconResId;
    }

    public String getTitle() {
        return title;
    }

    public Class<?> getFragmentClass() {
        return fragmentClass;
    }
}
