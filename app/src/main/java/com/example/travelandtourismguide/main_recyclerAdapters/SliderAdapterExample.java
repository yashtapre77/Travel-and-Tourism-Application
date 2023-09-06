package com.example.travelandtourismguide.main_recyclerAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.travelandtourismguide.R;
import com.smarteist.autoimageslider.SliderViewAdapter;

public class SliderAdapterExample extends
        SliderViewAdapter<SliderAdapterExample.SliderAdapterVH> {

    private Context context;
    private int[] ids;

    public SliderAdapterExample(Context context, int[] ids) {
        this.context = context;
        this.ids = ids;
    }

//    public void renewItems(List<SliderItem> sliderItems) {
//        this.mSliderItems = sliderItems;
//        notifyDataSetChanged();
//    }
//
//    public void deleteItem(int position) {
//        this.mSliderItems.remove(position);
//        notifyDataSetChanged();
//    }
//
//    public void addItem(SliderItem sliderItem) {
//        this.mSliderItems.add(sliderItem);
//        notifyDataSetChanged();
//    }

    @Override
    public SliderAdapterVH onCreateViewHolder(ViewGroup parent) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_main_top, parent,false);
        return new SliderAdapterVH(inflate);
    }

    @Override
    public void onBindViewHolder(SliderAdapterVH viewHolder, int position) {
        viewHolder.imageView.setImageResource(ids[position]);
    }



    @Override
    public int getCount() {
        //slider view count could be dynamic size
        return ids.length;
    }

    class SliderAdapterVH extends SliderViewAdapter.ViewHolder {

        View itemView;
        ImageView imageView;
//        ImageView imageGifContainer;

        public SliderAdapterVH(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.main_Top_slider_imageV);
//            imageGifContainer = itemView.findViewById(R.id.iv_gif_container);
            this.itemView = itemView;
        }
    }

}
