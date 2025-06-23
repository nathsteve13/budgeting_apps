package com.ubaya.budgetingapps.view.report

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ubaya.budgetingapps.databinding.ItemReportCardBinding
import com.ubaya.budgetingapps.model.budget.Budget
import com.ubaya.budgetingapps.model.expense.Expense
import java.text.NumberFormat
import java.util.Locale

class ReportAdapter(private val budgets: ArrayList<Budget>, private val expenses: List<Expense>) :
    RecyclerView.Adapter<ReportAdapter.ReportViewHolder>() {

    class ReportViewHolder(val binding: ItemReportCardBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReportViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemReportCardBinding.inflate(inflater, parent, false)
        return ReportViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ReportViewHolder, position: Int) {
        val budget = budgets[position]

        // Hitung total pengeluaran untuk budget ini
        val used = expenses.filter { it.budgetId == budget.uuid }.sumOf { it.amount }
        val left = (budget.amount.toIntOrNull() ?: 0) - used
        val percent = if ((budget.amount.toIntOrNull() ?: 0) == 0) 0 else (used * 100 / budget.amount.toInt())

        with(holder.binding) {
            tvBudgetName.text = budget.name
            tvBudgetMax.text = "IDR ${formatCurrency(budget.amount.toIntOrNull() ?: 0)}"
            tvBudgetUsed.text = "IDR ${formatCurrency(used)}"
            tvBudgetLeft.text = "Budget left: IDR ${formatCurrency(left)}"
            progressBar.progress = percent
        }
    }

    override fun getItemCount() = budgets.size

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(newBudgets: List<Budget>) {
        budgets.clear()
        budgets.addAll(newBudgets)
        notifyDataSetChanged()
    }

    private fun formatCurrency(value: Int): String {
        return NumberFormat.getNumberInstance(Locale("id", "ID")).format(value)
    }
}
