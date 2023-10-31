package com.yukle.qrcodescanner.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yukle.qrcodescanner.data.local.entity.EntityQrCode
import com.yukle.qrcodescanner.databinding.QrCodeItemBinding

class AdapterQrCode() : RecyclerView.Adapter<AdapterQrCode.PhotoViewHolder>() {

    private var qrCodeList: List<EntityQrCode> = arrayListOf()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        val b = QrCodeItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PhotoViewHolder(b)
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        val current = qrCodeList[position]
        holder.b.text.text = current.text
        holder.b.id.text = "${position + 1}."
    }

    override fun getItemCount(): Int {
        return qrCodeList.size
    }

    fun setItems(qrCodeList: List<EntityQrCode>) {
        this.qrCodeList = qrCodeList
        notifyDataSetChanged()
    }

    class PhotoViewHolder(b: QrCodeItemBinding) : RecyclerView.ViewHolder(b.getRoot()) {
        var b: QrCodeItemBinding

        init {
            this.b = b
        }
    }
}