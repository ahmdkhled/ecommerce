package com.ahmdkhled.ecommerce.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.content.Intent;

import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.ahmdkhled.ecommerce.R;
import com.ahmdkhled.ecommerce.adapter.AddressAdapter;
import com.ahmdkhled.ecommerce.model.Address;
import com.ahmdkhled.ecommerce.model.AddressItem;
import com.ahmdkhled.ecommerce.model.Response;
import com.ahmdkhled.ecommerce.utils.AddressCommunication;
import com.ahmdkhled.ecommerce.viewmodel.AddressViewModel;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class AddressActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.address_fragment_container)
    FrameLayout container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);


        ButterKnife.bind(this);

        AddressFragment addressFragment = new AddressFragment();
        Bundle bundle = new Bundle();
        bundle.putString("source","address_activity");
        addressFragment.setArguments(bundle);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.address_fragment_container,addressFragment);
        transaction.commit();


        setSupportActionBar(mToolbar);
        mToolbarTitle.setText(getString(R.string.address_activity_title));
        mToolbarTitle.setTypeface(Typeface.createFromAsset(getAssets(),getString(R.string.roboto_black)));

    }
}
