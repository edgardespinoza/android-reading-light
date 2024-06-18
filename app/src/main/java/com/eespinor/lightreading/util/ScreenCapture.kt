package com.eespinor.lightreading.util

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.graphics.Bitmap
import android.graphics.Canvas
import android.view.View
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.core.content.FileProvider
import java.io.File
import java.io.FileOutputStream

/*
Convert a composable view into image in Jetpack Compose
https://stackoverflow.com/questions/74450838/convert-a-composable-view-into-image-in-jetpack-compose
 */

fun saveBitmapToFile(context: Context, bitmap: Bitmap): File {
    val file = File(context.externalCacheDir, "image0.png")
    val fileOutputStream = FileOutputStream(file)
    bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream)
    fileOutputStream.flush()
    fileOutputStream.close()
    return file
}

fun shareScreenshot(context: Context, file: File) {
    val uri = FileProvider.getUriForFile(
        context,
        "${context.packageName}.provider",
        file
    )

    val shareIntent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_STREAM, uri)
        type = "image/*"
        addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        setPackage("com.whatsapp")

    }
    val chooser = Intent.createChooser(shareIntent, "Share Screenshot")

    val resInfoList: List<ResolveInfo> =
        context.getPackageManager()
            .queryIntentActivities(chooser, PackageManager.MATCH_DEFAULT_ONLY)

    for (resolveInfo in resInfoList) {
        val packageName = resolveInfo.activityInfo.packageName
        context.grantUriPermission(
            packageName,
            uri,
            Intent.FLAG_GRANT_WRITE_URI_PERMISSION or Intent.FLAG_GRANT_READ_URI_PERMISSION
        )
    }

    context.startActivity(chooser)
}

