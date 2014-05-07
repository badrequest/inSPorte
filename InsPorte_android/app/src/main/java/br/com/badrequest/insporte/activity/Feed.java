package br.com.badrequest.insporte.activity;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import br.com.badrequest.insporte.R;
import br.com.badrequest.insporte.activity.base.FullTranslucentActivity;
import br.com.badrequest.insporte.adapter.TwitterPagerAdapter;
import com.nvanbenschoten.motion.ParallaxImageView;
import org.androidannotations.annotations.*;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

import java.util.List;

@WindowFeature(Window.FEATURE_NO_TITLE)
@EActivity(R.layout.feed_activity)
public class Feed extends FullTranslucentActivity {

    @ViewById
    ViewPager pager;

    @ViewById
    ParallaxImageView background;

    @ViewById
    ImageView twitterBird;

    @Override
    protected void onStart() {
        super.onStart();
        if(background != null) background.registerSensorManager();
    }

    @Override
    protected void onStop() {
        super.onStop();
        background.unregisterSensorManager();
    }

    @AfterViews
    void afterViews() {
        background.registerSensorManager();
        getTwitter();
    }

    @Background
    void getTwitter() {
        try {
            ConfigurationBuilder cb = new ConfigurationBuilder();
            cb.setDebugEnabled(true)
                    .setOAuthConsumerKey(getString(R.string.twitter_consumer_key))
                    .setOAuthConsumerSecret(getString(R.string.twitter_consumer_secret))
                    .setApplicationOnlyAuthEnabled(true);
            TwitterFactory tf = new TwitterFactory(cb.build());
            Twitter twitter = tf.getInstance();
            twitter.getOAuth2Token();

            List<Status> statuses = twitter.getUserTimeline("sptrans_");
            updateFeeds(statuses);
        } catch (TwitterException e) {
            e.printStackTrace();
        }
    }

    @UiThread
    void updateFeeds(List<Status> statuses) {
        if(!statuses.isEmpty()) {
            twitterBird.setVisibility(View.VISIBLE);
            pager.setAdapter(new TwitterPagerAdapter(getSupportFragmentManager(), statuses));
            circleTwitter();
        }
    }

    @UiThread(delay = 4000)
    void circleTwitter() {
        if(pager.getCurrentItem()+1 < pager.getAdapter().getCount()) {
            pager.setCurrentItem(pager.getCurrentItem()+1, true);
        }
        circleTwitter();
    }

//    @Click(R.id.imageButtonHistorico)
//    void teste() {
//        if(locationServiceBound) {
//            int num = mLocationService.getRandomNumber();
//            Toast.makeText(this, "number: " + num, Toast.LENGTH_SHORT).show();
//        }
//    }

    @Click(R.id.imageButtonAvaliar)
    void survey() {
        startActivity(new Intent(this, Routes_.class));
    }

    @Click(R.id.imageButtonSobre)
    void about() {
        startActivity(new Intent(this, About_.class));
    }

    @Click(R.id.imageButtonHistorico)
    void historico() {
        startActivity(new Intent(this, History_.class));
    }
}
