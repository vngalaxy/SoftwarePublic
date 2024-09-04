package vn.vngalaxy.fas.data.source.remote.api

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path
import vn.vngalaxy.fas.model.ChirpStackDeviceBody
import vn.vngalaxy.fas.model.ChirpStackDeviceKeysBody

interface ChirpStackApi {
    @POST("devices")
    suspend fun createDevice(
        @Body body: ChirpStackDeviceBody,
    ): Response<ResponseBody>

    @POST("devices/{devEUI}/keys")
    suspend fun activeDevice(
        @Path("devEUI") devEUI: String,
        @Body body: ChirpStackDeviceKeysBody,
    ): Response<ResponseBody>
}