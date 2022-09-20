package pl.softwarelytest.View

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import pl.softwarelytest.R
import pl.softwarelytest.dto.CatModel

class CatsAdapter() : RecyclerView.Adapter<CatsAdapter.CatsHolder>() {
    private val cats = mutableListOf<CatModel>()

    fun setList(catsData: List<CatModel>) {
        if (cats.isNotEmpty())
            cats.clear()
        cats.addAll(catsData)
        notifyDataSetChanged()
    }

    class CatsHolder(
        private val view: View,
    ) : RecyclerView.ViewHolder(view) {
        fun bind(catData: CatModel) {
            if(catData.name != null)
                view.findViewById<TextView>(R.id.name).text = catData.name
            if(catData.age != null)
                view.findViewById<TextView>(R.id.age).text = catData.age
            if(catData.description != null)
                view.findViewById<TextView>(R.id.description).text = catData.description
            if(catData.imageUrl != null)
            Picasso.get().load(catData.imageUrl).into(view.findViewById<ImageView>(R.id.itemImage1))
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatsHolder {
        val inflatedView = LayoutInflater.from(parent.context).inflate(
            R.layout.cat_element,
            parent,
            false
        )


        return CatsHolder(inflatedView)
    }

    override fun onBindViewHolder(holder: CatsHolder, position: Int) {
        holder.bind(cats[position])
    }

    override fun getItemCount(): Int {
        return cats.size
    }
}