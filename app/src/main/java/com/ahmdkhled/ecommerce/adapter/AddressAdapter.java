package com.ahmdkhled.ecommerce.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;

import com.ahmdkhled.ecommerce.R;
import com.ahmdkhled.ecommerce.model.Address;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.AddressHolder> {

    private Context mContext;
    private ArrayList<Address> addresses;
    int mLastCheckedPosition = -1;

    public AddressAdapter(Context mContext, ArrayList<Address> addresses) {
        this.mContext = mContext;
        this.addresses = addresses;
    }

    @NonNull
    @Override
    public AddressHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AddressHolder(LayoutInflater.from(mContext)
        .inflate(R.layout.address_item_row,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull final AddressHolder holder, int position) {
        Address mAddress = addresses.get(position);
        holder.mAddressDetail.setText(mAddress.getAddress1()+", "+mAddress.getAddress2());
        holder.mSelectAddressRB.setChecked(mLastCheckedPosition == position);

    }

    @Override
    public int getItemCount() {
        if (addresses != null && addresses.size() != 0)return addresses.size();
        else return 0;
    }

    public void notifyAdapter(ArrayList<Address> mAddresses) {
        if(mAddresses != null && mAddresses.size() != 0){
            addresses = mAddresses;
            this.notifyDataSetChanged();
        }
    }

    class AddressHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.address_user_name)
        TextView mUserName;
        @BindView(R.id.address_details)
        TextView mAddressDetail;
        @BindView(R.id.select_address_rb)
        RadioButton mSelectAddressRB;
        @BindView(R.id.edit_address)
        TextView mEditAddress;
        @BindView(R.id.delete_address)
        TextView mDeleteAddress;



        public AddressHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mLastCheckedPosition = getAdapterPosition();
                    notifyDataSetChanged();
                }
            });
        }




    }
}
