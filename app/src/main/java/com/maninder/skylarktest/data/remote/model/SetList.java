package com.maninder.skylarktest.data.remote.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Maninder on 12/10/16.
 */

/**
 * List of Set Model to get from Server
 */
public class SetList {

    @SerializedName("objects")
    public List<Set> restSets;

}
