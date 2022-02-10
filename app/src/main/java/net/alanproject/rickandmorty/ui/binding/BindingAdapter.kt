package net.alanproject.rickandmorty.ui.binding

import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.StringDef
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import jp.wasabeef.glide.transformations.BlurTransformation
import net.alanproject.domain.model.DomainCharacterModel
import net.alanproject.domain.model.DomainEpiFromAsset
import net.alanproject.domain.model.DomainLocationModel
import net.alanproject.rickandmorty.R
import net.alanproject.rickandmorty.ui.adapter.CharacterAdapter
import net.alanproject.rickandmorty.ui.adapter.EpisodeAdapter
import net.alanproject.rickandmorty.ui.adapter.LocationsAdapter

@BindingAdapter("updateCharacterList")
fun bindUpdateCharacterList(recyclerView: RecyclerView, itemList: List<DomainCharacterModel>?) {
    itemList?.let {
        (recyclerView.adapter as CharacterAdapter).update(itemList.toMutableList())

    }
}

@BindingAdapter("submitCharacterList")
fun bindSubmitCharacterList(recyclerView: RecyclerView, itemList: List<DomainCharacterModel>?) {
//    itemList?.let {
    (recyclerView.adapter as CharacterAdapter).submit(itemList?.toMutableList())

//    }
}

@BindingAdapter("updateEpisodeList")
fun bindUpdateEpisodeList(recyclerView: RecyclerView, itemList: List<DomainEpiFromAsset>?) {
//    Timber.d("itemList:$itemList")
    itemList?.let {
        (recyclerView.adapter as EpisodeAdapter).update(itemList.toMutableList())
    }
}


@BindingAdapter("updateLocations")
fun bindUpdateLocations(recyclerView: RecyclerView, itemList: List<DomainLocationModel>?) {
//    Timber.d("bindSubmitLocations: $itemList")
    itemList?.let {
        (recyclerView.adapter as LocationsAdapter).update(itemList.toMutableList())

    }
}

@BindingAdapter("submitLocations")
fun bindSubmitLocations(recyclerView: RecyclerView, itemList: List<DomainLocationModel>?) {
//    Timber.d("bindSubmitLocations: $itemList")
//    itemList?.let {
    (recyclerView.adapter as LocationsAdapter).submit(itemList?.toMutableList())

//    }
}

@BindingAdapter("characterImage")
fun bindCharacterImage(view: ImageView, image: String?) {
//    Timber.d("Glide Image:$image")
    Glide.with(view)
        .load(image)
        .error(R.drawable.rick_morty)
        .into(view)
}

@BindingAdapter("blurredCharacterImage")
fun bindBlurredCharacterImage(view: ImageView, image: String?) {
//    Timber.d("Glide Image:$image")
    Glide.with(view)
        .load(image)
        .apply(RequestOptions.bitmapTransform(BlurTransformation(25, 1)))
        .error(R.drawable.rick_morty)
        .into(view)
}

@BindingAdapter("blurredCharacterDrawableImage")
fun bindBlurredCharacterDrawableImage(view: ImageView, image: Drawable) {
//    Timber.d("Glide Image:$image")
    Glide.with(view)
        .load(image)
        .apply(RequestOptions.bitmapTransform(BlurTransformation(25, 1)))
        .error(R.drawable.rick_morty)
        .into(view)
}

@BindingAdapter("characterDrawableImage")
fun bindCharacterDrawableImage(view: ImageView, image: Drawable) {
//    Timber.d("Glide Image:$image")
    Glide.with(view)
        .load(image)
        .error(R.drawable.rick_morty)
        .into(view)
}

@BindingAdapter("tvColor")
fun bindTextViewColor(view: TextView, @CharStatus status: String?) =
    //    Timber.d("binding: status: $status")
    when (status) {
        CharStatus.ALIVE -> {
            view.setBackgroundResource(R.drawable.rect_green_corner)
        }
        CharStatus.UNKNOWN -> {
            view.setBackgroundResource(R.drawable.rect_grey_corner)
        }
        else -> {
            view.setBackgroundResource(R.drawable.rect_red_corner)
        }
    }

@Retention(AnnotationRetention.SOURCE)
@StringDef(
    CharStatus.ALIVE,
    CharStatus.UNKNOWN,
)
annotation class CharStatus {
    companion object {
        const val ALIVE = "Alive"
        const val UNKNOWN = "unknown"
    }
}

@BindingAdapter("epiFormat")
fun bindEpisodeFormat(view: TextView, episode: String?) {
    val epi: String? = episode?.replace("https://rickandmortyapi.com/api/episode/", "")

    view.text = "Episode $epi"
}

@BindingAdapter("updateBackground")
fun bindQuoteBackground(view: View, isDakMode: Boolean?) {
    if (isDakMode == true) {
        view.setBackgroundResource(R.drawable.bg_quote)
    }else{
        view.setBackgroundResource(R.drawable.bg_quote_day)

    }

}