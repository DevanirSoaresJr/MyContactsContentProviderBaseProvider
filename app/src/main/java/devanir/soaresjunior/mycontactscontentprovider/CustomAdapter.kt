package devanir.soaresjunior.mycontactscontentprovider

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

class CustomAdapter() : BaseAdapter() {

     private val contactModelArrayList =  ArrayList<ContactModel>()



    override fun getItemViewType(position: Int): Int {

        return position
    }

    override fun getCount(): Int {
        return contactModelArrayList.size
    }

    override fun getItem(position: Int): Any {
        return contactModelArrayList[position]
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    fun updateData(data: List<ContactModel>) {
        contactModelArrayList.clear()
        contactModelArrayList.addAll(data)
        notifyDataSetChanged()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView
        val holder: ViewHolder

        if (convertView == null) {
            holder = ViewHolder()
            val inflater = parent.context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            convertView = inflater.inflate(R.layout.listview_item, null, true)

            holder.tvName = convertView!!.findViewById(R.id.tvName) as TextView
            holder.tvNumber = convertView.findViewById(R.id.tvNumber) as TextView

            convertView.tag = holder
        } else {
            // the getTag returns the viewHolder object set as a tag to the view
            holder = convertView.tag as ViewHolder
        }

        holder.tvName.setText(contactModelArrayList[position].getNames())
        holder.tvNumber.setText(contactModelArrayList[position].getNumbers())

        return convertView
    }

    private inner class ViewHolder {

        lateinit var tvName: TextView
        lateinit var tvNumber: TextView

    }
}