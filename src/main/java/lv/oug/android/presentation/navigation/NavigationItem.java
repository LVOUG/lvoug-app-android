package lv.oug.android.presentation.navigation;

public class NavigationItem {

    private String iconName;
    private String titleName;

    public NavigationItem(String titleName) {
        this.titleName = titleName;
        this.iconName = titleName + "_icon";
    }

    public String getIconName() {
        return iconName;
    }

    public void setIconName(String iconName) {
        this.iconName = iconName;
    }

    public String getTitleName() {
        return titleName;
    }

    public void setTitleName(String titleName) {
        this.titleName = titleName;
    }
}
