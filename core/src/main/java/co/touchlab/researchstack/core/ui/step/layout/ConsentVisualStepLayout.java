package co.touchlab.researchstack.core.ui.step.layout;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.PorterDuff;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jakewharton.rxbinding.view.RxView;

import co.touchlab.researchstack.core.R;
import co.touchlab.researchstack.core.model.ConsentSection;
import co.touchlab.researchstack.core.result.StepResult;
import co.touchlab.researchstack.core.step.ConsentVisualStep;
import co.touchlab.researchstack.core.step.Step;
import co.touchlab.researchstack.core.ui.ViewWebDocumentActivity;
import co.touchlab.researchstack.core.ui.callbacks.SceneCallbacks;
import co.touchlab.researchstack.core.ui.views.SubmitBar;
import co.touchlab.researchstack.core.utils.ResUtils;

public class ConsentVisualStepLayout extends RelativeLayout implements StepLayout
{

    private SceneCallbacks    callbacks;
    private ConsentVisualStep step;

    public ConsentVisualStepLayout(Context context)
    {
        super(context);
    }

    public ConsentVisualStepLayout(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public ConsentVisualStepLayout(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
    }


    @Override
    public void initialize(Step step, StepResult result)
    {
        this.step = (ConsentVisualStep) step;
        initializeScene();
    }

    @Override
    public View getLayout()
    {
        return this;
    }

    @Override
    public boolean isBackEventConsumed()
    {
        callbacks.onSaveStep(SceneCallbacks.ACTION_PREV, step, null);
        return false;
    }

    @Override
    public void setCallbacks(SceneCallbacks callbacks)
    {
        this.callbacks = callbacks;
    }

    private void initializeScene()
    {
        LayoutInflater.from(getContext()).inflate(R.layout.scene_consent_visual, this, true);

        ConsentSection data = step.getSection();

        // Set Image
        TypedValue typedValue = new TypedValue();
        TypedArray a = getContext().obtainStyledAttributes(typedValue.data,
                new int[] {R.attr.colorAccent});
        int accentColor = a.getColor(0, 0);
        a.recycle();

        ImageView imageView = (ImageView) findViewById(R.id.image);
        imageView.setColorFilter(accentColor, PorterDuff.Mode.ADD);

        String imageName = ! TextUtils.isEmpty(data.getCustomImageName())
                ? data.getCustomImageName()
                : data.getType().getImageName();

        if(! TextUtils.isEmpty(imageName))
        {
            int imageResId = ResUtils.getDrawableResourceId(getContext(), imageName);
            imageView.setImageResource(imageResId);
            imageView.setVisibility(View.VISIBLE);
        }
        else
        {
            imageView.setVisibility(View.GONE);
        }

        // Set Title
        TextView titleView = (TextView) findViewById(R.id.title);
        String title = TextUtils.isEmpty(data.getTitle()) ? getResources().getString(data.getType()
                .getTitleResId()) : data.getTitle();
        titleView.setText(title);

        // Set Summary
        TextView summaryView = (TextView) findViewById(R.id.summary);
        summaryView.setText(data.getSummary());

        // Set more info
        TextView moreInfoView = (TextView) findViewById(R.id.more_info);
        moreInfoView.setText(data.getType().getMoreInfoResId());
        RxView.clicks(moreInfoView).subscribe(v -> {
            String path = data.getHtmlContent();
            String webTitle = getResources().getString(R.string.rsc_consent_section_more_info);
            Intent webDoc = ViewWebDocumentActivity.newIntent(getContext(), webTitle, path);
            getContext().startActivity(webDoc);
        });

        SubmitBar submitBar = (SubmitBar) findViewById(R.id.submit_bar);
        submitBar.setPositiveAction(step.getNextButtonString(),
                (v -> callbacks.onSaveStep(SceneCallbacks.ACTION_NEXT, step, null)));
        submitBar.setExitAction((v -> callbacks.onSaveStep(SceneCallbacks.ACTION_END, step, null)));
    }
}
