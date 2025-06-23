package com.ubaya.budgetingapps.view.report

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ubaya.budgetingapps.databinding.ItemReportCardBinding
import com.ubaya.budgetingapps.model.budget.Budget

class ReportAdapter(private val budgets: ArrayList<Budget>) :
    RecyclerView.Adapter<ReportAdapter.ReportViewHolder>() {

    class ReportViewHolder(val binding: ItemReportCardBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReportViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemReportCardBinding.inflate(inflater, parent, false)
        return ReportViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ReportViewHolder, position: Int) {
        val item = budgets[position]
        val max = item.amount.toIntOrNull() ?: 0
        val used = item.used.toIntOrNull() ?: 0
        val left = max - used
        val progress = if (max > 0) (used * 100 / max) else 0

        holder.binding.tvBudgetName.text = item.name
        holder.binding.tvBudgetMax.text = "Rp $max"
        holder.binding.tvBudgetUsed.text = "Rp $used"
        holder.binding.tvBudgetLeft.text = "Sisa Budget: Rp $left"
        holder.binding.progressBar.progress = progress
    }

    override fun getItemCount(): Int = budgets.size

    fun updateList(newList: List<Budget>) {
        budgets.clear()
        budgets.addAll(newList)
        notifyDataSetChanged()
    }
}
