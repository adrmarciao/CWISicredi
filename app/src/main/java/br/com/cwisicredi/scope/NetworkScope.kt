package br.com.cwisicredi.scope

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

class NetworkScope(private val job: Job) : CoroutineScope {

    override val coroutineContext: CoroutineContext by lazy { Dispatchers.IO + job }

}