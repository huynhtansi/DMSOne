package com.htsi.dmsone.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Created by Huỳnh Phúc.
 * Since: 9/24/2016 on 11:19 PM
 * Project: DMSOne
 */

public class Order implements Parcelable {

    @SerializedName("id")
    public long id;

    @SerializedName("amount")
    public long total;

    @SerializedName("shortCode")
    public String customerCode;

    @SerializedName("customerId")
    public long customerId;

    @SerializedName("customerAddress")
    public String customerAddress;

    @SerializedName("customerName")
    public String customerName;

    @SerializedName("deliveryCode")
    public String shipper;

    @SerializedName("deliveryDate")
    public Date deliveryDate;

    @SerializedName("orderNumber")
    public String orderNumber;

    @SerializedName("staffCode")
    public long staffCode;

    @SerializedName("staffName")
    public String staffName;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(total);
        dest.writeLong(customerId);
        dest.writeString(customerCode);
        dest.writeString(customerAddress);
        dest.writeString(customerName);
        dest.writeString(shipper);
        dest.writeString(orderNumber);
        dest.writeString(staffName);
        dest.writeLong(staffCode);
        dest.writeLong(deliveryDate.getTime());
    }

    public static final Creator<Order> CREATOR = new Creator<Order>() {
        @Override
        public Order createFromParcel(Parcel in) {
            return new Order(in);
        }

        @Override
        public Order[] newArray(int size) {
            return new Order[size];
        }
    };

    public Order(Parcel in) {
        total = in.readLong();
        customerId = in.readLong();
        customerCode = in.readString();
        customerAddress = in.readString();
        customerName = in.readString();
        shipper = in.readString();
        orderNumber = in.readString();
        staffName = in.readString();
        staffCode = in.readLong();
        deliveryDate = new Date(in.readLong());
    }

}
