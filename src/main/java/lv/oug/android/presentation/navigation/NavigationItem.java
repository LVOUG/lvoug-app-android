package lv.oug.android.presentation.navigation;

import lv.oug.android.R;

public enum NavigationItem {

    HOME(R.string.nav_home, R.drawable.home_icon),
    NEWS(R.string.nav_news, R.drawable.news_icon),
    ABOUT(R.string.nav_about, R.drawable.about_icon);

    private int imgId;
    private int titleId;

    NavigationItem(int titleId, int imgId) {
        this.titleId = titleId;
        this.imgId = imgId;
    }

    public int getImgId() {
        return imgId;
    }

    public int getTitleId() {
        return titleId;
    }
}
