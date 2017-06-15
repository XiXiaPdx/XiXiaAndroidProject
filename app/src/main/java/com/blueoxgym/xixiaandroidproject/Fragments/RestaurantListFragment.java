package com.blueoxgym.xixiaandroidproject.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.blueoxgym.xixiaandroidproject.Adapters.RestaurantListAdapter;
import com.blueoxgym.xixiaandroidproject.Interfaces.adapterToFragmentListener;
import com.blueoxgym.xixiaandroidproject.MainActivity;
import com.blueoxgym.xixiaandroidproject.Models.Restaurant;
import com.blueoxgym.xixiaandroidproject.R;

import org.parceler.Parcels;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class RestaurantListFragment extends Fragment implements adapterToFragmentListener {
    public ArrayList<Restaurant> mRestaurants;
    public RestaurantListAdapter mAdapter;
    public RecyclerView mRestaurantRecyclerView;
    public View fragmentView;

    public RestaurantListFragment() {
        // Required empty public constructor
    }

    public static RestaurantListFragment newInstance(ArrayList<Restaurant> restaurants){
        RestaurantListFragment newFragment = new RestaurantListFragment();
        Bundle args = new Bundle();
        args.putParcelable("restaurants", Parcels.wrap(restaurants));
        newFragment.setArguments(args);
        return newFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


         fragmentView =  inflater.inflate(R.layout.fragment_restaurant_list, container, false);
        mRestaurantRecyclerView = (RecyclerView) fragmentView.findViewById(R.id.searchRecyclerView);

        return fragmentView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        mRestaurants = Parcels.unwrap(getArguments().getParcelable("restaurants"));
    }

    @Override
    public void onStart(){
        super.onStart();
        fillRecycler();
    }

    public void fillRecycler(){
        mAdapter = new RestaurantListAdapter(getActivity(), mRestaurants, this);
        mRestaurantRecyclerView.setAdapter(mAdapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRestaurantRecyclerView.setLayoutManager(layoutManager);
        mRestaurantRecyclerView.setHasFixedSize(true);
    }

    @Override
    public void sendMessage(String message) {
        Log.d("Restaurant Fragment ", message);
    }
}
