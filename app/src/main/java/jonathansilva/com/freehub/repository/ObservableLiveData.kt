package jonathansilva.com.freehub.repository

import android.arch.lifecycle.LiveData
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

/*Transforms RxJava Observable<T> to Android LiveData<T>*/
class ObservableLiveData<T>(val observable: Observable<T>): LiveData<T>() {
    private var disposable: Disposable? = null

    override fun onActive() {
        disposable = observable
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe {data ->
                    value = data
                }
    }

    override fun onInactive() {
        disposable?.let {
            it.dispose()
        }
    }

}