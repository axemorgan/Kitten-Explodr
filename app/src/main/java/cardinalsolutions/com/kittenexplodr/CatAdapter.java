package cardinalsolutions.com.kittenexplodr;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

/**
 * Created by amorgan on 10/8/15.
 */
public class CatAdapter extends RecyclerView.Adapter<CatAdapter.CatViewHolder> {

    Context context;
    View.OnClickListener onClickListener;
    int[] images = {R.drawable.kitten1, R.drawable.kitten2, R.drawable.kitten3, R.drawable.kitten4};
    boolean[] exploded = new boolean[images.length]; //Inits to false by default

    public CatAdapter(Context context, View.OnClickListener onClickListener) {
        this.context = context;
        this.onClickListener = onClickListener;
    }

    @Override
    public CatViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_cell_cat, parent, false);
        return new CatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CatViewHolder holder, int position) {
        Picasso.with(context).load(images[position]).fit().centerCrop().into(holder.imageView);
        holder.imageView.setOnClickListener(this.onClickListener);

        if (!exploded[position]) {
            View root = holder.getView();
            this.reset(root);
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

    public class CatViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        View view;

        public CatViewHolder(View itemView) {
            super(itemView);
            this.imageView = (ImageView) itemView.findViewById(R.id.catImage);
            this.view = itemView;
        }

        public View getView() {
            return this.view;
        }

    }

    public void reset() {
        this.exploded = new boolean[images.length];
        this.notifyDataSetChanged();
    }
}
