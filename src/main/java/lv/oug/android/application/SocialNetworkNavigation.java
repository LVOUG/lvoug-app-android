package lv.oug.android.application;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import lv.oug.android.infrastructure.common.StringService;

import javax.inject.Inject;

public class SocialNetworkNavigation {

    @Inject
    Context context;

    @Inject
    StringService stringService;

    public void goGooglePlus(int profileId) {
        Intent intent;
        String profile = stringService.loadString(profileId);
        try {
            context.getPackageManager().getPackageInfo("com.google.android.apps.plus", 0);
            intent = new Intent(Intent.ACTION_VIEW);
            intent.setClassName("com.google.android.apps.plus",
                    "com.google.android.apps.plus.phone.UrlGatewayActivity");
            intent.putExtra("customAppUri", profile);
        } catch (PackageManager.NameNotFoundException e) {
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://plus.google.com/" + profile + "/posts"));
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public void goFacebook(int id) {
        Intent intent;
        String profile = stringService.loadString(id);
        try {
            context.getPackageManager().getPackageInfo("com.facebook.katana", 0);
            String facebookScheme = "fb://profile/" + profile;
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse(facebookScheme));
        } catch (PackageManager.NameNotFoundException e) {
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/" + profile));
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public void goTwitter(int profileId) {
        Intent intent;
        String profile = stringService.loadString(profileId);
        try {
            context.getPackageManager().getPackageInfo("com.twitter.android", 0);
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse("twitter://user?screen_name=" + profile));
        } catch (Exception e) {
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/" + profile));
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public void goMailTo(int mailId, int subjectId) {
        String mail = stringService.loadString(mailId);
        String subject = stringService.loadString(subjectId);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:" + mail));
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

}
