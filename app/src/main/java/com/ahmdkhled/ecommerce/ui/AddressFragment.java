package com.ahmdkhled.ecommerce.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.LoginFilter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.ahmdkhled.ecommerce.R;
import com.ahmdkhled.ecommerce.adapter.AddressAdapter;
import com.ahmdkhled.ecommerce.model.Address;
import com.ahmdkhled.ecommerce.model.AddressItem;
import com.ahmdkhled.ecommerce.model.Response;
import com.ahmdkhled.ecommerce.utils.AddressCommunication;
import com.ahmdkhled.ecommerce.viewmodel.AddAddressViewModel;
import com.ahmdkhled.ecommerce.viewmodel.AddressViewModel;
import com.ahmdkhled.ecommerce.viewmodel.CheckoutViewModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.app.Activity.RESULT_OK;

public class AddressFragment extends Fragment {


    private static final String TAG = "ADDRESS_FRAGMENT";
    private static final int EDIT_ADDRESS_REQUEST_CODE = 1006;
    private static final int SELECT_ADDRESS_REQUEST_CODE = 1007;
    private static final int ADD_ADDRESS_REQUESTED_CODE = 1008;

    @BindView(R.id.add_address_txt)
    TextView mAddAddressTxt;
    @BindView(R.id.address_layout)
    ConstraintLayout mAddressFragmentLayout;
    @BindView(R.id.progressBar)
    ProgressBar mProgressBar;
    @BindView(R.id.address_user_name)
    TextView mUserNameTxt;
    @BindView(R.id.address_address_1)
    TextView mAddress1Txt;
    @BindView(R.id.address_address_2)
    TextView mAddress2Txt;
    @BindView(R.id.address_mobile_number)
    TextView mMobileNumberTxt;
    @BindView(R.id.edit_address_image_btn)
    ImageButton mEditAddressBtn;
    @BindView(R.id.delete_address_image_btn)
    ImageButton mDeleteAddressBtn;




    AddAddressViewModel mAddAddressViewModel;
    AddressViewModel mAddressViewModel;
    private String userId = "2";
    private boolean userHasAddress;
    private Address mAddress;
    private int editedAddressPosition;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.address_fragment,container,false);

        // bind views
        ButterKnife.bind(this,view);

        // set Roboto font to text view
        setupViewFont();



        // view model
        mAddAddressViewModel = ViewModelProviders.of(getActivity()).get(AddAddressViewModel.class);
        mAddressViewModel = ViewModelProviders.of(getActivity()).get(AddressViewModel.class);
        mAddressViewModel.init();



        // load address
        mAddressViewModel.loadAddresses(userId,"1"); // 1 means default address.

        // observe loading address response
        mAddressViewModel.getAddressList().observe(getActivity(), new Observer<List<Address>>() {
            @Override
            public void onChanged(@Nullable List<Address> addresses) {
                if(addresses != null && addresses.size() > 0){
                    mAddress = addresses.get(0);
                    fillAddressViews(addresses.get(0));
                    userHasAddress = true;
                    mAddAddressTxt.setText("Choose Another Address");
                }else {
                    // no addresses
                    userHasAddress = false;
                    mAddAddressTxt.setText("Add New Address");

                }
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
                if(userHasAddress){
                    // add new address
                    chooseNewAddress();
                }else{
                    // add new address
                    addNewAddress();
                }
            }
        });



        mEditAddressBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editAddress(mAddress);
            }
        });

        mDeleteAddressBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteAddress(mAddress);
            }
        });


        return view;
    }

    private void addNewAddress() {
        Intent intent = new Intent(getActivity(),AddAddressActivity.class);
        startActivityForResult(intent,ADD_ADDRESS_REQUESTED_CODE);
    }

    private void fillAddressViews(Address address) {
        mUserNameTxt.setText(address.getFirst_name()+" "+address.getLast_name());
        mAddress1Txt.setText(address.getAddress_1());
        mAddress2Txt.setText(address.getAddress_2());
        mMobileNumberTxt.setText(address.getPhone_number());

    }

    private void chooseNewAddress() {
        Intent chooseAddressIntent = new Intent(getContext(), AddressActivity.class);
        chooseAddressIntent.putExtra("source","checkout");
        startActivityForResult(chooseAddressIntent,SELECT_ADDRESS_REQUEST_CODE);
    }


    private void setupViewFont() {
        mAddAddressTxt.setTypeface(Typeface.createFromAsset(getContext().getAssets()
                ,getString(R.string.roboto_black)));
        mUserNameTxt.setTypeface(Typeface.createFromAsset(getContext().getAssets()
                ,getContext().getString(R.string.roboto_medium)));
        mAddress1Txt.setTypeface(Typeface.createFromAsset(getContext().getAssets()
                ,getContext().getString(R.string.roboto_light)));
        mAddress2Txt.setTypeface(Typeface.createFromAsset(getContext().getAssets()
                ,getContext().getString(R.string.roboto_light)));
        mMobileNumberTxt.setTypeface(Typeface.createFromAsset(getContext().getAssets()
                ,getContext().getString(R.string.roboto_black)));
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
            if(requestCode == SELECT_ADDRESS_REQUEST_CODE){
                Address address = data.getParcelableExtra("selected_address");
                fillAddressViews(address);
            }

            else if(requestCode == EDIT_ADDRESS_REQUEST_CODE){
                Address address = data.getParcelableExtra("new_address");
                fillAddressViews(address);
            }

            else if(requestCode == ADD_ADDRESS_REQUESTED_CODE){
                Address address = data.getParcelableExtra("new_address");
                fillAddressViews(address);
            }
        }
    }




    private void editAddress(Address address) {
        if(address != null){
            Intent editAddressIntent = new Intent(getActivity(),AddAddressActivity.class);
            editAddressIntent.putExtra("edit_address",address);
            startActivityForResult(editAddressIntent,EDIT_ADDRESS_REQUEST_CODE);
        }
    }


    private void deleteAddress( Address address) {
        mAddressViewModel.deleteAddress(address);
        mAddressViewModel.getDeleteResponse().observe(getActivity(), new Observer<Response>() {
            @Override
            public void onChanged(@Nullable Response response) {
                if(response != null){
                    Log.d("address_fragment","address deleted "+response.getMessage());
                    if(!response.isError()){

                    }
                }

            }
        });
    }


}
