package cardinalsolutions.com.kittenexplodr;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

class CatAdapter extends RecyclerView.Adapter<CatAdapter.CatViewHolder> {

    private final View.OnClickListener onClickListener;

    int[] images = {R.drawable.kitten1, R.drawable.kitten2, R.drawable.kitten3, R.drawable.kitten4};
    boolean[] exploded = new boolean[images.length]; //Inits to false by default

    CatAdapter(@NonNull View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    @Override
    public CatViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_cell_cat, parent, false);
        return new CatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CatViewHolder holder, int position) {

        Picasso.with(holder.itemView.getContext())
                .load(images[position])
                .into(holder.target);
        holder.imageView.setOnClickListener(this.onClickListener);

        if (!exploded[position]) {
            this.reset(holder.itemView);
        }
    }

    private void reset(View root) {
        if (root instanceof ViewGroup) {
            ViewGroup parent = (ViewGroup) root;
            for (int i = 0; i < parent.getChildCount(); i++) {
                reset(parent.getChildAt(i));
            }
        } else {
            root.setScaleX(1);
            root.setScaleY(1);
            root.setAlpha(1);
        }
    }

    @Override
    public int getItemCount() {
        return images.length;
    }

    void reset() {
        this.exploded = new boolean[images.length];
        this.notifyDataSetChanged();
    }


    static class CatViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        CardView cardView;

        Target target;

        CatViewHolder(final View itemView) {
            super(itemView);
            this.imageView = (ImageView) itemView.findViewById(R.id.catImage);
            this.cardView = (CardView) itemView.findViewById(R.id.card_view);

            target = new Target() {
                @Override
                public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                    imageView.setImageBitmap(bitmap);

                    int defaultBackground = itemView.getContext().getResources().getColor(android.R.color.white);
                    // TODO: it's recommended to generate Palettes on a background thread
                    int background = new Palette.Builder(bitmap).generate().getLightMutedColor(defaultBackground);

                    cardView.setBackgroundColor(background);
                }

                @Override
                public void onBitmapFailed(Drawable errorDrawable) {
                    imageView.setImageDrawable(errorDrawable);
                }

                @Override
                public void onPrepareLoad(Drawable placeHolderDrawable) {
                    imageView.setImageDrawable(placeHolderDrawable);
                }
            };
        }
    }
}
