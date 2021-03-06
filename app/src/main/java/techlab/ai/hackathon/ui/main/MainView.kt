package techlab.ai.hackathon.ui.main

import techlab.ai.hackathon.data.model.DemoModel
import techlab.ai.hackathon.data.model.FeedMenuModel
import techlab.ai.hackathon.data.model.NewFeed

/**
 * @author BachDV
 */
interface MainView {

    fun onNewFeedsResult(newFeeds : List<NewFeed>)
    fun onFail()

    fun onMenuResult(menus: List<FeedMenuModel> )
    fun onMenuFail()
}