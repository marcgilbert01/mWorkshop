package marc.customviews;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

/**
 * Created by gilbertm on 01/03/2016.
 */


public class OpenRightViewHolder extends RecyclerView.ViewHolder{

    int position;
    FrameLayout frameLayout;
    OnOpenRightViewClickedListener onOpenRightViewClickedListener;

    public OpenRightViewHolder(View itemView) {

        super( new FrameLayout(itemView.getContext()));
        frameLayout = (FrameLayout) super.itemView;
        itemView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT ));
        frameLayout.addView(itemView);
        frameLayout.setLayoutParams( new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT , ViewGroup.LayoutParams.WRAP_CONTENT ));

    }

    public void setPosition(int position) {
        this.position = position;
    }

    public void setOnFrameLayoutClickedListener(final OnOpenRightViewClickedListener onOpenRightViewClickedListener){
        this.onOpenRightViewClickedListener = onOpenRightViewClickedListener;
        frameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onOpenRightViewClickedListener.onOpenRightViewClicked( frameLayout , position );
            }
        });
    }


    interface OnOpenRightViewClickedListener {

        public void onOpenRightViewClicked(ViewGroup viewGroup, int position);

    }


}



