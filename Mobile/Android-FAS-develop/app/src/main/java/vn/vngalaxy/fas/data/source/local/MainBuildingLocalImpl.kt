package vn.vngalaxy.fas.data.source.local

import android.content.Context
import vn.vngalaxy.fas.data.source.MainBuildingDatasource
import vn.vngalaxy.fas.shared.constant.Constant.STRING_EMPTY

class MainBuildingLocalImpl(
    context: Context
) : MainBuildingDatasource.Local {
    override var projectId: String by Preferences(context).StringProp("projectId", STRING_EMPTY)

    override var name: String by Preferences(context).StringProp("buildingName", STRING_EMPTY)

    override var endPointUrl: String by Preferences(context).StringProp("endPointUrl", STRING_EMPTY)

    override var buildingDatabaseId: String by Preferences(context).StringProp("buildingDatabaseId", STRING_EMPTY)

    override var userCollectionId: String by Preferences(context).StringProp("userCollectionId", STRING_EMPTY)

    override var floorCollectionId: String by Preferences(context).StringProp("floorCollectionId", STRING_EMPTY)

    override var apartmentCollectionId: String by Preferences(context).StringProp("apartmentCollectionId", STRING_EMPTY)

    override var roomCollectionId: String by Preferences(context).StringProp("roomCollectionId", STRING_EMPTY)

    override var sensorCollectionId: String by Preferences(context).StringProp("sensorCollectionId", STRING_EMPTY)

    override var userNotificationCollectionId: String by Preferences(context).StringProp("userNotificationCollectionId", STRING_EMPTY)

    override var sensorNotificationCollectionId: String by Preferences(context).StringProp("sensorNotificationCollectionId", STRING_EMPTY)

    override var notificationCollectionId: String by Preferences(context).StringProp("notificationCollectionId", STRING_EMPTY)

    override var caseFireLocationCollectionId: String by Preferences(context).StringProp("caseFireLocationCollectionId", STRING_EMPTY)

    override var cornerLocationBuilding: List<String> by Preferences(context).StringListProp("cornerLocationBuilding")

}