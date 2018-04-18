package jonathansilva.com.freehub.searchbox

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import io.reactivex.Observable
import jonathansilva.com.freehub.R

import kotlinx.android.synthetic.main.view_search_box.view.*

class SearchBoxView @JvmOverloads constructor(context: Context, attributes: AttributeSet? = null,
        defStyleAttribute: Int = 0, defStyleRes: Int = 0):
        LinearLayout(context, attributes, defStyleAttribute, defStyleRes),
        SearchBoxContract.View {

    private var presenter: SearchBoxContract.Presenter

    init {
        LayoutInflater.from(context).inflate(R.layout.view_search_box, this)
        presenter = SearchBoxPresenter()
    }

    fun onSearch(): Observable<String> {
        return presenter.listenToActions(editText, imageButton)
    }

    fun onEdit(): Observable<String> {
        return presenter.listenToTextChanges(editText)
    }

    fun showLoading() {
        imageButton.visibility = View.INVISIBLE
        progressBar.visibility = View.VISIBLE
        editText.isEnabled = false
    }

    fun hideLoading() {
        imageButton.visibility = View.VISIBLE
        progressBar.visibility = View.INVISIBLE
        editText.isEnabled = true
    }
}