package br.com.badrequest.insporte.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import br.com.badrequest.insporte.activity.fragment.WizardFragment_;
import br.com.badrequest.insporte.bean.WizardPage;

import java.util.List;

public class WizardPagerAdapter extends FragmentPagerAdapter {

    private List<WizardPage> mWizardPages;

    public WizardPagerAdapter(FragmentManager fm, List<WizardPage> wizardPages) {
        super(fm);
        mWizardPages = wizardPages;
    }

    @Override
    public Fragment getItem(int position) {
        return  WizardFragment_.builder()
                .page(mWizardPages.get(position))
                .build();
    }

    @Override
    public int getCount() {
        return mWizardPages.size();
    }
}
