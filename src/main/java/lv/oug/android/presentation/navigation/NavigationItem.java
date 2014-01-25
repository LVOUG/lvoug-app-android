package lv.oug.android.presentation.navigation;

public class NavigationItem {

    private int imgId;
    private int titleId;

    public NavigationItem(int titleId, int imgId) {
        this.titleId = titleId;
        this.imgId = imgId;
    }

    public int getImgId() {
        return imgId;
    }

    public void setImgId(int imgId) {
        this.imgId = imgId;
    }

    public int getTitleId() {
        return titleId;
    }

    public void setTitleId(int titleId) {
        this.titleId = titleId;
    }
}
