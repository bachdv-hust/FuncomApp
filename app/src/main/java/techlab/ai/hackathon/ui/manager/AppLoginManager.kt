package techlab.ai.hackathon.ui.manager

/**
 * @author BachDV
 */
object AppLoginManager {

    private val listeners: MutableSet<LoginChangedListener> by lazy(::mutableSetOf)

    var isLogged: Boolean = false
        set(value) {
            field = value
            listeners.forEach { listener -> listener.onLoginChanged(value) }
        }


    fun addListener(listener: LoginChangedListener) {
        listeners.add(listener)
    }

    fun removeListener(listener: LoginChangedListener) {
        listeners.remove(listener)
    }

}

interface LoginChangedListener {
    fun onLoginChanged(isLogged: Boolean)
}
