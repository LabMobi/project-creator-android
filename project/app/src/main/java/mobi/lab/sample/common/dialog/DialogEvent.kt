@file:Suppress("MemberVisibilityCanBePrivate")

package mobi.lab.sample.common.dialog

import android.os.Bundle
import android.text.TextUtils
import mobi.lab.sample.common.eventbus.DataEvent

class DialogEvent(val tag: String, val action: Action, bundle: Bundle? = null) : DataEvent<Bundle?>(bundle) {

    @Suppress("unused")
    fun isFor(action: Action): Boolean {
        return this.action == action
    }

    @Suppress("unused")
    fun isFor(tag: String): Boolean {
        return TextUtils.equals(this.tag, tag)
    }

    @Suppress("unused")
    fun isFor(tag: String, action: Action): Boolean {
        return isFor(tag) && isFor(action)
    }

    override fun toString(): String {
        return "DialogEvent{tag=$tag, action='${getActionString()}'}"
    }

    private fun getActionString(): String {
        return when (action) {
            Action.BUTTON_NEGATIVE -> "BUTTON_NEGATIVE"
            Action.BUTTON_POSITIVE -> "BUTTON_POSITIVE"
            Action.CANCELLED -> "CANCELLED"
        }
    }

    enum class Action {
        BUTTON_POSITIVE,
        BUTTON_NEGATIVE,
        CANCELLED
    }
}
