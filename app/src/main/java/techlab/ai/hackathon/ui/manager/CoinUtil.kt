package techlab.ai.hackathon.ui.manager

import techlab.ai.hackathon.cached.SharePref

/**
 * @author BachDV
 */
class CoinUtil {
    companion object {
        fun updateCoin(coin: Float) {
            SharePref.totalCoin = coin
            AppCoinManager.currentCoin = coin
        }

        fun getCurrentCoin(): Float {
            return SharePref.totalCoin
        }
    }
}