package com.jenish.veterinaryclinic.ui.pet

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.jenish.veterinaryclinic.R
import com.jenish.veterinaryclinic.widget.ImageLoader

/**
 * Developed By JENISH KHANPARA on 19 June 2020.
 */
@BindingAdapter("petImage")
fun petImage(imageView: ImageView, url: String) {
    // set placeholder image
    imageView.setImageResource(R.drawable.ic_placeholder_image)
    ImageLoader.displayImage(url, imageView)
}

@BindingAdapter("goneUnless")
fun goneUnless(view: View, visible: Boolean) {
    view.visibility = if (visible) View.VISIBLE else View.GONE
}
