package io.rainrobot.wake.android.util;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.function.Consumer;

import io.rainrobot.wake.android.activities.R;
import io.rainrobot.wake.core.util.OrderdMap;

public class CarouselStringActionAdapter<T> extends PagerAdapter {

    private final OrderdMap<T, String> values;
    private final Context context;

    public CarouselStringActionAdapter(OrderdMap<T, String> values, Context context) {
        this.values = values;
        this.context = context;
    }

    @Override
    public int getCount() {
        return values.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }

    @Override
    public int getItemPosition(Object object) {
        return values.indexOf((T) object);
    }

    @Override
    public Object instantiateItem(ViewGroup collection, int position) {
        TextView txtView = new TextView(context);
        txtView.setCompoundDrawablesWithIntrinsicBounds
                (R.drawable.up_down_arrow_vector, 0, 0, 0);
        txtView.setText(values.getValueAt(position));
        collection.addView(txtView);
        return txtView;
    }


    @Override
    public void destroyItem(ViewGroup collection, int position, Object view) {
        collection.removeView((View) view);
    }

    public T getItem(int i) { return values.getKeyAt(i); }
}
