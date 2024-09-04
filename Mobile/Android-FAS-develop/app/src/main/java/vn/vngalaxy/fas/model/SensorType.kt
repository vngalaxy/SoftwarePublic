package vn.vngalaxy.fas.model

import vn.vngalaxy.fas.shared.constant.Constant
import vn.vngalaxy.fas.shared.constant.Constant.DEFAULT_MAX_VALUE
import vn.vngalaxy.fas.shared.constant.Constant.HEAT_ID
import vn.vngalaxy.fas.shared.constant.Constant.HEAT_MAX_VALUE
import vn.vngalaxy.fas.shared.constant.Constant.SMOKE_ID
import vn.vngalaxy.fas.shared.constant.Constant.SMOKE_MAX_VALUE
import vn.vngalaxy.fas.shared.constant.Constant.STRING_EMPTY

enum class SensorType(val value: String, val id: String = STRING_EMPTY, val viValue: String) {
    SMOKE("smoke", SMOKE_ID, Constant.SMOKE),
    HEAT("heat", HEAT_ID, Constant.HEAT),
    NONE("none", viValue = Constant.UNKNOWN);

    companion object {
        fun maxValue(type: String): Int {
            return when (type) {
                SMOKE.value -> {
                    SMOKE_MAX_VALUE
                }

                HEAT.value -> {
                    HEAT_MAX_VALUE
                }

                else -> {
                    DEFAULT_MAX_VALUE
                }
            }
        }

//        fun enName(type: String): String {
//            return when (type) {
//                Constant.SMOKE -> {
//                    SMOKE.value
//                }
//
//                Constant.HEAT -> {
//                    HEAT.value
//                }
//
//                else -> {
//                    NONE.value
//                }
//            }
//        }

//        fun listTypeVi(): List<String> {
//            val list = mutableListOf<String>()
//            for (type in entries) {
//                if (type != NONE) {
//                    list.add(viName(type.value))
//                }
//            }
//            return list
//        }

        fun fromValue(value: String?): SensorType {
            return entries.find { it.value == value } ?: NONE
        }

        fun fromId(id: String?): SensorType {
            return entries.find { it.id == id } ?: NONE
        }
    }
}