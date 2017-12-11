package com.grp15.cmpe272.unitedwayapp.bornlearning.profile

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.grp15.cmpe272.unitedwayapp.bornlearning.Constants
import com.grp15.cmpe272.unitedwayapp.bornlearning.R
import com.grp15.cmpe272.unitedwayapp.bornlearning.model.Center
import com.grp15.cmpe272.unitedwayapp.bornlearning.model.Child
import com.grp15.cmpe272.unitedwayapp.bornlearning.model.Indicator
import com.grp15.cmpe272.unitedwayapp.bornlearning.task.IndicatorGetListTask
import com.grp15.cmpe272.unitedwayapp.bornlearning.util.GlobalProperties


/**
 * This class is responsible to handle Indicators.
 */
class QuestionImplementationFragment : Fragment() {

    private lateinit var selectedCenter: Center

    private lateinit var selectedChild: Child

    private var selectedSubCategory: String? = null

    private lateinit var listView: ListView

    private var indicators: List<Indicator>? = null

    private lateinit var questionCustomAdapter: QuestionCustomAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_question_implementation,
                container, false)

        // take the selectedCenter from intent
        selectedCenter = activity?.intent?.getSerializableExtra(Center::class.simpleName) as Center
        selectedChild = activity?.intent?.getSerializableExtra(Child::class.simpleName) as Child
        selectedSubCategory = activity?.intent?.getStringExtra(Constants.SUBCATEGORY_TYPE)

        val submitButton : Button = view.findViewById(R.id.button_question_implementation_submit)
        submitButton.setOnClickListener{ submitForm(it) }

        val centerNameTextView: TextView = view.findViewById(R.id.textview_questions_center_name)
        centerNameTextView.text = selectedCenter.centerName

        val childNameTextView: TextView = view.findViewById(R.id.textview_questions_child_name)
        val childFullName = selectedChild.childFName + " " + selectedChild.childLName
        childNameTextView.text = childFullName

        listView = view.findViewById(R.id.listview_questions)

        return view
    }

    override fun onResume() {
        super.onResume()

        getIndicators()
        // setup listview
        questionCustomAdapter = QuestionCustomAdapter(activity!!.applicationContext, ArrayList(indicators))
        listView.adapter = questionCustomAdapter

    }

    private fun getIndicators() {
        val getIndicatorsTask = IndicatorGetListTask()
        if (selectedSubCategory == null) {
            Toast.makeText(activity, "Subcategory cannot be empty", Toast.LENGTH_SHORT).show()
        } else {
            getIndicatorsTask.execute(selectedSubCategory, selectedChild.age.toString())
            indicators = getIndicatorsTask.get()?.toMutableList()
        }
        if (indicators == null) {
            Toast.makeText(activity, "Unable to find Indicators.", Toast.LENGTH_SHORT).show()
            indicators = emptyList()
        }
    }

    fun submitForm(view: View) {
        Toast.makeText(this.activity, "Submit", Toast.LENGTH_SHORT).show()
    }
}