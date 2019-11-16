package kuran.ne.kerko.com.myapplication

/*
 * Copyright (c) 2018 Razeware LLC
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * Notwithstanding the foregoing, you may not use, copy, modify, merge, publish,
 * distribute, sublicense, create a derivative work, and/or sell copies of the
 * Software in any work that is designed, intended, or marketed for pedagogical or
 * instructional purposes related to programming, coding, application development,
 * or information technology.  Permission for such use, copying, modification,
 * merger, publication, distribution, sublicensing, creation of derivative works,
 * or sale is expressly withheld.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 *
 */

import Models.KategoriteModel
import android.content.Context
import android.support.v4.content.ContextCompat
import android.support.v4.content.res.ResourcesCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView


class KategoriteListAdapter(private val context: Context,
                    private val dataSource: ArrayList<KategoriteModel>) : BaseAdapter() {

    private val inflater: LayoutInflater
            = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getCount(): Int {
        return dataSource.size
    }

    override fun getItem(position: Int): Any {
        return dataSource[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view: View
        val holder: ViewHolder

        // 1
        if (convertView == null) {

            // 2
            view = inflater.inflate(R.layout.kategorite_list_item, parent, false)

            // 3
            holder = ViewHolder()
            holder.nrRendor = view.findViewById(R.id.txtNrRendor) as TextView
            holder.AjetiDheSurjaThot = view.findViewById(R.id.AjetiDheSurjaThot) as TextView
            holder.txtAjetiPershkruan = view.findViewById(R.id.txtAjetiPershkruan) as TextView

            // 4
            view.tag = holder
        } else {
            // 5
            view = convertView
            holder = convertView.tag as ViewHolder
        }

        // 6
        val AjetiDheSurjaThot = holder.AjetiDheSurjaThot
        val txtAjetiPershkruan = holder.txtAjetiPershkruan
        val nrRendor = holder.nrRendor

        val kategoria = getItem(position) as KategoriteModel
        nrRendor.text = (position + 1).toString()
        AjetiDheSurjaThot.text = kategoria.surja + "-" + kategoria.ajeti_id + " "+ context.getString(R.string.ajetithot)
        txtAjetiPershkruan.text = kategoria.ajeti

        return view
    }

    private class ViewHolder {
        lateinit var AjetiDheSurjaThot: TextView
        lateinit var txtAjetiPershkruan: TextView
        lateinit var nrRendor: TextView
    }
}
