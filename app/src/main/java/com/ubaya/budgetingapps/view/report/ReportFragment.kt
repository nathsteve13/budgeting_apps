package com.ubaya.budgetingapps.view.report

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.ubaya.budgetingapps.databinding.FragmentReportBinding
import com.ubaya.budgetingapps.viewmodel.ReportViewModel
import java.text.NumberFormat
import java.util.Locale

class ReportFragment : Fragment() {
    private lateinit var binding: FragmentReportBinding
    private lateinit var viewModel: ReportViewModel
    private lateinit var reportAdapter: ReportAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentReportBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[ReportViewModel::class.java]
        viewModel.refresh()

        binding.recViewReport.layoutManager = LinearLayoutManager(requireContext())

        // Observasi perubahan data
        viewModel.budgetLD.observe(viewLifecycleOwner) { budgets ->
            viewModel.expenseLD.observe(viewLifecycleOwner) { expenses ->
                reportAdapter = ReportAdapter(ArrayList(budgets), expenses)
                binding.recViewReport.adapter = reportAdapter

                val totalUsed = expenses.sumOf { it.amount }
                val totalBudget = budgets.sumOf { it.amount.toIntOrNull() ?: 0 }
                val remaining = totalBudget - totalUsed

                val formatter = NumberFormat.getNumberInstance(Locale("id", "ID"))
                binding.tvTotalBudget.text = "Total Budget: IDR ${formatter.format(totalBudget)}"
                binding.tvTotalExpense.text = "Total Expense: IDR ${formatter.format(totalUsed)}"
                binding.tvRemainingBudget.text = "Remaining Budget: IDR ${formatter.format(remaining)}"
            }
        }
    }
}
