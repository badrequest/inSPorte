package br.com.badrequest.insporte.activity.fragment;

import android.support.v4.app.Fragment;
import android.widget.ImageView;
import android.widget.TextView;
import br.com.badrequest.insporte.R;
import br.com.badrequest.insporte.bean.WizardPage;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.ViewById;

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
