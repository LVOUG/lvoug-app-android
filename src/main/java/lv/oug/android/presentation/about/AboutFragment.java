package lv.oug.android.presentation.about;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import butterknife.InjectView;
import lv.oug.android.R;
import lv.oug.android.application.SocialNetworkNavigation;
import lv.oug.android.presentation.BaseFragment;

import javax.inject.Inject;

public class AboutFragment extends BaseFragment {


    @Inject
    SocialNetworkNavigation socialNetworkNavigation;
    @InjectView(R.id.facebookButton)
    ImageButton facebookButton;
    @InjectView(R.id.twitterButton)
    ImageButton twitterButton;
    @InjectView(R.id.googlePlusButton)
    ImageButton googlePlusButton;
    @InjectView(R.id.mailToButton)
    ImageButton mailToButton;

    @Override
    protected int contentViewId() {
        return R.layout.about;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);
        View.OnClickListener buttonClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view.getId() == R.id.facebookButton) {
                    socialNetworkNavigation.goFacebook("417374488294373");
                } else if (view.getId() == R.id.googlePlusButton) {
                    socialNetworkNavigation.goGooglePlus("+LvOugLv");
                } else if (view.getId() == R.id.twitterButton) {
                    socialNetworkNavigation.goTwitter("lvoug");
                } else if (view.getId() == R.id.mailToButton) {
                    socialNetworkNavigation.goMailTo("andrejs.vorobjovs@lvoug.lv", "LVOUG Android App question");
                }
            }
        };
        facebookButton.setOnClickListener(buttonClickListener);
        twitterButton.setOnClickListener(buttonClickListener);
        googlePlusButton.setOnClickListener(buttonClickListener);
        mailToButton.setOnClickListener(buttonClickListener);
    }


}
