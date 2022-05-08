package techlab.ai.hackathon.ui.manager

/**
 * @author BachDV
 */
object AppCoinManager {
    private val listeners: MutableSet<CoinChangeListener> by lazy(::mutableSetOf)

    var currentCoin: Float = 0f
        set(value) {
            field = value
            listeners.forEach { listener -> listener.onChange(value) }
        }


    fun addListener(listener: CoinChangeListener) {
        listeners.add(listener)
    }

    fun removeListener(listener: CoinChangeListener) {
        listeners.remove(listener)
    }

}

interface CoinChangeListener {
    fun onChange(totalCoin: Float)
}