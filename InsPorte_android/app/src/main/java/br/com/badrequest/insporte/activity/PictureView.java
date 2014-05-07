package br.com.badrequest.insporte.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.BitmapFactory;
import android.widget.ImageView;
import br.com.badrequest.insporte.R;
import br.com.badrequest.insporte.activity.base.FullTranslucentActivity;
import br.com.badrequest.insporte.util.BitmapUtils;
import org.androidannotations.annotations.*;
import uk.co.senab.photoview.PhotoViewAttacher;

@OptionsMenu(R.menu.picture_view)
@EActivity(R.layout.picture_view_activity)
public class PictureView extends FullTranslucentActivity {

    public static final String PERGUNTA_INTENT_EXTRA = "pergunta_extra";
    public static final int RESULT_IMAGE_DELETED = 2000;

    PhotoViewAttacher mAttacher;

    @ViewById
    ImageView imageView;

    @Extra(PERGUNTA_INTENT_EXTRA)
    String imgPath;

    @AfterViews
    void afterViews() {
        super.systemBarTint();

        imageView.setImageBitmap(BitmapFactory.decodeFile(imgPath));
        mAttacher = new PhotoViewAttacher(imageView);
        mAttacher.setZoomable(true);
        mAttacher.setScaleType(ImageView.ScaleType.FIT_CENTER);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mAttacher.cleanup();
        BitmapUtils.stripImageView(imageView);
    }

    @OptionsItem(R.id.action_delete)
    void deleteFoto() {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        BitmapUtils.deleteImage(imgPath);
                        setResult(RESULT_IMAGE_DELETED);
                        finish();
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Deseja excluir a foto?")
                .setPositiveButton("Sim", dialogClickListener)
                .setNegativeButton("Não", dialogClickListener)
                .show();

    }
}
