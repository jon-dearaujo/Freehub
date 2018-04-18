package jonathansilva.com.freehub.searchbox

import android.widget.EditText
import android.widget.ImageButton
import io.reactivex.Observable

interface SearchBoxContract {
    interface View { }
    interface Presenter {
        fun listenToActions(editText: EditText, button: ImageButton): Observable<String>
        fun listenToTextChanges(editText: EditText): Observable<String>
    }
}
