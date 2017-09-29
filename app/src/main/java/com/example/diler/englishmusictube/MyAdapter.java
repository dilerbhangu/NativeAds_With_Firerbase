
package com.example.diler.englishmusictube;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.NativeExpressAdView;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;



public class MyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    List<Info> arrayList;
    DatabaseReference databaseReference;
    private static final int MENU_VIEW=0;
    private static final int AD_VIEW=1;
    NativeExpressAdView adView;
    Context context;
    int count=0;
    boolean value=true;

    MyAdapter(List<Info> arrayList,DatabaseReference databaseReference,Context context)
    {
        this.context=context;
        this.arrayList=arrayList;
        this.databaseReference=databaseReference;
        databaseReference.addChildEventListener(new ChildEvent());
        Log.i("bhangu", "MyAdapter calls");

    }



    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Log.i("bhangu", "On CReate View Holder calls");

        switch (viewType) {
            case MENU_VIEW:
                default:
                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_layout, parent, false);
                MyViewHolder myViewHolder = new MyViewHolder(v);
                return myViewHolder;

            case AD_VIEW:
                View v1=LayoutInflater.from(parent.getContext()).inflate(R.layout.ad_container,parent,false);
                AdViewHolder adViewHolder=new AdViewHolder(v1);
                return adViewHolder;
        }

    }



    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Log.i("bhangu", "On Bind View Holder");


        int viewType=getItemViewType(position);
        if(viewType==MENU_VIEW) {
            MyViewHolder myViewHolder = (MyViewHolder) holder;
            Info info = arrayList.get(position);
            myViewHolder.textView.setText(info.getName());

        }
    }

    @Override
    public int getItemCount() {
        Log.i("bhangu", "Get Item Count called");

        return arrayList.size();
    }


    @Override
    public int getItemViewType(int position) {

        Log.i("bhangu", "Get Item View Type called");



        if (position%8==0) {
            return AD_VIEW;
        }

        return MENU_VIEW;

    }

    public  class MyViewHolder extends RecyclerView.ViewHolder{

        TextView textView;

        public MyViewHolder(View itemView) {
            super(itemView);
            textView= (TextView) itemView.findViewById(R.id.textView);
        }
    }

    public class AdViewHolder extends RecyclerView.ViewHolder{

        private NativeExpressAdView mNativeAd;
        LinearLayout linearLayout;
        DisplayMetrics displayMetrics =  context.getResources().getDisplayMetrics();
        float deviceWidthInDp = displayMetrics.widthPixels / displayMetrics.density;
        int adWidth = (int)(deviceWidthInDp);

        AdSize adSize=new AdSize(adWidth,150);
        public AdViewHolder(final View itemView) {
            super(itemView);

            linearLayout= (LinearLayout) itemView;
            linearLayout.removeAllViews();

           mNativeAd=new NativeExpressAdView(itemView.getContext());
            if(mNativeAd.getParent()!=null)
            {
                ((ViewGroup)mNativeAd.getParent()).removeView(mNativeAd);
            }
            mNativeAd.setAdUnitId("ca-app-pub-3940256099942544/2793859312");
            mNativeAd.setAdSize(adSize);
            mNativeAd.setAdListener(new AdListener() {
                @Override
                public void onAdLoaded() {
                    super.onAdLoaded();
                    linearLayout.addView(mNativeAd);
                    notifyDataSetChanged();
                }

                @Override
                public void onAdClosed() {
                    super.onAdClosed();
                }

                @Override
                public void onAdFailedToLoad(int errorCode) {
                    super.onAdFailedToLoad(errorCode);
                }

                @Override
                public void onAdLeftApplication() {
                    super.onAdLeftApplication();

                }

                @Override
                public void onAdOpened() {
                    super.onAdOpened();

                }
            });

            AdRequest adRequest = new AdRequest.Builder()
                    .build();

            mNativeAd.loadAd(adRequest);

        }
    }

    class ChildEvent implements ChildEventListener{

        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            Info info=dataSnapshot.getValue(Info.class);
            info.setKey(dataSnapshot.getKey());
            if(count==0 || count%8==0) {
                arrayList.add(count,null);
                arrayList.add(count+1,info);
                count=count+2;
                notifyDataSetChanged();
                return;
            }else {
                arrayList.add(count,info);
                count=count+1;
                notifyDataSetChanged();
            }

        }

        @Override
        public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            String key=dataSnapshot.getKey();
            Info newInfo=dataSnapshot.getValue(Info.class);
            for(Info i:arrayList)
            {
                if(key.equals(i.getKey()))
                {
                    i.setValues(newInfo);
                    notifyDataSetChanged();
                    break;
                }
            }


        }

        @Override
        public void onChildRemoved(DataSnapshot dataSnapshot) {
            String key=dataSnapshot.getKey();

            for(Info i:arrayList)
            {
                if(key.equals(i.getKey()))
                {
                    arrayList.remove(i);
                    notifyDataSetChanged();
                    break;
                }
            }
        }

        @Override
        public void onChildMoved(DataSnapshot dataSnapshot, String s) {

        }



        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    }





}
