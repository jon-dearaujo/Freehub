package jonathansilva.com.freehub.detail

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.view.View
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target
import java.lang.Exception

class PicassoImageLoadTarget(
        private val backgroundView: View, private val context: Context?): Target {
    override fun onBitmapFailed(e: Exception?, errorDrawable: Drawable?) {

    }

    override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {
        context?.let {
            backgroundView.background = BitmapDrawable(context.resources, bitmap)
        }
    }

    override fun onPrepareLoad(placeHolderDrawable: Drawable?) {

    }
}