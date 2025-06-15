package com.ubaya.budgetingapps.view.budgeting

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ubaya.budgetingapps.databinding.BudgetItemLayoutBinding
import com.ubaya.budgetingapps.model.budget.Budget

class BudgetAdapter(
    private val budgetList: ArrayList<Budget>,
    private val onClick: (Budget) -> Unit
) : RecyclerView.Adapter<BudgetAdapter.BudgetViewHolder>() {

    class BudgetViewHolder(val binding: BudgetItemLayoutBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BudgetViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = BudgetItemLayoutBinding.inflate(inflater, parent, false)
        return BudgetViewHolder(binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: BudgetViewHolder, position: Int) {
        val budget = budgetList[position]
        holder.binding.txtBudgetName.text = budget.name
        holder.binding.txtAmount.text = "Rp ${budget.amount}"
        holder.itemView.setOnClickListener {
            onClick(budget)
        }
    }

    override fun getItemCount(): Int = budgetList.size

    @SuppressLint("NotifyDataSetChanged")
    fun updateBudgetList(newList: List<Budget>) {
        budgetList.clear()
        budgetList.addAll(newList)
        notifyDataSetChanged()
    }
}
