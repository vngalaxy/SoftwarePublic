package vn.vngalaxy.fas.model

enum class SensorStatus(val value: String?) {
    ON("on"),
    OFF("off"),
    WARNING("warning"),
    FIRE("fire"),
    NULL(null),
}