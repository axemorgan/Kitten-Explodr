package cardinalsolutions.com.kittenexplodr;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

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
        recycler.setLayoutManager(new LinearLayoutManager(this));
        this.adapter = new CatAdapter(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exploder.explode(v);
            }
        });
        recycler.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_reset) {
            exploder.clear();
            this.adapter.reset();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
