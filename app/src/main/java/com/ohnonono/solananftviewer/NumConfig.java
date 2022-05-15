package com.ohnonono.solananftviewer;

import java.text.DecimalFormat;

public class NumConfig {

    public static String percent_format(double amount) {
        try {
            DecimalFormat decimalFormat = new DecimalFormat("#.###");
            return decimalFormat.format(amount);
        }catch (NumberFormatException e){
            return String.valueOf(amount);
        }
    }


    public static String priceamount_format(double amount) {
        try {
            if (amount >= 10) {
                DecimalFormat decimalFormat = new DecimalFormat("#,###.##");
                decimalFormat.setGroupingSize(3);
                decimalFormat.setGroupingUsed(true);
                return decimalFormat.format(amount);
            } else if (amount < 10 && amount >= 1) {
                DecimalFormat decimalFormat = new DecimalFormat("#.###");

                return decimalFormat.format(amount);
            } else if (amount < 1 && amount >= .001) {
                DecimalFormat decimalFormat = new DecimalFormat("#.####");
                return decimalFormat.format(amount);
            } else {
                DecimalFormat decimalFormat = new DecimalFormat("#.#####");
                return decimalFormat.format(amount);
            }
        }catch (NumberFormatException e){
            return String.valueOf(amount);
        }

    }


    public static String integer_format(int amount){
        try {
            if (amount < 1000) {
                return String.valueOf(amount);
            } else if (amount >= 1000 && amount < 1000000) {
                return String.valueOf(amount / 1000) + 'k';
            } else {
                return String.valueOf(amount / 1000000) + 'M';
            }
        }catch (NumberFormatException e ){
            return String.valueOf(amount);
        }

    }


}
