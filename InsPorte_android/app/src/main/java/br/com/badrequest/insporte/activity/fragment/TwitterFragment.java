package br.com.badrequest.insporte.activity.fragment;

import android.support.v4.app.Fragment;
import android.widget.TextView;
import br.com.badrequest.insporte.R;
import org.androidannotations.annotations.*;
import twitter4j.Status;

/**
 * Created by gmarques on 4/30/14.
 */
@EFragment(R.layout.twitter_fragment)
public class TwitterFragment extends Fragment {

    @FragmentArg
    Status mStatus;

    @ViewById
    TextView tvTwitter;

    @AfterViews
    void setupPage() {
        tvTwitter.setText(mStatus.getText());
    }

}
