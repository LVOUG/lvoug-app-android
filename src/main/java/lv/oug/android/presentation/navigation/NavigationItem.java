package lv.oug.android.presentation.navigation;

import lv.oug.android.R;

public enum NavigationItem {

    HOME(R.string.nav_home, R.drawable.ico_home),
    NEWS(R.string.nav_news, R.drawable.ico_news),
    PAST_EVENTS(R.string.nav_past_events, R.drawable.ico_events),
    ABOUT(R.string.nav_about, R.drawable.ico_about),
    TWITTER(R.string.nav_twitter, R.drawable.ico_twitter);

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
