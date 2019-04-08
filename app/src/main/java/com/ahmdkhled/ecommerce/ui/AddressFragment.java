package com.ahmdkhled.ecommerce.ui;

import android.app.AlertDialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.ahmdkhled.ecommerce.R;
import com.ahmdkhled.ecommerce.adapter.AddressAdapter;
import com.ahmdkhled.ecommerce.model.Address;
import com.ahmdkhled.ecommerce.model.AddressItem;
import com.ahmdkhled.ecommerce.model.Response;
import com.ahmdkhled.ecommerce.utils.AddressCommunication;
import com.ahmdkhled.ecommerce.utils.SessionManager;
import com.ahmdkhled.ecommerce.viewmodel.AddressViewModel;
import com.ahmdkhled.ecommerce.viewmodel.CheckoutViewModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.app.Activity.RESULT_OK;

public class AddressFragment extends Fragment implements AddressCommunication {


    private static final String TAG = "ADDRESS_FRAGMENT";
    private static final int EDIT_ADDRESS_REQUEST_CODE = 1006;
    private static final int ADD_ADDRESS_REQUESTED_CODE = 1008;

    @BindView(R.id.add_address_txt)
    TextView mAddAddressTxt;
    @BindView(R.id.address_layout)
    ConstraintLayout mAddressFragmentLayout;
    @BindView(R.id.progressBar)
    ProgressBar mProgressBar;
    @BindView(R.id.address_recycler_view)
    RecyclerView mAddressRecyclerView;



    AddressAdapter mAddressAdapter;
    AddressViewModel mAddressViewModel;
    private String userId;
    private int editedAddressPosition;
    private CheckoutViewModel mCheckOutViewModel;
    private int shippingAddress;
    private String source = "checkout";


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.address_fragment,container,false);

        // bind views
        ButterKnife.bind(this,view);

        // set Roboto font to text view
        setupViewFont();

        // get user id
        SessionManager sessionManager = new SessionManager(getContext());
        userId = String.valueOf(sessionManager.getId());


        Bundle bundle = getArguments();
        if(bundle != null && bundle.getString("source") != null){
            source = bundle.getString("source");
        }

        // view model
        mCheckOutViewModel = ViewModelProviders.of(getActivity()).get(CheckoutViewModel.class);
        mAddressViewModel = ViewModelProviders.of(getActivity()).get(AddressViewModel.class);
        mAddressViewModel.init();

        // load address
        mAddressViewModel.loadAddresses(userId,null);

        // observe loading address response
        mAddressViewModel.getAddressList().observe(getActivity(), new Observer<List<Address>>() {
            @Override
            public void onChanged(@Nullable List<Address> addresses) {
                Log.d("address_tag","getAddressList");
                if (addresses != null) {
                    Log.d("address_tag","getAddressList "+addresses.size());
                    if (addresses.size() > 0) {
                        Log.d("address_tag","getAddressList "+addresses.size());
                        mAddressAdapter.notifyAdapter(addresses);
                    } else {
                        // no addresses


                    }
                }else Toast.makeText(getContext(), getContext().getString(R.string.error_message), Toast.LENGTH_SHORT).show();
            }
        });


        // observe loading address status
        mAddressViewModel.isLoading().observe(getActivity(), new Observer<Boolean>() {
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



        mAddAddressTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addNewAddress();
            }
        });


        setupRecyclerView();

        return view;
    }

    private void setupRecyclerView() {
        mAddressAdapter = new AddressAdapter(getContext(),this,source);
        mAddressRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAddressRecyclerView.setHasFixedSize(true);
        mAddressRecyclerView.setAdapter(mAddressAdapter);
    }

    private void addNewAddress() {
        Intent intent = new Intent(getActivity(),AddAddressActivity.class);
        startActivityForResult(intent,ADD_ADDRESS_REQUESTED_CODE);
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK && data != null){
             if(requestCode == EDIT_ADDRESS_REQUEST_CODE){
                Address address = data.getParcelableExtra("new_address");
                mAddressAdapter.editAddress(address,editedAddressPosition);
            }

            else if(requestCode == ADD_ADDRESS_REQUESTED_CODE){
                Address address = data.getParcelableExtra("new_address");
                 mAddressAdapter.setSelectAddress(shippingAddress);
                mAddressAdapter.addAddress(address);
            }
        }
    }


    @Override
    public void selectAddress(AddressItem addressItem) {
       // save address into shared preferences
        if(addressItem != null && source.equals("checkout")) {
            shippingAddress = addressItem.getmAddress().getId();
            Log.d("address_tag", "address id " + shippingAddress);
            mCheckOutViewModel.setShippingAddress(addressItem.getmAddress().getId());
        }else editAddress(addressItem);
    }

    @Override
    public void editAddress(AddressItem addressItem) {
        if(addressItem != null){
            editedAddressPosition = addressItem.getPosition();
            Intent editAddressIntent = new Intent(getActivity(),AddAddressActivity.class);
            editAddressIntent.putExtra("edit_address",addressItem.getmAddress());
            startActivityForResult(editAddressIntent,EDIT_ADDRESS_REQUEST_CODE);
        }
    }

    @Override
    public void deleteAddress(final AddressItem addressItem) {
        if(addressItem != null){
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setMessage("Are you sure you want to delete this address ?");
            builder.setNegativeButton("Cancel",null);
            builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    mAddressViewModel.deleteAddress(addressItem.getmAddress());
                    mAddressViewModel.getDeleteResponse().observe(getActivity(), new Observer<Response>() {
                        @Override
                        public void onChanged(@Nullable Response response) {
                            if(response != null){
                                if(!response.isError()){
                                    if(addressItem.getmAddress().getId() == shippingAddress){
                                        shippingAddress = -1;
                                        mCheckOutViewModel.setShippingAddress(-1);
                                    }
                                    mAddressAdapter.setSelectAddress(shippingAddress);
                                    mAddressAdapter.removeAddress(addressItem.getPosition());
                                }
                            }else Toast.makeText(getActivity(), getString(R.string.error_message), Toast.LENGTH_SHORT).show();

                        }
                    });

                    mAddressViewModel.getIsDeleting().observe(getActivity(), new Observer<Boolean>() {
                        @Override
                        public void onChanged(@Nullable Boolean aBoolean) {
                            if(aBoolean)showProgressBar();
                            else hideProgressBar();
                        }
                    });
                }
            });

            builder.show();
        }

    }
}
