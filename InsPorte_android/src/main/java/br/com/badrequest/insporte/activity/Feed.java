package br.com.badrequest.insporte.activity;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import br.com.badrequest.insporte.R;
import br.com.badrequest.insporte.adapter.FeedListAdapter;
import org.androidannotations.annotations.*;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

import java.util.List;

@EActivity(R.layout.feed_activity)
public class Feed extends TranslucentActivity {

    @ViewById
    ProgressBar progressBar;

    @ViewById
    ListView list;

    @AfterViews
    void afterViews() {
        getTwitter();
    }

    @Background
    void getTwitter() {
        try {
            ConfigurationBuilder cb = new ConfigurationBuilder();
            cb.setDebugEnabled(true)
                    .setOAuthConsumerKey("tyVFkClEMvNhk9wXOzRFA")
                    .setOAuthConsumerSecret("Idp6VGJ0Fo5ASZ1qjrOlDyi8AdkPr5OAGnjeKKDVZQ")
                    .setOAuthAccessToken("43277931-5DGgSrw2wICiKEACrXLzOsCIVj979VQDtNUXOyuM1")
                    .setOAuthAccessTokenSecret("b2q8V8XAK45MI0f9cQcJmuOwch5jNbaAKjZyoq9BvQLjC");
            TwitterFactory tf = new TwitterFactory(cb.build());
            Twitter twitter = tf.getInstance();

            List<Status> statuses = twitter.getUserTimeline("sptrans_");
            updateFeeds(statuses);
        } catch (TwitterException e) {
            e.printStackTrace();
        }
    }

    @UiThread
    void updateFeeds(List<Status> statuses) {
        progressBar.setVisibility(View.GONE);
        list.setAdapter(new FeedListAdapter(this, R.layout.feed_line, statuses));
    }

    @Click(R.id.imageButtonAvaliar)
    void survey() {
        startActivity(new Intent(this, Routes_.class));
    }

    @Click(R.id.imageButtonSobre)
    void about() {
        startActivity(new Intent(this, About_.class));
    }

}
