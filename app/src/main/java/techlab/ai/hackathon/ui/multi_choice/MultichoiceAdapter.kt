package techlab.ai.hackathon.ui.multi_choice

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import techlab.ai.hackathon.R
import techlab.ai.hackathon.data.model.QuestionData

class MultichoiceAdapter(val listener: OnQuestionClick) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val listQuestion: MutableList<QuestionData> = mutableListOf()

    private val question: Int = 0
    private val answer: Int = 1
    private var setResult = false

    class QuestionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txt_question = itemView.findViewById<TextView>(R.id.txt_question)
    }

    class AnwserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cbt_anwser = itemView.findViewById<ImageView>(R.id.cbt_anwser)
        val txt_anwser = itemView.findViewById<TextView>(R.id.txt_anwser)
        val row_answer = itemView.findViewById<LinearLayout>(R.id.row_answer)
    }

    override fun getItemViewType(position: Int): Int {
        return if (listQuestion[position].type == question) {
            question
        } else {
            answer
        }
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        if (payloads.isNotEmpty()) {
            val context = holder.itemView.context
            if (holder is AnwserViewHolder) {
                if (payloads[0] == "reset") {
                    holder.cbt_anwser.setImageDrawable(context.resources.getDrawable(R.drawable.questionselectionunselected))
                    holder.txt_anwser.setTextColor(context.resources.getColor(R.color.Base_B500))
                }else{
                    holder.txt_anwser.setTextColor(context.resources.getColor(R.color.Base_B500))
                    holder.cbt_anwser.setImageDrawable(context.resources.getDrawable(R.drawable.questionsselectionselected))
                }
            }

        } else {
            super.onBindViewHolder(holder, position, payloads)
        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val context = holder.itemView.context
        if (holder is QuestionViewHolder) {
            holder.txt_question.text = listQuestion[position].question
        } else if (holder is AnwserViewHolder) {
            val check = false
            if (listQuestion[position].isCheck) {
                if (listQuestion[position].answer.equals(listQuestion[position].correct)) {
                    holder.txt_anwser.setTextColor(context.resources.getColor(R.color.Base_B500))
                    holder.cbt_anwser.setImageDrawable(context.resources.getDrawable(R.drawable.questionsselectionselected))
                } else {
                    holder.cbt_anwser.setImageDrawable(context.resources.getDrawable(R.drawable.questionselectionunerror))
                    holder.txt_anwser.setTextColor(context.resources.getColor(R.color.red))
                }
            } else {
                holder.cbt_anwser.setImageDrawable(context.resources.getDrawable(R.drawable.questionselectionunselected))
                holder.txt_anwser.setTextColor(context.resources.getColor(R.color.Base_B500))
            }

            holder.txt_anwser.text = listQuestion[position].answer
            holder.row_answer.setOnClickListener {
                listQuestion[position].isCheck = !check
                listener.questionClick(listQuestion[position], position)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == question) {
            QuestionViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_question_multi_choice, parent, false)
            )
        } else {
            AnwserViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_anwser_multi_choice, parent, false)
            )
        }
    }

    override fun getItemCount(): Int {
        return listQuestion.size
    }

    fun checkResult() {
        setResult = true
        notifyDataSetChanged()
    }

    fun setResult(list: List<QuestionData>) {
        setResult = false
        listQuestion.clear()
        listQuestion.addAll(list)
        notifyDataSetChanged()
    }

    fun setData(list: List<QuestionData>) {
        setResult = false
        listQuestion.addAll(list)
        notifyDataSetChanged()
    }
}

interface OnQuestionClick {
    fun questionClick(questionData: QuestionData, position: Int)
}