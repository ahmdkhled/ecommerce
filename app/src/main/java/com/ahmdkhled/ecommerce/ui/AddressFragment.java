package com.ahmdkhled.ecommerce.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ahmdkhled.ecommerce.R;
import com.ahmdkhled.ecommerce.adapter.AddressAdapter;
import com.ahmdkhled.ecommerce.model.Address;
import com.ahmdkhled.ecommerce.viewmodel.CheckoutViewModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddressFragment extends Fragment {


    private static final String TAG = "ADDRESS_FRAGMENT";
    @BindView(R.id.add_address_txt)
    TextView mAddAddressTxt;
    @BindView(R.id.address_fragment_layout)
    ConstraintLayout mAddressFragmentLayout;
    @BindView(R.id.progressBar)
    ProgressBar mProgressBar;
    @BindView(R.id.address_recycler_view)
    RecyclerView mAddressRecyclerView;

    private AddressAdapter mAddressAdapter;



    CheckoutViewModel mCheckoutViewModel;
    private String userId = "37";


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.address_fragment,container,false);

        // bind views
        ButterKnife.bind(this,view);

        // set Roboto font to text view
        setupViewFont();

        // view model
        mCheckoutViewModel = ViewModelProviders.of(this).get(CheckoutViewModel.class);

        // load address
        mCheckoutViewModel.loadAddress(userId,null);

        // observe loading address response
        mCheckoutViewModel.getAddress().observe(this, new Observer<List<Address>>() {
            @Override
            public void onChanged(@Nullable List<Address> addresses) {
                Log.d(TAG,"get address");
                if(addresses != null && addresses.size() > 0){
                    mAddressAdapter.notifyAdapter(addresses);
                }else Log.d(TAG,"null address list");
            }
        });


        // observe loading address status
        mCheckoutViewModel.getIsAddressLoading().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                if(aBoolean){
                    showProgressBar();
                    hideLayout();
                }else {
                    hideProgressBar();
                    showLayout();
                }
            }
        });


        setupRecyclerView();


        return view;
    }

    private void setupRecyclerView() {
        mAddressAdapter = new AddressAdapter(getContext(),null,"");
        mAddressRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAddressRecyclerView.setAdapter(mAddressAdapter);
        mAddressRecyclerView.setHasFixedSize(true);
    }



    private void setupViewFont() {

        mAddAddressTxt.setTypeface(Typeface.createFromAsset(getContext().getAssets()
                ,getString(R.string.roboto_black)));





    }


    public void showProgressBar(){
        mProgressBar.setVisibility(View.VISIBLE);
    }

    public void hideProgressBar(){
        mProgressBar.setVisibility(View.INVISIBLE);
    }

    public void showLayout(){
        mAddressFragmentLayout.setVisibility(View.VISIBLE);
    }

    public void hideLayout(){
        mAddressFragmentLayout.setVisibility(View.INVISIBLE);
    }
}
