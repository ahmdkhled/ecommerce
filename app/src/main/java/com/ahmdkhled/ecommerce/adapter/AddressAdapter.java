package com.ahmdkhled.ecommerce.adapter;

import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.ahmdkhled.ecommerce.R;
import com.ahmdkhled.ecommerce.model.Address;
import com.ahmdkhled.ecommerce.model.AddressItem;
import com.ahmdkhled.ecommerce.model.Response;
import com.ahmdkhled.ecommerce.network.RetrofetClient;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;

public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.AddressHolder> {

    private Context mContext;
    private List<Address> addresses;
    private RadioButton mLastRbChecked = null;
    private MutableLiveData<AddressItem> wannaDelete;

    public AddressAdapter(Context mContext, List<Address> addresses) {
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
    public void onBindViewHolder(@NonNull final AddressHolder holder, final int position) {
        final Address mAddress = addresses.get(position);
        holder.mAddressDetail.setText(mContext.getString(R.string.address_details,
                mAddress.getAddress1(),mAddress.getAddress2()));

        // user can select only one address
        holder.mSelectAddressRB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                RadioButton rb = holder.mSelectAddressRB;
                if(mLastRbChecked != null){
                    mLastRbChecked.setChecked(false);
                }
                mLastRbChecked = rb;
            }
        });

        holder.mDeleteAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // delete address from adapter
                Log.d("delete_add","postion wanna delete is "+position);
                wannaDelete.setValue(new AddressItem(mAddress,position));
            }
        });




    }

    public MutableLiveData<AddressItem> getWannaDelete() {
        if(wannaDelete == null)wannaDelete = new MutableLiveData<>();
        return wannaDelete;
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
            addresses = mAddresses;
            this.notifyDataSetChanged();
        }
    }

    public void notifyAddressHasRemoved(int position) {
        Log.d("delete_add","position deleted "+position);
        addresses.remove(position);
        notifyDataSetChanged();
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




        public AddressHolder(@NonNull final View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }




    }
}
