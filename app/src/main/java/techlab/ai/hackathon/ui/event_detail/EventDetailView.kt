package techlab.ai.hackathon.ui.event_detail

import techlab.ai.hackathon.data.model.DemoModel
import techlab.ai.hackathon.data.model.EventDetail
import techlab.ai.hackathon.data.model.NewFeed

/**
 * @author Tienbm
 */
interface EventDetailView {

    fun onEventDetailResult(eventDetail: EventDetail)
}