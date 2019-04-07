package com.ahmdkhled.ecommerce.utils;

import com.ahmdkhled.ecommerce.model.Address;
import com.ahmdkhled.ecommerce.model.AddressItem;

public interface AddressCommunication {
    public void selectAddress(Address mAddress);

    void editAddress(AddressItem addressItem);

    void deleteAddress(AddressItem addressItem);
}
