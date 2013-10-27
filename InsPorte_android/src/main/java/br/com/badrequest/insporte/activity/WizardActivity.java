package br.com.badrequest.insporte.activity;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.widget.Button;
import android.widget.Toast;
import br.com.badrequest.insporte.R;
import br.com.badrequest.insporte.adapter.WizardPagerAdapter;
import br.com.badrequest.insporte.beans.WizardPage;
import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Click;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.ViewById;
import com.viewpagerindicator.UnderlinePageIndicator;

import java.util.ArrayList;
import java.util.List;

@EActivity(R.layout.wizard_activity)
public class WizardActivity extends ActionBarActivity implements ViewPager.OnPageChangeListener {

    @ViewById
    ViewPager pager;

    @ViewById
    UnderlinePageIndicator indicator;

    @ViewById
    Button btnNext;

    @AfterViews
    void afterViews() {
        List<WizardPage> wizardPages = new ArrayList<WizardPage>();
        wizardPages.add(new WizardPage(R.drawable.ic_informante, "Seja um informante", "Denuncie os problemas com o transporte coletivo e ajude na construção de uma São Paulo melhor."));
        wizardPages.add(new WizardPage(R.drawable.ic_notificacao, "Receba notificações", "Você será notificado em tempo real sobre qualquer informação relevante ao seu transporte diário."));
        wizardPages.add(new WizardPage(R.drawable.ic_cidadao, "Seja um cidadão consciente", "Ajude sua cidade e viva melhor."));

        pager.setAdapter(new WizardPagerAdapter(getSupportFragmentManager(), wizardPages));
        indicator.setOnPageChangeListener(this);
        indicator.setViewPager(pager);
        indicator.notifyDataSetChanged();
    }

    @Click(R.id.btnNext)
    void next() {
        if(pager.getCurrentItem() == pager.getAdapter().getCount()-1) {
            startActivity(new Intent(this, Login_.class));
        } else {
            pager.setCurrentItem(pager.getCurrentItem()+1, true);
        }
    }

    @Override
    public void onPageSelected(int position) {
        if(position == pager.getAdapter().getCount()-1) {
            btnNext.setText(getResources().getText(R.string.finish));
        } else {
            btnNext.setText(getResources().getText(R.string.next));
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }
}
