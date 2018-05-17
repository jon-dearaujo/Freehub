package jonathansilva.com.freehub.repository

import android.arch.lifecycle.LiveData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/*Transforms Retrofit Call to Android LiveData<T>*/
class RetrofitLiveData<T>(
        private val call: Call<T>,
        private val state: LiveData<LoadDataState>): LiveData<T>(), Callback<T> {
    override fun onActive() {
        if (!call.isExecuted && !call.isCanceled) {
            state.value = LoadDataState.LOADING
            call.enqueue(this)
        }
    }

    override fun onResponse(call: Call<T>?, response: Response<T>?) {
        value = response?.body()
        state.value = LoadDataState.LOADED
    }

    override fun onFailure(call: Call<T>?, t: Throwable?) {
    }

}