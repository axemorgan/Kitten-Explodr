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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

class CatAdapter extends RecyclerView.Adapter<CatAdapter.CatViewHolder> {

    private final View.OnClickListener onClickListener;
    private List<KittenModel> kittens;

    CatAdapter(@NonNull View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
        this.kittens = new ArrayList<>();
        kittens.add(new KittenModel());
        kittens.add(new KittenModel());
        kittens.add(new KittenModel());
        kittens.add(new KittenModel());

        this.setHasStableIds(true);
    }

    @Override
    public CatViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_cell_cat, parent, false);
        return new CatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final CatViewHolder holder, final int position) {
        final KittenModel kitten = this.kittens.get(position);

        Picasso.with(holder.itemView.getContext())
                .load(kitten.getIamgeUrl())
                .into(holder.target);
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        kittens.remove(holder.getAdapterPosition());
                        notifyItemRemoved(holder.getAdapterPosition());
                    }
                }, 0x75);
                onClickListener.onClick(view);
            }
        });

        if (!kitten.isExploded()) {
            this.reset(holder.itemView);
        }
    }

    @Override
    public long getItemId(int position) {
        return kittens.get(position).getId();
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
        return kittens.size();
    }

    void addMoreKittens(@NonNull Collection<KittenModel> moreKittens) {
        int startingSize = kittens.size();
        kittens.addAll(moreKittens);

        this.notifyItemRangeInserted(startingSize, kittens.size() - startingSize);
    }

    void reset() {
        kittens.clear();
        kittens.add(new KittenModel());
        kittens.add(new KittenModel());
        kittens.add(new KittenModel());
        kittens.add(new KittenModel());
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
