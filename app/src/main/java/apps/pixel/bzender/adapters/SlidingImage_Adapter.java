package apps.pixel.bzender.adapters;

import android.content.Context;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import apps.pixel.bzender.R;
import apps.pixel.bzender.Utills.MyTextViewBold;


public class SlidingImage_Adapter extends PagerAdapter {


    private final List<String> urls;
    private final LayoutInflater inflater;
    private final Context context;
    private final OnClickHandler onClickHandler;
    private List<String> detailsUrl;


    public SlidingImage_Adapter(Context context, List<String> urls, List<String> detailsUrl, OnClickHandler onClickHandler) {
        this.context = context;
        this.urls = urls;
        this.detailsUrl = detailsUrl;
        inflater = LayoutInflater.from(context);
        this.onClickHandler = onClickHandler;
    }

    @Override
    public void destroyItem(@NotNull ViewGroup container, int position, @NotNull Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return urls.size();
    }

    @NotNull
    @Override
    public Object instantiateItem(@NotNull ViewGroup view, int position) {
        View imageLayout = inflater.inflate(R.layout.item_slider, view, false);

        assert imageLayout != null;
        final ImageView imageView = imageLayout
                .findViewById(R.id.img_view);

        MyTextViewBold txtDetails = imageLayout.findViewById(R.id.txt_details);
        txtDetails.setOnClickListener(v -> {
            onClickHandler.onItemSliderClicked(detailsUrl.get(position));
        });


        Glide.with(context)
                .load(urls.get(position))
                .placeholder(R.drawable.gry_image)
                .into(imageView);

        view.addView(imageLayout, 0);

        return imageLayout;
    }

    @Override
    public boolean isViewFromObject(@NotNull View view, @NotNull Object object) {
        return view.equals(object);
    }

    @Override
    public void restoreState(Parcelable state, ClassLoader loader) {
    }

    @Override
    public Parcelable saveState() {
        return null;
    }

    public interface OnClickHandler {
        void onItemSliderClicked(String url);
    }


}