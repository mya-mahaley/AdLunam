package com.example.adlunam.api

import com.google.gson.annotations.SerializedName

data class Weather (
    @SerializedName("moonrise") var moonrise : Int? = null,
    @SerializedName("moonset") var moonset : Int? = null,
    @SerializedName("moon_phase") var moonPhase : Double? = null,
)