package vn.vngalaxy.fas.model

data class ChirpStackDevice(
    val applicationID: String,
    val description: String,
    val devEUI: String,
    val deviceProfileID: String,
    val isDisabled: Boolean = false,
    val name: String,
    val referenceAltitude: Int = 0,
    val skipFCntCheck: Boolean = false,
    val tags: Map<String, String> = emptyMap(),
    val variables: Map<String, String> = emptyMap()
)

data class ChirpStackDeviceBody(
    val device: ChirpStackDevice
)

data class ChirpStackDeviceKeys(
    val devEUI: String,
    val nwkKey: String
)

data class ChirpStackDeviceKeysBody(
    val deviceKeys: ChirpStackDeviceKeys
)

