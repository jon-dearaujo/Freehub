package jonathansilva.com.freehub.searchbox

import android.widget.EditText
import android.widget.ImageButton
import io.reactivex.Observable
import io.reactivex.ObservableEmitter

class SearchBoxPresenter: SearchBoxContract.Presenter {

    override fun listenToActions(editText: EditText, button: ImageButton): Observable<String> {
        return Observable.create<String> { emitter ->
            listenToTextEditActions(editText, emitter)
            listenToButtonClicks(button, editText, emitter)

            emitter.setCancellable {
                editText.setOnEditorActionListener(null)
                button.setOnClickListener(null)
            }
        }.filter { it -> !it.isNullOrEmpty()}
                .filter { it -> it.isNotEmpty()}
    }

    private fun listenToTextEditActions(editText: EditText, emitter: ObservableEmitter<String>) {
        editText.setOnEditorActionListener { view, _, _ ->
            emitter.onNext(view.text.toString())
            true
        }
    }

    private fun listenToButtonClicks(button: ImageButton, editText: EditText, emitter: ObservableEmitter<String>) {
        button.setOnClickListener {
            emitter.onNext(editText.text.toString())
        }
    }
}