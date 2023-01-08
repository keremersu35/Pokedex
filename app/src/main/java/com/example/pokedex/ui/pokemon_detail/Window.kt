package com.example.pokedex.ui.pokemon_detail

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.PixelFormat
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.*
import android.view.ViewGroup.LayoutParams
import android.widget.ImageView
import android.widget.TextView
import com.example.pokedex.R
import com.example.pokedex.domain.model.PokemonDetail
import com.example.pokedex.utils.Constants
import java.net.URL
import java.util.concurrent.Executors


class Window(  // declaring required variables
    private val context: Context,
    private val pokemon : PokemonDetail
) {
    private val mView: View
    private var mParams: WindowManager.LayoutParams? = null
    private val mWindowManager: WindowManager
    private val layoutInflater: LayoutInflater

    init {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // set the layout parameters of the window
            mParams = WindowManager.LayoutParams( // Shrink the window to wrap the content rather
                // than filling the screen
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,  // Display it on top of other application windows
                WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,  // Don't let it grab the input focus
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,  // Make the underlying application window visible
                // through any transparent parts
                PixelFormat.TRANSLUCENT
            )
            mParams!!.width = LayoutParams.WRAP_CONTENT
            mParams!!.height = LayoutParams.WRAP_CONTENT
        }
        // getting a LayoutInflater
        layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        // inflating the view with the custom layout we created
        mView = layoutInflater.inflate(R.layout.overlay_layout, null)
        // set onClickListener on the remove button, which removes
        // the view from the window
        val button = mView.findViewById<View>(R.id.overlay_close)
        button.setOnClickListener { close() }
        val weight = mView.findViewById<TextView>(R.id.overlay_weight)
        weight.text = pokemon.weight.toString()
        val imageView = mView.findViewById<ImageView>(R.id.overlay_image)
        val executor = Executors.newSingleThreadExecutor()
        val handler = Handler(Looper.getMainLooper())
        var image: Bitmap?
        // Only for Background process (can take time depending on the Internet speed)
        executor.execute {
            try {
                val `in` = URL(pokemon.getImage()).openStream()
                image = BitmapFactory.decodeStream(`in`)
                handler.post {
                    imageView.setImageBitmap(image)
                }
            }
            catch (e: Exception) { e.printStackTrace() }
        }
        //Glide.with(context).load(pokemon.getImage()).into(mView.findViewById<ImageView>(R.id.overlay_image));
        val name = mView.findViewById<TextView>(R.id.overlay_name)
        name.text = pokemon.name
        val height = mView.findViewById<TextView>(R.id.overlay_hei)
        height.text = pokemon.height.toString()
        // Define the position of the
        // window within the screen
        mParams!!.gravity = Gravity.CENTER
        mWindowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
    }

    fun open() {
        try {
            // check if the view is already
            // inflated or present in the window
            if (mView.windowToken == null) {
                if (mView.parent == null) {
                        mWindowManager.addView(mView, mParams)

                }
            }
        }
        catch (e: Exception) {
            Log.d("Error1", e.toString())
        }
    }

    private fun close() {
        try {
            // remove the view from the window
            (context.getSystemService(Context.WINDOW_SERVICE) as WindowManager).removeView(mView)
            // invalidate the view
            mView.invalidate()
            Constants.temp = 0
            // remove all views
            (mView.parent as ViewGroup).removeAllViews()

            // the above steps are necessary when you are adding and removing
            // the view simultaneously, it might give some exceptions
        } catch (e: Exception) {
            Log.d("Error2", e.toString())
        }
    }
}

