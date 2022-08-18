package com.nadafeteiha.adoptapet.util

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import com.google.android.material.snackbar.Snackbar


fun Context.openMap(latitude: Double, longitude: Double, centerName: String) {
    val uri =
        "http://maps.google.com/maps?daddr=$latitude,$longitude ($centerName)"
    val intent = Intent(
        Intent.ACTION_VIEW,
        Uri.parse(uri)
    )
    intent.setPackage("com.google.android.apps.maps")
    startActivity(intent)
}

fun Context.composeEmail(address: String) {
    val intent = Intent(Intent.ACTION_SENDTO).apply {
        data = Uri.parse("mailto:")
        putExtra(Intent.EXTRA_EMAIL, arrayOf(address))
        putExtra(Intent.EXTRA_SUBJECT, "subject")
    }
    if (intent.resolveActivity(packageManager) != null) {
        startActivity(intent)
    }
}

fun Context.callPhoneCenter(phoneNumber: String) {
    val intent = Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phoneNumber, null))
    startActivity(intent)
}


fun Activity.showSnackBar(message: String) {
    Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG).show()
}