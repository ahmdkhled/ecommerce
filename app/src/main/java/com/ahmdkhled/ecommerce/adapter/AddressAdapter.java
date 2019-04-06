package com.ahmdkhled.ecommerce.adapter;

import android.arch.lifecycle.MutableLiveData;
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

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class
AddressAdapter extends RecyclerView.Adapter<AddressAdapter.AddressHolder> {

    private Context mContext;
    private List<Address> addresses;
    private String source;
    private RadioButton mLastRbChecked = null;
    private MutableLiveData<AddressItem> mDelete,mEdit,mDefault;
    private MutableLiveData<Address> mSelectAddress;

    public AddressAdapter(Context mContext, List<Address> addresses, String source) {
        this.mContext = mContext;
        this.addresses = addresses;
        this.source = source;


    }

    @NonNull
    @Override
    public AddressHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AddressHolder(LayoutInflater.from(mContext)
        .inflate(R.layout.address_card_view,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull final AddressHolder holder, final int position) {

        holder.setupFonts();

        if(source.equals("checkout")){
            holder.mDeleteAddressBtn.setVisibility(View.GONE);
            holder.mEditAddressBtn.setVisibility(View.GONE);
        }

        final Address mAddress = addresses.get(position);

        // fill views
        holder.mUserNameTxt.setText(mContext.getString(R.string.address_user_name,mAddress.getFirst_name(),
                                    mAddress.getLast_name()));
        holder.mAddress1Txt.setText(mAddress.getAddress_1());
        holder.mAddress2Txt.setText(mAddress.getAddress_2());
        holder.mMobileNumberTxt.setText(mAddress.getPhone_number());


        /**
         * if there is a default address so that it should be marked
         */
//        if(mAddress.getisDefault() == 1){
//            holder.mSelectAddressRB.setChecked(true);
//            mLastRbChecked = holder.mSelectAddressRB;
//        }

        // user can select only one address

//        holder.mSelectAddressIcon.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Log.d("ADDRESS_ACTIVITY_TAG","RB cahnge");
//                RadioButton rb = holder.mSelectAddressRB;
//                if(mLastRbChecked != null){
//                    mLastRbChecked.setChecked(false);
//                }
//                mLastRbChecked = rb;
//                mSelectAddress.setValue(mAddress);
//            }
//        });


        holder.mDeleteAddressBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // delete address from adapter
                mDelete.setValue(new AddressItem(mAddress,position));
            }
        });

        holder.mEditAddressBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("edit_add","position "+position);
                mEdit.setValue(new AddressItem(mAddress,position));
            }
        });





    }

    public MutableLiveData<AddressItem> getmDelete() {
        if(mDelete == null) mDelete = new MutableLiveData<>();
        return mDelete;
    }

    public MutableLiveData<AddressItem> getmEdit() {
        if(mEdit == null) mEdit = new MutableLiveData<>();
        return mEdit;
    }

    public MutableLiveData<Address> getmSelectAddress() {
        if(mSelectAddress == null) mSelectAddress = new MutableLiveData<>();
        return mSelectAddress;
    }





    @Override
    public int getItemCount() {

        if (addresses != null && addresses.size() != 0){
            return addresses.size();
        }
        else{
            Log.d("viewmodeldemo","null list in adapter");
            return 0;
        }
    }

    public void notifyAdapter(List<Address> mAddresses) {
        if(mAddresses != null && mAddresses.size() != 0){
            Log.d("address_adapter","address "+mAddresses.size());
            addresses = mAddresses;
            this.notifyDataSetChanged();
        }
    }

    public void removeAddress(int position) {
        Log.d("delete_add","position deleted "+position);
        addresses.remove(position);
        notifyDataSetChanged();
    }

    public void editAddress(Address address, int position) {
        Log.d("edit_add","position  "+position);
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
