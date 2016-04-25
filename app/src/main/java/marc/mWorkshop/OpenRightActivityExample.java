package marc.mWorkshop;

import android.app.Fragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import marc.customviews.OpenRightView;
import marc.customviews.OpenRightViewHolder;


// TEST NEW REPOSITORY


public class OpenRightActivityExample extends AppCompatActivity implements OpenRightFragment.OnFragmentInteractionListener{

    OpenRightView openRightView;
    MyOpenRightAdapter myOpenRightAdapter;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_right_activity_example);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             //   Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            finish();
            }
        });

        this.context = this;
        // INIT OPEN RIGHT VIEW (SAME AS RECYCLERVIEW)
        openRightView = (OpenRightView) findViewById(R.id.openRightView);
        myOpenRightAdapter = new MyOpenRightAdapter();
        openRightView.setAdapter(myOpenRightAdapter);

    }

    @Override
    public void onFragmentInteraction(Uri uri) {
    }

    @Override
    public void closeFragment() {

        openRightView.goBack();
    }

    @Override
    public void onBackPressed() {

        openRightView.goBack();
    }



    public class MyOpenRightAdapter extends OpenRightView.OpenRightAdapter<MyOpenRightAdapter.MyViewHolder>{

        @Override
        public Fragment getFragment(int position) {

            OpenRightFragment openRightFragment = OpenRightFragment.newInstance( "Number "+ position );

            return openRightFragment;
        }

        @Override
        public MyViewHolder onCreateOpenRightViewHolder(ViewGroup parent, int viewType) {

            View view = LayoutInflater.from(context).inflate( R.layout.open_right_item_view, null );
            MyViewHolder myViewHolder = new MyViewHolder(view);

            return myViewHolder;
        }

        @Override
        public void onBindOpenRightViewHolder(MyViewHolder viewHolder, int position) {

            viewHolder.textView1.setText("Number ( " + position + " )");
            viewHolder.textView2.setText("Number ( " + position + " )");
        }

        @Override
        public int getItemCount() {

            return 50;
        }

        public class MyViewHolder extends OpenRightViewHolder {

            View itemView;
            TextView textView1;
            TextView textView2;

            public MyViewHolder(View itemView) {
                super(itemView);
                this.itemView = itemView;
                textView1 = (TextView) itemView.findViewById(R.id.textView1);
                textView2 = (TextView) itemView.findViewById(R.id.textView2);
            }
        }

    }






















}
