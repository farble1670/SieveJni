package layout

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil.ItemCallback
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.clover.sieve.databinding.ItemNumberBinding
import com.google.android.flexbox.FlexboxLayoutManager

class NumberAdapter: ListAdapter<Long, NumberAdapter.NumberHolder>(diff) {
  class NumberHolder(private val binding: ItemNumberBinding) : RecyclerView.ViewHolder(binding.root) {
    init {
      val lp: ViewGroup.LayoutParams = binding.number.layoutParams
      if (lp is FlexboxLayoutManager.LayoutParams) {
        (binding.number.layoutParams as FlexboxLayoutManager.LayoutParams).flexGrow = 1.0f
      }
    }

    fun bind(n: Long) {
      binding.number.text = n.toString()
    }
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NumberHolder {
    return NumberHolder(ItemNumberBinding.inflate(LayoutInflater.from(parent.context), parent, false))
  }

  override fun onBindViewHolder(holder: NumberHolder, position: Int) {
    holder.bind(getItem(position))
  }

  companion object {
    val diff = object:ItemCallback<Long>() {
      override fun areItemsTheSame(oldItem: Long, newItem: Long): Boolean {
        return areContentsTheSame(oldItem, newItem)
      }

      override fun areContentsTheSame(oldItem: Long, newItem: Long): Boolean {
        return oldItem == newItem
      }

    }
  }
}

