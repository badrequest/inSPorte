package br.com.badrequest.insporte.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import br.com.badrequest.insporte.activity.fragment.TwitterFragment_;
import twitter4j.Status;

import java.util.List;

/**
 * Created by gmarques on 4/30/14.
 */
public class TwitterPagerAdapter extends FragmentPagerAdapter {

    private List<Status> mFeed;

    public TwitterPagerAdapter(FragmentManager fm, List<Status> feed) {
        super(fm);
        mFeed = feed;
    }

    @Override
    public Fragment getItem(int position) {
        return TwitterFragment_.builder()
                .mStatus(mFeed.get(position))
                .build();
    }

    @Override
    public int getCount() {
        return mFeed.size();
    }
}
