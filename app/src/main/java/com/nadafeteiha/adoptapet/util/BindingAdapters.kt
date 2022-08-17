package com.nadafeteiha.adoptapet.util

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.nadafeteiha.adoptapet.R
import com.squareup.picasso.Picasso

@BindingAdapter("petImage")
fun bindPetImage(image: ImageView, imageUrl: String?) {
    imageUrl?.let {
        Picasso.get()
            .load(imageUrl)
//            .placeholder(R.drawable.loading_animation)
//            .error(R.drawable.icon_weather)
            .resize(600, 400)
            .into(image)
    }
}

@BindingAdapter("petSex")
fun bindPetDrawer(text: TextView, sex: String?) {
    sex?.let {
        val drawer =
            if (sex.equals(text.context.resources.getString(R.string.female), true)) {
                R.drawable.female_icon
            } else {
                R.drawable.male_icon
            }
        text.setCompoundDrawablesWithIntrinsicBounds(drawer, 0, 0, 0)
    }
}

@BindingAdapter("petBreed")
fun bindPetBreed(text: TextView, breed: String?) {
    breed?.let {
        val drawer =
            if (breed.equals(text.context.resources.getString(R.string.cat), true)) {
                R.drawable.cat_icon
            } else {
                R.drawable.dog_icon
            }
        text.setCompoundDrawablesWithIntrinsicBounds(drawer, 0, 0, 0)
    }
}

@BindingAdapter("petDescription")
fun bindDescription(text: TextView, description: String?) {
    description?.let {
        text.text = description.subSequence(0, description.indexOf("<"))
    }
}
