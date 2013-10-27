package br.com.badrequest.insporte.activity.fragment;

import android.graphics.Bitmap;
import android.support.v4.app.Fragment;
import android.widget.ImageView;
import android.widget.TextView;
import br.com.badrequest.insporte.R;
import br.com.badrequest.insporte.beans.WizardPage;
import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.EFragment;
import com.googlecode.androidannotations.annotations.FragmentArg;
import com.googlecode.androidannotations.annotations.ViewById;

@EFragment(R.layout.wizard_fragment)
public class WizardFragment extends Fragment {

    @FragmentArg
    WizardPage page;

    @ViewById
    TextView title1;

    @ViewById
    TextView title2;

    @ViewById
    ImageView image;

    @AfterViews
    void afterViews() {
        title1.setText(page.getText1());
        title2.setText(page.getText2());
        image.setImageResource(page.getImageResource());
    }
}
