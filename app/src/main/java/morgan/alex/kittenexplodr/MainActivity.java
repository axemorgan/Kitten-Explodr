package morgan.alex.kittenexplodr;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.OnScrollListener;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;

import tyrantgit.explosionfield.ExplosionField;

public class MainActivity extends AppCompatActivity {

    private CatAdapter adapter;
    private ExplosionField exploder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        exploder = ExplosionField.attach2Window(this);

        RecyclerView recycler = (RecyclerView) this.findViewById(R.id.recycler);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recycler.addOnScrollListener(new OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0) {
                    int lastVisibleItem = layoutManager.findLastVisibleItemPosition();

                    int childCount = layoutManager.getItemCount();
                    if (lastVisibleItem + 1 >= childCount) {
                        ArrayList<KittenModel> moreKittens = new ArrayList<>(4);
                        moreKittens.add(new KittenModel());
                        moreKittens.add(new KittenModel());
                        moreKittens.add(new KittenModel());
                        moreKittens.add(new KittenModel());
                        adapter.addMoreKittens(moreKittens);
                    }
                }
            }
        });
        recycler.setLayoutManager(layoutManager);
        this.adapter = new CatAdapter(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exploder.explode(v);
            }
        });
        recycler.setAdapter(adapter);
        adapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {

            @Override
            public void onItemRangeRemoved(int positionStart, int itemCount) {
                if (adapter.getItemCount() <= layoutManager.findLastVisibleItemPosition() + 2) {
                    ArrayList<KittenModel> moreKittens = new ArrayList<>(4);
                    moreKittens.add(new KittenModel());
                    moreKittens.add(new KittenModel());
                    moreKittens.add(new KittenModel());
                    moreKittens.add(new KittenModel());
                    adapter.addMoreKittens(moreKittens);
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_reset) {
            exploder.clear();
            this.adapter.reset();
            return true;
        } else if (id == R.id.action_about) {
            AboutFragment.build().show(this.getSupportFragmentManager(), "About");
        }

        return super.onOptionsItemSelected(item);
    }
}
