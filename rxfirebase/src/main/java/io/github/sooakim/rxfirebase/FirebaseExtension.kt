package io.github.sooakim.rxfirebase

import com.google.android.gms.tasks.Task
import io.reactivex.Completable
import io.reactivex.Single

fun <T> Task<T>.rxSingle(): Single<T> {
    return Single.create { emitter ->
        this.addOnCompleteListener { task ->
            when (task.isSuccessful) {
                true -> task.result?.let {
                    emitter.onSuccess(it)
                }
                false -> task.exception?.let {
                    emitter.onError(it)
                }
            }
        }
    }
}

fun <T> Task<T>.rxCompletable(): Completable {
    return Completable.create { emitter ->
        this.addOnCompleteListener { task ->
            when (task.isSuccessful) {
                true -> emitter.onComplete()
                false -> task.exception?.let {
                    emitter.onError(it)
                }
            }
        }
    }
}