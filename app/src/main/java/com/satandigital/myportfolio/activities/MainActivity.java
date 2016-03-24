package com.satandigital.myportfolio.activities;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.satandigital.myportfolio.R;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Sanat Dutta on 3/24/2016.
 */
public class MainActivity extends AppCompatActivity {

    private String TAG = "MainActivity";

    //Views
    private ListView mListView;

    //Data
    private List<String> mAppNames;
    private List<String> mAppDrawables;
    private List<String> mAppLinks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        findViews();
        getStrings();
        appsAdapter mAdapter = new appsAdapter();
        mListView.setAdapter(mAdapter);
    }

    private void getStrings() {
        mAppNames = Arrays.asList(getResources().getStringArray(R.array.apps));
        mAppDrawables = Arrays.asList(getResources().getStringArray(R.array.apps_drawable));
        mAppLinks = Arrays.asList(getResources().getStringArray(R.array.apps_links));
    }

    private void findViews() {
        mListView = (ListView) findViewById(R.id.list_view);
    }

    private class appsAdapter extends BaseAdapter {

        private LayoutInflater mLayoutInflater;

        private appsAdapter() {
            mLayoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return mAppNames.size();
        }

        @Override
        public Object getItem(int position) {
            return mAppNames.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View mView;
            final boolean activeApp = !(position > (mAppDrawables.size() - 1));

            if (activeApp)
                mView = mLayoutInflater.inflate(R.layout.item_list_active, parent, false);
            else mView = mLayoutInflater.inflate(R.layout.item_list_inactive, parent, false);

            ViewHolder mViewHolder = new ViewHolder();

            if (activeApp) mViewHolder.appImage = (ImageView) mView.findViewById(R.id.app_image);
            mViewHolder.appName = (TextView) mView.findViewById(R.id.app_name);

            if (activeApp)
                mViewHolder.appImage.setImageResource(getResources().getIdentifier("drawable/ic_app_" + mAppDrawables.get(position), null, getPackageName()));
            mViewHolder.appName.setText(mAppNames.get(position));

            mViewHolder.appName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (activeApp) {
                        Intent mIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(mAppLinks.get(position)));
                        startActivity(mIntent);
                    } else
                        Toast.makeText(MainActivity.this, mAppNames.get(position) + " is yet to be developed", Toast.LENGTH_SHORT).show();
                }
            });

            return mView;
        }

        private class ViewHolder {
            private ImageView appImage;
            private TextView appName;
        }
    }
}
