package com.programmergabut.core.utils

import io.reactivex.functions.BiFunction

fun <R, T> mergeObservable(): BiFunction<R, T, Pair<R, T>> =
    BiFunction { item1, item2 -> Pair(item1, item2) }
