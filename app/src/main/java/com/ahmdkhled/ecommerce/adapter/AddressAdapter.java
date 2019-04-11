package com.ahmdkhled.ecommerce.adapter;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.graphics.Typeface;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.TextView;

import com.ahmdkhled.ecommerce.R;
import com.ahmdkhled.ecommerce.model.Address;
import com.ahmdkhled.ecommerce.model.AddressItem;
import com.ahmdkhled.ecommerce.utils.AddressCommunication;
import com.ahmdkhled.ecommerce.viewmodel.AddAddressViewModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class
AddressAdapter extends RecyclerView.Adapter<AddressAdapter.AddressHolder> {

    private final String source;
    private Context mContext;
    private List<Address> addresses = new ArrayList<>();
    private ImageButton mLastDefaultAddress = null;
    private AddressCommunication mCommunication;
    private int shippingAddress;

    public AddressAdapter(Context mContext,AddressCommunication mCommunication,
                          String source) {
        this.mCommunication = mCommunication;
        this.mContext = mContext;
        this.source = source;

    }

    @NonNull
    @Override
    public AddressHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AddressHolder(LayoutInflater.from(mContext)
        .inflate(R.layout.address_item_row,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull final AddressHolder holder, final int position) {

        holder.setupFonts();
        final Address mAddress = addresses.get(position);
        // fill views
        holder.mUserNameTxt.setText(mContext.getString(R.string.address_user_name,mAddress.getFirst_name(),
                                    mAddress.getLast_name()));
        holder.mAddress1Txt.setText(mAddress.getAddress_1());
        holder.mAddress2Txt.setText(mAddress.getAddress_2());
        holder.mMobileNumberTxt.setText(mAddress.getPhone_number());



        // user can select only one address
        if(!source.equals("address_activity") && mAddress.getId() == shippingAddress)holder.mSelectAddressIcon.setVisibility(View.VISIBLE);
        else holder.mSelectAddressIcon.setVisibility(View.INVISIBLE);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!source.equals("address_activity")) {
                    ImageButton ib = holder.mSelectAddressIcon;
                    if (mLastDefaultAddress != null) {
                        mLastDefaultAddress.setVisibility(View.INVISIBLE);
                    }
                    mLastDefaultAddress = ib;
                    ib.setVisibility(View.VISIBLE);
                }
                mCommunication.selectAddress(new AddressItem(mAddress,position));

            }
        });


        holder.mDeleteAddressBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCommunication.deleteAddress(new AddressItem(mAddress,position));
            }
        });

        holder.mEditAddressBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCommunication.editAddress(new AddressItem(mAddress,position));
            }
        });


    }

    @Override
    public int getItemCount() {

        if (addresses != null && addresses.size() != 0){
            return addresses.size();
        }
        else{
            return 0;
        }
    }

    public void notifyAdapter(List<Address> mAddresses) {
        if(mAddresses != null && mAddresses.size() != 0){
            addresses = mAddresses;
            this.notifyDataSetChanged();
        }
    }

    public void removeAddress(int position) {
        addresses.remove(position);
        notifyDataSetChanged();
    }

    public void editAddress(Address address, int position) {
        addresses.remove(position);
        addresses.add(position,address);
        notifyDataSetChanged();
    }

    public void addAddress(Address newAddress) {
        if(newAddress != null){
            addresses.add(newAddress);
            this.notifyDataSetChanged();
        }
    }

    public void setSelectAddress(int shippingAddress) {
        this.shippingAddress = shippingAddress;
    }


    class AddressHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.address_user_name)
        TextView mUserNameTxt;
        @BindView(R.id.address_address_1)
        TextView mAddress1Txt;
        @BindView(R.id.address_address_2)
        TextView mAddress2Txt;
        @BindView(R.id.address_mobile_number)
        TextView mMobileNumberTxt;
        @BindView(R.id.selected_address_image_btn)
        ImageButton mSelectAddressIcon;
        @BindView(R.id.edit_address_image_btn)
        ImageButton mEditAddressBtn;
        @BindView(R.id.delete_address_image_btn)
        ImageButton mDeleteAddressBtn;




        public AddressHolder(@NonNull final View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }


        public void setupFonts() {
            mUserNameTxt.setTypeface(Typeface.createFromAsset(mContext.getAssets()
                    ,mContext.getString(R.string.roboto_medium)));
            mAddress1Txt.setTypeface(Typeface.createFromAsset(mContext.getAssets()
                    ,mContext.getString(R.string.roboto_light)));
            mAddress2Txt.setTypeface(Typeface.createFromAsset(mContext.getAssets()
                    ,mContext.getString(R.string.roboto_light)));
            mMobileNumberTxt.setTypeface(Typeface.createFromAsset(mContext.getAssets()
                    ,mContext.getString(R.string.roboto_black)));
        }
    }
}
