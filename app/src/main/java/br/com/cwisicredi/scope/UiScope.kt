package br.com.cwisicredi.scope

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

class UiScope(private val job: Job) : CoroutineScope {

    override val coroutineContext: CoroutineContext by lazy { Dispatchers.Main + job }

}